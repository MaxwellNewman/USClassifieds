package com.cs310.usclassifieds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cs310.usclassifieds.model.datamodel.User;
import com.cs310.usclassifieds.model.manager.DataManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private String username = "";
    private String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        this.mAuth = FirebaseAuth.getInstance();
    }

    public void signInButtonPressed(View view) {
        this.username = ((EditText)findViewById(R.id.username)).getText().toString();
        this.password = ((EditText)findViewById(R.id.password)).getText().toString();

        System.out.println("in sign in ");

        mAuth.signInWithEmailAndPassword(username + "@usclassifieds.com", password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, give toast, redirect to Main Activity
                            Intent mainIntent = new Intent(SignInActivity.this, MainActivity.class);
                            startActivity(mainIntent);
                            Toast.makeText(SignInActivity.this, "Hello, " + username,
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display an error toast to the user.
                            Toast.makeText(SignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                            final DataManager manager = new DataManager();

                            final User user = new User("user", "email", "myId");
                            manager.addUser(user);

                            final User returnUser = manager.getUser("myId");

                            System.out.println("return: " + returnUser.username);
                        }
                    }
                });
    }

    /** Called when user presses the create user clickable text **/
    public void createUserClickableTextPressed(View view) {
        // Switch to Create User page
        Intent intent = new Intent(this, CreateUserActivity.class);
        startActivity(intent);
    }
}
