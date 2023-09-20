package com.example.breathingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnGo;
    RadioButton children,adult,seniorCitizen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        children = findViewById(R.id.children);
        adult = findViewById(R.id.Adult);
        seniorCitizen = findViewById(R.id.seniorcitizen);
        btnGo = findViewById(R.id.btn_go);
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result = "Select Your Age: ";
                result += (children.isChecked()) ? "Children" : (adult.isChecked()) ? "Adult" : (seniorCitizen.isChecked()) ? "SeniorCitizen" : "";
                startActivity(new Intent(MainActivity.this,BreathingActivity.class));
            }
        });

    }
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        String str="";
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.children:
                if(checked)
                    str = "Children Selected";
                break;
            case R.id.Adult:
                if(checked)
                    str = "Adult Selected";
                break;
            case R.id.seniorcitizen:
                if(checked)
                    str = "SeniorCitizen  Selected";
                break;
        }
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

}