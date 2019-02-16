package com.startworksgroup.socialcourses.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.startworksgroup.socialcourses.domain.Curso;
import com.startworksgroup.socialcourses.repository.CursosRepository;
import com.startworksgroup.socialcourses.services.exceptions.CursoNaoEncontradoException;

@Service
public class CursosService {

	@Autowired
	private CursosRepository cursosRepository;
	
	public List<Curso> listar(){
		return cursosRepository.findAll();
	}
	
	public Optional<Curso> buscar(Long id) {
		Optional<Curso> curso = cursosRepository.findById(id);
		
		if (!curso.isPresent()){
			throw new CursoNaoEncontradoException("O curso com id:"+id+" Não foi encontrado.");
		}
		
		return curso;
	}
	
	public Curso salvar(Curso curso) {
		
		// Tentar garantir que o curso terá o id nulo
		curso.setId(null);
		
		return cursosRepository.save(curso);
	}
	
	public void deletar(Long id) {
		try {
			cursosRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new CursoNaoEncontradoException("O curso com id:"+id+" Não foi encontrado.");
		}
	}
	
	public void atualizar(Curso curso) {
		verificarExistencia(curso);
		cursosRepository.save(curso);
	}
	
	private void verificarExistencia(Curso curso) {
		buscar(curso.getId());
	}
}
