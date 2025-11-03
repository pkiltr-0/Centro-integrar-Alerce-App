<<<<<<< HEAD
package prog.android.centroalr;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import prog.android.centroalr.controller.PasswordRecoveryController;
import prog.android.centroalr.model.AuthModel;
import prog.android.centroalr.view.PasswordRecoveryView;

// 1. Implementa la interfaz de la Vista
public class PasswordRecoveryActivity extends AppCompatActivity implements PasswordRecoveryView {

    // Vistas
    private TextInputEditText emailEditText;
    private TextInputLayout emailInputLayout;
    private MaterialButton sendCodeButton;

    // 2. Referencia al Controlador y Modelo
    private PasswordRecoveryController controller;
    private AuthModel model;
=======
package prog.android.centroalr; // Asegúrate que este sea tu paquete correcto

import android.os.Bundle;
import android.util.Patterns; // Import para validar email
import android.view.View;
import android.widget.Toast; // Import para mostrar mensajes
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

// Imports de Material Design y Firebase
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordRecoveryActivity extends AppCompatActivity {

    // Declara las variables para los elementos de la UI y Firebase
    private TextInputEditText emailEditText;
    private TextInputLayout emailInputLayout;
    private MaterialButton sendCodeButton; // Asumiendo que tu botón tiene este ID
    private FirebaseAuth firebaseAuth;
>>>>>>> aaba39e10092bee3399cab94c4dcb9980c6a403a

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD
        setContentView(R.layout.activity_rec_contrase_a);

        // 3. Inicializar MVC
        model = new AuthModel(); // El mismo modelo, nueva instancia
        controller = new PasswordRecoveryController(this, model);

        // UI refs
        emailEditText = findViewById(R.id.emailEditText);
        emailInputLayout = findViewById(R.id.emailInputLayout);
        sendCodeButton = findViewById(R.id.sendCodeButton);

        // 4. Delegar evento al Controlador
        sendCodeButton.setOnClickListener(v -> {
            String email = emailEditText.getText() != null ? emailEditText.getText().toString().trim() : "";
            controller.onRecoverClicked(email);
        });
    }

    // --- Implementación de los métodos de PasswordRecoveryView ---

    @Override
    public void showEmailError(String message) {
        emailInputLayout.setError(message);
    }

    @Override
    public void clearEmailError() {
        emailInputLayout.setError(null);
    }

    @Override
    public void showLoading(boolean isLoading) {
        sendCodeButton.setEnabled(!isLoading);
    }

    @Override
    public void showSuccessMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showRecoverError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void navigateBackToLogin() {
        finish(); // Cierra esta actividad y vuelve a Login
=======
        setContentView(R.layout.activity_rec_contrase_a); // Conecta con tu layout XML

        // 1. Inicializa las vistas y Firebase Auth
        emailEditText = findViewById(R.id.emailEditText);       // Reemplaza con el ID de tu TextInputEditText
        emailInputLayout = findViewById(R.id.emailInputLayout); // Reemplaza con el ID de tu TextInputLayout
        sendCodeButton = findViewById(R.id.sendCodeButton);     // Reemplaza con el ID de tu MaterialButton
        firebaseAuth = FirebaseAuth.getInstance();              // Obtiene la instancia de Firebase Auth

        // Configura el botón de retroceso (opcional, si tienes uno en el XML)
        // ImageButton backButton = findViewById(R.id.backButton); // Reemplaza con el ID de tu ImageButton
        // backButton.setOnClickListener(v -> finish()); // Cierra esta pantalla al hacer clic

        // 2. Configura el listener (la acción) del botón "Recibir código"
        sendCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recoverPassword(); // Llama a la función para iniciar la recuperación
            }
        });
    }

    // Función que contiene la lógica de recuperación
    private void recoverPassword() {
        // Obtiene el texto del campo de correo y quita espacios extra
        String email = emailEditText.getText().toString().trim();

        // 3. Valida el formato del correo
        if (email.isEmpty()) {
            emailInputLayout.setError("El correo no puede estar vacío");
            return; // Detiene la función si está vacío
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInputLayout.setError("Formato de correo inválido");
            return; // Detiene la función si el formato es incorrecto
        } else {
            emailInputLayout.setError(null); // Limpia cualquier error previo si el correo es válido
        }

        // Muestra un mensaje mientras Firebase trabaja (opcional)
        Toast.makeText(this, "Enviando correo...", Toast.LENGTH_SHORT).show();

        // 4. Llama a Firebase para enviar el correo de restablecimiento
        firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Éxito: Informa al usuario y cierra la pantalla
                            Toast.makeText(PasswordRecoveryActivity.this,
                                    "Correo enviado. Revisa tu bandeja de entrada para restablecer la contraseña.",
                                    Toast.LENGTH_LONG).show();
                            finish(); // Cierra esta actividad y vuelve a la pantalla de Login
                        } else {
                            // Error: Informa al usuario del problema específico (si es posible)
                            String errorMessage = task.getException() != null ? task.getException().getMessage() : "Error desconocido";
                            Toast.makeText(PasswordRecoveryActivity.this,
                                    "Error al enviar correo: " + errorMessage,
                                    Toast.LENGTH_LONG).show();
                            // Puedes añadir lógica más específica aquí, como verificar si el usuario existe
                        }
                    }
                });
>>>>>>> aaba39e10092bee3399cab94c4dcb9980c6a403a
    }
}