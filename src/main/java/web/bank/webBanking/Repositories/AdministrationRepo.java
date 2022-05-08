package web.bank.webBanking.Repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import web.bank.webBanking.DTO.AdminReportDTO;
import web.bank.webBanking.Models.Administration;

import java.util.List;

public interface AdministrationRepo extends CrudRepository<Administration, Long> {
    @Modifying
    @Query(value="INSERT INTO `administration`( `usr_id`, `a_date`, `admin_id`, `operation`) VALUES (?, CURDATE(),?, 'adding')", nativeQuery = true)
    void recordAdding(long usrId, long adminId);

    @Modifying
    @Query(value="INSERT INTO `administration`( `usr_id`, `a_date`, `admin_id`, `operation`) VALUES (?, CURDATE(),?, 'baning')", nativeQuery = true)
    void recordBaning(long usrId, long adminId);

    @Query(value="SELECT a.id, a.usr_id as usrId, u.full_name as usrName, r.name as roleName , a.a_date as 'date', a.admin_id as adminId, ad.full_name as adminName, a.operation FROM `administration` as a JOIN `usr` as u on a.usr_id = u.id JOIN `usr` as ad on a.admin_id = ad.id JOIN role as r on r.id = u.role_id" , nativeQuery = true)
    List<AdminReportDTO> getReport();
}
