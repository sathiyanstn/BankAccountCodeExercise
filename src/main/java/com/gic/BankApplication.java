package com.gic;

import com.gic.dto.Account;
import com.gic.service.AccountService;
import com.gic.utils.Constants;

public class BankApplication {
    private static AccountService accountService = new AccountService();

    public static void main(String[] args) {
        System.out.print(Constants.WELCOME_PHRASE);

        // Allow user to login or sign up, will return account number
        Account account = accountService.loginSignUpAccount();

        // when user enter stop when data not exist will return null
        if (account != null) {
            System.out.println(Constants.LINE_BREAK_PHRASE);
            System.out.println(
                    "You have successfully logged in to account " +
                            account.getAccountNumber() +
                            " ! " +
                            Constants.INITIAL_QUESTION_PHRASE
            );

            // With that previous account number we can make transactions
            accountService.makeTransaction(account);
        }
    }
}
