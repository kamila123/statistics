package org.kjanuaria.statistics.app.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = StatisticsControllerTest.class, secure = false)
public class StatisticsControllerTest {
	private MockMvc mockMvc;

	@InjectMocks
	private StatisticsControllerTest statisticsControllerTest;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(statisticsControllerTest).build();
	}

	@Test
	public void get_statistics_success() throws Exception {

		String createUrl = "/statistics/findCurrent";
		mockMvc.perform(get(createUrl).contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());

	}
}
