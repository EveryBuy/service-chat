package ua.everybuy.buisnesslogic.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ua.everybuy.database.entity.Chat;
import ua.everybuy.database.repository.ChatRepository;
import ua.everybuy.errorhandling.exceptions.subexceptionimpl.ChatAlreadyExistsException;
import ua.everybuy.errorhandling.exceptions.subexceptionimpl.ChatNotFoundException;
import ua.everybuy.routing.dto.mapper.ChatMapper;
import ua.everybuy.routing.dto.request.ChatRequest;
import ua.everybuy.routing.dto.response.StatusResponse;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;
    private final BlackListService blackListService;

    public StatusResponse createChatRoom(ChatRequest chatRequest, Principal principal){
        throwExcIfChatPresent(chatRequest, principal);
        long buyerId = Long.parseLong(principal.getName());
        Chat savedChat = chatRepository.save(chatMapper.mapRequestToChat(chatRequest, buyerId));
        return new StatusResponse(HttpStatus.CREATED.value(), chatMapper.mapChatToCreateChatResponse(savedChat));
    }

    public Chat findChatById(Long id) {
        return chatRepository.findById(id).orElseThrow(() -> new ChatNotFoundException(id));
    }

    private void throwExcIfChatPresent(ChatRequest chatRequest, Principal principal){
        boolean isPresent = chatRepository.existsChatByAdvertisementIdAndSellerIdAndBuyerId(chatRequest.advertisementId(),
                chatRequest.sellerId(),
                Long.parseLong(principal.getName()));
        if (isPresent) {
            throw new ChatAlreadyExistsException();
        }
    }

    public StatusResponse getChat(Long chatId, Principal principal){
        Chat chat = findChatById(chatId);
        long checkingUserId = Long.parseLong(principal.getName());
        long checkedUserId = checkingUserId == chat.getSellerId() ? chat.getBuyerId() : chat.getSellerId();
        boolean isBlock = blackListService.isUserInBlackList(checkingUserId, checkedUserId);
        return new StatusResponse(HttpStatus.OK.value(), chatMapper.mapChatToChatResponse(chat, isBlock));
    }
}
