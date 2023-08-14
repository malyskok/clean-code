package com.c.refactoring.lock;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

public class UserLoginCheckerTest {
    public static final int DEFAULT_LOGIN_ID = 10;
    public static final String DEFAULT_LOGIN_STATUS = "NOT_USED";
    public static final String TEST_USER_1 = "TEST_USER_ID_1";
    public static final String TEST_USER_2 = "TEST_USER_ID_2";
    public static final String TEST_USER_0 = "TEST_USER_ID";
    public static final boolean IS_FIRST_SCREEN_TRUE = true;
    public static final boolean IS_FIRST_SCREEN_FALSE = false;
    UserLoginChecker userLoginChecker = new UserLoginChecker();

    @Test
    public void testisUserAllowedToLogin_DifferentUserTriesImmediatelyAfter() {
        List<Object[]> existingLocks = createAccessList(TEST_USER_1, getDateNow());
        User userToLogIn = new User(TEST_USER_2);

        Lock lock = userLoginChecker.isUserAllowedToLogin(DEFAULT_LOGIN_ID, DEFAULT_LOGIN_STATUS,
                IS_FIRST_SCREEN_TRUE, userToLogIn, existingLocks);

        assertReadAccess(lock);
    }

    @Test
    public void testisUserAllowedToLogin_SameUserReturnsToFirstScreen() {
        List<Object[]> existingLocks = createAccessList(TEST_USER_0, getDateNow());
        User userToLogIn = new User(TEST_USER_0);

        Lock lock = userLoginChecker.isUserAllowedToLogin(DEFAULT_LOGIN_ID, DEFAULT_LOGIN_STATUS,
                IS_FIRST_SCREEN_TRUE, userToLogIn, existingLocks);

        assertWriteAccess(lock);
    }

    @Test
    public void testisUserAllowedToLogin_SameUserReturnsToSecondScreen() {
        List<Object[]> existingLocks = createAccessList(TEST_USER_0, getDateNow());
        User userToLogIn = new User(TEST_USER_0);

        Lock lock = userLoginChecker.isUserAllowedToLogin(DEFAULT_LOGIN_ID, DEFAULT_LOGIN_STATUS,
                IS_FIRST_SCREEN_FALSE, userToLogIn, existingLocks);

        assertWriteAccess(lock);
    }

    @Test
    public void testisUserAllowedToLogin_User2TriesToLoginToFirstScreen3hoursAfterUser1() {
        Object[] access = new Object[] {TEST_USER_1, threeHoursBefore() };
        Lock lock = userLoginChecker.isUserAllowedToLogin(DEFAULT_LOGIN_ID, DEFAULT_LOGIN_STATUS, true, new User(
                TEST_USER_2), Arrays.asList(new Object[][] { access }));
        assertWriteAccess(lock);
    }

    @Test
    public void testisUserAllowedToLogin_User2TriesToLoginToSecondScreen3hoursAfterUser1() {
        Object[] access = new Object[] {TEST_USER_1, threeHoursBefore() };
        Lock lock = userLoginChecker.isUserAllowedToLogin(DEFAULT_LOGIN_ID, DEFAULT_LOGIN_STATUS, false, new User(
                TEST_USER_2), Arrays.asList(new Object[][] { access }));
        assertReadAccess(lock);
    }

    private static void assertReadAccess(Lock lock) {
        assertTrue(lock.isReadAccess());
        assertNotNull(lock.getLockReason());
    }

    private static void assertWriteAccess(Lock lock) {
        assertFalse(lock.isReadAccess());
        assertNull(lock.getLockReason());
    }

    private static List<Object[]> createAccessList(String user, Date date) {
        Object[] access = new Object[] {user, date};
        return Arrays.asList(new Object[][]{access});
    }

    private static Date getDateNow() {
        return new Date();
    }

    public Date threeHoursBefore() {
        Date now = getDateNow();
        return new Date(now.getTime() - 3 * 60 * 60 * 1000);
    }

}
