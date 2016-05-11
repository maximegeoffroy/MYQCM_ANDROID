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
