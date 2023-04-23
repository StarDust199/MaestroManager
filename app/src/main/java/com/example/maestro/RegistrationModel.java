package com.example.maestro;

public class RegistrationModel {

        public static final String TABLE_NAME = "registration";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_LOGIN = "login";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_EMAIL = "email";

        private int id;
        private String login;
        private String password;
        private String email;

        // Tworzenie tabeli rejestracji
        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + "("
                        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + COLUMN_LOGIN + " TEXT,"
                        + COLUMN_PASSWORD + " TEXT,"
                        + COLUMN_EMAIL + " TEXT"
                        + ")";

        public RegistrationModel() {
        }

        public RegistrationModel(int id, String login, String password, String email) {
            this.id = id;
            this.login = login;
            this.password = password;
            this.email = email;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

