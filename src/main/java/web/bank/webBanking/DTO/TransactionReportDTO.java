package web.bank.webBanking.DTO;


public interface TransactionReportDTO {
    long getId();
    int getAmount();
    String getRate();
    String getT_date();
    String getClient();
    String getBanker();
    String getGetter();
}
