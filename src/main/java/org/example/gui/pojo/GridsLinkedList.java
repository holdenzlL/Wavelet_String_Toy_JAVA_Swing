package org.example.gui.pojo;

import lombok.Data;

import java.util.LinkedList;
/**
 * As a collection of data, this class passes data between the class Wavelet and the class MyPanel.
 * All data processed by wavelet will be packed and passed to the class MyPanel to display.
 * The data structure is a linkedlist.
 * &#064;Auther  Zhilun LI
 * &#064;Date 02.07.2022
 * &#064;Version  Prototype
 */
@Data
public class GridsLinkedList {
    private int N;  // signal length
    private int index; // recent operated index
    private LinkedList<DataGrids> sigs;
    private LinkedList<DataGrids> interDetails;
    private LinkedList<DataGrids> interScalings;
    private LinkedList<DataGrids> details;
    private LinkedList<DataGrids> scalings;
    LinkedList<DataGrids> re_interDetails;
    LinkedList<DataGrids> re_interScalings;
    LinkedList<DataGrids> re_sigs;

    public GridsLinkedList(int N){
        this.N = N;
        sigs = new LinkedList<>();
        interDetails = new LinkedList<>();
        interScalings = new LinkedList<>();
        details = new LinkedList<>();
        scalings = new LinkedList<>();
        re_interDetails = new LinkedList<>();
        re_interScalings = new LinkedList<>();
        re_sigs = new LinkedList<>();
    }
    public void addEntry(){
        DataGrids sigs_tmp = new DataGrids();
        DataGrids interScalings_tmp = new DataGrids();
        DataGrids interDetails_tmp = new DataGrids();
        DataGrids details_tmp = new DataGrids();
        DataGrids scalings_tmp = new DataGrids();
        DataGrids re_interDetails_tmp = new DataGrids();
        DataGrids re_interScalings_tmp = new DataGrids();
        DataGrids re_sigs_tmp = new DataGrids();

        sigs_tmp.setDataGrids(new DataGrid[N]);
        interDetails_tmp.setDataGrids(new DataGrid[N/2]);
        interScalings_tmp.setDataGrids(new DataGrid[N/2]);
        details_tmp.setDataGrids(new DataGrid[N/2]);
        scalings_tmp.setDataGrids(new DataGrid[N/2]);
        re_interDetails_tmp.setDataGrids(new DataGrid[N/2]);
        re_interScalings_tmp.setDataGrids(new DataGrid[N/2]);
        re_sigs_tmp.setDataGrids(new DataGrid[N]);

        for (int i = 0; i < N; i++) {
            sigs_tmp.getDataGrids()[i] = new DataGrid();
            re_sigs_tmp.getDataGrids()[i] = new DataGrid();
            if(i<N/2)
            {
                interDetails_tmp.getDataGrids()[i] = new DataGrid();
                interScalings_tmp.getDataGrids()[i] = new DataGrid();
                details_tmp.getDataGrids()[i] = new DataGrid();
                scalings_tmp.getDataGrids()[i] = new DataGrid();
                re_interDetails_tmp.getDataGrids()[i] = new DataGrid();
                re_interScalings_tmp.getDataGrids()[i] = new DataGrid();
            }
        }
        sigs.add(sigs_tmp);
        interDetails.add(interDetails_tmp);
        interScalings.add(interScalings_tmp);
        details.add(details_tmp);
        scalings.add(scalings_tmp);
        re_sigs.add(re_sigs_tmp);
        re_interDetails.add(re_interDetails_tmp);
        re_interScalings.add(re_interScalings_tmp);

        index = sigs.indexOf(sigs.getLast());
    }

