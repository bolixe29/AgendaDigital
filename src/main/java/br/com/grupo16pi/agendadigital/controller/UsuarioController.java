package br.com.grupo16pi.agendadigital.controller;

import br.com.grupo16pi.agendadigital.model.Usuario;
import br.com.grupo16pi.agendadigital.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // API REST
@RequestMapping("/usuarios") // Caminho: /usuarios
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService; // Lida com a lógica de usuário

    @GetMapping // GET em /usuarios: Lista todos
    public List<Usuario> findAll() {
        return usuarioService.findAll();
    }

    @GetMapping("/{id}") // GET em /usuarios/{id}: Busca por ID
    public Optional<Usuario> findById(@PathVariable Long id) {
        return usuarioService.findById(id);
    }

    @PostMapping // POST em /usuarios: Salva novo
    public Usuario save(@RequestBody Usuario usuario) {
        return usuarioService.save(usuario);
    }

    @PutMapping("/{id}") // PUT em /usuarios/{id}: Atualiza por ID
    public Usuario update(@PathVariable Long id, @RequestBody Usuario usuario) {
        usuario.setId(id);
        return usuarioService.save(usuario);
    }

    @DeleteMapping("/{id}") // DELETE em /usuarios/{id}: Deleta por ID
    public void deleteById(@PathVariable Long id) {
        usuarioService.deleteById(id);
    }
}