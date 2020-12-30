package com.example.ml_accelerometer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager;
import com.google.firebase.ml.custom.FirebaseCustomRemoteModel;

public class PredictionPage extends AppCompatActivity {

    TextView txt_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction_page);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // back button on bar

        txt_show = (TextView) findViewById(R.id.textView_pred);

        downloadModel("model_SDSI");

        //txt_show.setText("abs");
    }

    private synchronized void downloadModel(String modelName) {
        final FirebaseCustomRemoteModel remoteModel =
                new FirebaseCustomRemoteModel
                        .Builder(modelName)
                        .build();
        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder()
                .requireWifi()
                .build();
        FirebaseModelManager.getInstance().download(remoteModel, conditions)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Success.
                        txt_show.setText("Success");
                    }
                });
    }
}