package co.yabx.kyc.app.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import co.yabx.kyc.app.entities.OTP;
import co.yabx.kyc.app.enums.ActionType;
import co.yabx.kyc.app.enums.OtpGroup;
import co.yabx.kyc.app.enums.OtpType;
import co.yabx.kyc.app.fullKyc.entity.DSRUser;
import co.yabx.kyc.app.fullKyc.repository.DSRUserRepository;
import co.yabx.kyc.app.repositories.OtpRepository;
import co.yabx.kyc.app.service.AppConfigService;
import co.yabx.kyc.app.service.EmailService;
import co.yabx.kyc.app.service.OtpService;
import co.yabx.kyc.app.util.MailSource;
import co.yabx.kyc.app.util.UtilHelper;
import freemarker.template.TemplateException;

/**
 * 
 * @author Asad.ali
 *
 */
@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private OtpRepository otpRepository;

	@Autowired
	private AppConfigService appConfigService;

	@Autowired
	private DSRUserRepository dsrUserRepository;

	@Autowired
	private MailSource mailSource;

	@Autowired
	private Environment env;

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

	@Override
	public void sendOTP(OTP otp, String mail, ActionType actionType) {
		try {
			Map<String, Object> variables = new HashMap<>();
			variables.put("otp", otp.getOtp());
			if (mail != null && !mail.isEmpty()) {
				if (ActionType.Verify == actionType)
					mailSource.sendMailTLS(mail, appConfigService.getProperty("VERIFY_EMAIL_SUBJECT", "Verify Email!"),
							appConfigService.getProperty("VERIFY_EMAIL_TEMPLATE_PATH", "D:\\templates\\")
									+ "/mail_templates",
							appConfigService.getProperty("VERIFY_EMAIL_TEMPLATE_FILE_NAME", "verify_email.ftl"),
							variables);
				else
					mailSource.sendMailTLS(mail, appConfigService.getProperty("LOGIN_OTP_EMAIL_SUBJECT", "Login OTP!"),
							appConfigService.getProperty("LOGIN_OTP_EMAIL_TEMPLATE_PATH", "D:\\templates\\")
									+ "/mail_templates",
							appConfigService.getProperty("LOGIN_OTP_EMAIL_TEMPLATE_FILE_NAME",
									"login_mail_templates.ftl"),
							variables);
			}
		} catch (IOException | TemplateException | MessagingException ex) {
			LOGGER.error("Exception while sending OTP to email={},error={}", mail, ex.getMessage());
		}
	}

}
