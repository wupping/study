package com.wuping;

/**
 * Created by wupingping on 16/2/20.
 */
public class JVMTest {
    private int count = 0;

    public void recursion() {
        count++;
        recursion();
    }

    public void testStack() {
        try {

            recursion();
        } catch (Throwable e) {
            System.out.println("deep of stack is " + count);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        JVMTest test = new JVMTest();
        test.testStack();
    }
}
