package com.goodcompany.group5_finalproject_w2019_mad3125.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.goodcompany.group5_finalproject_w2019_mad3125.Dialogs.MyProgressDialog;
import com.goodcompany.group5_finalproject_w2019_mad3125.Modals.CredentialsLogin;
import com.goodcompany.group5_finalproject_w2019_mad3125.R;
import com.goodcompany.group5_finalproject_w2019_mad3125.Utils.AppNetworkStatus;
import com.goodcompany.group5_finalproject_w2019_mad3125.Utils.ShadowLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.login_sl)
    ShadowLayout loginSl;
    @BindView(R.id.forgot_password_tv)
    TextView forgotPasswordTv;
    @BindView(R.id.input_name)
    EditText inputName;
    @BindView(R.id.input_password)
    EditText inputPassword;
    @BindView(R.id.input_layout_name)
    TextInputLayout inputLayoutName;
    @BindView(R.id.input_layout_password)
    TextInputLayout inputLayoutPassword;
    @BindView(R.id.signup_tv)
    TextView signupTv;
    @BindView(R.id.login_top_tv)
    TextView loginTopTv;
    @BindView(R.id.login_tv)
    TextView loginTv;
    @BindView(R.id.no_account)
    TextView noAccount;
    private Context mContext;
    private FirebaseAuth mAuth;
    public static String TAG = LoginActivity.class.getSimpleName();
    private CredentialsLogin loginCredentials;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        loginCredentials = new CredentialsLogin();
        mContext = LoginActivity.this;
        inputName.addTextChangedListener(new MyTextWatcher(inputName));
        inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));
        addFontToViews();
    }


    private void addFontToViews() {
        loginTopTv.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-DISPLAY-BOLD.OTF"));
        inputName.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-DISPLAY-SEMIBOLD.OTF"));
        inputPassword.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-DISPLAY-SEMIBOLD.OTF"));
        forgotPasswordTv.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-DISPLAY-SEMIBOLD.OTF"));
        signupTv.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-DISPLAY-BOLD.OTF"));
        loginTv.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-DISPLAY-BOLD.OTF"));
        noAccount.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-DISPLAY-BOLD.OTF"));
        inputLayoutName.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-DISPLAY-SEMIBOLD.OTF"));
        inputLayoutPassword.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-DISPLAY-SEMIBOLD.OTF"));
    }


    @OnClick({R.id.forgot_password_tv, R.id.login_sl, R.id.signup_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.forgot_password_tv:
                Intent i = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(i);
                break;
            case R.id.login_sl:
                submitForm();
                break;
            case R.id.signup_tv:
                i = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(i);
                break;
        }
    }

    private boolean validateName() {
        if (inputName.getText().toString().trim().isEmpty()) {
            showMessage("Enter your valid username or email");
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
            loginCredentials.email = inputName.getText().toString().trim();
        }
        return true;
    }

    private boolean validatePassword() {
        if (inputPassword.getText().toString().trim().isEmpty()) {
            showMessage("Enter valid password");
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
            loginCredentials.password = inputPassword.getText().toString().trim();
        }

        return true;
    }


    private void submitForm() {
        if (!validateName()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }

        if (AppNetworkStatus.getInstance(this).isOnline()) {
            if (validateName() && validatePassword()) {
                MyProgressDialog.show(this);
                mAuth.signInWithEmailAndPassword(loginCredentials.getEmail(), loginCredentials.getPassword())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    MyProgressDialog.dismiss();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    MyProgressDialog.dismiss();
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }

                                // ...
                            }
                        });
            }
        } else {
            showMessage("Unable to connect to internet");
        }

    }




    private class MyTextWatcher implements TextWatcher {
        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_name:
                    validateName();
                    break;
                case R.id.input_password:
                    validatePassword();
                    break;
            }
        }
    }

}
