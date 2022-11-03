package tutorial.misionTic.Seguridad.Repositorios.Repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;
import tutorial.misionTic.Seguridad.Modelos.Rol;

public interface RepositorioRol extends MongoRepository <Rol,String> {
}
