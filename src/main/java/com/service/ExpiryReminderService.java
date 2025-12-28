package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.ExpiryReminderEntity;
import java.util.List;
import java.util.Map;

/**
 * 过期提醒
 *
 * @author 
 * @email 
 * @date 2023-04-25 08:11:08
 */
public interface ExpiryReminderService extends IService<ExpiryReminderEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	PageUtils queryPage(Map<String, Object> params, Wrapper<ExpiryReminderEntity> wrapper);
}
