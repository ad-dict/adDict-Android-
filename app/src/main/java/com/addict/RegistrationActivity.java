package com.addict;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.addict.R;


public class RegistrationActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    EditText email_editText;
    EditText password_editText;
    EditText username_editText;
    TextView signIn_text;
    Button signUp_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        auth=FirebaseAuth.getInstance();

        email_editText = findViewById(R.id.editText_email);
        password_editText = findViewById(R.id.editText_password);
        signIn_text = findViewById(R.id.signIn_text);
        signUp_button = findViewById(R.id.button_signUp);

        signUp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_editText.getText().toString();
                String password = password_editText.getText().toString();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"Please enter your email!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(),"Please enter your password!",Toast.LENGTH_SHORT).show();
                }
                if (password.length()<6){
                    Toast.makeText(getApplicationContext(),"Password must be at least 6 digit!",Toast.LENGTH_SHORT).show();
                }
            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        //İşlem başarısız olursa kullanıcıya bir Toast mesajıyla bildiriyoruz.
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegistrationActivity.this, "Authorization Error!",
                                    Toast.LENGTH_SHORT).show();
                        }

                        //İşlem başarılı olduğu takdir de giriş yapılıp MainActivity e yönlendiriyoruz.
                        else {
                            startActivity(new Intent(RegistrationActivity.this, HomeActivity.class));
                            finish();
                        }

                    }
                });
            }
        });

        signIn_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
}
