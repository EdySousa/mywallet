package com.ed2nd.mywallet.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ed2nd.mywallet.domain.Account;

@Repository
public interface AccountRespository extends JpaRepository<Account, Integer> {
	
	@Transactional(readOnly = true)
	@Query("SELECT obj FROM Account obj WHERE obj.date >= :startDate AND obj.date <= :endDate AND obj.wallet.user.id = :userID")
	List<Account> findAllByUserIdFromDateBetween(@Param("userID") Integer userID, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Transactional(readOnly = true)
	@Query("SELECT obj FROM Account obj WHERE obj.wallet.user.id = :userID")
	List<Account> findAllByUserId(Integer userID);


}
