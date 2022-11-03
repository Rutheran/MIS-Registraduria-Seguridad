package tutorial.misionTic.Seguridad.Repositorios.Repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import tutorial.misionTic.Seguridad.Modelos.Usuario;

public interface RepositorioUsuario extends MongoRepository <Usuario, String> {
    @Query("{'correo': ?0}")
    public Usuario getUserByEmail(String correo);
}
