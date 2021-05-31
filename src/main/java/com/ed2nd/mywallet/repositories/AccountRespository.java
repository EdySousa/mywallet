package com.ed2nd.mywallet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ed2nd.mywallet.domain.Account;

@Repository
public interface AccountRespository extends JpaRepository<Account, Integer> {

}