    public void addressSigElement(int[] indexList){
        for (int index:
                indexList) {
            this.getSigs().get(this.index).getDataGrids()[index].setAddressed(true);
        }
    }
    public void addressInterDetailsElement(int[] indexList){
        for (int index:
                indexList) {
            this.getInterDetails().get(this.index).getDataGrids()[index].setAddressed(true);
        }
    }
    public void addressInterScalingsElement(int[] indexList){
        for (int index:
                indexList) {
            this.getInterScalings().get(this.index).getDataGrids()[index].setAddressed(true);
        }
    }
    public void addressDetailsElement(int[] indexList){
        for (int index:
                indexList) {
            this.getDetails().get(this.index).getDataGrids()[index].setAddressed(true);
        }
    }
    public void addressScalingsElement(int[] indexList){
        for (int index:
                indexList) {
            this.getScalings().get(this.index).getDataGrids()[index].setAddressed(true);
        }
    }
    public void address_RE_SigElement(int[] indexList){
        for (int index:
                indexList) {
            this.getRe_sigs().get(this.index).getDataGrids()[index].setAddressed(true);
        }
    }
    public void address_RE_InterDetailsElement(int[] indexList){
        for (int index:
                indexList) {
            this.getRe_interDetails().get(this.index).getDataGrids()[index].setAddressed(true);
        }
    }
    public void address_RE_InterScalingsElement(int[] indexList){
        for (int index:
                indexList) {
            this.getRe_interScalings().get(this.index).getDataGrids()[index].setAddressed(true);
        }
    }
    public void addressSigElement(int index){
        this.getSigs().get(this.index).getDataGrids()[index].setAddressed(true);
    }
    public void addressInterDetailsElement(int index){
        this.getInterDetails().get(this.index).getDataGrids()[index].setAddressed(true);
    }
    public void addressInterScalingsElement(int index){
        this.getInterScalings().get(this.index).getDataGrids()[index].setAddressed(true);
    }
    public void addressDetailsElement(int index){
        this.getDetails().get(this.index).getDataGrids()[index].setAddressed(true);
    }
    public void addressScalingsElement(int index){
        this.getScalings().get(this.index).getDataGrids()[index].setAddressed(true);
    }
    public void address_RE_SigElement(int index){
        this.getRe_sigs().get(this.index).getDataGrids()[index].setAddressed(true);
    }
    public void address_RE_InterDetailsElement(int index){
        this.getRe_interDetails().get(this.index).getDataGrids()[index].setAddressed(true);
    }
    public void address_RE_InterScalingsElement(int index){
        this.getRe_interScalings().get(this.index).getDataGrids()[index].setAddressed(true);
    }
    public void storeData_2inter(double[] sig, double[] details, double[] scalings){
        for (int i = 0; i < N; i++) {
            this.getSigs().get(this.index).getDataGrids()[i].setData(sig[i]);
            if(i<N/2)
            {
                this.getInterScalings().get(this.index).getDataGrids()[i].setData(scalings[i]);
                this.getInterDetails().get(this.index).getDataGrids()[i].setData(details[i]);
            }
        }
        // for the final, copy previous
        if(this.index > 0)
        {
            for (int i = 0; i < N/2; i++) {
                double scaling = this.getScalings().get(this.index-1).getDataGrids()[i].getData();
                double detail = this.getDetails().get(this.index-1).getDataGrids()[i].getData();
                this.getScalings().get(this.index).getDataGrids()[i].setData(scaling);
                this.getDetails().get(this.index).getDataGrids()[i].setData(detail);
            }
        }
    }
    public void storeData_RE_2inter_detail(double[] sigSaved ,double[] scalingSaved, double[] detailSaved,double[] sig, double[] scaling, double[] detail, int detail_index){

        for (int i = 0; i < N; i++) {
            // copy and save the old value
            this.getSigs().get(this.index).getDataGrids()[i].setData(sigSaved[i]);

            //store the new value
            this.getRe_sigs().get(this.index).getDataGrids()[i].setData(sig[i]);

            if(i<N/2) {
                // copy and save the old value
                this.getInterScalings().get(this.index).getDataGrids()[i].setData(scalingSaved[i]);
                this.getInterDetails().get(this.index).getDataGrids()[i].setData(detailSaved[i]);
                this.getDetails().get(this.index).getDataGrids()[i].setData(detailSaved[i]);
                this.getScalings().get(this.index).getDataGrids()[i].setData(scalingSaved[i]);

                //store the new value
                double re_scaling = this.getRe_interScalings().get(this.index-1).getDataGrids()[i].getData();
                this.getRe_interScalings().get(this.index).getDataGrids()[i].setData(re_scaling);
                if(i>detail_index-1)
                {
                    this.getRe_interDetails().get(this.index).getDataGrids()[i].setData(detail[i]);
                }
            }
        }
    }
    public void storeData_RE_2inter_scaling(double[] sigSaved ,double[] scalingSaved, double[] detailSaved,double[] sig, double[] scaling, double[] detail, int scaling_index){

        for (int i = 0; i < N; i++) {
            // copy and save the old value
            this.getSigs().get(this.index).getDataGrids()[i].setData(sigSaved[i]);

            //store the new value
            this.getRe_sigs().get(this.index).getDataGrids()[i].setData(sig[i]);

            if(i<N/2) {
                // copy and save the old value
                this.getInterScalings().get(this.index).getDataGrids()[i].setData(scalingSaved[i]);
                this.getInterDetails().get(this.index).getDataGrids()[i].setData(detailSaved[i]);
                this.getDetails().get(this.index).getDataGrids()[i].setData(detailSaved[i]);
                this.getScalings().get(this.index).getDataGrids()[i].setData(scalingSaved[i]);

                //store the new value
                double re_detail = this.getRe_interDetails().get(this.index-1).getDataGrids()[i].getData();
                this.getRe_interDetails().get(this.index).getDataGrids()[i].setData(re_detail);
                if(i>scaling_index-1)
                {
                    this.getRe_interScalings().get(this.index).getDataGrids()[i].setData(scaling[i]);
                }
            }
        }
    }
    public void storeData_RE_2sig(double[] sigSaved ,double[] scalingSaved, double[] detailSaved,double[] sig){

        for (int i = 0; i < N; i++) {
            // copy and save the old value
            this.getSigs().get(this.index).getDataGrids()[i].setData(sigSaved[i]);

            //store the new value
            this.getRe_sigs().get(this.index).getDataGrids()[i].setData(sig[i]);

            if(i<N/2) {
                // copy and save the old value
                this.getInterScalings().get(this.index).getDataGrids()[i].setData(scalingSaved[i]);
                this.getInterDetails().get(this.index).getDataGrids()[i].setData(detailSaved[i]);
                this.getDetails().get(this.index).getDataGrids()[i].setData(detailSaved[i]);
                this.getScalings().get(this.index).getDataGrids()[i].setData(scalingSaved[i]);

                //store the new value
                double re_detail = this.getRe_interDetails().get(this.index-1).getDataGrids()[i].getData();
                double re_scaling = this.getRe_interScalings().get(this.index-1).getDataGrids()[i].getData();
                this.getRe_interScalings().get(this.index).getDataGrids()[i].setData(re_scaling);
                this.getRe_interDetails().get(this.index).getDataGrids()[i].setData(re_detail);
            }
        }
    }
    public void storeData_toFinal_detail(double[] sig, double[] details, int scalingHighlight_index){
        for (int i = 0; i < N; i++) {
            this.getSigs().get(this.index).getDataGrids()[i].setData(sig[i]);
            if(i<N/2)
            {
                if(i<=scalingHighlight_index)
                {
                    this.getDetails().get(this.index).getDataGrids()[i].setData(details[i]);
                }
                this.getInterDetails().get(this.index).getDataGrids()[i].setData(details[i]);
            }
        }
        // for the final, copy previous
        if(this.index > 0)
        {
            for (int i = 0; i < N/2; i++) {
                double scaling = this.getScalings().get(this.index-1).getDataGrids()[i].getData();
//                double interdetail = this.getDetails().get(this.index-1).getDataGrids()[i].getData();
                double interscaling = this.getInterScalings().get(this.index-1).getDataGrids()[i].getData();
                this.getScalings().get(this.index).getDataGrids()[i].setData(scaling);
//                this.getInterDetails().get(this.index).getDataGrids()[i].setData(interdetail);
                this.getInterScalings().get(this.index).getDataGrids()[i].setData(interscaling);
            }
        }
    }
    public void storeData_toFinal_scaling(double[] sig, double[] scalings, int scalingHighlight_index){
        for (int i = 0; i < N; i++) {
            this.getSigs().get(this.index).getDataGrids()[i].setData(sig[i]);
            if(i<N/2)
            {
                if(i<=scalingHighlight_index)
                {
                    this.getScalings().get(this.index).getDataGrids()[i].setData(scalings[i]);
                }
                this.getInterScalings().get(this.index).getDataGrids()[i].setData(scalings[i]);
            }
        }
        // for the final, copy previous
        if(this.index > 0)
        {
            for (int i = 0; i < N/2; i++) {
                double detail = this.getDetails().get(this.index-1).getDataGrids()[i].getData();
                double interdetail = this.getInterDetails().get(this.index-1).getDataGrids()[i].getData();
                this.getDetails().get(this.index).getDataGrids()[i].setData(detail);
                this.getInterDetails().get(this.index).getDataGrids()[i].setData(interdetail);
            }
        }
    }
}
