/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.math3.distribution.TDistribution;
import org.apache.commons.math3.stat.correlation.Covariance;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
/**
 *
 * @author maria
 */
public class Calculations {
private DescriptiveStatistics stats;
    
    // Среднее геометрическое
    public double geomMean(double[] x){ 
//    for (int i = 0; i < x.length; i++){
//            x[i]= Math.abs(x[i]);
//        }
    stats = new DescriptiveStatistics(x);
    return stats.getGeometricMean(); 
    }
    
    // Среднее арифметическое
    public double arithMean(double[] x){ 
    stats = new DescriptiveStatistics(x);
    return stats.getMean();
    }
    
    // Оценка стандартного отклонения
    public double std(double[] x){ 
    stats = new DescriptiveStatistics(x);
    return stats.getStandardDeviation();
    }
    
    // Размах
    public double range(double[] x){ 
    stats = new DescriptiveStatistics(x);
    return max(x) - min(x); 
    }
    
    // Коэффициент ковариации 
    public double cov(double[] x, double[] y){ 
    stats = new DescriptiveStatistics(x);
    Covariance covariance = new Covariance();
    return covariance.covariance(x, y);
    }
    
    // Количество элементов
    public long volume(double[] x){
    stats = new DescriptiveStatistics(x);
    return stats.getN();
    }
    
    // Коэффициент вариации
    public double variation(double[] x){ 
    stats = new DescriptiveStatistics(x);
    return (std(x) / arithMean(x)) * 100; 
    }
    
    // Доверительный интервал 
    public double[] conInterval(double[] x){   
    double confidenceLevel = 0.95;
    double alpha = 1 - confidenceLevel;
    TDistribution tDistribution = new TDistribution((int) volume(x) - 1);
    double tValue = tDistribution.inverseCumulativeProbability(1 - alpha / 2);
        
    stats = new DescriptiveStatistics(x);
    double leftLimit = arithMean(x) - tValue * (std(x) / Math.sqrt(volume(x)));
    double rightLimit = arithMean(x) + tValue * (std(x) / Math.sqrt(volume(x)));
    double[] ci = {leftLimit, rightLimit};
    return ci;
    }
    
    // Дисперсия
    public double var(double[] x){
    stats = new DescriptiveStatistics(x);
    return stats.getVariance();
    }
    
    // Минимум
    public double min(double[] x){
    stats = new DescriptiveStatistics(x);
    return stats.getMin();
    }
    
    // Максимум
    public double max(double[] x){
    stats = new DescriptiveStatistics(x);
    return stats.getMax();
    }  
    
    
    public Map<String, Double> calculateSampleStats(double[] currentSample) {
        Map<String, Double> statistics = new LinkedHashMap<>();
//        DescriptiveStatistics ds = new DescriptiveStatistics(currentSample);

        statistics.put("Среднее геометрическое", geomMean(currentSample));
        statistics.put("Среднее арифметическое", arithMean(currentSample));     
        statistics.put("Стандартное отклонение", std(currentSample));
        statistics.put("Размах", range(currentSample));
        statistics.put("Количество элементов", (double) volume(currentSample));
        statistics.put("Коэффициент вариации (%)", variation(currentSample));
        
        double[] ci = conInterval(currentSample);
        statistics.put("Доверительный интервал (нижняя граница)", ci[0]);
        statistics.put("Доверительный интервал (верхняя граница)", ci[1]);
       
        statistics.put("Дисперсия", var(currentSample));
        statistics.put("Максимум", max(currentSample));
        statistics.put("Минимум", min(currentSample));
        
        return statistics;
    }
    
    // Ковариационная матрица (все пары выборок)
    public double[][] calculateCovarianceMatrix(List<double[]> samples) {
        int n = samples.size();
        double[][] matrix = new double[n][n];
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = cov(samples.get(i), samples.get(j));
            }
        }
        return matrix;
    }
}
