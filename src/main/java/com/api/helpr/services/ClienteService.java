package com.api.helpr.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.helpr.domain.Cliente;
import com.api.helpr.repositories.ClienteRepository;
import com.api.helpr.repositories.PessoaRepository;
import com.api.helpr.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired //Vinculo com repositório.
	private ClienteRepository repository;

	@Autowired //Vinculo com repositório de pessoa.
	private PessoaRepository pessoaRepository;

	//Métoido de busca por um ID no banco.
	public Cliente findById(Integer id) {
		Optional<Cliente> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não foi encontrado: " + id));
	}

	
}
