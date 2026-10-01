package com.example.diarioderede.model;

/**
 * Modelo de uma nota do diário.
 * Cada nota possui texto, data/hora e o tipo de rede detectado no momento do registo.
 */
public class NetworkNote {
    private final String text;
    private final String dateTime;
    private final String networkType;

    public NetworkNote(String text, String dateTime, String networkType) {
        this.text = text;
        this.dateTime = dateTime;
        this.networkType = networkType;
    }

    public String getText() {
        return text;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getNetworkType() {
        return networkType;
    }
}
