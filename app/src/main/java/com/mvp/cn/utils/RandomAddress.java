package com.mvp.cn.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author iqiao
 * @date 2020-03-15 08:40
 * @desc
 */
public class RandomAddress {


    public static void main(String[] args) {
        List<String> xiaoqu = generateAddress("xiaoqu.txt");
        List<String> lu = generateAddress("lu.txt");
        List<String> inputs = new ArrayList<>();
        System.out.println("请输入本次获取条数以及地址前缀用空格隔开，#号结束(输入示例: 10 北京市 #)：");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String s = scanner.next();
            inputs.add(s);
//            System.out.println("read: " + s);
            if (s.equals("#")) {
               break;
            }
        }
        scanner.close();
        int count = Integer.parseInt(inputs.get(0));
        String address = inputs.get(1);
        for (int i = 0; i < count; i++) {
            System.out.println(address + lu.get(generateRandom(lu.size())) + xiaoqu.get(generateRandom(xiaoqu.size())));
        }

}


    private static List<String> generateAddress(String path) {
        List<String> allContent = new ArrayList<>();
        try {
            File file = new File(path);
            if (file == null) {
                System.out.println("file not found");
                return null;
            }
            FileInputStream inputStream = new FileInputStream(file);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "gbk"));
            while (bufferedReader.readLine() != null) {
//                    System.out.println(bufferedReader.readLine());
                allContent.add(bufferedReader.readLine());
            }
            //close
            inputStream.close();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allContent;
    }


    private static int generateRandom(int max1) {
        int min = 1;
        int max = max1;
        return (int) min + (int) (Math.random() * (max - min));
    }

}
