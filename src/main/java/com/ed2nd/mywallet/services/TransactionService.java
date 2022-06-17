package com.ed2nd.mywallet.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ed2nd.mywallet.domain.Account;
import com.ed2nd.mywallet.domain.Budget;
import com.ed2nd.mywallet.domain.Transaction;
import com.ed2nd.mywallet.domain.enums.TransactionType;
import com.ed2nd.mywallet.dto.TransactionNewDTO;
import com.ed2nd.mywallet.repositories.TransactionRepository;
import com.ed2nd.mywallet.services.exception.ObjectNotFoundException;

/**
 * @author Edvaldo
 *
 */
@Service
public class TransactionService {

	@Autowired
	private TransactionRepository repo;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private BudgetService budgetService;

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

	public Page<Transaction> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	public List<Transaction> findAllByUserIdFromDateBetween(Integer userID, Date startDate, Date endDate) {
		return repo.findAllByUserIdFromDateBetween(userID, startDate, endDate);
	}

	public Page<Transaction> findAllByUserId(Integer userID, Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAllByUserId(userID, pageRequest);
	}

	public Transaction fromDTO(TransactionNewDTO objDto) {
		
		Date date = (objDto.getDate() != null) ? objDto.getDate() : new Date();
		Account account = accountService.find(objDto.getAccountId());
		Budget budget  = (objDto.getBudgetId() != null && objDto.getType().equals(TransactionType.EXPENSE)) ? budgetService.find(objDto.getBudgetId()) : null;
		
		Transaction transaction =  new Transaction(null, objDto.getType(), objDto.getName(), objDto.getValue(), date, account, budget);
		
		account.getTransactions().add(transaction);
		
		if(budget != null) {
			budget.getTransactions().add(transaction);
		}
		
		
		return transaction;
	}
	
	public Transaction updateFromDTO(TransactionNewDTO objDto, Integer id) {
		
		Date date = (objDto.getDate() != null) ? objDto.getDate() : new Date();
		
		Transaction tran = find(id);
		Budget bgt = budgetService.find(objDto.getBudgetId());
		Account acc = accountService.find(objDto.getAccountId());
		
		tran.setName(objDto.getName());
		tran.setDate(date);
		tran.setType(objDto.getType());
		tran.setValue(objDto.getValue());
		tran.setBudget(bgt);
		tran.setAccount(acc);
				
		
		return tran;
	}
}
