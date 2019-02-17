package com.startworksgroup.socialcourses.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.startworksgroup.socialcourses.domain.Instituicao;
import com.startworksgroup.socialcourses.services.InstituicoesService;

@RestController
@RequestMapping("/instituicoes")
public class InstituicoesResources {

	@Autowired
	private InstituicoesService instituicoesService;
	
	@GetMapping
	public ResponseEntity<List<Instituicao>> listar() {
		List<Instituicao> result = instituicoesService.listar();
		
		return ResponseEntity.ok().body(result);
		
	}
	
	@PostMapping
	public ResponseEntity<Void> salvar(@RequestBody Instituicao instituicao) {
		instituicao = instituicoesService.salvar(instituicao);	
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(instituicao.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Void> atualizar(
			@RequestBody Instituicao instituicao, 
			@PathVariable("id") Long id
		) {
		
		// Tentar garantir que a instituicao não terá o id nulo
		instituicao.setId(id);
		
		instituicoesService.atualizar(instituicao);
		
		return ResponseEntity.noContent().build();
	}
	@GetMapping("{id}")
	public ResponseEntity<?> buscar(@PathVariable("id") Long id) {
		
		Instituicao instituicao = instituicoesService.buscar(id);
		
		return ResponseEntity.ok().body(instituicao);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Void> deletar(@PathVariable("id") Long id) {
		
		instituicoesService.deletar(id);
		
		return ResponseEntity.noContent().build();
	}
}
