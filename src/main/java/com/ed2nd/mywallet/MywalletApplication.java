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
import com.ed2nd.mywallet.domain.Wallet;
import com.ed2nd.mywallet.domain.enums.TransactionType;
import com.ed2nd.mywallet.repositories.AccountRespository;
import com.ed2nd.mywallet.repositories.BudgetRespository;
import com.ed2nd.mywallet.repositories.MenuRepository;
import com.ed2nd.mywallet.repositories.TransactionRespository;
import com.ed2nd.mywallet.repositories.WalletRepository;

@SpringBootApplication
public class MywalletApplication implements CommandLineRunner {

	@Autowired
	private MenuRepository menuRepository;
	@Autowired
	private WalletRepository walletRepository;
	@Autowired
	private AccountRespository bankRespository;
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

		Menu mc1 = new Menu(null, "Visão Geral");
		Menu mc2 = new Menu(null, "Resumo");
		Menu mc3 = new Menu(null, "Despesas");
		Menu mc4 = new Menu(null, "Contas Bancarias");
		Menu mc5 = new Menu(null, "Orcamentos");
		Menu mc6 = new Menu(null, "Transações");

		menuRepository.saveAll(Arrays.asList(mc1, mc2, mc3, mc4, mc5, mc6));

		Wallet w1 = new Wallet(null, sdf.parse("09/05/2021 21:45"), "Carteira Principal");
		Wallet w2 = new Wallet(null, sdf.parse("31/05/2021 14:33"), "Carteira Secundária");

		Account b1 = new Account(null, "Millennium", sdf.parse("09/05/2021 21:47"), w1);
		Account b2 = new Account(null, "ActivoBank", sdf.parse("09/05/2021 21:48"), w1);

		Budget bg1 = new Budget(null, "Combustivel", 100.0, 0.0, sdf.parse("23/05/2021 21:47"), b1);
		Budget bg2 = new Budget(null, "Apostas", 50.0, 0.0, sdf.parse("23/05/2021 22:00"), b1);

		Transaction t0 = new Transaction(null, TransactionType.INCOME, "Salario", 1000.0, sdf.parse("01/05/2021 22:08"), b1, null);
		Transaction t1 = new Transaction(null, TransactionType.EXPENSE, "Combustivel", 20.0, sdf.parse("23/05/2021 22:08"), b1, bg1);
		Transaction t2 = new Transaction(null, TransactionType.EXPENSE, "Luz", 24.50, sdf.parse("23/05/2021 22:20"), b1, null);

		w1.getAccounts().addAll(Arrays.asList(b1, b2));

		b1.getBudgets().addAll(Arrays.asList(bg1, bg2));
		b1.getTransactions().addAll(Arrays.asList(t1, t2));

		bg1.getTransactions().addAll(Arrays.asList(t1));

		walletRepository.saveAll(Arrays.asList(w1, w2));
		bankRespository.saveAll(Arrays.asList(b1, b2));
		budgetRespository.saveAll(Arrays.asList(bg1, bg2));
		transactionRespository.saveAll(Arrays.asList(t0, t1, t2));

	}

}
