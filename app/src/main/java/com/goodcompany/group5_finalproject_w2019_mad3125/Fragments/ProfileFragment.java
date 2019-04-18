package com.goodcompany.group5_finalproject_w2019_mad3125.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goodcompany.group5_finalproject_w2019_mad3125.R;
import com.goodcompany.group5_finalproject_w2019_mad3125.Utils.ShadowLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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
    @BindView(R.id.firstName)
    TextView firstName;
    @BindView(R.id.et_firstName)
    EditText etFirstName;
    @BindView(R.id.lastName)
    TextView lastName;
    @BindView(R.id.et_lastName)
    EditText etLastName;
    @BindView(R.id.Address)
    TextView Address;
    @BindView(R.id.address)
    EditText address;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.et_ph)
    EditText etPh;
    Unbinder unbinder;
    private DatabaseReference mDatabase;
// ...

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser){
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
    }


    public void updateUserInfor() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            String uid = user.getUid();
            HashMap<String,Object> kmap = new HashMap<>();
            kmap.put("country","Canada");
            mDatabase.updateChildren(kmap);

            mDatabase.setValue("I'm writing data", new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        System.out.println("Data could not be saved " + databaseError.getMessage());
                    } else {
                        System.out.println("Data saved successfully.");
                    }
                }
            });
        }
    }


    public  void getUserInfo(){
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists() && dataSnapshot !=null){
                        String country = dataSnapshot.child("country").getValue().toString();
                        Log.e("check", "onDataChange: "+country );
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
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
                break;
        }
    }
}
