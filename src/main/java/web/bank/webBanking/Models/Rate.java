package web.bank.webBanking.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="rate")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rate {


    private int sellDollar;
    @Id
    private int buyDollar;
}
