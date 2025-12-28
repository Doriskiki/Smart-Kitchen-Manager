package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.ShicaiShelfLifeEntity;
import java.util.List;
import java.util.Map;

/**
 * 保质期参考库
 *
 * @author 
 * @email 
 * @date 2023-04-25 08:11:08
 */
public interface ShicaiShelfLifeService extends IService<ShicaiShelfLifeEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	PageUtils queryPage(Map<String, Object> params, Wrapper<ShicaiShelfLifeEntity> wrapper);
}
