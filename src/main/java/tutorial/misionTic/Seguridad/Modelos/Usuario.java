package tutorial.misionTic.Seguridad.Modelos;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document

public class Usuario {
    @Id
    private String id;
    private String seudonimo;
    private String correo;
    private String contraseña;
    @DBRef
    private Rol rol;

    public Usuario(String seudonimo, String correo, String contraseña) {
        this.seudonimo = seudonimo;
        this.correo = correo;
        this.contraseña = contraseña;

    }

    public void getId(String id) {
        this.id = id;
    }

    public String getSeudonimo() {
        return seudonimo;
    }

    public void setSeudonimo(String seudonimo) {
        this.seudonimo = seudonimo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}
