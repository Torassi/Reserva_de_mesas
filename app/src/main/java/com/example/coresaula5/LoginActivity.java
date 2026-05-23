package com.example.coresaula5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEdit, senhaEdit;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity); // Carrega o XML do login

        // Mapeia os campos de texto e o botão pelos IDs definidos no XML
        emailEdit = findViewById(R.id.emailEdit);
        senhaEdit = findViewById(R.id.senhaEdit);
        btnLogin = findViewById(R.id.buttonEdit);

        // Configura o evento de clique no botão
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Captura o texto digitado nos campos
                String user = emailEdit.getText().toString();
                String pass = senhaEdit.getText().toString();

                // Requisito: Não pode estar em branco
                if (user.isEmpty() || pass.isEmpty()) {
                    // Nota: Certifique-se de que a string 'erro_login_vazio' existe no seu strings.xml
                    Toast.makeText(LoginActivity.this, R.string.erro_login_vazio, Toast.LENGTH_SHORT).show();
                    return; // Para a execução aqui se estiver vazio
                }

                // Requisito: Validação de usuários específicos
                if (validarCredenciais(user, pass)) {
                    // Cria a intenção de sair da LoginActivity e ir para a MainActivity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);

                    // Finaliza a tela de login para que o usuário não consiga
                    // voltar para ela apertando o botão "Voltar" do celular.
                    finish();
                } else {
                    // Credenciais incorretas
                    // Nota: Certifique-se de que a string 'erro_login_invalido' existe no seu strings.xml
                    Toast.makeText(LoginActivity.this, R.string.erro_login_invalido, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Método auxiliar para validar os três usuários permitidos na prova
    private boolean validarCredenciais(String u, String p) {
        if (u.equals("Administrador") && p.equals("Administrador")) return true;
        if (u.equals("Adm") && p.equals("Adm")) return true;
        if (u.equals("Administrator") && p.equals("pr4frentef0reve")) return true;
        return false;
    }
}