package com.edu.services;

public interface IDbToExcelService {
	
	/**
	 * ��Excel�е����ݵ��뵽���ݿ���
	 * @param excelpath excel���ڵ�·��
	 * @param table ���ݱ���
	 * @param fieldList �ֶ�
	 * @param columnCount һ���ж�����
	 */
	public abstract void excelToDb(String excelpath, String table, String fieldList, int columnCount);
	
	/**
	 * �����ݱ�����Excel
	 * @param table ���ݱ���
	 * @param fieldList �ֶ�
	 * @param titles ����
	 * @param condition ����
	 * @param order ˳��
	 * @param file �ļ�·��
	 */
	public abstract void dbToExcel(String table, String[] fieldList, String[] titles, String condition, String order, String file);

}
