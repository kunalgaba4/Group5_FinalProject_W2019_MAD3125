package com.goodcompany.group5_finalproject_w2019_mad3125.Activities;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.goodcompany.group5_finalproject_w2019_mad3125.Dialogs.MyProgressDialog;
import com.goodcompany.group5_finalproject_w2019_mad3125.Modals.CredentialsLogin;
import com.goodcompany.group5_finalproject_w2019_mad3125.R;
import com.goodcompany.group5_finalproject_w2019_mad3125.Utils.AppNetworkStatus;
import com.goodcompany.group5_finalproject_w2019_mad3125.Utils.ShadowLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignupActivity extends BaseActivity {

    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.input_layout_name)
    TextInputLayout inputLayoutName;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.input_layout_email)
    TextInputLayout inputLayoutEmail;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.input_layout_password)
    TextInputLayout inputLayoutPassword;
    @BindView(R.id.confirm_pass)
    EditText confirmPass;
    @BindView(R.id.input_layout_conf_password)
    TextInputLayout inputLayoutConfPassword;
    @BindView(R.id.sign_up_tv)
    TextView signUpTv;
    @BindView(R.id.sign_up_tv_sl)
    ShadowLayout signUpTvSl;
    @BindView(R.id.signin_sl)
    ShadowLayout signinSl;
    @BindView(R.id.no_account)
    TextView noAccount;
    @BindView(R.id.signup_top_tv)
    TextView signupTopTv;
    @BindView(R.id.signIn_tv)
    TextView signInTv;
    private CredentialsLogin loginCredentials;
    private Context mContext;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        firebaseAuth = FirebaseAuth.getInstance();
        mContext = this;
        loginCredentials = new CredentialsLogin();
        username.addTextChangedListener(new MyTextWatcher(username));
        password.addTextChangedListener(new MyTextWatcher(password));
        confirmPass.addTextChangedListener(new MyTextWatcher(confirmPass));
        email.addTextChangedListener(new MyTextWatcher(email));
        addFontToTheView();
    }

    private void addFontToTheView() {
        username.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-DISPLAY-SEMIBOLD.OTF"));
        inputLayoutName.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-DISPLAY-SEMIBOLD.OTF"));
        inputLayoutEmail.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-DISPLAY-SEMIBOLD.OTF"));
        email.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-DISPLAY-SEMIBOLD.OTF"));
        inputLayoutPassword.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-DISPLAY-SEMIBOLD.OTF"));
        password.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-DISPLAY-SEMIBOLD.OTF"));
        confirmPass.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-DISPLAY-SEMIBOLD.OTF"));
        inputLayoutConfPassword.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-DISPLAY-SEMIBOLD.OTF"));
        signUpTv.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-DISPLAY-BOLD.OTF"));
        noAccount.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-DISPLAY-BOLD.OTF"));
        signupTopTv.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-DISPLAY-BOLD.OTF"));
        signInTv.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-DISPLAY-BOLD.OTF"));

    }


    private boolean validatePassword() {
        if (password.getText().toString().trim().isEmpty()) {
            showMessage(getString(R.string.err_msg_password));
            return false;
        }else if (password.getText().length()<6){
            showMessage(getString(R.string.err_pass_length));
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
            loginCredentials.password = password.getText().toString().trim();
        }
        return true;
    }

    private boolean validateName() {
        if (username.getText().toString().trim().isEmpty()) {
            showMessage(getString(R.string.err_msg_name));
            return false;
        }else {
            inputLayoutName.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateConfPassword() {
        if (confirmPass.getText().toString().trim().isEmpty()) {
            showMessage(getString(R.string.err_msg_password));
            return false;
        } else if (!(confirmPass.getText().toString().equals(password.getText().toString()))) {
            showMessage(getString(R.string.cnf_msg_password));
            return false;
        } else {
            inputLayoutConfPassword.setErrorEnabled(false);
            loginCredentials.password = password.getText().toString().trim();
        }
        return true;
    }

    private boolean validateEmail() {
        if (email.getText().toString().trim().isEmpty()) {
            showMessage(getString(R.string.err_msg_email));
            return false;
        }else if (!(isValidEmail(email.getText().toString()))){
            showMessage(getString(R.string.err_msg_email));
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
            loginCredentials.email = email.getText().toString().trim();
        }
        return true;
    }


    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target)
                && android.util.Patterns.EMAIL_ADDRESS.matcher(target)
                .matches();
    }

    private void submitForm() {
        if (!validateName()) {
            return;
        }

        if (!validateEmail()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }

        if (!validateConfPassword()) {
            return;
        }

        if (AppNetworkStatus.getInstance(this).isOnline()) {
            MyProgressDialog.show(mContext);
            firebaseAuth.createUserWithEmailAndPassword(loginCredentials.getEmail(),loginCredentials.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        MyProgressDialog.dismiss();
                        finish();
                    }else {
                        showMessage("Unable to Signup");
                        MyProgressDialog.dismiss();
                    }
                }
            });

        } else {
            showMessage("Unable to connect to internet");
            MyProgressDialog.dismiss();
        }

    }

    @OnClick({R.id.sign_up_tv_sl, R.id.signin_sl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sign_up_tv_sl:
                submitForm();
                break;
            case R.id.signin_sl:
                finish();
                break;
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
                case R.id.input_layout_email:
                    validateEmail();
                    break;
                case R.id.input_layout_conf_password:
                    validateConfPassword();
                    break;
            }
        }
    }

}
