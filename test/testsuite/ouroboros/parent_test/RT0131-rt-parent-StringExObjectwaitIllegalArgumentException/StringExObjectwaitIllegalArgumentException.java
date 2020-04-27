/*
 * Copyright (c) [2020] Huawei Technologies Co.,Ltd.All rights reserved.
 *
 * OpenArkCompiler is licensed under the Mulan PSL v1.
 * You can use this software according to the terms and conditions of the Mulan PSL v1.
 * You may obtain a copy of Mulan PSL v1 at:
 *
 *     http://license.coscl.org.cn/MulanPSL
 *
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR
 * FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v1 for more details.
 * -@TestCaseID: StringExObjectwaitIllegalArgumentException.java
 * -@TestCaseName: Exception in String: final void wait(*)
 * -@TestCaseType: Function Test
 * -@RequirementName: 补充重写类的父类方法
 * -@Brief:
 * -#step1: Create 2 private classes to implement Runnable, and call wait (millis), wait (millis, nanos) respectively in run
 * -#step2: Call the run of the use case and execute the start () method of the private class 1.
 * -#step3: Execute the start () method of private class 2
 * -#step4: Wait 1000 ms
 * -#step5: Confirm that all classes throw IllegalArgumentException
 * -@Expect:0\n
 * -@Priority: High
 * -@Source: StringExObjectwaitIllegalArgumentException.java
 * -@ExecuteClass: StringExObjectwaitIllegalArgumentException
 * -@ExecuteArgs:
 */

import java.lang.Thread;

public class StringExObjectwaitIllegalArgumentException {
    static int res = 99;
    private static String mf2 = "this is a test";

    public static void main(String argv[]) {
        System.out.println(new StringExObjectwaitIllegalArgumentException().run());
    }

    private class StringExObjectwaitIllegalArgumentException21 implements Runnable {
        // IllegalArgumentException - if the value of timeout is negative.
        // final void wait(long millis)
        long millis = -1;

        /**
         * Thread run fun
         */
        public void run() {
            synchronized (mf2) {
                mf2.notifyAll();
                try {
                    mf2.wait(millis);
                    StringExObjectwaitIllegalArgumentException.res = StringExObjectwaitIllegalArgumentException.res - 15;
                } catch (InterruptedException e1) {
                    StringExObjectwaitIllegalArgumentException.res = StringExObjectwaitIllegalArgumentException.res - 20;
                } catch (IllegalMonitorStateException e2) {
                    StringExObjectwaitIllegalArgumentException.res = StringExObjectwaitIllegalArgumentException.res - 10;
                } catch (IllegalArgumentException e3) {
                    StringExObjectwaitIllegalArgumentException.res = StringExObjectwaitIllegalArgumentException.res - 5;
                }
            }
        }
    }

    private class StringExObjectwaitIllegalArgumentException31 implements Runnable {
        // IllegalArgumentException - if the value of timeout is negative or the value of nanos is not in the range 0-999999.
        //
        // final void wait(long millis, int nanos)
        long millis = -2;
        int nanos = 10;

        /**
         * Thread run fun
         */
        public void run() {
            synchronized (mf2) {
                mf2.notifyAll();
                try {
                    mf2.wait(millis, nanos);
                    StringExObjectwaitIllegalArgumentException.res = StringExObjectwaitIllegalArgumentException.res - 15;
                } catch (InterruptedException e1) {
                    StringExObjectwaitIllegalArgumentException.res = StringExObjectwaitIllegalArgumentException.res - 20;
                } catch (IllegalMonitorStateException e2) {
                    StringExObjectwaitIllegalArgumentException.res = StringExObjectwaitIllegalArgumentException.res - 10;
                } catch (IllegalArgumentException e3) {
                    StringExObjectwaitIllegalArgumentException.res = StringExObjectwaitIllegalArgumentException.res - 5;
                }
            }
        }
    }

    /**
     * sleep fun
     *
     * @param slpnum wait time
     */
    public void sleep(int slpnum) {
        try {
            Thread.sleep(slpnum);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * main test fun
     *
     * @return status code
     */
    public int run() {
        int result = 2; /*STATUS_FAILED*/
        // final void wait(long millis)
        Thread t3 = new Thread(new StringExObjectwaitIllegalArgumentException21());
        // final void wait(long millis, int nanos)
        Thread t5 = new Thread(new StringExObjectwaitIllegalArgumentException31());
        t3.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println(t.getName() + " : " + e.getMessage());
            }
        });
        t5.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println(t.getName() + " : " + e.getMessage());
            }
        });
        t3.start();
        t5.start();
        sleep(1000);
        if (result == 2 && StringExObjectwaitIllegalArgumentException.res == 89) {
            result = 0;
        }

        return result;
    }
}


// EXEC:%maple  %f %build_option -o %n.so
// EXEC:%run %n.so %n %run_option | compare %f
// ASSERT: scan 0\n