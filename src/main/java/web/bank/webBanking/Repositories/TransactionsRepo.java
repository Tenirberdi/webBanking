package web.bank.webBanking.Repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import web.bank.webBanking.DTO.BankerTransactionReportDTO;
import web.bank.webBanking.DTO.Report1DTO;
import web.bank.webBanking.DTO.SumDTO;
import web.bank.webBanking.DTO.TransactionReportDTO;
import web.bank.webBanking.Models.Transactions;

import java.util.List;

public interface TransactionsRepo extends CrudRepository<Transactions, Long> {

    @Modifying
    @Query(value="INSERT INTO `transactions`( `rate`, `client_id`, `banker_id`, `amount`, `t_date`, `getter_id`, `secret_code`) VALUES (?1,?2,?3,?4, CURDATE(),?5, '1') " , nativeQuery = true)
    void recordTransaction(String rate, long clientId, long bankerId, int amount, long getterId);

    @Modifying
    @Query(value="INSERT INTO `transactions`( `rate`, `client_id`, `amount`, `t_date`, `getter_id`, `secret_code`) VALUES (?1,?2,?3, CURDATE(),?4, '1') " , nativeQuery = true)
    void recordClientTransaction(String rate, long clientId, int amount, long getterId);

    @Query(value="SELECT t.id, t.amount, t.rate, t.t_date,c.id as clientId, c.full_name as client,g.id as getterId, g.full_name as getter FROM `transactions` as t JOIN `usr` as c on t.client_id = c.id JOIN `usr` as g on t.getter_id = g.id", nativeQuery = true)
    List<BankerTransactionReportDTO> getTransactionReport();

    @Query(value="SELECT MONTHNAME(t.t_date) as 'time', SUM(t.amount) as 'sum' FROM `transactions` as t WHERE t.`t_date` > date_sub(now(), interval 1 year) GROUP BY MONTH(t.t_date)", nativeQuery = true)
    List<SumDTO> getYearSumTransaction();

    @Query(value="SELECT QUARTER(t.t_date) as 'time', SUM(t.amount) as 'sum' FROM `transactions` as t WHERE t.`t_date` > date_sub(now(), interval 1 year) GROUP BY YEAR(t.t_date), QUARTER(t.t_date)", nativeQuery = true)
    List<SumDTO> getQuarterSumTransaction();

    @Query(value="SELECT 'All Time' as 'time', SUM(t.amount) as 'sum' FROM `transactions` as t", nativeQuery = true)
    List<SumDTO> getAllSumTransaction();

    @Query(value="SELECT t.id, t.amount, t.rate, t.t_date, c.full_name as client,  g.full_name as getter FROM `transactions` as t JOIN `usr` as c on t.client_id = c.id JOIN `usr` as g on t.getter_id = g.id where c.id = ?", nativeQuery = true)
    List<TransactionReportDTO> getTransactionHistory(long id);

    @Query(value="SELECT tSenderTable.client_id, u.full_name, tSenderTable.sent, tGetterTable.got, cSomTable.convertedToSom, cDollarTable.convertedToDollar, tSenderTable.year from \n" +
            "(SELECT t.client_id, sum(t.amount) as sent, year(t.t_date) as 'year' from `transactions` as t GROUP BY t.client_id, year(t.t_date)) as tSenderTable \n" +
            "JOIN (SELECT t.getter_id as client_id, sum(t.amount) as got, year(t.t_date) as 'year' from `transactions` as t GROUP BY t.getter_id, year(t.t_date)) as tGetterTable on tSenderTable.client_id = tGetterTable.client_id\n" +
            " \n" +
            "JOIN (SELECT c.client_id, sum(c.amount) as convertedToSom \n" +
            "      from `conversions` as c where c.to_currency = 'som' \n" +
            "      GROUP BY c.client_id, year(c.c_date)) as cSomTable on tSenderTable.client_id = cSomTable.client_id\n" +
            "      \n" +
            "JOIN (SELECT c.client_id, sum(c.amount) as convertedToDollar\n" +
            "      from `conversions` as c where c.to_currency = 'Dollar' \n" +
            "      GROUP BY c.client_id, year(c.c_date)) as cDollarTable on tSenderTable.client_id = cDollarTable.client_id\n" +
            "      \n" +
            "      JOIN `usr` as u on u.id = tSenderTable.client_id\n" +
            "      \n" +
            "      GROUP by tSenderTable.client_id, year(tSenderTable.year) limit ?,20", nativeQuery = true)
    List<Report1DTO> getReport1(int limit);
}
