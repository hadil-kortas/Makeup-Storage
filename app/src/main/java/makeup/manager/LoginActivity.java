package makeup.manager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import makeup.manager.data.InputValidation;
import makeup.manager.data.DatabaseHelper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = LoginActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;

    private EditText EditTextEmail;
    private EditText EditTextPassword;

    private ImageView Login;
    private TextView textViewLinkRegister;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();
    }

    /**
     * This method is to initialize views
     */

    private void initViews() {

        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);

        EditTextEmail = (EditText) findViewById(R.id.EditTextEmail);
        EditTextPassword = (EditText) findViewById(R.id.EditTextPassword);

        Login = (ImageView) findViewById(R.id.Login);

        textViewLinkRegister = (TextView) findViewById(R.id.textViewLinkRegister);

    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        Login.setOnClickListener(this);
        textViewLinkRegister.setOnClickListener(this);
    }

    /**
     * This method is to initialize objects to be used
     */

    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);

    }
    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Login:
                verifyFromSQLite();
                break;
            case R.id.textViewLinkRegister:
                // Navigate to RegisterActivity
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
                break;
        }

    }

    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */

    private void verifyFromSQLite() {
        if (!inputValidation.isInputEditTextFilled(EditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(EditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(EditTextPassword, textInputLayoutPassword, getString(R.string.error_message_email))) {
            return;
        }

        if (databaseHelper.checkUser(EditTextEmail.getText().toString().trim()
                , EditTextPassword.getText().toString().trim())) {


            Intent produit = new Intent(activity, HomeActivity.class);
            //produit.putExtra("EMAIL", textInputEditTextEmail.getText().toString().trim());
            //emptyInputEditText();
            startActivity(produit);


        } else {
            // Snack Bar to show success message that record is wrong
            Snackbar.make(nestedScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        EditTextEmail.setText(null);
        EditTextPassword.setText(null);
    }
}
