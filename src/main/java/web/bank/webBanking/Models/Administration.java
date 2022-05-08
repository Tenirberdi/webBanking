package web.bank.webBanking.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="administration")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Administration {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private Date aDate;
    private String operation;


    @ManyToOne
    @JoinColumn(name = "usr_id")
    private Usr newUsrId;

    @ManyToOne
    @JoinColumn(name= "admin_id")
    private Usr adminId;
}
