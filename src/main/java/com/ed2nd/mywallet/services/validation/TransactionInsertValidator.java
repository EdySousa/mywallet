package com.ed2nd.mywallet.services.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.ed2nd.mywallet.domain.Account;
import com.ed2nd.mywallet.domain.enums.TransactionType;
import com.ed2nd.mywallet.dto.TransactionNewDTO;
import com.ed2nd.mywallet.repositories.AccountRespository;
import com.ed2nd.mywallet.resources.exception.FieldMessage;

public class TransactionInsertValidator implements ConstraintValidator<TransactionInsert, TransactionNewDTO> {

	@Autowired
	private AccountRespository accountRepository;

	@Override
	public void initialize(TransactionInsert ann) {
	}

	@Override
	public boolean isValid(TransactionNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		if (objDto.getType() == null || !(Arrays.asList(TransactionType.EXPENSE, TransactionType.INCOME).contains(objDto.getType()))) {
			list.add(new FieldMessage("transactionType", "The Transaction Type not valid"));
		}

		Optional<Account> account = accountRepository.findById(objDto.getAccountId());
		if (!account.isPresent()) {
			list.add(new FieldMessage("accountId",
					"Account not found! ID: " + objDto.getAccountId() + " doesn't exist"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}