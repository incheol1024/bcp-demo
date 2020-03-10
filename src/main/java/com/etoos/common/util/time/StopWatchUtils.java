package com.etoos.common.util.time;

/**
 * <pre>
 * com.etoos.common.util.time
 *   |_ StopWatchAverage.java
 * </pre>
 *
 * 1. 업무명         :
 * 2. 단위 업무명  :
 * @Class     : StopWatchAverage
 * @Author    : JUNG YEON HO
 * @Since     : 2020. 3. 6. 오전 11:31:02
 * @Version   : 1.0
 * Copyright (c) ETOOS.
 * -------------------------------------------------------------------
 * Modification Information
 * -------------------------------------------------------------------
 * 수정일                                                     수정자                                         수정 내용
 * -------------------------------------------------------------------
 * 2020. 3. 6.                    JUNG YEON HO              [ETOOS] 최초 생성
 */
public class StopWatchUtils {

    long startTime;
    long elapsedTime = 0;
    double totalElapsedTime;
    long runCount = 0;
    String currentName;
    boolean threadFlag = false;

    /**
     * 특별히 이름을 지정하지 않은 StopWatchAverage 객체를 생성하고, Timer를 시작한다.
     */
    public StopWatchUtils() {
        currentName = "";
        startTime = System.nanoTime();
    }

    /**
     * Thread를 사용하는지 여부를 지정하는 생성자
     *
     * @param threadFlag
     *            Thread 사용여부
     */
    public StopWatchUtils(boolean threadFlag) {
        changeMessage("", threadFlag, true);
    }

    /**
     * 메시지만을 지정하는 생성자
     *
     * @param message
     *            추가로 명시할 메세지
     */
    public StopWatchUtils(String message) {
        changeMessage(message, false, true);
    }

    /**
     * 메시지와 Thread 사용여부를 함께 지정하는 생성자
     *
     * @param message
     *            추가로 명시할 메세지
     * @param threadFlag
     *            Thread 사용여부
     */
    public StopWatchUtils(String message, boolean threadFlag) {
        changeMessage(message, threadFlag, true);
    }

    /**
     * StopWatch 의 시간 데이터를 초기화한다.
     */
    public void reset() {
        startTime = System.nanoTime();
        elapsedTime = 0;
        totalElapsedTime = 0;
        runCount = 0;
    }

    /**
     * 시간측정을 시작한다.
     */
    public void start() {
        startTime = System.nanoTime();
        elapsedTime = 0;
    }

    /**
     * StopWatch를 멈추고 응답시간결과를 ArrarList 에 담는다.
     */
    public void stop() {
        elapsedTime = System.nanoTime() - startTime;
        totalElapsedTime += elapsedTime;
        runCount++;
    }

    /**
     * 메세지를 지정한다
     *
     * @param message
     *            추가로 명시할 메세지
     * @param threadFlag
     *            Thread 사용여부
     * @param resetFlag
     *            객체 생성시 StopWatch 리셋여부
     */
    private void changeMessage(String message, boolean threadFlag, boolean resetFlag) {
        String threadName = "";
        this.threadFlag = threadFlag;
        if (threadFlag) {
            threadName = "ThreadName = " + Thread.currentThread().getName();
        }

        currentName = "[" + message + "-" + threadName + "]";
        if (resetFlag) {
            reset();
        }
    }

    /**
     * StopWatch를 멈추고 마지막에 (혹은 현재까지) 수행된 시간을 리턴한다.
     *
     * @return 마지막에 수행된 밀리초
     */
    public double getElapsedMS() {
        if (elapsedTime == 0) {
            stop();
        }
        return elapsedTime / 1000000.0;
    }

    /**
     * StopWatch를 멈추고 마지막에 (혹은 현재까지) 수행된 시간을 리턴한다.
     *
     * @return 마지막에 수행된 나노초
     */
    public double getElapsedNano() {
        if (elapsedTime == 0) {
            stop();
        }
        return elapsedTime;
    }

    /**
     * 현재까지 수집된 횟수, 전체수행 시간의 합, 평균 수행시간을 밀리초 단위로 리턴해준다.
     *
     * @see java.lang.Object#toString()
     * @return 수행횟수, 전체수행시간, 평균수행시간
     */
    @Override
    public String toString() {
        if (elapsedTime == 0) {
            stop();
        }

        double elapsedAverage = totalElapsedTime / runCount;
        return currentName + "Run Count : " + runCount + ", Total : " + totalElapsedTime / 1000000.0 + " ms" + ", Average : " + elapsedAverage / 1000000.0 + " ms";

    }

}
