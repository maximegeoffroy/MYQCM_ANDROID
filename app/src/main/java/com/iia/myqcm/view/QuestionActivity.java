package com.iia.myqcm.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iia.myqcm.R;
import com.iia.myqcm.data.AnswerSQLiteAdapter;
import com.iia.myqcm.data.QuestionSQLiteAdapter;
import com.iia.myqcm.entity.Answer;
import com.iia.myqcm.entity.Question;
import com.iia.myqcm.webservice.UserWSAdapter;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class QuestionActivity extends AppCompatActivity {
    public final String SELECT_ANSWER = "Selectionner au moins une réponse";
    public final String BUTTON_ENVOYER = "Envoyer";

    public ArrayList<Question> questionsList;
    public ArrayList<Answer> resultAnswers;
    public int c = 0;
    public Question q;
    public boolean checkboxChecked = false;
    public long qcmId = 0;

    SharedPreferences sharedpreferences;

    public Context ctx;
    public LinearLayout linearLayout;
    public TextView tvContentQuestion;
    public TextView tvQcmName;
    public Button btValidate;
    public Button btBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        this.ctx = this;
        linearLayout = (LinearLayout) this.findViewById(R.id.linearLayoutContainer);
        tvContentQuestion = (TextView) this.findViewById(R.id.tvContentQuestion);
        tvQcmName = (TextView) this.findViewById(R.id.tvQcmName);
        btValidate = (Button) this.findViewById(R.id.btValidate);
        btBack = (Button) this.findViewById(R.id.btBack);

        btBack.setVisibility(View.GONE);

        Bundle b = this.getIntent().getExtras();
        qcmId = b.getLong(QcmListActivity.QCM_ID);

        QuestionSQLiteAdapter questionSQLiteAdapter = new QuestionSQLiteAdapter(this);
        questionSQLiteAdapter.open();
        questionsList = questionSQLiteAdapter.getAllByQcm(qcmId);
        questionSQLiteAdapter.close();

        /**
         * Show question and answers
         */
        resultAnswers = showQuestion(c);

        btValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (checkboxChecked) {

                    btBack.setVisibility(View.VISIBLE);

                    /**
                     * For answer in answersList update in database if isSelected = true
                     */
                    for (Answer a : resultAnswers) {
                        if (a.getIs_selected()) {
                            /**
                             * Update answer in database
                             */
                            AnswerSQLiteAdapter answerSqliteAdapter = new AnswerSQLiteAdapter(v.getContext());
                            answerSqliteAdapter.open();
                            answerSqliteAdapter.update(a);
                            answerSqliteAdapter.close();
                        }
                    }

                    /**
                     * if c == size of questionsList - 1 , open new activity
                     */
                    if (c == (questionsList.size() - 1)) {
                        postJson();
                    } else {

                        /**
                         * Increment c
                         */
                        c++;

                        linearLayout.removeAllViews();

                        /**
                         * Show next question and answers
                         */
                        resultAnswers = showQuestion(c);
                    }
                } else {
                    Toast.makeText(v.getContext(), SELECT_ANSWER, Toast.LENGTH_SHORT).show();
                }
            }
        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**
                 * Decrement c
                 */
                c--;

                if(c == 0){
                    btBack.setVisibility(View.GONE);
                }

                linearLayout.removeAllViews();

                /**
                 * Show previous question and answers
                 */
                resultAnswers = showQuestion(c);

            }
        });
    }

    public ArrayList<Answer> showQuestion(int c){

        checkboxChecked = false;
        q = questionsList.get(c);

        /**
         * Get answers list by question id
         */
        AnswerSQLiteAdapter answerSQLiteAdapter = new AnswerSQLiteAdapter(this);
        answerSQLiteAdapter.open();
        ArrayList<Answer> answers = answerSQLiteAdapter.getAllByQuestion(q.getId());
        answerSQLiteAdapter.close();

        tvQcmName.setText(q.getQcm().getCategory().getName());
        tvContentQuestion.setText(q.getContent());

        for (final Answer a : answers) {

            /**
             * Create checkboxes
             */
            final CheckBox checkBox = new CheckBox(this.ctx);
            checkBox.setId((int) a.getId());
            checkBox.setText(a.getContent());
            checkBox.setTextSize(getResources().getDimension(R.dimen.font_size_item_list));
            checkBox.setPadding(0, 0, 0, 20);

            linearLayout.addView(checkBox);

            /**
             * OnClickListener of checkbox
             */
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox.isChecked()) {
                        a.setIs_selected(true);
                        checkboxChecked = true;
                    }else{
                        a.setIs_selected(false);
                        checkboxChecked = false;
                    }
                }
            });
        }
        return answers;
    }

    public float calculateNote(long qcmId){
        float note = 0;
        boolean goodAnswer;

        QuestionSQLiteAdapter questionSQLiteAdapter = new QuestionSQLiteAdapter(this);
        questionSQLiteAdapter.open();
        final ArrayList<Question> questions = questionSQLiteAdapter.getAllByQcm(qcmId);
        questionSQLiteAdapter.close();

        float pointTotal = 0;
        for (Question question : questions){
            AnswerSQLiteAdapter answerSQLiteAdapter = new AnswerSQLiteAdapter(this);
            answerSQLiteAdapter.open();
            ArrayList<Answer> answers = answerSQLiteAdapter.getAllByQuestion(question.getId());
            answerSQLiteAdapter.close();

            goodAnswer = true;

            if (answers != null){
                for (Answer answer : answers){
                    pointTotal = pointTotal + answer.getPoint();
                    if (((answer.getIs_valid() == true) && (answer.getIs_selected() == false)) ||
                            ((answer.getIs_valid() == false) && (answer.getIs_selected() == true))) {
                        goodAnswer = false;
                    }
                    if(goodAnswer){
                        note = note+(answer.getPoint());
                    }
                }
            }
        }

        note = (note*20)/pointTotal;

        return note;
    }


    protected void postJson (){

        final ProgressDialog progressDialog = new ProgressDialog(QuestionActivity.this);
        progressDialog.setMessage("Envoi des réponses...");
        progressDialog.show();

        final float note = calculateNote(qcmId);

        sharedpreferences = getSharedPreferences(ConnexionActivity.MyPREFERENCES, MODE_PRIVATE);
        long userId = sharedpreferences.getLong(ConnexionActivity.USERID,0);

        UserWSAdapter userWSAdapter = new UserWSAdapter(this);
        RequestParams params = userWSAdapter.itemToParamsPostQcm(qcmId, userId, note);

        userWSAdapter.postQcm(params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                Toast.makeText(QuestionActivity.this, "ERREUR ENVOI JSON", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                Intent intent = new Intent(QuestionActivity.this, EndPageActivity.class);
                QuestionActivity.this.finish();
                startActivity(intent);
            }
        });
    }

}
