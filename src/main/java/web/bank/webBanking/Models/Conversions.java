package web.bank.webBanking.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="conversions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Conversions {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private int amount;
    private String fromCurrency;
    private String toCurrency;
    private int rate;
    private Date cDate;


    @ManyToOne
    @JoinColumn(name="client_id")
    private Usr clientId;

    @ManyToOne
    @JoinColumn(name="banker_id")
    private Usr bankerId;
}
