package com.disney4a.baymax_example.app.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;

import com.disney4a.baymax.utils.Reflect;

import java.util.HashMap;

public class Utils {

    public static class KeyValue {
        private String key;
        private Object value;

        public KeyValue(String key, Object value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }


    interface Convert {
        Object convert(String value);
    }

    static final HashMap<String, Convert> typeMappings = new HashMap<String, Convert>() {

        {
            put("int", new Convert() {
                @Override
                public Object convert(String value) {
                    return Integer.parseInt(value);
                }
            });
            put("int[]", new Convert() {
                @Override
                public Object convert(String value) {
                    String[] arrays = value.split(",");
                    int[] results = new int[arrays.length];
                    for (int i = 0; i < arrays.length; i++) {
                        results[i] = Integer.parseInt(arrays[i]);
                    }
                    return results;
                }
            });

            put("double", new Convert() {
                @Override
                public Object convert(String value) {
                    return Double.parseDouble(value);
                }
            });
            put("double[]", new Convert() {
                @Override
                public Object convert(String value) {
                    String[] arrays = value.split(",");
                    double[] results = new double[arrays.length];
                    for (int i = 0; i < arrays.length; i++) {
                        results[i] = Double.parseDouble(arrays[i]);
                    }
                    return results;
                }
            });

            put("short", new Convert() {
                @Override
                public Object convert(String value) {
                    return Short.parseShort(value);
                }
            });
            put("short[]", new Convert() {
                @Override
                public Object convert(String value) {
                    String[] arrays = value.split(",");
                    short[] results = new short[arrays.length];
                    for (int i = 0; i < arrays.length; i++) {
                        results[i] = Short.parseShort(arrays[i]);
                    }
                    return results;
                }
            });

            put("float", new Convert() {
                @Override
                public Object convert(String value) {
                    return Float.parseFloat(value);
                }
            });
            put("float[]", new Convert() {
                @Override
                public Object convert(String value) {
                    String[] arrays = value.split(",");
                    float[] results = new float[arrays.length];
                    for (int i = 0; i < arrays.length; i++) {
                        results[i] = Float.parseFloat(arrays[i]);
                    }
                    return results;
                }
            });

            put("boolean", new Convert() {
                @Override
                public Object convert(String value) {
                    return Boolean.parseBoolean(value);
                }
            });
            put("boolean[]", new Convert() {
                @Override
                public Object convert(String value) {
                    String[] arrays = value.split(",");
                    boolean[] results = new boolean[arrays.length];
                    for (int i = 0; i < arrays.length; i++) {
                        results[i] = Boolean.parseBoolean(arrays[i]);
                    }
                    return results;
                }
            });

            put("long", new Convert() {
                @Override
                public Object convert(String value) {
                    return Long.parseLong(value);
                }
            });
            put("long[]", new Convert() {
                @Override
                public Object convert(String value) {
                    String[] arrays = value.split(",");
                    long[] results = new long[arrays.length];
                    for (int i = 0; i < arrays.length; i++) {
                        results[i] = Long.parseLong(arrays[i]);
                    }
                    return results;
                }
            });

            put("char", new Convert() {
                @Override
                public Object convert(String value) {
                    return value.charAt(0);
                }
            });
            put("char[]", new Convert() {
                @Override
                public Object convert(String value) {
                    String[] arrays = value.split(",");
                    char[] results = new char[arrays.length];
                    for (int i = 0; i < arrays.length; i++) {
                        results[i] = arrays[i].charAt(0);
                    }
                    return results;
                }
            });

            put("string", new Convert() {
                @Override
                public Object convert(String value) {
                    return value;
                }
            });
            put("string[]", new Convert() {
                @Override
                public Object convert(String value) {
                    String[] arrays = value.split(",");
                    String[] results = new String[arrays.length];
                    for (int i = 0; i < arrays.length; i++) {
                        results[i] = arrays[i];
                    }
                    return results;
                }
            });
        }
    };

    /**
     * 启动活动，不带参数
     *
     * @param context
     * @param activityClass
     */
    public static void startActivity(Context context, Class<?> activityClass) {
        context.startActivity(new Intent(context, activityClass));
    }

    /**
     * 启动服务，不带参数
     *
     * @param context
     * @param serviceClass
     */
    public static void startService(Context context, Class<?> serviceClass) {
        context.startService(new Intent(context, serviceClass));
    }

    /**
     * 启动活动，结构数组
     *
     * @param context
     * @param activityClass
     * @param keyValues
     */
    public static void startActivity(Context context, Class<?> activityClass, KeyValue... keyValues) {
        context.startActivity(createIntent(context, activityClass, keyValues));
    }

    /**
     * 启动活动，字符串形式
     * name=张三, age=(int)23, arrays=(int[])23,24
     *
     * @param context
     * @param activityClass
     * @param params
     */
    public static void startActivity(Context context, Class<?> activityClass, String... params) {
        context.startActivity(createIntent(context, activityClass, params));
    }


    /**
     * 启动服务
     *
     * @param context
     * @param serviceClass
     * @param keyValues
     */
    public static void startService(Context context, Class<?> serviceClass, KeyValue... keyValues) {
        context.startService(createIntent(context, serviceClass, keyValues));
    }

    /**
     * @param context
     * @param serviceClass
     * @param params
     */
    public static void startService(Context context, Class<?> serviceClass, String... params) {
        context.startService(createIntent(context, serviceClass, params));
    }

    private static Intent createIntent(Context context, Class<?> targetClass, KeyValue... keyValues) {
        Intent intent = new Intent(context, targetClass);
        Reflect reflect = new Reflect();
        Bundle mExtras = reflect.on(intent).get("mExtras");
        if (mExtras == null) {
            reflect.on(intent).set("mExtras", mExtras = new Bundle());
        }
        reflect.on(mExtras).method("unparcel").invoke();
        ArrayMap<String, Object> mMap = reflect.on(mExtras).get("mMap");
        for (KeyValue keyValue : keyValues) {
            mMap.put(keyValue.getKey(), keyValue.getValue());
        }
        return intent;
    }

    private static Intent createIntent(Context context, Class<?> targetClass, String... params) {
        Intent intent = new Intent(context, targetClass);
        Reflect reflect = new Reflect();
        Bundle mExtras = reflect.on(intent).get("mExtras");
        if (mExtras == null) {
            reflect.on(intent).set("mExtras", mExtras = new Bundle());
        }
        reflect.on(mExtras).method("unparcel").invoke();
        ArrayMap<String, Object> mMap = reflect.on(mExtras).get("mMap");

        for (String param : params) {
            String[] keyValues = param.split("=");
            String key = keyValues[0];
            String value = keyValues[1];
            if (value.contains("(") && value.contains(")") && value.indexOf("(") < value.indexOf(")")) {
                String objType = value.substring(value.indexOf("(") + 1, value.indexOf(")")).toLowerCase();
                String objValue = value.substring(value.indexOf(")") + 1);
                if (typeMappings.containsKey(objType)) {
                    mMap.put(key, typeMappings.get(objType).convert(objValue));
                } else {
                    throw new UnsupportedOperationException("Error occurred in '" + param + "' -------------------- Because '" + objValue + "' Can not convert to Type '(" + objType + ")'");
                }
            } else {
                mMap.put(key, value);
            }
        }
        return intent;
    }

    public static void main(String[] args) {
        String value = "(int) 25";
        String objType = value.substring(value.indexOf("(") + 1, value.indexOf(")"));
        String objValue = value.substring(value.indexOf(")") + 1).trim();
        System.out.println(objType + ", " + objValue);

        System.out.println(int.class);
    }
}
