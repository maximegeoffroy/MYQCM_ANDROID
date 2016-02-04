package com.iia.myqcm.view;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.iia.myqcm.R;
import com.iia.myqcm.data.CategorySQLiteAdapter;
import com.iia.myqcm.entity.Category;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by gemax on 01/02/2016.
 */
public class CategoryCursorAdapter extends CursorAdapter {

    public CategoryCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    public CategoryCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.row_category, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvName = (TextView)view.findViewById(R.id.tvName);
        TextView tvDate = (TextView) view.findViewById(R.id.tvDate);

        Category c = CategorySQLiteAdapter.cursorToItem(cursor);

        //CONVERT DATE TO STRING
        /*DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = df.format(c.getCreated_at());*/

        tvName.setText(c.getName());
        //tvDate.setText(dateString);
    }
}
