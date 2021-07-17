package com.ed2nd.mywallet.resources;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ed2nd.mywallet.domain.Transaction;
import com.ed2nd.mywallet.dto.TransactionDTO;
import com.ed2nd.mywallet.dto.TransactionNewDTO;
import com.ed2nd.mywallet.services.TransactionService;

@RestController
@RequestMapping(value = "/transactions")
public class TransactionResource {

	@Autowired
	private TransactionService service;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<TransactionDTO>> findAll() {
		List<Transaction> list = service.findAll();
		List<TransactionDTO> listDto = list.stream().map(obj -> new TransactionDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<TransactionDTO> find(@PathVariable Integer id) {
		Transaction obj = service.find(id);
		TransactionDTO objDto =  new TransactionDTO(obj);
		return ResponseEntity.ok().body(objDto);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody TransactionNewDTO objDto) {
		Transaction obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody Transaction obj, @PathVariable Integer id) {
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<TransactionDTO>> findPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "date") String orderBy,
			@RequestParam(value = "direction", defaultValue = "DESC") String direction) {

		Page<Transaction> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<TransactionDTO> listDto = list.map(obj -> new TransactionDTO(obj));
		return ResponseEntity.ok().body(listDto);
	}

	@RequestMapping(value = "/user/{userID}/page", method = RequestMethod.GET)
	public ResponseEntity<Page<TransactionDTO>> findAllByUser(
			@PathVariable Integer userID,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "date") String orderBy,
			@RequestParam(value = "direction", defaultValue = "DESC") String direction){
		
		Page<Transaction> list = service.findAllByUserId(userID, page, linesPerPage, orderBy, direction);
		Page<TransactionDTO> listDto = list.map(obj -> new TransactionDTO(obj));
		return ResponseEntity.ok().body(listDto);
	}

	@RequestMapping(value = "/user/{userID}/date", method = RequestMethod.GET)
	public ResponseEntity<List<TransactionDTO>> findAllByUserFromDateBetween(
			@PathVariable Integer userID,
			@RequestParam("startDate") @DateTimeFormat(pattern = "dd/MM/yyyy") Date startDate,
			@RequestParam("endDate") @DateTimeFormat(pattern = "dd/MM/yyyy") Date endDate) {

		List<Transaction> list = service.findAllByUserIdFromDateBetween(userID, startDate, endDate);
		List<TransactionDTO> listDto = list.stream().map(obj -> new TransactionDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}

}
