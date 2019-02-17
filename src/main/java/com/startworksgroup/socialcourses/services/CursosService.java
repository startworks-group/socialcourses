package com.startworksgroup.socialcourses.services;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.startworksgroup.socialcourses.domain.Comentario;
import com.startworksgroup.socialcourses.domain.Curso;
import com.startworksgroup.socialcourses.domain.Instituicao;
import com.startworksgroup.socialcourses.repository.ComentariosRepository;
import com.startworksgroup.socialcourses.repository.CursosRepository;
import com.startworksgroup.socialcourses.services.exceptions.EntidadeNaoEncontradaException;

@Service
public class CursosService {

	@Autowired
	private CursosRepository cursosRepository;
	
	@Autowired
	private ComentariosRepository comentariosRepository;
	
	@Autowired
	private InstituicoesService instituicoesService;
	
	public List<Curso> listar(){
		return cursosRepository.findAll();
	}
	
	public Curso buscar(Long id) {
		Curso curso = null;
		
		try {
			curso = cursosRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new EntidadeNaoEncontradaException("O Curso com id:"+id+" Não foi encontrado.");
		}
				
		return curso;
	}
	
	public Curso salvar(Curso curso) {
		
		// Tentar garantir que o curso terá o id nulo
		curso.setId(null);
		
		Instituicao instituicao = curso.getInstituicao();
		
		// Lançando um NullPointException
		if(instituicao == null) {
			throw new NullPointerException("A instituição não foi informada ou informada incorretamente");
		}
		
		instituicoesService.buscar(instituicao.getId());
		
		return cursosRepository.save(curso);
	}
	
	public void deletar(Long id) {
		try {
			cursosRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException("O curso com id:"+id+" Não foi encontrado.");
		}
	}
	
	public void atualizar(Curso curso) {
		verificarExistencia(curso);
		cursosRepository.save(curso);
	}
	
	private void verificarExistencia(Curso curso) {
		buscar(curso.getId());
	}
	
	public Comentario salvarComentario(Long cursoId, Comentario comentario) {
		Curso curso = buscar(cursoId);
		
		comentario.setCurso(curso);
		comentario.setData(new Date());
		
		return comentariosRepository.save(comentario);
	}
	
	public List<Comentario>buscarComentarios(Long cursoId) {
		Curso curso = buscar(cursoId);
		
		return curso.getComentarios();
	}
}
