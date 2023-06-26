package br.com.dsw.LionByte.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dsw.LionByte.model.Usuario;
import br.com.dsw.LionByte.repository.UsuarioRepository;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @PostMapping
    public Usuario salvarUsuario(@RequestBody Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @GetMapping("/{id}")
    public Usuario getUsuario(@PathVariable Long id) throws Exception{
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if(usuario.isEmpty()){
            throw new Exception("Erro: Id de Produto não encontrado " + id);
        }
        return usuario.get();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirUsuario(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public Map<String, Object> realizarLogin(@RequestBody Map<String, String> loginParams) {
        String email = loginParams.get("email");
        String senha = loginParams.get("senha");

        Map<String, Object> response = new HashMap<>();

        // Consultar o usuário no banco de dados
        Usuario usuario = usuarioRepository.findByEmail(email);

        if (usuario != null && usuario.getSenha().equals(senha)) {
            response.put("msg", "Login realizado com sucesso!");
            response.put("is_admin", usuario.isAdmin()); 
            response.put("id_usuario", usuario.getId_usuario()); 
        } 
        else {
            response.put("msg", "Email ou senha inválidos.");
            response.put("is_admin", false);
            response.put("id_usuario", 0); 
        }

        return response;
    }
}

