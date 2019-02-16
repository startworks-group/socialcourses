package com.startworksgroup.socialcourses.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.startworksgroup.socialcourses.domain.Instituicao;
import com.startworksgroup.socialcourses.repository.InstituicoesRepository;
import com.startworksgroup.socialcourses.services.exceptions.EntidadeNaoEncontradaException;

@Service
public class InstituicoesService {

	@Autowired
	private InstituicoesRepository instituicoesRepository;
	
	public List<Instituicao> listar(){
		return instituicoesRepository.findAll();
	}
	
	public Optional<Instituicao> buscar(Long id) {
		Optional<Instituicao> instituicao = instituicoesRepository.findById(id);
		
		if (!instituicao.isPresent()){
			throw new EntidadeNaoEncontradaException("A instituição com id:"+id+" Não foi encontrado.");
		}
		
		return instituicao;
	}
	
	public Instituicao salvar(Instituicao instituicao) {
		
		// Tentar garantir que a instituicao terá o id nulo
		instituicao.setId(null);
		
		return instituicoesRepository.save(instituicao);
	}
	
	public void deletar(Long id) {
		try {
			instituicoesRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException("A instituição com id:"+id+" Não foi encontrado.");
		}
	}
	
	public void atualizar(Instituicao instituicao) {
		verificarExistencia(instituicao);
		instituicoesRepository.save(instituicao);
	}
	
	private void verificarExistencia(Instituicao instituicao) {
		buscar(instituicao.getId());
	}
	
	public void throwInstituicaoNullException() {
		throw new EntidadeNaoEncontradaException("A instituição Não foi informada.");
	}
}
