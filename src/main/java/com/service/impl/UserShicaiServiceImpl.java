package com.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.utils.PageUtils;
import com.utils.Query;
import com.dao.UserShicaiDao;
import com.entity.UserShicaiEntity;
import com.service.UserShicaiService;

@Service("userShicaiService")
public class UserShicaiServiceImpl extends ServiceImpl<UserShicaiDao, UserShicaiEntity> implements UserShicaiService {
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<UserShicaiEntity> page = this.selectPage(
                new Query<UserShicaiEntity>(params).getPage(),
                new EntityWrapper<UserShicaiEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<UserShicaiEntity> wrapper) {
		Page<UserShicaiEntity> page = this.selectPage(
                new Query<UserShicaiEntity>(params).getPage(),
                wrapper
        );
	    return new PageUtils(page);
 	}

}
