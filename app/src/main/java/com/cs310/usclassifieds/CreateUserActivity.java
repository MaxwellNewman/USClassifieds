package com.cs310.usclassifieds;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cs310.usclassifieds.model.datamodel.User;
import com.cs310.usclassifieds.model.datamodel.Contact;
import com.cs310.usclassifieds.model.datamodel.User;
import com.cs310.usclassifieds.model.manager.DataManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.cs310.usclassifieds.model.manager.UserManager;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;


public class CreateUserActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private UserManager userManager = new UserManager(new DataManager()); // Used for saving create account info

    private Button uploadButton;

    private Uri mImageUri;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        mAuth = FirebaseAuth.getInstance();

        // Set up stuff so that keyboard goes away when user clicks away
        EditText fullNameEditText = (EditText)findViewById(R.id.createName);
        EditText usernameEditText = (EditText)findViewById(R.id.createUsername);
        EditText passwordEditText = (EditText)findViewById(R.id.createPassword);
        EditText emailEditText = (EditText)findViewById(R.id.createEmail);
        EditText phoneNumberEditText = (EditText)findViewById(R.id.createPhoneNumber);
        EditText profileDescriptionEditText = (EditText)findViewById(R.id.createProfileDescription);
        uploadButton = findViewById(R.id.upload_profile_picture_button);

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFile();
            }
        });

        this.editTextFocusChangeSetup(fullNameEditText);
        this.editTextFocusChangeSetup(usernameEditText);
        this.editTextFocusChangeSetup(passwordEditText);
        this.editTextFocusChangeSetup(emailEditText);
        this.editTextFocusChangeSetup(phoneNumberEditText);
        this.editTextFocusChangeSetup(profileDescriptionEditText);
    }

    private void selectFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == -1 && data != null
                && data.getData() != null) {
            mImageUri = data.getData();
        }
    }

    /** Called when user presses Create User Button **/
    public void createUserButtonPressed(View view) {
        String fullName = ((EditText)findViewById(R.id.createName)).getText().toString();
        String username = ((EditText)findViewById(R.id.createUsername)).getText().toString();
        String password = ((EditText)findViewById(R.id.createPassword)).getText().toString();
        String email = ((EditText)findViewById(R.id.createEmail)).getText().toString();
        String phoneNumber = ((EditText)findViewById(R.id.createPhoneNumber)).getText().toString();
        String profileDescription = ((EditText)findViewById(R.id.createProfileDescription)).getText().toString();

        if (fullName == null || fullName.equals("")) {
            Toast.makeText(this, "You need to type in a name.",
                    Toast.LENGTH_SHORT).show();
        } else if (username == null || username.equals("")) {
            Toast.makeText(this, "You need to type in a username.",
                    Toast.LENGTH_SHORT).show();
        } else if (password == null || password.equals("")) {
            Toast.makeText(this, "You need to type in a password.",
                    Toast.LENGTH_SHORT).show();
        } else if (email == null || !isValidUscEmail(email)) {
            Toast.makeText(this, "You need to type in a valid @usc.edu email.",
                    Toast.LENGTH_SHORT).show();
        } else {
            this.createUser(fullName, username, password, email, phoneNumber, profileDescription);
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

    private void createUser(final String fullName, final String username, final String password, final String email,
                            final String phoneNumber, final String profileDescription) {
        mAuth.createUserWithEmailAndPassword(username + "@usclassifieds.com", password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Save user to database
                            String userId = task.getResult().getUser().getUid();
                            User newUser = new User(userId, fullName, username, email, phoneNumber, profileDescription, mImageUri);
                            userManager.addUser(newUser);

                            // Go to app (MainActivity)
                            Intent mainIntent = new Intent(CreateUserActivity.this, MainActivity.class);
                            newUser.imageUri = null;
                            mainIntent.putExtra(MainActivity.CURRENT_USER, newUser);
                            startActivity(mainIntent);
                        } else if(task.getException() instanceof FirebaseAuthUserCollisionException){
                            // If sign in fails, display a message to the user.
                            Toast.makeText(CreateUserActivity.this, "That username already exists.",
                                    Toast.LENGTH_SHORT).show();
                        } else if(task.getException() instanceof FirebaseAuthWeakPasswordException){
                            Toast.makeText(CreateUserActivity.this, "That Password is not strong enough",
                                    Toast.LENGTH_SHORT).show();
                        } else if(task.getException() instanceof FirebaseAuthInvalidCredentialsException)
                            Toast.makeText(CreateUserActivity.this, "Please enter a well-formed username",
                                    Toast.LENGTH_SHORT).show();
                        else {
                            Toast.makeText(CreateUserActivity.this, "Miscellaneous sign in error",
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
