package com.example.projetandroid;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference usersRef;
    private FirebaseAuth mAuth;
    private Button RegisterBtn;
    private EditText email;
    private EditText password;
    private EditText nom;
    private EditText nappartement;
    private EditText nimmeuble;
    private TextView loginredirect;
    private String emailStr;
    private String passwordStr;
    private String nomStr;
    private String nappartementStr;
    private String nimmeubleStr;

    private String loginredirectStr;
    FirebaseUser user;
    String userStr;

//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(intent);
//            finish();
//
//        }
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("users");

        RegisterBtn = findViewById(R.id.buttonregister);
        email = findViewById(R.id.email);
        password = findViewById(R.id.editTextTextPassword);
        nom = findViewById(R.id.nom);
        nappartement = findViewById(R.id.nappartement);
        nimmeuble = findViewById(R.id.nimmeuble);
        loginredirect=findViewById(R.id.loginRedirectText);

        loginredirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });


        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailStr = email.getText().toString();
                passwordStr = password.getText().toString();
                nomStr = nom.getText().toString();
                nappartementStr = nappartement.getText().toString();
                nimmeubleStr = nimmeuble.getText().toString();

                Log.d(TAG, emailStr + passwordStr + nomStr + nappartementStr + nimmeubleStr);


                mAuth.createUserWithEmailAndPassword(emailStr, passwordStr)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.w(TAG, "createUserWithEmail:Success");
                                    Toast.makeText(Register.this, "Authentication Successful.",
                                            Toast.LENGTH_SHORT).show();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    saveUserInfoToDatabase(user.getUid(), emailStr, nomStr, nappartementStr, nimmeubleStr);
                                    Intent intent = new Intent(getApplicationContext(), Login.class);
                                    startActivity(intent);
                                    finish();
//
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(Register.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
//
                                }
                            }

                            private void saveUserInfoToDatabase(String userId, String email, String nom, String nappartement, String nimmeuble) {
                                DatabaseReference currentUserRef = usersRef.child(userId);
                                currentUserRef.child("email").setValue(email);
                                currentUserRef.child("nom").setValue(nom);
                                currentUserRef.child("nappartement").setValue(nappartement);
                                currentUserRef.child("nimmeuble").setValue(nimmeuble);
                            }
                        });
            }

        });

    }
}



