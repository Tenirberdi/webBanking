package web.bank.webBanking.Repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import web.bank.webBanking.Models.Rate;

public interface RateRepo extends CrudRepository<Rate, String> {

    @Modifying
    @Query(value="UPDATE `rate` SET `sell_dollar`= ?", nativeQuery = true)
    void setSellDollar(int sum);

    @Modifying
    @Query(value="UPDATE `rate` SET `buy_dollar`= ?", nativeQuery = true)
    void setBuyDollar(int sum);
}
