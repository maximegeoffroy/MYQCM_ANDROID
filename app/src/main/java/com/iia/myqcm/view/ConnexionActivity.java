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
import com.iia.myqcm.data.QuestionSQLiteAdapter;
import com.iia.myqcm.data.UserSQLiteAdapter;
import com.iia.myqcm.entity.Category;
import com.iia.myqcm.entity.Group;
import com.iia.myqcm.entity.Qcm;
import com.iia.myqcm.entity.Question;
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
        sqliteAdapter.open();
        sqliteAdapter.insert(user);

        sqliteAdapter.close();

        CategorySQLiteAdapter categorySQLiteAdapter = new CategorySQLiteAdapter(this);
        categorySQLiteAdapter.open();

        //CREATION CATEGORY
        Category c = new Category();
        Date d1 = new Date();
        c.setName("Test");
        c.setCreated_at(d1);
        c.setUpdated_at(d1);

        Category c1 = new Category();
        c1.setName("Essai");
        c1.setCreated_at(d1);
        c1.setUpdated_at(d1);

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
        qcm.setDuration(120);
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

        Qcm qcm3 = new Qcm();
        qcm3.setName("QCM 3");
        qcm3.setStart_date(d);
        qcm3.setEnd_date(d);
        qcm3.setDuration(120);
        qcm3.setCreated_at(d);
        qcm3.setUpdated_at(d);
        qcm3.setCategory(c);

        qcm.setId(qcmSQLiteAdapter.insert(qcm));
        qcm1.setId(qcmSQLiteAdapter.insert(qcm1));
        qcm3.setId(qcmSQLiteAdapter.insert(qcm3));

        qcmSQLiteAdapter.close();

        QuestionSQLiteAdapter questionSQLiteAdapter = new QuestionSQLiteAdapter(this);
        questionSQLiteAdapter.open();

        Question q = new Question();
        q.setContent("Quel est ton nom ?");
        q.setCreated_at(d);
        q.setUpdated_at(d);
        q.setQcm(qcm);

        Question q1 = new Question();
        q1.setContent("Quel est ton age ?");
        q1.setCreated_at(d);
        q1.setUpdated_at(d);
        q1.setQcm(qcm1);

        Question q2 = new Question();
        q2.setContent("Quel est ton sexe ?");
        q2.setCreated_at(d);
        q2.setUpdated_at(d);
        q2.setQcm(qcm1);

        Question q3 = new Question();
        q3.setContent("Quel est ton travail ?");
        q3.setCreated_at(d);
        q3.setUpdated_at(d);
        q3.setQcm(qcm3);

        Question q4 = new Question();
        q4.setContent("Quel est ton loisir ?");
        q4.setCreated_at(d);
        q4.setUpdated_at(d);
        q4.setQcm(qcm3);

        questionSQLiteAdapter.insert(q);
        questionSQLiteAdapter.insert(q1);
        questionSQLiteAdapter.insert(q2);
        questionSQLiteAdapter.insert(q3);
        questionSQLiteAdapter.insert(q4);

        questionSQLiteAdapter.close();*/


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
