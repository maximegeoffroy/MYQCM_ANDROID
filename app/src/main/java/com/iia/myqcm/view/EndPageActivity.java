package com.iia.myqcm.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.iia.myqcm.R;

public class EndPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_page);

        Button btHomePage = (Button)this.findViewById(R.id.btHomePage);

        btHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EndPageActivity.this, CategoryListActivity.class);
                startActivity(intent);
            }
        });
    }
}
