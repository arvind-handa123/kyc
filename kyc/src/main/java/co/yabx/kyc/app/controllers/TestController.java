package co.yabx.kyc.app.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import co.yabx.kyc.app.fullKyc.entity.AttachmentDetails;
import co.yabx.kyc.app.fullKyc.entity.User;
import co.yabx.kyc.app.service.AttachmentService;
import co.yabx.kyc.app.service.StorageService;
import co.yabx.kyc.app.service.UserService;

/**
 * 
 * @author Asad.ali
 *
 */
@Controller
@CrossOrigin
@RequestMapping(value = "/v1")
public class TestController {

	@Autowired
	private StorageService storageService;

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private UserService userService;

	private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

	@RequestMapping(value = "/upload/image", method = RequestMethod.POST)
	public ResponseEntity<?> uploadImage(@RequestParam("retailerId") Long retailerId, @RequestParam MultipartFile files,
			HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
		User user = userService.getRetailerById(retailerId);
		try {
			String filename = storageService.uploadImage(files);
			AttachmentDetails attachmentDetails = attachmentService.persistInDb(user, files, filename);
			if (attachmentDetails != null)
				return new ResponseEntity<>(files, HttpStatus.OK);
			else {
				return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("exception raised while uploading image={},retailer={},error={}", files.getOriginalFilename(),
					retailerId, e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
