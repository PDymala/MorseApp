package com.diplabs.morseapp;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {

    EditText editText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int strtext = getArguments().getInt("timeUnit");


        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        editText = (EditText)v.findViewById(R.id.editTextTimeUnit);
        editText.setText(Integer.toString(strtext));

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0){
                    Toast.makeText(getActivity(), "Type a value", Toast.LENGTH_SHORT).show();
                    passData(strtext);
                } else{
                    passData(Integer.parseInt(s.toString()));
                }

            }
        });



        return v;

//        return inflater.inflate(R.layout.fragment_settings, container,false);



    }

    //https://stackoverflow.com/questions/9343241/passing-data-between-a-fragment-and-its-container-activity
    public void passData(int data) {
        dataPasser.onDataPassSettings(data);
    }

    public interface OnDataPassSettings {
        public void onDataPassSettings(int timeUnit);
    }

    OnDataPassSettings dataPasser;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataPasser = (OnDataPassSettings) context;
    }

}
