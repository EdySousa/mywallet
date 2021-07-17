package com.ed2nd.mywallet.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.ed2nd.mywallet.domain.Wallet;
import com.ed2nd.mywallet.dto.AccountNewDTO;
import com.ed2nd.mywallet.repositories.WalletRepository;
import com.ed2nd.mywallet.resources.exception.FieldMessage;

public class AccountInsertValidator implements ConstraintValidator<AccountInsert, AccountNewDTO> {

	@Autowired
	private WalletRepository walletRepository;

	@Override
	public void initialize(AccountInsert ann) {
	}

	@Override
	public boolean isValid(AccountNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		Optional<Wallet> wallet = walletRepository.findById(objDto.getWalletId());
		
		if(!wallet.isPresent()) {
			list.add(new FieldMessage("walletId", "Wallet not found! ID: " + objDto.getWalletId() + " doesn't exist"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}