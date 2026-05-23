package com.example.coresaula5;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    // Arrays para facilitar a manipulação das 9 mesas
    private LinearLayout[] layoutsMesas = new LinearLayout[9];
    private Button[] botoesReservar = new Button[9];
    private boolean[] statusReservas = new boolean[9]; // true = reservada, false = livre

    private EditText etNumeroMesa;
    private Button btnLiberarMesa, btnSalvarOperacao, btnReservarTodas;

    // Novos botões para Configurações e Logout
    private Button btnConfig, btnLogout;

    // Variável para armazenar a cor atual das mesas reservadas (Padrão: Vermelho)
    private int corReservaHex = 0xFFFF0000;

    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "ReservasPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        inicializarComponentes();
        carregarReservas();
        configurarEventos();
    }

    private void inicializarComponentes() {
        // Mapeando Layouts
        layoutsMesas[0] = findViewById(R.id.linear);
        layoutsMesas[1] = findViewById(R.id.linear2);
        layoutsMesas[2] = findViewById(R.id.linear3);
        layoutsMesas[3] = findViewById(R.id.linear4);
        layoutsMesas[4] = findViewById(R.id.linear5);
        layoutsMesas[5] = findViewById(R.id.linear6);
        layoutsMesas[6] = findViewById(R.id.linear7);
        layoutsMesas[7] = findViewById(R.id.linear8);
        layoutsMesas[8] = findViewById(R.id.linear9);

        // Mapeando Botões das Mesas
        botoesReservar[0] = findViewById(R.id.btnReservar1);
        botoesReservar[1] = findViewById(R.id.btnReservar2);
        botoesReservar[2] = findViewById(R.id.btnReservar3);
        botoesReservar[3] = findViewById(R.id.btnReservar4);
        botoesReservar[4] = findViewById(R.id.btnReservar5);
        botoesReservar[5] = findViewById(R.id.btnReservar6);
        botoesReservar[6] = findViewById(R.id.btnReservar7);
        botoesReservar[7] = findViewById(R.id.btnReservar8);
        botoesReservar[8] = findViewById(R.id.btnReservar9);

        // Controles de baixo
        etNumeroMesa = findViewById(R.id.NmrMesa);
        btnLiberarMesa = findViewById(R.id.LiberarMesa);
        btnSalvarOperacao = findViewById(R.id.SalvarOperacao);
        btnReservarTodas = findViewById(R.id.ReservarTodas);

        // Mapeando os novos botões
        btnConfig = findViewById(R.id.btnConfiguracoes);
        btnLogout = findViewById(R.id.btnLogout);
    }

    private void configurarEventos() {
        // Configura o clique para os 9 botões de "RESERVAR"
        for (int i = 0; i < 9; i++) {
            final int index = i;
            botoesReservar[i].setOnClickListener(v -> reservarMesa(index));
        }

        // Evento: LIBERAR MESA
        btnLiberarMesa.setOnClickListener(v -> {
            String input = etNumeroMesa.getText().toString();
            if (input.isEmpty()) {
                Toast.makeText(this, R.string.numero_invalido, Toast.LENGTH_SHORT).show();
                return;
            }

            int numeroMesa = Integer.parseInt(input);
            if (numeroMesa < 1 || numeroMesa > 9) {
                Toast.makeText(this, R.string.numero_invalido, Toast.LENGTH_SHORT).show();
                return;
            }

            int index = numeroMesa - 1; // Índices do array vão de 0 a 8

            if (statusReservas[index]) {
                liberarMesa(index);
            } else {
                // Mesa não estava reservada
                String msg = getString(R.string.mesa_habilitada, numeroMesa);
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            }
            etNumeroMesa.setText(""); // Limpa o campo
        });

        // Evento: SALVAR OPERAÇÃO (Shared Preferences)
        btnSalvarOperacao.setOnClickListener(v -> salvarReservas());

        // Evento: RESERVAR TODAS AS MESAS
        btnReservarTodas.setOnClickListener(v -> reservarTodas());

        // Evento: CONFIGURAÇÕES (Mudar cor)
        btnConfig.setOnClickListener(v -> abrirDialogoCores());

        // Evento: LOGOUT (Limpar preferências e sair)
        btnLogout.setOnClickListener(v -> realizarLogout());
    }

    private void reservarMesa(int index) {
        statusReservas[index] = true;
        // Substituído o R.color.red fixo pela variável de cor dinâmica corReservaHex
        layoutsMesas[index].setBackgroundColor(corReservaHex);
        botoesReservar[index].setEnabled(false);
    }

    private void liberarMesa(int index) {
        statusReservas[index] = false;
        layoutsMesas[index].setBackgroundColor(ContextCompat.getColor(this, R.color.blue));
        botoesReservar[index].setEnabled(true);
    }

    private void reservarTodas() {
        boolean todasJaReservadas = true;

        for (boolean status : statusReservas) {
            if (!status) {
                todasJaReservadas = false;
                break;
            }
        }

        if (todasJaReservadas) {
            Toast.makeText(this, R.string.todas_reservadas, Toast.LENGTH_LONG).show();
        } else {
            for (int i = 0; i < 9; i++) {
                if (!statusReservas[i]) {
                    reservarMesa(i);
                }
            }
        }
    }

    // --- REQUISITOS NOVOS: CORES E LOGOUT ---

    private void abrirDialogoCores() {
        // Opções de cores disponíveis
        String[] cores = {
                getString(R.string.cor_vermelha),
                getString(R.string.cor_verde),
                getString(R.string.cor_laranja)
        };

        // Valores em Hexadecimal correspondentes (Alpha, R, G, B)
        int[] valoresHex = { 0xFFFF0000, 0xFF00FF00, 0xFFFFA500 };

        new AlertDialog.Builder(this)
                .setTitle(R.string.escolha_cor)
                .setItems(cores, (dialog, which) -> {
                    corReservaHex = valoresHex[which];
                    // Salva a preferência de cor imediatamente no Shared Preferences
                    sharedPreferences.edit().putInt("cor_mesa", corReservaHex).apply();
                    // Atualiza visualmente as mesas que já estão reservadas com a nova cor
                    atualizarCoresInterface();
                }).show();
    }

    private void atualizarCoresInterface() {
        // Passa por todas as mesas e pinta com a nova cor apenas as que estão reservadas
        for (int i = 0; i < 9; i++) {
            if (statusReservas[i]) {
                layoutsMesas[i].setBackgroundColor(corReservaHex);
            }
        }
    }

    private void realizarLogout() {
        // Limpa TODAS as preferências salvas (Mesas reservadas e Cores alteradas)
        sharedPreferences.edit().clear().apply();

        // Retorna para a tela de Login
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

        // Finaliza a MainActivity
        finish();
    }

    // --- SHARED PREFERENCES ---

    private void salvarReservas() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i = 0; i < 9; i++) {
            editor.putBoolean("mesa_" + i, statusReservas[i]);
        }
        editor.apply();
        Toast.makeText(this, R.string.operacao_salva, Toast.LENGTH_SHORT).show();
    }

    private void carregarReservas() {
        // Carrega a cor salva pelo usuário (se não houver cor salva, usa o vermelho padrão)
        corReservaHex = sharedPreferences.getInt("cor_mesa", 0xFFFF0000);

        for (int i = 0; i < 9; i++) {
            boolean isReservada = sharedPreferences.getBoolean("mesa_" + i, false);
            if (isReservada) {
                reservarMesa(i); // Já vai utilizar a cor carregada (corReservaHex)
            } else {
                liberarMesa(i);
            }
        }
    }
}