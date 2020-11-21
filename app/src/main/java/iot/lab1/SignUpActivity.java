package iot.lab1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        EditText name = findViewById(R.id.name_filed);
        EditText email = findViewById(R.id.email_field);
        EditText password_one = findViewById(R.id.password_field_1);
        EditText password_two = findViewById(R.id.password_field_2);
        Button sign_up = findViewById(R.id.sign_up_button);

        Toolbar toolbar = findViewById(R.id.sign_up_toolbar);
        setSupportActionBar(toolbar);

        EmailValidator emailValidator = new EmailValidator();
        PasswordValidator passwordValidator = new PasswordValidator();
        ConfirmPasswordValidator confirmPasswordValidator = new ConfirmPasswordValidator();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignInActivity.class));
            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name_value = name.getText().toString();
                String email_value = email.getText().toString();
                String password_one_value = password_one.getText().toString();
                String password_two_value = password_two.getText().toString();
                boolean valid = true;

                if (name_value.isEmpty()) {
                    name.setError("Please enter your name");
                    valid = false;
                }

                if (emailValidator.validate(email_value)) {
                    email.setError("Please enter valid email");
                    valid = false;
                }

                if (passwordValidator.validate(password_one_value)) {
                    password_one.setError("Password should contain more than 8 symbols");
                    valid = false;
                }

                if (passwordValidator.validate(password_two_value)) {
                    password_two.setError("Password should contain more than 8 symbols");
                    valid = false;
                }

                if (confirmPasswordValidator.validate(password_one_value, password_two_value)) {
                    password_two.setError("Passwords do not match");
                    valid = false;
                }

                if (valid) {
                    Context context = getApplicationContext();
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}