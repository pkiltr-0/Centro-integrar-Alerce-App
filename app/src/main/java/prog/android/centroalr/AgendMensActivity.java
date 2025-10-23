package prog.android.centroalr;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast; // Para mostrar mensajes al usuario

import com.google.firebase.auth.FirebaseAuth;

public class AgendMensActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agend_mens); // Asegúrate de que el nombre del layout sea correcto

        // 1. Inicializar la instancia de Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // 2. Enlazar la vista del botón "SALIR"
        btnLogout = findViewById(R.id.btn_logout);

        // 3. Establecer el Listener para el clic
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        // Opcional: Si necesitas la funcionalidad del botón de regreso:
        // findViewById(R.id.back_arrow).setOnClickListener(v -> finish());
    }

    /**
     * Cierra la sesión del usuario de Firebase y navega a la pantalla de Login.
     */
    private void logoutUser() {
        try {
            // Cierra la sesión de Firebase
            mAuth.signOut();
            Toast.makeText(this, "Sesión cerrada correctamente.", Toast.LENGTH_SHORT).show();

            // Navega a la actividad de inicio de sesión
            Intent intent = new Intent(AgendMensActivity.this, LogInActivity.class);

            // Limpia la pila de actividades para prevenir que el usuario regrese
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish(); // Finaliza esta actividad de calendario

        } catch (Exception e) {
            Toast.makeText(this, "Error al cerrar sesión: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}