package com.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.annotation.IgnoreAuth;
import com.entity.UserShicaiEntity;
import com.service.UserShicaiService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;

/**
 * 用户食材库
 * 后端接口
 * @author 
 * @email 
 * @date 2023-04-25 08:11:08
 */
@RestController
@RequestMapping("/usershicai")
public class UserShicaiController {
    @Autowired
    private UserShicaiService userShicaiService;

    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, UserShicaiEntity userShicai,
		HttpServletRequest request){
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("yonghu")) {
			userShicai.setUserid((Long)request.getSession().getAttribute("userId"));
		}
		// 如果参数中有userid，使用参数中的userid（前端传递）
		if(params.get("userid") != null) {
			userShicai.setUserid(Long.parseLong(params.get("userid").toString()));
		}
        EntityWrapper<UserShicaiEntity> ew = new EntityWrapper<UserShicaiEntity>();

		PageUtils page = userShicaiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, userShicai), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, UserShicaiEntity userShicai, 
		HttpServletRequest request){
        EntityWrapper<UserShicaiEntity> ew = new EntityWrapper<UserShicaiEntity>();

		PageUtils page = userShicaiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, userShicai), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list(UserShicaiEntity userShicai){
       	EntityWrapper<UserShicaiEntity> ew = new EntityWrapper<UserShicaiEntity>();
      	ew.allEq(MPUtil.allEQMapPre(userShicai, "usershicai")); 
        return R.ok().put("data", userShicaiService.selectList(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(UserShicaiEntity userShicai){
        EntityWrapper<UserShicaiEntity> ew = new EntityWrapper<UserShicaiEntity>();
 		ew.allEq(MPUtil.allEQMapPre(userShicai, "usershicai")); 
		UserShicaiEntity userShicaiEntity = userShicaiService.selectOne(ew);
		return R.ok("查询用户食材库成功").put("data", userShicaiEntity);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        UserShicaiEntity userShicai = userShicaiService.selectById(id);
        return R.ok().put("data", userShicai);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        UserShicaiEntity userShicai = userShicaiService.selectById(id);
        return R.ok().put("data", userShicai);
    }

    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody UserShicaiEntity userShicai, HttpServletRequest request){
    	userShicai.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("yonghu")) {
			userShicai.setUserid((Long)request.getSession().getAttribute("userId"));
		}
		
		userShicai.setAddtime(new Date());
        userShicaiService.insert(userShicai);
        return R.ok();
    }
    
    /**
     * 批量保存（用于小票识别）
     */
    @RequestMapping("/batchSave")
    public R batchSave(@RequestBody java.util.List<UserShicaiEntity> userShicaiList, HttpServletRequest request){
        for(UserShicaiEntity userShicai : userShicaiList) {
            userShicai.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
            userShicai.setAddtime(new Date());
            userShicaiService.insert(userShicai);
        }
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody UserShicaiEntity userShicai, HttpServletRequest request){
    	userShicai.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	
		userShicai.setAddtime(new Date());
        userShicaiService.insert(userShicai);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody UserShicaiEntity userShicai, HttpServletRequest request){
        userShicaiService.updateById(userShicai);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        userShicaiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
    /**
     * 根据用户ID获取食材库
     */
    @RequestMapping("/listByUser")
    public R listByUser(@RequestParam Long userid){
        EntityWrapper<UserShicaiEntity> ew = new EntityWrapper<UserShicaiEntity>();
        ew.eq("userid", userid);
        return R.ok().put("data", userShicaiService.selectList(ew));
    }
    
    /**
     * 根据状态查询食材
     */
    @RequestMapping("/listByStatus")
    public R listByStatus(@RequestParam Long userid, @RequestParam String status){
        EntityWrapper<UserShicaiEntity> ew = new EntityWrapper<UserShicaiEntity>();
        ew.eq("userid", userid);
        ew.eq("status", status);
        return R.ok().put("data", userShicaiService.selectList(ew));
    }
    
    /**
     * 查询即将过期的食材（7天内）
     */
    @RequestMapping("/listExpiringSoon")
    public R listExpiringSoon(@RequestParam Long userid){
        EntityWrapper<UserShicaiEntity> ew = new EntityWrapper<UserShicaiEntity>();
        ew.eq("userid", userid);
        ew.eq("status", "new");
        
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        ew.le("expiry_date", calendar.getTime());
        ew.ge("expiry_date", new Date());
        
        return R.ok().put("data", userShicaiService.selectList(ew));
    }

}
