package com.ed2nd.mywallet.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ed2nd.mywallet.domain.Menu;
import com.ed2nd.mywallet.repositories.MenuRepository;
import com.ed2nd.mywallet.services.exception.ObjectNotFoundException;

@Service
public class MenuService {

	@Autowired
	private MenuRepository repo;

	public List<Menu> findAll() {
		return repo.findAll();
	}

	public Menu find(Integer id) {
		Optional<Menu> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Menu.class.getName()));
	}

	public Menu insert(Menu obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	public Menu update(Menu obj) {
		find(obj.getId());
		return repo.save(obj);
	}
	
	public void delete(Integer id) {
		find(id);
		repo.deleteById(id);
	}

}
