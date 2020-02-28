package com.xyvideo.zhujianju.util.utils;

import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class StringUtils {
    //正常对比
    public int findIndex(List<String> strList,String value){
        int index= -1;
        for (String s : strList) {
            index++;
            if(s!=null&&s.equals(value)){
                    break;
                }

        }
       return index;


    }
    //截取前4位字符串对比
    public int findSubInx(List<String> strList,String value){
        int index= -1;
        int k=-1;
        for (String s : strList) {
            index++;
//            System.out.println(s.replaceAll(" ",""));
//            System.out.println(s.trim().substring(0,4));
            if(s!=null&&s.trim().length()>4&&s.substring(0,4).equals(value)){
                k=0;
                break;
            }
        }
        return k<0?-1:index;
    }
}
