package com.sergeykuzmin.transaktion_app.repository;

import com.sergeykuzmin.transaktion_app.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String > {

    @Query(value = "SELECT COUNT(a) FROM Account a")
    Integer countAccounts();

    @Query(value = "SELECT SUM (a.amount) FROM Account a")
    Integer sumAmount();
}
