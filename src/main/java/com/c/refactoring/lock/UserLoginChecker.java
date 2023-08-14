package com.c.refactoring.lock;

import java.util.Date;
import java.util.List;

public class UserLoginChecker {

    /**
     * {@inheritDoc}.
     */
    public Lock isUserAllowedToLogin(long id, String status,
                                     boolean isFirstScreen, User userToLog, List existingLocks) {

        if (existingLocks.isEmpty() || existingLocks.get(0) == null) {
            return createWriteLock();
        }

        Object[] existingLock = (Object[]) existingLocks.get(0);
        String existingLockUserId = (String) existingLock[0];
        Date existingLockTimestamp = (Date) existingLock[1];

        if (existingLockUserId == null) {
            return createWriteLock();
        }

        if (isSameUser(userToLog, existingLockUserId)) {
            return createWriteLock();
        }

        if (isFirstScreen && isLockTimeAboveMax(existingLockTimestamp)) {
            return createWriteLock();
        }

        return createReadLock(existingLockUserId);
    }

    private static Lock createWriteLock() {
        Lock lock = new Lock();
        lock.setRead(false);
        return lock;
    }

    private Lock createReadLock(String lockedUserId) {
        Lock lock = new Lock();
        lock.setRead(true);
        lock.setLockReason(getMsgThatScreenIsLocked(lockedUserId));
        return lock;
    }

    private static String getMsgThatScreenIsLocked(String lockedUserId) {
        return Constants.LOCK_TEXT.replaceAll("@@USER@@", lockedUserId);
    }

    private static boolean isSameUser(User userToLog, String lockedUserId) {
        return lockedUserId.equalsIgnoreCase(userToLog.getUserId());
    }

    private static boolean isLockTimeAboveMax(Date lockedTimestamp) {
        Date now = new Date();
        int max = 3600000;
        return now.getTime() - lockedTimestamp.getTime() > max;
    }
}