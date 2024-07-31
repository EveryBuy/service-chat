package ua.everybuy.buisnesslogic.service.util;

import ua.everybuy.errorhandling.exceptions.subexceptionimpl.UserNotFoundException;
import java.security.Principal;

public class PrincipalConvertor {
    public static long extractPrincipalId(Principal principal){
       if(principal == null){
           throw new UserNotFoundException();
       }
        return Long.parseLong(principal.getName());
    }
}
