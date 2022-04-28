package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class AddActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{

    SeekBar r_input, g_input, b_input, a_input;
    TextView tvRGBA, tvCouleur;
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        a_input = findViewById( R.id.sbAlpha );
        a_input.setOnSeekBarChangeListener(this);

        r_input = findViewById( R.id.sbRouge );
        r_input.setOnSeekBarChangeListener(this);

        g_input = findViewById( R.id.sbVert );
        g_input.setOnSeekBarChangeListener(this);

        b_input = findViewById( R.id.sbBleu );
        b_input.setOnSeekBarChangeListener(this);

        tvRGBA = findViewById(R.id.tvRGBA);
        tvCouleur = findViewById(R.id.tvCouleur);

        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);
                myDB.addColor(r_input.getProgress(), g_input.getProgress(), b_input.getProgress(), a_input.getProgress());
            }
        });
    }

    public void onSeekBarUpdate( ) {
        int a = a_input.getProgress();
        int r = r_input.getProgress();
        int g = g_input.getProgress();
        int b = b_input.getProgress();
        String texteCouleur = "RGBA("+ r + " , " + g + " , " + b +","+ a +")";
        tvRGBA.setText( texteCouleur);
        tvCouleur.setBackgroundColor(Color.argb(a, r, g, b));
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
