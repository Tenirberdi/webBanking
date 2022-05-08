package web.bank.webBanking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import web.bank.webBanking.DTO.ClientDTO;
import web.bank.webBanking.Exceptions.CustomException;
import web.bank.webBanking.Models.Usr;
import web.bank.webBanking.Services.AdminService;
import web.bank.webBanking.Services.BankerService;

import java.util.List;

@SpringBootApplication
public class WebBankingApplication {



	public static void main(String[] args) throws CustomException {
		SpringApplication.run(WebBankingApplication.class, args);


	}

}
