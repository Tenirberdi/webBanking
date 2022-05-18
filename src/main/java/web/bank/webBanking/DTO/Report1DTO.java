package web.bank.webBanking.DTO;

public interface Report1DTO {
    long getClient_id();
    String getFull_name();
    int getSent();
    int getGot();
    int getConvertedToSom();
    int getConvertedToDollar();
    int getYear();
}
