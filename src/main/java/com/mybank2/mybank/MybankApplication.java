package com.mybank2.mybank;

import java.time.LocalDateTime;


import com.mybank2.mybank.dao.AccountRepository;
import com.mybank2.mybank.dao.TransactionRepository;
import com.mybank2.mybank.dao.UserRepository;
import com.mybank2.mybank.entities.Account;
import com.mybank2.mybank.entities.Deposit;
import com.mybank2.mybank.entities.Transaction;
import com.mybank2.mybank.entities.User;
import com.mybank2.mybank.entities.Withdrawal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MybankApplication implements CommandLineRunner {

	// @Bean
	// public ModelMapper modelMapper() {
	// return new ModelMapper();
	// }

	private final AccountRepository accountRepository;
	private final TransactionRepository transactionRepository;
	private final UserRepository userRepository;

	@Autowired
	public MybankApplication(AccountRepository accountRepository,
			UserRepository userRepository, TransactionRepository transactionRepository) {
		this.accountRepository = accountRepository;
		this.userRepository = userRepository;
		this.transactionRepository = transactionRepository;

	}

	// @Autowired
	// private AccountRepository accountRepository;

	// @Autowired
	// private TransactionRepository transactionRepository;

	// @Autowired
	// private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(MybankApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		User user1 = userRepository.save(new User("Najib", "najib@gmail.com"));
		userRepository.save(user1);
		Account account = new Account(LocalDateTime.now(), "code1", 2555, user1);
		accountRepository.save(account);

		Transaction deposit = new Deposit(LocalDateTime.now(), 1000, "code1");
		Transaction whithdrawal = new Withdrawal(LocalDateTime.now(), 1000, "code1");

		transactionRepository.save(deposit);
		transactionRepository.save(whithdrawal);
		// accountRepository.save(new Account(LocalDateTime.now(), "code1", new BigDecimal(42), user1));

	}
}