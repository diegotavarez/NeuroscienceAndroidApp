package luc.edu.neuroscienceapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import luc.edu.neuroscienceapp.R;

public class MainActivity extends AppCompatActivity {

    Button image_button;
    Button sound_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image_button = (Button) findViewById(R.id.image_button);
        sound_button = (Button) findViewById(R.id.sound_button);

        image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Clicked image button", Toast.LENGTH_SHORT).show();
                Intent image_intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(image_intent);

            }
        });
        sound_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Clicked sound button", Toast.LENGTH_SHORT).show();

            }
        });
    }




}
