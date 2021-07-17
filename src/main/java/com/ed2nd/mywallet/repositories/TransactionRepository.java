package com.ed2nd.mywallet.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ed2nd.mywallet.domain.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

	@Transactional(readOnly = true)
	@Query("SELECT obj FROM Transaction obj WHERE obj.date >= :startDate AND obj.date <= :endDate AND obj.account.wallet.user.id = :userID")
	List<Transaction> findAllByUserIdFromDateBetween(@Param("userID") Integer userID, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Transactional(readOnly = true)
	@Query("SELECT obj FROM Transaction obj WHERE obj.account.wallet.user.id = :userID")
	Page<Transaction> findAllByUserId(@Param("userID") Integer userID, Pageable pageRequest);

}
