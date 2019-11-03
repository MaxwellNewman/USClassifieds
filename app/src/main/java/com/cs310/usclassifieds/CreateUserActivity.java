package com.cs310.usclassifieds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreateUserActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private String username = "";
    private String password = "";
    private String email = "";
    private String phoneNumber = "";
    private String profileDescription = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        mAuth = FirebaseAuth.getInstance();
    }

    /** Called when user presses Create User Button **/
    public void createUserButtonPressed(View view) {
        this.username = ((EditText)findViewById(R.id.createUsername)).getText().toString();
        this.password = ((EditText)findViewById(R.id.createPassword)).getText().toString();
        this.email = ((EditText)findViewById(R.id.createEmail)).getText().toString();
        this.phoneNumber = ((EditText)findViewById(R.id.createPhoneNumber)).getText().toString();
        this.profileDescription = ((EditText)findViewById(R.id.createProfileDescription)).getText().toString();

        mAuth.createUserWithEmailAndPassword(username + "@usclassifieds.com", password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Intent mainIntent = new Intent(CreateUserActivity.this, MainActivity.class);
                            mainIntent.putExtra("USERNAME", username);
                            startActivity(mainIntent);
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(CreateUserActivity.this, "That username already exists.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    /** Called when user presses the sign in clickable text **/
    public void signInClickableTextPressed(View view) {
        // Switch to Sign in page
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }
}
