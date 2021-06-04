package com.ed2nd.mywallet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ed2nd.mywallet.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	@Transactional(readOnly = true)
	User findByEmail(String email);

}
