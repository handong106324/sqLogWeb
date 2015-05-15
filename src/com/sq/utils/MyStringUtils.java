package com.sq.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class MyStringUtils {

	public static List<String> split(String str,char cha){
		System.out.println(str+":"+cha);
		List<String> list = new ArrayList<String>();
		StringBuffer sbf = new StringBuffer(str);
		StringBuffer newSbf = new StringBuffer("");
		for(int i = 0 ; i < sbf.length();i++){
			char c = sbf.charAt(i);
			if(c ==cha){
				list.add(newSbf.toString());
				newSbf.delete(0, newSbf.length() );
			}else {
				newSbf.append(c);
			}
			if(i == sbf.length() -1){
				list.add(newSbf.toString());
			}
		}
		return list;
	}
	
	
//	public static String[] split(String string,String ch){
//		int len = StringUtils.sp
//	}
	
	public static StringBuilder trimEnd(StringBuilder str , char c){
		if(str.charAt(str.length()-1)==c){
			str.deleteCharAt(str.length()-1);
		}
		return str;
	}
	
	public static void main(String[] a){
		List<String> list = new ArrayList<String>();
		String aa = "{'title':'','nodes':{'demo_node_1':{'name':'运营01哦:运营01','left':122,'top':189,'type':'start round_old','width':24,'height':24,'alt':true,'content':'运营01哦','progress':'12','groupId':'10','hours':'12','tl':'运营01'},'demo_node_2':{'name':'羊奶:运维','left':854,'top':131,'type':'end_old','width':87,'height':45,'alt':true,'content':'羊奶','progress':'100','groupId':'13','hours':'9','tl':'运维'},'demo_node_3':{'name':'技术韩东:技术1-韩东','left':297,'top':78,'type':'plug_old','width':87,'height':65,'alt':true,'content':'技术韩东','progress':'19','groupId':'12','hours':'11','tl':'技术1-韩东'},'demo_node_4':{'name':'歘策划:策划','left':296,'top':179,'type':'plug','width':87,'height':45,'alt':true,'content':'歘策划','progress':'44','groupId':'11','hours':'2','tl':'策划'},'demo_node_5':{'name':'李娜李娜:技术-李娜','left':311,'top':272,'type':'plug','width':87,'height':65,'alt':true,'content':'李娜李娜','progress':'66','groupId':'12','hours':'6','tl':'技术-李娜'},'demo_node_6':{'name':'测试李娜:测试','left':560,'top':222,'type':'plug','width':87,'height':45,'alt':true,'content':'测试李娜','progress':'77','groupId':'16','hours':'3','tl':'测试'},'demo_node_7':{'name':'总测试:测试','left':704,'top':141,'type':'plug','width':87,'height':45,'alt':true,'content':'总测试','progress':'99','groupId':'16','hours':'33','tl':'测试'},'demo_node_8':{'name':'哦哦:技术-陶龙洋','left':560,'top':88,'type':'plug','width':87,'height':65,'alt':true,'content':'哦哦','progress':'88','groupId':'12','hours':'8','tl':'技术-陶龙洋'}},'lines':{'demo_line_9':{'type':'sl','from':'demo_node_1','to':'demo_node_3','name':'','alt':true},'demo_line_10':{'type':'sl','from':'demo_node_3','to':'demo_node_8','name':'','alt':true},'demo_line_11':{'type':'sl','from':'demo_node_8','to':'demo_node_7','name':'','alt':true},'demo_line_12':{'type':'sl','from':'demo_node_7','to':'demo_node_2','name':'','alt':true},'demo_line_13':{'type':'sl','from':'demo_node_1','to':'demo_node_4','name':'','alt':true},'demo_line_14':{'type':'sl','from':'demo_node_4','to':'demo_node_6','name':'','alt':true},'demo_line_15':{'type':'sl','from':'demo_node_6','to':'demo_node_7','name':'','alt':true},'demo_line_17':{'type':'sl','from':'demo_node_1','to':'demo_node_5','name':'','alt':true},'demo_line_19':{'type':'sl','from':'demo_node_5','to':'demo_node_6','name':'','alt':true}},'areas':{},'initNum':21}";
		changeValueOfInfoDelOld("demo_node_1", aa);
	}
	
	public static String trim(String str , String c){
		if(str == null){
			return "";
					
		}
		if(str.startsWith(c)){
			str = str.substring(c.length());
		}
		if(str.endsWith(c)){
			str = str.substring(0,str.length()  - c.length());
		}
		return str;
	}
	
	public static String changeValueOfInfoInsertOld(String nodeName,String info){
		StringBuilder sb = new StringBuilder(info);
		int index = info.indexOf(nodeName);
		String nstr = info.substring(index);
		int idx = nstr.indexOf("width");
		sb.insert(index + idx - 3, "_old");
		String newStr = sb.toString();
		while(newStr.indexOf("_old_old") > 0){
			newStr = newStr.replace("_old_old", "_old");
		}
		return newStr;
	}
	public static String changeValueOfInfoDelOld(String nodeName,String info){
		StringBuilder sb = new StringBuilder(info);
		int index = info.indexOf(nodeName);
		String nstr = info.substring(index);
		int idx = nstr.indexOf("width");
		String old = sb.substring(index + idx - 7, index + idx - 3);
		if(old.equals("_old")){
			sb.delete(index + idx - 7, index + idx - 3);
		}
		String newStr = sb.toString();
		while(newStr.indexOf("_old_old") > 0){
			newStr = newStr.replace("_old_old", "_old");
		}
		return newStr;
	}


	public static Object clearDup(String object) {
		// TODO Auto-generated method stub
		if(object == null) return null;
		if(object.trim().length() == 0)return object.trim();
		String[] strs = object.split(",");
		List<String> strList = new ArrayList<String>();
		StringBuilder sb = new StringBuilder("");
		for(String s :strs){
			if(s!= null && s.trim().length() > 0){
				if(!strList.contains(s)){
					strList.add(s);
					sb.append(s).append(",");
				}
			}
		}
		if(sb.toString().endsWith(",")){
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}
	
	public static String transferHanToEn(String str , String cn, String en) {
		if(str.contains(cn)){
			return str.replaceAll(cn, en);
		}
		return str;
	}
	
	public static int getIndex(double income , String[]  scopes){
		int len = scopes.length;
		len ++;
		double[]   vals   = new double[len];
		vals[0] = income;
		for(int i  = 1 ; i < len ; i++){
			vals[i] = Long.parseLong(scopes[i -1]);
		}
		Arrays.sort(vals);
		return (Arrays.binarySearch(vals, income) );
	}
}
