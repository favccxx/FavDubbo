package com.favccxx.dubbo.service;

import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.favccxx.dubbo.api.account.TAccountService;
import com.favccxx.dubbo.api.storage.TStorageService;

import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;

@Service
public class ProuctBizServiceImpl implements ProuctBizService {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@Reference(version = "1.0.1", check = false, retries = 0)
	TStorageService storageService;
	@Reference(group = "UserGroup", version = "1.0.2", check = false)
	TAccountService accountService;

	@Override
	@GlobalTransactional(timeoutMills = 300000, name="my_group")
	public void purchase(String userId, String prodCode, Double amount) {
		
		logger.info("xid: " + RootContext.getXID());
		accountService.decreaseUserAccount(userId, amount);
		storageService.decreaseStorage(prodCode, 1);
	}

}
