package com.gic.service;

import com.gic.dto.Account;
import com.gic.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AccountDataService {
    public static ArrayList<Account> accountsArray = new ArrayList<Account>();

    public void readAccountData() {
        ArrayList<Account> accountsArray = new ArrayList<Account>();
        try {
            Gson gson = new Gson();

            // Read the JSON file from that path and if not exist it will capture in FileNotFoundException
            FileReader fileReader = new FileReader(Constants.ACCOUNT_DATA_PATH);

            accountsArray = gson.fromJson(fileReader, new TypeToken<List<Account>>() {
            }.getType());
            fileReader.close();

            if (accountsArray != null && !accountsArray.isEmpty()) {
                // From the accounts list get the last index account number as last account number
                int lastAccountNumber = accountsArray.get(accountsArray.size() - 1).getAccountNumber();

                // Set that last account number into Account static variable
                Account.nextAvailableAccountNumber = lastAccountNumber + 1;
            } else {
                accountsArray = new ArrayList<Account>();
                // Consider the next available account number as 1 as initial
                Account.nextAvailableAccountNumber = 1;
            }
        } catch (FileNotFoundException e) {
            // Consider the next available account number as 1 as initial
            Account.nextAvailableAccountNumber = 1;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        AccountDataService.accountsArray = accountsArray;
    }

    public void updateAccountData() {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            FileWriter file = new FileWriter(Constants.ACCOUNT_DATA_PATH);

            // write the updated account array into the json using the path, if file not exist it will create new file and write the data
            file.write(gson.toJson(accountsArray));
            file.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void quit() {
        System.out.println(Constants.FINAL_PHRASE);
        System.out.println(Constants.LINE_BREAK_PHRASE);

        // Make sure we save the updated array list into the JSON file
        updateAccountData();

        // Exit
        System.exit(0);
    }

    // Only for test case purpose without System exit
    public void quitWithoutSystemExit() {
        System.out.println(Constants.FINAL_PHRASE);
        System.out.println(Constants.LINE_BREAK_PHRASE);
        updateAccountData();
    }
}
