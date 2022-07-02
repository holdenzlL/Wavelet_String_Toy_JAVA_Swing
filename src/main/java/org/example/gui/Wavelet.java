package org.example.gui;

import lombok.Getter;
import lombok.ToString;
import org.example.gui.pojo.GridsLinkedList;

import java.util.Arrays;

/**
 * The scaling and detail will be calculated automatically when invoking the constructor.
 * Intermediate steps will be stored in to GridLinkedList to be passed to MyPanel to display.
 * &#064;Auther  Zhilun LI
 * &#064;Date 02.07.2022
 * &#064;Version  Prototype
 */
@ToString
@Getter
public class Wavelet{
    GridsLinkedList linkedList;
//    GridsArray array;
    double[] scaling;
    double[] detail;

    /**
     * Perform decomposition on the input sig, the result will be stored in the corresponding fields.
     * @param sig
     */
    public Wavelet(double[] sig){
        super();

        final double K = (Math.sqrt(3)-1)/Math.sqrt(2);
        // the index of the final element
        final int N = sig.length;

        scaling = new double[N/2];
        detail = new double[N/2];

//        array = new GridsArray(N/2+1,N);
        linkedList = new GridsLinkedList(N);

        // s(1)=sig(3)+sqrt(3)*sig(4);
        scaling[0]=sig[2]+Math.sqrt(3)*sig[3];
        linkedList.addEntry();
        linkedList.addressSigElement(new int[]{2,3});
        linkedList.addressInterScalingsElement(0);
        linkedList.storeData_2inter(sig,detail,scaling);

        // d(1)=sig(4)-sqrt(3)/4*s(1)-(sqrt(3)-2)/4*(sig(1)+sqrt(3)*sig(2));
        detail[0]=sig[3]-Math.sqrt(3)/4*scaling[0]-(Math.sqrt(3)-2)/4*(sig[0]+Math.sqrt(3)*sig[1]);
        linkedList.addEntry();
        linkedList.addressSigElement(new int[]{0,1,3});
        linkedList.addressInterScalingsElement(0);
        linkedList.addressInterDetailsElement(0);
        linkedList.storeData_2inter(sig,detail,scaling);

//        linkedList.addEntry();
//        linkedList.addressInterDetailsElement(0);
//        linkedList.addressDetailsElement(0);
//        linkedList.storeData_2inter(sig,detail,scaling);

//        array.getSigs()[0].getDataGrids()[2].setAddressed(true);
//        array.getSigs()[0].getDataGrids()[3].setAddressed(true);
//        array.getScalings()[0].getDataGrids()[0].setAddressed(true);
//        array.getDetails()[0].getDataGrids()[0].setAddressed(true);
//        for (int i = 0; i < N; i++) {
//            array.getSigs()[0].getDataGrids()[i].setData(sig[i]);
//            if(i<N/2)
//            {
//                array.getScalings()[0].getDataGrids()[i].setData(scaling[i]);
//                array.getDetails()[0].getDataGrids()[i].setData(detail[i]);
//            }
//        }

        // for n=1:N/2-2
        for (int n = 0; n < N/2-2; n++) {
            //s(n+1)=sig(2*(n+1)-1+2)+sqrt(3)*sig(2*(n+1)+2);
            scaling[n+1]=sig[2*n+4]+Math.sqrt(3)*sig[2*n+5];
            linkedList.addEntry();
            linkedList.addressSigElement(new int[]{2*n+4,2*n+5});
            linkedList.addressInterScalingsElement(n+1);
            linkedList.storeData_2inter(sig,detail,scaling);

            //d(n+1)=sig(2*(n+1)+2)-sqrt(3)/4*s(n+1)-(sqrt(3)-2)/4*s(n);
            detail[n+1]=sig[2*n+5]-Math.sqrt(3)/4*scaling[n+1]-(Math.sqrt(3)-2)/4*scaling[n];
            linkedList.addEntry();
            linkedList.addressSigElement(2*n+5);
            linkedList.addressInterScalingsElement(new int[]{n,n+1});
            linkedList.addressInterDetailsElement(n+1);
            linkedList.storeData_2inter(sig,detail,scaling);

            //s(n)=s(n)-d(n+1);
            scaling[n]-=detail[n+1];
            linkedList.addEntry();
            linkedList.addressInterDetailsElement(n+1);
            linkedList.addressInterScalingsElement(n);
            linkedList.storeData_2inter(sig,detail,scaling);


            //s(n)=s(n)*K;
            scaling[n]*=K;
            linkedList.addEntry();
            linkedList.addressInterScalingsElement(n);
            linkedList.addressScalingsElement(n);
            linkedList.storeData_toFinal_scaling(sig,scaling,n);

            //d(n)=d(n)/K;
            detail[n]/=K;
            linkedList.addEntry();
            linkedList.addressInterDetailsElement(n);
            linkedList.addressDetailsElement(n);
            linkedList.storeData_toFinal_detail(sig,detail,n);

        }

        //s(N/2)=sig(1)+sqrt(3)*sig(2);
        scaling[N/2-1]=sig[0]+Math.sqrt(3)*sig[1];
        linkedList.addEntry();
        linkedList.addressSigElement(new int[]{0,1});
        linkedList.addressInterScalingsElement(N/2-1);
        linkedList.storeData_2inter(sig,detail,scaling);

        //d(N/2)=sig(2)-sqrt(3)/4*s(N/2)-(sqrt(3)-2)/4*s(N/2-1);
        detail[N/2-1]=sig[1]-Math.sqrt(3)/4*scaling[N/2-1]-(Math.sqrt(3)-2)/4*scaling[N/2-2];
        linkedList.addEntry();
        linkedList.addressSigElement(1);
        linkedList.addressInterScalingsElement(new int[]{N/2-2,N/2-1});
        linkedList.addressInterDetailsElement(N/2-1);
        linkedList.storeData_2inter(sig,detail,scaling);

        //s(N/2-1)=s(N/2-1)-d(N/2);
        scaling[N/2-2]-=detail[N/2-1];
        linkedList.addEntry();
        linkedList.addressInterDetailsElement(N/2-1);
        linkedList.addressInterScalingsElement(N/2-2);
        linkedList.storeData_2inter(sig,detail,scaling);

        //s(N/2-1)=s(N/2-1)*K;
        scaling[N/2-2]*=K;
        linkedList.addEntry();
        linkedList.addressInterScalingsElement(N/2-2);
        linkedList.addressScalingsElement(N/2-2);
        linkedList.storeData_toFinal_scaling(sig,scaling,N/2-2);

        //d(N/2-1)=d(N/2-1)/K;
        detail[N/2-2]/=K;
        linkedList.addEntry();
        linkedList.addressInterDetailsElement(N/2-2);
        linkedList.addressDetailsElement(N/2-2);
        linkedList.storeData_toFinal_detail(sig,detail,N/2-2);

//        array.getSigs()[N/2-1].getDataGrids()[0].setAddressed(true);
//        array.getSigs()[N/2-1].getDataGrids()[1].setAddressed(true);
//        array.getScalings()[N/2-1].getDataGrids()[N/2-2].setAddressed(true);
//        array.getDetails()[N/2-1].getDataGrids()[N/2-2].setAddressed(true);
//        for (int i = 0; i < N; i++) {
//            array.getSigs()[N/2-1].getDataGrids()[i].setData(sig[i]);
//            if(i<N/2)
//            {
//                array.getScalings()[N/2-1].getDataGrids()[i].setData(scaling[i]);
//                array.getDetails()[N/2-1].getDataGrids()[i].setData(detail[i]);
//            }
//        }

        //s(N/2)=s(N/2)-d(1);
        scaling[N/2-1]-=detail[0];
        linkedList.addEntry();
        linkedList.addressInterDetailsElement(0);
        linkedList.addressInterScalingsElement(N/2-1);
        linkedList.storeData_2inter(sig,detail,scaling);

        //s(N/2)=s(N/2)*K;
        scaling[N/2-1]*=K;
        linkedList.addEntry();
        linkedList.addressInterScalingsElement(N/2-1);
        linkedList.addressScalingsElement(N/2-1);
        linkedList.storeData_toFinal_scaling(sig,scaling,N/2-1);

        //d(N/2)=d(N/2)/K;
        detail[N/2-1]/=K;
        linkedList.addEntry();
        linkedList.addressInterDetailsElement(N/2-1);
        linkedList.addressDetailsElement(N/2-1);
        linkedList.storeData_toFinal_detail(sig,detail,N/2-1);

//        array.getScalings()[N/2].getDataGrids()[N/2-1].setAddressed(true);
//        array.getDetails()[N/2].getDataGrids()[N/2-1].setAddressed(true);
//        for (int i = 0; i < N; i++) {
//            array.getSigs()[N/2].getDataGrids()[i].setData(sig[i]);
//            if(i<N/2)
//            {
//                array.getScalings()[N/2].getDataGrids()[i].setData(scaling[i]);
//                array.getDetails()[N/2].getDataGrids()[i].setData(detail[i]);
//            }
//        }
    }

