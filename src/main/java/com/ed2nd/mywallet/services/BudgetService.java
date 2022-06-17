package com.ed2nd.mywallet.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.ed2nd.mywallet.domain.Account;
import com.ed2nd.mywallet.domain.Budget;
import com.ed2nd.mywallet.dto.BudgetNewDTO;
import com.ed2nd.mywallet.repositories.BudgetRepository;
import com.ed2nd.mywallet.services.exception.DataIntegrityException;
import com.ed2nd.mywallet.services.exception.ObjectNotFoundException;

@Service
public class BudgetService {

	@Autowired
	private BudgetRepository repo;
	
	@Autowired
	private AccountService accountService;

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
			throw new DataIntegrityException("Não é possível elimiar Budget que tem Despesas associadas");
		}
	}
	
	public List<Budget> findAllByUserIdFromDateBetween(Integer userID, Date startDate, Date endDate) {
		return repo.findAllByUserIdFromDateBetween(userID, startDate, endDate);
	}

	public List<Budget> findAllByUserId(Integer userID) {
		return repo.findAllByUserId(userID);
	}
	
	public Budget fromDTO(BudgetNewDTO objDto) {
		
		Date date = (objDto.getDate() != null) ? objDto.getDate() : new Date();
		Account account = accountService.find(objDto.getAccountId());
		
		return new Budget(null, objDto.getName(), objDto.getBudget(), date, account);
	}
	
	public Budget updateFromDTO(BudgetNewDTO objDto, Integer id) {
		
		Budget budget = find(id);
		Account account = accountService.find(objDto.getAccountId());
		
		budget.setName(objDto.getName());
		budget.setDate(objDto.getDate());
		budget.setBudget(objDto.getBudget());
		budget.setAccount(account);
		
		return budget;
	}

}
