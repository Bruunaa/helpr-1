package com.api.helpr.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.helpr.domain.Cliente;
import com.api.helpr.domain.Pessoa;
import com.api.helpr.domain.dtos.ClienteDTO;
import com.api.helpr.repositories.ClienteRepository;
import com.api.helpr.repositories.PessoaRepository;
import com.api.helpr.services.exceptions.DataIntegrityViolationException;
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

	public List<Cliente> findAllClientes() {
		return repository.findAll();
	}

		//Método que fará a criação de novo técnico.
		public Cliente create(ClienteDTO objDto) {
			objDto.setId(null);
			validaCpfEEmail(objDto);
			Cliente newObj = new Cliente(objDto);
		return repository.save(newObj);
	}

	
		//Método para modificar clientes existentes.
		public Cliente update(Integer id, ClienteDTO objDto) {
			objDto.setId(id);
			Cliente oldObj = findById(id);
			validaCpfEEmail(objDto);
			oldObj = new Cliente(objDto);
			return repository.save(oldObj);
		}
		
		//Excluirá um cliente pela ordem do endpoint.
		public void delete(Integer id) {
			Cliente obj = findById(id);
			if(obj.getChamados().size() > 0) {
				throw new DataIntegrityViolationException("O Cliente: "+
			id+" tem chamados no sistema: "+
			obj.getChamados().size());
			}
			repository.deleteById(id);
		}
		

		//Validará os CPFs e E-mails para update e create. 
		private void validaCpfEEmail(ClienteDTO objDto) {
			
			Optional<Pessoa> obj = pessoaRepository.findByCpf(objDto.getCpf());
			if (obj.isPresent() && obj.get().getId() != objDto.getId()) {
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
		}
			obj = pessoaRepository.findByEmail(objDto.getEmail());
			if (obj.isPresent() && obj.get().getId() != objDto.getId()) {
			throw new DataIntegrityViolationException("E-mail já cadastrado no sistema!");
		}
	}

	
}