    /**
     * Perform reconstruction
     * @param scaling
     * @param detail
     * @return
     */
    public static double[] reconstruction(double[] scaling, double[] detail)
    {
        final int N = scaling.length * 2;
        final double K = (Math.sqrt(3)-1)/Math.sqrt(2);
        double[] sig = new double[N];

        //d(N/2)=d(N/2)*K;
        detail[N/2-1]*=K;
        //s(N/2)=s(N/2)/K;
        scaling[N/2-1]/=K;
        //s(N/2)=s(N/2)+d(1);
        scaling[N/2-1]+=detail[0];

        //s(N/2-1)=s(N/2-1)*K;
        detail[N/2-2]*=K;
        //s(N/2-1)=s(N/2-1)/K;
        scaling[N/2-2]/=K;
        //s(N/2-1)=s(N/2-1)+d(N/2);
        scaling[N/2-2]+=detail[N/2-1];

        //sig(2)=d(N/2) + sqrt(3)/4*s(N/2) + (sqrt(3)-2)/4*s(N/2-1);
        sig[1]=detail[N/2-1]+Math.sqrt(3)/4*scaling[N/2-1]+(Math.sqrt(3)-2)/4*scaling[N/2-2];
        //sig(1)=s(N/2) - sqrt(3)*sig(2);
        sig[0]=scaling[N/2-1]-Math.sqrt(3)*sig[1];

        System.out.println(Arrays.toString(scaling));

        for (int n = N/2-3; n >=0 ; n--) {
            //d(n) = d(n)*K;
            detail[n]*=K;
            //s(n) = s(n)/K;
            scaling[n]/=K;
            //s(n) = s(n) + d(n+1);
            scaling[n]+=detail[n+1];

            //sig(2*(n+1)+2) = d(n+1) + sqrt(3)/4*s(n+1) + (sqrt(3)-2)/4*s(n);
            sig[2*n+5]=detail[n+1]+Math.sqrt(3)/4*scaling[n+1]+(Math.sqrt(3)-2)/4*scaling[n];
            //sig(2*(n+1)-1+2) = s(n+1) - sqrt(3)*sig(2*(n+1)+2);
            sig[2*n+4]=scaling[n+1]-Math.sqrt(3)*sig[2*n+5];
        }

        //sig(4) = d(1)+sqrt(3)/4*s(1) + (sqrt(3)-2)/4*(sig(1)+sqrt(3)*sig(2));
        sig[3]=detail[0]+Math.sqrt(3)/4*scaling[0]+(Math.sqrt(3)-2)/4*(sig[0]+Math.sqrt(3)*sig[1]);
        //sig(3) = s(1) - sqrt(3)*sig(4);
        sig[2]=scaling[0]-Math.sqrt(3)*sig[3];

        return sig;
    }
}
