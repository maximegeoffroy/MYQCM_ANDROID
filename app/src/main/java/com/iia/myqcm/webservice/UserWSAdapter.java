package com.iia.myqcm.webservice;

import android.content.Context;

import com.iia.myqcm.data.AnswerSQLiteAdapter;
import com.iia.myqcm.data.CategorySQLiteAdapter;
import com.iia.myqcm.data.GroupSQLiteAdapter;
import com.iia.myqcm.data.QcmSQLiteAdapter;
import com.iia.myqcm.data.QuestionSQLiteAdapter;
import com.iia.myqcm.entity.Answer;
import com.iia.myqcm.entity.Category;
import com.iia.myqcm.entity.Group;
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

    //private static final String BASE_URL = "http://172.20.10.2/myQCM/web/app_dev.php/api";
    public static final String IP = "http://192.168.1.88/";
    private static final String BASE_URL = IP + "/myQCM/web/app_dev.php/api";
    private static final String ENTITY = "users";
    private static final String AUTH = "auth";
    private static final String UPDATE_QCM = "update_qcm";
    private static final String VERSION = "1";
    private static final String ID = "id";
    private static final String USERNAME = "username";
    private static final String NAME = "name";
    private static final String FIRSTNAME = "firstname";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String CREATEDAT = "created_at";
    private static final String UPDATEDAT = "updated_at";
    private static final String USERQCMS = "user_qcms";
    private static final String USERGROUP = "user_group";

    private static AsyncHttpClient client = new AsyncHttpClient();
    private Context ctx;

    /**
     * Constructor of UserWSAdapter
     * @param context
     */
    public UserWSAdapter(Context context) {
        this.ctx = context;
    }

    /**
     * Get user in webservice database
     * @param username
     * @param handler
     */
    public void getUser(String username, AsyncHttpResponseHandler handler){
        String url = String.format("%s/%s/%s", BASE_URL, ENTITY, username);
        client.get(url, handler);
    }

    /**
     * Send username and password to the webservice
     * @param user
     * @param handler
     */
    public void auth(User user, AsyncHttpResponseHandler handler){
        RequestParams params = UserWSAdapter.itemToParams(user);

        String url = String.format("%s/%s", BASE_URL, AUTH);
        client.post(url, params, handler);
    }

    /**
     * Get all users in webservice database
     * @param responseHandler
     */
    public static void getAll(AsyncHttpResponseHandler responseHandler) {
        String url = String.format("%s/%s", BASE_URL, ENTITY);
        client.get(url, responseHandler);
    }

    /**
     * Create RequestParams with username and password of user
     * @param user
     * @return params
     */
    public static RequestParams itemToParams(User user){
        RequestParams params = new RequestParams();
        params.put(USERNAME, user.getUsername());
        params.put(PASSWORD, user.getPassword());

        return params;
    }

    /**
     * Convert DataObject to Params.
     * @param idQcm
     * @param idUser
     * @param note
     * @return RequestParams
     */
    public RequestParams itemToParamsPostQcm(long idQcm, long idUser, float note){
        RequestParams params = new RequestParams();
        params.put("id_qcm", idQcm);
        params.put("id_user", idUser);
        params.put("note", note);

        return params;
    }

    public void postQcm(RequestParams params, AsyncHttpResponseHandler handler){
        String url = String.format("%s/%s", BASE_URL, UPDATE_QCM);
        client.post(url, params, handler);
    }

    /**
     * Convert json to user and add in local database
     * @param json
     * @return User
     */
    public User jsonToItem(JSONObject json) {
        User item = new User();
        item.setUsername(json.optString(USERNAME));
        item.setEmail(json.optString(EMAIL));
        item.setPassword(json.optString(PASSWORD));
        item.setIdServer(json.optInt(ID));
        item.setName(json.optString(USERNAME));
        item.setFirstname(json.optString(FIRSTNAME));

        /**
         * Group
         */
        Date date = new Date();
        try {
            JSONObject userGroup = json.getJSONObject(USERGROUP);
            Group group = new Group();
            group.setIdServer(userGroup.optLong(ID));
            group.setName(userGroup.optString(NAME));
            group.setCreated_at(date);
            group.setUpdated_at(date);

            GroupSQLiteAdapter groupSQLiteAdapter = new GroupSQLiteAdapter(this.ctx);
            groupSQLiteAdapter.open();
            group.setId(groupSQLiteAdapter.insert(group));
            groupSQLiteAdapter.close();

            item.setGroup(group);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray qcmsUserJsonArray = json.optJSONArray(USERQCMS);
        ArrayList<QcmUser> qcmsUserList = new ArrayList<>();
        for (int i = 0; i < qcmsUserJsonArray.length(); i++) {
            try {
                JSONObject qcmsUserObject = qcmsUserJsonArray.getJSONObject(i);
                JSONObject qcmObject = qcmsUserObject.optJSONObject("qcm");

                /**
                 * Category
                 */
                Category category = new Category();
                category.setName(qcmObject.optJSONObject("category").optString("name"));
                category.setCreated_at(date);
                category.setUpdated_at(date);
                category.setIdServer(qcmObject.optJSONObject("category").optLong("id"));

                CategorySQLiteAdapter categoryAdapter = new CategorySQLiteAdapter(this.ctx);
                categoryAdapter.open();

                category.setId(categoryAdapter.insert(category));

                categoryAdapter.close();

                /**
                 * Qcm
                 */
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
                        a.setIs_selected(false);

                        a.setId(answerSQLiteAdapter.insert(a));
                    }
                    answerSQLiteAdapter.close();
                }
                questionSQLiteAdapter.close();

                /**
                 * QCM User
                 */
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
