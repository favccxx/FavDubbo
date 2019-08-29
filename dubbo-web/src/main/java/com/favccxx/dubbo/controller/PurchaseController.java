package com.favccxx.dubbo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.favccxx.dubbo.service.ProuctBizService;

@RestController
public class PurchaseController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	ProuctBizService prouctBizService;

	@RequestMapping("/purchase")
	public String purchase(String username, String productCode, double amount) {
		prouctBizService.purchase(username, productCode, amount);
		return JSON.toJSONString("success");
	}
}
