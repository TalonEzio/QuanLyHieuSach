package GUI.SubFrame;

import BLL.ThongKeBLL;
import com.toedter.calendar.JDateChooser;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.Styler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class frmThongKe extends JFrame implements IGetMainPanel {
    private JPanel pnlMain;
    private JPanel pnlNorth;
    private JPanel pnlCenter;
    private JPanel pnlNgayBatDau;
    private JPanel pnlNgayKetThuc;
    private JPanel pnlChart;
    private JButton btnThongKe;
    JDateChooser dtpNgayBatDau, dtpNgayKetThuc;
    private CategoryChart chart;

    @Override
    public JPanel getMainPanel() {
        return pnlMain;
    }

    @Override
    public String getName() {
        return "frmThongKe";
    }

    public frmThongKe() {
        initComponents();
        createControls();
    }

    private void createControls() {
        createDatePickers();
        createCharts();
        addEvents();

    }

    private void addEvents() {
        btnThongKe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadChart(dtpNgayBatDau.getDate(),dtpNgayKetThuc.getDate());
                pnlChart.revalidate();
                pnlChart.repaint();
            }
        });
    }

    private void loadChart(Date ngayBatDauDate, Date ngayketThuc) {
        chart.removeSeries("Doanh thu (Triệu đồng)");
        categories.clear();
        values.clear();
        ResultSet rs = ThongKeBLL.getInstance().thongKeTheoNgay(dtpNgayBatDau.getDate(), dtpNgayKetThuc.getDate());
        while(true)
        {
            try {
                if (!rs.next()) break;
                values.add(rs.getDouble(2));
                categories.add(rs.getString(1));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        chart.addSeries("Doanh thu (Triệu đồng)", categories, values);
    }

    ArrayList<String> categories = new ArrayList<>();
    ArrayList<Double> values = new ArrayList<>();
    private void createCharts() {

        chart = new CategoryChartBuilder()
                .title("Biểu đồ thống kê doanh thu theo nhà xuất bản")
                .xAxisTitle("Nhà xuất bản")
                .yAxisTitle("Doanh thu (Triệu đồng)")
                .theme(Styler.ChartTheme.Matlab)
                .build();
        chart.getStyler().setToolTipsEnabled(true); // Bật hiển thị giá trị
        chart.getStyler().setLabelsVisible(true); // Bật hiển thị giá trị
        chart.getStyler().setBaseFont(new Font("UTM AVO", Font.BOLD, 16)); // Định dạng font chữ cho giá trị
        ResultSet rs = ThongKeBLL.getInstance().thongKeTheoNgay(dtpNgayBatDau.getDate(), dtpNgayKetThuc.getDate());
        while(true)
        {
            try {
                if (!rs.next()) break;
                categories.add(rs.getString(1));
                values.add(rs.getDouble(2));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        chart.addSeries("Doanh thu (Triệu đồng)", categories, values);
        pnlChart.add(new XChartPanel<>(chart),BorderLayout.CENTER);
    }

    private void createDatePickers() {
        dtpNgayBatDau = new JDateChooser();
        dtpNgayBatDau.setDateFormatString("dd/MM/yyyy");
        pnlNgayBatDau.add(dtpNgayBatDau, BorderLayout.CENTER);
        dtpNgayBatDau.setDate(getDateBefore(1));
        dtpNgayBatDau.setFont(new Font("UTM AVO",Font.PLAIN,16));

        dtpNgayKetThuc = new JDateChooser();
        dtpNgayKetThuc.setDateFormatString("dd/MM/yyyy");
        pnlNgayKetThuc.add(dtpNgayKetThuc, BorderLayout.CENTER);
        dtpNgayKetThuc.setDate(getDateBefore(0));
        dtpNgayKetThuc.setFont(new Font("UTM AVO",Font.PLAIN,16));

    }

    private Date getDateBefore(int year) {
        LocalDate now = LocalDate.now().minusYears(year);
        Date date = Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return date;
    }

    private void initComponents() {
        this.setContentPane(pnlMain);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(1200, 600);
        this.setLocationRelativeTo(null);
    }

}
