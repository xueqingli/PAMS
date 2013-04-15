package com.lxq.platform.dao.interf;

import java.io.Serializable;
import java.util.List;

/**
 * 通用DAO接口
 * @author lixueqing
 *
 */
public interface IBaseDAO {
	
	/**
	 * 保存实体对象
	 * @param o 实体对象
	 */
	public void save(Object o);

	/**
	 * 保存实体对象
	 * @param c 实体类
	 * @param o 实体对象
	 */
	public void save(String entityName, Object entity);
	
	/**
	 * 删除实体对象
	 * @param o 实体对象
	 */
	public void delete(Object o);
	
	/**
	 * 删除实体对象
	 * @param c 实体类
	 * @param o 实体对象
	 */
	public void delete(String entityName, Object entity);
	
	/**
	 * 更新实体对象
	 * @param hql 更新语句
	 */
	public void update(String hql);
	
	/**
	 * 更新实体对象
	 * @param o 实体对象
	 */
	public void update(Object object);
	
	/**
	 * 更新实体对象
	 * @param c 实体类
	 * @param o 实体对象
	 */
	public void update(String entityName, Object entity);

	/**
	 * 保存或更新实体对象
	 * @param o 实体对象
	 */
	public void saveOrUpdate(Object object);

	/**
	 * 保存或更新实体对象
	 * @param c 实体类
	 * @param o 实体对象
	 */
	public void saveOrUpdate(String entityName, Object entity);

	/**
	 * 根据主键查找实体对象
	 * @param c 实体类
	 * @param id 实体类的主键
	 */
	public Object findById(Class<?> c,Serializable id);
	
	
	/**
	 * 根据hql语句查找结果集
	 * @param hql 查询语句
	 */
	public List<?> findByHQL(String hql);
	
	/**
	 * 根据hql语句，和分页参数，查找结果集
	 * @param hql 查询语句
	 */
	public List<?> findByHQL(String hql,int firstResult,int maxResults);

	
	/**
	 * 根据hql语句，获取结果集大小
	 * @param hql 查询语句
	 */
	public int getCount(String hql);
	
	/**
	 * 执行hql语句,获得唯一值
	 * @param hql 执行语句
	 */
	public Object findUniqueByHQL(String hql);
}