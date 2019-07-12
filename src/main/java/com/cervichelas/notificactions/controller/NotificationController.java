package com.cervichelas.notificactions.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.cervichelas.notificactions.entity.NotificationDTO;
import com.cervichelas.notificactions.entity.TopicDTO;
import com.cervichelas.notificactions.services.PushNotificationsService;

@RestController
@RequestMapping("/api/Notifications")
public class NotificationController {

	@Autowired
	PushNotificationsService pushNotificationsService;


	@RequestMapping(value = "/send", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> sendNotification(@RequestBody NotificationDTO notificationDTO) throws JSONException {

		JSONObject body = new JSONObject();
		body.put("to", "/topics/" + notificationDTO.getTopic());
		body.put("priority", "high");

		JSONObject notification = new JSONObject();
		notification.put("title", notificationDTO.getTitle());
		notification.put("body", notificationDTO.getBody());
		notification.put("url_imagen", notificationDTO.getUrl_imagen());

//	        JSONObject data = new JSONObject();
//	        data.put("Key-1", "JSA Data 1");
//	        data.put("Key-2", "JSA Data 2");

		body.put("notification", notification);
//	        body.put("data", data);

		HttpEntity<String> request = new HttpEntity<>(body.toString());

		CompletableFuture<String> pushNotification = pushNotificationsService.send(request);
		CompletableFuture.allOf(pushNotification).join();

		try {
			String firebaseResponse = pushNotification.get();

			return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		return new ResponseEntity<>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/subscribe", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> subscribeToTopic(@RequestBody TopicDTO topicDTO) {
		CompletableFuture<String> stringCompletableFuture = pushNotificationsService.subscribeToTopic(topicDTO);
		CompletableFuture.allOf(stringCompletableFuture).join();

		try {
			String firebaseResponse = stringCompletableFuture.get();

			return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);
	}

}
