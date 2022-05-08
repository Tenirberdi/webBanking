package web.bank.webBanking.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import web.bank.webBanking.Common.CommonFunc;
import web.bank.webBanking.DTO.ClientDTO;
import web.bank.webBanking.Exceptions.CustomException;
import web.bank.webBanking.Models.Rate;
import web.bank.webBanking.Services.ClientService;

import java.util.List;

@Controller
@RequestMapping("/client")
public class ClientController {

    private CommonFunc commonFunc = new CommonFunc();

    @Autowired
    private ClientService clientService;

    @GetMapping
    public String getStart(Model model){
        ClientDTO clientDTO =  clientService.getClientInfo();
        List<Rate> rates = clientService.getRate();

        model.addAttribute("client", clientDTO);
        model.addAttribute("rates", rates);
        return "Client";
    }

    @GetMapping("/myCrediets")
    public String getCredits(Model model){
        ClientDTO clientDTO =  clientService.getClientInfo();
        model.addAttribute("client", clientDTO);

        return "myCrediets";
    }

    @GetMapping("/transfer-money")
    public String makeTransfer(Model model) {

        return "transfer-money";
    }

    @PostMapping("/transfer-money")
    public String transfer(Model model, @RequestParam("getterInfo") String getterInfo, @RequestParam("rate") String rate, @RequestParam("amount") int amount, RedirectAttributes redirectAttributes) throws  CustomException{

        try{
            clientService.makeTransaction(rate,amount,getterInfo);
            return "redirect:/client";
        }
        catch (CustomException ce){
            redirectAttributes.addFlashAttribute("error", ce.getMessage());
            return "redirect:/client/error";
        }

    }

    @GetMapping("/transfer-report")
    public String getTransfers(Model model){
        model.addAttribute("report", clientService.getTransactionHistory());
        return "transfer-report";
    }

    @GetMapping("/buy-currency")
    public String makeCurrency(Model model){

        ClientDTO clientDTO =  clientService.getClientInfo();
        List<Rate> rates = clientService.getRate();

        model.addAttribute("client", clientDTO);
        model.addAttribute("rates", rates);
        return "buy-currency";
    }

    @GetMapping("/error")
    public String getErrorPage(Model model){
        model.addAttribute("error", (String) model.asMap().get("error"));
        model.addAttribute("path", "/client");
        return "error";
    }

    @PostMapping("/buy-currency")
    public String makeCurrencyPost(@RequestParam("amount") int amount, @RequestParam("rate") String rate, RedirectAttributes redirectAttributes) throws CustomException {
        try{
            clientService.convert(rate, amount);
            return "redirect:/client";
        }
        catch (CustomException ce){
            redirectAttributes.addFlashAttribute("error", ce.getMessage());
            return "redirect:/client/error";
        }


    }
}
