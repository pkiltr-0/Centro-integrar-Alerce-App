package prog.android.centroalr;

import android.content.Intent;
import android.os.Bundle;
<<<<<<< HEAD
=======
import android.text.TextUtils;
>>>>>>> aaba39e10092bee3399cab94c4dcb9980c6a403a
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
<<<<<<< HEAD

import prog.android.centroalr.controller.LoginController;
import prog.android.centroalr.model.AuthModel;
import prog.android.centroalr.view.LoginView;

// 1. Implementa la interfaz de la Vista
public class LogInActivity extends AppCompatActivity implements LoginView {

    // Referencias a la UI (Vista)
=======
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity {

    private FirebaseAuth auth;
>>>>>>> aaba39e10092bee3399cab94c4dcb9980c6a403a
    private TextInputEditText emailEditText, passwordEditText;
    private TextInputLayout emailInputLayout, passwordInputLayout;
    private Button loginButton;
    private TextView forgotPasswordTextView;

<<<<<<< HEAD
    // 2. Referencia al Controlador y al Modelo
    private LoginController controller;
    private AuthModel model;

=======
>>>>>>> aaba39e10092bee3399cab94c4dcb9980c6a403a
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

<<<<<<< HEAD
        // 3. Inicializar MVC (ESTO FALTABA)
        model = new AuthModel();
        controller = new LoginController(this, model);

        // UI refs (ESTO FALTABA)
=======
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
>>>>>>> aaba39e10092bee3399cab94c4dcb9980c6a403a
        emailInputLayout = findViewById(R.id.emailInputLayout);
        passwordInputLayout = findViewById(R.id.passwordInputLayout);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        forgotPasswordTextView = findViewById(R.id.forgotPasswordTextView);

<<<<<<< HEAD
        // 4. Delegar eventos al Controlador (ESTO FALTABA)
        forgotPasswordTextView.setOnClickListener(v ->
                controller.onForgotPasswordClicked());

        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText() != null ? emailEditText.getText().toString().trim() : "";
            String password = passwordEditText.getText() != null ? passwordEditText.getText().toString() : "";
            controller.onLoginClicked(email, password);
        });
=======
        // Recuperar contraseña
        forgotPasswordTextView.setOnClickListener(v ->
                startActivity(new Intent(this, PasswordRecoveryActivity.class)));

        // Iniciar sesión
        loginButton.setOnClickListener(v -> attemptLogin());
>>>>>>> aaba39e10092bee3399cab94c4dcb9980c6a403a
    }

    @Override
    protected void onStart() {
        super.onStart();
<<<<<<< HEAD
        // 5. Delegar chequeo al Controlador
        controller.checkUserLoggedIn();
    }

    // --- Implementación de los métodos de LoginView ---

    @Override
    public void showEmailError(String message) {
        emailInputLayout.setError(message);
    }

    @Override
    public void showPasswordError(String message) {
        passwordInputLayout.setError(message);
    }

    @Override
    public void clearErrors() {
        emailInputLayout.setError(null);
        passwordInputLayout.setError(null);
    }

    @Override
    public void showLoading(boolean isLoading) {
        loginButton.setEnabled(!isLoading);
    }

    @Override
    public void showLoginError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void navigateToMainApp() {
=======
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
>>>>>>> aaba39e10092bee3399cab94c4dcb9980c6a403a
        Intent i = new Intent(this, AgendMensActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }

<<<<<<< HEAD
    @Override
    public void navigateToPasswordRecovery() {
        startActivity(new Intent(this, PasswordRecoveryActivity.class));
    }
}
=======
    private void clearErrors() {
        emailInputLayout.setError(null);
        passwordInputLayout.setError(null);
    }
}
>>>>>>> aaba39e10092bee3399cab94c4dcb9980c6a403a
