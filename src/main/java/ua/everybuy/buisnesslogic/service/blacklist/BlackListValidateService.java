package ua.everybuy.buisnesslogic.service.blacklist;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.everybuy.buisnesslogic.service.integration.UserInfoService;
import ua.everybuy.database.repository.BlackListRepository;
import ua.everybuy.errorhandling.exceptions.subexceptionimpl.BlockAlreadyExistsException;
import ua.everybuy.errorhandling.exceptions.subexceptionimpl.BlockUserException;
import ua.everybuy.errorhandling.exceptions.subexceptionimpl.SelfBlockException;

@Service
@RequiredArgsConstructor
public class BlackListValidateService {
    private final BlackListRepository blackListRepository;
    private final UserInfoService userInfoService;

    public void validateBlockCreation(long userId, long blockedUserId) {
        ensureUserExist(userId);
        checkIfBlockExist(userId, blockedUserId);
        checkSelfBlocking(userId, blockedUserId);
    }

    public void checkBlock(long chatMemberIdFirst, long chatMemberIdSecond) {
        boolean isBlockExist =  isUserInBlackList(chatMemberIdFirst, chatMemberIdSecond)
                || isUserInBlackList(chatMemberIdSecond, chatMemberIdFirst);
        if (isBlockExist){
            throw new BlockUserException();
        }
    }

    public boolean isUserInBlackList(long checkingUserId, long checkedUserId) {
        return blackListRepository.existsBlackListByUserIdAndBlockedUserId(checkingUserId, checkedUserId);
    }

    private void checkIfBlockExist(long userId, long blockedUserId) {
        if (blackListRepository.existsBlackListByUserIdAndBlockedUserId(userId, blockedUserId)) {
            throw new BlockAlreadyExistsException(userId, blockedUserId);
        }
    }

    private void checkSelfBlocking(long userId, long blockedUserId) {
        if (userId == blockedUserId) {
            throw new SelfBlockException(userId);
        }
    }

    public void ensureUserExist(long userId) {
        userInfoService.getShortUserInfo(userId); //if such user doesn't exist UserNotFoundException will be thrown
    }

}
