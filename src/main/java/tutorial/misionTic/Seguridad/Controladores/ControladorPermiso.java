package tutorial.misionTic.Seguridad.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tutorial.misionTic.Seguridad.Modelos.Permiso;
import tutorial.misionTic.Seguridad.Repositorios.Repositorio.RepositorioPermiso;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping ("/permiso")
public class ControladorPermiso {
    public ControladorPermiso(RepositorioPermiso repositorioPermiso) {
        this.repositorioPermiso = repositorioPermiso;
    }

    @Autowired
    private final RepositorioPermiso repositorioPermiso;
    @GetMapping("")
    public List<Permiso> index(){
        return this.repositorioPermiso.findAll();
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Permiso create(@RequestBody Permiso infoPermiso){
        infoPermiso.setUrl(infoPermiso.getUrl());
        infoPermiso.setMetodo(infoPermiso.getMetodo());
        return this.repositorioPermiso.save(infoPermiso);
    }
    @GetMapping("{id}")
    public Permiso show(@PathVariable String id){
        Permiso permisoActual=this.repositorioPermiso
                .findById(id)
                .orElse(null);
        return permisoActual;
    }
    @PutMapping("{id}")
    public Permiso update(@PathVariable String id,@RequestBody Permiso infoPermiso){
        Permiso permisoActual=this.repositorioPermiso
                .findById(id)
                .orElse(null);
        if (permisoActual!=null){
            permisoActual.setUrl(infoPermiso.getUrl());
            permisoActual.setMetodo(infoPermiso.getMetodo());
            return this.repositorioPermiso.save(permisoActual);
        }else{
            return null;
        }
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete (@PathVariable String id){
        Permiso permisoActual=this.repositorioPermiso
                .findById(id)
                .orElse(null);
        if (permisoActual!=null){
            this.repositorioPermiso.delete(permisoActual);
        }
    }

}
