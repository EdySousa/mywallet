package com.ed2nd.mywallet.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ed2nd.mywallet.domain.User;
import com.ed2nd.mywallet.repositories.UserRepository;
import com.ed2nd.mywallet.services.exception.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder pe;

	private Random rand = new Random();

	public void sendNewPassword(String email) {
		User user = userRepository.findByEmail(email.toLowerCase());

		if (user == null) {
			throw new ObjectNotFoundException("Email not found");
		}

		String newPass = newPassword();
		user.setPassword(pe.encode(newPass));

		userRepository.save(user);

	}

	private String newPassword() {
		char[] vet = new char[10];

		for (int i = 0; i < 10; i++) {
			vet[i] = randomChar();
		}

		return new String(vet);
	}

	private char randomChar() {

		int opt = rand.nextInt(3);

		if (opt == 0) { // generate a digit
			return (char) (rand.nextInt(10) + 48);
		} else if (opt == 1) { // generate an upper case
			return (char) (rand.nextInt(26) + 65);
		} else { // generate an lower case
			return (char) (rand.nextInt(26) + 97);
		}

	}

}
