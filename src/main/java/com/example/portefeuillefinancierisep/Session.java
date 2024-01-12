package com.example.portefeuillefinancierisep;

public class Session {
    private static int userId; // Stocke l'ID de l'utilisateur connecté

    public static void setUserId(int id) {
        userId = id;
    }

    public static int getUserId() {
        return userId;
    }
}
