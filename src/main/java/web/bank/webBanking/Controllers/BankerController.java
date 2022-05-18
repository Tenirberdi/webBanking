package web.bank.webBanking.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import web.bank.webBanking.Common.CommonFunc;
import web.bank.webBanking.Exceptions.CustomException;
import web.bank.webBanking.Models.Rate;
import web.bank.webBanking.Services.BankerService;

import java.util.List;

@Controller
@RequestMapping("/banker")
public class BankerController {

    @Autowired
    BankerService bankerService;

    private CommonFunc commonFunc = new CommonFunc();

    @GetMapping
    public String getStart(Model model){
        List<Rate> rates = bankerService.getRate();
        model.addAttribute("rates", rates);
        return "banker";
    }

    @GetMapping("/list-clients")
    public String getClients(Model model){

        model.addAttribute("clients", bankerService.getClients());
        return "list-clients";
    }

    @GetMapping("/maxCredit")
    public String getMaxCredit(Model model){

        model.addAttribute("clients", bankerService.getMaxCredit());
        return "list-clients";
    }

    @GetMapping("/client/{clientId}")
    public String getClient(@PathVariable("clientId") long id){
        return "redirect:/banker";
    }

    @GetMapping("/converattion")
    public String getRatePage(Model model){
        List<Rate> rates = bankerService.getRate();
        model.addAttribute("rates", rates);
        return "converattion";
    }

    @PostMapping("/setRate")
    public String setRate(@RequestParam("buy") String buy, @RequestParam("sell") String sell, RedirectAttributes redirectAttributes) throws CustomException {
        try {
            bankerService.setRate(buy, sell);
        }catch(CustomException ce){

            redirectAttributes.addFlashAttribute("error", ce.getMessage());
            return "redirect:/banker/error";
        }
        return "redirect:/banker";

    }

    @GetMapping("/translation")
    public String makeTransfer(){

        return "international-translations";
    }

    @PostMapping("/transfer")
    public String transfer(@RequestParam("getterInfo") String getterInfo, @RequestParam("clientInfo") String clientInfo, @RequestParam("amount") int amount, @RequestParam("rate") String rate, RedirectAttributes redirectAttributes) throws CustomException {
        try{
            bankerService.makeTransaction(rate, clientInfo, amount, getterInfo);
            return "redirect:/banker";
        }
        catch (CustomException ce){
            redirectAttributes.addFlashAttribute("error", ce.getMessage());
            return "redirect:/banker/error";
        }
    }

    @GetMapping("/transfer-report")
    public String getTransfers(Model model){
        model.addAttribute("report", bankerService.getTransactionReport());

        return "transfer-report-banker";
    }

    @GetMapping("/report1/{limit}")
    public String getReport1(Model model, @PathVariable int limit){
        if(limit<0){
            limit = 0;
        }
        model.addAttribute("report", bankerService.getReport1(limit));
        model.addAttribute("limit", limit);
        return "report1";
    }

    @GetMapping("/error")
    public String getErrorPage(Model model){
        model.addAttribute("error", (String) model.asMap().get("error"));
        model.addAttribute("path", "/banker");
        return "error";
    }


}
