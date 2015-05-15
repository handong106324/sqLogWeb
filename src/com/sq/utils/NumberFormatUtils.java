package com.sq.utils;

import java.text.DecimalFormat;

public class NumberFormatUtils {

	public static String getTwoPoint(double val){
		DecimalFormat nf = new DecimalFormat("0.00");
		return nf.format(val);
	}
}
