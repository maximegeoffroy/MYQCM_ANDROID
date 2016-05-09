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
import com.iia.myqcm.data.AnswerSQLiteAdapter;
import com.iia.myqcm.data.CategorySQLiteAdapter;
import com.iia.myqcm.data.GroupSQLiteAdapter;
import com.iia.myqcm.data.QcmSQLiteAdapter;
import com.iia.myqcm.data.QuestionSQLiteAdapter;
import com.iia.myqcm.data.UserSQLiteAdapter;
import com.iia.myqcm.entity.Answer;
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

    public static final String ID = "id";
    public static final String CONNECTED = "Connecté";

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

        q.setId(questionSQLiteAdapter.insert(q));
        q1.setId(questionSQLiteAdapter.insert(q1));
        q2.setId(questionSQLiteAdapter.insert(q2));
        q3.setId(questionSQLiteAdapter.insert(q3));
        q4.setId(questionSQLiteAdapter.insert(q4));

        questionSQLiteAdapter.close();

        AnswerSQLiteAdapter answerSQLiteAdapter = new AnswerSQLiteAdapter(this);
        answerSQLiteAdapter.open();

        Answer a = new Answer();
        a.setContent("Maxime");
        a.setPoint(1);
        a.setIs_valid(true);
        a.setCreated_at(d);
        a.setUpdated_at(d);
        a.setQuestion(q);

        Answer a1 = new Answer();
        a1.setContent("Alexis");
        a1.setPoint(1);
        a1.setIs_valid(true);
        a1.setCreated_at(d);
        a1.setUpdated_at(d);
        a1.setQuestion(q);

        Answer a2 = new Answer();
        a2.setContent("Yoan");
        a2.setPoint(0);
        a2.setIs_valid(false);
        a2.setCreated_at(d);
        a2.setUpdated_at(d);
        a2.setQuestion(q);

        Answer a3 = new Answer();
        a3.setContent("- 20 ans");
        a3.setPoint(0);
        a3.setIs_valid(false);
        a3.setCreated_at(d);
        a3.setUpdated_at(d);
        a3.setQuestion(q1);

        Answer a4 = new Answer();
        a4.setContent("+ 20 ans");
        a4.setPoint(1);
        a4.setIs_valid(true);
        a4.setCreated_at(d);
        a4.setUpdated_at(d);
        a4.setQuestion(q1);

        Answer a5 = new Answer();
        a5.setContent("+ 40 ans");
        a5.setPoint(1);
        a5.setIs_valid(true);
        a5.setCreated_at(d);
        a5.setUpdated_at(d);
        a5.setQuestion(q1);

        Answer a6 = new Answer();
        a6.setContent("Foot");
        a6.setPoint(5);
        a6.setIs_valid(true);
        a6.setCreated_at(d);
        a6.setUpdated_at(d);
        a6.setQuestion(q4);

        Answer a7 = new Answer();
        a7.setContent("Basket");
        a7.setPoint(2);
        a7.setIs_valid(true);
        a7.setCreated_at(d);
        a7.setUpdated_at(d);
        a7.setQuestion(q4);

        Answer a8 = new Answer();
        a8.setContent("Hand");
        a8.setPoint(0);
        a8.setIs_valid(false);
        a8.setCreated_at(d);
        a8.setUpdated_at(d);
        a8.setQuestion(q4);

        Answer a9 = new Answer();
        a9.setContent("Tennis");
        a9.setPoint(1);
        a9.setIs_valid(true);
        a9.setCreated_at(d);
        a9.setUpdated_at(d);
        a9.setQuestion(q4);

        Answer a10 = new Answer();
        a10.setContent("Test");
        a10.setPoint(2);
        a10.setIs_valid(true);
        a10.setCreated_at(d);
        a10.setUpdated_at(d);
        a10.setQuestion(q2);

        Answer a11 = new Answer();
        a11.setContent("Essai");
        a11.setPoint(0);
        a11.setIs_valid(false);
        a11.setCreated_at(d);
        a11.setUpdated_at(d);
        a11.setQuestion(q2);

        Answer a12 = new Answer();
        a12.setContent("Try");
        a12.setPoint(1);
        a12.setIs_valid(true);
        a12.setCreated_at(d);
        a12.setUpdated_at(d);
        a12.setQuestion(q2);

        Answer a13 = new Answer();
        a13.setContent("Azerty");
        a13.setPoint(2);
        a13.setIs_valid(true);
        a13.setCreated_at(d);
        a13.setUpdated_at(d);
        a13.setQuestion(q3);

        Answer a14 = new Answer();
        a14.setContent("wxccv");
        a14.setPoint(3);
        a14.setIs_valid(false);
        a14.setCreated_at(d);
        a14.setUpdated_at(d);
        a14.setQuestion(q3);

        Answer a15 = new Answer();
        a15.setContent("Poklklk");
        a15.setPoint(1);
        a15.setIs_valid(true);
        a15.setCreated_at(d);
        a15.setUpdated_at(d);
        a15.setQuestion(q3);

        Answer a16 = new Answer();
        a16.setContent("zdqsdfses");
        a16.setPoint(0);
        a16.setIs_valid(true);
        a16.setCreated_at(d);
        a16.setUpdated_at(d);
        a16.setQuestion(q3);


        answerSQLiteAdapter.insert(a);
        answerSQLiteAdapter.insert(a1);
        answerSQLiteAdapter.insert(a2);
        answerSQLiteAdapter.insert(a3);
        answerSQLiteAdapter.insert(a4);
        answerSQLiteAdapter.insert(a5);
        answerSQLiteAdapter.insert(a6);
        answerSQLiteAdapter.insert(a7);
        answerSQLiteAdapter.insert(a8);
        answerSQLiteAdapter.insert(a9);
        answerSQLiteAdapter.insert(a10);
        answerSQLiteAdapter.insert(a11);
        answerSQLiteAdapter.insert(a12);
        answerSQLiteAdapter.insert(a13);
        answerSQLiteAdapter.insert(a14);
        answerSQLiteAdapter.insert(a15);
        answerSQLiteAdapter.insert(a16);

        answerSQLiteAdapter.close();*/

        sqliteAdapter.open();

        //Click on button Connexion
        btConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //OPEN VIEW CATEGORYLISTACTIVITY
                Intent intent = new Intent(ConnexionActivity.this, CategoryListActivity.class);
                startActivity(intent);
//                if (!etUsername.getText().toString().equals("") && !etPassword.getText().toString().equals("")) {
//                    User user = sqliteAdapter.getUser(etUsername.getText().toString());
//                    if (user != null) {
//                        if (user.getPassword().toString().equals(etPassword.getText().toString())) {
//                            Toast.makeText(ConnexionActivity.this, CONNECTED, Toast.LENGTH_SHORT).show();
//
//                            //OPEN VIEW CATEGORYLISTACTIVITY
//                            Intent intent = new Intent(ConnexionActivity.this, CategoryListActivity.class);
//                            Bundle b = new Bundle();
//                            b.putLong(ID,user.getId());
//                            intent.putExtras(b);
//                            startActivity(intent);
//                        } else {
//                            Toast.makeText(ConnexionActivity.this, "Utilisateur non trouvé", Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        Toast.makeText(ConnexionActivity.this, "Utilisateur non trouvé", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(ConnexionActivity.this, "Veuillez renseigner tous les champs", Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }
}
