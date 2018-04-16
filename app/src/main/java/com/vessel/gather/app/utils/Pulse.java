package com.vessel.gather.app.utils;

/**
 * 脉冲产生器
 */
public class Pulse implements Runnable {

    private boolean mCanceled = false;
    private final Runnable[] mRuns;
    /**
     * 脉冲时间
     */
    private final long mPulseTime;

    public Pulse(Runnable[] runs) {
        mRuns = runs;
        if (mRuns == null) {
            throw new IllegalArgumentException("runs is null");
        }
        // 默认10s
        mPulseTime = 10000L;
    }

    public Pulse(Runnable[] runs, long pulseTime) {
        mRuns = runs;
        if (mRuns == null) {
            throw new IllegalArgumentException("runs is null");
        }
        mPulseTime = pulseTime;
    }

    @Override
    public void run() {
        if (mRuns.length == 0) {
            cancel();
            return;
        }
        // 第一次启动延迟5秒
        try {
            Thread.sleep(5000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (; !mCanceled; ) {
            for (int i = 0; i < mRuns.length; i++) {
                try {
                    mRuns[i].run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // sleep放到下面，保证至少有一次脉冲
            try {
                Thread.sleep(mPulseTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void cancel() {
        mCanceled = true;
    }
}