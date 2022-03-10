package com.monster.blog.util;

import com.fasterxml.jackson.core.TreeNode;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author liz
 * @Description 时间工具类
 * @date 2020/6/8-13:58
 */
@Slf4j
public class TimeUtil {

    public static LocalDateTime getCurrentTimeFormat() {
        // 2019-11-15 22:03:40
        String str = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return LocalDateTime.parse(str, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static Date getCurrentDateTime() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info(sdf.format(new Date()));
        return sdf.parse(sdf.format(new Date()));
    }

    public static List<Integer> getTheResult(Integer result) {
        List<Integer> resultArray = new ArrayList<>();
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] + array[j] == result) {
                    System.out.println(i + ":" + array[i] + "--->" + j + ":" + array[j]);
                    resultArray.add(i);
                    resultArray.add(j);
                }
            }
        }
        return resultArray;
    }

    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]{map.get(target - nums[i]), i};
            }
            map.put(nums[i], i);
        }
        return new int[]{-1, -1};
    }

    public static void main(String[] args) throws Exception {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date = getCurrentDateTime();
//        log.info(sdf.format(date));
//        log.info(date.toString());
//        Set<String> set = new HashSet<>();
//        set.add("15");
//        set.add("10");
//        List<String> list = new ArrayList<>(set);
//        for (String str:list) {
//            System.out.println("从Set中获取的字符串：" + str);
//        }
        List<Integer> array = getTheResult(17);
        int[] nums = {2,7,10,15};
        System.out.println(array);
    }
}
