package GUI.SubFrame;

import BLL.ChiTietHoaDonBLL;
import BLL.NhaXuatBanBLL;
import BLL.SachBLL;
import DTO.*;
import GUI.Custom.ChiTietHoaDonModel;
import GUI.Custom.JBookButton;
import GUI.Custom.WrapLayout;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.EventObject;

public class frmChiTietHoaDon extends JDialog {
    private JPanel pnlMain;
    private JPanel pnlCenter;
    private JPanel pnlCenterTop;
    private JPanel pnlCenterBottom;
    private JPanel pnlEast;
    private JPanel pnlTimKiem;
    private JComboBox cmbLoc;
    private JTextField txtTimKiem;
    private JPanel pnlChiTiet;
    private JTextArea txtTenSach;
    private JTextField txtTacGia;
    private JComboBox cmbNhaXuatBan;
    private JSpinner numSoTrang;
    private JLabel lblAnh;
    private JButton btnThem;
    private JSpinner numSoLuong;
    private JTextField txtDonGia;
    private JLabel lblTenNguoiNhan;
    private JLabel lblMaHoaDon;
    private JLabel lblGhiChu;
    private JLabel lblSoDienThoai;
    private JLabel lblDiaChi;
    private JTextArea txtGhiChu;
    private JButton btnCapNhat;
    private JButton btnXoa;
    private final HoaDon hoaDon;
    private final KhachHang khachHang;
    WrapLayout layout = new WrapLayout();
    JPanel pnlSach = new JPanel(layout);
    JScrollPane scrollSach = new JScrollPane(pnlSach);
    private Sach current;

    public frmChiTietHoaDon(KhachHang khachHang, HoaDon hoaDon) {
        this.khachHang = khachHang;
        this.hoaDon = hoaDon;

        lblMaHoaDon.setText(String.valueOf(hoaDon.getMaHoaDon()));
        lblTenNguoiNhan.setText(khachHang.getTenKhachHang());
        lblDiaChi.setText(khachHang.getDiaChi());
        lblSoDienThoai.setText(khachHang.getSoDienThoai());
        txtGhiChu.setText(hoaDon.getMoTa());


        initComponents();
        loadComboBox();
        createControls();
        loadBook(SachBLL.getInstance().laySach(0));
        loadDetail(ChiTietHoaDonBLL.getInstance().layChiTietHoaDon(khachHang.getMaKhachHang(), hoaDon.getMaHoaDon()));
        addEvents();

    }

    private void initComponents() {
        this.setContentPane(pnlMain);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(1280, 800);
        this.setLocationRelativeTo(null);
        this.setModal(true);
    }

    private void loadComboBox() {
        ArrayList<NhaXuatBan> filter = new ArrayList<>();
        NhaXuatBan defaultNXB = new NhaXuatBan(0, "Tất cả");
        filter.add(defaultNXB);
        filter.addAll(NhaXuatBanBLL.getInstance().layNXB());

        DefaultComboBoxModel cmb1 = new DefaultComboBoxModel();
        cmb1.addAll(filter);
        cmbLoc.setModel(cmb1);
        cmbLoc.setSelectedItem(filter.get(0));


        DefaultComboBoxModel cmb2 = new DefaultComboBoxModel();
        cmb2.addAll(NhaXuatBanBLL.getInstance().layNXB());
        cmbNhaXuatBan.setModel(cmb2);
    }

    void createControls() {
        createTopPanel();
        createBottomPanel();
    }

    JTable tblChiTietHoaDon;

    private void createTopPanel() {
        layout.setHgap(10);
        layout.setVgap(5);
        layout.setAlignment(FlowLayout.LEFT);

        scrollSach.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollSach.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        pnlCenterTop.add(scrollSach, BorderLayout.CENTER);
    }

