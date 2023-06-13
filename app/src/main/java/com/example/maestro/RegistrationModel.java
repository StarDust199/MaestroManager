package com.example.maestro;

public class RegistrationModel {

        private int id;
        private String login;
        private String password;
        private String email;

        private String role;

        public RegistrationModel() {
        }

        public RegistrationModel(int id, String login, String password, String email, String role) {
            this.id = id;
            this.login = login;
            this.password = password;
            this.email = email;
            this.role = role;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

