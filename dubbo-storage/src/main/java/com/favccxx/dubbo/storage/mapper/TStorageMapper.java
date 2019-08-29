package com.favccxx.dubbo.storage.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.favccxx.dubbo.model.TStorage;

@Component
public interface TStorageMapper extends BaseMapper<TStorage> {

	@Update("update t_storage t set t.count=t.count-#{count} where t.prod_code=#{prodCode}")
	int decreaseStorage(@Param("prodCode") String prodCode, @Param("count") int count);
	
}
