package br.java.operacaoes_crud_com_android_usando_firebase_database.modelo;

public class DBHelper {

    private String usuarioNome;
    private String email;
    private String numero;

    public DBHelper() {
    }

    public DBHelper(String usuarioNome, String email, String numero) {
        this.usuarioNome = usuarioNome;
        this.email = email;
        this.numero = numero;
    }

    public String getUsuarioNome() {
        return usuarioNome;
    }

    public void setUsuarioNome(String usuarioNome) {
        this.usuarioNome = usuarioNome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}