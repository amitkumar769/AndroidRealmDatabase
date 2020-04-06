package com.example.androidrealmdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText id, name, age;
    private Button add, update, read, delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();


    }

    private void initViews() {
        id= findViewById(R.id.et_id);
        name= findViewById(R.id.et_name);
        age= findViewById(R.id.et_age);

        add= findViewById(R.id.btnAdd);
        update= findViewById(R.id.btnUpdate);
        read= findViewById(R.id.btnRead);
        delete=findViewById(R.id.btnDelete);

    }

    @Override
    public void onClick(View v) {

    }
}
