package com.oxd.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oxd.dao.ZhaoPinRepository;
import com.oxd.model.ZhaoPinModel;
import com.oxd.vo.ZhaoPinVo;

@Service
@Transactional(rollbackFor = Exception.class)
public class ZhaoPinService extends AbstractService {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private ZhaoPinRepository repository;
	
	/**
	 * 新增或修改
	 * @param news
	 */
	public void saveOrUpdate(ZhaoPinModel entity) {
		this.fillEntity(entity);
		repository.save(entity);
	}
	
	
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<ZhaoPinVo> search() {
		try {
			Session session = entityManager.unwrap(Session.class);
			String[] columns = { "id", "type", "typeId", "content", "createTime", "updateTime",
					"createUser", "updateUser"};
	
			String sql = "select m.id as typeId, m.name as type, a.id, a.content, DATE_FORMAT(a.create_time, '%Y-%m-%d %T') as createTime, "
					+ " DATE_FORMAT(a.update_time, '%Y-%m-%d %T') as updateTime, u1.name as createUser, u2.name as updateUser from menu_model m "
					+ " left join zhao_pin_model a on a.m_id=m.id left join user_model u1 on u1.id=a.create_user left join user_model u2 on u2.id=a.update_user where m.parent_id=8";
			SQLQuery query = session.createSQLQuery(sql);
			this.setScalars(query, columns);
			List<ZhaoPinVo> vos = query.setResultTransformer(Transformers.aliasToBean(ZhaoPinVo.class)).list();
			return vos;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 删除
	 * @param id
	 */
	public void delete(int id) {
		repository.delete(id);
	}
	
	/**--------------------------------网站相关查询-------------------------------**/
	
	/**
	 * 网站页面内容查询
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public ZhaoPinVo findOne(int id) {
		Session session = entityManager.unwrap(Session.class);
		String[] columns = { "content"};
		String sql = "select a.content from zhao_pin_model a where a.m_id=?";
		List<Object> params = new ArrayList<Object>(1);
		params.add(id);
		SQLQuery query = session.createSQLQuery(sql);
		this.setScalarsAndParams(query, columns, params);
		List<ZhaoPinVo> vos = query.setResultTransformer(Transformers.aliasToBean(ZhaoPinVo.class)).list();
		if(vos.size() > 0) {
			return vos.get(0);
		}
		return null;
	}

}
