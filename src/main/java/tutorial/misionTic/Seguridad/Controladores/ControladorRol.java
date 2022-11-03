package tutorial.misionTic.Seguridad.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tutorial.misionTic.Seguridad.Modelos.Rol;
import tutorial.misionTic.Seguridad.Repositorios.Repositorio.RepositorioRol;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping ("/rol")
public class ControladorRol {
    private ControladorRol(RepositorioRol repositorioRol) {
        this.repositorioRol = repositorioRol;
    }

    @Autowired
    private final RepositorioRol repositorioRol;

    @GetMapping("")
    public List<Rol> index() {
        return this.repositorioRol.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Rol create(@RequestBody Rol infoRol) {
        infoRol.setNombre(infoRol.getNombre());
        infoRol.setDescripcion(infoRol.getDescripcion());
        return this.repositorioRol.save(infoRol);
    }

    @GetMapping("{id}")
    public Rol show(@PathVariable String id) {
        Rol rolActual = this.repositorioRol
                .findById(id)
                .orElse(null);
        return rolActual;
    }

    @PutMapping("{id}")
    public Rol update(@PathVariable String id, @RequestBody Rol infoRol) {
        Rol rolActual = this.repositorioRol
                .findById(id)
                .orElse(null);
        if (rolActual != null) {
            rolActual.setNombre(infoRol.getNombre());
            rolActual.setDescripcion(infoRol.getDescripcion());
            return this.repositorioRol.save(rolActual);
        } else {
            return null;
        }

    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        Rol rolActual = this.repositorioRol
                .findById(id)
                .orElse(null);
        if (rolActual != null) {
            this.repositorioRol.delete(rolActual);
        }
    }
}