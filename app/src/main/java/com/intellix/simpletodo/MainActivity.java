package com.intellix.simpletodo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.intellix.simpletodo.R.id.lvItems;

public class MainActivity extends Activity {

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = (ListView)findViewById(R.id.lvItems);
        //items = new ArrayList<>();
        readItems();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
       // items.add("First Item");
       // items.add("Second Item");
        setupListViewListener();
    }

    private void setupListViewListener()
    {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
                //remove item from the list when someone longclicks (holds) on an item
                @Override
                public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id)
                {
                    items.remove(pos);
                    itemsAdapter.notifyDataSetChanged();
                    writeItems();
                    return true;
                }
            });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                // use position to find your values
                // to go to ShowDetailsActivity, you have to use Intent
                Intent editItemScreen = new Intent(getApplicationContext(), EditItemActivity.class);

                //create a bundle and pass selected item and items array to be modified on next screen
                String selectedItem = lvItems.getItemAtPosition(position).toString();
                Bundle b = new Bundle();
                b.putString("item", selectedItem);
                b.putInt("position", position);

                //b.putStringArrayList("itemArray", items);
                editItemScreen.putExtras(b);
                //fire the subactiviity and wait for it to complete
                startActivityForResult(editItemScreen, REQUEST_CODE);
            }
        });
    }
    public void onAddItem(View v)
    {
        EditText etNewItem = (EditText)findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
    }

    // time to handle the result of the sub-activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract item and position from result extras
            String modifiedItem = data.getExtras().getString("item");
            int position = data.getExtras().getInt("position", 0);

            //update items with new value
            items.set(position, modifiedItem);

            //fire notification to reflect new value in list display
            itemsAdapter.notifyDataSetChanged();

            //write new values to fi
            writeItems();
        }
    }

    private void readItems()
    {
        File filesDir = getFilesDir();
        File todoFile =  new File(filesDir, "todo.txt");
        try
        {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e)
        {
            items = new ArrayList<String>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try
        {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
