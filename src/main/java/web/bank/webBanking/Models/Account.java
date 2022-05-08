package web.bank.webBanking.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="account")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private int som_cash;
    private int dollar_cash;
    private int credit;


    @ManyToOne
    @JoinColumn(name="usr_id")
    private Usr usrId;
}
