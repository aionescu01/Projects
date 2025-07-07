package com.example.proiect_dsdm;

import static androidx.navigation.ui.NavigationUI.setupActionBarWithNavController;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.proiect_dsdm.budget.transaction.AddTransactionFragment;
import com.example.proiect_dsdm.login.LoginActivity;
import com.example.proiect_dsdm.task.AppDatabase;
import com.example.proiect_dsdm.task.TaskListFragment;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button logoutButton;
    public static AppDatabase database;
    private FirebaseAuth mAuth;
    private static final String PREF_NAME = "AuthPrefs";
    private static final String KEY_USER_ID = "userId";
    private SharedPreferences sharedPreferences;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        database = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "task_database").build();
        dbHelper = new DatabaseHelper();

        if (getUserId() == null || getUserId().isEmpty()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
/*            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new TaskListFragment())
                    .commit();*/
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new AddTransactionFragment())
                    .commit();
        }

        logoutButton = findViewById(R.id.logout);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(KEY_USER_ID);
                editor.apply();

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });


    }

    private String getUserId() {
        return sharedPreferences.getString(KEY_USER_ID, null);
    }


}
