package web.bank.webBanking.Repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import web.bank.webBanking.DTO.ClientDTO;
import web.bank.webBanking.DTO.UsrDTO;
import web.bank.webBanking.Models.Usr;

import java.util.List;

public interface UsrRepo extends CrudRepository<Usr, Long> {

    @Query(value = "SELECT u.id, u.full_name, u.address, u.phone, acc.som_cash, acc.dollar_cash, acc.credit from usr as u JOIN account as acc on  u.id = acc.usr_id where u.enabled = 1", nativeQuery = true)
    List<ClientDTO> getClients();

    @Query(value = "SELECT u.id, u.full_name, u.address, u.phone, acc.som_cash, acc.dollar_cash, acc.credit from usr as u JOIN account as acc on  u.id = acc.usr_id where u.id = ? and u.enabled = 1", nativeQuery = true)
    ClientDTO getClient(long id);

    @Query(value = "SELECT u.id, u.full_name, u.address, u.phone, acc.som_cash, acc.dollar_cash, acc.credit from usr as u JOIN account as acc on  u.id = acc.usr_id where u.full_name = ? ", nativeQuery = true)
    ClientDTO getClientByName(String name);

    @Query(value = "SELECT u.id, u.full_name, u.address, u.phone, acc.som_cash, acc.dollar_cash, acc.credit from usr as u JOIN account as acc on  u.id = acc.usr_id where u.enabled = 1 ORDER BY acc.credit desc limit 10", nativeQuery = true)
    List<ClientDTO> getMaxCredit();

    @Query(value = "SELECT u.id, u.full_name, u.address, u.phone, acc.som_cash, acc.dollar_cash, acc.credit from usr as u JOIN account as acc on  u.id = acc.usr_id where u.enabled = 1 ORDER BY acc.credit limit 10", nativeQuery = true)
    List<ClientDTO> getMinCredit();



    @Query(value = "select * from usr where login = ?1", nativeQuery = true)
    Usr getUsrIdByLogin(String login);

    @Modifying
    @Query(value="INSERT INTO `usr`( `login`, `pass`, `full_name`, `enabled`, `role_id`, `address`, `phone`) VALUES (?,?,?,1,3,?,?)", nativeQuery = true)
    void addClient(String login, String pass, String name, String address, String phone);

    @Modifying
    @Query(value="INSERT INTO `account`(`usr_id`, `som_cash`, `dollar_cash`, `credit`) VALUES (?,0,0,0)", nativeQuery = true)
    void addAccount(long usrId);

    @Modifying
    @Query(value="INSERT INTO `usr`( `login`, `pass`, `full_name`, `enabled`, `role_id`, `address`, `phone`) VALUES (?,?,?,1,1,?,?)", nativeQuery = true)
    void addAdmin(String login, String pass, String name, String address, String phone);

    @Modifying
    @Query(value="INSERT INTO `usr`( `login`, `pass`, `full_name`, `enabled`, `role_id`, `address`, `phone`) VALUES (?,?,?,1,2,?,?)", nativeQuery = true)
    void addBanker(String login, String pass, String name, String address, String phone);

    @Modifying
    @Query(value="UPDATE `usr` SET `enabled` = 0 WHERE `id` = ?", nativeQuery = true)
    void banUsr(long id);

    @Query(value="SELECT * FROM `usr` WHERE `full_name` = ? and role_id = 3", nativeQuery = true)
    Usr getClientByFullName(String fullName);

    @Query(value="select * from usr where id = ? and role_id = 3", nativeQuery = true)
    Usr getById(long id);

    Usr findByLogin(String login);

    @Query(value="SELECT u.id, u.full_name, u.login, u.address, u.phone, r.name FROM `usr` as u JOIN `role` as r on r.id = u.role_id where u.enabled = 1 limit ?, 10", nativeQuery = true)
    List<UsrDTO> getUsers(int limit);

}
