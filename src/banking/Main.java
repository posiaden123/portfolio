package banking;
import java.util.*;

public class Main {
    private static String card;
    private static int balanceOfCard = 0;
    private static String myPIN;
    static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        banking banking = new banking();
        long finalCard = 0;
        long PIN = 0;
        banking.main();
        boolean loop = true;
        while (loop) {
            System.out.println("1. Create an account");
            System.out.println("2. Log into account");
            System.out.println("0. Exit");
            int action = sc.nextInt();
            sc.nextLine();
            if (action == 1) {
                long number = createCard();
                long checkSum = calculateLuhnsCheckDigit(number);
                finalCard = generateFinalCard(number, checkSum);
                PIN = createPin();
                System.out.println();
                System.out.println("Your card has been created");
                System.out.println("Your card number:");
                System.out.println(finalCard);
                System.out.println("Your card PIN:");
                System.out.println(PIN);
                banking.addToDataBase(finalCard, PIN);
                System.out.println();
            }
            if (action == 2) {
                System.out.println();
                System.out.println("Enter your card number:");
                String checkNumber = sc.nextLine();
                System.out.println("Enter your PIN:");
                String checkPIN = sc.nextLine();
                if (banking.checkIfExists(checkNumber, checkPIN)) {
                    card = checkNumber;
                    myPIN = checkPIN;
                    if (loggedIn()) {
                        System.out.println();
                        System.out.println("Bye!");
                        System.out.println();
                        break;
                    }
                } else {
                    System.out.println();
                    System.out.println("Wrong card number or PIN!");
                    System.out.println();
                }

            }
            if (action == 0) {
                System.out.println();
                System.out.println("Bye!");
                System.out.println();
                break;
            }
        }
    }
    public static long createCard() {
        Random random = new Random();
        long id = 400000000000000L;
        long card = random.nextInt((int) 999999999L - (int) 100000000L + 1) + 100000000L;
        card += id;
        return card;
    }

    public static long createPin() {
        Random random = new Random();
        int pin = random.nextInt(9 - 1 + 1) + 1;
        int pin2 = random.nextInt(10);
        int pin3 = random.nextInt(10);
        int pin4 = random.nextInt(10);
        String pIN = pin + "" + pin2 + "" + pin3 + "" + pin4;
        long PIN = Long.parseLong(pIN);
        return PIN;
    }

    public static long generateFinalCard(long l, long cs) {
        long finalCard = (l * 10) + cs;
        return finalCard;
    }
    public static int calculateLuhnsCheckDigit(final long number) {
        int     sum       = 0;
        boolean alternate = false;
        String  digits    = Long.toString(number);

        for (int i = digits.length() - 1; i >= 0; --i) {
            int digit = Character.getNumericValue(digits.charAt(i)); // get the digit at the given index
            digit = (alternate = !alternate) ? (digit * 2) : digit;  // double every other digit
            digit = (digit > 9)              ? (digit - 9) : digit;  // subtract 9 if the value is greater than 9
            sum += digit;                                            // add the digit to the sum
        }

        return (sum * 9) % 10;
    }
    public static boolean loggedIn() {
        boolean loginLoop = true;
        int balance  = 0;
        boolean done = false;
        System.out.println();
        System.out.println("You have successfully logged in!");
        System.out.println();
            while (loginLoop) {
                System.out.println("1. Balance");
                System.out.println("2. Add income");
                System.out.println("3. Do transfer");
                System.out.println("4. Close account");
                System.out.println("5. Log out");
                System.out.println("0. Exit");
                int action2 = sc.nextInt();
                sc.nextLine();
                if (action2 == 1) {
                    System.out.println();
                    balanceOfCard = banking.checkBalance(card, myPIN);

                    System.out.println("Balance: " + balanceOfCard);
                    System.out.println();
                } else if (action2 == 5) {
                    System.out.println();
                    System.out.println("You have successfully logged out!");
                    System.out.println();
                    loginLoop = false;
                } else if (action2 == 0) {
                    done = true;
                    break;
                } else if (action2 == 2) {
                    System.out.println();
                    System.out.println("Enter income:");
                    int income = sc.nextInt();
                    sc.nextLine();
                    banking.addIncome(income, card);
                    System.out.println("Income was added!");
                    System.out.println();
                } else if (action2 == 3) {
                    System.out.println();
                    System.out.println("Transfer");
                    System.out.println("Enter card number:");
                    String str = sc.nextLine();
                    if (banking.checkIfPassesLuhn(str)) {
                        if (banking.checkIfCardExists(str)) {
                            System.out.println("Enter how much money you want to transfer:");
                            int transfer = sc.nextInt();
                            sc.nextLine();
                            balanceOfCard = banking.checkBalance(card, myPIN);
                            if (balanceOfCard >= transfer) {
                                banking.transferToAccount(card, str, transfer);
                                System.out.println("Success!");
                                System.out.println();
                            } else {
                                System.out.println("Not enough money!");
                                System.out.println();
                            }
                        } else {
                            System.out.println("Such a card does not exist.");
                            System.out.println();
                        }
                    } else {
                        System.out.println("Probably you made mistake in the card number. Please try again!");
                        System.out.println();
                    }
                }else if (action2 == 4) {
                    banking.deleteAccount(card, myPIN);
                    System.out.println();
                    System.out.println("The account has been closed!");
                    System.out.println();
                }

            }
        return done;
    }
    }

