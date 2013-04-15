package com.lxq.platform.dao.hibernateImpl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.lxq.platform.dao.interf.IBaseDAO;

/**
 * 通用DAO实现
 * @author lixueqing
 *
 */
public class BaseDAOImpl implements IBaseDAO {
	
	private HibernateTemplate hibernateTemplate;
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	public void save(Object o) {
		hibernateTemplate.save(o);
	}
	
	public void delete(Object o) {
		
		hibernateTemplate.delete(o);
	}
	
	public void update(Object o) {
		
		hibernateTemplate.update(o);
	}
	
	public void saveOrUpdate(Object o){
		hibernateTemplate.saveOrUpdate(o);
	}
	
	public Object findById(Class<?> c , Serializable id) {
		Object o = hibernateTemplate.get(c, id);
		return o;
	}

	public List<?> findByHQL(String hql) {
		List<?> list = hibernateTemplate.find(hql);
		
		return list;
	}
	
	public List<?> findByHQL(final String hql,final int firstResult,final int maxResults)
    throws DataAccessException{
    return (List<?>)hibernateTemplate.execute(new HibernateCallback() {

        public Object doInHibernate(Session session)
            throws HibernateException
        {
	            Query query = session.createQuery(hql);
	
	    		query.setFirstResult(firstResult);
	    		query.setMaxResults(maxResults);
	    		
	    		List<?> list = query.list();
	    		
	    		return list;
	    		
	        }
	
	    });
	}	
    public void update(final String hql)
    throws DataAccessException{
    	hibernateTemplate.execute(new HibernateCallback() {
	        public Object doInHibernate(Session session)
	            throws HibernateException
	        {
	            Query query = session.createQuery(hql);
	            
	            query.executeUpdate();	
	    		return null;
	        }
	    });
	}
	
	public int getCount(String hql) {
		return ((Long)hibernateTemplate.find(hql).iterator().next()).intValue();
	}

	public Object findUniqueByHQL(String hql) {
		
		return hibernateTemplate.find(hql).iterator().hasNext()?hibernateTemplate.find(hql).iterator().next():null;
	}

	public void delete(String entityName, Object entity) {
		hibernateTemplate.delete(entityName, entity);
	}

	public void save(String entityName, Object entity) {
		hibernateTemplate.save(entityName, entity);
		
	}
	
	public void saveOrUpdate(String entityName, Object entity) {
		hibernateTemplate.saveOrUpdate(entityName, entity);
	}

	public void update(String entityName, Object entity) {
		hibernateTemplate.update(entityName, entity);
	}

	
}