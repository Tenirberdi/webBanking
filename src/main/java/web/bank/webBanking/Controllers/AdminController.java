package web.bank.webBanking.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import web.bank.webBanking.DTO.UsrDTO;
import web.bank.webBanking.Exceptions.CustomException;
import web.bank.webBanking.Models.Usr;
import web.bank.webBanking.Services.AdminService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping
    public String getStart(Model model) throws CustomException{
        model.addAttribute("report", adminService.getReport());


        return "admin";
    }

    @GetMapping("/error")
    public String getErrorPage(Model model){
        model.addAttribute("error", (String) model.asMap().get("error"));
        model.addAttribute("path", "/admin");
        return "error";
    }

    @GetMapping("/register")
    public String registerPage(){
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam("login") String login , @RequestParam("password") String password, @RequestParam("fullName") String fullName , @RequestParam("role") int roleId, @RequestParam("address") String address, @RequestParam("phone") String phone, RedirectAttributes redirectAttributes) throws CustomException {
        try {
            if (roleId == 1) {
                adminService.addAdmin(login, password, fullName, address, phone);
            } else if (roleId == 2) {
                adminService.addBanker(login, password, fullName, address, phone);
            } else if (roleId == 3) {
                adminService.addClient(login, password, fullName, address, phone);
            }

            return "redirect:/admin";
        }catch(CustomException ce){
            redirectAttributes.addFlashAttribute("error", ce.getMessage());
            return "redirect:/admin/error";
        }

    }

    @GetMapping("/delete/{userId}")
    public String deleteUser(@PathVariable("userId") long id, RedirectAttributes redirectAttributes) throws CustomException{
        try{
            adminService.deleteUser(id);
            return "redirect:/admin/users";
        }catch(CustomException ce){
            redirectAttributes.addFlashAttribute("error", ce.getMessage());
            return "redirect:/admin/error";
        }
    }


    @GetMapping("/users")
    public String getUsers(Model model){

        List<UsrDTO> list = adminService.getUsers(0);
        model.addAttribute("users", list);
        return "Users";
    }
}
