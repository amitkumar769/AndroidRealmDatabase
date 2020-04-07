package com.example.androidrealmdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText id, name, age;
    private Button add, update, read, delete;
    private TextView result;

    Realm mRealm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        mRealm = Realm.getDefaultInstance();
    }

    private void initViews() {
        id= findViewById(R.id.et_id);
        name= findViewById(R.id.et_name);
        age= findViewById(R.id.et_age);
        result= findViewById(R.id.result);


        add= findViewById(R.id.btnAdd);
        update= findViewById(R.id.btnUpdate);
        read= findViewById(R.id.btnRead);
        delete=findViewById(R.id.btnDelete);

        add.setOnClickListener(this);
        update.setOnClickListener(this);
        read.setOnClickListener(this);
        delete.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnAdd:
                addUser();
                break;
            case R.id.btnRead:
                readUserRecords();
                break;
            case R.id.btnUpdate:
                updateUserRecords();
                break;
            case R.id.btnDelete:
                deleteUserRecords();
                break;

        }

    }

    private void addUser() {
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {


                    try {


                        if (!name.getText().toString().trim().isEmpty()) {
                            User user = new User();
                             user.setName(name.getText().toString().trim());;

                            if (!age.getText().toString().trim().isEmpty())
                                user.setAge(Integer.parseInt(age.getText().toString().trim()));

                            if (!id.getText().toString().trim().isEmpty())
                            user.setId(Integer.parseInt(id.getText().toString().trim()));
                            realm.copyToRealm(user);
                        }

                    } catch (RealmPrimaryKeyConstraintException e) {
                        Toast.makeText(getApplicationContext(), "Primary Key exists, Press Update instead", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }

    private void readUserRecords() {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<User> realmResults = realm.where(User.class).findAll();
                result.setText("");
                for (User user: realmResults) {
                    result.append("Id: "+user.getId()+" name: "+user.getName()+" age: "+user.getAge());
                }

            }
        });
    }

    private void updateUserRecords() {
     mRealm.executeTransaction(new Realm.Transaction() {
         @Override
         public void execute(Realm realm) {
             if (!id.getText().toString().trim().isEmpty()) {

                 User user = realm.where(User.class).equalTo("id",Integer.parseInt( id.getText().toString())).findFirst();
                 if (user == null) {
                     user = realm.createObject(User.class, Integer.parseInt( id.getText().toString()));
                 }
                 if (!age.getText().toString().trim().isEmpty())
                     user.setAge(Integer.parseInt(age.getText().toString().trim()));

                 if (!name.getText().toString().trim().isEmpty())
                     user.setName(name.getText().toString().trim());
                    mRealm.copyToRealm(user);
             }


         }
     });

    }

    private void deleteUserRecords() {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                User user = realm.where(User.class).equalTo("id", Integer.parseInt( id.getText().toString())).findFirst();
                if (user != null) {
                    user.deleteFromRealm();
                }
            }
        });
    }
}
