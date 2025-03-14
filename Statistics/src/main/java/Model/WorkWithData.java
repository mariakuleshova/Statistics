/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author maria
 */
public class WorkWithData {
    private List<double[]> samples = new ArrayList<>(); 
    private ResultStorage resultStorage = new ResultStorage(); 
    private Calculations calculator = new Calculations(); 

    public void setSamples(List<double[]> samples) {
        this.samples = samples;
    }

    public void performCalculations() {
        if (samples.isEmpty()) {
            throw new IllegalStateException("Данные не загружены!");
        }
        resultStorage.clear();

        for (int i = 0; i < samples.size(); i++) {
            Map<String, Double> stats = calculator.calculateSampleStats(samples.get(i));
            resultStorage.addSampleStats(stats);
        }
        
        double[][] covMatrix = calculator.calculateCovarianceMatrix(samples);
        resultStorage.setCovarianceMatrix(covMatrix);
    }

    // Доступ к хранилищу результатов
    public ResultStorage getResultStorage() {
        return resultStorage;
    }
}
