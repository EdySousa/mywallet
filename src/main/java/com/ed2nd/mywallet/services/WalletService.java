package com.ed2nd.mywallet.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.ed2nd.mywallet.domain.Wallet;
import com.ed2nd.mywallet.repositories.WalletRepository;
import com.ed2nd.mywallet.services.exception.DataIntegrityException;
import com.ed2nd.mywallet.services.exception.ObjectNotFoundException;

@Service
public class WalletService {

	@Autowired
	private WalletRepository repo;

	public List<Wallet> findAll() {
		return repo.findAll();
	}

	public Wallet find(Integer id) {
		Optional<Wallet> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Wallet.class.getName()));
	}

	public Wallet insert(Wallet obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	public Wallet update(Wallet obj) {
		find(obj.getId());
		return repo.save(obj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível eliminar Carteira que possui Conta Bancária");
		}
	}

}
