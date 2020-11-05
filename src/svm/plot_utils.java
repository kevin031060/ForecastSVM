package svm;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import java.io.IOException;

public class plot_utils {
    public static void plot(double[] y_pred, double[] y_test, int nums) throws IOException {
        final XYChart chart = new XYChartBuilder().width(600).height(400).title("Area Chart").xAxisTitle("X").yAxisTitle("Y").build();
        int len = Math.min(nums, y_pred.length);
        double[] x_axis = new double[len];
        double[] y_pred_ = new double[len];
        double[] y_test_ = new double[len];
        for (int i = 0; i < len; i++){
            x_axis[i] = i + 1;
            y_pred_[i] = y_pred[i];
            y_test_[i] = y_test[i];
        }

// Customize Chart
//        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
//        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Area);

// Series
        chart.addSeries("prediction", x_axis, y_pred_);
        chart.addSeries("True", x_axis, y_test_);
// Show it
        new SwingWrapper(chart).displayChart();

// Save it
        BitmapEncoder.saveBitmap(chart, "./Sample_Chart", BitmapEncoder.BitmapFormat.PNG);

// or save it in high-res
        BitmapEncoder.saveBitmapWithDPI(chart, "./Sample_Chart_300_DPI", BitmapEncoder.BitmapFormat.PNG, 300);

    }
}
