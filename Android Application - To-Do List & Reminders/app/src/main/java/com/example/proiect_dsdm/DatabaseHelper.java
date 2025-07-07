package com.example.proiect_dsdm;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHelper {

    private Connection getConnection() throws SQLException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String connectionString = String.format(
                "jdbc:jtds:sqlserver://%s;databaseName=%s;user=%s;password=%s;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;",
                BuildConfig.SERVER_NAME, BuildConfig.DATABASE_NAME, BuildConfig.USERNAME, BuildConfig.PASSWORD
        );
        return DriverManager.getConnection(connectionString);
    }



}
