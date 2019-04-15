package com.example.bucketlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;

public class AddEntryActivity extends AppCompatActivity {

    private EditText mNewTitle;
    private EditText mNewDescription;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        mButton = findViewById(R.id.button);
        mNewTitle = findViewById(R.id.editText);
        mNewDescription = findViewById(R.id.editText2);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mNewTitle.getText().toString();
                String description = mNewDescription.getText().toString();
                if (!(TextUtils.isEmpty(title)) &&
                        !(TextUtils.isEmpty(description))) {
                    Entry newEntry = new Entry(0, title, description);
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("entry", newEntry);
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                } else {
                    Snackbar.make(v, R.string.emptyaddentry, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }
}
