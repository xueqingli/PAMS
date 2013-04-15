package com.lxq.platform.service.impl;

import java.io.Serializable;
import java.util.List;

import com.lxq.platform.dao.interf.IBaseDAO;
import com.lxq.platform.service.intef.IBaseService;
import com.lxq.platform.userManage.pojo.User;

public class BaseServiceImpl implements IBaseService {

	protected IBaseDAO baseDAO;

	public IBaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public List<?> findByHQL(String hql) {
		return baseDAO.findByHQL(hql);
	}

	public List<?> findByHQL(String hql, int firstResult, int maxResults) {
		return baseDAO.findByHQL(hql, firstResult, maxResults);
	}

	public Object findById(Class<?> c, Serializable id) {
		return baseDAO.findById(c, id);
	}

	public Object findUniqueByHQL(String hql) {
		return baseDAO.findUniqueByHQL(hql);
	}

	public int getCount(String hql) {
		return baseDAO.getCount(hql);
	}

	public void delete(Object entity, User user, String ipAddress,String message) throws Exception {
		baseDAO.delete(entity);
	}

	public void delete(Object entity) {
		baseDAO.delete(entity);
	}

	public void add(Object entity, User user, String ipAddress, String message) throws Exception {
		baseDAO.saveOrUpdate(entity);
	}

	public void add(Object entity) {
		baseDAO.saveOrUpdate(entity);
	}

	public void update(Object entity, User user, String ipAddress,String message) throws Exception {
		baseDAO.saveOrUpdate(entity);
	}

	public void update(Object entity) {
		baseDAO.saveOrUpdate(entity);
	}
}
