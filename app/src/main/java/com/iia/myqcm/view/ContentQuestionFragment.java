package com.iia.myqcm.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class ContentQuestionFragment extends Fragment {
    public final String SELECT_ANSWER = "Selectionner au moins une réponse";
    public final String BUTTON_ENVOYER = "Envoyer";

    public ArrayList<Question> questionsList;
    public ArrayList<Answer> resultAnswers;
    public int c = 0;
    public Question q;
    public boolean checkboxChecked = false;

    public View view;
    public LinearLayout linearLayout;
    public TextView tvContentQuestion;
    public TextView tvQcmName;
    public Button btValidate;
    public Button btBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_content_question, container, false);
        linearLayout = (LinearLayout) view.findViewById(R.id.linearLayoutContainer);
        tvContentQuestion = (TextView) view.findViewById(R.id.tvContentQuestion);
        tvQcmName = (TextView) view.findViewById(R.id.tvQcmName);
        btValidate = (Button) view.findViewById(R.id.btValidate);
        btBack = (Button) view.findViewById(R.id.btBack);

        Bundle b = getActivity().getIntent().getExtras();
        long qcmId = b.getLong(QcmListActivity.QCM_ID);

        QuestionSQLiteAdapter questionSQLiteAdapter = new QuestionSQLiteAdapter(view.getContext());
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
                        Intent intent = new Intent(view.getContext(), EndPageActivity.class);
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

        // Inflate the layout for this fragment
        return view;
    }

    public ArrayList<Answer> showQuestion(int c){

        checkboxChecked = false;
        q = questionsList.get(c);

        /**
         * Get answers list by question id
         */
        AnswerSQLiteAdapter answerSQLiteAdapter = new AnswerSQLiteAdapter(view.getContext());
        answerSQLiteAdapter.open();
        ArrayList<Answer> answers = answerSQLiteAdapter.getAllByQuestion(q.getId());
        answerSQLiteAdapter.close();

        tvQcmName.setText(q.getQcm().getCategory().getName());
        tvContentQuestion.setText(q.getContent());

        for (final Answer a : answers) {

            /**
             * Create checkboxes
             */
            final CheckBox checkBox = new CheckBox(view.getContext());
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

    /**
     * Receive the result from a previous call to
     * {@link #startActivityForResult(Intent, int)}.  This follows the
     * related Activity API as described there in
     * {@link Activity#onActivityResult(int, int, Intent)}.
     *
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode  The integer result code returned by the child activity
     *                    through its setResult().
     * @param data        An Intent, which can return result data to the caller
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
