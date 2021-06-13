package svm;

import org.knowm.xchart.*;
import org.knowm.xchart.style.colors.XChartSeriesColors;
import org.knowm.xchart.style.lines.SeriesLines;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.io.IOException;

public class plot_utils {
    public static void plot(double[] y_pred, double[] y_test, int nums) throws IOException {
        final XYChart chart = new XYChartBuilder().width(600).height(400).title("Load").xAxisTitle("X").yAxisTitle("Y").build();
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

    public static void plot_interval(double[][] y_pred, double[] y_test, int nums) throws IOException {
        final XYChart chart = new XYChartBuilder().width(600).height(400).title("Load").xAxisTitle("X").yAxisTitle("Y").build();
        int len = Math.min(nums, y_pred[0].length);
        double[] x_axis = new double[len];
        double[] y_pred_ = new double[len];
        double[] y_test_ = new double[len];

        double[] y_up = new double[len];
        double[] y_down = new double[len];
        for (int i = 0; i < len; i++){
            x_axis[i] = i + 1;
            y_pred_[i] = y_pred[1][i];
            y_up[i] = y_pred[2][i];
            y_down[i] = y_pred[0][i];
            y_test_[i] = y_test[i];
        }

// Customize Chart
//        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
//        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Area);

// Series


//        chart.addSeries("prediction", x_axis, y_pred_);
        XYSeries tre = chart.addSeries("True", x_axis, y_test_);
        tre.setLineStyle(SeriesLines.DASH_DOT);

        XYSeries low = chart.addSeries("lower", x_axis, y_down);
        XYSeries upper = chart.addSeries("upper", x_axis, y_up);
        low.setLineColor(XChartSeriesColors.BLACK);
        upper.setLineColor(XChartSeriesColors.BLACK);
//        XYSeries seriesLiability = chart.addSeries("Liability", x_axis, y_down, y_up);
//        seriesLiability.setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Area);
        low.setMarker(SeriesMarkers.NONE);
        upper.setMarker(SeriesMarkers.NONE);
        low.setLineStyle(SeriesLines.DASH_DOT);
        upper.setLineStyle(SeriesLines.DASH_DOT);

// Show it
        new SwingWrapper(chart).displayChart();

// Save it
        BitmapEncoder.saveBitmap(chart, "./Sample_Chart", BitmapEncoder.BitmapFormat.PNG);

// or save it in high-res
        BitmapEncoder.saveBitmapWithDPI(chart, "./Sample_Chart_300_DPI", BitmapEncoder.BitmapFormat.PNG, 300);

    }

}
