package GUI;

import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ChartFrame extends JFrame {
    public ChartFrame() {
        List<String> categories = new ArrayList<>();

        List<Double> values = new ArrayList<>();

        CategoryChart chart = new CategoryChartBuilder()
                .width(800)
                .height(600)
                .title("Biểu đồ Dạng cột")
                .xAxisTitle("Category")
                .yAxisTitle("Value")
                .theme(Styler.ChartTheme.Matlab)
                .build();
        chart.getStyler().setToolTipsEnabled(true); // Bật hiển thị giá trị
        chart.getStyler().setLabelsVisible(true); // Bật hiển thị giá trị
        chart.getStyler().setBaseFont(new Font("UTM AVO", Font.BOLD, 12)); // Định dạng font chữ cho giá trị
        chart.addSeries("Series 1", categories, values);

        // Hiển thị biểu đồ trên JFrame
        JFrame frame = new JFrame("Column Chart Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new XChartPanel<>(chart));
        frame.pack();
        frame.setVisible(true);
    }

    private double[] createXData(int length) {
        double[] xData = new double[length];
        for (int i = 0; i < length; i++) {
            xData[i] = i + 1;
        }
        return xData;
    }
}
