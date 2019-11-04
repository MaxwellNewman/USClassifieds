package com.cs310.usclassifieds;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cs310.usclassifieds.model.datamodel.Item;
import com.cs310.usclassifieds.model.datamodel.Location;
import com.cs310.usclassifieds.model.manager.DataManager;
import com.cs310.usclassifieds.model.manager.SearchManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class SignInActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        this.mAuth = FirebaseAuth.getInstance();

        // Set up stuff so that keyboard goes away when user clicks away
        EditText usernameEditText = (EditText)findViewById(R.id.username);
        EditText passwordEditText = (EditText)findViewById(R.id.password);

        this.editTextFocusChangeSetup(usernameEditText);
        this.editTextFocusChangeSetup(passwordEditText);
    }

    /** Runs when sign in button pressed, contains logic for basic form validation **/
    public void signInButtonPressed(View view) {
        String username = ((EditText)findViewById(R.id.username)).getText().toString();
        String password = ((EditText)findViewById(R.id.password)).getText().toString();

        if (username == null || username.equals("")) {
            Toast.makeText(this, "You need to type in a username.",
                    Toast.LENGTH_SHORT).show();
        } else if (password == null || password.equals("")) {
            Toast.makeText(this, "You need to type in a password.",
                    Toast.LENGTH_SHORT).show();
        } else {
            this.signInUser(username, password);
        }
    }

    /** Called when user presses the create user clickable text **/
    public void createUserClickableTextPressed(View view) {
        // Switch to Create User page
        Intent intent = new Intent(this, CreateUserActivity.class);
        startActivity(intent);
    }

    /** Signs in user through Firebase **/
    private void signInUser(final String username, final String password) {

        mAuth.signInWithEmailAndPassword(username + "@usclassifieds.com", password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, give toast, redirect to Main Activity
                            Intent mainIntent = new Intent(SignInActivity.this, MainActivity.class);
                            startActivity(mainIntent);
                        } else {
                            // If sign in fails, display an error toast to the user.
                            Toast.makeText(SignInActivity.this, "Username and password incorrect. Try again.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /** Hides keyboard **/
    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

     /** Setup so that clicking on editText results in keyboard appearing **/
    private void editTextFocusChangeSetup(EditText editText) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
    }
}
