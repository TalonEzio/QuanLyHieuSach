package GUI.SubFrame;

import BLL.HoaDonBLL;
import BLL.KhachHangBLL;
import DTO.HoaDon;
import DTO.KhachHang;
import DTO.TrangThai;
import GUI.Custom.HoaDonTableModel;
import GUI.Custom.KhachHangTableModel;
import com.toedter.calendar.JDateChooser;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EventObject;

public class frmKhachHang extends JFrame implements IGetMainPanel {
    private JPanel pnlMain;
    private JTextField txtTenKhachHang;
    private JTextArea txtDiaChi;
    private JTextField txtSoDienThoai;
    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JTable tblKhachHang;
    private  JTable tblHoaDon;
    private JPanel pnlData;
    private JPanel pnlNgaySinh;
    private JTextField txtTimKiem;
    private JPanel pnlTop;
    private JPanel pnlBottom;
    private JPanel pnlCenter;
    private JPanel pnlKhachHang;
    private JPanel pnlHoaDon;
    private JPanel pnlTTKH;
    private JPanel pnlDetailTop;
    private JPanel pnlDetail;
    private JPanel pnlDetailBottom;
    private JTextField txtTongTien;
    private JTextField txtSoLuong;
    private JPanel pnlNgayTao;
    private JButton btnThemHoaDon;
    private JButton btnSuaChiTiet;
    private JButton btnXoaHoaDon;
    private JTextArea txtMoTa;
    private JButton btnSuaHoaDon;
    private JCheckBox ckbThanhToan;
    JDateChooser dtpNgaySinh,dtpNgayTao;
    private KhachHang khachHangCurrent;
    private  HoaDon hoaDonCurrent;
    public frmKhachHang()
    {
        initComponents();

        KhachHangTableModel khachHangTableModel = new KhachHangTableModel(KhachHangBLL.getInstance().layDanhSachKhachHang());
        tblKhachHang = cusTomTable(tblKhachHang, pnlKhachHang,khachHangTableModel);

        HoaDonTableModel hoaDonTableModel = new HoaDonTableModel(null);
        tblHoaDon = cusTomTable(tblHoaDon, pnlHoaDon,hoaDonTableModel);

        createDatePicker();
        addEvents();

    }
    private  void loadDaTa(ArrayList<KhachHang> dSKH,boolean resetInput)
    {
        KhachHangTableModel model = (KhachHangTableModel) tblKhachHang.getModel();
        model.setDataSource(dSKH);
        tblKhachHang.revalidate();
        tblKhachHang.repaint();
        if(resetInput)resetKhachHangInput();
    }
    private  void loadDaTa(int maKhachHang)
    {
        ArrayList<HoaDon> dSHD = HoaDonBLL.getInstance().layDanhSachHoaDon(maKhachHang);
        HoaDonTableModel model = (HoaDonTableModel) tblHoaDon.getModel();
        model.setDataSource(dSHD);
        tblHoaDon.revalidate();
        tblHoaDon.repaint();
        resetHoaDonInput();

    }

    private void resetKhachHangInput() {
        txtDiaChi.setText("");txtSoDienThoai.setText("");txtTenKhachHang.setText("");txtTimKiem.setText("");
        dtpNgaySinh.setDate(new Date());
        khachHangCurrent = null;
    }
    private  void resetHoaDonInput()
    {
        txtSoLuong.setText("");txtTongTien.setText("");
        txtMoTa.setText("");
        dtpNgayTao.setDate(new Date());
    }

