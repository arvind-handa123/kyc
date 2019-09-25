package co.yabx.kyc.app.controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import co.yabx.kyc.app.dto.KycDetailsDTO;
import co.yabx.kyc.app.miniKyc.entity.AccountStatuses;
import co.yabx.kyc.app.miniKyc.entity.KycDetails;
import co.yabx.kyc.app.service.AccountStatusService;
import co.yabx.kyc.app.service.AccountStatusTrackerService;
import co.yabx.kyc.app.service.AppConfigService;
import co.yabx.kyc.app.service.KYCService;

/**
 * 
 * @author Asad.ali
 *
 */
@Controller
@CrossOrigin
@RequestMapping(value = "/v1")
public class KYCController {
	@Autowired
	private KYCService kycService;

	@Autowired
	private AccountStatusService accountStatusService;

	@Autowired
	private AccountStatusTrackerService accountStatusTrackerService;

	@Autowired
	private AppConfigService appConfigService;

	private static final Logger LOGGER = LoggerFactory.getLogger(KYCController.class);

	/**
	 * 
	 * @param kycDetailsDTO
	 * @return
	 */
	@RequestMapping(value = "/kyc/create", method = RequestMethod.POST)
	public ResponseEntity<?> createAccount(@RequestBody List<KycDetailsDTO> kycDetailsDTO) {
		if (appConfigService.getBooleanProperty("IS_TO_DISPLAY_POST_API_LOGS", true))
			LOGGER.info("/v1/kyc/create request recieved with parameters={}", kycDetailsDTO);
		if (kycDetailsDTO != null) {
			List<KycDetails> kycDetailsList = kycService.persistKYC(kycDetailsDTO);
			if (kycDetailsList != null && !kycDetailsList.isEmpty()) {
				for (KycDetails kycDetails : kycDetailsList) {
					AccountStatuses accountStatuses = accountStatusService.createAccountStatus(kycDetails);
					/*
					 * if (accountStatuses != null) {
					 * accountStatusTrackerService.createAccountTracker(accountStatuses); }
					 */
				}
				return new ResponseEntity<>(HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	/**
	 * 
	 * @param msisdn
	 * @param masked
	 * @param scrumbled
	 * @return
	 */
	@RequestMapping(value = "/kyc/details", method = RequestMethod.GET)
	public ResponseEntity<?> getKycdetails(@RequestParam(name = "msisdn") String msisdn,
			@RequestParam(name = "masked", required = false) boolean masked,
			@RequestParam(name = "scrumbled", required = false) boolean scrumbled) {
		if (appConfigService.getBooleanProperty("IS_TO_DISPLAY_GET_API_LOGS", true))
			LOGGER.info("/v1/kyc/details request recieved for msisdn={}", msisdn);
		if (msisdn != null && !msisdn.isEmpty()) {
			KycDetailsDTO kycDetailsDTO = kycService.getKycDetails(msisdn, masked, scrumbled);
			if (kycDetailsDTO != null) {
				return new ResponseEntity<>(kycDetailsDTO, HttpStatus.OK);
			}
			return new ResponseEntity<>("Customer KYC is not available", HttpStatus.NOT_FOUND);

		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	/**
	 * 
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/upload/photo", method = RequestMethod.POST)
	public ResponseEntity<?> createAccount(@RequestParam(name = "file") MultipartFile file) {
		File convFile = new File(file.getOriginalFilename());
		try {
			convFile.createNewFile();
			try (FileOutputStream fos = new FileOutputStream(convFile)) {
				fos.write(file.getBytes());
			}
			BufferedImage image = null;
			String extension = FilenameUtils.getExtension(file.getOriginalFilename());
			image = ImageIO.read(convFile);
			ImageIO.write(image, extension, new File("D://" + file.getOriginalFilename()));
		} catch (Exception e) {

		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
