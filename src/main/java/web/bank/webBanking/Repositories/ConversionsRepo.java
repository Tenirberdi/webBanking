package web.bank.webBanking.Repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import web.bank.webBanking.Models.Conversions;

public interface ConversionsRepo extends CrudRepository<Conversions, Long> {

    @Modifying
    @Query(value="INSERT INTO `conversions`( `client_id`, `amount`, `from_currency`, `to_currency`, `rate`, `c_date`)SELECT ?1, ?2, ?3, ?4, r.buy_dollar , CURDATE() from `rate` as r" , nativeQuery = true)
    void recordBuyConversion(long clientId, int amount, String fCurrency, String tCurrency);

    @Modifying
    @Query(value="INSERT INTO `conversions`( `client_id`, `amount`, `from_currency`, `to_currency`, `rate`, `c_date`)SELECT ?1, ?2, ?3, ?4, r.sell_dollar , CURDATE() from `rate` as r" , nativeQuery = true)
    void recordSellConversion(long clientId, int amount, String fCurrency, String tCurrency);
}
