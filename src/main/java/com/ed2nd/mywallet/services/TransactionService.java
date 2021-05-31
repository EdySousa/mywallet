package com.ed2nd.mywallet.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ed2nd.mywallet.domain.Transaction;
import com.ed2nd.mywallet.repositories.TransactionRespository;
import com.ed2nd.mywallet.services.exception.ObjectNotFoundException;

@Service
public class TransactionService {

	@Autowired
	private TransactionRespository repo;

	public List<Transaction> findAll() {
		return repo.findAll();
	}

	public Transaction find(Integer id) {
		Optional<Transaction> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Transaction.class.getName()));
	}

	public Transaction insert(Transaction obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	public Transaction update(Transaction obj) {
		find(obj.getId());
		return repo.save(obj);
	}

	public void delete(Integer id) {
		find(id);
		repo.deleteById(id);
	}

}
