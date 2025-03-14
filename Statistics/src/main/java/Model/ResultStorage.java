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
public class ResultStorage {
    private List<Map<String, Double>> sampleStats = new ArrayList<>(); 
    private double[][] covarianceMatrix;

    public void addSampleStats(Map<String, Double> stats) {
        sampleStats.add(stats);
    }

    public List<Map<String, Double>> getSampleStats() {
        return sampleStats;
    }

     public void setCovarianceMatrix(double[][] matrix) {
        this.covarianceMatrix = matrix;
    }

    public double[][] getCovarianceMatrix() {
        return covarianceMatrix;
    }

    public void clear() {
        sampleStats.clear();
        covarianceMatrix = null;
    }
}