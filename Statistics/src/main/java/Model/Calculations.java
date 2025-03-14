/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.math3.stat.correlation.Covariance;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
/**
 *
 * @author maria
 */
public class Calculations {
private DescriptiveStatistics stats;
    
    public double geomMean(double[] x){
//    for (int i = 0; i < x.length; i++){
//            x[i]= Math.abs(x[i]);
//        }
    stats = new DescriptiveStatistics(x);
    return stats.getGeometricMean(); 
    }
    
    public double arithMean(double[] x){ 
    stats = new DescriptiveStatistics(x);
    return stats.getMean();
    }
    
    public double std(double[] x){ 
    stats = new DescriptiveStatistics(x);
    return stats.getStandardDeviation();
    }
    
    public double range(double[] x){ 
    stats = new DescriptiveStatistics(x);
    return max(x) - min(x); 
    }
    
    public double cov(double[] x, double[] y){ //коэффициент ковариации 
    stats = new DescriptiveStatistics(x);
    Covariance covariance = new Covariance();
    return covariance.covariance(x, y);
    }
    
    public long volume(double[] x){
    stats = new DescriptiveStatistics(x);
    return stats.getN();
    }
    
    public double variation(double[] x){ //коэффициент вариации
    stats = new DescriptiveStatistics(x);
    return (std(x) / arithMean(x)) * 100; 
    }
    
    public double[] conInterval(double[] x){ //доверительный интервал 
    stats = new DescriptiveStatistics(x);
    double leftLimit = arithMean(x) - 1.96 * (std(x) / volume(x));
    double rightLimit = arithMean(x) + 1.96 * (std(x) / volume(x));
    double[] CI = {leftLimit, rightLimit};
    return CI;
    
//    stats = new DescriptiveStatistics(x);
//        double tValue = 1.96;
//        double se = stats.getStandardDeviation() / Math.sqrt(stats.getN());
//        return new double[]{
//            stats.getMean() - tValue * se,
//            stats.getMean() + tValue * se
//        };
    }
    
    public double var(double[] x){
    stats = new DescriptiveStatistics(x);
    return stats.getVariance();
    }
    
    public double min(double[] x){
    stats = new DescriptiveStatistics(x);
    return stats.getMin();
    }
    
    public double max(double[] x){
    stats = new DescriptiveStatistics(x);
    return stats.getMax();
    }  
    
    
    public Map<String, Double> calculateSampleStats(double[] currentSample) {
        Map<String, Double> statistics = new LinkedHashMap<>();
        DescriptiveStatistics ds = new DescriptiveStatistics(currentSample);

        statistics.put("Среднее арифметическое", ds.getMean());
        statistics.put("Среднее геометрическое", ds.getGeometricMean());
        statistics.put("Стандартное отклонение", ds.getStandardDeviation());
        statistics.put("Размах", ds.getMax() - ds.getMin());
        statistics.put("Дисперсия", ds.getVariance());
        statistics.put("Коэффициент вариации (%)", (ds.getStandardDeviation() / ds.getMean()) * 100);
        statistics.put("Минимум", ds.getMin());
        statistics.put("Максимум", ds.getMax());
        statistics.put("Количество элементов", (double) ds.getN());

        return statistics;
    }
    
    // Создание ковариационной матрицы (все пары выборок)
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