    private JTable cusTomTable(JTable tbl, JPanel parentPanel, AbstractTableModel model) {
        tbl = new JTable(model);
        tbl.setFont(new Font("UTM AVO",Font.PLAIN,13));
        tbl.setShowGrid(true);
        tbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        customCannotChange(tbl);

        JScrollPane scTable=new JScrollPane(tbl,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        tbl.getColumnModel().getColumn(0).setMinWidth(0);
        tbl.getColumnModel().getColumn(0).setMaxWidth(0);

        parentPanel.add(scTable,BorderLayout.CENTER);
        return tbl;
    }
    private void customCannotChange(JTable tbl)
    {
        DefaultCellEditor cellEditor = new DefaultCellEditor(new JTextField()) {
            @Override
            public boolean isCellEditable(EventObject event) {
                return false;
            }
        };
        for (int i = 0; i < tbl.getColumnCount(); i++) {
            TableColumn column = tbl.getColumnModel().getColumn(i);
            column.setCellEditor(cellEditor);
        }
    }
    private void createDatePicker() {
        dtpNgaySinh = new JDateChooser();
        dtpNgaySinh.setDateFormatString("dd/MM/yyyy");
        pnlNgaySinh.add(dtpNgaySinh,BorderLayout.CENTER);

        dtpNgayTao = new JDateChooser();
        dtpNgayTao.setDateFormatString("dd/MM/yyyy");
        pnlNgayTao.add(dtpNgayTao,BorderLayout.CENTER);


    }

    private void initComponents() {
        this.setContentPane(pnlMain);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(1200, 600);
        this.setLocationRelativeTo(null);

        txtSoLuong.setEditable(false);
        txtTongTien.setEditable(false);
    }

    private void addEvents() {
        tableEvents();
        buttonEvents();
        textEvents();


    }

    private void buttonEvents() {
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(txtTenKhachHang.getText().length() <5)
                {
                    JOptionPane.showMessageDialog(null,"Tên phải từ 5 ký tự trở lên!","Lỗi",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(txtDiaChi.getText().length() <5)
                {
                    JOptionPane.showMessageDialog(null,"Địa chỉ phải từ 5 ký tự trở lên!","Lỗi",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(dtpNgaySinh.getDate() == null)
                {
                    JOptionPane.showMessageDialog(null,"Ngày sinh không được để trống!","Lỗi",JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Calendar cal1 = Calendar.getInstance();
                cal1.setTime(dtpNgaySinh.getDate());

                Calendar cal2 = Calendar.getInstance();
                cal2.setTime(new Date());

                int diffYears = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR);
                if(diffYears < 14)
                {
                    JOptionPane.showMessageDialog(null,"Khách hàng phải 14 tuổi trở lên!","Lỗi",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(txtSoDienThoai.getText().length() < 10)
                {
                    JOptionPane.showMessageDialog(null,"Số điện thoạiphải từ 10 ký tự trở lên!","Lỗi",JOptionPane.ERROR_MESSAGE);
                    return;
                }

                TrangThai trangThai = KhachHangBLL.getInstance().themKhachHang(khachHangCurrent);
                if(trangThai ==TrangThai.THANH_CONG)
                {
                    JOptionPane.showMessageDialog(null,"Thêm khách hàng mới thành công","Trạng thái",JOptionPane.INFORMATION_MESSAGE);
                    loadDaTa(KhachHangBLL.getInstance().layDanhSachKhachHang(),true);
                    pnlData.revalidate();
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"Thêm khách hàng mới thất bại","Trạng thái",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(khachHangCurrent == null)
                {
                    JOptionPane.showMessageDialog(null,"Chưa chọn khách hàng!","Trạng thái",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                KhachHang khachHang = getFromInput();
                khachHang.setMaKhachHang(khachHangCurrent.getMaKhachHang());

                TrangThai trangThai = KhachHangBLL.getInstance().suaKhachHang(khachHang);

                if(trangThai ==TrangThai.THANH_CONG)
                {
                    JOptionPane.showMessageDialog(null,"Cập nhật thành công","Trạng thái",JOptionPane.INFORMATION_MESSAGE);
                    loadDaTa(KhachHangBLL.getInstance().layDanhSachKhachHang(),true);
                    pnlData.revalidate();
                    khachHangCurrent = null;
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"Cập nhật thất bại","Trạng thái",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(khachHangCurrent == null)
                {
                    JOptionPane.showMessageDialog(null,"Chưa chọn khách hàng!","Trạng thái",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int choose  = JOptionPane.showConfirmDialog(null,
                        "Xóa khách hàng đồng nghĩa với hóa tất cả hóa đơn của họ.\nBạn vẫn muốn xóa chứ?",
                        "Cảnh báo",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                if(choose == JOptionPane.NO_OPTION)return;
                TrangThai trangThai = KhachHangBLL.getInstance().xoaKhachHang(khachHangCurrent.getMaKhachHang());

                if(trangThai ==TrangThai.THANH_CONG)
                {
                    JOptionPane.showMessageDialog(null,"Xóa thành công","Trạng thái",JOptionPane.INFORMATION_MESSAGE);
                    loadDaTa(KhachHangBLL.getInstance().layDanhSachKhachHang(),true);
                    pnlData.revalidate();
                    khachHangCurrent = null;
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"Xóa thất bại","Trạng thái",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnThemHoaDon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(khachHangCurrent == null)
                {
                    JOptionPane.showMessageDialog(null,"Chưa chọn khách hàng!","Trạng thái",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                TrangThai trangThai = HoaDonBLL.getInstance().themHoaDon(khachHangCurrent.getMaKhachHang(),txtMoTa.getText());
                if(trangThai ==TrangThai.THANH_CONG)
                {
                    JOptionPane.showMessageDialog(
                            null,
                            "Thêm hóa đơn mới thành công!\n Hãy thêm sản phầm cần mua vào hóa đơn!",
                            "Trạng thái",
                            JOptionPane.INFORMATION_MESSAGE);
                    loadDaTa(khachHangCurrent.getMaKhachHang());
                    pnlData.revalidate();
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"Thêm hóa đơn mới thất bại","Trạng thái",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnSuaHoaDon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(khachHangCurrent == null)
                {
                    JOptionPane.showMessageDialog(null,"Chưa chọn khách hàng!","Trạng thái",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(hoaDonCurrent == null)
                {
                    JOptionPane.showMessageDialog(null,"Chưa chọn hóa đơn!","Trạng thái",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                hoaDonCurrent.setNgayTao(dtpNgayTao.getDate());
                hoaDonCurrent.setMoTa(txtMoTa.getText());
                hoaDonCurrent.setDaThanhToan(ckbThanhToan.isSelected());
                TrangThai trangThai = HoaDonBLL.getInstance().suaHoaDon(hoaDonCurrent);
                if(trangThai ==TrangThai.THANH_CONG)
                {
                    JOptionPane.showMessageDialog(
                            null,
                            "Cập nhật thông tin hóa đơn thành công!",
                            "Trạng thái",
                            JOptionPane.INFORMATION_MESSAGE);
                    loadDaTa(khachHangCurrent.getMaKhachHang());
                    pnlData.revalidate();
                    hoaDonCurrent = null;
                }
                else
                {
                    JOptionPane.showMessageDialog(
                            null,
                            "Cập nhật thông tin hóa đơn thất bại!",
                            "Trạng thái",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnXoaHoaDon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(khachHangCurrent == null)
                {
                    JOptionPane.showMessageDialog(null,"Chưa chọn khách hàng!","Trạng thái",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(hoaDonCurrent == null)
                {
                    JOptionPane.showMessageDialog(null,"Chưa chọn hóa đơn!","Trạng thái",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int choose  = JOptionPane.showConfirmDialog(null,
                        "Xóa hóa đơn có thể liên quan đến pháp luật.\nBạn vẫn muốn xóa chứ?",
                        "Cảnh báo",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                if(choose == JOptionPane.NO_OPTION)return;
                TrangThai trangThai = HoaDonBLL.getInstance().xoaHoaDon(hoaDonCurrent);
                if(trangThai ==TrangThai.THANH_CONG)
                {
                    JOptionPane.showMessageDialog(
                            null,
                            "Xóa hóa đơn thành công!",
                            "Trạng thái",
                            JOptionPane.INFORMATION_MESSAGE);
                    loadDaTa(khachHangCurrent.getMaKhachHang());
                    pnlData.revalidate();
                    hoaDonCurrent = null;
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"Xóa hóa đơn thất bại","Trạng thái",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnSuaChiTiet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(khachHangCurrent == null)
                {
                    JOptionPane.showMessageDialog(null,"Chưa chọn khách hàng!","Trạng thái",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(hoaDonCurrent == null)
                {
                    JOptionPane.showMessageDialog(null,"Chưa chọn hóa đơn!","Trạng thái",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                frmChiTietHoaDon chiTietHoaDon = new frmChiTietHoaDon(khachHangCurrent,hoaDonCurrent);
                chiTietHoaDon.setVisible(true);
            }
        });
    }

    private void tableEvents() {
        tblKhachHang.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                KhachHang khachHang =  getDataFromKhachHangTable((JTable) e.getSource());
                fillValueToEdit(khachHang);
                loadDaTa(khachHang.getMaKhachHang());
            }
            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        tblKhachHang.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                KhachHang khachHang =  getDataFromKhachHangTable((JTable) e.getSource());
                fillValueToEdit(khachHang);
                loadDaTa(khachHang.getMaKhachHang());
            }
        });
        tblHoaDon.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                hoaDonCurrent = getDataFromHoaDonTable((JTable) e.getSource());
                fillValueToEdit(hoaDonCurrent);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        tblHoaDon.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                hoaDonCurrent= getDataFromHoaDonTable((JTable) e.getSource());
                fillValueToEdit(hoaDonCurrent);
            }
        });
    }


    private void textEvents() {
        txtTimKiem.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                loadDaTa(KhachHangBLL.getInstance().timKiemKhachHang(txtTimKiem.getText()),false);

            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                loadDaTa(KhachHangBLL.getInstance().timKiemKhachHang(txtTimKiem.getText()),false);

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                loadDaTa(KhachHangBLL.getInstance().timKiemKhachHang(txtTimKiem.getText()),false);

            }
        });
    }


    private  KhachHang getDataFromKhachHangTable(JTable tbl)
    {
        int rowIndex = tbl.getSelectedRow();
        KhachHangTableModel model = (KhachHangTableModel) tbl.getModel();
        KhachHang khachHang = model.getData(rowIndex);

        khachHangCurrent = khachHang;
        return  khachHang;
    }
    private HoaDon getDataFromHoaDonTable(JTable tbl)
    {
        int rowIndex = tbl.getSelectedRow();
        HoaDonTableModel model = (HoaDonTableModel) tbl.getModel();
        HoaDon hoaDon= model.getData(rowIndex);
        return hoaDon;

    }
    private KhachHang getFromInput()
    {
        KhachHang khachHang = new KhachHang();
        khachHang.setTenKhachHang(txtTenKhachHang.getText());
        khachHang.setDiaChi(txtDiaChi.getText());
        khachHang.setSoDienThoai(txtSoDienThoai.getText());
        khachHang.setNgaySinh(dtpNgaySinh.getDate());
        return khachHang;
    }
    private void fillValueToEdit(KhachHang khachHang) {
        txtTenKhachHang.setText(khachHang.getTenKhachHang());
        txtDiaChi.setText(khachHang.getDiaChi());
        txtSoDienThoai.setText(khachHang.getSoDienThoai());
        dtpNgaySinh.setDate(khachHang.getNgaySinh());
        dtpNgaySinh.revalidate();
        dtpNgaySinh.repaint();
    }
    private void fillValueToEdit(HoaDon hoaDon) {
        txtSoLuong.setText(hoaDon.getSoLuongSach()+"");
        txtTongTien.setText(hoaDon.getThanhTien()+"");
        txtMoTa.setText(hoaDon.getMoTa());
        ckbThanhToan.setSelected(hoaDon.isDaThanhToan());
        dtpNgayTao.setDate(hoaDon.getNgayTao());
        dtpNgayTao.revalidate();
        dtpNgayTao.repaint();
    }
    @Override
    public JPanel getMainPanel() {
        return this.pnlMain;
    }
    @Override
    public String getName()
    {
        return "frmKhachHang";
    }

}
