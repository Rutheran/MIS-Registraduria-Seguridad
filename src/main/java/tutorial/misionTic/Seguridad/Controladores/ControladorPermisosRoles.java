package tutorial.misionTic.Seguridad.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tutorial.misionTic.Seguridad.Modelos.Permiso;
import tutorial.misionTic.Seguridad.Modelos.PermisosRoles;
import tutorial.misionTic.Seguridad.Modelos.Rol;
import tutorial.misionTic.Seguridad.Repositorios.Repositorio.RepositorioPermiso;
import tutorial.misionTic.Seguridad.Repositorios.Repositorio.RepositorioPermisosRoles;
import tutorial.misionTic.Seguridad.Repositorios.Repositorio.RepositorioRol;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/permisos-roles")
public class ControladorPermisosRoles {
    @Autowired
    private RepositorioPermisosRoles repositorioPermisoRoles;
    @Autowired
    private RepositorioPermiso repositorioPermiso;
    @Autowired
    private RepositorioRol repositorioRol;

    @GetMapping("")
    public List<PermisosRoles> index(){
        return this.repositorioPermisoRoles.findAll();
    }
    /**

     * Asignación rol y permiso
     * @param id_rol
     * @param id_permiso
     * @return
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("rol/{id_rol}/permiso/{id_permiso}")
    public PermisosRoles create(@PathVariable String id_rol,@PathVariable String id_permiso){
        PermisosRoles nuevo=new PermisosRoles();
        Rol elRol=this.repositorioRol.findById(id_rol).get();
        Permiso elPermiso=this.repositorioPermiso.findById(id_permiso).get();
        if (elRol!=null && elPermiso!=null){
            nuevo.setPermiso(elPermiso);
            nuevo.setRol(elRol);
            return this.repositorioPermisoRoles.save(nuevo);
        }else{
            return null;
        }
    }
    @GetMapping("{id}")
    public PermisosRoles show(@PathVariable String id){
        PermisosRoles permisosRolesActual=this.repositorioPermisoRoles
                .findById(id)
                .orElse(null);
        return permisosRolesActual;
    }
    /**
     * Modificación Rol y Permiso
     * @param id
     * @param id_rol
     * @param id_permiso
     * @return
     */
    @PutMapping("{id}/rol/{id_rol}/permiso/{id_permiso}")
    public PermisosRoles update(@PathVariable String id,@PathVariable String id_rol,@PathVariable String id_permiso){
        PermisosRoles permisosRolesActual=this.repositorioPermisoRoles
                .findById(id)
                .orElse(null);
        Rol elRol=this.repositorioRol.findById(id_rol).get();
        Permiso elPermiso=this.repositorioPermiso.findById(id_permiso).get();
        if(permisosRolesActual!=null && elPermiso!=null && elRol!=null){
            permisosRolesActual.setPermiso(elPermiso);
            permisosRolesActual.setRol(elRol);
            return
                    this.repositorioPermisoRoles.save(permisosRolesActual);
        }else{
            return null;
        }
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        PermisosRoles permisosRolesActual=this.repositorioPermisoRoles
                .findById(id)
                .orElse(null);
        if (permisosRolesActual!=null){
            this.repositorioPermisoRoles.delete(permisosRolesActual);
        }
    }
    /**VALIDAR PERMISO*/

    @GetMapping("validar-permiso/rol/{id_rol}")
    public PermisosRoles getPermiso(@PathVariable String id_rol,@RequestBody
    Permiso infoPermiso) {
        Permiso elPermiso = this.repositorioPermiso
                .getPermiso(infoPermiso.getUrl(),
                        infoPermiso.getMetodo());
        Rol elRol = this.repositorioRol.findById(id_rol).get();
        if (elPermiso != null && elRol != null) {
            return
                    this.repositorioPermisoRoles.getPermisoRol(elRol.getId(), elPermiso.getId());
        } else {
            return null;
        }
    }
}
