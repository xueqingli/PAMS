package com.lxq.platform.service.intef;

import java.io.Serializable;
import java.util.List;

import com.lxq.platform.userManage.pojo.User;

/**
 * 基础通用服务
 * @author lixueqing
 *
 */
public interface IBaseService {
	/**
	 * 根据主键获取一条记录
	 * @param className 实体类名称
	 * @param id 主键
	 * @return 实体类
	 * @throws ClassNotFoundException
	 */
	public Object findById(Class<?> c , Serializable id);

	/**
	 * 根据hql语句获取一条记录
	 * @param hql 查询语句
	 * @return 实体类
	 */
	public Object findUniqueByHQL(String hql);

	/**
	 * 根据hql获取记录集
	 * @param hql 查询语句
	 * @return 实体类集合
	 */
	public List<?> findByHQL(String hql);
	
	/**
	 * 根据hql获取分页记录集
	 * @param hql 查询语句
	 * @param firstResult 记录开始位置
	 * @param maxResults 最大的记录条数
	 * @return 实体类集合
	 */
    public List<?> findByHQL(String hql , int firstResult , int maxResults);
	
    /**
     * 根据hql获取记录总数
     * @param hql 查询语句
     * @return 记录总数
     */
	public int getCount(String hql);
		
	
	/**
	 * 保存实体对象
	 * @param entity 实体对象
	 * @param user 操作用户
	 * @param ipAddress 用户登录ip
	 * @param message 操作内容
	 * @throws Exception
	 */
	public void add(Object entity , User user , String ipAddress , String message) throws Exception;
	
	
	/**
	 * 保存实体
	 * @param entity 实体类
	 * @throws Exception
	 */
	public void add(Object entity) throws Exception;
	
	/**
	 * 更新实体
	 * @param entity 实体对象
	 * @param user 操作用户
	 * @param ipAddress 用户登录ip
	 * @param message 操作内容
	 * @throws Exception
	 */
	public void update(Object entity , User user , String ipAddress , String message) throws Exception;
	
	
	/**
	 * 更新实体
	 * @param entity 实体类
	 * @throws Exception
	 */
	public void update(Object entity) throws Exception;
	
	/**
	 * 删除实体对象
	 * @param entity 实体对象
	 * @param user 操作用户
	 * @param ipAddress 用户登录ip
	 * @param message 操作内容
	 * @throws Exception
	 */
	public void delete(Object entity , User user , String ipAddress , String message) throws Exception;

	/**
	 * 删除实体
	 * @param entity 实体类
	 * @throws Exception
	 */
	public void delete(Object entity) throws Exception;
	
}
