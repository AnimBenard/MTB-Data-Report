package sample.Dashboard.Hub.FullRequestTable;

import javafx.collections.ObservableList;
import sample.Alert.DialogBox;
import sample.Dashboard.Hub.RequestListDataClass;

import java.sql.*;

public class RetrieveRequestListDataByDate {
    private static final String DB_PATH = "jdbc:mysql://lookcave.com:3306/limbobo_easy_tbd";
    private static final String DB_USERNAME = "limbobo_root";
    private static final String DB_PASSWORD = "Limbobowrn-1311";
    private static final String CLASS_PATH = "com.mysql.cj.jdbc.Driver";
    private static final String FETCH_LIST = "SELECT * FROM request_list WHERE request_date BETWEEN ? AND ? AND request_type = 'HUB' ORDER BY Id DESC";

    Connection connection = null;
    Statement statement = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    DialogBox dialogBox = new DialogBox();

    public void retrieveRequestList(ObservableList<RequestListDataClass> data, String from, String to){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName(CLASS_PATH);
                    connection = DriverManager.getConnection(DB_PATH, DB_USERNAME, DB_PASSWORD);
                    preparedStatement = connection.prepareStatement(FETCH_LIST, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    preparedStatement.setString(1, from);
                    preparedStatement.setString(2, to);
                    resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()){
                        Integer id = resultSet.getInt("Id");
                        String serial = resultSet.getString("serial_id");
                        String samId = resultSet.getString("sample_id");
                        String surname = resultSet.getString("surname");
                        String othername = resultSet.getString("othernames");
                        String fullName = othername + " " + surname;
                        String age = resultSet.getString("age");
                        String sex = resultSet.getString("sex");
                        String address = resultSet.getString("address");
                        String disTBNo = resultSet.getString("district_tb_number");
                        String refFac = resultSet.getString("referring_facility");
                        String specType = resultSet.getString("type_of_specimen");
                        String samApp = resultSet.getString("sample_appearance");
                        String reason = resultSet.getString("reason_for_examination");
                        String reqTime = resultSet.getString("request_time");
                        String reqDate = resultSet.getString("request_date");
                        String reqUser = resultSet.getString("request_user");
                        String reqType = resultSet.getString("request_type");
                        String reqId = resultSet.getString("request_id");
                        String results = resultSet.getString("results");
                        String examinedBy = resultSet.getString("examined_by");
                        String examinedTime = resultSet.getString("examined_time");
                        String examDate = resultSet.getString("examined_date");
                        String remarks = resultSet.getString("remarks");
                        String validation = resultSet.getString("validation");
                        String valTime = resultSet.getString("validation_time");
                        String valDate = resultSet.getString("validation_date");
                        String valBy = resultSet.getString("validated_by");
                        String delReq = resultSet.getString("validated_by");
                        String followUpMonths = resultSet.getString("follow_up_months");
                        String ageUnits = resultSet.getString("age_units");
                        String fullAge = age + " " + ageUnits;
                        data.add(new RequestListDataClass(id, serial, samId, surname, othername, age, sex, address, disTBNo, refFac, specType, samApp, reason, reqTime, reqDate,
                                reqUser, reqType, reqId, results, examinedBy, examinedTime, examDate, remarks, validation, valTime, valDate, valBy, delReq, followUpMonths, fullName, ageUnits, fullAge));
                    }
                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                } finally {
                    if(resultSet != null){
                        try {
                            resultSet.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    if(preparedStatement != null){
                        try {
                            preparedStatement.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    if(connection != null){
                        try {
                            connection.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }
}
