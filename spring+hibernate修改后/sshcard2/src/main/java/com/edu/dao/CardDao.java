package com.edu.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.edu.entity.Card;
import com.edu.entity.User;

@Repository
public class CardDao extends DaoHibernate<Card>{
		
	/**
	 * ͳһע��Class����
	 */
	public CardDao() {
		super.setT(new Card());
	}
	
	/**
	 * �����µ���Ƭ
	 * @param card
	 * @return
	 */
	public int saveCard(Card card) {
		return this.insert(card);
	}
	
	/**
	 * ������Ƭid����Ƭ
	 * @param id ��Ƭid
	 * @return
	 */
	public Card findCardById(int id) {
		return this.findById(id);
	}
	
	/**
	 * ������Ƭ��Ϣ
	 * @param card ��Ҫ���µ���Ƭʵ��
	 * @return
	 */
	public int updateCard(Card card) {
		return this.update(card);
	}
	
	/**
	 * ������Ƭidɾ����Ƭ
	 * @param id ��Ƭ��id
	 * @return
	 */
	public int deleteCard(int id) {
		return this.delete(id);
	}
	
	/**
	 * ����id��������ɾ����Ƭ
	 * @param ids id�ļ���
	 * @return
	 */
	public int deleteCardList(int...ids) {
		return this.deleteList(ids);
	}
	
	/**
	 * ��ҳ������Ƭ
	 * @param flag ��Ƭ�����ͣ���Ƭ����/����վ
	 * @param page ҳ��
	 * @param size ÿҳ������
	 * @return
	 */
	public List<Card> findCardByPage(String name, String flag, int page, int size) {
		String sql = "select * from card where flag = ? and addby like ? ";
		return this.findPage(sql, new String[]{flag,name}, page, size);
	}
	
	/**
	 * ȡ�ò�����Ƭ���������
	 * @param flag ��Ƭ���ͣ���Ƭ����/����վ
	 * @return
	 */
	public int findCardNumber(String name, String flag) {
		String sql = "select * from card where flag = ? and addby like ? ";
		return this.find(sql, new String[]{flag,name}).size();
	}
	
	/**
	 * ȡ��ģ����ѯ����Ƭ����
	 * @param flag ��Ƭ���ͣ���Ƭ����ϵͳ/����վ
	 * @param condition ģ����ѯ�Ĺؼ���
	 * @return
	 */
	public int findConditionNumber(String name, String flag, String condition) {
		String sql = "select * from card where flag = ? and addby like ? ";
		List<Card> li =  this.findByFields(sql, new String[]{"name","department","address"},new String[]{flag, name}, condition, 0, 10);
		return li.size();
	}
	
	/**
	 * ģ��������Ƭ����
	 * @param flag ��Ƭ����
	 * @param condition ģ����ѯ�Ĺؼ���
	 * @param page ҳ��
	 * @param size ÿҳ������
	 * @return
	 */
	public List<Card> findConditionByPage(String name, String flag, String condition, int page, int size) {
		String sql = "select * from card where flag = ? and addby like ? ";
		return this.findByFields(sql, new String[]{"name","department","address"},new String[]{flag,name}, condition, page, size);
	}
	
	/**
	 * ִ���Զ���SQL���
	 * @param sql
	 * @param params
	 * @return
	 */
	public int exeSql(String sql, String[] params) {
		return this.ExeSql(sql, params);
	}
	
	/**
	 * �ƶ���Ƭ
	 * @param id ��Ƭ��id
	 * @param flag �ƶ�������
	 * @return
	 */
	public int moveBatchCard(int id, String flag) {
		String sql = "update card set flag = ? where id = ?";
		return this.ExeSql(sql, new String[]{flag,String.valueOf(id)});
	}
}
