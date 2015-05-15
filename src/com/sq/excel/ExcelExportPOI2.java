package com.sq.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * 利用开源组件POI3.0.2动态导出EXCEL文档
 * 
 * @author leno
 * @version v1.0
 * @param <T>
 *            应用泛型，代表任意一个符合javabean风格的类
 *            注意这里为了简单起见，boolean型的属性xxx的get器方式为getXxx(),而不是isXxx()
 *            byte[]表jpg格式的图片数据
 */
public class ExcelExportPOI2<T> {
	
	public void exportExcel(String title, List<String[]> titleInfo,String[] headers, List<Object[]> dataset,
				OutputStream out) {
		exportExcel(title, titleInfo, headers, dataset, out, "yyyy-MM-dd");
	}
	public void exportExcel(String title,String period,String[] headers, List<Object[]> dataset,
			OutputStream out) {
		List<String[]> ls = new ArrayList<String[]>();
		String[] company ={"Company:","神奇时代"};
		String[] s1 ={"Period:",period};
		String[] currency ={"Currency:","RMB"};
		String[] sheetname ={title};//{"应收账款明细表 Accounts Receivable Detail"};
		String[] s2 ={""};
		ls.add(company);
		ls.add(s1);
		ls.add(currency);
		ls.add(sheetname);
		ls.add(s2);
	exportExcel(title, ls, headers, dataset, out, "yyyy-MM-dd");
}

	/**
	 * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
	 * 
	 * @param title
	 *            表格标题名
	 * @param headers
	 *            表格属性列名数组
	 * @param dataset
	 *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
	 *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
	 * @param out
	 *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
	 * @param pattern
	 *            如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
	 */
	@SuppressWarnings("deprecation")
	public void exportExcel(String title,List<String[]> titleInfo, String[] headers,
			List<Object[]> dataset, OutputStream out, String pattern) {
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 15);
		sheet.setColumnWidth(0, (short) (35.7 * 180));
		sheet.setColumnWidth(1, (short) (35.7 * 240));
		sheet.setColumnWidth(2, (short) (35.7 * 200));
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
//		style.setFillForegroundColor(HSSFColor.SEA_GREEN.index);
		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.BLACK.index);
		font.setFontHeightInPoints((short) 9);
//		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 生成并设置另一个样式
		HSSFCellStyle style2 = workbook.createCellStyle();
//		style2.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
//		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		font2.setFontHeightInPoints((short) 9);
		// 把字体应用到当前的样式
		style2.setFont(font2);
		// 声明一个画图的顶级管理器
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		// 定义注释的大小和位置,详见文档
//		HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,
//				0, 0, 0, (short) 4, 2, (short) 6, 5));
		// 设置注释内容
//		comment.setString(new HSSFRichTextString(""));//可以在POI中添加注释！
		// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
//		comment.setAuthor("");//leno
		if(titleInfo!=null&&titleInfo.size()>0){
			for (int i = 0; i < titleInfo.size(); i++) {
				HSSFRow row = sheet.createRow(i);
				for (short j = 0; j < titleInfo.get(i).length; j++) {
					String[] s =  titleInfo.get(i);
					HSSFCell cell = row.createCell(j);
					HSSFCellStyle style3 = workbook.createCellStyle();
					HSSFFont font3 = workbook.createFont();
					font3.setFontHeightInPoints((short)9);
					style3.setFont(font3);
					cell.setCellStyle(style3);
					HSSFRichTextString text = new HSSFRichTextString(s[j]);
					cell.setCellValue(text);
				}
			}
		}
		// 产生表格标题行
		HSSFRow row = sheet.createRow(titleInfo.size());
		for (short i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}
		// 遍历集合数据，产生数据行
//		Iterator<T> it = dataset.iterator();
		int index = titleInfo.size();
