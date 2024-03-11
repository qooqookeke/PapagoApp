package com.qooke.papagoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.qooke.papagoapp.adapter.TextAdapter;
import com.qooke.papagoapp.model.History;

import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextAdapter adapter;
    ArrayList<History> historyArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // 액션바 보이기
        getSupportActionBar().setTitle("History");

        // 메인 액티비티에서 보낸 데이터 받기
        historyArrayList = (ArrayList<History>) getIntent().getSerializableExtra("historyArrayList");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(AddActivity.this));

        adapter = new TextAdapter(AddActivity.this, historyArrayList); // implements Serializable(직렬화)해서 어레이리스트의 데이터 뽑아오기
        recyclerView.setAdapter(adapter);


    }
}