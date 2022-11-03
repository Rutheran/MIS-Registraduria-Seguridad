package tutorial.misionTic.Seguridad.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tutorial.misionTic.Seguridad.Modelos.Rol;
import tutorial.misionTic.Seguridad.Modelos.Usuario;
import tutorial.misionTic.Seguridad.Repositorios.Repositorio.RepositorioRol;
import tutorial.misionTic.Seguridad.Repositorios.Repositorio.RepositorioUsuario;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/usuario")
public class ControladorUsuario {
    public ControladorUsuario(RepositorioUsuario repositorioUsuario, RepositorioRol repositorioRol) {
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioRol = repositorioRol;
    }
    @Autowired
    private final RepositorioUsuario repositorioUsuario;
    @Autowired
    private final RepositorioRol repositorioRol;
    @GetMapping("")
    public List<Usuario> index(){
        return this.repositorioUsuario.findAll();
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Usuario create(@RequestBody Usuario infoUsuario){
        infoUsuario.setSeudonimo(infoUsuario.getSeudonimo());
        infoUsuario.setCorreo(infoUsuario.getCorreo());
        infoUsuario.setContraseña(convertirSHA256(infoUsuario.getContraseña()));
        return this.repositorioUsuario.save(infoUsuario);
    }

    @GetMapping("{id}")
    public Usuario show(@PathVariable String id){
        Usuario usuarioActual=this.repositorioUsuario
                .findById(id)
                .orElse(null);
        return usuarioActual;
    }
    @PutMapping("{id}")
    public Usuario update(@PathVariable String id,@RequestBody Usuario infoUsuario){
        Usuario usuarioActual=this.repositorioUsuario
                .findById(id)
                .orElse(null);
        if (usuarioActual!=null){
            usuarioActual.setSeudonimo(infoUsuario.getSeudonimo());
            usuarioActual.setCorreo(infoUsuario.getCorreo());
            usuarioActual.setContraseña(convertirSHA256(infoUsuario.getContraseña()))
            ;
            return this.repositorioUsuario.save(usuarioActual);
        }else{
            return null;
        }
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        Usuario usuarioActual=this.repositorioUsuario
                .findById(id)
                .orElse(null);
        if (usuarioActual!=null){
            this.repositorioUsuario.delete(usuarioActual);
        }
    }
    public String convertirSHA256(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        byte[] hash = md.digest(password.getBytes());
        StringBuffer sb = new StringBuffer();
        for(byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    /**
     * Relación (1 a n) entre rol y usuario
     * @param id
     * @param id_rol
     * @return
     */
    @PutMapping("{id}/rol/{id_rol}")
    public Usuario asignarRolAUsuario(@PathVariable String id,@PathVariable  String id_rol){
        Usuario  usuarioActual=this.repositorioUsuario
                .findById(id)
                .orElseThrow(RuntimeException::new);
        Rol rolActual=this.repositorioRol
                .findById(id_rol)
                .orElseThrow(RuntimeException::new);
        usuarioActual.setRol(rolActual);
        return this.repositorioUsuario.save(usuarioActual);
    }
    @PostMapping("/validar")
    public Usuario validate(@RequestBody Usuario infoUsuario,
                            final HttpServletResponse response) throws IOException {
        Usuario usuarioActual = this.repositorioUsuario
                .getUserByEmail(infoUsuario.getCorreo());
        if (usuarioActual != null &&
                usuarioActual.getContraseña().equals(convertirSHA256(infoUsuario.getContraseña()))) {
            usuarioActual.setContraseña("");
            return usuarioActual;
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }
    }
}
