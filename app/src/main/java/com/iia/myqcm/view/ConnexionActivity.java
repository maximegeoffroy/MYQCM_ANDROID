package com.iia.myqcm.view;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.iia.myqcm.R;
import com.iia.myqcm.data.CategorySQLiteAdapter;
import com.iia.myqcm.data.GroupSQLiteAdapter;
import com.iia.myqcm.data.QcmSQLiteAdapter;
import com.iia.myqcm.data.UserSQLiteAdapter;
import com.iia.myqcm.entity.Category;
import com.iia.myqcm.entity.Group;
import com.iia.myqcm.entity.Qcm;
import com.iia.myqcm.entity.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ConnexionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        final EditText etUsername = (EditText) this.findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) this.findViewById(R.id.etPasswordUser);
        Button btConnexion = (Button) this.findViewById(R.id.buttonConnexion);

        final UserSQLiteAdapter sqliteAdapter = new UserSQLiteAdapter(this);
        /*GroupSQLiteAdapter groupSQLiteAdapter = new GroupSQLiteAdapter(this);
        groupSQLiteAdapter.open();

        User user = new User();
        user.setUsername("Test");
        user.setPassword("passw0rd");
        user.setName("Test name");
        user.setFirstname("Test firstname");
        user.setEmail("test@gmail.com");
        Date d = new Date();
        user.setCreated_at(d);
        user.setUpdated_at(d);

        Group group = new Group();
        group.setName("CDSM");
        group.setCreated_at(d);
        group.setUpdated_at(d);

        long id = groupSQLiteAdapter.insert(group);
        group.setId(id);

        groupSQLiteAdapter.close();

        user.setGroup(group);
        sqliteAdapter.insert(user);

        sqliteAdapter.close();

        CategorySQLiteAdapter categorySQLiteAdapter = new CategorySQLiteAdapter(this);
        categorySQLiteAdapter.open();

        //CREATION CATEGORY
        Category c = new Category();
        c.setName("Test");
        c.setCreated_at(d);
        c.setUpdated_at(d);

        Category c1 = new Category();
        c1.setName("Essai");
        c1.setCreated_at(d);
        c1.setUpdated_at(d);

        long idCategory1 = categorySQLiteAdapter.insert(c);
        long idCategory2 = categorySQLiteAdapter.insert(c1);

        c.setId(idCategory1);
        c1.setId(idCategory2);

        categorySQLiteAdapter.close();

        QcmSQLiteAdapter qcmSQLiteAdapter = new QcmSQLiteAdapter(this);
        qcmSQLiteAdapter.open();

        Qcm qcm = new Qcm();
        qcm.setName("QCM 1");
        qcm.setStart_date(d);
        qcm.setEnd_date(d);
        qcm.setDuration(50);
        qcm.setCreated_at(d);
        qcm.setUpdated_at(d);
        qcm.setCategory(c);

        Qcm qcm1 = new Qcm();
        qcm1.setName("QCM 2");
        qcm1.setStart_date(d);
        qcm1.setEnd_date(d);
        qcm1.setDuration(60);
        qcm1.setCreated_at(d);
        qcm1.setUpdated_at(d);
        qcm1.setCategory(c1);

        qcmSQLiteAdapter.insert(qcm);
        qcmSQLiteAdapter.insert(qcm1);

        qcmSQLiteAdapter.close();*/

        sqliteAdapter.open();

        //Click on button Connexion
        btConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etUsername.getText().toString().equals("") && !etPassword.getText().toString().equals("")) {
                    User user = sqliteAdapter.getUser(etUsername.getText().toString());
                    if (user != null) {
                        if (user.getPassword().toString().equals(etPassword.getText().toString())) {
                            Toast.makeText(ConnexionActivity.this, "Connecté", Toast.LENGTH_SHORT).show();

                            //OPEN VIEW CATEGORYLISTACTIVITY
                            Intent intent = new Intent(ConnexionActivity.this, CategoryListActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(ConnexionActivity.this, "Utilisateur non trouvé", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ConnexionActivity.this, "Utilisateur non trouvé", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ConnexionActivity.this, "Veuillez renseigner tous les champs", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
