package com.musicfire.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Test {


    class Role{

    }


    class Permission{


        private String premissionCode;

        public String getPremissionCode(){
            return this.premissionCode;
        }

    }

    public static void main(String[] args) {


        //这个是以角色ID为键，权限码为值的HashMap
        Map<Long, List<String>> roleMap = new HashMap<>();


        //这一步执行把所有MAP的值取出来,装进一个集合里面然后再去重拿到权限码
        List<String> permissionCodeList = roleMap.entrySet().stream()
                .flatMap(map -> map.getValue().stream())
                .distinct().collect(Collectors.toList());


        //这个是以角色ID为键，权限码实体为值的HashMap
        Map<Long, List<Permission>> rolePremissionEntityMap = new HashMap<>();


        //这一步执行把所有MAP的值取出来,装进一个集合里面然后再去重拿到权限实体 然后再用getPermissionCode方法拿到权限码并且去重
        List<String> rolePremissionEntityList = rolePremissionEntityMap.entrySet().stream()
                .flatMap(map -> map.getValue().stream())
                .map(Permission::getPremissionCode).collect(Collectors.toList());

    }

}
