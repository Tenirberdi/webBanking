package web.bank.webBanking.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.bank.webBanking.Common.CommonFunc;
import web.bank.webBanking.DTO.BankerTransactionReportDTO;
import web.bank.webBanking.DTO.ClientDTO;
import web.bank.webBanking.DTO.SumDTO;
import web.bank.webBanking.DTO.TransactionReportDTO;
import web.bank.webBanking.Exceptions.CustomException;
import web.bank.webBanking.Models.Rate;
import web.bank.webBanking.Models.Usr;
import web.bank.webBanking.Repositories.AccountRepo;
import web.bank.webBanking.Repositories.RateRepo;
import web.bank.webBanking.Repositories.TransactionsRepo;
import web.bank.webBanking.Repositories.UsrRepo;

import java.util.List;

@Service
public class BankerService {

    private CommonFunc commonFunc;

    @Autowired
    private TransactionsRepo transactionsRepo;

    @Autowired
    private UsrRepo usrRepo;

    @Autowired
    private RateRepo rateRepo;

    @Autowired
    private AccountRepo accountRepo;

    public List<ClientDTO> getClients(){
        List<ClientDTO> clients = usrRepo.getClients();
        return clients;
    }

    public List<Usr> getUsrs(){
        return (List<Usr>) usrRepo.findAll();
    }

    public ClientDTO getClient(long id){

        return usrRepo.getClient(id);
    }

    public ClientDTO getClientByName(String name){
        ClientDTO client = usrRepo.getClientByName(name);
        return client;
    }

    public List<ClientDTO> getMaxCredit(){
        return usrRepo.getMaxCredit();
    }

    public List<ClientDTO> getMinCredit(){
        return usrRepo.getMinCredit();
    }


    @Transactional
    public void setRate(String buy, String sell) throws  CustomException{

        if(!buy.equals("")){
            try{
                int n = Integer.parseInt(buy);
                setBuyDollar(n);
            }catch(NumberFormatException nfe){
                throw  new CustomException("Entered not acceptable data type");
            }
        }
        if(!sell.equals("")){
            try{
                int n = Integer.parseInt(sell);
                setSellDollar(n);
            }catch(NumberFormatException nfe){
                throw  new CustomException("Entered not acceptable data type");
            }
        }

    }

    @Transactional
    public void setSellDollar(int newSum){
        rateRepo.setSellDollar(newSum);
    }

    @Transactional
    public void setBuyDollar(int newSum){
        rateRepo.setBuyDollar(newSum);
    }

    @Transactional
    public void makeTransaction(String rate, String clientInfo, int amount, String getterInfo) throws CustomException{
        int size = 0;
        boolean isSom = true;
        boolean isDollar = true;

        long clientId;
        long getterId;


        try{
            clientId = Integer.parseInt(clientInfo);
        }catch(NumberFormatException nfe){
            clientId = usrRepo.getClientByFullName(clientInfo).getId();
        }

        try{
            getterId = Integer.parseInt(getterInfo);
        }catch(NumberFormatException nfe){
            getterId = usrRepo.getClientByFullName(getterInfo).getId();
        }

        if(usrRepo.getClient(clientId) == null){
            throw new CustomException("not found user");
        }

        if(usrRepo.getClient(getterId) == null){
            throw new CustomException("not found user");
        }

        long bankerId = getCurrentUser().getId();

        if(rate.equals("som")) {
            isSom = true;
            isDollar = false;
        }else if (rate.equals("dollar")){
            isSom = false;
            isDollar = true;
        }

        if(isSom) {
            size = accountRepo.getAccountBySomCash(clientId,amount).size();
        }else if (isDollar){
            size = accountRepo.getAccountByDollarCash(clientId, amount).size();
        }

        if(size == 0){
            throw new CustomException("not enough money on balance");
        }


        if(isSom){
            accountRepo.makeSomTransaction1(amount, clientId);
            accountRepo.makeSomTransaction2(amount, getterId);
        }else if (isDollar){
            accountRepo.makeDollarTransaction1(amount, clientId);
            accountRepo.makeDollarTransaction2(amount, getterId);
        }

        transactionsRepo.recordTransaction(rate, clientId, bankerId, amount, getterId);
    }

    public List<BankerTransactionReportDTO> getTransactionReport(){
        return transactionsRepo.getTransactionReport();
    }

    public List<SumDTO> getYearSumTransaction(){
        return transactionsRepo.getYearSumTransaction();
    }

    public List<SumDTO> getQuarterSumTransaction(){
        return transactionsRepo.getQuarterSumTransaction();
    }

    public List<SumDTO> getAllSumTransaction(){
        return transactionsRepo.getAllSumTransaction();
    }

    public Usr getCurrentUser(){
        return usrRepo.getUsrIdByLogin(CommonFunc.getCurrentUsersUserName());
    }

    public List<Rate> getRate(){
        return (List<Rate>)rateRepo.findAll();
    }



}
