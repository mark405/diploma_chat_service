//package com.diploma.chat;
//
//import com.diploma.chat.models.ChatMessage;
//import com.diploma.chat.models.ChatNotificationModel;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.messaging.simp.stomp.StompHeaders;
//import org.springframework.messaging.simp.stomp.StompSession;
//import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.client.WebSocketTestClient;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//class ChatApplicationTests {
//
//	@Test
//	void contextLoads() {
//	}
//
//	@Test
//	public void testWebSocketMessageHandling() throws Exception {
//		// Set up the WebSocketTestClient to connect to your endpoint
//		WebSocketTestClient testClient = new WebSocketTestClient();
//		testClient.start();
//
//		WebSocketSession session = testClient.connect("ws://localhost:8080/ws").get();
//
//		// Define a Stomp handler that will listen to the response
//		StompSession stompSession = session.connect().get();
//
//		stompSession.subscribe("/user/queue/messages", new StompSessionHandlerAdapter() {
//			@Override
//			public void handleFrame(StompHeaders headers, Object payload) {
//				ChatNotificationModel notification = (ChatNotificationModel) payload;
//				// Assert that the message received matches expected content
//				assertThat(notification.getSenderId()).isEqualTo("senderId");
//				assertThat(notification.getSenderName()).isEqualTo("senderName");
//			}
//		});
//
//		ChatMessage testMessage = new ChatMessage();
//		testMessage.setSenderId("senderId");
//		testMessage.setReceiverId("receiverId");
//		testMessage.setSenderName("senderName");
//
//		// Send a test message through WebSocket (it should be received by the above handler)
//		stompSession.send("/app/chat", testMessage);
//
//		// Validate results here
//		// You could add more checks to ensure the message is processed correctly
//	}
//
//}
