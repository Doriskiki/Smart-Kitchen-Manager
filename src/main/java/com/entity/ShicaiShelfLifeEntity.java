package com.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.apache.commons.beanutils.BeanUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

/**
 * 保质期参考库
 * 数据库通用操作实体类（普通增删改查）
 * @author 
 * @email 
 * @date 2023-04-25 08:11:08
 */
@TableName("shicai_shelf_life")
public class ShicaiShelfLifeEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	public ShicaiShelfLifeEntity() {
		
	}
	
	public ShicaiShelfLifeEntity(T t) {
		try {
			BeanUtils.copyProperties(this, t);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 主键id
	 */
	@TableId
	private Long id;
	
	/**
	 * 食材id
	 */
	private Long shicaiId;
	
	/**
	 * 保质期天数
	 */
	private Integer shelfLifeDays;
	
	/**
	 * 储存方法
	 */
	private String storageMethod;
	
	/**
	 * 添加时间
	 */
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date addtime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getShicaiId() {
		return shicaiId;
	}

	public void setShicaiId(Long shicaiId) {
		this.shicaiId = shicaiId;
	}

	public Integer getShelfLifeDays() {
		return shelfLifeDays;
	}

	public void setShelfLifeDays(Integer shelfLifeDays) {
		this.shelfLifeDays = shelfLifeDays;
	}

	public String getStorageMethod() {
		return storageMethod;
	}

	public void setStorageMethod(String storageMethod) {
		this.storageMethod = storageMethod;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
}
