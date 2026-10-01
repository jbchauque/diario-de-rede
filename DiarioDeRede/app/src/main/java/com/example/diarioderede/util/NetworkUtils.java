package com.example.diarioderede.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;

import com.example.diarioderede.R;

/**
 * Classe utilitária responsável por consultar o estado REAL da ligação de rede.
 */
public final class NetworkUtils {

    public static final int WIFI = 1;
    public static final int MOBILE = 2;
    public static final int NONE = 3;
    public static final int UNKNOWN = 4;

    private NetworkUtils() {
        // Classe utilitária: não deve ser instanciada.
    }

    public static int getNetworkType(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return UNKNOWN;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network activeNetwork = connectivityManager.getActiveNetwork();
            if (activeNetwork == null) {
                return NONE;
            }

            NetworkCapabilities capabilities =
                    connectivityManager.getNetworkCapabilities(activeNetwork);

            if (capabilities == null) {
                return NONE;
            }

            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                return WIFI;
            }

            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                return MOBILE;
            }

            return UNKNOWN;
        }

        // Compatibilidade para versões antigas do Android.
        android.net.NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            return NONE;
        }

        if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return WIFI;
        }

        if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return MOBILE;
        }

        return UNKNOWN;
    }

    public static String getNetworkTypeName(Context context) {
        switch (getNetworkType(context)) {
            case WIFI:
                return context.getString(R.string.wifi);
            case MOBILE:
                return context.getString(R.string.mobile_data);
            case NONE:
                return context.getString(R.string.no_connection);
            default:
                return context.getString(R.string.unknown_network);
        }
    }
}
