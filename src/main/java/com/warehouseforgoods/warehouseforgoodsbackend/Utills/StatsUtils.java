package com.warehouseforgoods.warehouseforgoodsbackend.Utills;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StatsUtils {
    public static Map<String, Integer> sortMapByMonths(Map<String, Integer> stats){
        List<String> months = List.of("JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER");
        Map<String, Integer> sortedStats = new LinkedHashMap<>();
        months.forEach(month -> {
            if(stats.containsKey(month)){
                sortedStats.put(month, stats.get(month));
            }
        });
        return sortedStats;
    }

    public static Map<String, Double> sortDoubleMapByMonths(Map<String, Double> stats){
        List<String> months = List.of("JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER");
        Map<String, Double> sortedStats = new LinkedHashMap<>();
        months.forEach(month -> {
            if(stats.containsKey(month)){
                sortedStats.put(month, stats.get(month));
            }
        });
        return sortedStats;
    }
}
