package com.mad_2020_g08_10_2.tasktrackerv01;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class RegisterFragment extends Fragment {



    private DatabaseHelper db ;
    //declaring objects for registration;
    EditText fN, uN, pW, pW2;
    Button b1;

    //Declare String variables
    String s1,s2,s3,s4;






    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        //for registration

        fN = view.findViewById(R.id.et_name);
        uN = view.findViewById(R.id.et_email);
        pW = view.findViewById(R.id.et_password);
        pW2 = view.findViewById(R.id.et_repassword);
        b1 = view.findViewById(R.id.btn_register);

        s1 = fN.getText().toString();


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db= new DatabaseHelper(getActivity());
                s1 = fN.getText().toString();
                s2 = uN.getText().toString();
                s3 = pW.getText().toString();
                s4 = pW2.getText().toString();

                if (s1.equals("") || s2.equals("") || s3.equals("") || s4.equals("")) {
                    Toast.makeText(getContext(), "Fields are empty", Toast.LENGTH_SHORT).show();
                } else {
                    if (s3.contentEquals(s4)) {
                        Boolean chkUN = db.chkUN(s2);
                        if (chkUN == true) {
                            Boolean insert = db.insert(s1, s2, s3);
                            if (insert == true) {
                                Toast.makeText(getContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();

                                //automatically going to login fragment

                            }
                        } else {
                            Toast.makeText(getContext(), "User Name already exist", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getContext(), "Password dose not matching", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return view;
    }

}