package com.wuping;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;

/**
 * Created by wupingping on 16/2/18.
 */
public class Mutex implements Lock {
    public static void main(String[] args) {
        Map<String,String>  map = new HashMap<String, String>();
        Map<String,String> map2 = new ConcurrentHashMap<String, String>();
    }
    private static class Sync extends AbstractQueuedSynchronizer{

    }

    public void lock() {

    }

    public void lockInterruptibly() throws InterruptedException {

    }

    public boolean tryLock() {
        return false;
    }

    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    public void unlock() {

    }

    public Condition newCondition() {
        return null;
    }
}
