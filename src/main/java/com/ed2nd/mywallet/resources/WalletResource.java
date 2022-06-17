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

import com.ed2nd.mywallet.domain.Wallet;
import com.ed2nd.mywallet.dto.WalletNewDTO;
import com.ed2nd.mywallet.services.WalletService;

@RestController
@RequestMapping(value = "/wallets")
public class WalletResource {

	@Autowired
	private WalletService service;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Wallet>> findAll() {
		List<Wallet> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Wallet> find(@PathVariable Integer id) {
		Wallet obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody WalletNewDTO objDto) {
		Wallet obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody Wallet obj, @PathVariable Integer id) {
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
	public ResponseEntity<Wallet> findAllByUser(@PathVariable Integer userID) {
		Wallet wallet = service.findAllByUserId(userID);
		return ResponseEntity.ok().body(wallet);
	}

	@RequestMapping(value = "/user/{userID}/date", method = RequestMethod.GET)
	public ResponseEntity<Wallet> findAllByUserFromDateBetween(
			@PathVariable Integer userID,
			@RequestParam("startDate") @DateTimeFormat(pattern = "dd/MM/yyyy") Date startDate,
			@RequestParam("endDate") @DateTimeFormat(pattern = "dd/MM/yyyy") Date endDate) {

		Wallet wallet = service.findAllByUserIdFromDateBetween(userID, startDate, endDate);
		return ResponseEntity.ok().body(wallet);
	}

}
