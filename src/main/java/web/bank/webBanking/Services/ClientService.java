package web.bank.webBanking.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.bank.webBanking.Common.CommonFunc;
import web.bank.webBanking.DTO.ClientDTO;
import web.bank.webBanking.DTO.TransactionReportDTO;
import web.bank.webBanking.Exceptions.CustomException;
import web.bank.webBanking.Models.Account;
import web.bank.webBanking.Models.Rate;
import web.bank.webBanking.Models.Usr;
import web.bank.webBanking.Repositories.*;

import java.util.List;

@Service
public class ClientService {

    private CommonFunc commonFunc;

    @Autowired
    UsrRepo usrRepo;

    @Autowired
    AccountRepo accountRepo;

    @Autowired
    ConversionsRepo conversionsRepo;

    @Autowired
    TransactionsRepo transactionsRepo;

    @Autowired
    RateRepo rateRepo;



    public ClientDTO getClientInfo(){
        return usrRepo.getClient(getCurrentUser().getId());
    }

    @Transactional
    public void convert(String rate, int amount) throws CustomException{

        int size = 0;
        String fCurrency = "";

        if(rate.equals("dollar")){
            fCurrency = "som";
        }else if(rate.equals("som")){
            fCurrency = "dollar";
        }

        if(rate.equals("dollar")){
            int sum = amount * getRate().get(0).getBuyDollar();
            size = accountRepo.checkSomCash(sum, getCurrentUser().getId()).size();

            if(size == 0){
                throw new CustomException("not enough money on balance");
            }
        }else if (rate.equals("som")){
            int sum = amount / getRate().get(0).getSellDollar();
            size = accountRepo.checkDollarCash(sum,getCurrentUser().getId()).size();

            if(size == 0){
                throw new CustomException("not enough money on balance");
            }
        }

        if(rate.equals("dollar")){
            accountRepo.buyDollar(amount, getCurrentUser().getId());
            conversionsRepo.recordBuyConversion(getCurrentUser().getId(), amount, fCurrency, rate);
        }else if (rate.equals("som")){
            accountRepo.sellDollar(amount, getCurrentUser().getId());
            conversionsRepo.recordSellConversion(getCurrentUser().getId(), amount, fCurrency, rate);
        }

    }

    @Transactional
    public void makeTransaction(String rate, int amount, String getterInfo) throws CustomException{
        int size = 0;
        boolean isSom = true;
        boolean isDollar = true;

        long getterId;
        try{
            getterId = Integer.parseInt(getterInfo);
        }catch (NumberFormatException nfe){
            getterId = usrRepo.getClientByFullName(getterInfo).getId();
        }

        if(usrRepo.getById(getterId) == null){
            throw new CustomException("client not found");
        }

        long clientId = getCurrentUser().getId();

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

        transactionsRepo.recordClientTransaction(rate, clientId, amount, getterId);
    }

    public List<TransactionReportDTO> getTransactionHistory(){
        return transactionsRepo.getTransactionHistory(getCurrentUser().getId());
    }

    public Usr getCurrentUser(){
        return usrRepo.getUsrIdByLogin(CommonFunc.getCurrentUsersUserName());
    }

    public List<Rate> getRate(){
        return (List<Rate>)rateRepo.findAll();
    }
}
