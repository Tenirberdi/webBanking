package web.bank.webBanking.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Transaction;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="usr")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usr {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    private String login;
    private String pass;
    private String fullName;
    private boolean enabled;
    private String address;
    private String phone;

    @ManyToOne
    @JoinColumn(name= "role_id")
    private Role roleId;

    @JsonIgnore
    @OneToMany(mappedBy = "usrId")
    private List<Account> accounts;

    @JsonIgnore
    @OneToMany(mappedBy = "getterId")
    private List<Transactions> getterTransactions;

    @JsonIgnore
    @OneToMany(mappedBy = "clientId")
    private List<Transactions> clientTransactions;

    @JsonIgnore
    @OneToMany(mappedBy = "bankerId")
    private List<Transactions> bankerTransactions;

    @JsonIgnore
    @OneToMany(mappedBy = "bankerId")
    private List<Credits> bankerCredits;

    @JsonIgnore
    @OneToMany(mappedBy = "clientId")
    private List<Credits> clientCredits;

    @JsonIgnore
    @OneToMany(mappedBy = "newUsrId")
    private List<Administration> administrations;

    @JsonIgnore
    @OneToMany(mappedBy = "adminId")
    private List<Administration> administrations2;

    @JsonIgnore
    @OneToMany(mappedBy = "clientId")
    private List<Conversions> conversions;

    @JsonIgnore
    @OneToMany(mappedBy = "bankerId")
    private List<Conversions> conversions2;
}
