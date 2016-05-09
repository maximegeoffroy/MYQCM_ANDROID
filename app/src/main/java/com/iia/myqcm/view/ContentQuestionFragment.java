package com.iia.myqcm.view;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iia.myqcm.R;
import com.iia.myqcm.data.AnswerSQLiteAdapter;
import com.iia.myqcm.data.AnswerUserSQLiteAdapter;
import com.iia.myqcm.data.QuestionSQLiteAdapter;
import com.iia.myqcm.entity.Answer;
import com.iia.myqcm.entity.AnswerUser;
import com.iia.myqcm.entity.Question;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class ContentQuestionFragment extends Fragment {
    public final String SELECT_ANSWER = "Selectionner au moins une réponse";
    public final String NO_QUESTIONS = "Plus de questions";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_content_question, container, false);
        final LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linearLayoutContainer);
        final TextView tvContentQuestion = (TextView) view.findViewById(R.id.tvContentQuestion);
        final TextView tvQcmName = (TextView) view.findViewById(R.id.tvQcmName);
        final Button btValidate = (Button) view.findViewById(R.id.btValidate);

        Bundle b = getActivity().getIntent().getExtras();
        long qcmId = b.getLong(QcmListActivity.QCM_ID);

        QuestionSQLiteAdapter questionSQLiteAdapter = new QuestionSQLiteAdapter(view.getContext());
        questionSQLiteAdapter.open();
        final ArrayList<Question> questionsList = questionSQLiteAdapter.getAllByQcm(qcmId);
        questionSQLiteAdapter.close();

        final Question q = questionsList.get(0);

        tvQcmName.setText(q.getQcm().getCategory().getName());
        tvContentQuestion.setText(q.getContent());

        AnswerSQLiteAdapter answerSQLiteAdapter = new AnswerSQLiteAdapter(view.getContext());
        answerSQLiteAdapter.open();
        final ArrayList<Answer> answers = answerSQLiteAdapter.getAllByQuestion(q.getId());
        answerSQLiteAdapter.close();

        final ArrayList<Integer> checkBoxes = new ArrayList<>();
        final HashMap<Long, ArrayList<Integer>> responsesUser = new HashMap<>();
        if (answers.size() > 1) {
            for (int i = 0; i < answers.size(); i++) {
                final AppCompatCheckBox c = new AppCompatCheckBox(view.getContext());
                c.setId((int) answers.get(i).getId());
                c.setText(answers.get(i).getContent());
                c.setTextSize(getResources().getDimension(R.dimen.font_size_item_list));
                c.setPadding(0,0,0,20);

                linearLayout.addView(c);

                c.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkBoxes.contains(c.getId())) {
                            int pos = checkBoxes.indexOf(c.getId());
                            checkBoxes.remove(pos);
                        } else {
                            checkBoxes.add(c.getId());
                        }
                    }
                });
            }
        }
        btValidate.setOnClickListener(new View.OnClickListener() {
            int compteur = 1;
            @Override
            public void onClick(View v) {
                if(checkBoxes.size() > 1){
                    AnswerUserSQLiteAdapter answerUserSQLiteAdapter = new AnswerUserSQLiteAdapter(view.getContext());
                    answerUserSQLiteAdapter.open();
                    for(Integer i: checkBoxes){
                        AnswerUser answerUser = new AnswerUser();
                        answerUser.setAnswer_id(i);
                        answerUser.setQuestion_id(q.getId());

                        answerUser.setId(answerUserSQLiteAdapter.insert(answerUser));
                    }
                    answerUserSQLiteAdapter.close();
                }
                //Stocke les réponses de l'utilisateur
                responsesUser.put(q.getId(), checkBoxes);

                checkBoxes.clear();
                linearLayout.removeAllViews();
                while (compteur < questionsList.size()) {
                    //CHARGEMENT DE LA QUESTION SUIVANTE
                    Question q = questionsList.get(compteur);

                    tvQcmName.setText(q.getQcm().getCategory().getName());
                    tvContentQuestion.setText(q.getContent());

                    AnswerSQLiteAdapter answerSQLiteAdapter = new AnswerSQLiteAdapter(v.getContext());
                    answerSQLiteAdapter.open();
                    ArrayList<Answer> answers = answerSQLiteAdapter.getAllByQuestion(q.getId());
                    answerSQLiteAdapter.close();

                    if (answers.size() > 1) {
                        for (int i = 0; i < answers.size(); i++) {
                            final AppCompatCheckBox c = new AppCompatCheckBox(view.getContext());
                            c.setId((int) answers.get(i).getId());
                            c.setText(answers.get(i).getContent());
                            c.setTextSize(getResources().getDimension(R.dimen.font_size_item_list));
                            c.setPadding(0,0,0,20);

                            linearLayout.addView(c);

                            c.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (checkBoxes.contains(c.getId())) {
                                        int pos = checkBoxes.indexOf(c.getId());
                                        checkBoxes.remove(pos);
                                    } else {
                                        checkBoxes.add(c.getId());
                                    }
                                }
                            });
                        }
                        compteur++;

                        if(compteur == questionsList.size()){
                            btValidate.setText("Envoyer");
                            Toast.makeText(v.getContext(), NO_QUESTIONS, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        // Inflate the layout for this fragment
        return view;
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
