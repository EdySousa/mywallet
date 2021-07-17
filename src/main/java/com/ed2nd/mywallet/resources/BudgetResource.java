package com.ed2nd.mywallet.resources;

import java.net.URI;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ed2nd.mywallet.domain.Budget;
import com.ed2nd.mywallet.dto.BudgetNewDTO;
import com.ed2nd.mywallet.services.BudgetService;

@RestController
@RequestMapping(value = "/budgets")
public class BudgetResource {

	@Autowired
	private BudgetService service;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> findAll() {
		List<Budget> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Budget> find(@PathVariable Integer id) {
		Budget obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Budget> insert(@Valid @RequestBody BudgetNewDTO objDto) {
		Budget obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody Budget obj, @PathVariable Integer id) {
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/user/{userID}", method = RequestMethod.GET)
	public ResponseEntity<List<Budget>> findAllByUser(@PathVariable Integer userID) {
		List<Budget> list = service.findAllByUserId(userID);
		return ResponseEntity.ok().body(list);
	}

	@RequestMapping(value = "/user/{userID}/date", method = RequestMethod.GET)
	public ResponseEntity<List<Budget>> findAllByUserFromDateBetween(
			@PathVariable Integer userID,
			@RequestParam("startDate") @DateTimeFormat(pattern = "dd/MM/yyyy") Date startDate,
			@RequestParam("endDate") @DateTimeFormat(pattern = "dd/MM/yyyy") Date endDate) {

		List<Budget> list = service.findAllByUserIdFromDateBetween(userID, startDate, endDate);
		return ResponseEntity.ok().body(list);
	}

}