    private void createBottomPanel() {
        ChiTietHoaDonModel model = new ChiTietHoaDonModel(null);
        tblChiTietHoaDon = new JTable(model);
        tblChiTietHoaDon.setFont(new Font("UTM AVO", Font.PLAIN, 13));
        tblChiTietHoaDon.setShowGrid(true);
        tblChiTietHoaDon.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        customCannotChange(tblChiTietHoaDon);

        JScrollPane scTable = new JScrollPane(tblChiTietHoaDon,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        tblChiTietHoaDon.getColumnModel().getColumn(0).setMinWidth(0);
        tblChiTietHoaDon.getColumnModel().getColumn(0).setMaxWidth(0);

        pnlCenterBottom.add(scTable, BorderLayout.CENTER);
    }

    private void customCannotChange(JTable tbl) {
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

    private void loadBook(ArrayList<Sach> dsSach) {
        pnlSach.removeAll();
        for (int i = 0; i < dsSach.size(); i++) {
            JBookButton bookButton = new JBookButton(dsSach.get(i));
            bookButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JBookButton bookButton = (JBookButton) e.getSource();
                    Sach sach = bookButton.getSach();
                    txtTenSach.setText(sach.getTenSach());
                    txtTacGia.setText(sach.getTacGia());
                    numSoTrang.setValue(sach.getSoTrang());
                    cmbNhaXuatBan.setSelectedIndex(sach.getMaNXB() - 1);
                    lblAnh.setIcon(sach.getAnh());
                    current = bookButton.getSach();
                    txtDonGia.setText("");
                    numSoLuong.setValue(0);
                }
            });
            pnlSach.add(bookButton);
        }
        pnlSach.revalidate();
        pnlSach.repaint();
    }

    private void loadDetail(ArrayList<ChiTietHoaDon> list) {
        ChiTietHoaDonModel model = (ChiTietHoaDonModel) tblChiTietHoaDon.getModel();
        model.setDataSource(list);
        tblChiTietHoaDon.revalidate();
        tblChiTietHoaDon.repaint();
    }

    private void addEvents() {
        comboBoxEvents();
        tableEvents();
        buttonEvents();
        textEvents();
    }

    private void comboBoxEvents() {
        cmbLoc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cmbLoc.getSelectedIndex() == -1) return;
                NhaXuatBan nhaXuatBan = (NhaXuatBan) cmbLoc.getSelectedItem();
                resetInput(nhaXuatBan.getMaNXB());
                loadBook(SachBLL.getInstance().laySach(nhaXuatBan.getMaNXB()));
            }
        });
    }

    private void buttonEvents() {
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!validateInput()) return;
                if (hasInList()) {
                    JOptionPane.showMessageDialog(null, "Sách đã có trong hóa đơn, hãy chọn sách khác!", "Thông báo", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon();
                chiTietHoaDon.setDonGia(Double.parseDouble(txtDonGia.getText()));
                chiTietHoaDon.setMaSach(current.getMaSach());
                chiTietHoaDon.setSoLuong((int) numSoLuong.getValue());
                TrangThai trangThai = ChiTietHoaDonBLL.getInstance().themSachVaoHoaDon(chiTietHoaDon, hoaDon.getMaHoaDon());
                if (trangThai == TrangThai.THANH_CONG) {
                    JOptionPane.showMessageDialog(null, "Thêm sách vào hóa đơn thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    loadDetail(ChiTietHoaDonBLL.getInstance().layChiTietHoaDon(khachHang.getMaKhachHang(), hoaDon.getMaHoaDon()));
                    tblChiTietHoaDon.repaint();
                    tblChiTietHoaDon.revalidate();
                    resetInput(current.getMaNXB());
                    current = null;
                } else {
                    JOptionPane.showMessageDialog(null, "Thêm sách vào hóa đơn thất bại, hãy xem lại!", "Thông báo", JOptionPane.ERROR_MESSAGE);

                }
            }
        });
        btnCapNhat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (current == null) {
                    JOptionPane.showMessageDialog(null, "Chưa chọn sách!", "Thông báo", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!validateInput()) return;
                if (!hasInList()) {
                    JOptionPane.showMessageDialog(null, "Sách chưa có trong hóa đơn, hãy thêm trước!", "Thông báo", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ChiTietHoaDon chiTietHoaDon = getCTHD(tblChiTietHoaDon);

                chiTietHoaDon.setMaSach(current.getMaSach());
                chiTietHoaDon.setDonGia(Double.parseDouble(txtDonGia.getText()));
                chiTietHoaDon.setSoLuong((int) numSoLuong.getValue());

                TrangThai trangThai = ChiTietHoaDonBLL.getInstance().capNhatChiTietHoaDon(chiTietHoaDon, hoaDon.getMaHoaDon());

                if (trangThai == TrangThai.THANH_CONG) {
                    JOptionPane.showMessageDialog(null, "Cập nhật thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    loadDetail(ChiTietHoaDonBLL.getInstance().layChiTietHoaDon(khachHang.getMaKhachHang(), hoaDon.getMaHoaDon()));
                    tblChiTietHoaDon.repaint();
                    tblChiTietHoaDon.revalidate();
                    resetInput(current.getMaNXB());
                    current = null;
                } else {
                    JOptionPane.showMessageDialog(null, "Cập nhật thất bại!", "Thông báo", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (current == null) {
                    JOptionPane.showMessageDialog(null, "Chưa chọn sách!", "Thông báo", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!hasInList()) {
                    JOptionPane.showMessageDialog(null, "Sách chưa có trong hóa đơn, hãy thêm trước!", "Thông báo", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                ChiTietHoaDon chiTietHoaDon = getCTHD(tblChiTietHoaDon);
                TrangThai trangThai = ChiTietHoaDonBLL.getInstance().xoaSachTrongHoaDon(hoaDon.getMaHoaDon(), chiTietHoaDon.getMaSach());
                if (trangThai == TrangThai.THANH_CONG) {
                    JOptionPane.showMessageDialog(null, "Xóa thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    loadDetail(ChiTietHoaDonBLL.getInstance().layChiTietHoaDon(khachHang.getMaKhachHang(), hoaDon.getMaHoaDon()));
                    tblChiTietHoaDon.repaint();
                    tblChiTietHoaDon.revalidate();
                    resetInput(current.getMaNXB());
                    current = null;
                } else {
                    JOptionPane.showMessageDialog(null, "Xóa thất bại!", "Thông báo", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void tableEvents() {
        tblChiTietHoaDon.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Sach sach = getSachFromCTHD((JTable) e.getSource());
                txtTenSach.setText(sach.getTenSach());
                txtTacGia.setText(sach.getTacGia());
                numSoTrang.setValue(sach.getSoTrang());
                cmbNhaXuatBan.setSelectedIndex(sach.getMaNXB() - 1);
                lblAnh.setIcon(sach.getAnh());
                current = sach;

                ChiTietHoaDon chiTietHoaDon = getCTHD((JTable) e.getSource());
                numSoLuong.setValue(chiTietHoaDon.getSoLuong());
                txtDonGia.setText(String.valueOf(chiTietHoaDon.getDonGia()));
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
        tblChiTietHoaDon.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                Sach sach = getSachFromCTHD((JTable) e.getSource());
                txtTenSach.setText(sach.getTenSach());
                txtTacGia.setText(sach.getTacGia());
                numSoTrang.setValue(sach.getSoTrang());
                cmbNhaXuatBan.setSelectedIndex(sach.getMaNXB() - 1);
                lblAnh.setIcon(sach.getAnh());
                current = sach;

                ChiTietHoaDon chiTietHoaDon = getCTHD((JTable) e.getSource());
                numSoLuong.setValue(chiTietHoaDon.getSoLuong());
                txtDonGia.setText(String.valueOf(chiTietHoaDon.getDonGia()));
            }
        });
    }

    private void textEvents() {
        txtTimKiem.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (cmbLoc.getSelectedIndex() == -1) return;
                loadBook(SachBLL.getInstance().timKiemSach(cmbLoc.getSelectedIndex(), txtTimKiem.getText()));

            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (cmbLoc.getSelectedIndex() == -1) return;
                loadBook(SachBLL.getInstance().timKiemSach(cmbLoc.getSelectedIndex(), txtTimKiem.getText()));

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (cmbLoc.getSelectedIndex() == -1) return;
                loadBook(SachBLL.getInstance().timKiemSach(cmbLoc.getSelectedIndex(), txtTimKiem.getText()));

            }
        });
    }

    private boolean validateInput() {
        if (current == null) {
            JOptionPane.showMessageDialog(null, "Chưa chọn sách cần thêm!", "Trạng thái", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        int soLuong = (int) numSoLuong.getValue();
        if (soLuong <= 0) {
            JOptionPane.showMessageDialog(null, "Số lượng phải luôn dương!", "Trạng thái", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!isNumeric(txtDonGia.getText())) {
            JOptionPane.showMessageDialog(null, "Giá nhập lỗi, hãy xem lại!", "Trạng thái", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private boolean hasInList() {
        ChiTietHoaDonModel model = (ChiTietHoaDonModel) tblChiTietHoaDon.getModel();
        ArrayList<ChiTietHoaDon> list = model.getDataSource();

        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i).getMaSach() == current.getMaSach()) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private Sach getSachFromCTHD(JTable tbl) {
        int rowIndex = tbl.getSelectedRow();
        ChiTietHoaDonModel model = (ChiTietHoaDonModel) tbl.getModel();
        ChiTietHoaDon chiTietHoaDon = model.getData(rowIndex);
        Sach sach = SachBLL.getInstance().laySachTheoMa(chiTietHoaDon.getMaSach());
        return sach;
    }

    private ChiTietHoaDon getCTHD(JTable tbl) {
        int rowIndex = tbl.getSelectedRow();
        ChiTietHoaDonModel model = (ChiTietHoaDonModel) tbl.getModel();
        return model.getData(rowIndex);
    }

    private Sach getFromInput() {
        Sach sach = new Sach();
        sach.setTenSach(txtTenSach.getText());
        sach.setTacGia(txtTacGia.getText());
        sach.setMaNXB(((NhaXuatBan) cmbNhaXuatBan.getSelectedItem()).getMaNXB());
        sach.setSoTrang((int) numSoTrang.getValue());
        sach.setAnh((ImageIcon) lblAnh.getIcon());
        return sach;
    }

    private void resetInput(int maNXB) {
        txtTacGia.setText("");
        txtTimKiem.setText("");
        txtTenSach.setText("");
        numSoTrang.setValue(0);
        lblAnh.setIcon(null);
        cmbNhaXuatBan.setSelectedIndex(maNXB - 1);
        cmbLoc.setSelectedIndex(maNXB);
        txtDonGia.setText("");
        numSoLuong.setValue(0);

    }
}
