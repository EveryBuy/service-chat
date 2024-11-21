package ua.everybuy.buisnesslogic.service.chat;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.everybuy.buisnesslogic.service.BlackListService;
import ua.everybuy.database.repository.ChatRepository;
import ua.everybuy.errorhandling.exceptions.subexceptionimpl.BlockUserException;
import ua.everybuy.errorhandling.exceptions.subexceptionimpl.ChatAlreadyExistsException;
import ua.everybuy.errorhandling.exceptions.subexceptionimpl.SelfChatCreationException;

@Service
@RequiredArgsConstructor
public class ChatValidateService {
    private final BlackListService blackListService;
    private final ChatRepository chatRepository;

    public void validateChatCreation(long advertisementId, long initiatorId, long adOwnerId){
        checkIfChatPresent(advertisementId, initiatorId, adOwnerId);
        checkIfBuyerIsAdOwner(initiatorId, adOwnerId);
        checkIfUserBlocked(adOwnerId, initiatorId);
    }

    private void checkIfUserBlocked(long adOwnerId, long initiatorId){
        if (blackListService.isUserInBlackList(adOwnerId, initiatorId)) {
            throw new BlockUserException(initiatorId);
        }
    }

    private void checkIfChatPresent(Long advertisementId, Long initiatorId, long adOwnerId
    ) {
        boolean isPresent = chatRepository.existsChatByAdvertisementIdAndInitiatorIdAndAdOwnerId(
                advertisementId,
                initiatorId,
                adOwnerId);

        if (isPresent) {
            throw new ChatAlreadyExistsException();
        }
    }

    private void checkIfBuyerIsAdOwner(long userId, long buyerId) {
        if (userId == buyerId) {
            throw new SelfChatCreationException(userId);
        }
    }
}
