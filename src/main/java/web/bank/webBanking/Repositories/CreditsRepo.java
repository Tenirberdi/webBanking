package web.bank.webBanking.Repositories;

import org.springframework.data.repository.CrudRepository;
import web.bank.webBanking.Models.Credits;

public interface CreditsRepo extends CrudRepository<Credits, Long> {
}
