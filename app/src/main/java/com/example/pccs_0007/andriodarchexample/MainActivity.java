package com.example.pccs_0007.andriodarchexample;




import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.pccs_0007.andriodarchexample.addItem.AddActivity;
import com.example.pccs_0007.andriodarchexample.addItem.AddBorrowViewModel;
import com.example.pccs_0007.andriodarchexample.db.BorrowModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnLongClickListener{

    private BorrowedListviewModel viewModel;
    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;

    Button test;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddActivity.class));
            }
        });


        recyclerView = findViewById(R.id.recyclerView);
        recyclerViewAdapter = new RecyclerViewAdapter(new ArrayList<BorrowModel>(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerViewAdapter);

        viewModel   = ViewModelProviders.of(this).get(BorrowedListviewModel.class);
        viewModel.getItemAndPersonList().observe(this, new Observer<List<BorrowModel>>() {
            @Override
            public void onChanged(@Nullable List<BorrowModel> borrowModels) {
                 recyclerViewAdapter.addItems(borrowModels);

            }
        });


        test.setVisibility(View.GONE);
        test = findViewById(R.id.test_button);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddBorrowViewModel addBorrowViewModel = ViewModelProviders.of(MainActivity.this).get(AddBorrowViewModel.class);
                addBorrowViewModel.addBorrow(new BorrowModel("book","ramu",new Date(12,12,2018)));

            }
        });




    }

    @Override
    public boolean onLongClick(View v) {
        BorrowModel borrowModel = (BorrowModel) v.getTag();
        viewModel.deleteItem(borrowModel);
        return true;
    }
}
