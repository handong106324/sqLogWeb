package com.sq.log;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * 枚举找不到，抛出此异常
 * @author  ZhengWei
 * @version 2012-12-25下午4:07:07
 * @since   1.6
 */
public class EnumNotFoundException extends Exception {

    private Throwable nestedThrowable = null;

    public EnumNotFoundException() {
        super();
    }

    public EnumNotFoundException(String msg) {
        super(msg);
    }

    public EnumNotFoundException(Throwable nestedThrowable) {
        this.nestedThrowable = nestedThrowable;
    }

    public EnumNotFoundException(String msg, Throwable nestedThrowable) {
        super(msg);
        this.nestedThrowable = nestedThrowable;
    }

    public void printStackTrace() {
        super.printStackTrace();
        if (nestedThrowable != null) {
            nestedThrowable.printStackTrace();
        }
    }

    public void printStackTrace(PrintStream ps) {
        super.printStackTrace(ps);
        if (nestedThrowable != null) {
            nestedThrowable.printStackTrace(ps);
        }
    }

    public void printStackTrace(PrintWriter pw) {
        super.printStackTrace(pw);
        if (nestedThrowable != null) {
            nestedThrowable.printStackTrace(pw);
        }
    }
}

