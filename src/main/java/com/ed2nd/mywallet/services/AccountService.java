package com.ed2nd.mywallet.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ed2nd.mywallet.domain.Account;
import com.ed2nd.mywallet.domain.Wallet;
import com.ed2nd.mywallet.dto.AccountNewDTO;
import com.ed2nd.mywallet.repositories.AccountRespository;
import com.ed2nd.mywallet.services.exception.ObjectNotFoundException;

@Service
public class AccountService {

	@Autowired
	private AccountRespository repo;

	@Autowired
	private WalletService walletService;

	public List<Account> findAll() {
		return repo.findAll();
	}

	public Account find(Integer id) {
		Optional<Account> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Object not found! Id: " + id + ", Type: " + Account.class.getName()));
	}

	public Account insert(Account obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	public Account update(Account obj) {
		find(obj.getId());
		return repo.save(obj);
	}

	public void delete(Integer id) {
		find(id);
		repo.deleteById(id);
	}

	public List<Account> findAllByUserIdFromDateBetween(Integer userID, Date startDate, Date endDate) {
		return repo.findAllByUserIdFromDateBetween(userID, startDate, endDate);
	}

	public List<Account> findAllByUserId(Integer userID) {
		return repo.findAllByUserId(userID);
	}

	public Account fromDTO(AccountNewDTO objDto) {

		objDto.setDate((objDto.getDate() != null) ? objDto.getDate() : new Date());
		Wallet wallet = walletService.find(objDto.getWalletId());

		return new Account(null, objDto.getName(), objDto.getDate(), wallet);
	}
}
