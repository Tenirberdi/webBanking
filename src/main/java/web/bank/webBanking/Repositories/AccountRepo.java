package web.bank.webBanking.Repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import web.bank.webBanking.Models.Account;

import java.util.List;

@Transactional
public interface AccountRepo extends CrudRepository<Account, Long> {

    @Modifying
    @Query(value="UPDATE `account` SET `som_cash`= `som_cash` - ?1 WHERE `usr_id` = ?2 and `som_cash` >= ?1", nativeQuery = true)
    void makeSomTransaction1(int amount , long clientId);

    @Modifying
    @Query(value= "UPDATE `account` SET `som_cash`= `som_cash` + ?1 WHERE `usr_id` = ?2", nativeQuery = true)
    void makeSomTransaction2(int amount , long getterId);

    @Modifying
    @Query(value="UPDATE `account` SET `dollar_cash`= `dollar_cash` - ?1 WHERE `usr_id` = ?2 and `dollar_cash` >= ?1", nativeQuery = true)
    void makeDollarTransaction1(int amount , long clientId);

    @Modifying
    @Query(value= "UPDATE `account` SET `dollar_cash`= `dollar_cash` + ?1 WHERE `usr_id` = ?2", nativeQuery = true)
    void makeDollarTransaction2(int amount , long getterId);

    @Query(value="select * from account where usr_id = ?1 and som_cash >= ?2", nativeQuery = true)
    List<Account> getAccountBySomCash(long id, int amount);

    @Query(value="select * from account where usr_id = ?1 and dollar_cash >= ?2", nativeQuery = true)
    List<Account> getAccountByDollarCash(long id, int amount);

    @Modifying
    @Query(value="UPDATE `account` as a join `rate` as r SET a.`som_cash`= a.som_cash-?1*r.buy_dollar ,a.`dollar_cash`= a.dollar_cash+?1 WHERE a.usr_id = ?2", nativeQuery = true)
    void buyDollar(int amount, long clientId);

    @Modifying
    @Query(value="UPDATE `account` as a join `rate` as r SET a.`dollar_cash`= a.dollar_cash-?1 / r.sell_dollar, a.`som_cash`= a.som_cash+?1 WHERE a.usr_id = ?2", nativeQuery = true)
    void sellDollar(int amount, long clientId);

    @Query(value="SELECT * FROM `account` WHERE `usr_id` = ?2 and `som_cash` >= ?1", nativeQuery = true)
    List<Account> checkSomCash(int amount, long clientId);

    @Query(value="SELECT * FROM `account` WHERE `usr_id` = ?2 and `dollar_cash` >= ?1", nativeQuery = true)
    List<Account> checkDollarCash(int amount, long clientId);

    @Modifying
    @Query(value="DELETE FROM `account` WHERE `usr_id` = ?", nativeQuery = true)
    void deleteByUsrId(long id);


}
