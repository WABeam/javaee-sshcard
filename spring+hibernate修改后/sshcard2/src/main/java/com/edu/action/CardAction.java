package com.edu.action;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.edu.entity.Card;
import com.edu.entity.User;
import com.edu.services.ICardService;
import com.edu.services.IUserService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class CardAction extends ActionSupport{
	
	//actionִ�гɹ�����success
	private final String SUCCESS = "success";

	@Autowired
	private ICardService cardService;
	
	@Resource
	private IUserService userService;
	
	//ǰ����ʾ�����ݼ�
	private List<Card> listCard;
	
	//��ҳ��ʼ��ҳ��
	private int page;
	
	//��Ƭ���ͣ�0��ʾ������Ƭ��1��ʾ����վ��Ƭ
	private String flag;
	
	//id�ļ���
	private String ids;
	
	//�ϴ���ͷ��
	private File file;
	
	//�ļ�����
	private String fileContentType;

	//�ļ���
	private String fileFileName;
	
	//��ҳÿҳ�Ĵ�С
	private final int SIZE = 10;
	
	//Card��ʵ�弯
	private Card myCard;
	
	//ģ�������ؼ���
	private String content;
	
	//���ҵ�����Ƭ����
	private int sum;
	
	/**
	 * ��Ƭ����ϵͳ��ҳȡ�����е�����
	 * sum:��ѯ�������м�¼������
	 * listCard:��ѯ���Ľ����
	 * content:�����Ĺؼ���
	 * @return
	 */
	public String cardList() {
		Map<String,Object> output = ActionContext.getContext().getSession();
		String name = (String) output.get("user_name");
		User u = userService.findUserByName(name);
		if (u.getUserType().equals("��������Ա")) {  //��������Ա��ʾ������Ƭ������ֻ��ʾ�Լ���������Ƭ
			name = "%%";
		}
		if (content!=null&&content.length()>0) {
			sum = cardService.findCardByConditionAndFlag(name, flag, content);
			listCard = cardService.findCardByCondition(name, flag, content, page, SIZE);
		} else {
			sum = cardService.findCardByFlag(name, flag);
			listCard = cardService.findCardByPage(name, flag, page, SIZE);
		}
		return SUCCESS;
	}
	
	/**
	 * �����Ƭ��Ĭ��flagΪ0����ʾ������Ƭ
	 * �������ɺ󣬸��ݷ��ص�Ψһ��������id������ͷ��
	 * ͷ��洢���ļ���Ϊid.jpg
	 * ͷ�εĴ洢·��ΪC:\\workspace\\uploadImg
	 * @return
	 */
	public String newCard() {
		myCard.setFlag("0");
		Map<String,Object> output = ActionContext.getContext().getSession();
		String name = (String) output.get("user_name");
		myCard.setAddby(name);  //��˭��������Ƭ
		int newId = cardService.save(myCard);
		try {
			String fileN = newId + ".jpg";
			String dir = "C:\\workspace\\uploadImg";
			String path = dir + File.separator+fileN;
			File destFile = new File(path);;
			InputStream inputStream = new FileInputStream(file);
			//�������洢�ļ�
	        FileUtils.copyInputStreamToFile(inputStream, destFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * ������Ƭ����
	 * ����ϴ����µ�ͷ�������ͷ�����
	 * @return
	 */
	public String updateCard() {
		myCard.setFlag("0");
		Card cc = cardService.findCardById(myCard.getId());
		myCard.setAddby(cc.getAddby());
		cardService.updateCard(myCard);
		if (file!=null) {
			try {
				String fileN = myCard.getId() + ".jpg";
				String dir = "C:\\workspace\\uploadImg";
				String path = dir + File.separator+fileN;
				File destFile = new File(path);;
				InputStream inputStream = new FileInputStream(file);
		        FileUtils.copyInputStreamToFile(inputStream, destFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}
	
	// ����ɾ����ѡ����Ƭ(����)
	public String deleteCards() {
		List<Integer> intList  = JSON.parseArray(ids,Integer.TYPE);
		int[] d = new int[intList.size()];
		for(int i = 0;i<intList.size();i++){
		    d[i] = intList.get(i);
		}
		cardService.deleteBatchCard(d);
		return SUCCESS;
	}
	
	// ɾ��������Ƭ
	public String deleteCard() {
		cardService.deleteCard(myCard.getId());
		return SUCCESS;
	}
	
	//��ѡ����Ƭ�ƶ�������վ
	public String moveCardsToTrash() {
		List<Integer> intList  = JSON.parseArray(ids,Integer.TYPE);
		for (int i=0; i<intList.size(); i++) {
			cardService.moveBatchCard("1", intList.get(i));
		}
		return SUCCESS;
	}
	
	//������վ����Ƭ��ԭ
	public String moveCardsFromTrash() {
		List<Integer> intList  = JSON.parseArray(ids,Integer.TYPE);
		for (int i=0; i<intList.size(); i++) {
			cardService.moveBatchCard("0", intList.get(i));
		}
		return SUCCESS;
	}
	
	//��ȡ��Ƭ��ͷ��
	public void getUserImg() {
		try {
			byte[] b =  getHeadByte(myCard.getId());
			HttpServletResponse response = ServletActionContext.getResponse();
			ServletOutputStream out = response.getOutputStream();
			out.write(b);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//ȡ�ö�����ͼ��
	public byte[] getHeadByte(int id) throws Exception {     
        byte[] buffer = null;  
        try {  
            File file = new File("C:\\workspace\\uploadImg\\"+id+".jpg");  
            FileInputStream fis = new FileInputStream(file);  
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);  
            byte[] b = new byte[1000];  
            int n;  
            while ((n = fis.read(b)) != -1) {  
                bos.write(b, 0, n);  
            }  
            fis.close();  
            bos.close();  
            buffer = bos.toByteArray();  
        } catch(Exception e) {  
        	File file = new File("C:\\workspace\\uploadImg\\test.jpg");  
            FileInputStream fis = new FileInputStream(file);  
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);  
            byte[] b = new byte[1000];  
            int n;  
            while ((n = fis.read(b)) != -1) {  
                bos.write(b, 0, n);  
            }  
            fis.close();  
            bos.close();  
            buffer = bos.toByteArray();  
        }
        return buffer;  
    }  

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Card getMyCard() {
		return myCard;
	}

	public void setMyCard(Card myCard) {
		this.myCard = myCard;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public List<Card> getListCard() {
		return listCard;
	}

	public void setListCard(List<Card> listCard) {
		this.listCard = listCard;
	}
}
