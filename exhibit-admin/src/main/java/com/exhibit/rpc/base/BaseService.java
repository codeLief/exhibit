package com.exhibit.rpc.base;

import java.util.List;
import java.util.Map;

public interface BaseService<E extends BaseModel> {

	/**
	 * 查询
	 */
	public List<E> query(Map<String, Object> params);

	/**
     * ID查询
     */
	public E findById(String id);

	/**
	 * ID批量查询
	 */
	public List<E> queryByIds(List<String> ids);

	/**
	 * 参数分页查询
	 *//*
	public Page<E> pages(Map<String, Object> params, int pageNo, int pageSize);*/

	/**
	 * 参数分页查询
	 */
	public List<E> page(Map<String, Object> params, int offset, int size);

	/**
	 * 参数查询总数
	 */
	public int count(Map<String, Object> params);

	/**
	 * First查询
	 */
	public E findFirst(Map<String, Object> params);

	/**
	 * 参数查询
	 */
	E findByParams(Map<String, Object> params);

	/**
	 * 保存
	 */
    public int save(E entity);

	/**
	 * 批量保存
	 */
	public int batchSave(List<E> entityList);

	/**
	 * 修改
	 */
	public int modify(E entity);
	
	public int modifySelective(E entity);

	/**
	 * 删除
	 */
    public int remove(String id);

}
