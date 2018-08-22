package com.example.student.shareitlocallist;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<ListRegister> list = new ArrayList<>();
    private AdapterRegister adapterRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addFab();

        if (getArrayList() != null) {
            list = getArrayList();
        }
        setRecycler();
    }


    private void swipeToDeleteLeft(final RecyclerView recyclerView) {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT) {
                    openDialog(position);
                }
            }
        };
        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }


    private void swipeToDeleteRight(final RecyclerView recyclerView) {
        final ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                list.remove(position);
                adapterRegister.notifyDataSetChanged();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void openDialog(final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(getString(R.string.message_del));

        builder.setPositiveButton(getString(R.string.remove), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                adapterRegister.notifyItemRemoved(position);
                list.remove(position);
            }
        }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                adapterRegister.notifyItemRemoved(position + 1);
                adapterRegister.notifyItemRangeChanged(position, adapterRegister.getItemCount());
            }
        }).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveArrayList(list);
    }

    private void setRecycler() {
        final RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterRegister = new AdapterRegister(this, list);
        recyclerView.setAdapter(adapterRegister);
        swipeToDeleteLeft(recyclerView);
        swipeToDeleteRight(recyclerView);
    }


    private void addFab() {
        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDialog myDialog = new MyDialog();
                myDialog.show(getFragmentManager(), "tag");
                adapterRegister.notifyDataSetChanged();
            }
        });
    }


    protected List<ListRegister> getList() {
        return list;
    }

    private void saveArrayList(List<ListRegister> list) {
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        final Gson gson = new Gson();
        final String json = gson.toJson(list);
        editor.putString("List", json);
        editor.apply();
    }

    private ArrayList<ListRegister> getArrayList() {
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final Gson gson = new Gson();
        final String json = prefs.getString("List", null);
        final Type type = new TypeToken<ArrayList<ListRegister>>() {
        }.getType();
        return gson.fromJson(json, type);
    }
}
