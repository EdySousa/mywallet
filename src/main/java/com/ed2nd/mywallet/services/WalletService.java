package com.ed2nd.mywallet.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ed2nd.mywallet.domain.User;
import com.ed2nd.mywallet.domain.Wallet;
import com.ed2nd.mywallet.dto.WalletNewDTO;
import com.ed2nd.mywallet.repositories.WalletRepository;
import com.ed2nd.mywallet.security.UserSS;
import com.ed2nd.mywallet.services.exception.AuthorizationException;
import com.ed2nd.mywallet.services.exception.ObjectNotFoundException;

@Service
public class WalletService {

	@Autowired
	private WalletRepository repo;

	@Autowired
	private UserService userService;

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
		Wallet newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		repo.deleteById(id);
	}

	public List<Wallet> findAllByUserIdFromDateBetween(Integer userID, Date startDate, Date endDate) {
		UserSS userSS = UserService.authenticated();
		
		if(userSS == null) {
			throw new AuthorizationException("Acess dinied");
		}
		
		User user = userService.find(userID);
		
		return repo.findByUserDateBetween(user, startDate, endDate);
	}

	public List<Wallet> findAllByUserId(Integer userID) {
		UserSS userSS = UserService.authenticated();
		
		if(userSS == null) {
			throw new AuthorizationException("Acess dinied");
		}
		
		User user = userService.find(userID);
		
		return repo.findByUser(user);
	}

	public Wallet fromDTO(WalletNewDTO objDto) {

		objDto.setDate((objDto.getDate() != null) ? objDto.getDate() : new Date());
		User user = userService.find(objDto.getUserId());

		return new Wallet(null, objDto.getDate(), objDto.getDescription(), user);
	}

	private void updateData(Wallet newObj, Wallet obj) {
		newObj.setDate(obj.getDate() != null ? obj.getDate() : newObj.getDate());
		newObj.setDescription(obj.getDescription());
	}

}
