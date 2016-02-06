package com.iia.myqcm.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.iia.myqcm.R;
import com.iia.myqcm.data.QuestionSQLiteAdapter;
import com.iia.myqcm.entity.Question;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ContentQuestionFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content_question, container, false);

        TextView tvContentQuestion = (TextView) view.findViewById(R.id.tvContentQuestion);
        TextView tvQcmName = (TextView) view.findViewById(R.id.tvQcmName);

        Bundle b = getActivity().getIntent().getExtras();
        long qcmId = b.getLong(QcmListActivity.QCM_ID);

        QuestionSQLiteAdapter questionSQLiteAdapter = new QuestionSQLiteAdapter(view.getContext());
        questionSQLiteAdapter.open();

        ArrayList<Question> questionsList = questionSQLiteAdapter.getAllByQcm(qcmId);

        tvContentQuestion.setText(questionsList.get(0).getContent());
        tvQcmName.setText(questionsList.get(0).getQcm().getCategory().getName());

        questionSQLiteAdapter.close();

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
