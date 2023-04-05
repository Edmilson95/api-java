package br.com.alura.linguagens.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class LinguagemController {
   
    @Autowired
    private LinguagemRepository repositorio;

    //read
    @GetMapping("/linguagens")
    public List<Linguagem> obterLinguagens(){
        List<Linguagem> linguagens = repositorio.findByOrderByRanking();
        return linguagens;
    }

    //create
    @PostMapping("/linguagens")
    public Linguagem cadastrarLinguagem(@RequestBody Linguagem linguagem) {
        Linguagem linguagemSalva = repositorio.save(linguagem);
        return linguagemSalva;
    } 

    @GetMapping("/linguagens/{id}")
    public Linguagem obterLinguagemPorId(@PathVariable String id){ //path variable retorna o que está na url
        return repositorio.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    //update(atualizar)
    @PutMapping("/linguagens/{id}")
    public Linguagem atualizarLinguagem(@PathVariable String id, @RequestBody Linguagem linguagem) {//request body retorna o que está na requisição
        if(!repositorio.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        linguagem.setId(id);
        Linguagem linguagemSalva = repositorio.save(linguagem);
        return linguagemSalva;
    }

    //delete
    @DeleteMapping("/linguagens/{id}")
    public void excluirLinguagem(@PathVariable String id){
        repositorio.deleteById(id);
    }
}
