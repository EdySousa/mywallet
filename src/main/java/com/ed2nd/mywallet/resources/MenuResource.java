package com.ed2nd.mywallet.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ed2nd.mywallet.domain.Menu;
import com.ed2nd.mywallet.dto.MenuDTO;
import com.ed2nd.mywallet.services.MenuService;

@RestController
@RequestMapping(value = "/menu")
public class MenuResource {

	@Autowired
	private MenuService service;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<MenuDTO>> findAll() {
		List<Menu> list = service.findAll();
		List<MenuDTO> listDto = list.stream().map(obj -> new MenuDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<MenuDTO> find(@PathVariable Integer id) {
		Menu obj = service.find(id);
		MenuDTO objDto = new MenuDTO(obj);
		return ResponseEntity.ok().body(objDto);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody MenuDTO objDto) {
		Menu obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody MenuDTO objDto, @PathVariable Integer id) {
		Menu obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
