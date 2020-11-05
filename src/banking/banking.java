package banking;

import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class banking {
    private Object ResultSet;

    public static void main() {
        String url = "jdbc:sqlite:C:\\Users\\bobwe\\IdeaProjects\\Simple Banking System\\Simple Banking System\\task\\card.s3db";
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS card(" +
                        "id INTEGER PRIMARY KEY," +
                        "number TEXT," +
                        "pin TEXT," +
                        "balance INTEGER DEFAULT 0);");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static long addToDataBase(long card, long pin) {
        String url = "jdbc:sqlite:C:\\Users\\bobwe\\IdeaProjects\\Simple Banking System\\Simple Banking System\\task\\card.s3db";
        long add = 0;
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                add = statement.executeUpdate("INSERT INTO card (number, pin) VALUES ('" + card + "', '" + pin + "')");
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return add;
    }

    public static boolean checkIfExists(String number, String pin) {
        boolean exists = false;
        String url = "jdbc:sqlite:C:\\Users\\bobwe\\IdeaProjects\\Simple Banking System\\Simple Banking System\\task\\card.s3db";
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM card WHERE number = '" + number + "' AND pin = '" + pin + "'";

            ResultSet rs = statement.executeQuery(sql);
            exists = rs.next();
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return exists;
    }

    public static void addIncome(int income, String number) {
        String url = "jdbc:sqlite:C:\\Users\\bobwe\\IdeaProjects\\Simple Banking System\\Simple Banking System\\task\\card.s3db";
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            String sql = "UPDATE card SET balance = balance + " + income + " WHERE number = " + number;
            statement.executeUpdate(sql);
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean checkIfCardExists(String number) {
        String url = "jdbc:sqlite:C:\\Users\\bobwe\\IdeaProjects\\Simple Banking System\\Simple Banking System\\task\\card.s3db";
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);
        try (Connection conn = dataSource.getConnection()) {
            // Statement creation
            try (Statement statement = conn.createStatement()) {
                try (ResultSet correctCard = statement.executeQuery("SELECT * FROM card")) {
                    while (correctCard.next()) {
                        // Retrieve column values
                        if (correctCard.getString("number").equals(number)){
                            return true;
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean checkIfPassesLuhn(String number) {
        int[] numArr = new int[number.length()];
        int sum = 0;

        for (int i = 0; i < numArr.length; i++)
        {
            numArr[i] = Integer.parseInt(String.valueOf(number.charAt(i)));

            if ((i + 1) % 2 != 0)
            {
                numArr[i] *= 2;
            }
            if (numArr[i] > 9)
            {
                numArr[i] -= 9;
            }

            sum += numArr[i];
        }
        return sum % 10 == 0;
    }

    public static int checkBalance(String number, String pin) {
        String url = "jdbc:sqlite:C:\\Users\\bobwe\\IdeaProjects\\Simple Banking System\\Simple Banking System\\task\\card.s3db";
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);
        int newBal = 0;
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT balance FROM card WHERE number = '" + number + "' AND pin = '" + pin + "'";
            java.sql.ResultSet balance = statement.executeQuery(sql);
            newBal = balance.getInt(1);
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newBal;
    }
    public static void deleteAccount(String number, String pin) {
        String url = "jdbc:sqlite:C:\\Users\\bobwe\\IdeaProjects\\Simple Banking System\\Simple Banking System\\task\\card.s3db";
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            String sql = "DELETE FROM card WHERE number = '" + number + "' AND pin = '" + pin + "'";
            statement.executeUpdate(sql);
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void transferToAccount(String cardNum, String transferCardNum, int amount) {
        String url = "jdbc:sqlite:C:\\Users\\bobwe\\IdeaProjects\\Simple Banking System\\Simple Banking System\\task\\card.s3db";
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);
        try
        {
            Connection connection = dataSource.getConnection();
            Statement stmt = connection.createStatement();
            String sql = "UPDATE card SET balance = balance + " + amount + " WHERE number = '" + transferCardNum + "'";
            String sql2 = "UPDATE card SET balance = balance - " + amount + " WHERE number = '" + cardNum + "'";
            stmt.execute(sql);
            stmt.execute(sql2);
            stmt.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    }




