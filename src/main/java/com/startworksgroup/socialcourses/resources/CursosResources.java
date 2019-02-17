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

import com.startworksgroup.socialcourses.domain.Comentario;
import com.startworksgroup.socialcourses.domain.Curso;
import com.startworksgroup.socialcourses.services.CursosService;

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
		
		curso = cursosService.salvar(curso);
		
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
		
		cursosService.atualizar(curso);
			
		return ResponseEntity.noContent().build();
	}
	@GetMapping("{id}")
	public ResponseEntity<?> buscar(@PathVariable("id") Long id) {
		
		Curso curso = cursosService.buscar(id);
		
		return ResponseEntity.status(HttpStatus.OK).body(curso); 
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Void> deletar(@PathVariable("id") Long id) {

		cursosService.deletar(id);
				
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("{id}/comentarios")
	public ResponseEntity<Void> adicionarComentario(
			@PathVariable("id") Long cursoId,
			@RequestBody Comentario comentario
			) {
		
		cursosService.salvarComentario(cursoId, comentario);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
}
