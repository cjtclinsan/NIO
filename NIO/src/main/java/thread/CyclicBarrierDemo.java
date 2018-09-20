package thread;

import java.util.concurrent.*;

/**
 * Created by Administrator on 2018/9/15.
 */
public class CyclicBarrierDemo {
    private static final ThreadPoolExecutor threadPool =
            new ThreadPoolExecutor(4,10,60, TimeUnit.SECONDS,
                    new LinkedBlockingQueue<Runnable>());
    //当拦截线程数达到4时，便优先执行barrierAction，然后再执行被拦截的线程
    private static final CyclicBarrier cb = new CyclicBarrier(4, new Runnable() {
        @Override
        public void run() {
            System.out.println("寝室四兄弟一起出发去球场");
        }
    });

    private static class  GoThread extends Thread{
        private final String name;

        public GoThread(String name) {
            this.name = name;
        }

        @Override
        public void run(){
            System.out.println(name+" 开始从宿舍出发");

            try {
                Thread.sleep(2000);
                cb.await();       //拦截线程
                System.out.println(name+" 到达楼底下");
                Thread.sleep(2000);
                System.out.println(name+" 到达操场");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        String[] str= {"李明","王强","刘凯","赵杰"};
        String[] str1= {"王二","洪光","雷兵","赵三"};

        for ( int i = 0; i < 4; i++){
            threadPool.execute(new GoThread(str[i]));
        }

        try {
            Thread.sleep(3000);
            System.out.println("四个人一起到达球场，现在开始打球");
            System.out.println("现在对CyclicBarrier进行复用.....");
            System.out.println("又来了一拨人，看看愿不愿意一起打：");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for ( int i = 0; i < 4; i++){
            threadPool.execute(new GoThread(str1[i]));
        }

        try {
            Thread.sleep(4000);
            System.out.println("四个人一起到达球场，表示愿意一起打球，现在八个人开始打球");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}