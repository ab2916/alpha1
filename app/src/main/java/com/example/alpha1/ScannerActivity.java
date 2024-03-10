package com.example.alpha1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScannerActivity extends AppCompatActivity {
    Button scan_btn;
    TextView  textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        scan_btn = findViewById(R.id.scanner);
        textView = findViewById(R.id.textScanner);

        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(ScannerActivity.this);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setPrompt("Scan a QR Code");
                intentIntegrator.setDesiredBarcodeFormats(intentIntegrator.QR_CODE);
                intentIntegrator.initiateScan();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(intentResult != null)
        {
            String contents = intentResult.getContents();
            if(contents != null)
            {
                textView.setText(intentResult.getContents());
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}