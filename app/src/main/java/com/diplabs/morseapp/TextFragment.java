package com.diplabs.morseapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TextFragment extends Fragment {

    Button buttonOn;
    EditText editTextInput;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text, container,false);

        buttonOn = (Button) view.findViewById(R.id.buttonOn);
        editTextInput = (EditText) view.findViewById(R.id.editTextInput);



        buttonOn.setOnClickListener(v -> {


            if (editTextInput.getText().length() == 0){

            } else{

                if (isRunning){
                    isRunning  = false;
                    passDataText(" ");
                    buttonOn.setText("ON");
                    editTextInput.setEnabled(true);



                } else{

                    isRunning = true;
                    passDataText(editTextInput.getText().toString());
                    buttonOn.setText("OFF");
                    editTextInput.setEnabled(false);
                }
            }


        });





        return view;
    }
        boolean isRunning = false;




    public void passDataText(String data) {
        dataPasser.onDataPassText(data);
    }

    public interface OnDataPassTextTranslated {
        public void onDataPassText(String translatedData);
    }

    OnDataPassTextTranslated dataPasser;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataPasser = (OnDataPassTextTranslated) context;
    }






}