//		while (it.hasNext()) {
		for (Object[] it:dataset) {
			index++;
			row = sheet.createRow(index);
//			T t = (T) it.next();
			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
//			Field[] fields = t.getClass().getDeclaredFields();
			//jFinal使用数组
			Object[] fields = it;
			for (short i = 0; i < fields.length; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(style2);
//				Field field = fields[i];
//				String fieldName = field.getName();
//				String getMethodName = "get"
//						+ fieldName.substring(0, 1).toUpperCase()
//						+ fieldName.substring(1);
				try {
//					Class tCls = t.getClass();
//					Method getMethod = tCls.getMethod(getMethodName,
//							new Class[] {});
					Object value = fields[i];//getMethod.invoke(t, new Object[] {});
					// 判断值的类型后进行强制类型转换
					String textValue = null;
					// if (value instanceof Integer) {
					// int intValue = (Integer) value;
					// cell.setCellValue(intValue);
					// } else if (value instanceof Float) {
					// float fValue = (Float) value;
					// textValue = new HSSFRichTextString(
					// String.valueOf(fValue));
					// cell.setCellValue(textValue);
					// } else if (value instanceof Double) {
					// double dValue = (Double) value;
					// textValue = new HSSFRichTextString(
					// String.valueOf(dValue));
					// cell.setCellValue(textValue);
					// } else if (value instanceof Long) {
					// long longValue = (Long) value;
					// cell.setCellValue(longValue);
					// }
					if (value instanceof Boolean) {
						boolean bValue = (Boolean) value;
						textValue = bValue+"";
						if (!bValue) {
							textValue = bValue+"";
						}
					} else if (value instanceof Date) {
						Date date = (Date) value;
						SimpleDateFormat sdf = new SimpleDateFormat(pattern);
						textValue = sdf.format(date);
					} else if (value instanceof byte[]) {
						// 有图片时，设置行高为60px;
						row.setHeightInPoints(60);
						// 设置图片所在列宽度为80px,注意这里单位的一个换算
						sheet.setColumnWidth(i, (short) (35.7 * 80));
						// sheet.autoSizeColumn(i);
						byte[] bsValue = (byte[]) value;
						HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
								1023, 255, (short) 6, index, (short) 6, index);
						anchor.setAnchorType(2);
						patriarch.createPicture(anchor, workbook.addPicture(
								bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
					} else {
						// 其它数据类型都当作字符串简单处理
						textValue = value.toString();
					}
					// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
					if (textValue != null) {
						Pattern p = Pattern.compile("^//d+(//.//d+)?$");
						Matcher matcher = p.matcher(textValue);
						if (matcher.matches()) {
							// 是数字当作double处理
							cell.setCellValue(Double.parseDouble(textValue));
						} else {
							HSSFRichTextString richString = new HSSFRichTextString(
									textValue);
							HSSFFont font3 = workbook.createFont();
							font3.setColor(HSSFColor.BLACK.index);
							richString.applyFont(font3);
							cell.setCellValue(richString);
						}
					}
				} catch (SecurityException e) {
					e.printStackTrace();
//				} catch (NoSuchMethodException e) {
//					e.printStackTrace();
				} catch (IllegalArgumentException e) {
//					e.printStackTrace();
//				} catch (IllegalAccessException e) {
//					e.printStackTrace();
//				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} finally {
					// 清理资源
				}
			}
		}
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public void exportExcel(String title,List<String[]> titleInfo, List<String> headers,List<Object[]> dataset, OutputStream out, String pattern) {
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 15);
		sheet.setColumnWidth(0, (short) (35.7 * 180));
		sheet.setColumnWidth(1, (short) (35.7 * 240));
		sheet.setColumnWidth(2, (short) (35.7 * 200));
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
//		style.setFillForegroundColor(HSSFColor.SEA_GREEN.index);
		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.BLACK.index);
		font.setFontHeightInPoints((short) 9);
//		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 生成并设置另一个样式
		HSSFCellStyle style2 = workbook.createCellStyle();
//		style2.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
//		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		font2.setFontHeightInPoints((short) 9);
		// 把字体应用到当前的样式
		style2.setFont(font2);
		// 声明一个画图的顶级管理器
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		// 定义注释的大小和位置,详见文档
//		HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,
//				0, 0, 0, (short) 4, 2, (short) 6, 5));
		// 设置注释内容
//		comment.setString(new HSSFRichTextString(""));//可以在POI中添加注释！
		// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
