package org.kjanuaria.statistics.controller;

import javax.validation.Valid;

import org.kjanuaria.statistics.entity.Transaction;
import org.kjanuaria.statistics.exception.TransactionExpiredException;
import org.kjanuaria.statistics.json.TransactionJson;
import org.kjanuaria.statistics.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@PostMapping
	public ResponseEntity<Transaction> post(@RequestBody @Valid TransactionJson transactionRequest) {

		try {
			Transaction transaction = this.transactionService.add(transactionRequest);
			ResponseEntity<Transaction> response = new ResponseEntity<Transaction>(transaction, HttpStatus.CREATED);

			return response;
		} catch (TransactionExpiredException e) {
			System.err.println(e.getMessage());
			return new ResponseEntity<Transaction>(HttpStatus.NO_CONTENT);
		}
	}

}
