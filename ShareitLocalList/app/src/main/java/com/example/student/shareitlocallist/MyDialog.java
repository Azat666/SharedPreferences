package com.example.student.shareitlocallist;

import android.annotation.SuppressLint;
import android.app.DialogFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class MyDialog extends DialogFragment {


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        @SuppressLint({"NewApi", "LocalSuppress"})

        final View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_item, container, false);
        setListener(view);
        return view;
    }

    private void setListener(final View view) {
        final EditText editName = view.findViewById(R.id.edit_name);
        final EditText editSurname = view.findViewById(R.id.edit_surname);
        final Button addButton = view.findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"NewApi", "CommitPrefEdits"})
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).getList().add(new ListRegister(editName.getText().toString(), editSurname.getText().toString()));
                dismiss();
            }
        });
    }
}
