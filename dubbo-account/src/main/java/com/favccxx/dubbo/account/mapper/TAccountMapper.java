package com.favccxx.dubbo.account.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.favccxx.dubbo.model.TAccount;

@Component
public interface TAccountMapper extends BaseMapper<TAccount> {

	@Update("update t_account t set t.amount=t.amount-#{amount} where t.user_Id=#{userId}")
	int updateUserAccount(@Param("userId") String userId, @Param("amount") Double amount);
}
