package com.gic;

import com.gic.utils.Constants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class BankApplicationTest {

    private static String userInput;
    private static ByteArrayInputStream byteArrayInputStream;

    @Before
    public void init() {
        // Setup user input data for all test cases
        userInput = String.format(
                "S %s P %s Stop %s " +
                        "o %s L %s -1 %s Stop %s " +
                        "o %s L %s 1 %s P %s Stop %s " +
                        "L %s o %s L %s 1 %s D %s o %s D %s 100 %s W %s 50 %s P %s Stop",
                "\n", "\n", "\n",
                "\n", "\n", "\n", "\n",
                "\n", "\n", "\n", "\n", "\n",
                "\n", "\n", "\n", "\n", "\n", "\n", "\n", "\n", "\n", "\n", "\n");
        byteArrayInputStream = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(byteArrayInputStream);
    }

    @Test
    public void Test1() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        System.setOut(printStream);

        BankApplication.main(null);

        String[] lines = byteArrayOutputStream.toString().split("\r\n");
        String actual = lines[lines.length - 2];

        // checkout output
        Assert.assertEquals(Constants.FINAL_PHRASE, actual);
    }

    @Test
    public void Test2() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        System.setOut(printStream);

        BankApplication.main(null);

        String[] lines = byteArrayOutputStream.toString().split("\r\n");
        String actual = lines[lines.length - 2];

        // checkout output
        Assert.assertEquals(Constants.FINAL_PHRASE, actual);
    }

    @Test
    public void Test3() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        System.setOut(printStream);

        BankApplication.main(null);

        String[] lines = byteArrayOutputStream.toString().split("\r\n");
        String actual = lines[lines.length - 2];

        // checkout output
        Assert.assertEquals(Constants.FINAL_PHRASE, actual);
    }

    @Test
    public void Test4() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        System.setOut(printStream);

        BankApplication.main(null);

        String[] lines = byteArrayOutputStream.toString().split("\r\n");
        String actual = lines[lines.length - 2];

        // checkout output
        Assert.assertEquals(Constants.FINAL_PHRASE, actual);
    }
}