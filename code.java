import java.util.ArrayList;
import java.util.Scanner;

class BankAccount {
    String accountHolderName;
    String accountNumber;
    int pin;
    double balance;
    double dailyWithdrawalLimit;
    double dailyWithdrawnAmount;

    BankAccount(String name, String accNumber, int pin, double initialBalance) {
        this.accountHolderName = name;
        this.accountNumber = accNumber;
        this.pin = pin;
        this.balance = initialBalance;
        this.dailyWithdrawalLimit = 20000; // Daily ATM withdrawal limit
        this.dailyWithdrawnAmount = 0;
    }

    void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Amount deposited successfully.");
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            if ((dailyWithdrawnAmount + amount) <= dailyWithdrawalLimit) {
                balance -= amount;
                dailyWithdrawnAmount += amount;
                System.out.println("Amount withdrawn successfully.");
            } else {
                System.out.println("Daily withdrawal limit exceeded! You can withdraw up to " + (dailyWithdrawalLimit - dailyWithdrawnAmount));
            }
        } else {
            System.out.println("Invalid withdrawal amount or insufficient balance.");
        }
    }

    void checkBalance() {
        System.out.println("Current Balance: " + balance);
    }

    void changePin(int newPin) {
        this.pin = newPin;
        System.out.println("PIN changed successfully.");
    }

    void resetDailyLimit() {
        this.dailyWithdrawnAmount = 0;
    }
}

public class ATMSimulation {
    static ArrayList<BankAccount> accounts = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n--- ATM Main Menu ---");
            System.out.println("1. Create Account");
            System.out.println("2. Login to ATM");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            while (!sc.hasNextInt()) {
                System.out.print("Enter a valid number: ");
                sc.next();
            }
            choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    System.out.println("Thank you for using our ATM!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 3);

        sc.close();
    }

    static void createAccount() {
        System.out.print("Enter Account Holder Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Account Number: ");
        String accNumber = sc.nextLine();
        System.out.print("Set 4-digit PIN: ");
        int pin = sc.nextInt();
        System.out.print("Enter Initial Balance: ");
        double initialBalance = sc.nextDouble();
        sc.nextLine(); // Consume newline

        BankAccount account = new BankAccount(name, accNumber, pin, initialBalance);
        accounts.add(account);
        System.out.println("Account created successfully!");
    }

    static BankAccount findAccount(String accNumber, int pin) {
        for (BankAccount acc : accounts) {
            if (acc.accountNumber.equals(accNumber) && acc.pin == pin) {
                return acc;
            }
        }
        return null;
    }

    static void login() {
        System.out.print("Enter Account Number: ");
        String accNumber = sc.nextLine();
        System.out.print("Enter PIN: ");
        int pin = sc.nextInt();
        sc.nextLine(); // Consume newline

        BankAccount account = findAccount(accNumber, pin);
        if (account != null) {
            System.out.println("Login successful!");
            atmMenu(account);
        } else {
            System.out.println("Invalid account number or PIN.");
        }
    }

    static void atmMenu(BankAccount account) {
        int choice;
        do {
            System.out.println("\n--- ATM Transaction Menu ---");
            System.out.println("1. Withdraw Money");
            System.out.println("2. Deposit Money");
            System.out.println("3. Check Balance");
            System.out.println("4. Change PIN");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            while (!sc.hasNextInt()) {
                System.out.print("Enter a valid number: ");
                sc.next();
            }
            choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = sc.nextDouble();
                    sc.nextLine(); // Consume newline
                    account.withdraw(withdrawAmount);
                    break;
                case 2:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = sc.nextDouble();
                    sc.nextLine(); // Consume newline
                    account.deposit(depositAmount);
                    break;
                case 3:
                    account.checkBalance();
                    break;
                case 4:
                    System.out.print("Enter new 4-digit PIN: ");
                    int newPin = sc.nextInt();
                    sc.nextLine(); // Consume newline
                    account.changePin(newPin);
                    break;
                case 5:
                    System.out.println("Logged out successfully.");
                    account.resetDailyLimit(); // Simulate daily reset on logout
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }
}
