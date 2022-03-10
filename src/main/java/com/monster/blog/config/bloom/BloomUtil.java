package com.monster.blog.config.bloom;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.Charsets;

import java.util.*;

/**
 * 布隆过滤器实现
 *
 * @author liz
 * @date 2020/12/16-15:36
 */
@Slf4j
public class BloomUtil {

    private static final int INSERTIONS = 1000000;

    public static void bloomFilter() {
        // 初始化一个存储String数据的布隆过滤器，初始化大小为100W
        BloomFilter<String> bf = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), INSERTIONS, 0.001);

        // 初始化一个存储String数据的set容器，初始化大小为100W
        Set<String> sets = new HashSet<>(INSERTIONS);
        // 初始化一个存储String数据的list容器，初始化大小为100W
        List<String> lists = new ArrayList<>(INSERTIONS);

        // 向三个容器存入100W个随机且唯一的UUID，34M多
        for (int i = 0; i < INSERTIONS; i++) {
            String uuid = UUID.randomUUID().toString();
            bf.put(uuid);
            sets.add(uuid);
            lists.add(uuid);
        }

        int wrong = 0;
        int right = 0;
        for (int i = 0; i < 10000; i++) {
            String test = i % 100 == 0 ? lists.get(i / 100) : UUID.randomUUID().toString();
            if (bf.mightContain(test)) {
                if (sets.contains(test)) {
                    right++;
                } else {
                    wrong++;
                }
            }
        }

        log.info("查询正确的个数：{}", right);
        log.info("查询错误的个数：{}", wrong);
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        log.info("执行开始时间：{}", startTime);
        bloomFilter();
        log.info("执行结束时间：{}", System.currentTimeMillis() - startTime);
    }

}
