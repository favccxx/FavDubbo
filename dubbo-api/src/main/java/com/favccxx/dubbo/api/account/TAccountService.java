package com.favccxx.dubbo.api.account;

public interface TAccountService {

	/**
	 * 更新用户账户： 花钱
	 * @param userId 用户账户
	 * @param amount 消费的金钱数目
	 * @return
	 */
	int decreaseUserAccount(String userId, Double amount);
	
}
