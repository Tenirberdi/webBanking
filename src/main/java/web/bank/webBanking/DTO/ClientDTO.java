package web.bank.webBanking.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
public interface ClientDTO {
    long getId();
    String getFull_name();
    String getAddress();
    String getPhone();
    int getSom_cash();
    int getDollar_cash();
    int getCredit();
}
