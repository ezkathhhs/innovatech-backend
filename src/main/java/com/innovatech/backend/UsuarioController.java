package com.innovatech.backend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*") // Permite que el frontend se comunique sin bloqueo de CORS
public class UsuarioController {
    @Autowired
    private UsuarioRepository repository;

    @GetMapping
    public List<Usuario> listar() { return repository.findAll(); }

    @PostMapping
    public Usuario crear(@RequestBody Usuario usuario) { return repository.save(usuario); }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) { repository.deleteById(id); }

    @PutMapping("/{id}")
    public Usuario actualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        usuario.setId(id);
        return repository.save(usuario);
    }
}
