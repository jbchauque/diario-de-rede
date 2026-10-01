package com.example.diarioderede;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.diarioderede.util.NetworkUtils;

/**
 * Activity principal: apresenta o estado atual da ligação e encaminha o utilizador para o diário.
 */
public class MainActivity extends AppCompatActivity {

    private TextView tvNetworkStatus;
    private TextView tvNetworkDetails;
    private ConnectivityManager connectivityManager;
    private ConnectivityManager.NetworkCallback networkCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvNetworkStatus = findViewById(R.id.tvNetworkStatus);
        tvNetworkDetails = findViewById(R.id.tvNetworkDetails);
        Button btnRefresh = findViewById(R.id.btnRefresh);
        Button btnAddNote = findViewById(R.id.btnAddNote);
        Button btnViewDiary = findViewById(R.id.btnViewDiary);

        btnRefresh.setOnClickListener(v -> updateNetworkStatus());
        btnAddNote.setOnClickListener(v -> openDiary());
        btnViewDiary.setOnClickListener(v -> openDiary());

        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        registerNetworkCallback();
        updateNetworkStatus();
    }

    private void openDiary() {
        Intent intent = new Intent(MainActivity.this, DiaryActivity.class);
        startActivity(intent);
    }

    private void updateNetworkStatus() {
        String networkName = NetworkUtils.getNetworkTypeName(this);
        int type = NetworkUtils.getNetworkType(this);

        tvNetworkStatus.setText(networkName);

        switch (type) {
            case NetworkUtils.WIFI:
                tvNetworkDetails.setText("O dispositivo está ligado através de uma rede Wi-Fi.");
                break;
            case NetworkUtils.MOBILE:
                tvNetworkDetails.setText("O dispositivo está ligado através de dados móveis.");
                break;
            case NetworkUtils.NONE:
                tvNetworkDetails.setText("O dispositivo não possui uma ligação de rede activa.");
                break;
            default:
                tvNetworkDetails.setText("Foi detectada uma rede, mas o tipo não foi identificado.");
                break;
        }
    }

    private void registerNetworkCallback() {
        if (connectivityManager == null) {
            return;
        }

        networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                runOnUiThread(() -> updateNetworkStatus());
            }

            @Override
            public void onLost(@NonNull Network network) {
                runOnUiThread(() -> updateNetworkStatus());
            }

            @Override
            public void onCapabilitiesChanged(@NonNull Network network,
                                              @NonNull NetworkCapabilities networkCapabilities) {
                runOnUiThread(() -> updateNetworkStatus());
            }
        };

        try {
            connectivityManager.registerDefaultNetworkCallback(networkCallback);
        } catch (Exception ignored) {
            // O botão "Actualizar" continua disponível como alternativa.
        }
    }

    @Override
    protected void onDestroy() {
        if (connectivityManager != null && networkCallback != null) {
            try {
                connectivityManager.unregisterNetworkCallback(networkCallback);
            } catch (Exception ignored) {
                // Callback já removido ou indisponível.
            }
        }
        super.onDestroy();
    }
}
