package GUI.SubFrame;

import BLL.NhaXuatBanBLL;
import BLL.ThongKeBLL;
import DTO.NhaXuatBan;
import com.toedter.calendar.JDateChooser;
import org.knowm.xchart.*;
import org.knowm.xchart.style.PieStyler;
import org.knowm.xchart.style.Styler;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class frmThongKe extends JFrame implements IGetMainPanel {
    private JPanel pnlMain;
    private JPanel pnlNorthTop;
    private JPanel pnlNgayBatDau;
    private JPanel pnlNgayKetThuc;
    private JPanel pnlChart;
    private JButton btnThongKe;
    private JPanel pnlChartTop;
    private JPanel pnlCenterTop;
    private JPanel pnlChartBottom;
    private JPanel pnlCenterBottom;
    private JComboBox cmbNhaXuatBan;
    private JPanel pnlNam;
    JDateChooser dtpNgayBatDau, dtpNgayKetThuc;
    private CategoryChart categoryChart;
    private PieChart pieChart;
    private JSpinner spinner;

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
        cmbNhaXuatBan.setSelectedIndex(0);
        btnThongKe.doClick();
    }

    private void createControls() {
        createDatePickers();
        loadComboBox();
        customSpinner();
        createCharts();
        addEvents();

    }

    private void loadComboBox() {
        DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel();
        comboBoxModel.addAll(NhaXuatBanBLL.getInstance().layNXB());
        cmbNhaXuatBan.setModel(comboBoxModel);
        cmbNhaXuatBan.setSelectedIndex(0);
    }

    private void customSpinner() {
        Calendar calendar = Calendar.getInstance();
        int nowYear = calendar.get(Calendar.YEAR);
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(nowYear, nowYear - 4, nowYear, 1);
        spinner = new JSpinner(spinnerModel);
        spinner.setFont(new Font("UTM Avo",Font.PLAIN,16));
        pnlNam.add(spinner,BorderLayout.CENTER);
    }

    private void addEvents() {
        btnThongKe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadCategoryChart(dtpNgayBatDau.getDate(), dtpNgayKetThuc.getDate());
                pnlChart.revalidate();
                pnlChart.repaint();
            }
        });
        cmbNhaXuatBan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadPieChart();
            }
        });
        spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                loadPieChart();
            }
        });

    }

    private void loadCategoryChart(Date ngayBatDauDate, Date ngayketThuc) {
        categoryChart.removeSeries("Doanh thu (Triệu đồng)");
        categories.clear();
        values.clear();
        ResultSet rs = ThongKeBLL.getInstance().thongKeTheoNgay(dtpNgayBatDau.getDate(), dtpNgayKetThuc.getDate());

        try {

            while (rs.next()) {
                categories.add(rs.getString(1));
                values.add(rs.getDouble(2));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        categoryChart.addSeries("Doanh thu (Triệu đồng)", categories, values);
    }
    private  void loadPieChart()    {
        if(cmbNhaXuatBan.getSelectedIndex() == -1)return;

        int maNXB =((NhaXuatBan)cmbNhaXuatBan.getSelectedItem()).getMaNXB();
        int nam = (int)spinner.getValue();
        pieChart.getSeriesMap().clear();
        ResultSet rs = ThongKeBLL.getInstance().thongKePhanTramSachTheoNXB(maNXB,nam);
        int i = 1;
        double sum = 100;
        while (true) {
            try {
                if (!rs.next()) break;
                double value = rs.getDouble(3);
                String formatContent = String.format("Top %d: %s (%.2f triệu đồng)",i++,rs.getString(1),rs.getDouble(2));
                pieChart.addSeries(formatContent,value);
                sum -= value;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(sum != 0) pieChart.addSeries("Các sản phẩm khác",sum);
        pnlChartBottom.repaint();
        pnlChartBottom.revalidate();

    }
    ArrayList<String> categories = new ArrayList<>();
    ArrayList<Double> values = new ArrayList<>();

    private void createCharts() {

        createCategoriesChart();
        createPiesChart();
    }



    private void createCategoriesChart() {
        categoryChart = new CategoryChartBuilder()
                .title("Biểu đồ thống kê doanh thu theo nhà xuất bản")
                .yAxisTitle("Doanh thu (Triệu đồng)")
                //.theme(Styler.ChartTheme.XChart)
                .build();
        categoryChart.getStyler().setToolTipsEnabled(true);
        categoryChart.getStyler().setLabelsVisible(true);
        Font customFont = new Font("Utm avo", Font.PLAIN, 16);
        categoryChart.getStyler().setBaseFont(customFont);
        categoryChart.getStyler().setAxisTitleFont(customFont);
        categoryChart.getStyler().setLabelsFont(customFont);
        categoryChart.getStyler().setLegendFont(customFont);
        categoryChart.getStyler().setLabelsFontColor(Color.WHITE);
        categoryChart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);

        pnlCenterTop.add(new XChartPanel<>(categoryChart), BorderLayout.CENTER);
    }
    private void createPiesChart() {
        pieChart = new PieChartBuilder().width(500).height(400).build();
        PieStyler styler = pieChart.getStyler();
        Font font = new Font("UTM Avo", Font.PLAIN, 16);
        styler.setLegendFont(font);
        styler.setChartTitleFont(font);
        styler.setLabelsFont(font);
        styler.setBaseFont(font);
        styler.setBaseFont(font);
        styler.setLegendPadding(10);
        pnlChartBottom.add(new XChartPanel<>(pieChart),BorderLayout.CENTER);
        cmbNhaXuatBan.setSelectedIndex(0);
    }

    private void createDatePickers() {
        dtpNgayBatDau = new JDateChooser();
        dtpNgayBatDau.setDateFormatString("dd/MM/yyyy");
        pnlNgayBatDau.add(dtpNgayBatDau, BorderLayout.CENTER);
        dtpNgayBatDau.setDate(getDateBefore(1));
        dtpNgayBatDau.setFont(new Font("UTM AVO", Font.PLAIN, 16));

        dtpNgayKetThuc = new JDateChooser();
        dtpNgayKetThuc.setDateFormatString("dd/MM/yyyy");
        pnlNgayKetThuc.add(dtpNgayKetThuc, BorderLayout.CENTER);
        dtpNgayKetThuc.setDate(getDateBefore(0));
        dtpNgayKetThuc.setFont(new Font("UTM AVO", Font.PLAIN, 16));

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
