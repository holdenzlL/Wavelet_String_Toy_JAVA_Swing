package org.example.gui;

import lombok.Getter;
import lombok.ToString;
import org.example.gui.pojo.GridsLinkedList;

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
    private GridsLinkedList linkedList;
//    GridsArray array;
    private double[] scaling;
    private double[] detail;
    private double[] sig;
    private int N;

    /**
     * Perform decomposition on the input sig, the result will be stored in the corresponding fields.
     * @param sig
     */
    public Wavelet(double[] sig){
        super();
        final double K = (Math.sqrt(3)-1)/Math.sqrt(2);
        // the index of the final element
        this.N = sig.length;
        this.sig = new double[N];
        System.arraycopy(sig,0,this.sig,0,N);

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
    }

    /**
     * Perform reconstruction
     * @return
     */
    public void reconstruction()
    {
        final double K = (Math.sqrt(3)-1)/Math.sqrt(2);
        double[] sig = new double[N];

        double[] scalingSaved = new double[N/2];
        double[] detailSaved = new double[N/2];

        for (int i = 0; i < N/2; i++) {
            scalingSaved[i] = scaling[i];
            detailSaved[i] = detail[i];
        }

        //d(N/2)=d(N/2)*K;
        detail[N/2-1]*=K;
        linkedList.addEntry();
        linkedList.addressDetailsElement(N/2-1);
        linkedList.address_RE_InterDetailsElement(N/2-1);
        linkedList.storeData_RE_2inter_detail(this.sig,scalingSaved,detailSaved,sig,scaling,detail,N/2-1);

        //s(N/2)=s(N/2)/K;
        scaling[N/2-1]/=K;
        linkedList.addEntry();
        linkedList.addressScalingsElement(N/2-1);
        linkedList.address_RE_InterScalingsElement(N/2-1);
        linkedList.storeData_RE_2inter_scaling(this.sig,scalingSaved,detailSaved,sig,scaling,detail,N/2-1);

        //s(N/2)=s(N/2)+d(1);
        scaling[N/2-1]+=detail[0];
        linkedList.addEntry();
        linkedList.addressScalingsElement(N/2-1);
        linkedList.addressDetailsElement(0);
        linkedList.address_RE_InterScalingsElement(N/2-1);
        linkedList.storeData_RE_2inter_scaling(this.sig,scalingSaved,detailSaved,sig,scaling,detail,N/2-1);

        //s(N/2-1)=s(N/2-1)*K;
        detail[N/2-2]*=K;
        linkedList.addEntry();
        linkedList.addressDetailsElement(N/2-2);
        linkedList.address_RE_InterDetailsElement(N/2-2);
        linkedList.storeData_RE_2inter_detail(this.sig,scalingSaved,detailSaved,sig,scaling,detail,N/2-2);

        //s(N/2-1)=s(N/2-1)/K;
        scaling[N/2-2]/=K;
        linkedList.addEntry();
        linkedList.addressScalingsElement(N/2-2);
        linkedList.address_RE_InterScalingsElement(N/2-2);
        linkedList.storeData_RE_2inter_scaling(this.sig,scalingSaved,detailSaved,sig,scaling,detail,N/2-2);

        //s(N/2-1)=s(N/2-1)+d(N/2);
        scaling[N/2-2]+=detail[N/2-1];
        linkedList.addEntry();
        linkedList.addressScalingsElement(N/2-2);
        linkedList.addressDetailsElement(N/2-1);
        linkedList.address_RE_InterScalingsElement(N/2-2);
        linkedList.storeData_RE_2inter_scaling(this.sig,scalingSaved,detailSaved,sig,scaling,detail,N/2-2);

        //sig(2)=d(N/2) + sqrt(3)/4*s(N/2) + (sqrt(3)-2)/4*s(N/2-1);
        sig[1]=detail[N/2-1]+Math.sqrt(3)/4*scaling[N/2-1]+(Math.sqrt(3)-2)/4*scaling[N/2-2];
        linkedList.addEntry();
        linkedList.address_RE_SigElement(1);
        linkedList.address_RE_InterScalingsElement(new int[]{N/2-2,N/2-1});
        linkedList.address_RE_InterDetailsElement(N/2-1);
        linkedList.storeData_RE_2sig(this.sig,scalingSaved,detailSaved,sig);

        //sig(1)=s(N/2) - sqrt(3)*sig(2);
        sig[0]=scaling[N/2-1]-Math.sqrt(3)*sig[1];
        linkedList.addEntry();
        linkedList.address_RE_SigElement(new int[]{0,1});
        linkedList.address_RE_InterScalingsElement(N/2-1);
        linkedList.storeData_RE_2sig(this.sig,scalingSaved,detailSaved,sig);

        for (int n = N/2-3; n >=0 ; n--) {
            //d(n) = d(n)*K;
            detail[n]*=K;
            linkedList.addEntry();
            linkedList.addressDetailsElement(n);
            linkedList.address_RE_InterDetailsElement(n);
            linkedList.storeData_RE_2inter_detail(this.sig,scalingSaved,detailSaved,sig,scaling,detail,n);

            //s(n) = s(n)/K;
            scaling[n]/=K;
            linkedList.addEntry();
            linkedList.addressScalingsElement(n);
            linkedList.address_RE_InterScalingsElement(n);
            linkedList.storeData_RE_2inter_scaling(this.sig,scalingSaved,detailSaved,sig,scaling,detail,n);

            //s(n) = s(n) + d(n+1);
            scaling[n]+=detail[n+1];
            linkedList.addEntry();
            linkedList.addressScalingsElement(n);
            linkedList.addressDetailsElement(n+1);
            linkedList.address_RE_InterScalingsElement(n);
            linkedList.storeData_RE_2inter_scaling(this.sig,scalingSaved,detailSaved,sig,scaling,detail,n);

            //sig(2*(n+1)+2) = d(n+1) + sqrt(3)/4*s(n+1) + (sqrt(3)-2)/4*s(n);
            sig[2*n+5]=detail[n+1]+Math.sqrt(3)/4*scaling[n+1]+(Math.sqrt(3)-2)/4*scaling[n];
            linkedList.addEntry();
            linkedList.address_RE_SigElement(2*n+5);
            linkedList.address_RE_InterScalingsElement(new int[]{n,n+1});
            linkedList.address_RE_InterDetailsElement(n+1);
            linkedList.storeData_RE_2sig(this.sig,scalingSaved,detailSaved,sig);

            //sig(2*(n+1)-1+2) = s(n+1) - sqrt(3)*sig(2*(n+1)+2);
            sig[2*n+4]=scaling[n+1]-Math.sqrt(3)*sig[2*n+5];
            linkedList.addEntry();
            linkedList.address_RE_SigElement(new int[]{2*n+4,2*n+5});
            linkedList.address_RE_InterScalingsElement(n+1);
            linkedList.storeData_RE_2sig(this.sig,scalingSaved,detailSaved,sig);
        }

        //sig(4) = d(1)+sqrt(3)/4*s(1) + (sqrt(3)-2)/4*(sig(1)+sqrt(3)*sig(2));
        sig[3]=detail[0]+Math.sqrt(3)/4*scaling[0]+(Math.sqrt(3)-2)/4*(sig[0]+Math.sqrt(3)*sig[1]);
        linkedList.addEntry();
        linkedList.address_RE_SigElement(new int[]{0,1,3});
        linkedList.address_RE_InterDetailsElement(0);
        linkedList.address_RE_InterScalingsElement(0);
        linkedList.storeData_RE_2sig(this.sig,scalingSaved,detailSaved,sig);

        //sig(3) = s(1) - sqrt(3)*sig(4);
        sig[2]=scaling[0]-Math.sqrt(3)*sig[3];
        linkedList.addEntry();
        linkedList.address_RE_SigElement(new int[]{2,3});
        linkedList.address_RE_InterScalingsElement(0);
        linkedList.storeData_RE_2sig(this.sig,scalingSaved,detailSaved,sig);
    }
}
