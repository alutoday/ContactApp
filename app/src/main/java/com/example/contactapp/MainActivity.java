package com.example.contactapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.view.MenuInflater;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.example.contactapp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ActivityMainBinding bindingNew;
    private ArrayList<Contact> contactList;
    private ContactsAdapter contactAdapter;

    private AppDatabase appDatabase;
    private ContactDAO contactDAO;

    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        setSupportActionBar(binding.toolbar);


        binding.rvContacts.setLayoutManager(new LinearLayoutManager(this));
        contactList = new ArrayList<Contact>();
        contactAdapter = new ContactsAdapter(contactList);
        binding.rvContacts.setAdapter(contactAdapter);


        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase = AppDatabase.getInstance(getApplicationContext());
                contactDAO = appDatabase.contactDAO();

                List<Contact> contactsFromDb = contactDAO.getAll();

                contactList.clear();
                contactList.addAll(contactsFromDb);
                contactAdapter.notifyDataSetChanged();

            }
        });
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewViewAdd.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);

        SearchManager searchManager = (SearchManager)  getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                contactAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                contactAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Nhận dữ liệu từ AddContactActivity
            String name = data.getStringExtra("name");
            String mobile = data.getStringExtra("mobile");
            String email = data.getStringExtra("email");

            // Thêm liên hệ mới vào danh sách
            contactList.add(new Contact(name, mobile, email));
            contactAdapter.notifyDataSetChanged();
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    appDatabase = AppDatabase.getInstance(getApplicationContext());
                    contactDAO = appDatabase.contactDAO();

                    contactDAO.insert(new Contact(name, mobile, email));
                }
            });
        }
    }

}