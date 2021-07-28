package com.ed2nd.mywallet.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ed2nd.mywallet.domain.Account;
import com.ed2nd.mywallet.domain.Budget;
import com.ed2nd.mywallet.domain.Menu;
import com.ed2nd.mywallet.domain.Transaction;
import com.ed2nd.mywallet.domain.User;
import com.ed2nd.mywallet.domain.Wallet;
import com.ed2nd.mywallet.domain.enums.Perfil;
import com.ed2nd.mywallet.domain.enums.TransactionType;
import com.ed2nd.mywallet.repositories.AccountRespository;
import com.ed2nd.mywallet.repositories.BudgetRepository;
import com.ed2nd.mywallet.repositories.MenuRepository;
import com.ed2nd.mywallet.repositories.TransactionRepository;
import com.ed2nd.mywallet.repositories.UserRepository;
import com.ed2nd.mywallet.repositories.WalletRepository;

@Service
public class DBService {
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private MenuRepository menuRepository;

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private WalletRepository walletRepository;
	@Autowired
	private AccountRespository accountRespository;
	@Autowired
	private BudgetRepository budgetRespository;
	@Autowired
	private TransactionRepository transactionRespository;
	
	public void instantiateTestDatabase() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Menu mc1 = new Menu(null, "Overview");
		Menu mc2 = new Menu(null, "Resume");
		Menu mc3 = new Menu(null, "Expenses");
		Menu mc4 = new Menu(null, "Bank accounts");
		Menu mc5 = new Menu(null, "Budgets");
		Menu mc6 = new Menu(null, "Transactions");

		menuRepository.saveAll(Arrays.asList(mc1, mc2, mc3, mc4, mc5, mc6));

		User user1 = new User(null, "Edvaldo", "Segundo", "Edvaldo.Segundo@hotmail.com", pe.encode("123"));
		user1.addPerfil(Perfil.ADMIN);
		User user2 = new User(null, "Edvaldinho", "Soares", "EdvaldinhoSoares@hotmail.com",  pe.encode("123"));

		Wallet w1 = new Wallet(null, sdf.parse("09/05/2021 21:45"), "Carteira Principal", user1);
		Wallet w2 = new Wallet(null, sdf.parse("31/05/2021 14:33"), "Carteira Secundária", user2);
		Wallet w3 = new Wallet(null, sdf.parse("05/06/2021 21:45"), "Carteira Principal", user1);

		Account ac1 = new Account(null, "Millennium", sdf.parse("09/05/2021 21:47"), w1);
		Account ac2 = new Account(null, "ActivoBank", sdf.parse("09/05/2021 21:48"), w2);
		Account ac3 = new Account(null, "Poupança", sdf.parse("09/05/2021 21:47"), w1);

		Budget bg1 = new Budget(null, "Combustivel", 100.0, sdf.parse("23/05/2021 21:47"), ac1);
		Budget bg2 = new Budget(null, "Apostas", 50.0, sdf.parse("23/05/2021 22:00"), ac1);

		Transaction t0 = new Transaction(null, TransactionType.INCOME, "Salario", 1000.0, sdf.parse("01/05/2021 22:08"), ac1, null);
		Transaction t1 = new Transaction(null, TransactionType.EXPENSE, "Combustivel", 20.0, sdf.parse("23/04/2021 22:08"), ac1, bg1);
		Transaction t2 = new Transaction(null, TransactionType.EXPENSE, "Luz", 24.50, sdf.parse("23/05/2021 22:20"), ac2, null);
		Transaction t3 = new Transaction(null, TransactionType.EXPENSE, "Combustivel", 24.50, sdf.parse("02/06/2021 16:41"), ac1, bg1);
		Transaction t4 = new Transaction(null, TransactionType.INCOME, "Conta Corrente", 24.50, sdf.parse("23/05/2021 22:20"), ac3, null);

		// Add the User Wallets
		user1.getWallets().addAll(Arrays.asList(w1, w3));
		user2.getWallets().addAll(Arrays.asList(w2));

		// Add the Wallets Accounts
		w1.getAccounts().addAll(Arrays.asList(ac1, ac3));
		w2.getAccounts().addAll(Arrays.asList(ac2));

		// Add the Account Budgets
		ac1.getBudgets().addAll(Arrays.asList(bg1, bg2));

		// Add the Account Transactions
		ac1.getTransactions().addAll(Arrays.asList(t0, t1, t3));
		ac2.getTransactions().addAll(Arrays.asList(t2));
		ac3.getTransactions().addAll(Arrays.asList(t4));
		
		// Add the Budget Transactions
		bg1.getTransactions().addAll(Arrays.asList(t1, t3));

		userRepository.saveAll(Arrays.asList(user1, user2));
		walletRepository.saveAll(Arrays.asList(w1, w2, w3));
		accountRespository.saveAll(Arrays.asList(ac1, ac2, ac3));
		budgetRespository.saveAll(Arrays.asList(bg1, bg2));
		transactionRespository.saveAll(Arrays.asList(t0, t1, t2, t3, t4));
	}

}
