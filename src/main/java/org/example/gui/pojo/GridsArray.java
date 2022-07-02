package org.example.gui.pojo;

import lombok.Data;

/**
 * As a collection of data, this class passes data between the class Wavelet and the class MyPanel.
 * All data processed by wavelet will be packed and passed to the class MyPanel to display.
 * The data structure is an array. It is deprecated for its inconvenience of adding additional steps.
 * &#064;Auther  Zhilun LI
 * &#064;Date 02.07.2022
 * &#064;Version  Prototype
 */
@Data
@Deprecated
public class GridsArray {
    DataGrids[] sigs;
    DataGrids[] interDetails;
    DataGrids[] interScalings;
    DataGrids[] details;
    DataGrids[] scalings;
    int steps;  // how many steps to store
    int N;  // signal lengh, not detail or scaling

    /**
     * Generate Grids array and allocate memory for it and its sub-elements.
     * Need to determine the number of steps first
     * @param steps
     * @param N
     */
    public GridsArray(int steps, int N){
        this.steps = steps;
        this.N = N;
        sigs = new DataGrids[steps];
        interDetails = new DataGrids[steps];
        interScalings = new DataGrids[steps];
        details = new DataGrids[steps];
        scalings = new DataGrids[steps];

        for (int i = 0; i < steps; i++) {
            sigs[i] = new DataGrids();
            interDetails[i] = new DataGrids();
            interScalings[i] = new DataGrids();
            details[i] = new DataGrids();
            scalings[i] = new DataGrids();

            sigs[i].setDataGrids(new DataGrid[N]);
            interDetails[i].setDataGrids(new DataGrid[N/2]);
            interScalings[i].setDataGrids(new DataGrid[N/2]);
            details[i].setDataGrids(new DataGrid[N/2]);
            scalings[i].setDataGrids(new DataGrid[N/2]);

            for (int j = 0; j < N; j++) {
                sigs[i].getDataGrids()[j] = new DataGrid();
                if (j<N/2)
                {
                    interDetails[i].getDataGrids()[j] = new DataGrid();
                    interScalings[i].getDataGrids()[j] = new DataGrid();
                    details[i].getDataGrids()[j] = new DataGrid();
                    scalings[i].getDataGrids()[j] = new DataGrid();
                }
            }
        }
    }
}
