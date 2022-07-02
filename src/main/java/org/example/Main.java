package org.example;

import org.example.gui.MyFrame;

/**
 * This GUI program is created initially to cope with the assignment of the class, Advanced programming in HKA Germany.
 * The idea is inspired by <a href="https://github.com/mossblaser/streaming_wavelet_toy">...</a> amd the class, Efficient video coding in HKA Germany.
 * For tracing the update of this project, please visit <a href="https://github.com/holdenzlL">...</a>
 * &#064;Auther  Zhilun LI
 * &#064;Date 02.07.2022
 * &#064;Version  Prototype
 */
public class Main {
    /**
     * the entrance of the program
     * @param args
     */
    public static void main(String[] args)
    {
//        Wavelet wavelet = new Wavelet(new double[]{7,7,5,8,5,2,1,0});
//        System.out.println(wavelet.getArray().length);
//        System.out.println(Arrays.toString(Wavelet.reconstruction(wavelet.getScaling(),wavelet.getDetail())));
        new MyFrame("Daubechines 4 lifting scheme for decomposition only with shifting [Prototype Version]");
    }
}