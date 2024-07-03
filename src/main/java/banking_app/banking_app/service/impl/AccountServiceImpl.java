package banking_app.banking_app.service.impl;

import org.springframework.stereotype.Service;

import banking_app.banking_app.dto.AccountDto;
import banking_app.banking_app.entity.Account;
import banking_app.banking_app.mapper.AccountMapper;
import banking_app.banking_app.repository.AccountRepository;
import banking_app.banking_app.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService{

    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }



    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }



    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Account does not exist"));
        return AccountMapper.mapToAccountDto(account);
    }



    @Override
    public AccountDto deposit(Long id, double amount) {
        Account account = accountRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Account does not exist"));
        
        double total = account.getBalance() + amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);

        return AccountMapper.mapToAccountDto(savedAccount);
    }



    @Override
    public AccountDto withdraw(Long id, double amount) {
        Account account = accountRepository
        .findById(id)
        .orElseThrow(() -> new RuntimeException("Account does not exist"));
    
        if(account.getBalance() < amount){
            throw new RuntimeException("Insufficient amount"); 
        }

        double total = account.getBalance() - amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

}
