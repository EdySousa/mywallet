package com.ed2nd.mywallet.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.ed2nd.mywallet.domain.Account;
import com.ed2nd.mywallet.repositories.AccountRespository;
import com.ed2nd.mywallet.services.exception.DataIntegrityException;
import com.ed2nd.mywallet.services.exception.ObjectNotFoundException;

@Service
public class AccountService {

	@Autowired
	private AccountRespository repo;

	public List<Account> findAll() {
		return repo.findAll();
	}

	public Account find(Integer id) {
		Optional<Account> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Account.class.getName()));
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
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível eliminar Conta que possui Transição ou Budget");
		}
	}

}
