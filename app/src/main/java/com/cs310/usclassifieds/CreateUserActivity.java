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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CreateUserActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        mAuth = FirebaseAuth.getInstance();

        // Set up stuff so that keyboard goes away when user clicks away
        EditText usernameEditText = (EditText)findViewById(R.id.createUsername);
        EditText passwordEditText = (EditText)findViewById(R.id.createPassword);
        EditText emailEditText = (EditText)findViewById(R.id.createEmail);
        EditText phoneNumberEditText = (EditText)findViewById(R.id.createPhoneNumber);
        EditText profileDescriptionEditText = (EditText)findViewById(R.id.createProfileDescription);

        this.editTextFocusChangeSetup(usernameEditText);
        this.editTextFocusChangeSetup(passwordEditText);
        this.editTextFocusChangeSetup(emailEditText);
        this.editTextFocusChangeSetup(phoneNumberEditText);
        this.editTextFocusChangeSetup(profileDescriptionEditText);
    }

    /** Called when user presses Create User Button **/
    public void createUserButtonPressed(View view) {
        String username = ((EditText)findViewById(R.id.createUsername)).getText().toString();
        String password = ((EditText)findViewById(R.id.createPassword)).getText().toString();
        String email = ((EditText)findViewById(R.id.createEmail)).getText().toString();
        String phoneNumber = ((EditText)findViewById(R.id.createPhoneNumber)).getText().toString();
        String profileDescription = ((EditText)findViewById(R.id.createProfileDescription)).getText().toString();

        if (username == null || username.equals("")) {
            Toast.makeText(this, "You need to type in a username.",
                    Toast.LENGTH_SHORT).show();
        } else if (password == null || password.equals("")) {
            Toast.makeText(this, "You need to type in a password.",
                    Toast.LENGTH_SHORT).show();
        } else if (email == null || !isValidUscEmail(email)) {
            Toast.makeText(this, "You need to type in a valid @usc.edu email.",
                    Toast.LENGTH_SHORT).show();
        } else {
            this.createUser(username, password, email, phoneNumber, profileDescription);
        }
    }

    /** Called when user presses the sign in clickable text **/
    public void signInClickableTextPressed(View view) {
        // Switch to Sign in page
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    /** Checks if an email is a valid usc email (ends in @usc.edu) **/
    private boolean isValidUscEmail(String email) {
        String[] parts = email.split("@");
        return parts.length == 2 && parts[1].equals("usc.edu");
    }

    private void createUser(String username, String password, String email,
                            String phoneNumber, String profileDescription) {

        mAuth.createUserWithEmailAndPassword(username + "@usclassifieds.com", password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, go to app (MainActivity)
                            Intent mainIntent = new Intent(CreateUserActivity.this, MainActivity.class);
                            startActivity(mainIntent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(CreateUserActivity.this, "That username already exists.",
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
