package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int numero1,numero2;
    TextView affichage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void recupererNumeros(View view) {
            EditText champs1, champs2;
            champs1 = (EditText) findViewById(R.id.editText1);
            champs2 = (EditText) findViewById(R.id.editText2);
            affichage = (TextView) findViewById(R.id.textView);
            numero1 = Integer.parseInt(champs1.getText().toString());
            numero2 = Integer.parseInt(champs2.getText().toString());
    }
    public void calculerSomme(View view) {
        try {
            recupererNumeros(view);
            affichage.setText(String.valueOf(numero1 + numero2));
        }
        catch (RuntimeException e){

        }
    }
    public void calculerProduit(View view) {
        try {
            recupererNumeros(view);
            affichage.setText(String.valueOf(numero1 * numero2));
        }
        catch (RuntimeException e) {

        }
    }
    public void calculerSub(View view) {
        try {
            recupererNumeros(view);
            affichage.setText(String.valueOf(numero1 - numero2));
        }
        catch (RuntimeException e) {

        }
    }
}