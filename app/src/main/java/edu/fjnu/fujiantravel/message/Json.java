package edu.fjnu.fujiantravel.message;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/22 0022.
 */
public class Json {
    public static Gson gson = new Gson();

    public static String ObjecttoJson(Object object) {
        String jsonstr = null;
        jsonstr = gson.toJson(object);
        return jsonstr;
    }

    public static <T> Object JsontoObject(String JsonStr, Class<T> c) {
        Object object = null;
        object = gson.fromJson(JsonStr, c);
        return object;
    }

    public static <T> String ListtoJson(List<T> list) {
        String jsonstr = null;
        jsonstr = gson.toJson(list);
        return jsonstr;
    }

    public static <T> List<T> JsontoList(String JsonStr, List<T> l) {
        List<T> list = null;
        list = gson.fromJson(JsonStr, new TypeToken<List<T>>() {
        }.getType());
        return list;
    }

    public static <A, B> String MaptoJson(Map<A, B> map) {
        String jsonstr = null;
        jsonstr = gson.toJson(map);
        return jsonstr;
    }

    public static <A, B> Map<A, B> JsontoMap(String JsonStr, Map<A, B> m) {
        Map<A, B> map = null;
        map = gson.fromJson(JsonStr, new TypeToken<Map<A, B>>() {
        }.getType());
        return map;
    }
}
