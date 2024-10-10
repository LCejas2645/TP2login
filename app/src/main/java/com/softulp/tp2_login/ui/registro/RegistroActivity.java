package com.softulp.tp2_login.ui.registro;

import static android.Manifest.permission.CAMERA;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.softulp.tp2_login.databinding.ActivityRegistroBinding;
import com.softulp.tp2_login.modelo.Usuario;
import com.softulp.tp2_login.request.Api;
import com.softulp.tp2_login.request.ApiClient;
import com.softulp.tp2_login.ui.login.MainActivity;


public class RegistroActivity extends AppCompatActivity {
    ActivityRegistroBinding binding;
    RegistroActivityViewModel mv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mv = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(RegistroActivityViewModel.class);


        binding.btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long dni = Long.parseLong(binding.etDni.getText().toString());
                String nombre = binding.etNombre.getText().toString();
                String apellido = binding.etApellido.getText().toString();
                String mail = binding.etMail.getText().toString();
                String password = binding.etPassword2.getText().toString();

                Usuario usuario = new Usuario(dni, nombre, apellido, mail, password);
                Api.guardarUsuario(getApplication(), usuario);
            }
        });


        mv.getUsuarioMutable().observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                binding.etDni.setText(usuario.getDni() + "");
                binding.etNombre.setText(usuario.getNombre() + "");
                binding.etApellido.setText(usuario.getApellido() + "");
                binding.etMail.setText(usuario.getMail() + "");
                binding.etPassword2.setText(usuario.getPassword() + "");
            }
        });

        Intent intent = getIntent();
        Usuario usuario = (Usuario) intent.getSerializableExtra("usuario");
        mv.recuperarUsuario(usuario);
    }
}