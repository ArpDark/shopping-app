package com.example.shoppingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.shoppingapp.databinding.ActivityMainBinding;
//import com.example.shoppingapp.models.UserDetails;
import com.example.shoppingapp.models.UserDetails;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity
{
    //private ActivityMainBinding binding; or like line no. 16
    TextInputLayout nameLayout,phoneLayout,passLayout,emailLayout;
    TextInputEditText nameInput,phoneInput,passInput,emailInput;
    String name,pass,email,mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
         ActivityMainBinding binding;
        super.onCreate(savedInstanceState);  //executing super class method

        binding = ActivityMainBinding.inflate(getLayoutInflater()); //creating the instance of binding attachment
        setContentView(binding.getRoot());
        //map the views of the layout with the variables

        if(!new StoreInfo(MainActivity.this).getId().isEmpty())
        {
            startActivity(new Intent(MainActivity.this,BottomNavActivity.class));
            MainActivity.this.finish();
        }

        nameLayout=binding.layoutName;
        nameInput= binding.inputName;
        phoneLayout=binding.layoutPhone;
        phoneInput= binding.inputPhone;
        passLayout= binding.layoutPassword;
        passInput=binding.inputPassword;
        emailLayout= binding.layoutEmail;
        emailInput=binding.inputEmail;

        //adding the event for the button
        binding.btnSignUp.setOnClickListener(view -> {
            //get the values as user input
            name=String.valueOf(nameInput.getText());
            mobile=String.valueOf(phoneInput.getText());
            email=String.valueOf(emailInput.getText());
            pass=String.valueOf(passInput.getText());

            //nullifying error portion
            nameLayout.setError(null);
            phoneLayout.setError(null);
            emailLayout.setError(null);
            passLayout.setError(null);

            if(doValidation())
            {
                FirebaseAuth auth=FirebaseAuth.getInstance();
                auth.createUserWithEmailAndPassword(email,pass)
                        .addOnSuccessListener(authResult -> {
                            if(authResult!=null)
                            {
                                String userId=authResult.getUser().getUid();//saving the userId
                                saveDetails(userId);
                            }
                        })
                        .addOnFailureListener(e -> Toast.makeText(this, e.getMessage(),
                                Toast.LENGTH_SHORT).show());
            }
        });
        binding.btnLogIn.setOnClickListener(view -> {
            Intent route2 = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(route2);
            MainActivity.this.finish();
        });
    }

    private void saveDetails(String userId)
    {
        UserDetails userDetails=new UserDetails(name,userId,mobile, email,"");

        FirebaseFirestore fs=FirebaseFirestore.getInstance();
        CollectionReference ref=fs.collection("User_details");//creating collection if not present
        ref.document(userId)
                .set(userDetails)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(MainActivity.this, "Account Created Successfully",
                                Toast.LENGTH_SHORT).show();
                        //navigate to different page
                        Intent route =new Intent(MainActivity.this,BottomNavActivity.class);
                        startActivity(route);
                        //destroy the current activity & context
                        MainActivity.this.finish();
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Sorry process not completed. Try again!",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e ->
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private boolean doValidation()
    {
        if(name.isEmpty())
            nameLayout.setError("Enter your Name");
        else if(mobile.trim().length()!=10)
        {
            phoneLayout.setError("Enter valid mobile no.");
        }
        else if(pass.isEmpty())
        {
            passLayout.setError("Enter password");
        }
        else if(email.isEmpty())
        {
            emailLayout.setError("Enter email");
        }
        else
        {
            return true;
        }
        return false;
    }
}
