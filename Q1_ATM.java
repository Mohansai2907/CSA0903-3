import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

class InsufficientFundsException extends Exception {
    InsufficientFundsException(String message) {
        super(message);
    }
}

public class Q1_ATM {
    static double balance = 5000;

    static void withdraw(double amount)
            throws InsufficientFundsException {
        if (amount < 0)
            throw new IllegalArgumentException("Amount cannot be negative");
        if (amount > balance)
            throw new InsufficientFundsException("Insufficient balance");

        balance -= amount;
        System.out.println("Withdrawal successful");
        System.out.println("Remaining balance: " + balance);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            // Simulate checking the ATM card-reader file.
            FileReader card = new FileReader("cardreader.txt");
            card.close();

            System.out.print("Enter account number: ");
            String account = sc.nextLine();

            System.out.print("Enter withdrawal amount: ");
            double amount = Double.parseDouble(sc.nextLine());

            if (account.isEmpty())
                throw new IllegalArgumentException("Account number is required");

            withdraw(amount);

        } catch (NumberFormatException e) {
            System.out.println("Error: Enter numbers only");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (InsufficientFundsException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (IOException | SecurityException e) {
            System.out.println("Card reader error: " + e.getMessage());
        } finally {
            System.out.println("ATM transaction completed");
            sc.close();
        }
    }
}

/*
Create an empty file named cardreader.txt in the same folder before running.
Example input:
123456
1000
*/