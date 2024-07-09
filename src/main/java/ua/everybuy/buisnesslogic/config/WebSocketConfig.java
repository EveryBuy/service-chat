package ua.everybuy.buisnesslogic.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.OriginHandshakeInterceptor;

import java.util.Arrays;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        HandshakeInterceptor originInterceptor = new OriginHandshakeInterceptor(Arrays.asList(
                "chrome-extension://mdmlhchldhfnfnkfmljgeinlffmdgkjo"
        ));

        logger.debug("Registering STOMP endpoint with OriginHandshakeInterceptor");

        registry.addEndpoint("/chat");
        registry.addEndpoint("/chat")
                .addInterceptors(originInterceptor)
                .setAllowedOrigins("mdmlhchldhfnfnkfmljgeinlffmdgkjo")
                .withSockJS();
    }
}