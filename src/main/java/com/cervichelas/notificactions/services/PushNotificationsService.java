package com.cervichelas.notificactions.services;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.client.RestTemplate;

import com.cervichelas.notificactions.auth.HeaderRequestInterceptor;
import com.cervichelas.notificactions.entity.TopicDTO;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

@Service
public class PushNotificationsService {

	private static final String FIREBASE_SERVER_KEY = "AAAAu42ttHw:APA91bGTL7429zItCcqHhwYBwEAmGKXRtPFb8O45ASh5dZaGZrNt-RLZapJW42twRMl_dBAmivV2CDJhpDVTBhAQasJihKwq1EhS_29pYkYWIZqIQaZEFTDTgvpXX6nMkc7YK90Tz2-K";
	private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";
	private static final String FIREBASE_SUBSCRIBE_URL = "https://iid.googleapis.com/iid/v1/";
	

	@Async
	public CompletableFuture<String> send(HttpEntity<String> entity) {

		RestTemplate restTemplate = new RestTemplate();

		ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
		interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
		restTemplate.setInterceptors(interceptors);

		String firebaseResponse = restTemplate.postForObject(FIREBASE_API_URL, entity, String.class);

		return CompletableFuture.completedFuture(firebaseResponse);
	}

	@Async
	public CompletableFuture<String> subscribeToTopic(TopicDTO topicDTO) {

		RestTemplate restTemplate = new RestTemplate();

		ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
		interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
		restTemplate.setInterceptors(interceptors);

		HttpEntity<String> test = new HttpEntity<>("test");

		for (String deviceToken : topicDTO.getDeviceTokens()) {

			String firebaseResponse = restTemplate.postForObject(
					FIREBASE_SUBSCRIBE_URL + deviceToken + "/rel/topics/" + topicDTO.getTopic(), test, String.class);
		}
		return CompletableFuture.completedFuture("Devices subscription completed");

	}
	
}
