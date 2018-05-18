package com.camsoulsby.oldmate;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

//TODO: Delete unused import statements below

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.database.sqlite.*;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//TODO: Put settings button up in title bar

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;
    FeedReaderDbHelper mDbHelper;
    static final int MAX_NAME_LENGTH = 18;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //FrameLayout fLayout = (FrameLayout) findViewById(R.id.activity_to_do);
        RecyclerView mateView = (RecyclerView)findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mateView.setLayoutManager(linearLayoutManager);
        mateView.setHasFixedSize(true);
        //Create DbHelper
        mDbHelper = new FeedReaderDbHelper(this);

        // Gets the data repository in write mode
        db = mDbHelper.getWritableDatabase();

        List<Mate> allMates = mDbHelper.listMates();
        if(allMates.size() > 0){
            mateView.setVisibility(View.VISIBLE);
            MateAdapter mAdapter = new MateAdapter(this, allMates);
            mateView.setAdapter(mAdapter);
        }else {
            mateView.setVisibility(View.GONE);
            Toast.makeText(this, "There is no product in the database. Start adding now", Toast.LENGTH_LONG).show();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                receiveInputFromDialog(view);
            }
        });



        TextWatcher inputTextWatcher = new TextWatcher() {
            public void afterTextChanged(Editable s) {
                showResults();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        };

        EditText editTextSearchFor = (EditText)findViewById(R.id.editTextSearchFor);
      //  TODO: Work out how this works and make sure search is implemented
        editTextSearchFor.addTextChangedListener(inputTextWatcher);
        showResults();

    }

    public void onResume(){
        super.onResume();
        // refresh data when user presses back etc
        showResults();
    }

    public void receiveInputFromDialog (View view) {
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        View promptView = layoutInflater.inflate(R.layout.add_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setView(promptView);

    final EditText editText = (EditText) promptView.findViewById(R.id.edit_text_name);
    editText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(MAX_NAME_LENGTH) });

        alertDialogBuilder.setCancelable(false)
         .setPositiveButton("OK", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id){
            addMate(editText.getText().toString());
        }
         })
         .setNegativeButton("Cancel",
                 new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int id) {
                         dialog.cancel();
                     }
                 });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();

        alert.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        alert.show();

    }

    public void addMate(String entryName){

        //TODO: Get this working so mates are actually added!
        String metDate;
        String unformattedMetDate;
        Date date = new Date();
        unformattedMetDate = date.toString();

        //convert today's date into a sortable format

        Date formattedDate = new Date();

        try {
            SimpleDateFormat parser = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");

            formattedDate = parser.parse(unformattedMetDate);
        } catch (java.text.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        metDate = dateFormat.format(formattedDate);

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_NAME, entryName);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DATE_MET, metDate);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_NOTES, "");

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);

        String stringID = String.valueOf(newRowId);

        Intent intent = new Intent(this, ViewEditMateActivity.class);
        intent.putExtra("newRowId",newRowId);
        startActivity(intent);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void showResults() {
        // keep showResults simple for now...

        //TODO: Explore OldmateFirebase to understand how results were displayed using recyclerview.
        //TODO: Find simple way to test what is acutally being stored in database
        //TODO: Make alterations to addMate if data is not being stored.
        //TODO: Create class for Mate
        //TODO: Create card layout and work out how to iterate through database and add a card to the layout for each Mate

        //TODO: set onclicklisteners for each card which pass ref to ViewEditMateActivity
        //TODO: create layout for ViewEdit
        //TODO: Work out how to add general location to entry automatically
        //TODO: Work out how to back up to drive
        //TODO: Look through requirements for publishing app and create more items
        //TODO: Get emulator working
        //TODO: Test on other devices

        //TODO: design logos and artwork as very last step before launching

    }
        /**

        // Minimise the keyboard when search button is clicked
          //View view2 = this.getCurrentFocus();
        // if (view2 != null) {
         //     InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
          //    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        //  }


        // Remove previous search results when search button is clicked

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.relativeLayoutResults);
        layout.removeAllViews();

        // TODO: Add logic in here to search by other stuff

        EditText editTextSearchFor = (EditText)findViewById(R.id.editTextSearchFor);
        String searchFor = editTextSearchFor.getText().toString();


        // Define a projection that specifies which columns from the database you will actually use after this query.

        String[] projection = {
                FeedReaderContract.FeedEntry._ID,
                FeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_NAME,
                FeedReaderContract.FeedEntry.COLUMN_NAME_DATE_MET,
                FeedReaderContract.FeedEntry.COLUMN_NAME_NOTES
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = FeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_NAME + " LIKE ? " + "OR " + FeedReaderContract.FeedEntry.COLUMN_NAME_NOTES+ " LIKE ? " ;
        String[] selectionArgs = {"%" + searchFor + "%","%" + searchFor + "%"};


        // How you want the results sorted in the resulting Cursor

        String sortOrder =
                FeedReaderContract.FeedEntry.COLUMN_NAME_DATE_MET + " DESC";

        Cursor cursor = db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );



        String entryName = "";
        Long rowId;
        String metDate = "";

        int i = 0;
        int prevId = 0;
        String prevMetDate = "";
        while(cursor.moveToNext()) {
            i = i+1;

            entryName = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_NAME));
            metDate = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_DATE_MET));
            rowId = cursor.getLong(cursor.getColumnIndex(FeedReaderContract.FeedEntry._ID));

            Date formattedDate = new Date();

            try {
                SimpleDateFormat parser = new SimpleDateFormat("yyyyMMdd");


                formattedDate = parser.parse(metDate);
            } catch (java.text.ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }



            DateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
            String currentDateString = dateFormat.format(formattedDate);



            //For each unique metDate, create a label in the scroll view

            if (!currentDateString.equals(prevMetDate)){


                Log.d("Tag", "Dates not Equal, current = " + currentDateString + " prev = " + prevMetDate);

                prevMetDate=currentDateString;

                TextView tv = new TextView(this);
                RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                if(i > 1) {
                    params.addRule(RelativeLayout.BELOW, prevId);
                }

                tv.setLayoutParams(params);
                tv.setGravity(Gravity.CENTER_HORIZONTAL);
                tv.setTextSize(15);




                tv.setText( currentDateString );
                int id = 1000000+i;
                tv.setId(id);
                layout.addView(tv);
                prevId=tv.getId();
            }

            final Button btnTag = new Button(this);
            RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

            //prevId will be the first date label when I =0
            params.addRule(RelativeLayout.BELOW, prevId);

            btnTag.setLayoutParams(params);

            btnTag.setTextSize(20);
            btnTag.setAllCaps(false);
            btnTag.setGravity(Gravity.LEFT);

            btnTag.setText(entryName);

            btnTag.setId(i);



            final Long id = rowId;

            btnTag.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.i("TAG", "The index is" + id);

                    Intent intent = new Intent(MainActivity.this, ViewEditMateActivity.class);
                    intent.putExtra("newRowId",id);
                    startActivity(intent);
                }
            });

            layout.addView(btnTag);
            prevId = btnTag.getId();
        }

        cursor.close();

    }
     */

    @Override
    protected void onDestroy() {
        mDbHelper.close();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }
}
