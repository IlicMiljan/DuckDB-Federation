package com.miljanilic.executor;

import com.google.inject.Singleton;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

@Singleton
public class ResultSetPrinter {
    public void print(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        StringBuilder output = new StringBuilder();

        output.append("+").append("-".repeat(24).repeat(columnCount)).append("+\n");

        output.append("|");
        for (int i = 1; i <= columnCount; i++) {
            output.append(String.format(" %-22s|", metaData.getColumnName(i).length() > 20 ? metaData.getColumnName(i).substring(0, 20) : metaData.getColumnName(i)));
        }
        output.append("\n");

        output.append("+").append("-".repeat(24).repeat(columnCount)).append("+\n");

        while (resultSet.next()) {
            output.append("|");
            for (int i = 1; i <= columnCount; i++) {
                String value = resultSet.getString(i);
                if (value == null) {
                    value = "NULL";
                }
                output.append(String.format(" %-22s|", value));
            }
            output.append("\n");
        }

        output.append("+").append("-".repeat(24).repeat(columnCount)).append("+\n");

        synchronized (System.out) {
            System.out.print(output);
        }
    }
}
