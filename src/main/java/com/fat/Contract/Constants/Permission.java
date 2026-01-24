package com.fat.Contract.Constants;

import java.util.HashMap;
import java.util.Map;

public class Permission {
    private static final Map<String, Integer> permissions  = new HashMap<>();
    static {
        int fullPermission = Action.CREATE | Action.READ | Action.UPDATE | Action.DELETE;  // 15
        permissions.put(Function.CATEGORY, fullPermission);
        permissions.put(Function.CUSTOMER, fullPermission);
        permissions.put(Function.STAFF, fullPermission);
        permissions.put(Function.ROLE, fullPermission);
        permissions.put(Function.PROMOTION, fullPermission);
        permissions.put(Function.IMPORT, fullPermission);
        permissions.put(Function.PRODUCT, fullPermission);
        permissions.put(Function.SUPPLIER, fullPermission);
        permissions.put(Function.RECEIPT, Action.READ);
        permissions.put(Function.SELL, Action.READ | Action.CREATE);
        permissions.put(Function.STATISTIC, Action.READ);
    }
    public static int getPermission(String functionName) {
        return permissions.getOrDefault(functionName, Action.CREATE | Action.READ | Action.UPDATE | Action.DELETE);
    }

    public static Map<String, Integer> getPermissions() {
        return permissions;
    }
}
