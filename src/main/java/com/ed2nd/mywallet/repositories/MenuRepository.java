package com.ed2nd.mywallet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ed2nd.mywallet.domain.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {

}
