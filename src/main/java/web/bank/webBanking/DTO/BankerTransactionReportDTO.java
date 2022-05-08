package web.bank.webBanking.DTO;


public interface BankerTransactionReportDTO {
    long getId();
    int getAmount();
    String getRate();
    String getT_date();
    long getClientId();
    String getClient();
    String getBanker();
    long getGetterId();
    String getGetter();
}

