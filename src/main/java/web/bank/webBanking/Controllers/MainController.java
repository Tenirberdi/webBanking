package web.bank.webBanking.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import web.bank.webBanking.DTO.ClientDTO;
import web.bank.webBanking.Exceptions.CustomException;
import web.bank.webBanking.Models.Usr;
import web.bank.webBanking.Services.BankerService;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    BankerService bankerService;


    @GetMapping("/")
    public String getStart() throws CustomException {
        return "Main";
    }

    @GetMapping("/home")
    public String getStart1() throws CustomException {
        return "Main";
    }

    @GetMapping("/login")
    public String login() throws CustomException {
        return "Main";
    }




}
