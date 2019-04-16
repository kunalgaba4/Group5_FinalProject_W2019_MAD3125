package com.goodcompany.group5_finalproject_w2019_mad3125.Activities;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.goodcompany.group5_finalproject_w2019_mad3125.Modals.CredentialsLogin;
import com.goodcompany.group5_finalproject_w2019_mad3125.R;
import com.goodcompany.group5_finalproject_w2019_mad3125.Utils.AppNetworkStatus;
import com.goodcompany.group5_finalproject_w2019_mad3125.Utils.ShadowLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgotPasswordActivity extends BaseActivity {

    @BindView(R.id.login_top_tv)
    TextView loginTopTv;
    @BindView(R.id.input_name)
    AppCompatEditText inputName;
    @BindView(R.id.input_layout_name)
    TextInputLayout inputLayoutName;
    @BindView(R.id.send_pass_tv)
    TextView sendPassTv;
    @BindView(R.id.send_pass_sl)
    ShadowLayout sendPassSl;
    @BindView(R.id.already_account_tv)
    TextView alreadyAccountTv;
    @BindView(R.id.signin_tv)
    TextView signinTv;
    @BindView(R.id.signin_sl)
    ShadowLayout signinSl;
    private Context mContext;
    CredentialsLogin forgotPassCredentials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);
        mContext = ForgotPasswordActivity.this;
        forgotPassCredentials = new CredentialsLogin();
        addFontToTheView();
        inputName.addTextChangedListener(new MyTextWatcher(inputName));
    }

    private void addFontToTheView() {
        loginTopTv.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-DISPLAY-BOLD.OTF"));
        signinTv.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-DISPLAY-BOLD.OTF"));
        alreadyAccountTv.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-DISPLAY-BOLD.OTF"));
        sendPassTv.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-DISPLAY-BOLD.OTF"));
        inputName.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-DISPLAY-SEMIBOLD.OTF"));
        inputLayoutName.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-DISPLAY-SEMIBOLD.OTF"));
    }

    private boolean validateName() {
        if (inputName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_username));
            requestFocus(inputName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
            forgotPassCredentials.email = inputName.getText().toString();
        }
        return true;
    }

    @OnClick({R.id.send_pass_sl, R.id.signin_sl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.send_pass_sl:
                sendPassword();
                break;
            case R.id.signin_sl:
                finish();
                break;
        }
    }

    private void sendPassword() {
        if (!validateName()) {
            return;
        }

        if (AppNetworkStatus.getInstance(this).isOnline()) {
            if (validateName()) {
                FirebaseAuth.getInstance().sendPasswordResetEmail(forgotPassCredentials.getEmail())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    showMessage("Email Sent Successfully");
                                }
                            }
                        });
            }
        } else {
            showMessage("Unable to connect to internet");
        }

    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
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

            }
        }
    }
}