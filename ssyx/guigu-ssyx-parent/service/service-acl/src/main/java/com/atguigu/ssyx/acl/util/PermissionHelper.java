package com.atguigu.ssyx.acl.util;

import com.atguigu.ssyx.model.acl.Permission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @BelongsProject: guigu-ssyx-parent
 * @BelongsPackage: com.atguigu.ssyx.acl.util
 * @Author: GuoXiaofeng
 * @CreateTime: 2025-07-21  21:13
 * @Description: TODO
 * @Version: 1.0
 */
public class PermissionHelper {

    public static List<Permission> buildPermission(List<Permission> allList) {
        // 创建最终数据封装List集合
        List<Permission> trees = new ArrayList<>();
        // 遍历所有菜单list集合，得到第一层数据，pid = 0
        for (Permission permission : allList) {
            if (permission.getPid() == 0) {
                permission.setLevel(1);
                trees.add(findChildren(permission, allList));
            }
        }
        return trees;
    }

    private static Permission findChildren(Permission permission, List<Permission> allList) {
        permission.setChildren(new ArrayList<Permission>());
        for (Permission pi : allList) {
            if (pi.getPid().longValue() == permission.getId().longValue()) {
                int level = permission.getLevel() + 1;
                pi.setLevel(level);
                permission.getChildren().add(findChildren(pi, allList));
            }
        }
        return permission;
    }

}
