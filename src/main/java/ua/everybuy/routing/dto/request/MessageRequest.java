package ua.everybuy.routing.dto.request;


public record MessageRequest(String text,
                             Long chatId,
                             String imageUrl,
                             String fileUrl) {

}
