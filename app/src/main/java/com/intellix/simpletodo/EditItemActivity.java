package com.intellix.simpletodo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

import static com.intellix.simpletodo.R.id.etEditItem;
import static com.intellix.simpletodo.R.id.etNewItem;
import static com.intellix.simpletodo.R.id.lvItems;

public class EditItemActivity extends Activity {

    //ArrayList<String> items;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);


        Bundle b = this.getIntent().getExtras();
        String itemSelected = b.getString("item");
        EditText etEditItem = (EditText)findViewById(R.id.etEditItem);
        etEditItem.setText(itemSelected);
        position = b.getInt("position");
    }

    public void onSave(View v) {
        EditText etEditItem = (EditText)findViewById(R.id.etEditItem);
        // Prepare data intent
        Intent data = new Intent();
        // Pass relevant data back as a result
        data.putExtra("item", etEditItem.getText().toString());
        data.putExtra("position", position);

        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response

        finish(); // closes the activity, pass data to parent
    }

}
