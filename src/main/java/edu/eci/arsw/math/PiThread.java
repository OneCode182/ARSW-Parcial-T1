package edu.eci.arsw.math;

public class PiThread extends Thread {


    public static int digitsPerSum = 8;
    public static double epsilon = 1e-17;
    private int startNum;
    private int count;
    public static byte[] digits;

    public static Object locked = new Object();


    public PiThread(int startNum, int count) {
        super();
        digits = new byte[count];
        this.startNum = startNum;
        this.count = count;
    }

    public byte[] getDigits() {
        return digits;
    }

    @Override
    public void run() {

        for (int i = 0; i < count; i++) {
            synchronized (locked) {
                try {
                    locked.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                double sum = 0;
                if (i % PiDigits.DigitsPerSum == 0) {
                    sum = 4 * PiDigits.sum(1, startNum)
                            - 2 * PiDigits.sum(4, startNum)
                            - PiDigits.sum(5, startNum)
                            - PiDigits.sum(6, startNum);

                    startNum += PiDigits.DigitsPerSum;
                }

                sum = 16 * (sum - Math.floor(sum));
                Main.digits[i] = (byte) sum;
            }


        }


    }
}
