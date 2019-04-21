package com.goodcompany.group5_finalproject_w2019_mad3125.Fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Guideline;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goodcompany.group5_finalproject_w2019_mad3125.Activities.BaseActivity;
import com.goodcompany.group5_finalproject_w2019_mad3125.Activities.SelectImage;
import com.goodcompany.group5_finalproject_w2019_mad3125.Dialogs.MyProgressDialog;
import com.goodcompany.group5_finalproject_w2019_mad3125.Modals.CredentialsLogin;
import com.goodcompany.group5_finalproject_w2019_mad3125.R;
import com.goodcompany.group5_finalproject_w2019_mad3125.Utils.Constants;
import com.goodcompany.group5_finalproject_w2019_mad3125.Utils.ShadowLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    @BindView(R.id.heading)
    TextView heading;
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.sl_back)
    ShadowLayout slBack;
    @BindView(R.id.Update)
    TextView Update;
    @BindView(R.id.sl_update)
    ShadowLayout slUpdate;
    @BindView(R.id.header)
    RelativeLayout header;
    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    @BindView(R.id.firstName)
    EditText firstName;
    @BindView(R.id.input_layout_name)
    TextInputLayout inputLayoutName;
    @BindView(R.id.lastName)
    EditText lastName;
    @BindView(R.id.input_layout_lname)
    TextInputLayout inputLayoutLname;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.input_layout_email)
    TextInputLayout inputLayoutEmail;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.input_layout_ph)
    TextInputLayout inputLayoutPh;
    @BindView(R.id.street)
    EditText street;
    @BindView(R.id.input_layout_conf_street)
    TextInputLayout inputLayoutConfStreet;
    @BindView(R.id.city)
    EditText city;
    @BindView(R.id.input_layout_city)
    TextInputLayout inputLayoutCity;
    @BindView(R.id.state)
    EditText state;
    @BindView(R.id.input_layout_state)
    TextInputLayout inputLayoutState;
    @BindView(R.id.zip)
    EditText zip;
    @BindView(R.id.input_layout_zip)
    TextInputLayout inputLayoutZip;
    Unbinder unbinder;
    private DatabaseReference mDatabase;
    private CredentialsLogin credentialsLogin;
    private Context mContext;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            MyProgressDialog.show(mContext);
            getUserInfo();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        credentialsLogin = new CredentialsLogin();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), SelectImage.class);
                startActivityForResult(i, Constants.REQUEST_CODE_FROM_FACE_CHOOSE);
            }
        });
    }

    public  String convertImageToBase64(){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitmap = ((BitmapDrawable)profileImage.getDrawable()).getBitmap();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return imageString;

    }

    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target)
                && Patterns.EMAIL_ADDRESS.matcher(target)
                .matches();
    }

    private boolean validateName() {
        if (firstName.getText().toString().trim().isEmpty()) {
            ((BaseActivity)getActivity()).showMessage(getString(R.string.err_msg_name));
            return false;
        }else {
            credentialsLogin.firstName = firstName.getText().toString().trim();
            inputLayoutName.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateLname() {
        if (lastName.getText().toString().trim().isEmpty()) {
            ((BaseActivity)getActivity()).showMessage(getString(R.string.err_msg_name));
            return false;
        }else {
            credentialsLogin.lastName = lastName.getText().toString().trim();
            inputLayoutLname.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateEmail() {
        if (email.getText().toString().trim().isEmpty()) {
            ((BaseActivity)getActivity()).showMessage(getString(R.string.err_msg_email));
            return false;
        }else if (!(isValidEmail(email.getText().toString()))){
            ((BaseActivity)getActivity()).showMessage(getString(R.string.err_msg_email));
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
            credentialsLogin.email = email.getText().toString().trim();
        }
        return true;
    }

    private boolean validateNumber(){
        if (phone.getText().toString().trim().isEmpty()) {
            ((BaseActivity)getActivity()).showMessage(getString(R.string.err_pass_number));
            return false;
        }else if(phone.getText().toString().length()<10) {
            ((BaseActivity)getActivity()).showMessage(getString(R.string.err_pass_number));
            return false;
        }else {
            credentialsLogin.phone = phone.getText().toString().trim();
            inputLayoutPh.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateCity() {
        if (city.getText().toString().trim().isEmpty()) {
            ((BaseActivity)getActivity()).showMessage(getString(R.string.err_msg_city));
            return false;
        }else {
            credentialsLogin.city = city.getText().toString().trim();
            inputLayoutCity.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateStreet() {
        if (street.getText().toString().trim().isEmpty()) {
            ((BaseActivity)getActivity()).showMessage(getString(R.string.err_msg_city));
            return false;
        }else {
            credentialsLogin.street = street.getText().toString().trim();
            inputLayoutConfStreet.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateState() {
        if (state.getText().toString().trim().isEmpty()) {
            ((BaseActivity)getActivity()).showMessage(getString(R.string.err_msg_state));
            return false;
        }else {
            credentialsLogin.state = state.getText().toString().trim();
            inputLayoutState.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateZip() {
        if (zip.getText().toString().trim().isEmpty()) {
            ((BaseActivity)getActivity()).showMessage(getString(R.string.err_msg_zip));
            return false;
        }else {
            credentialsLogin.zip = zip.getText().toString().trim();
            inputLayoutZip.setErrorEnabled(false);
        }
        return true;
    }




    public void updateUserInfor() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (!validateName()){
            return;
        }

        if (!validateLname()){
            return;
        }

        if (!validateEmail()){
            return;
        }

        if (!validateStreet()){
            return;
        }

        if (!validateCity()){
            return;
        }
        if (!validateState()){
            return;
        }

        if (!validateNumber()){
            return;
        }

        if (!validateZip()){
            return;
        }
        credentialsLogin.setProfile(convertImageToBase64());
        MyProgressDialog.show(getActivity());
        if (user != null) {
            mDatabase.setValue(credentialsLogin, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        System.out.println("Data could not be saved " + databaseError.getMessage());
                        MyProgressDialog.dismiss();
                    } else {
                        System.out.println("Data saved successfully.");
                        MyProgressDialog.dismiss();
                    }
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CODE_FROM_FACE_CHOOSE){
            if (resultCode == Activity.RESULT_OK){
                profileImage.setImageURI(Uri.parse(data.getExtras().getString("uri")));
            }else if (resultCode == Activity.RESULT_CANCELED){
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                Bitmap bmp = BitmapFactory.decodeFile(data.getExtras().getString("gallery"), bmOptions);
                profileImage.setImageBitmap(bmp);
            }
        }
    }

    public void getUserInfo() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot != null) {
                    CredentialsLogin user = dataSnapshot.getValue(CredentialsLogin.class);
                    firstName.setText(user.getFirstName());
                    lastName.setText(user.getLastName());
                    email.setText(user.getEmail());
                    phone.setText(user.getPhone());
                    street.setText(user.getStreet());
                    city.setText(user.getCity());
                    state.setText(user.getState());
                    zip.setText(user.getZip());
                    decodeBase64ToImageview(user.getProfile());
                    MyProgressDialog.dismiss();
                }
                MyProgressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void decodeBase64ToImageview(String profile) {
        if (profile != null && profile.length()>0){
            byte[] imageBytes = Base64.decode(profile, Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            profileImage.setImageBitmap(decodedImage);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.sl_back, R.id.sl_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sl_back:
                FirebaseAuth.getInstance().signOut();
                getActivity().finish();
                break;
            case R.id.sl_update:
                updateUserInfor();
                break;
        }
    }
}
