package com.bit603.a3;

/**
* BIT603 Assignemnt 3
*
* Name: Ryan McArthur
* ID: 5105426
* Created 23/05/2026
* Main Activity class, for logging in.
*/

import static com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.CustomCredential;
import androidx.credentials.exceptions.GetCredentialException;
import androidx.credentials.CredentialManager;

import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;

import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption;
import java.util.concurrent.Executors;

import com.google.android.gms.common.SignInButton;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    CredentialManager credentialManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.videoDescription), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();
        credentialManager = CredentialManager.create(getBaseContext());

        SignInButton signInButton = findViewById(R.id.signinButton);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInOnClick();
            }
        });

        // skip button
        Button buttonName = (Button)findViewById(R.id.skipButton);
        buttonName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotToNavHub();
            }
        });

    }

    private void gotToNavHub(){
        Intent intent = new Intent(this, NavigationHub.class);
        intent.putExtra("Skipped", true);
        startActivity(intent);
    }

    private void signInOnClick() {
        //Get the web client ID from resources
        String serverClientID = getString(R.string.web_client_id);
        //Instantiate a Google Sign-In request using the google sign in button flow
        GetSignInWithGoogleOption signInWithGoogleOption = new GetSignInWithGoogleOption.Builder(serverClientID)
                .build();
        //Create the credential manager request
        GetCredentialRequest request = new GetCredentialRequest.Builder()
                .addCredentialOption(signInWithGoogleOption).build();
        // Launch Credential Manager UI

        credentialManager.getCredentialAsync(
                getBaseContext(),
                request,
                new CancellationSignal(),
                Executors.newSingleThreadExecutor(),

                new CredentialManagerCallback<GetCredentialResponse, GetCredentialException>() {
                    @Override
                    public void onResult(GetCredentialResponse result) {
                        handleSignIn(result.getCredential());
                    }

                    @Override
                    public void onError(GetCredentialException e) {
                        Log.e( "MAIN_LOGIN", "Couldn't retrieve user's credentials: " + e.getLocalizedMessage());
                    }
                }
        );

    }


    /*
    private void handleSignIn(androidx.credentials.Credential credential) {
        // Check if credential is of type Google ID
        Log.d("MAIN_LOGIN", "credential type: " + credential.getType());
        Log.d("MAIN_LOGIN", "equals check: " + credential.getType().equals(TYPE_GOOGLE_ID_TOKEN_CREDENTIAL));
        Log.d("MAIN_LOGIN", "TYPE constant: " + TYPE_GOOGLE_ID_TOKEN_CREDENTIAL);


        if (credential.getType().equals("286963781155-19hb4pldmdn9l5llileimo046psh4i23.apps.googleusercontent.com")) {

            CustomCredential customCredential = (CustomCredential) credential;
            // Create Google ID Token
            Bundle credentialData = customCredential.getData();
            GoogleIdTokenCredential googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credentialData);

            // Sign in to Firebase using the token
            firebaseAuthWithGoogle(googleIdTokenCredential.getIdToken());
            Log.d("MAIN_LOGIN","Logged in with Firebase");
        } else {
            //showAlert("Incorrect credential", "Credential is not a type of Google ID!");
            Log.w("MAIN_LOGIN", "Credential is not of type Google ID!");
            Log.w("MAIN_LOGIN", "Credential type was: " + credential.getType());
            Log.w("MAIN_LOGIN", "Expected type: " + TYPE_GOOGLE_ID_TOKEN_CREDENTIAL);
        }
    }*/

    private void handleSignIn(androidx.credentials.Credential credential) {
        Log.d("MAIN_LOGIN", "credential type: " + credential.getType());
        Log.d("MAIN_LOGIN", "equals check: " + credential.getType().equals(TYPE_GOOGLE_ID_TOKEN_CREDENTIAL));
        Log.d("MAIN_LOGIN", "TYPE constant: " + TYPE_GOOGLE_ID_TOKEN_CREDENTIAL);

        if (credential.getType().equals(TYPE_GOOGLE_ID_TOKEN_CREDENTIAL)) {
            Log.d("MAIN_LOGIN", "inside if block");
            CustomCredential customCredential = (CustomCredential) credential;
            Bundle credentialData = customCredential.getData();
            GoogleIdTokenCredential googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credentialData);
            firebaseAuthWithGoogle(googleIdTokenCredential.getIdToken());
        } else {
            Log.w("MAIN_LOGIN", "failed equals check");
        }
    }

    public void onStart() {
        super.onStart();
        // Check if user is already signed in
        if (mAuth.getCurrentUser() != null) {
            Log.d("MAIN_LOGIN", "User is already signed in: " + mAuth.getCurrentUser().getEmail());
            // Proceed to the next activity
            Intent intent = new Intent(getApplicationContext(),NavigationHub.class);
            startActivity(intent);
        } else {
            //showAlert("No user signed in", "No user signed in.");
            Log.d("MAIN_LOGIN", "No user is signed in.");
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d("MAIN_LOGIN", "signInWithCredential:success");
                        // navigate to next activity
                        Intent intent = new Intent(getApplicationContext(), NavigationHub.class);
                        startActivity(intent);
                    } else {
                        showAlert("Sign-in failure", "Sign-in failure");
                        Log.w("MAIN_LOGIN", "signInWithCredential:failure", task.getException());
                    }
                });
    }

    public void showAlert(String title, String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle(title);
        builder1.setMessage(message);
        builder1.setCancelable(true);
        builder1.setNeutralButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }



}