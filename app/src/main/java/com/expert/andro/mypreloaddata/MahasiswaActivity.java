package com.expert.andro.mypreloaddata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;

import com.expert.andro.mypreloaddata.adapter.MahasiswaAdapter;
import com.expert.andro.mypreloaddata.db.MahasiswaHelper;

import java.util.ArrayList;

public class MahasiswaActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SearchView edt_search;
    ArrayList<MahasiswaModel> mahasiswaModels;
    MahasiswaAdapter mahasiswaAdapter;
    MahasiswaHelper mahasiswaHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa);

        recyclerView = (RecyclerView) findViewById(R.id.recylerview);
        edt_search = (SearchView) findViewById(R.id.sv);
        mahasiswaAdapter = new MahasiswaAdapter(this);
        mahasiswaHelper = new MahasiswaHelper(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(mahasiswaAdapter);

        edt_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.length()>0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    mahasiswaHelper.open();
                    mahasiswaModels = mahasiswaHelper.getData(newText);

                    mahasiswaHelper.close();

                    mahasiswaAdapter.addItem(mahasiswaModels);
                } else {
                    recyclerView.setVisibility(View.GONE);
                }
                return false;
            }
        });
    }
}
