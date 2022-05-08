package web.bank.webBanking.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="credits")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Credits {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private int rate;
    private int amount;
    private Date tDate;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Usr clientId;

    @ManyToOne
    @JoinColumn(name = "banker_id")
    private Usr bankerId;

}
