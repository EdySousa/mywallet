package com.ed2nd.mywallet.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ed2nd.mywallet.domain.Budget;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Integer> {
	
	@Transactional(readOnly = true)
	@Query("SELECT obj FROM Budget obj WHERE obj.date >= :startDate AND obj.date <= :endDate AND obj.account.wallet.user.id = :userID")
	List<Budget> findAllByUserIdFromDateBetween(@Param("userID") Integer userID, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Transactional(readOnly = true)
	@Query("SELECT obj FROM Budget obj WHERE obj.account.wallet.user.id = :userID")
	List<Budget> findAllByUserId(Integer userID);

}
