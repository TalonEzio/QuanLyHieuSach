package GUI.SubFrame;

import BLL.KhachHangBLL;
import DTO.KhachHang;
import DTO.TrangThai;
import GUI.Custom.KhachHangTableModel;
import com.toedter.calendar.JDateChooser;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.ArrayList;
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
    private JPanel pnlCenter;
    private JPanel pnlNgaySinh;
    private JTextField txtTimKiem;
    JDateChooser dtpNgaySinh;
    private KhachHang current;
    public frmKhachHang()
    {
        initComponents();
        tblKhachHang = createCustomTable(tblKhachHang,pnlCenter);
        createDatePicker();
        addEvents();

    }
    private  void loadDaTa(ArrayList<KhachHang> dSKH,boolean resetInput)
    {
        KhachHangTableModel model = (KhachHangTableModel) tblKhachHang.getModel();
        model.setDataSource(dSKH);
        tblKhachHang.revalidate();
        tblKhachHang.repaint();
        if(resetInput)resetInput();
    }

    private void resetInput() {
        txtDiaChi.setText("");txtSoDienThoai.setText("");txtTenKhachHang.setText("");txtTimKiem.setText("");
        dtpNgaySinh.setDate(new Date());
        current = null;
    }

    private JTable createCustomTable(JTable tbl,JPanel parentPanel) {
        KhachHangTableModel model = new KhachHangTableModel(KhachHangBLL.getInstance().layDanhSachKhachHang());
        tbl = new JTable(model);
        tbl.setFont(new Font("UTM AVO",Font.PLAIN,13));
        tbl.setShowGrid(true);

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
        //dtpNgaySinh.setFont(new Font("UTM AVO",Font.PLAIN,16));
        pnlNgaySinh.add(dtpNgaySinh,BorderLayout.CENTER);
    }

    private void initComponents() {
        this.setContentPane(pnlMain);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(1200, 600);
        this.setLocationRelativeTo(null);
    }

    private void addEvents() {
        tblKhachHang.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                KhachHang khachHang =  getDataFromTable((JTable) e.getSource());
                fillValueToEdit(khachHang);
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
                KhachHang khachHang =  getDataFromTable((JTable) e.getSource());
                fillValueToEdit(khachHang);
            }
        });
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TrangThai trangThai = KhachHangBLL.getInstance().themKhachHang(current);
                if(trangThai ==TrangThai.THANH_CONG)
                {
                    JOptionPane.showMessageDialog(null,"Thêm khách hàng mới thành công","Trạng thái",JOptionPane.INFORMATION_MESSAGE);
                    loadDaTa(KhachHangBLL.getInstance().layDanhSachKhachHang(),true);
                    pnlCenter.revalidate();
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
                KhachHang khachHang = getFromInput();
                khachHang.setMaKhachHang(current.getMaKhachHang());

                TrangThai trangThai = KhachHangBLL.getInstance().suaKhachHang(khachHang);

                if(trangThai ==TrangThai.THANH_CONG)
                {
                    JOptionPane.showMessageDialog(null,"Cập nhật thành công","Trạng thái",JOptionPane.INFORMATION_MESSAGE);
                    loadDaTa(KhachHangBLL.getInstance().layDanhSachKhachHang(),true);
                    pnlCenter.revalidate();
                    current = null;
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
                TrangThai trangThai = KhachHangBLL.getInstance().xoaKhachHang(current.getMaKhachHang());

                if(trangThai ==TrangThai.THANH_CONG)
                {
                    JOptionPane.showMessageDialog(null,"Xóa thành công","Trạng thái",JOptionPane.INFORMATION_MESSAGE);
                    loadDaTa(KhachHangBLL.getInstance().layDanhSachKhachHang(),true);
                    pnlCenter.revalidate();
                    current = null;
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"Xóa thất bại","Trạng thái",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
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
    private  KhachHang getDataFromTable(JTable tbl)
    {
        int rowIndex = tbl.getSelectedRow();
        KhachHangTableModel model = (KhachHangTableModel) tbl.getModel();
        KhachHang khachHang = model.getData(rowIndex);

        current = khachHang;
        return  khachHang;
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
