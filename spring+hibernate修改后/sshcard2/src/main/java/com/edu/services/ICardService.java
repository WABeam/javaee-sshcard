package com.edu.services;

import java.util.List;

import com.edu.entity.Card;

public interface ICardService {

	/**
	 * ������Ƭid������Ƭ����
	 * @param id ��Ƭid
	 * @return
	 */
	public abstract Card findCardById(int id);
	
	/**
	 * �����µ���Ƭ
	 * @param card ��Ƭʵ��
	 * @return �����²�����Ƭ��id
	 */
	public abstract int save(Card card);
	
	/**
	 * ��ҳ������Ƭ����
	 * @param flag ��Ƭ����
	 * @param page ҳ��
	 * @param size úҵ������
	 * @return ��ѯ�Ľ����
	 */
	public abstract List<Card> findCardByPage(String name, String flag, int page, int size);
	
	/**
	 * ����ɾ��
	 * @param ids ��Ҫɾ����id
	 * @return ɾ���ɹ�������
	 */
	public abstract int deleteBatchCard(int...ids);
	
	/**
	 * ������Ƭ��idɾ����Ƭ
	 * @param id ��Ƭid
	 * @return ɾ���Ľ��
	 */
	public abstract int deleteCard(int id);
	
	/**
	 * ����Ƭ�ƶ���/�Ƴ�����վ
	 * @param flag �ƶ��ı�־
	 * @param id ��Ҫ�ƶ�����Ƭ��id
	 * @return 
	 */
	public abstract int moveBatchCard(String flag,int id);
	
	/**
	 * ��ѯ����ָ����������Ƭ������
	 * @param flag ��Ƭ������
	 * @return
	 */
	public abstract int findCardByFlag(String name, String flag);
	
	/**
	 * ģ����ѯ��Ƭ������
	 * @param flag ��Ƭ����
	 * @param condition ��ѯ�ؼ���
	 * @return
	 */
	public abstract int findCardByConditionAndFlag(String name, String flag, String condition);
	
	/**
	 * ������Ƭ
	 * @param card ��Ҫ���µ���Ƭʵ��
	 * @return
	 */
	public abstract int updateCard(Card card);
	
	/**
	 * ģ��������Ƭ
	 * @param flag ��Ƭ�����ͣ�����վ/��Ƭ����
	 * @param condition ���صĹؼ���
	 * @param page ��ʼҳ��
	 * @param size ÿҳ������
	 * @return
	 */
	public abstract List<Card> findCardByCondition(String name,String flag, String condition, int page, int size);
	
	
}
