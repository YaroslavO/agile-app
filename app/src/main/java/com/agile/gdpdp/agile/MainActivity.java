package com.agile.gdpdp.agile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageButton createSprintButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addListenerOnButton();
    }

    private void addListenerOnButton() {
        createSprintButton = (ImageButton) findViewById(R.id.createSprintButton);

        createSprintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,
                        "CreateSprintButton is clicked!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
