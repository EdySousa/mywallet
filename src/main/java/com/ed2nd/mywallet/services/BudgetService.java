package com.ed2nd.mywallet.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.ed2nd.mywallet.domain.Budget;
import com.ed2nd.mywallet.repositories.BudgetRespository;
import com.ed2nd.mywallet.services.exception.DataIntegrityException;
import com.ed2nd.mywallet.services.exception.ObjectNotFoundException;

@Service
public class BudgetService {

	@Autowired
	private BudgetRespository repo;

	public List<Budget> findAll() {
		return repo.findAll();
	}

	public Budget find(Integer id) {
		Optional<Budget> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Budget.class.getName()));
	}

	public Budget insert(Budget obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	public Budget update(Budget obj) {
		find(obj.getId());
		return repo.save(obj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível elimiar Budget que tem Transição associada");
		}
	}

}
