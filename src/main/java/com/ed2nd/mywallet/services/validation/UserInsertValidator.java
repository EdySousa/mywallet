package com.ed2nd.mywallet.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.ed2nd.mywallet.domain.User;
import com.ed2nd.mywallet.dto.UserNewDTO;
import com.ed2nd.mywallet.repositories.UserRepository;
import com.ed2nd.mywallet.resources.exception.FieldMessage;


public class UserInsertValidator implements ConstraintValidator<UserInsert, UserNewDTO> {

	
	@Autowired
	private UserRepository repo;
	
	@Override
	public void initialize(UserInsert ann) {}

	@Override
	public boolean isValid(UserNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		
		User aux = repo.findByEmail(objDto.getEmail());
		if(aux != null) {
			list.add(new FieldMessage("email", "Email already exists"));
		}

		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}