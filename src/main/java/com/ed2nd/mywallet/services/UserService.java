package com.ed2nd.mywallet.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ed2nd.mywallet.domain.User;
import com.ed2nd.mywallet.domain.Wallet;
import com.ed2nd.mywallet.domain.enums.Perfil;
import com.ed2nd.mywallet.dto.UserDTO;
import com.ed2nd.mywallet.dto.UserNewDTO;
import com.ed2nd.mywallet.repositories.UserRepository;
import com.ed2nd.mywallet.repositories.WalletRepository;
import com.ed2nd.mywallet.security.UserSS;
import com.ed2nd.mywallet.services.exception.AuthorizationException;
import com.ed2nd.mywallet.services.exception.ObjectNotFoundException;

@Service
public class UserService {

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private UserRepository repo;

	@Autowired
	private WalletRepository walletRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private WalletService walletService;

	public static UserSS authenticated() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}

	public List<User> findAll() {
		return repo.findAll();
	}

	public User findByEmail(String email) {

		UserSS user = authenticated();

		if (user == null || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())) {
			throw new AuthorizationException("Access denied!");
		}

		User obj = repo.findByEmailIgnoreCase(email);

		if (obj == null) {
			throw new ObjectNotFoundException(
					"Object not found id: " + user.getId() + ", Type: " + User.class.getName());
		}

		return obj;
	}

	public User find(Integer id) {

		UserSS user = UserService.authenticated();

		if (user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Access denied");
		}

		Optional<User> obj = repo.findById(id);
		return obj.orElseThrow(
				() -> new ObjectNotFoundException("Object not found! Id: " + id + ", Type: " + User.class.getName()));
	}

	@Transactional
	public User insert(User obj) {
		obj.setId(null);
		obj.setEmail(obj.getEmail());
		obj = repo.save(obj);
		walletRepository.save(obj.getWallet());

		emailService.sendUserConfirmationHtmlEmail(obj);

		return obj;
	}

	public User update(User obj) {
		User newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		repo.deleteById(id);
	}

	public User fromDTO(UserDTO obj) {
		return new User(obj.getId(), obj.getFirstName(), obj.getLastName(), obj.getEmail(), null);
	}

	public User fromDTO(UserNewDTO obj) {
		Date date = (obj.getDate() != null) ? obj.getDate() : new Date();

		User user = new User(null, obj.getFirstName(), obj.getLastName(), obj.getEmail(), pe.encode(obj.getPassword()));
		Wallet wallet = new Wallet(null, date, obj.getDescription(), user);
		user.setWallet(wallet);
		return user;
	}

	private void updateData(User newObj, User obj) {
		newObj.setFirstName(obj.getFirstName());
		newObj.setLastName(obj.getLastName());
		newObj.setEmail(obj.getEmail() != null ? obj.getEmail() : newObj.getEmail());
	}

	public Wallet findOverviewByUserFromDateBetween(Integer userID, Date startDate, Date endDate) {

		User user = find(userID);
		Wallet wallet = walletService.findAllByUserIdFromDateBetween(userID, startDate, endDate);
		user.setWallet(wallet);

		return wallet;
	}

}
