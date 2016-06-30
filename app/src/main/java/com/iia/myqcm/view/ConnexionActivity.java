package com.iia.myqcm.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.hardware.camera2.TotalCaptureResult;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import com.iia.myqcm.webservice.UserWSAdapter;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class ConnexionActivity extends AppCompatActivity {

    public static final String ID = "id";
    public static final String CONNECTED = "Connecté";
    public static final String ERROR_CONNEXION = "Erreur de connexion";
    public static final String ERROR_USER = "Erreur sur l'utilisateur";
    public static final String LOADING = "Connexion";
    public ProgressDialog progressDialog;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String USERID = "userId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        final EditText etUsername = (EditText) this.findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) this.findViewById(R.id.etPasswordUser);
        Button btConnexion = (Button) this.findViewById(R.id.buttonConnexion);

        final UserSQLiteAdapter sqliteAdapter = new UserSQLiteAdapter(this);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        sqliteAdapter.open();

        /**
         * Click on the connexion button
         */
        btConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    final UserWSAdapter userWSAdapter = new UserWSAdapter(ConnexionActivity.this);

                    /**
                     * Create and show progressDialog
                     */
                    progressDialog = new ProgressDialog(ConnexionActivity.this, R.style.myQcmProgressDialog);
                    progressDialog.setMessage(ConnexionActivity.LOADING);
                    progressDialog.show();

                    userWSAdapter.getUser(etUsername.getText().toString(), new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            User item = userWSAdapter.jsonToItem(response);
                            UserSQLiteAdapter userSQLiteAdapter = new UserSQLiteAdapter(ConnexionActivity.this);
                            userSQLiteAdapter.open();
                            long id = userSQLiteAdapter.insert(item);
                            userSQLiteAdapter.close();

                            /**
                             * Put in preferences user id
                             */
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putLong(USERID, id);
                            editor.apply();

                            /**
                             * If progressDialog showing, close this
                             */
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                                progressDialog = null;
                            }

                            /**
                             * Open categoryListActivity
                             */
                            Intent intent = new Intent(ConnexionActivity.this, CategoryListActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                            Toast.makeText(ConnexionActivity.this, ConnexionActivity.ERROR_CONNEXION, Toast.LENGTH_LONG).show();

                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                                progressDialog = null;
                            }
                        }
                    });
            }
        });
    }
}
