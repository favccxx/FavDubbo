package com.favccxx.dubbo.storage.service.impl;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.favccxx.dubbo.api.storage.TStorageService;
import com.favccxx.dubbo.model.TStorage;
import com.favccxx.dubbo.storage.mapper.TStorageMapper;

@Service(version="1.0.1")
@Component
public class TStorageServiceImpl extends ServiceImpl<TStorageMapper, TStorage> implements TStorageService {

	@Override
	public int decreaseStorage(String prodCode, int count) {
		baseMapper.decreaseStorage(prodCode, count);
		return 0;
	}

}
