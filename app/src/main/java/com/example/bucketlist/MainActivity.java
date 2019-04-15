package com.example.bucketlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements RecyclerView.OnItemTouchListener {

    private EntryRoomDatabase db;
    private Executor executor = Executors.newSingleThreadExecutor();
    private RecyclerView mRecyclerView;
    private EntryAdapter mAdapter;
    private List<Entry> mEntries;
    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recycler);
        mEntries = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //divider line thing, seems to not work (maybe should be somewhere else?)
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        mRecyclerView.addOnItemTouchListener(this);
        db = EntryRoomDatabase.getDatabase(this);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEntryActivity.class);
                startActivityForResult(intent, 9999);
            }
        });

        mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                View child = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null) {
                    int adapterPosition = mRecyclerView.getChildAdapterPosition(child);
                    deleteEntry(mEntries.get(adapterPosition));
                }
            }
            @Override
            public boolean onSingleTapUp (MotionEvent e){
                return true;
            }
        });

        getAllEntries();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void updateUI() {
        if (mAdapter == null) {
            mAdapter = new EntryAdapter(mEntries);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.swapList(mEntries);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 9999) {
            if (resultCode == RESULT_OK) {
                Entry newEntry = data.getParcelableExtra("entry");
                insertEntry(newEntry);
            }
        }
    }

    private void getAllEntries() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mEntries = db.entryDao().getAllEntries();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateUI();
                    }
                });
            }
        });
    }

    private void insertEntry(final Entry entry) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.entryDao().insertEntry(entry);
                getAllEntries();
            }
        });
    }

    private void updateEntry(final Entry entry) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.entryDao().updateEntry(entry);
                getAllEntries();
            }
        });
    }

    private void deleteEntry(final Entry entry) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.entryDao().deleteEntry(entry);
                getAllEntries();
            }
        });
    }

    private void deleteAllEntries(final List<Entry> entries) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.entryDao().deleteAll(entries);
                getAllEntries();
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
        View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
        int adapterPosition = mRecyclerView.getChildAdapterPosition(child);
        if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                Entry touchedEntry = mEntries.get(adapterPosition);
                if (touchedEntry.getEntryStrikeThru() == 0) {
                    touchedEntry.setEntryStrikeThru(1);
                } else if (touchedEntry.getEntryStrikeThru() == 1) {
                    touchedEntry.setEntryStrikeThru(0);
                }
                updateEntry(touchedEntry);
        }
        return false;
    }

    @Override

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_delete_item) {
            deleteAllEntries(mEntries);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) {

    }
}
