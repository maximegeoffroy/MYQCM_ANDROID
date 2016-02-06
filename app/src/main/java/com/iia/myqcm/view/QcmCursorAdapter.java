package com.iia.myqcm.view;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.iia.myqcm.R;
import com.iia.myqcm.data.CategorySQLiteAdapter;
import com.iia.myqcm.data.QcmSQLiteAdapter;
import com.iia.myqcm.entity.Category;
import com.iia.myqcm.entity.Qcm;

/**
 * Created by gemax on 04/02/2016.
 */
public class QcmCursorAdapter extends CursorAdapter {
    /**
     * Constructor that allows control over auto-requery.  It is recommended
     * you not use this, but instead {@link #CursorAdapter(Context, Cursor, int)}.
     * When using this constructor, {@link #FLAG_REGISTER_CONTENT_OBSERVER}
     * will always be set.
     *
     * @param context     The context
     * @param c           The cursor from which to get the data.
     * @param autoRequery If true the adapter will call requery() on the
     *                    cursor whenever it changes so the most recent
     */
    public QcmCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    /**
     * Recommended constructor.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     * @param flags   Flags used to determine the behavior of the adapter; may
     *                be any combination of {@link #FLAG_AUTO_REQUERY} and
     *                {@link #FLAG_REGISTER_CONTENT_OBSERVER}.
     */
    public QcmCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    /**
     * Makes a new view to hold the data pointed to by cursor.
     *
     * @param context Interface to application's global information
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.row_qcm, parent, false);
    }

    /**
     * Bind an existing view to the data pointed to by cursor
     *
     * @param view    Existing view, returned earlier by newView
     * @param context Interface to application's global information
     * @param cursor  The cursor from which to get the data. The cursor is already
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvName = (TextView)view.findViewById(R.id.tvNameQcm);
        QcmSQLiteAdapter qcmSQLiteAdapter = new QcmSQLiteAdapter(context);
        Qcm q = qcmSQLiteAdapter.cursorToItem(cursor);

        tvName.setText(q.getName());
    }
}
