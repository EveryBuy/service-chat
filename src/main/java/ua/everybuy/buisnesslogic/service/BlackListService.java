package ua.everybuy.buisnesslogic.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ua.everybuy.database.entity.BlackList;
import ua.everybuy.database.repository.BlackListRepository;
import ua.everybuy.errorhandling.exceptions.subexceptionimpl.BlockAlreadyExistsException;
import ua.everybuy.errorhandling.exceptions.subexceptionimpl.BlockNotFoundException;
import ua.everybuy.routing.dto.mapper.BlackListMapper;
import ua.everybuy.routing.dto.response.StatusResponse;

import java.security.Principal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlackListService {
    private final BlackListRepository blackListRepository;
    private final BlackListMapper blackListMapper;

    public StatusResponse blockUser(Principal principal, long blockedUserId){
        long userId = Long.parseLong(principal.getName());

        if (blackListRepository.existsBlackListByUserIdAndBlockedUserId(userId, blockedUserId)){
            throw new BlockAlreadyExistsException(userId, blockedUserId);
        }

        BlackList saved = blackListRepository.save(new BlackList(userId, blockedUserId));

        return new StatusResponse(HttpStatus.CREATED.value(), blackListMapper.convertToResponse(saved));
    }

    public void unblockUser(Principal principal, long blockedUserId){
        Optional<BlackList> blackListToDelete = blackListRepository
                .findByUserIdAndBlockedUserId(Long.parseLong(principal.getName()), blockedUserId);

        if (blackListToDelete.isEmpty()){
            throw new BlockNotFoundException(Long.parseLong(principal.getName()), blockedUserId);
        }
        blackListRepository.delete(blackListToDelete.get());
    }

    public boolean isUserBlocked(long recipientId, long senderId){
        return blackListRepository.existsBlackListByUserIdAndBlockedUserId(recipientId, senderId);
    }
}
