package cl.bootcamp.ejercicioindividualcuatro;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "user_data_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referencia los elementos de la interfaz
        EditText editTextName = findViewById(R.id.editTextText);
        EditText editTextPassword = findViewById(R.id.etPass);
        EditText editTextEmail = findViewById(R.id.editTextTextEmailAddress);
        //btn
        Button button = findViewById(R.id.button);

        createNotificationChannel();

        // evento del botón
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // permite obtener los valores de los campos de texto
                String name = editTextName.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();

                // permite validar que los campos no estén vacíos
                if (name.isEmpty() || password.isEmpty() || email.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Crea y muestra la notificación
                showNotification(name, password, email);
            }
        });
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Canal de datos del usuario";
            String description = "Canal para mostrar los datos ingresados por el usuario";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    private void showNotification(String name, String password, String email) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Datos ingresados")
                .setContentText("Nombre: " + name + ", Contraseña: " + password + ", Email: " + email)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Nombre: " + name + "\nContraseña: " + password + "\nEmail: " + email))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(1, builder.build());
        }
    }
}