package com.dao;

import com.entity.ExpiryReminderEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;

/**
 * 过期提醒
 * 
 * @author 
 * @email 
 * @date 2023-04-25 08:11:08
 */
public interface ExpiryReminderDao extends BaseMapper<ExpiryReminderEntity> {
	
}
