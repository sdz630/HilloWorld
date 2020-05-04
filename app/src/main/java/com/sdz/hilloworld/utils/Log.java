package com.sdz.hilloworld.utils;

public class Log {

    private static String prefix() {
        StackTraceElement stack[] = (new Throwable()).getStackTrace();
        StringBuilder prefix = new StringBuilder();
        for (StackTraceElement element : stack) {
            if (!element.getClassName().equals(Log.class.getName())) {
                prefix.append(element.getClassName()).append("(")
                        .append(element.getMethodName())
                        .append(":")
                        .append(element.getLineNumber())
                        .append("): ");
                break;
            }
        }
        return prefix.toString();
    }

    private static String wrapTAG(String TAG) {
        return "[" + TAG + "] ";
    }

    public static void i(String TAG, String msg, Object... arg) {
        System.out.println(prefix() + wrapTAG(TAG) + String.format(msg, arg));
    }

    public static void e(String TAG, Throwable throwable) {
        System.out.println(prefix() + wrapTAG(TAG) + throwable.getMessage());
    }
}
