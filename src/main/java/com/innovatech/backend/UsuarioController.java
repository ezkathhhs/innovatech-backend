package com.innovatech.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*") // Permite que el frontend se comunique sin bloqueo de CORS
public class UsuarioController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/{usuarioId}/consultar-producto/{productoId}")
    public ResponseEntity<?> consultarProductoDesdeUsuario(@PathVariable Long usuarioId, @PathVariable Long productoId) {
        
        // Aquí usaríamos tu URL real del balanceador de carga apuntando a la API de productos
        String urlProducto = "http://perfulandia-alb-620512428.us-east-1.elb.amazonaws.com/api/productos/" + productoId;
        
        try {
            // El UsuarioService hace una llamada HTTP GET al ProductoService
            Object producto = restTemplate.getForObject(urlProducto, Object.class);
            
            // Armamos una respuesta demostrando la integración
            Map<String, Object> respuestaIntegrada = new HashMap<>();
            respuestaIntegrada.put("mensaje", "El usuario con ID " + usuarioId + " está consultando este producto.");
            respuestaIntegrada.put("datosDelProducto", producto);
            
            return ResponseEntity.ok(respuestaIntegrada);
            
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al contactar al ProductoService: " + e.getMessage());
        }
    }

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

    // 1. Inyectamos la herramienta de comunicación
    @Autowired
    private RestTemplate restTemplate;

    // 2. Creamos el endpoint de integración
    @GetMapping("/{usuarioId}/consultar-perfume/{productoId}")
    public ResponseEntity<?> consultarPerfumeDesdeUsuario(
            @PathVariable Long usuarioId, 
            @PathVariable Long productoId) {
        
        // Usamos la URL exacta de tu Balanceador de Carga apuntando a la ruta de productos
        String urlProducto = "http://perfulandia-alb-620512428.us-east-1.elb.amazonaws.com/api/productos/" + productoId;
        
        try {
            // El UsuarioService "llama por teléfono" al ProductoService
            Object producto = restTemplate.getForObject(urlProducto, Object.class);
            
            // Empaquetamos la respuesta para demostrar que ambos servicios hablaron
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("idUsuarioConsulta", usuarioId);
            respuesta.put("mensaje", "Integración exitosa: El usuario está viendo este perfume.");
            respuesta.put("datosDelPerfume", producto);
            
            return ResponseEntity.ok(respuesta);
            
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error en la integración con ProductoService: " + e.getMessage());
        }
    }
}
