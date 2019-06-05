package com.test;

import java.util.Date;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 各种队列
 */
public class Block {

    /**
     * 延时队列
     */
    public static void delay(){
        DelayQueue<Data> queue = new DelayQueue<>();
        Date now = new Date();
        long l = now.getTime() + 10000L;
        Date date = new Date();
        date.setTime(l);
        Data data = new Data(date);
        queue.add(data);
        while (true) {
            Data poll = queue.poll();
            if(poll!=null){
                System.out.println(poll.value);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    static class Data implements Delayed{

        private Date time;
        private String value = "123";

        public Data(Date time){
            this.time = time;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            Date now = new Date();
            long diff = time.getTime() - now.getTime();
            return unit.convert(diff, unit.SECONDS);// 放入过期时间
        }

        @Override
        public int compareTo(Delayed o) {
            long result = this.getDelay(TimeUnit.NANOSECONDS)
                    - o.getDelay(TimeUnit.NANOSECONDS);
            if (result < 0) {
                return -1;
            } else if (result > 0) {
                return 1;
            } else {
                return 0;
            }
        }

    }



    public static void main(String[] args) {
        delay();
    }

}
