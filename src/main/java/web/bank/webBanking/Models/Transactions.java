package web.bank.webBanking.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transactions {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private String rate;
    private int amount;
    private Date tDate;
    private String secretCode;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Usr clientId;


    @ManyToOne
    @JoinColumn(name = "getter_id")
    private Usr getterId;

    @ManyToOne
    @JoinColumn(name="banker_id")
    private Usr bankerId;
}
