package com.c.refactoring.menuexamples;

import java.util.Arrays;
import java.util.List;

public class MenuAccess {

    public void setAuthorizationsInEachMenus(
            List<MenuItem> menuItemsList, Role[] roles) {

        if (roles == null) {
            return;
        }

        menuItemsList.forEach(menuItem -> setAccessForSingleMenu(menuItem, roles));
    }

    private static void setAccessForSingleMenu(MenuItem menuItem, Role[] roles) {
        if (isWrite(menuItem, roles)) {
            menuItem.setAccess(Constants.WRITE);
            menuItem.setVisible(true);
            return;
        }

        if (isRead(menuItem, roles)) {
            menuItem.setAccess(Constants.READ);
            menuItem.setVisible(true);
        }
    }

    private static boolean isWrite(MenuItem menuItem, Role[] roles) {
        return doesUserHaveRole(menuItem.getWriteAccessRole(), roles);
    }

    private static boolean isRead(MenuItem menuItem, Role[] roles) {
        return doesUserHaveRole(menuItem.getReadAccessRole(), roles);
    }

    private static boolean doesUserHaveRole(String accessRole, Role[] roles) {
        return Arrays.stream(roles).anyMatch(role -> role.getName().equals(accessRole));
    }

}
