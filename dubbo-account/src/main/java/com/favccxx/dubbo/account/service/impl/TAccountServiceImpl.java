package com.favccxx.dubbo.account.service.impl;

import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.favccxx.dubbo.account.mapper.TAccountMapper;
import com.favccxx.dubbo.api.account.TAccountService;
import com.favccxx.dubbo.model.TAccount;

import io.seata.core.context.RootContext;

@Service(group = "UserGroup", version = "1.0.2")
@Component
public class TAccountServiceImpl extends ServiceImpl<TAccountMapper, TAccount> implements TAccountService {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public int decreaseUserAccount(String userId, Double amount) {
		logger.info("xid: " + RootContext.getXID());		
		baseMapper.updateUserAccount(userId, amount);
		return 0;
	}

}
