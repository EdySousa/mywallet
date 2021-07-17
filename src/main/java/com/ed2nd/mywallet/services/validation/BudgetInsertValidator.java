package com.ed2nd.mywallet.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.ed2nd.mywallet.domain.Account;
import com.ed2nd.mywallet.dto.BudgetNewDTO;
import com.ed2nd.mywallet.repositories.AccountRespository;
import com.ed2nd.mywallet.resources.exception.FieldMessage;

public class BudgetInsertValidator implements ConstraintValidator<BudgetInsert, BudgetNewDTO> {

	@Autowired
	private AccountRespository accountRepository;

	@Override
	public void initialize(BudgetInsert ann) {
	}

	@Override
	public boolean isValid(BudgetNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		Optional<Account> account = accountRepository.findById(objDto.getAccountId());
		
		if(!account.isPresent()) {
			list.add(new FieldMessage("accountId", "Account not found! ID: " + objDto.getAccountId() + " doesn't exist"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}