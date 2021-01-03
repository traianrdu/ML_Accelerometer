package com.example.ml_accelerometer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.common.FirebaseMLException;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager;
import com.google.firebase.ml.custom.FirebaseCustomLocalModel;
import com.google.firebase.ml.custom.FirebaseCustomRemoteModel;
import com.google.firebase.ml.custom.FirebaseModelDataType;
import com.google.firebase.ml.custom.FirebaseModelInputOutputOptions;
import com.google.firebase.ml.custom.FirebaseModelInterpreter;
import com.google.firebase.ml.custom.FirebaseModelInterpreterOptions;


import org.tensorflow.lite.Interpreter;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PredictionPage extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    Sensor accelerometer;
    TextView txt_show, xValue, yValue, zValue;

    StringBuilder data= new StringBuilder();
    int check=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction_page);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // back button on bar

        txt_show = (TextView) findViewById(R.id.textView_pred);

        downloadModel();
        //create_model();
        //txt_show.setText("abs");
        xValue = (TextView) findViewById(R.id.textView1);
        yValue = (TextView) findViewById(R.id.textView2);
        zValue = (TextView) findViewById(R.id.textView3);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        assert sensorManager != null;
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(PredictionPage.this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

    }

    public void startPrediction(View view) {
        initialize_model();
        check = 1;

    }

    public void stopPrediction(View view){
        check = 0;
    }

    public void create_model(){
        FirebaseCustomRemoteModel remoteModel =
                new FirebaseCustomRemoteModel.Builder("model_SDSI").build();
        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder()
                .requireWifi()
                .build();
        FirebaseModelManager.getInstance().download(remoteModel, conditions)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void v) {
                        // Download complete. Depending on your app, you could enable
                        // the ML feature, or switch from the local model to the remote
                        // model, etc.
                        txt_show.setText("1");
                    }
                });
        FirebaseModelManager.getInstance().getLatestModelFile(remoteModel)
                .addOnCompleteListener(new OnCompleteListener<File>() {
                    @Override
                    public void onComplete(@NonNull Task<File> task) {
                        File modelFile = task.getResult();
                        if (modelFile != null) {
                            Interpreter interpreter = new Interpreter(modelFile);
                            double[][] input = {{1, 2, 3}};
                            //float xxxx = 0;
                            //float[] modelOutput = new float[0];

                            Map<Integer, Object>  modelOutput = new HashMap<>();
                            try {
                                //interpreter.runForMultipleInputsOutputs(input, modelOutput);
                                int x=0;
                                txt_show.setText(x);
                            }
                            catch (Exception e) {
                                txt_show.setText((CharSequence) e);
                            }
                        }
                    }
                });
    }

    public void initialize_model(){

        FirebaseCustomRemoteModel remoteModel = new FirebaseCustomRemoteModel.Builder("model_SDSI").build();

        FirebaseModelManager.getInstance().getLatestModelFile(remoteModel)
                .addOnCompleteListener(new OnCompleteListener<File>() {
                    @Override
                    public void onComplete(@NonNull Task<File> task) {
                        File modelFile = task.getResult();
                        if (modelFile != null) {
                            Interpreter interpreter = new Interpreter(modelFile);

                            //double[][] input = {{1, 2, 3}};
                            double[] input = new double[3];
                            double[] modelOutput = new double[1];
                            //txt_show.setText("xssssa");
                            //Map<Integer, Object>  modelOutput = new HashMap<>();
                            try {
                                interpreter.run(input, modelOutput);
                                int x=0;
                                txt_show.setText("x");
                            }
                            catch (Exception e) {
                                txt_show.setText("eroare");
                            }
                            //txt_show.setText((CharSequence) modelOutput);
                            /*

                            modelOutput.rewind();
                            FloatBuffer probabilities = modelOutput.asFloatBuffer();
                            for (int i = 0; i < probabilities.capacity(); i++) {
                                float probability = probabilities.get(i);
                                txt_show.setText((int) probability);
                            }*/
                        }
                        else{
                            txt_show.setText("loading problems");
                        }
                        if(check == 2) {
                            create_model();
                        }
                    }
                });


    }

    private synchronized void downloadModel() {
        final FirebaseCustomRemoteModel remoteModel =
                new FirebaseCustomRemoteModel.Builder("model_SDSI").build();
        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder()
                .requireWifi()
                .build();
        FirebaseModelManager.getInstance().download(remoteModel, conditions)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void v) {
                        // Download complete. Depending on your app, you could enable
                        // the ML feature, or switch from the local model to the remote
                        // model, etc.
                        //txt_show.setText("1");
                    }
                });
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(check == 1) {
            xValue.setText("xValue: " + sensorEvent.values[0]);
            yValue.setText("yValue: " + sensorEvent.values[1]);
            zValue.setText("zValue: " + sensorEvent.values[2]);
            txt_show.setText("Persoana_1 foloseste telefonul");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}