package com.edu.services;

import java.util.List;

import com.edu.entity.User;

public interface IUserService {

	/**
	 * �û���¼��֤
	 * @param name �û���
	 * @param pass ����
	 * @return
	 */
	public abstract User findUserByLogin(String name, String pass);
	
	/**
	 * �û�ע��
	 * @param user �û�ʵ��
	 * @return
	 */
	public abstract int save(User user);
	
	/**
	 * �����û��������û�����
	 * @param name
	 * @return
	 */
	public abstract User findUserByName(String name);
	
	/**
	 * ��ҳ��ʾ�û�����
	 * @param page ��ǰҳ��
	 * @param size ÿҳ�Ĵ�С
	 * @return
	 */
	public abstract List<User> findUserByPage(int page, int size);
	
	/**
	 * �����Ѿ�ע����û�����
	 * @return
	 */
	public abstract int findUserNumber();
	
	/**
	 * ����ɾ���û�
	 * @return
	 */
	public abstract int deleteUser(int id);
	
	/**
	 * ����ɾ���û�
	 * @return 
	 */
	public abstract int deleteUserList(int...ids);
	
	/**
	 * ����ָ��id�����еĳ�������Ա������
	 * @param ids ����
	 * @param userType ����Ա������
	 * @return ���ҵ���������
	 */
	public abstract int findBySuperAdmin(String ids, String userType);
	
	/**
	 * �����û�id�����û���¼
	 * @param id �û�id
	 * @return
	 */
	public abstract User findUserById(int id);
	
	/**
	 * �޸��û�����
	 * @param u �û�ʵ��
 	 * @return
	 */
	public abstract int updateUserPass(User u);
	
	/**
	 * ����ģ�������û�������
	 * @param condition ģ�����ҹؼ���
	 * @return
	 */
	public abstract int findUserNumberByCondition(String condition);
	
	/**
	 * ������ģ�������û��Ľ����
	 * @param condition ģ�����ҹؼ���
	 * @return
	 */
	public abstract List<User> findUserByCondition(String condition, int page, int size);
}
