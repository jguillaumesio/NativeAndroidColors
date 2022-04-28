package com.example;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{

    TextView tvRGBA, tvCouleur;
    SeekBar r_input, g_input, b_input, a_input;
    Button update_button, delete_button;

    int old_r, old_g, old_b, old_a;
    int r, g, b, a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        a_input = findViewById( R.id.sbAlpha );
        a_input.setOnSeekBarChangeListener((SeekBar.OnSeekBarChangeListener) this);

        r_input = findViewById( R.id.sbRouge );
        r_input.setOnSeekBarChangeListener((SeekBar.OnSeekBarChangeListener) this);

        g_input = findViewById( R.id.sbVert );
        g_input.setOnSeekBarChangeListener((SeekBar.OnSeekBarChangeListener) this);

        b_input = findViewById( R.id.sbBleu );
        b_input.setOnSeekBarChangeListener((SeekBar.OnSeekBarChangeListener) this);

        tvRGBA = findViewById( R.id.tvARGB );
        tvCouleur = findViewById( R.id.tvCouleur);

        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);

        //First we call this
        getAndSetIntentData();

        //Set actionbar title after getAndSetIntentData method
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Modifier la couleur");
        }

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                r = r_input.getProgress();
                g = g_input.getProgress();
                b = b_input.getProgress();
                a = a_input.getProgress();
                myDB.updateData(old_r, old_g, old_b, old_a, r, g, b, a);
                old_r = r;
                old_g = g;
                old_b = b;
                old_a = a;
            }
        });
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });

    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("r") && getIntent().hasExtra("g") && getIntent().hasExtra("b") && getIntent().hasExtra("a")){
            //Getting Data from Intent
            r = Integer.valueOf(getIntent().getStringExtra("r"));
            g = Integer.valueOf(getIntent().getStringExtra("g"));
            b = Integer.valueOf(getIntent().getStringExtra("b"));
            a =Integer.valueOf(getIntent().getStringExtra("a"));

            old_r = Integer.valueOf(getIntent().getStringExtra("r"));
            old_g = Integer.valueOf(getIntent().getStringExtra("g"));
            old_b = Integer.valueOf(getIntent().getStringExtra("b"));
            old_a =Integer.valueOf(getIntent().getStringExtra("a"));

            r_input.setProgress(r);
            g_input.setProgress(g);
            b_input.setProgress(b);
            a_input.setProgress(a);
        }else{
            Toast.makeText(this, "Aucune couleur", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Supprimer rgba("+r+","+g+","+b+","+a+") ?");
        builder.setMessage("Êtes-vous sûr de vouloir supprimer rgba("+r+","+g+","+b+","+a+") ?");
        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteOneRow(r,g,b,a);
                finish();
            }
        });
        builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

    public void onSeekBarUpdate( ) {
        int a = a_input.getProgress();
        int r = r_input.getProgress();
        int v = g_input.getProgress();
        int b = b_input.getProgress();
        String texteCouleur = "RGBA("+ r + " , " + v + " , " + b +","+ a +")";
        tvRGBA.setText( texteCouleur);
        tvCouleur.setBackgroundColor(Color.argb( a , r, v ,b ));
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        onSeekBarUpdate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