//		comment.setAuthor("");//leno
		if(titleInfo!=null&&titleInfo.size()>0){
			for (int i = 0; i < titleInfo.size(); i++) {
				HSSFRow row = sheet.createRow(i);
				for (short j = 0; j < titleInfo.get(i).length; j++) {
					String[] s =  titleInfo.get(i);
					HSSFCell cell = row.createCell(j);
					HSSFCellStyle style3 = workbook.createCellStyle();
					HSSFFont font3 = workbook.createFont();
					font3.setFontHeightInPoints((short)9);
					style3.setFont(font3);
					cell.setCellStyle(style3);
					HSSFRichTextString text = new HSSFRichTextString(s[j]);
					cell.setCellValue(text);
				}
			}
		}
		// 产生表格标题行
		HSSFRow row = sheet.createRow(titleInfo.size());
		for (short i = 0; i < headers.size(); i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers.get(i));
			cell.setCellValue(text);
		}
		// 遍历集合数据，产生数据行
//		Iterator<T> it = dataset.iterator();
		int index = titleInfo.size();
		HSSFFont font3 = workbook.createFont();
//		while (it.hasNext()) {
		for (Object[] it:dataset) {
			index++;
			row = sheet.createRow(index);
//			T t = (T) it.next();
			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
//			Field[] fields = t.getClass().getDeclaredFields();
			//jFinal使用数组
			Object[] fields = it;
			for (short i = 0; i < fields.length; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(style2);
				try {
					Object value = fields[i];//getMethod.invoke(t, new Object[] {});
					// 判断值的类型后进行强制类型转换
					String textValue = null;
					if (value instanceof Boolean) {
						boolean bValue = (Boolean) value;
						textValue = bValue+"";
						if (!bValue) {
							textValue = bValue+"";
						}
					} else if (value instanceof Date) {
						Date date = (Date) value;
						SimpleDateFormat sdf = new SimpleDateFormat(pattern);
						textValue = sdf.format(date);
					} else if (value instanceof byte[]) {
						// 有图片时，设置行高为60px;
						row.setHeightInPoints(60);
						// 设置图片所在列宽度为80px,注意这里单位的一个换算
						sheet.setColumnWidth(i, (short) (35.7 * 80));
						// sheet.autoSizeColumn(i);
						byte[] bsValue = (byte[]) value;
						HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
								1023, 255, (short) 6, index, (short) 6, index);
						anchor.setAnchorType(2);
						patriarch.createPicture(anchor, workbook.addPicture(
								bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
					} else {
						// 其它数据类型都当作字符串简单处理
						textValue = value.toString();
					}
					// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
					if (textValue != null) {
						Pattern p = Pattern.compile("^//d+(//.//d+)?$");
						Matcher matcher = p.matcher(textValue);
						if (matcher.matches()) {
							// 是数字当作double处理
							cell.setCellValue(Double.parseDouble(textValue));
						} else {
							HSSFRichTextString richString = new HSSFRichTextString(
									textValue);
							
							font3.setColor(HSSFColor.BLACK.index);
							richString.applyFont(font3);
							cell.setCellValue(richString);
						}
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} finally {
					// 清理资源
				}
			}
		}
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void test() {
//		ExcelExportPOI2 ex = new ExcelExportPOI2();
//		String[] headers = { "yearMonth", "projectId", "companyId", "realMoneyAndTax", "realMoneyNoTax","pic" };
//		List<Object[]> dataset = new ArrayList<Object[]>();
//		try {
//			BufferedInputStream bis = new BufferedInputStream(
//					new FileInputStream("E://smile.jpg"));
//			byte[] buf = new byte[bis.available()];
//			while ((bis.read(buf)) != -1) {
//				//
//			}
//			Object[] s1 = {"201408", "1", "1", "100", "100",buf};
//			dataset.add(s1);
//			OutputStream out = new FileOutputStream("E://a.xls");
//			String title = "应收账款明细表";
//			ex.exportExcel(title,headers, dataset, out);
//			out.close();
//			JOptionPane.showMessageDialog(null, "导出成功!");
//			System.out.println("excel导出成功！");
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	public static void main(String args[]){
//		new ExcelExportPOI2().test();
	}
}