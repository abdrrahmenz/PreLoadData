package com.expert.andro.mypreloaddata;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.expert.andro.mypreloaddata.db.MahasiswaHelper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        AppPreference appPreference = new AppPreference(this);

        Boolean firtsRun = appPreference.getFirstRun();

        if (firtsRun) {
            new LoadData().execute();
        }else {
            startActivity(new Intent(MainActivity.this, MahasiswaActivity.class));
            finish();
        }
    }

    private class LoadData extends AsyncTask<Void,Integer, Void>{

        MahasiswaHelper mahasiswaHelper;
        AppPreference appPreference;
        double progress;
        double maxprogress = 100;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mahasiswaHelper = new MahasiswaHelper(MainActivity.this);
            appPreference = new AppPreference(MainActivity.this);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Boolean firtsRun = appPreference.getFirstRun();
            Log.d("FIRST RUN", "doInBackground: "+firtsRun);
            if (firtsRun){
                ArrayList<MahasiswaModel> listMahasiswa = preLoadRaw();
                Log.d("size", " "+listMahasiswa.size());
                progress = 30;

                publishProgress((int)progress);

                mahasiswaHelper.open();

                Double progressMaxInsert = 80.0;
                Double progressDiff = (progressMaxInsert - progress) / listMahasiswa.size();

                mahasiswaHelper.insertTransaction(listMahasiswa);

//                for (MahasiswaModel model : listMahasiswa){
//                    mahasiswaHelper.insert(model);
//                    progress += progressDiff;
//
//                    publishProgress((int) progress);
//                }

                mahasiswaHelper.close();
                appPreference.setFirstRun(false);

                publishProgress((int) maxprogress);
            } else {
                try {
                    synchronized (this) {
                        this.wait(2000);

                        publishProgress(50);
                        this.wait();
                        publishProgress((int)maxprogress);
                    }
                }catch (Exception e){

                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Intent i = new Intent(MainActivity.this, MahasiswaActivity.class);
            startActivity(i);
            finish();
        }
    }

    public ArrayList<MahasiswaModel> preLoadRaw(){
        ArrayList<MahasiswaModel> mahasiswaModels = new ArrayList<>();
        String line = null;
        BufferedReader reader;

        try {
            Resources res = getResources();
            InputStream raw_dict = res.openRawResource(R.raw.data_mahasiswa);
            reader = new BufferedReader(new InputStreamReader(raw_dict));
            int count = 0;
            do {
                line = reader.readLine();
                String[] splistr = line.split("\t");
                MahasiswaModel mahasiswaModel;
                mahasiswaModel = new MahasiswaModel(splistr[0],splistr[1]);
                mahasiswaModels.add(mahasiswaModel);
                count++;

            } while (line != null);
        }catch (Exception e){
            e.printStackTrace();
        }
        return mahasiswaModels;
    }
}
