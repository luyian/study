package com.it.hello.recursion;

import com.alibaba.fastjson.JSONObject;
import entity.Org;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 获取组织机构树
 */
public class RecursionGetTree {
    private static ArrayList<Org> list = new ArrayList<>();
    private static JSONObject jsonObject = new JSONObject();

    public static void main(String[] args) {
        init();
//        for (Org org : list) {
//            System.out.println(org);
//        }
//        System.out.println(getById(1));

//        List<Org> orgTree = getList(11);
//        String json = JSONObject.toJSONString(orgTree);
//        getTree(1);
        List<Org> allTree = getAllTree(0);
        System.out.println(JSONObject.toJSONString(allTree));
    }

    public static void init() {
        //顶级父组织，parent=null
        for (int i = 1; i < 5; i++) {
            list.add(new Org(i,"省级" + i, 0, null));
        }
        //二级父组织
        for (int j = 1; j < 5; j++) {
            list.add(new Org(10 + j, "市级" + j, j, null));
        }
        //三级
        for (int x = 1; x < 20; x++) {
            list.add(new Org(20 + x, "县级" + x, 10 + x%3+1, null));
        }
    }

    //根据id获取org
    public static Org getById(Integer id) {
        return list.stream().filter(item -> item.getId() == id).findFirst().get();
    }

    //获取子组织结构
    public static  List<Org> getList(Integer id) {
        return list.stream().filter(item -> item.getParent() == id).collect(Collectors.toList());
    }

    //获取所有组织机构信息
    public static List<Org> getAllTree(int id) {
        return recursionGet(id, null);
    }

    public static List<Org> recursionGet(int id, List<Org> list) {
        list = getList(id);
        if (list.size() > 0) {
            for (Org org : list) {
                org.setChild(recursionGet(org.getId(), list));
            }
        }
        return list;
    }

    //获取树-->初步尝试
    public static void getTree(Integer id) {
        List<Org> list = getList(id);
        if (list.size() > 0) {
            Org org = getById(id);
            jsonObject.put(org.getName(), list);
            for (Org org1 : list) {
                getTree(org1.getId());
            }
        }
    }

    //求阶乘
    public static int getFactorial(int a) {
        if (a == 1) {
            return a;
        }
        return a*getFactorial(a-1);
    }
}
