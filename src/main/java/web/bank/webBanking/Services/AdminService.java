package web.bank.webBanking.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.bank.webBanking.Common.CommonFunc;
import web.bank.webBanking.DTO.AdminReportDTO;
import web.bank.webBanking.DTO.UsrDTO;
import web.bank.webBanking.Exceptions.CustomException;
import web.bank.webBanking.Models.Usr;
import web.bank.webBanking.Repositories.AccountRepo;
import web.bank.webBanking.Repositories.AdministrationRepo;
import web.bank.webBanking.Repositories.UsrRepo;

import java.util.List;

@Service
public class AdminService {

    CommonFunc commonFunc;

    @Autowired
    UsrRepo usrRepo;

    @Autowired
    AccountRepo accountRepo;

    @Autowired
    AdministrationRepo administrationRepo;
    public Usr getCurrentUser(){
        return usrRepo.getUsrIdByLogin(CommonFunc.getCurrentUsersUserName());
    }

    @Transactional
    public void addClient(String login, String pass, String name, String address, String phone) throws  CustomException{

        if(usrRepo.findByLogin(login) != null){
            throw new CustomException("there is already a client with such login");
        }
        usrRepo.addClient(login, pass, name, address, phone);
        Usr client = usrRepo.getUsrIdByLogin(login);
        usrRepo.addAccount(client.getId());
        administrationRepo.recordAdding(client.getId(), getCurrentUser().getId());

    }

    @Transactional
    public void addAdmin(String login, String pass, String name, String address, String phone) throws CustomException{
        if(usrRepo.findByLogin(login) != null){
            throw new CustomException("there is already a client with such login");
        }
        usrRepo.addAdmin(login, pass, name, address, phone);
        Usr admin = usrRepo.getUsrIdByLogin(login);
        administrationRepo.recordAdding(admin.getId(), getCurrentUser().getId());
    }

    @Transactional
    public void addBanker(String login, String pass, String name, String address, String phone) throws CustomException{
        if(usrRepo.findByLogin(login) != null){
            throw new CustomException("there is already a client with such login");
        }
        usrRepo.addBanker(login, pass, name, address, phone);
        Usr banker = usrRepo.getUsrIdByLogin(login);
        administrationRepo.recordAdding(banker.getId(), getCurrentUser().getId());

    }

//    public void deleteClient(long id) throws CustomException{
//        if(usrRepo.existsById(id)){
//            usrRepo.banUsr(id);
//        }else{
//            throw new CustomException("such user doesn't exists");
//        }
//
//
//    }

    @Transactional
    public void deleteUser(long id) throws CustomException{
        if(usrRepo.existsById(id)){
            usrRepo.banUsr(id);
            administrationRepo.recordBaning(id, getCurrentUser().getId());

        }else{
            throw new CustomException("such user doesn't exists");
        }
    }

    public List<Usr> getUsrs(){
        return (List<Usr>) usrRepo.findAll();
    }

    public List<AdminReportDTO> getReport(){
        return administrationRepo.getReport();
    }

    public List<UsrDTO> getUsers(int limit){
        return usrRepo.getUsers(limit);
    }

}
