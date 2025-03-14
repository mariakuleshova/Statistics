/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import java.io.IOException;
import java.util.List;
import Model.*;
import java.util.Map;

/**
 *
 * @author maria
 */
public class Controller {
    private final DataAction dataAction = new DataAction();
    private final WorkWithData workWithData = new WorkWithData();

    public void importData(String filePath) throws IOException {
        List<double[]> samples = dataAction.downloadData(filePath);
        workWithData.setSamples(samples);
    }

    public void exportData(String filePath) throws IOException {
        dataAction.uploadData(filePath, workWithData.getResultStorage().getSampleStats(), workWithData.getResultStorage().getCovarianceMatrix());
    }
    
    // Вызывается при нажатии calcButton
    public void performCalculations() {
        workWithData.performCalculations();
    }

    public List<Map<String, Double>> getSampleStats() {
        return workWithData.getResultStorage().getSampleStats();
    }
    
    public double[][] getCovarianceMatrix() {
        return workWithData.getResultStorage().getCovarianceMatrix();
    }
}
