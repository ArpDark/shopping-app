package com.example.shoppingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;


import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
//import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.example.shoppingapp.databinding.ActivityLoginBinding;
import com.example.shoppingapp.LoginActivity;

public class LoginActivity extends AppCompatActivity
{

    TextInputLayout emailLayout,passLayout;
    TextInputEditText emailInput,passInput;
    String email,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding;
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        passLayout= binding.layoutPassword1;
        passInput=binding.inputPassword1;
        emailLayout= binding.layoutEmail1;
        emailInput=binding.inputEmail1;

        binding.btnLogIn1.setOnClickListener(view -> {

            email=String.valueOf(emailInput.getText());
            pass=String.valueOf(passInput.getText());

            emailLayout.setError(null);
            passLayout.setError(null);
            if(valid())
            {
                FirebaseAuth auth= FirebaseAuth.getInstance();
                auth.signInWithEmailAndPassword(email,pass)
                        .addOnCompleteListener(task -> {
                            if(task.isSuccessful())
                            {
                                FirebaseUser fUser= FirebaseAuth.getInstance().getCurrentUser();

                                if(fUser!=null)
                                {
                                    StoreInfo obj=new StoreInfo(LoginActivity.this);
                                    obj.storeId(auth.getUid());

                                    Toast.makeText(LoginActivity.this, "Sign In Successful",
                                            Toast.LENGTH_SHORT).show();
                                    Intent r =new Intent(LoginActivity.this,BottomNavActivity.class);
                                    startActivity(r);
                                    LoginActivity.this.finish();
                                }
                                else
                                {
                                    Toast.makeText(LoginActivity.this, "Sign In Failed",
                                            Toast.LENGTH_SHORT).show();
                                }

                            }
                        })
                        .addOnFailureListener(e -> Toast.makeText(this, e.getMessage(),
                                Toast.LENGTH_SHORT).show());
            }


        });
    }


    private boolean valid()
    {
        if(email.isEmpty())
        {
            emailLayout.setError("Enter email");
        }
        if(pass.isEmpty())
        {
            passLayout.setError("Enter password");
        }
        else
        {
            return true;
        }
        return false;
    }
}
