package com.edu.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.edu.entity.Card;
import com.edu.entity.User;

@Repository
public class UserDao extends DaoHibernate<User>{
		
	/**
	 * ��Class������ͳ��ע��
	 */
	public UserDao() {
		super.setT(new User());
	}
	
	/**
	 * ����µ��û�
	 * @param user �µ��û�����
	 * @return
	 */
	public int saveUser(User user) {
		return this.insert(user);
	}
	
	/**
	 * �����û�������������û�
	 * @param name �û���
	 * @param pass ����
	 * @return
	 */
	public User findUserByLogin(String name, String pass) {
		String sql = "select * from user where userName = ? and userPassword = ?";
		return this.findOne(sql, new String[]{name,pass});
	}
	
	/**
	 * �����û�����������
	 * @param name �û���
	 * @return
	 */
	public User findUserByName(String name) {
		String sql = "select * from user where userName = ?";
		return this.findOne(sql, new String[]{name});
	}
	
	/**
	 * ��ҳ��ʾ�û�����
	 * @param page ��ǰҳ��
	 * @param size ÿҳ��ʾ������
	 * @return
	 */
	public List<User> findUserByPage(int page, int size) {
		String sql = "select * from user";
		return this.findPage(sql, null, page, size);
	}
	
	/**
	 * ��ѯ�û�������������select count(*) ����
	 * @return
	 */
	public int findUserNumber() {
		String sql = "select * from user";
		return this.find(sql, null).size();
	}
	
	/**
	 * ����idɾ���û�
	 * @param id �û�id
	 * @return
	 */
	public int deleteUser(int id) {
		return this.delete(id);
	}
	
	/**
	 * ����ɾ���û�
	 * @param ids id�ļ���
	 * @return
	 */
	public int deleteUserList(int...ids) {
		return this.deleteList(ids);
	}
	
	/**
	 * ����ִ�м����г�������Ա������
	 * @param ids id�ļ���
	 * @param type ����Ա������
	 * @return ���ز��ҵ��ļ�¼����
	 */
	public int findBySuperAdmin(String ids,String type) {
		String sql = "select * from user where userType = ? and id in ("+ids+")";
		return this.find(sql, new String[]{type}).size();
	}
	
	/**
	 * �����û�id�����û�����
	 * @param id �û�id
	 * @return
	 */
	public User findByUserId(int id) {
		return this.findById(id);
	}
	
	/**
	 * �޸��û�����
	 * @param u �û�ʵ������
	 * @return
	 */
	public int updateUserPass(User u) {
		return this.update(u);
	}
	
	/**
	 * ģ�������û�����
	 * @param condition ģ����ѯ�Ĺؼ���
	 * @param page ҳ��
	 * @param size ÿҳ������
	 * @return
	 */
	public List<User> findConditionByPage(String condition, int page, int size) {
		String sql = "select * from user where 1=1 ";
		return this.findByFields(sql, new String[]{"userName","userType"},null, condition, page, size);
	}
	
	/**
	 * ȡ��ģ����ѯ���û�����
	 * @param condition ģ����ѯ�Ĺؼ���
	 * @return
	 */
	public int findConditionNumber(String condition) {
		String sql = "select * from user where 1=1 ";
		List<User> li =  this.findByFields(sql, new String[]{"userName","userType"},null, condition, 0, 10);
		return li.size();
	}
	
}
