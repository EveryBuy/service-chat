package ua.everybuy.routing.controler.message;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.everybuy.buisnesslogic.service.AwsS3Service;
import ua.everybuy.buisnesslogic.service.MessageService;
import ua.everybuy.routing.dto.request.MessageRequest;
import ua.everybuy.routing.dto.response.subresponse.subresponsemarkerimpl.MessageResponse;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MessageControllerImpl implements MessageController{
    private final MessageService messageService;
    private final AwsS3Service awsS3Service;

    @Override
    @CrossOrigin("*")
    @MessageMapping("/chat/{chatId}")
    @SendTo("/topic/chat/{chatId}")
    public MessageResponse messaging(@DestinationVariable long chatId, MessageRequest message, Principal principal) {
        MessageResponse response = messageService.createMessage(chatId, message, principal);
        System.out.println(response);
        return response;
    }

//    Method for aws s3 testing

//    @PostMapping("/send-file")
//    public String sendFile(@RequestParam MultipartFile file, Principal principal) throws IOException {
//        messageService.createMessage(15,
//                new MessageRequest("test message s3", awsS3Service.uploadFile(file)), principal);
//        return awsS3Service.uploadFile(file);
//    }
}
