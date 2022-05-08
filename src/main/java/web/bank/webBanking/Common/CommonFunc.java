package web.bank.webBanking.Common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import web.bank.webBanking.Models.Usr;
import web.bank.webBanking.Repositories.UsrRepo;

public class CommonFunc {




    public static String getCurrentUsersUserName(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails)principal).getUsername();
        } else {
            return principal.toString();
        }
    }


}
