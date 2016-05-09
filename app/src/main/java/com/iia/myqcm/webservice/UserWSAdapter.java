package com.iia.myqcm.webservice;

import android.content.Context;

import com.iia.myqcm.data.AnswerSQLiteAdapter;
import com.iia.myqcm.data.CategorySQLiteAdapter;
import com.iia.myqcm.data.QcmSQLiteAdapter;
import com.iia.myqcm.data.QuestionSQLiteAdapter;
import com.iia.myqcm.entity.Answer;
import com.iia.myqcm.entity.Category;
import com.iia.myqcm.entity.Qcm;
import com.iia.myqcm.entity.QcmUser;
import com.iia.myqcm.entity.Question;
import com.iia.myqcm.entity.User;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by gemax on 13/04/2016.
 */
public class UserWSAdapter {

    private static final String BASE_URL = "http://172.20.10.2/myQCM/web/app_dev.php/api";
    private static final String ENTITY = "users";
    //private static final String ENTITY = "title/test1/isbn/123456";
    private static final String VERSION = "1";
    private static AsyncHttpClient client = new AsyncHttpClient();
    private Context ctx;

    private static final String USERNAME = "username";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String CREATEDAT = "created_at";
    private static final String UPDATEDAT = "updated_at";
    private static final String USERQCMS = "user_qcms";

    public UserWSAdapter(Context context) {
        this.ctx = context;
    }

    public void getUser(String username, AsyncHttpResponseHandler handler){
        String url = String.format("%s/%s/%s", BASE_URL, ENTITY, username);
        client.get(url, handler);
    }

    public static void getAll(AsyncHttpResponseHandler responseHandler) {
        String url = String.format("%s/%s", BASE_URL, ENTITY);
        client.get(url, responseHandler);
    }

    public User jsonToItem(JSONObject json) {
        User item = new User();
        item.setUsername(json.optString(USERNAME));
        item.setEmail(json.optString(EMAIL));
        item.setPassword(json.optString(PASSWORD));
        //Recover qcmsUser
        JSONArray qcmsUserJsonArray = json.optJSONArray(USERQCMS);
        ArrayList<QcmUser> qcmsUserList = new ArrayList<>();
        Date date = new Date();
        for (int i = 0; i < qcmsUserJsonArray.length(); i++) {
            try {
                JSONObject qcmsUserObject = qcmsUserJsonArray.getJSONObject(i);
                JSONObject qcmObject = qcmsUserObject.optJSONObject("qcm");

                //Category
                Category category = new Category();
                category.setName(qcmObject.optJSONObject("category").optString("name"));
                category.setCreated_at(date);
                category.setUpdated_at(date);
                category.setIdServer(qcmObject.optJSONObject("category").optLong("id"));

                CategorySQLiteAdapter categoryAdapter = new CategorySQLiteAdapter(this.ctx);
                categoryAdapter.open();

                category.setId(categoryAdapter.insert(category));

                categoryAdapter.close();

                //QCM
                Qcm qcm = new Qcm();
                qcm.setCategory(category);
                qcm.setDuration(qcmObject.optInt("duration"));
                qcm.setName(qcmObject.optString("name"));
                qcm.setStart_date(date);
                qcm.setEnd_date(date);
                qcm.setCreated_at(date);
                qcm.setUpdated_at(date);
                qcm.setIdServer(qcmObject.optLong("id"));

                QcmSQLiteAdapter qcmAdapter = new QcmSQLiteAdapter(this.ctx);
                qcmAdapter.open();

                qcm.setId(qcmAdapter.insert(qcm));

                qcmAdapter.close();

                //Qcm questions
                JSONArray questionsArray = qcmObject.optJSONArray("questions");
                QuestionSQLiteAdapter questionSQLiteAdapter = new QuestionSQLiteAdapter(this.ctx);
                questionSQLiteAdapter.open();
                for(int j=0; j<questionsArray.length(); j++){
                    JSONObject qObject = questionsArray.getJSONObject(j);
                    Question q = new Question();
                    q.setContent(qObject.optString("content"));
                    q.setCreated_at(date);
                    q.setUpdated_at(date);
                    q.setQcm(qcm);
                    q.setIdServer(qObject.optLong("id"));

                    q.setId(questionSQLiteAdapter.insert(q));

                    //ANSWER QUESTIONS
                    JSONArray answersArray = qObject.optJSONArray("answers");
                    AnswerSQLiteAdapter answerSQLiteAdapter = new AnswerSQLiteAdapter(this.ctx);
                    answerSQLiteAdapter.open();
                    for(int k=0; k<answersArray.length(); k++){
                        JSONObject answerObject = answersArray.getJSONObject(k);
                        Answer a = new Answer();
                        a.setContent(answerObject.optString("content"));
                        a.setPoint(answerObject.optInt("point"));
                        a.setIs_valid(answerObject.optBoolean("is_valid"));
                        a.setQuestion(q);
                        a.setCreated_at(date);
                        a.setUpdated_at(date);
                        a.setIdServer(answerObject.optLong("id"));

                        a.setId(answerSQLiteAdapter.insert(a));
                    }
                    answerSQLiteAdapter.close();
                }
                questionSQLiteAdapter.close();

                //qcmsUser
                QcmUser qcmUser = new QcmUser();
                qcmUser.setQcm(qcm);
                qcmUser.setUser(item);
                //manque note + is done

                //Add qcmUser at qcmsUser list
                qcmsUserList.add(qcmUser);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        item.setQcmsUser(qcmsUserList);
        //item.setCreated_at(json.op(CREATEDAT));
        //item.setCreated_at(json.op(CREATEDAT));



        return item;
    }
}
//    public static JSONObject itemToJson(Book item){
//        JSONObject result = new JSONObject();
//
//        try {
//            if(item.getTitle() != null){
//                result.put(TITLE, item.getTitle());
//            }
//            if(item.getIsbn() != null){
//                result.put(ISBN, item.getIsbn());
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return result;
//    }
//
//    public static RequestParams itemToParams(Book item){
//        RequestParams params = new RequestParams();
//        params.put(TITLE, item.getTitle());
//        params.put(ISBN, item.getIsbn());
//
//        return params;
//    }
//
//}
