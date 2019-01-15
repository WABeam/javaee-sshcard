package com.edu.dao;

import java.util.List;

public interface IBaseDao<T> {
	
	/**
	 * �������o�����ݿ���
	 * @param o ʵ����
	 * @return ����ļ�¼����
	 */
	public int insert(T o);  
	
	/**
	 * �����󼯺ϲ��뵽���ݿ���
	 * @param list ���󼯺�
	 * @return ����ļ�¼����
	 */
	public int insertList(List<T> list);   
	
	/**
	 * ���ö���o�޸ĵ�ǰ��¼
	 * @param o ����o
	 * @return ���޸ĵļ�¼����
	 */
	public int update(T o);
	
	/**
	 * ����id�ļ���ɾ���ü����ж�Ӧ��id�ļ�¼
	 * @param c ��������
	 * @param ids ��Ӧ��id����
	 * @return ɾ���ļ�¼����
	 */
	public int deleteList(int...ids);
	
	/**
	 * �����ݿ���ɾ��һ����¼o
	 * @param o ����o
	 * @return ɾ���ļ�¼����
	 */
	public int delete(T o);
	
	/**
	 * �����ݿ���ɾ��һ����Ӧid�ļ�¼
	 * @param id �ƶ���id
	 * @return ɾ���ɹ�������
	 */
	public int delete(int id);
	
	/**
	 * ����id����һ����¼
	 * @param id ��Ӧ��id
	 * @return ���ҵ���ʵ�弯
	 */
	public T findById(int id);
	
	/**
	 * ��ѯ������¼
	 * @param sql ��ִ�е�sql���
	 * @param params ��Ҫ���õĲ���
	 * @return ���ҵ���ʵ�弯
	 */
	public T findOne(String sql, String[] params);
	
	/**
	 * ���������Ҷ�����¼
	 * @param sql ��ִ�е�sql���
	 * @param params ��Ҫ���õĲ���
	 * @return ���ҵ���ʵ�弯
	 */
	public List<T> find(String sql, String[] params);
	
	/**
	 * ��ҳ���Ҷ���
	 * @param sql ��ִ�е�sql���
	 * @param param ��Ҫ���õĲ���
	 * @param page ҳ��
	 * @param size ÿҳ�ļ�¼����
	 * @return ���ҵ��ļ�¼����
	 */
	public List<T> findPage(String sql, String[] param, int page, int size);
	
	/**
	 * ģ������
	 * @param sql ��ִ�е�sql
	 * @param fields ��Ҫ���ҵ��ֶ�
	 * @param condition ��ѯ����
	 * @return ���ҵ��ļ�¼
	 */
	public List<T> findByFields(String sql, String fields[],String[] params, String condition,int page, int size);
	
	/**
	 * ��ִ̬��sql
	 * @return
	 */
	public int ExeSql(String sql, String[] params);
	
}
