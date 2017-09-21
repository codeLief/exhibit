package com.exhibit.rpc.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.log4j.Log4j;

import org.springframework.beans.factory.annotation.Autowired;

import com.exhibit.rpc.dao.BaseDAO;

@Log4j
public class BaseServiceImpl<T extends BaseDAO<E>, E extends BaseModel> implements BaseService<E> {

    @Autowired
    protected T dao;

    @Override
    public List<E> query(Map<String, Object> params) {
        return dao.selectByParams(params);
    }

    @Override
    public E findById(String id) {
        return dao.selectByPrimaryKey(id);
    }

    @Override
    public List<E> queryByIds(List<String> ids) {
        Map<String, Object> params = new HashMap<>();
        params.put("ids", ids);
        return dao.selectByParams(params);
    }

/*    @Override
    public Page<E> pages(Map<String, Object> params, int pageNo, int pageSize) {

        int totalCount = dao.countByParams(params);

        params.put("offset", (pageNo - 1) * pageSize);
        params.put("size", pageSize);

        Page<E> page = new Page<>(pageNo, pageSize);
        //params.put("page", page);

        page.setList(dao.selectByParams(params));
        page.setTotalRow(totalCount);

        return page;
    }*/

    @Override
    public List<E> page(Map<String, Object> params, int offset, int size) {
        params.put("offset", offset);
        params.put("size", size);
        return dao.selectByParams(params);
    }

    @Override
    public int count(Map<String, Object> params) {
        return dao.countByParams(params);
    }

    @Override
    public E findFirst(Map<String, Object> params) {
        return findByParams(params);
    }

    @Override
    public E findByParams(Map<String, Object> params) {
        params.put("offset", 0);
        params.put("size", 2);
        List<E> list = dao.selectByParams(params);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public int save(E entity) {
        return dao.insert(entity);
    }

    @Override
    public int batchSave(List<E> entityList) {
        return dao.insertBatch(entityList);
    }

    @Override
    public int modify(E entity) {
        return dao.updateByPrimaryKey(entity);
    }

    @Override
    public int remove(String id) {
        return dao.deleteByPrimaryKey(id);
    }
    
	@Override
	public int modifySelective(E entity) {
		return dao.updateByPrimaryKeySelective(entity);
	}
}