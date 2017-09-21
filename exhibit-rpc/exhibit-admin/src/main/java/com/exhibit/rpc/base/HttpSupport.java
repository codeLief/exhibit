package com.exhibit.rpc.base;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.exhibit.rpc.util.DozerUtil;
import com.exhibit.rpc.util.GsonUtil;

public abstract class HttpSupport {

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    @Autowired
    protected DozerUtil dozerUtil;

    /**
     * 获取当前页码
     *
     * @return
     */
    protected final int getPageNo() {
        int defaultPage = 1;
        int page = NumberUtils.toInt(getParamStr("pageNo"), defaultPage);
        return page > 0 ? page : defaultPage;
    }

    /**
     * 获取每页大小
     *
     * @return
     */
    protected final int getPageSize() {
        int defaultPageSize = 5;
        int pageSize = NumberUtils.toInt(getParamStr("pageSize"), defaultPageSize);
        return pageSize > 0 ? pageSize : defaultPageSize;
    }

    /**
     * 获取分页偏移量
     *
     * @return
     */
    protected final int getOffset() {
        return (getPageNo() - 1) * getPageSize();
    }

    /**
     * 获取请求中的参数
     *
     * @return
     */
    protected final Map<String, Object> getParams() {
        return (Map<String, Object>) request.getAttribute("params");
    }

    /**
     * 获取请求中的参数，并转换为指定实体对象
     *
     * @param clazz
     * @param <M>
     * @return
     * @throws Exception
     */
    protected final <M> M getParamsObj(Class<M> clazz) throws Exception {
    	 M m = JSON.parseObject(GsonUtil.toJsonSerializeNulls(getParams()), clazz);
       
        return m;
    }

    /**
     * 根据name，获取请求中的参数：字符串 仅支持一级
     *
     * @param name
     * @return
     */
    protected final String getParamStr(String name) {
        return getParams().containsKey(name) ? String.valueOf(getParams().get(name)) : StringUtils.EMPTY;
    }

    /**
     * 根据name，获取请求中的参数：字符串 仅支持一级
     *
     * @param name
     * @return
     */
    protected final <T> T getParamT(String name) {
        if (getParams().containsKey(name)) {
            return (T) getParams().get(name);
        }
        return null;
    }

    /**
     * 根据name，获取请求中的参数：整型 仅支持一级
     *
     * @param name
     * @return
     */
    protected final int getParamInt(String name) {
        return getParams().containsKey(name) ? Integer.valueOf(String.valueOf(getParams().get(name))) : 0;
    }

    /**
     * 根据name，获取请求中的参数：长整型 仅支持一级
     *
     * @param name
     * @return
     */
    protected final long getParamLong(String name) {
        return getParams().containsKey(name) ? Long.valueOf(String.valueOf(getParams().get(name))) : 0l;
    }

    /**
     * 根据name，获取请求中的参数：Double型 仅支持一级
     *
     * @param name
     * @return
     */
    protected final double getParamDouble(String name) {
        return getParams().containsKey(name) ? Double.valueOf(String.valueOf(getParams().get(name))) : 0.0f;
    }

    /**
     * 根据name，获取请求中的参数：Float型 仅支持一级
     *
     * @param name
     * @return
     */
    protected final double getParamFloat(String name) {
        return getParams().containsKey(name) ? Float.valueOf(String.valueOf(getParams().get(name))) : 0.0f;
    }

    /**
     * 根据name，获取请求中的参数：T 数组 仅支持一级
     *
     * @param name
     * @return
     */
    protected final <T> T[] getParamTArray(String name, Class<T> clazz) {
        if (getParams().containsKey(name)) {

            JSONArray array = (JSONArray) getParams().get(name);
            T[] result = (T[]) Array.newInstance(clazz, array.size());
            for (int i = 0; i < array.size(); i++) {
                result[i] = (T) array.getObject(i, clazz);
            }
            return result;
        }
        return null;
    }

    /**
     * 根据name，获取请求中的参数：Long数组 仅支持一级
     *
     * @param name
     * @return
     */
    protected final long[] getParamLongArray(String name) {
        if (getParams().containsKey(name)) {
            JSONArray array = (JSONArray) getParams().get(name);
            long[] result = new long[array.size()];
            for (int i = 0; i < array.size(); i++) {
                result[i] = array.getLongValue(i);
            }
            return result;
        }
        return null;
    }

    /**
     * 根据name，获取请求中的参数：Integer数组 仅支持一级
     *
     * @param name
     * @return
     */
    protected final int[] getParamIntArray(String name) {
        if (getParams().containsKey(name)) {
            JSONArray array = (JSONArray) getParams().get(name);
            int[] result = new int[array.size()];
            for (int i = 0; i < array.size(); i++) {
                result[i] = array.getIntValue(i);
            }
            return result;
        }
        return null;
    }

    /**
     * 根据name，获取请求中的参数：String数组 仅支持一级
     *
     * @param name
     * @return
     */
    protected final String[] getParamStrArray(String name) {
        if (getParams().containsKey(name)) {
            JSONArray array = (JSONArray) getParams().get(name);
            String[] result = new String[array.size()];
            for (int i = 0; i < array.size(); i++) {
                result[i] = array.getString(i);
            }
            return result;
        }
        return null;
    }


    /**
     * 获取key=entity请求中的参数，默认转换为指定实体对象
     *
     * @return
     */
    protected final <M> M getParamObj(Class<M> clazz) {
        return getParamObj("entity", clazz);
    }

    /**
     * 根据name, 获取请求中的参数，默认转换为指定实体对象
     *
     * @return
     */
    protected final <M> M getParamObj(String name, Class<M> clazz) {
        return getParams().containsKey(name) ? GsonUtil.fromJson(String.valueOf(getParams().get(name)), clazz) : null;
    }


    /**
     * 处理响应信息
     *
     * @param code
     * @param message
     * @return
     */
    protected final ModelResult responseMessage(int code, String message) {
        ModelResult modelResult = new ModelResult(code);
        modelResult.setMessage(message);

        return modelResult;
    }

    /**
     * 处理响应信息
     *
     * @param success
     * @return
     */
    protected final ModelResult responseMessage(boolean success) {
        ModelResult modelResult = new ModelResult(ModelResult.CODE_200);
        modelResult.setMessage(ModelResult.SUCCESS);

        if (!success) {
            modelResult.setCode(ModelResult.CODE_500);
            modelResult.setMessage(ModelResult.FAIL);
        }

        return modelResult;
    }

    /**
     * 处理响应单个实体
     *
     * @param code
     * @param message
     * @param entity
     * @return
     */
    protected final ModelResult responseEntity(int code, String message, Object entity) {
        ModelResult modelResult = new ModelResult(code);
        modelResult.setMessage(message);
        modelResult.setEntity(entity);

        return modelResult;
    }

    /**
     * 处理响应list
     *
     * @param code
     * @param message
     * @param list
     * @return
     */
    protected final ModelResult responseList(int code, String message, List list) {
        ModelResult modelResult = new ModelResult(code);
        modelResult.setMessage(message);
        modelResult.setList(list);

        return modelResult;
    }

    /**
     * 处理响应page
     *
     * @param code
     * @param message
     * @param totalCount
     * @param items
     * @return
     */
    protected final ModelResult responsePage(int code, String message, int totalCount, List items) {
        ModelResult modelResult = new ModelResult(code);
        modelResult.setMessage(message);
        modelResult.setResultPage(new ResultPage(totalCount, getPageSize(), getPageNo(), items));

        return modelResult;
    }

    protected final String responseRedirect(String url) {
        return "redirect:" + url;
    }

}