package co.yabx.kyc.app.controllers;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import co.yabx.kyc.app.fullKyc.entity.User;
import co.yabx.kyc.app.fullKyc.repository.UserRepository;
import co.yabx.kyc.app.service.AppConfigService;

/**
 * 
 * @author Asad.ali
 *
 */
@Controller
@RequestMapping(value = "/version/v1")
public class AppConfigSyncController {

	private static Logger LOGGER = LoggerFactory.getLogger(AppConfigSyncController.class);

	@Autowired
	private AppConfigService appConfigService;

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "/app/config/sync", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> sync(@RequestParam("msisdn") String msisdn,
			@RequestParam(value = "syncType", required = false, defaultValue = "ALL") String syncType) {
		User user = userRepository.findBymsisdn(msisdn);

		if (user == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		Map<String, Object> resp = new HashMap<>();

		if (syncType.equalsIgnoreCase("ALL")) {

			resp.put("version", appConfigService.getProperty("SERVER_LATEST_VERSION", "1.3"));
			resp.put("updatedTime", appConfigService.getLongProperty("UPDATED_TIME", System.currentTimeMillis()));
			resp.put("locale", appConfigService.getProperty("BANGLADESH_LOCALE", "bn_BD"));
			resp.put("popUpMessage",
					appConfigService.getProperty("POP_UP_MESSGE_FOR_TEXT_TRANSLATION", "Convert text in Bangla?"));

		} else {
			if (syncType.contains("PROFILE_OPTIONS")) {
			}

			if (syncType.contains("VERSION")) {
				resp.put("version", appConfigService.getProperty("SERVER_LATEST_VERSION", "1.3"));
				resp.put("updatedTime", appConfigService.getLongProperty("UPDATED_TIME", System.currentTimeMillis()));
			}
			if (syncType.contains("TIPS")) {
			}
			if (syncType.contains("LOCALE")) {
				resp.put("locale", appConfigService.getProperty("BANGLADESH_LOCALE", "bn_BD"));
				resp.put("popUpMessage",
						appConfigService.getProperty("POP_UP_MESSGE_FOR_TEXT_TRANSLATION", "Convert text in Bangla?"));
			}
		}
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
}