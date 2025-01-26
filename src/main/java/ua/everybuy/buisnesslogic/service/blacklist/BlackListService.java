package ua.everybuy.buisnesslogic.service.blacklist;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ua.everybuy.buisnesslogic.service.util.PrincipalConvertor;
import ua.everybuy.database.entity.BlackList;
import ua.everybuy.database.repository.BlackListRepository;
import ua.everybuy.errorhandling.exceptions.subexceptionimpl.BlockNotFoundException;
import ua.everybuy.routing.dto.mapper.BlackListMapper;
import ua.everybuy.routing.dto.response.StatusResponse;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class BlackListService {
    private final BlackListRepository blackListRepository;
    private final BlackListMapper blackListMapper;
    private final BlackListValidateService blackListValidateService;


    public StatusResponse blockUser(Principal principal, long blockedUserId) {
        long userId = PrincipalConvertor.extractPrincipalId(principal);
        blackListValidateService.validateBlockCreation(userId, blockedUserId);
        BlackList saved = blackListRepository.save(new BlackList(userId, blockedUserId));
        return new StatusResponse(HttpStatus.CREATED.value(), blackListMapper.convertToResponse(saved));
    }

    public void unblockUser(Principal principal, long blockedUserId) {
        long userId = PrincipalConvertor.extractPrincipalId(principal);
        blackListValidateService.ensureUserExist(blockedUserId);
        BlackList blackListToDelete = getBlockIfExist(userId, blockedUserId);
        blackListRepository.delete(blackListToDelete);
    }


    private BlackList getBlockIfExist(long userId, long blockedUserId){
        return blackListRepository
                .findByUserIdAndBlockedUserId(userId, blockedUserId)
                .orElseThrow(() -> new BlockNotFoundException(userId, blockedUserId));
    }

}
