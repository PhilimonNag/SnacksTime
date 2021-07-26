package com.philimonnag.snackstime.login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.philimonnag.snackstime.R;
import com.philimonnag.snackstime.databinding.FragmentMobileBinding;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class MobileFragment extends Fragment {
    private FragmentMobileBinding binding;
    FirebaseAuth mAuth;
    String phoneNumber;
    String mVerificationId;
    String otp;
    ProgressDialog pd;
    PhoneAuthProvider.ForceResendingToken token;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMobileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mAuth = FirebaseAuth.getInstance();
        pd=new ProgressDialog(getContext());
        pd.setMessage("Sending Otp...");
        binding.SendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber=binding.phoneTv.getText().toString();
                if(TextUtils.isEmpty(phoneNumber)){
                    binding.phoneTv.setError("Phone Number is Required");

                }else {
                    pd.show();
                    PhoneAuthOptions options= PhoneAuthOptions.newBuilder(mAuth)
                            .setPhoneNumber(phoneNumber)       // Phone number to verify
                            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                            .setActivity(requireActivity())                 // Activity (for callback binding)
                            .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(@NonNull @NotNull PhoneAuthCredential phoneAuthCredential) {
                                    mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                                            pd.dismiss();
                                            if(task.isSuccessful()){
                                                Toast.makeText(getContext(), "Login", Toast.LENGTH_SHORT).show();
                                                Bundle bundle=new Bundle();
                                                bundle.putString("phone",phoneNumber);
                                                Navigation.findNavController(v).navigate(R.id.action_mobileFragment_to_signUpFragment,bundle);
                                            }else {
                                                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                    pd.dismiss();
                                }

                                @Override
                                public void onVerificationFailed(@NonNull @NotNull FirebaseException e) {
                                        pd.dismiss();
                                    Toast.makeText(getContext(), "it because "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCodeSent(@NonNull @NotNull String s, @NonNull @NotNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    super.onCodeSent(s, forceResendingToken);
                                    pd.dismiss();
                                    mVerificationId=s;
                                    token = forceResendingToken;
                                    binding.otpView.setVisibility(View.VISIBLE);
                                    binding.verify.setVisibility(View.VISIBLE);
                                    binding.Resend.setVisibility(View.VISIBLE);
                                }
                            })          // OnVerificationStateChangedCallbacks
                            .build();
                    PhoneAuthProvider.verifyPhoneNumber(options);
                }
            }
        });
        binding.verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp=binding.otpView.getText().toString().trim();
                if(TextUtils.isEmpty(otp)){
                    binding.otpView.setError("otp is required");
                }else {
                    PhoneAuthCredential credential=PhoneAuthProvider.getCredential(mVerificationId,otp);
                   mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                           if(task.isSuccessful()){
                               Toast.makeText(getContext(), "Login", Toast.LENGTH_SHORT).show();
                               Bundle bundle=new Bundle();
                               bundle.putString("phone",phoneNumber);
                               Navigation.findNavController(v).navigate(R.id.action_mobileFragment_to_signUpFragment,bundle);
                           }else {
                               Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                           }
                       }
                   });
                }
            }
        });
        binding.Resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return root;
    }

}