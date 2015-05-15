package test;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import com.sq.utils.MyTimeUtils;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(MyTimeUtils.getYYYYMMDD(new Date()));
	}

}
