package org.kjanuaria.statistics.app.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kjanuaria.statistics.controller.TransactionController;
import org.kjanuaria.statistics.json.TransactionJson;
import org.kjanuaria.statistics.service.TransactionService;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = TransactionControllerTest.class, secure = false)
public class TransactionControllerTest {
	private MockMvc mockMvc;

	@InjectMocks
	private TransactionController transactionController;

	@MockBean
	private TransactionService transactionService;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
	}

	@Test
	public void test_create_transaction_success() throws Exception {

		String createUrl = "/transactions";
		Double amount = (double) 900;
		java.sql.Timestamp ts2 = java.sql.Timestamp.valueOf("2018-04-25 09:26:10");
		Long timestamp = ts2.getTime();
		transactionService.add(createTransactionJson(amount, timestamp));
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(createUrl)
				.contentType(MediaType.APPLICATION_JSON).content(createUserInJson(amount, timestamp));

		this.mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isCreated());

	}

	private TransactionJson createTransactionJson(Double amount, Long timestamp) {
		TransactionJson transactionJson = new TransactionJson();
		transactionJson.setAmount(amount);
		transactionJson.setTimestamp(timestamp);
		return transactionJson;
	}

	private static String createUserInJson(Double amount, Long timestamp) {
		return "{ \"amount\": \"" + amount + "\", " + "\"timestamp\":\"" + timestamp + "\"}";
	}
}
