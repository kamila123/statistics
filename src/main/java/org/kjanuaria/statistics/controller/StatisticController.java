package org.kjanuaria.statistics.controller;

import javax.ws.rs.core.MediaType;

import org.kjanuaria.statistics.entity.Statistic;
import org.kjanuaria.statistics.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticController {

	@Autowired
	private StatisticService statisticService;

	@ResponseBody
	@RequestMapping(value = "/statistics", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<Statistic> findCurrent() {

		Statistic statistic = this.statisticService.findCurrent();

		ResponseEntity<Statistic> response = new ResponseEntity<Statistic>(statistic, HttpStatus.OK);

		return response;
	}

}
