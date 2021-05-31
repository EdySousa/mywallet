package com.ed2nd.mywallet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ed2nd.mywallet.domain.Transaction;

@Repository
public interface TransactionRespository extends JpaRepository<Transaction, Integer> {

}
