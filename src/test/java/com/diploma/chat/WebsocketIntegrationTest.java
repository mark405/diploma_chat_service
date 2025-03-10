package com.diploma.chat;

import com.diploma.chat.models.ChatMessage;
import com.diploma.chat.models.ChatNotificationModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebsocketIntegrationTest {

    @LocalServerPort
    private int port;

    private String getWsUrl() {
        return "ws://localhost:" + port + "/ws";
    }

    BlockingQueue<ChatNotificationModel> blockingQueue;
    WebSocketStompClient stompClient;

    @BeforeEach
    public void setup() {
        blockingQueue = new LinkedBlockingDeque<>();
        List<Transport> transports = List.of(new WebSocketTransport(new StandardWebSocketClient()));
        SockJsClient sockJsClient = new SockJsClient(transports);
        stompClient = new WebSocketStompClient(sockJsClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
    }

    @Test
    void shouldReceiveAMessageFromTheServer() throws Exception {
        CompletableFuture<StompSession> futureSession = stompClient.connectAsync(getWsUrl(), new StompSessionHandlerAdapter() {
            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                session.subscribe("/user/receiver1/queue/messages", new StompFrameHandler() {
                    @Override
                    public Type getPayloadType(StompHeaders headers) {
                        return ChatNotificationModel.class;
                    }

                    @Override
                    public void handleFrame(StompHeaders headers, Object payload) {
                        ChatNotificationModel notification = (ChatNotificationModel) payload;
                        blockingQueue.offer(notification);
                    }
                });

                ChatMessage testMessage = new ChatMessage();
                testMessage.setSenderId("sender1");
                testMessage.setSenderName("Sender One");
                testMessage.setReceiverId("receiver1");
                testMessage.setContent("Test Message");
                session.send("/app/chat", testMessage);
            }
        });

        StompSession stompSession = futureSession.get(5, TimeUnit.SECONDS);
        assertNotNull(stompSession);

        ChatNotificationModel notification = blockingQueue.poll(10, TimeUnit.SECONDS);
        assertNotNull(notification, "Did not receive chat notification in time");

        assertEquals("sender1", notification.getSenderId());
        assertEquals("Sender One", notification.getSenderName());

        stompSession.disconnect();
    }
}
