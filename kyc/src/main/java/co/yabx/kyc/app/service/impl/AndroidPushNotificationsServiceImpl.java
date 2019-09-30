package co.yabx.kyc.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import co.yabx.kyc.app.entities.Device;
import co.yabx.kyc.app.interceptor.HeaderRequestInterceptor;
import co.yabx.kyc.app.repositories.DeviceRepository;
import co.yabx.kyc.app.service.AndroidPushNotificationsService;
import co.yabx.kyc.app.service.AppConfigService;

/**
 * 
 * @author Asad.ali
 *
 */
@Service
public class AndroidPushNotificationsServiceImpl implements AndroidPushNotificationsService {

	@Autowired
	private DeviceRepository deviceRepository;

	@Autowired
	private AppConfigService appConfigService;

	public CompletableFuture<String> send(HttpEntity<String> entity) {

		RestTemplate restTemplate = new RestTemplate();

		ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		interceptors.add(new HeaderRequestInterceptor("Authorization",
				"key=" + appConfigService.getProperty("FIREBASE_SERVER_KEY")));
		interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
		restTemplate.setInterceptors(interceptors);

		String firebaseResponse = restTemplate.postForObject(appConfigService.getProperty("FIREBASE_API_URL"), entity,
				String.class);

		return CompletableFuture.completedFuture(firebaseResponse);
	}

	@Override
	public ResponseEntity<String> sendNotificationToDevice(String deviceId, String title, String message,
			String dataToSend) {
		try {
			JSONObject body = new JSONObject();
			body.put("to", deviceId);
			body.put("priority", "high");

			JSONObject notification = new JSONObject();
			notification.put("title", title);
			notification.put("body", message);

			JSONObject data = new JSONObject();
			data.put("key", dataToSend);

			body.put("notification", notification);
			body.put("data", data);

			HttpEntity<String> request = new HttpEntity<>(body.toString());

			CompletableFuture<String> pushNotification = send(request);
			CompletableFuture.allOf(pushNotification).join();

			try {
				String firebaseResponse = pushNotification.get();

				return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
			} catch (InterruptedException | ExecutionException ex) {
			}

			return new ResponseEntity<>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);
		} catch (JSONException ex) {
			Logger.getLogger(AndroidPushNotificationsService.class.getName()).log(Level.SEVERE, null, ex);
			return new ResponseEntity<>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);
		}
	}

	@Async
	public void sendNotificationToUserAsync(Long user, String title, String message, String data) {

		List<Device> devices = deviceRepository.getDevicesByUser(user);

		for (Device d : devices) {
			ResponseEntity<String> result = this.sendNotificationToDevice(d.getDeviceId(), title, message, data);
		}
	}

	public void sendNotificationToUser(Long user, String title, String message, String data) {

		List<Device> devices = deviceRepository.getDevicesByUser(user);

		for (Device d : devices) {
			ResponseEntity<String> result = this.sendNotificationToDevice(d.getDeviceId(), title, message, data);
		}
	}

	@Async
	public void notifyAllQC() {

	}
}
