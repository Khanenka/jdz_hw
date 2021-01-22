import java.sql.*;

public class Task5 {


        public static void main(String[] args) {

            validateParameters(args);
            insertIntoDataBase(args);
            selectTable();

        }

    private static void selectTable() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            final Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/expenses?serverTimezone=UTC", "root", "root");
            final Statement statement = connection.createStatement();
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM expenses;");
            while (resultSet.next()) {
                int num = resultSet.getInt(1);
                String date = resultSet.getString(2);
                double value = resultSet.getDouble(3);
                int receiver = resultSet.getInt(4);


                System.out.println("num=" + num + " date=" + date   + " receiver=" + receiver+ " value=" + value);
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }




        private static void insertIntoDataBase(String[] args) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                final String dbURL ="jdbc:mysql://localhost:3306/expenses?serverTimezone=UTC";
                final String username="root";
                final String password="root";
                final Connection myConnection;
                myConnection = DriverManager.getConnection(dbURL,username,password);
                  PreparedStatement preparedStatement = myConnection.prepareStatement("insert into expenses.expenses (num, paydate, receiver,value)    values(?,?,?,?)");


                ResultSet resultSet = preparedStatement.executeQuery("SELECT max(num) FROM expenses.expenses;");
                resultSet.next();
                int id = resultSet.getInt(1) + 1;
                preparedStatement.setInt(1, id);
                preparedStatement.setDate(2, Date.valueOf(args[0]));
                preparedStatement.setDouble(3, Double.parseDouble(args[1]));
                preparedStatement.setInt(4, Integer.parseInt(args[2]));
                preparedStatement.executeUpdate();


        } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            }
    private static void validateParameters(String[] args) {
        if (args.length != 3) {
            System.out.println("input 3 params");
            System.exit(0);
        }
        try {
            Date.valueOf(args[0]);
        } catch (Exception e) {
            System.out.println("incorrect date");
            System.exit(0);

        }
        try {
            Double.parseDouble(args[1]);
            Integer.parseInt(args[2]);

        } catch (Exception e) {
            System.out.println("input param value and input param receiver  ");
            System.exit(0);
        }
    }


}