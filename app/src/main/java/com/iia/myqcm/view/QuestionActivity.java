package com.iia.myqcm.view;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;

public class QuestionActivity extends AppCompatActivity {
    public final String SELECT_ANSWER = "Selectionner au moins une réponse";
    public final String BUTTON_ENVOYER = "Envoyer";

    public ArrayList<Question> questionsList;
    public ArrayList<Answer> resultAnswers;
    public int c = 0;
    public Question q;
    public boolean checkboxChecked = false;

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

        Bundle b = this.getIntent().getExtras();
        long qcmId = b.getLong(QcmListActivity.QCM_ID);

        QuestionSQLiteAdapter questionSQLiteAdapter = new QuestionSQLiteAdapter(this);
        questionSQLiteAdapter.open();
        questionsList = questionSQLiteAdapter.getAllByQcm(qcmId);
        questionSQLiteAdapter.close();

        /**
         * Show question and answers
         */
        if(c <= 0){
            btBack.setClickable(false);
        }

        resultAnswers = showQuestion(c);

        btValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkboxChecked) {

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
                            Toast.makeText(v.getContext(), String.valueOf(a.getId()), Toast.LENGTH_SHORT).show();
                        }
                    }

                    /**
                     * if c == size of questionsList - 1 , open new activity
                     */
                    if (c == (questionsList.size() - 1)) {
                        Intent intent = new Intent(v.getContext(), EndPageActivity.class);
                        startActivity(intent);
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
                }else{
                    Toast.makeText(v.getContext(),SELECT_ANSWER,Toast.LENGTH_SHORT).show();
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

}
