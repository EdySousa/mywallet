package com.ed2nd.mywallet;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ed2nd.mywallet.domain.Account;
import com.ed2nd.mywallet.domain.Budget;
import com.ed2nd.mywallet.domain.Menu;
import com.ed2nd.mywallet.domain.Transaction;
import com.ed2nd.mywallet.domain.User;
import com.ed2nd.mywallet.domain.Wallet;
import com.ed2nd.mywallet.domain.enums.TransactionType;
import com.ed2nd.mywallet.repositories.AccountRespository;
import com.ed2nd.mywallet.repositories.BudgetRespository;
import com.ed2nd.mywallet.repositories.MenuRepository;
import com.ed2nd.mywallet.repositories.TransactionRespository;
import com.ed2nd.mywallet.repositories.UserRepository;
import com.ed2nd.mywallet.repositories.WalletRepository;

@SpringBootApplication
public class MywalletApplication implements CommandLineRunner {

	@Autowired
	private MenuRepository menuRepository;

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private WalletRepository walletRepository;
	@Autowired
	private AccountRespository accountRespository;
	@Autowired
	private BudgetRespository budgetRespository;
	@Autowired
	private TransactionRespository transactionRespository;

	public static void main(String[] args) {
		SpringApplication.run(MywalletApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Menu mc1 = new Menu(null, "Overview");
		Menu mc2 = new Menu(null, "Resume");
		Menu mc3 = new Menu(null, "Expenses");
		Menu mc4 = new Menu(null, "Bank accounts");
		Menu mc5 = new Menu(null, "Budgets");
		Menu mc6 = new Menu(null, "Transactions");

		menuRepository.saveAll(Arrays.asList(mc1, mc2, mc3, mc4, mc5, mc6));

		User user1 = new User(null, "Edvaldo", "Segundo", "Edvaldo.Segundo@hotmail.com", "123");
		User user2 = new User(null, "Edvaldinho", "Soares", "EdvaldinhoSoares@hotmail.com", "123");

		Wallet w1 = new Wallet(null, sdf.parse("09/05/2021 21:45"), "Carteira Principal", user1);
		Wallet w2 = new Wallet(null, sdf.parse("31/05/2021 14:33"), "Carteira Secundária", user2);

		Account ac1 = new Account(null, "Millennium", sdf.parse("09/05/2021 21:47"), w1);
		Account ac2 = new Account(null, "ActivoBank", sdf.parse("09/05/2021 21:48"), w2);

		Budget bg1 = new Budget(null, "Combustivel", 100.0, 0.0, sdf.parse("23/05/2021 21:47"), ac1);
		Budget bg2 = new Budget(null, "Apostas", 50.0, 0.0, sdf.parse("23/05/2021 22:00"), ac1);

		Transaction t0 = new Transaction(null, TransactionType.INCOME, "Salario", 1000.0, sdf.parse("01/05/2021 22:08"), ac1, null);
		Transaction t1 = new Transaction(null, TransactionType.EXPENSE, "Combustivel", 20.0, sdf.parse("23/04/2021 22:08"), ac1, bg1);
		Transaction t2 = new Transaction(null, TransactionType.EXPENSE, "Luz", 24.50, sdf.parse("23/05/2021 22:20"), ac2, null);
		Transaction t3 = new Transaction(null, TransactionType.EXPENSE, "Combustivel", 24.50, sdf.parse("02/06/2021 16:41"), ac1, bg1);

		// Add the User Wallets
		user1.getWallets().addAll(Arrays.asList(w1));
		user2.getWallets().addAll(Arrays.asList(w2));

		// Add the Wallets Accounts
		w1.getAccounts().addAll(Arrays.asList(ac1));
		w2.getAccounts().addAll(Arrays.asList(ac2));

		// Add the Account Budgets
		ac1.getBudgets().addAll(Arrays.asList(bg1, bg2));

		// Add the Account Transactions
		ac1.getTransactions().addAll(Arrays.asList(t0, t1));
		ac2.getTransactions().addAll(Arrays.asList(t2));

		// Add the Budget Transactions
		bg1.getTransactions().addAll(Arrays.asList(t1, t3));
		bg1.addSpent(t1.getValue());
		bg1.addSpent(t2.getValue());

		userRepository.saveAll(Arrays.asList(user1, user2));
		walletRepository.saveAll(Arrays.asList(w1, w2));
		accountRespository.saveAll(Arrays.asList(ac1, ac2));
		budgetRespository.saveAll(Arrays.asList(bg1, bg2));
		transactionRespository.saveAll(Arrays.asList(t0, t1, t2, t3));

	}

}
