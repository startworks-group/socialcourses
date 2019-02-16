package com.startworksgroup.socialcourses.resources;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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

import com.startworksgroup.socialcourses.domain.Curso;
import com.startworksgroup.socialcourses.services.CursosService;
import com.startworksgroup.socialcourses.services.exceptions.CursoNaoEncontradoException;
import com.startworksgroup.socialcourses.services.exceptions.InstituicaoNaoEncontradaException;

@RestController
@RequestMapping("/cursos")
public class CursosResources {

	@Autowired
	private CursosService cursosService;
	
	@GetMapping
	public ResponseEntity<List<Curso>> listar() {
		List<Curso> result = cursosService.listar();
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody Curso curso) {
		
		try {
			curso = cursosService.salvar(curso);
		} catch (InstituicaoNaoEncontradaException e) {
			return ResponseEntity.badRequest().build();
		}
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(curso.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Void> atualizar(
			@RequestBody Curso curso, 
			@PathVariable("id") Long id
		) {
		
		// Tentar garantir que o curso não terá o id nulo
		curso.setId(id);
		
		try {
			cursosService.atualizar(curso);
		} catch (CursoNaoEncontradoException e) {
			return ResponseEntity.notFound().build();
		}
			
		return ResponseEntity.noContent().build();
	}
	@GetMapping("{id}")
	public ResponseEntity<?> buscar(@PathVariable("id") Long id) {
		Optional<Curso> curso = null;
		
		try {
			curso = cursosService.buscar(id);
		} catch (CursoNaoEncontradoException e) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(curso); 
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Void> deletar(@PathVariable("id") Long id) {

		try {
			cursosService.deletar(id);
		} catch (CursoNaoEncontradoException e) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}
}
