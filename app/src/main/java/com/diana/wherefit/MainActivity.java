package com.diana.wherefit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showAll(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        //EditText editText = (EditText) findViewById(R.id.button_all);
        //String message = editText.getText().toString();
        String message = "dianka";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
