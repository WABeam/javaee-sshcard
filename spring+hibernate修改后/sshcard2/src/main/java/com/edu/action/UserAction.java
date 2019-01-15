package com.edu.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.edu.entity.User;
import com.edu.services.IUserService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class UserAction extends ActionSupport{
	
	@Resource
	private IUserService userService;
	
	//�û���Ϣʵ��
	private User user;  
	
	private String veriCode;

	//������ʾ
	private String msg;
	
	//ȷ������
	private String re_password;
	
	//������
	private String old_password;
	
	//�û������ؼ���
	private String content;
	
	//ȡ���û�����Ϣʵ��
	private List<User> userList;
	
	//actionִ�гɹ�����success
	private final String SUCCESS = "success";
	
	//actionִ��ʧ�ܷ���error
	private final String ERROR = "error";
	
	//ÿҳĬ����ʾ������Ϊ10
	private final int SIZE = 10;
	
	//��ǰҳ��
	private int page;
	
	//���ҵ����û�����
	private int sum;
	
	//id�ļ���
	private String ids;
	
	/**
	 * ����ҳ�������
	 * 1���û�������ϵͳ
	 * 2����Ƭ������ϵͳ
	 */
	private String type;
	
	/**
	 * ��ҳ��ʾ�û���Ϣ
	 * sum��ʾ����ע����û�����
	 * @return
	 */
	public String userList() {	
		if (content!=null&&content.length()>0) {
			sum = userService.findUserNumberByCondition(content);
			userList = userService.findUserByCondition(content, page, SIZE);
		} else {
			sum = userService.findUserNumber();
			userList = userService.findUserByPage(page, SIZE);
		}
		return SUCCESS;
	}
	
	/**
	 * ����ɾ���û�
	 * @return
	 */
	public String deleteUsers() {
		Map<String,Object> output = ActionContext.getContext().getSession();
		String name = (String) output.get("user_name");
		//��ǰ��¼�û����û�����
		User u1 = userService.findUserByName(name);
		List<Integer> intList  = JSON.parseArray(ids,Integer.TYPE);
		String ss = intList.toString();
		//��ɾ����id�����к��г�������Ա������
		int cot = userService.findBySuperAdmin(ss.substring(1,ss.length()-1), "��������Ա");
		int[] d = new int[intList.size()];
		for(int i = 0;i<intList.size();i++){
			//��ɾ����id�������Ƿ�����Լ�
			if (intList.get(i).equals(u1.getId())) {
				cot = -1;
			}
		    d[i] = intList.get(i);
		}
		if (cot==0) {
			userService.deleteUserList(d);
		} else if (cot==-1){
			return "error1";
		} else if (cot>0) {
			return "error2";
		}
		return SUCCESS;
	}
	
	//����ɾ���û�
	public String deleteUser() {
		Map<String,Object> output = ActionContext.getContext().getSession();
		String name = (String) output.get("user_name");
		//��ǰ��¼�û����û�����
		User u1 = userService.findUserByName(name);
		User u2 = userService.findUserById(user.getId());
		if (u1.getId()==user.getId()) {  //ɾ���Լ�
			return "error1";
		} else if (u2.getUserType().equals("��������Ա")) {   //ɾ����������Ա
			return "error2";
		} else {
			userService.deleteUser(user.getId());
			return SUCCESS;
		}
	}
	
	/**
	 * �޸��û�����
	 * @return
	 */
	public String changePass() {
		User u = userService.findUserById(user.getId());
		if (!re_password.equals(user.getUserPassword())) {
			return "error1";
		} else if (!u.getUserPassword().equals(old_password)) {
			return "error2";
		} else {
			u.setUserPassword(user.getUserPassword());
			userService.updateUserPass(u);
			return SUCCESS;
		}
	}
	
	/**
	 * ��֤�û��Ƿ��¼
	 * �û���¼���û���¼��Ϣ�洢��session��key��user_name��
	 * ͨ���ж�user_name���Ƿ���ֵ���ж��û��Ƿ��¼
	 * @return
	 */
	public String checkIn() {
		String forward = null;
		Map<String,Object> output = ActionContext.getContext().getSession();
		String name = (String) output.get("user_name");
		if (name==null) {
			msg = "����δ��¼�����ȵ�¼";
			forward = ERROR;
		} else {
			if ("1".equals(type)) {
				forward = "userPage";
			} else {
				forward = "cardPage";
			}
		}
		return forward;
	}

	/**
	 * �û���¼����¼���û����洢��session��
	 * @return
	 * @throws Exception
	 */
	public String userLogin() throws Exception{
		String forward=null;
		Map<String,Object> output = ActionContext.getContext().getSession();
		User u = userService.findUserByLogin(user.getUserName(), user.getUserPassword());
		if (u==null) {
			msg = "�û������������";
			forward = ERROR;
		} else {
			output.put("user_name", u.getUserName());
			forward = SUCCESS;
		}
		return forward;
	}
	
	/**
	 * �û�ע�ᣬveriCode���û��ύ����֤��
	 * v_code�Ǵ洢��session�е���ȷ����֤��
	 * @return
	 */
	public String userRegister() {
		Map<String,Object> output = ActionContext.getContext().getSession();
		String ve_code = (String)output.get("v_code");
		User u = userService.findUserByName(user.getUserName());
		if (ve_code==null || !ve_code.equalsIgnoreCase(veriCode)) {
			msg = "��֤�����";
			return ERROR;
		}
		if (u==null) {
			user.setUserType("��ͨ����Ա");
			userService.save(user);
			return SUCCESS;
		} else {
			msg = "���û��Ѵ���";
			return ERROR;
		}
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getRe_password() {
		return re_password;
	}

	public void setRe_password(String re_password) {
		this.re_password = re_password;
	}

	public String getVeriCode() {
		return veriCode;
	}

	public void setVeriCode(String veriCode) {
		this.veriCode = veriCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getOld_password() {
		return old_password;
	}

	public void setOld_password(String old_password) {
		this.old_password = old_password;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
