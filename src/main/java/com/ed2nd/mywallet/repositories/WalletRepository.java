package com.ed2nd.mywallet.repositories;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ed2nd.mywallet.domain.User;
import com.ed2nd.mywallet.domain.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Integer> {
	
	@Transactional(readOnly = true)
	@Query("SELECT obj FROM Wallet obj WHERE obj.date >= :startDate AND obj.date <= :endDate AND obj.user = :user")
	Wallet findByUserDateBetween(@Param("user") User user, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Transactional(readOnly = true)
	Wallet findByUser(User user);


}
