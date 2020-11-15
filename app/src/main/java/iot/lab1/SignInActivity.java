package iot.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        EditText email = findViewById(R.id.email_field);
        EditText password = findViewById(R.id.password_field);
        Button sign_in = findViewById(R.id.sign_in_button);
        TextView sign_up_nav = findViewById(R.id.sign_up_nav);

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_value = email.getText().toString();
                String password_value = password.getText().toString();
                Helper helper = new Helper();
                boolean valid = true;

                if (!helper.isEmailValid(email_value)) {
                    email.setError("Please enter a valid email!");
                    valid = false;
                }

                if (password_value.length() <= 8) {
                    password.setError("Password should contain more than 8 symbols");
                    valid = false;
                }

                if (valid) {
                    Context context = getApplicationContext();
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                }
            }
        });

        sign_up_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
            }
        });
    }
}