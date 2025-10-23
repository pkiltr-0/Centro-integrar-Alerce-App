package prog.android.centroalr;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.debug.BuildConfig;
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory;
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private TextInputEditText emailEditText, passwordEditText;
    private TextInputLayout emailInputLayout, passwordInputLayout;
    private Button loginButton;
    private TextView forgotPasswordTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        // Firebase + App Check
        FirebaseApp.initializeApp(this);
        FirebaseAppCheck appCheck = FirebaseAppCheck.getInstance();
        if (BuildConfig.DEBUG) {
            appCheck.installAppCheckProviderFactory(DebugAppCheckProviderFactory.getInstance());
        } else {
            appCheck.installAppCheckProviderFactory(PlayIntegrityAppCheckProviderFactory.getInstance());
        }

        auth = FirebaseAuth.getInstance();

        // UI refs
        emailInputLayout = findViewById(R.id.emailInputLayout);
        passwordInputLayout = findViewById(R.id.passwordInputLayout);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        forgotPasswordTextView = findViewById(R.id.forgotPasswordTextView);

        // Recuperar contraseña
        forgotPasswordTextView.setOnClickListener(v ->
                startActivity(new Intent(this, PasswordRecoveryActivity.class)));

        // Iniciar sesión
        loginButton.setOnClickListener(v -> attemptLogin());
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            goToAgendMensAndFinish(); // <- ahora entra a la agenda mensual
        }
    }

    private void attemptLogin() {
        clearErrors();

        String email = emailEditText.getText() != null ? emailEditText.getText().toString().trim() : "";
        String password = passwordEditText.getText() != null ? passwordEditText.getText().toString() : "";

        boolean hasError = false;

        if (TextUtils.isEmpty(email)) {
            emailInputLayout.setError("Ingresa tu correo");
            hasError = true;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInputLayout.setError("Formato de correo inválido");
            hasError = true;
        }

        if (TextUtils.isEmpty(password)) {
            passwordInputLayout.setError("Ingresa tu contraseña");
            hasError = true;
        } else if (password.length() < 6) {
            passwordInputLayout.setError("Mínimo 6 caracteres");
            hasError = true;
        }

        if (hasError) return;

        loginButton.setEnabled(false);

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    loginButton.setEnabled(true);
                    if (task.isSuccessful()) {
                        goToAgendMensAndFinish(); // <- destino actualizado tras login
                    } else {
                        Throwable t = task.getException();
                        Toast.makeText(
                                this,
                                t != null ? t.getLocalizedMessage() : "Error al iniciar sesión",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }

    private void goToAgendMensAndFinish() {
        Intent i = new Intent(this, AgendMensActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }

    private void clearErrors() {
        emailInputLayout.setError(null);
        passwordInputLayout.setError(null);
    }
}
