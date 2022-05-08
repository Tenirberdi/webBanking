package web.bank.webBanking.Repositories;

import org.springframework.data.repository.CrudRepository;
import web.bank.webBanking.Models.Role;

public interface RoleRepo extends CrudRepository<Role, Long> {
}
