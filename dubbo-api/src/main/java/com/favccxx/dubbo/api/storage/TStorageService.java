package com.favccxx.dubbo.api.storage;

public interface TStorageService {
	
	/**
	 * 更新产品库存
	 * @param prodCode 产品代码
	 * @param count 库存
	 * @return
	 */
	int decreaseStorage(String prodCode, int count);

}
