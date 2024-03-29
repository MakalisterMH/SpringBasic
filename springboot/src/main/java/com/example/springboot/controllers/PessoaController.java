package com.example.springboot.controllers;


import com.example.springboot.dtos.PessoaRecordDto;
import com.example.springboot.models.PessoaModel;
import com.example.springboot.repositories.PessoaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class PessoaController {

    @Autowired
    PessoaRepository pessoaRepository;

@PostMapping("/pessoas")
    public ResponseEntity<PessoaModel> savePessoa(@RequestBody @Valid PessoaRecordDto pessoaRecordDto){
    var pessoaModel = new PessoaModel();
    BeanUtils.copyProperties(pessoaRecordDto , pessoaModel);
    return ResponseEntity.status(HttpStatus.CREATED).body(pessoaRepository.save(pessoaModel));

}

@GetMapping("/pessoas")
    public ResponseEntity <List<PessoaModel>> buscarPessoas(){
    return ResponseEntity.status(HttpStatus.OK).body(pessoaRepository.findAll());
    }

@GetMapping("/pessoas/{id}")
public ResponseEntity<Object> buscarPessoa(@PathVariable(value = "id")UUID id){
    Optional<PessoaModel> pessoaO = pessoaRepository.findById(id);
    if(pessoaO.isEmpty()){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não existe");
    }
    return ResponseEntity.status(HttpStatus.OK).body(pessoaO.get());
}

@PutMapping("/pessoas/{id}")
    public ResponseEntity<Object> atualizarPessoa(@PathVariable(value = "id")UUID id,
                                                  @Valid @RequestBody PessoaRecordDto pessoaRecordDto){
    Optional<PessoaModel> pessoa0 = pessoaRepository.findById(id);
    if(pessoa0.isEmpty()){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa Não existe");
    }

    var pessoaModel = pessoa0.get();
    BeanUtils.copyProperties(pessoaRecordDto, pessoaModel);
    return ResponseEntity.status(HttpStatus.OK).body(pessoaRepository.save(pessoaModel));
}

@DeleteMapping("/pessoas/{id}")
public ResponseEntity<Object> deletarPessoa(@PathVariable(value = "id")UUID id){
    Optional<PessoaModel> pessoa0 = pessoaRepository.findById(id);
    if(pessoa0.isEmpty()){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa Não existe");
    }

    var pessoaModel = pessoa0.get();
    pessoaRepository.delete(pessoa0.get());
    return ResponseEntity.status(HttpStatus.OK).body("Pessoa deletada com Sucesso");
}

}
