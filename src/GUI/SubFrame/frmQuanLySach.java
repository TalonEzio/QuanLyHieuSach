package GUI.SubFrame;

import BLL.NhaXuatBanBLL;
import BLL.SachBLL;
import DTO.NhaXuatBan;
import DTO.Sach;
import GUI.Custom.JBookButton;
import GUI.Custom.WrapLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class frmQuanLySach  extends  JFrame implements  IGetMainPanel{
    private JPanel pnlMain;
    private JPanel pnlThongTinSach;
    private JPanel pnlTimKiem;
    private JPanel pnlChiTiet;
    private JComboBox cmbLoc;
    private JTextArea txtTenSach;
    private JTextField txtTacGia;
    private JComboBox cmbNhaXuatBan;
    private JSpinner numSoTrang;
    private JButton btnAnh;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JLabel lblAnh;

    public frmQuanLySach()
    {
        initComponents();
        loadComboBox();
        createControls();
        loadData(0);
        addEvents();
    }
    void initComponents() {
        this.setContentPane(pnlMain);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(1200, 600);
        this.setLocationRelativeTo(null);
    }

    private void loadComboBox() {
        ArrayList<NhaXuatBan> filter = new ArrayList<>();
        filter.add(new NhaXuatBan(0,"Tất cả"));
        filter.addAll(NhaXuatBanBLL.getInstance().layNXB());

        DefaultComboBoxModel cmb1 = new DefaultComboBoxModel();
        cmb1.addAll(filter);
        cmbLoc.setModel(cmb1);
        cmbLoc.setSelectedItem(filter.get(0));


        DefaultComboBoxModel cmb2 = new DefaultComboBoxModel();
        cmb2.addAll(NhaXuatBanBLL.getInstance().layNXB());
        cmbNhaXuatBan.setModel(cmb2);
    }
    WrapLayout layout = new WrapLayout();
    JPanel pnlCenter = new JPanel(layout);
    JScrollPane scrollSach = new JScrollPane(pnlCenter);
    void createControls()
    {
        layout.setHgap(10);
        layout.setVgap(5);
        layout.setAlignment(FlowLayout.LEFT);

        scrollSach.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollSach.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        pnlThongTinSach.add(scrollSach,BorderLayout.CENTER);
    }
    private void loadData(int maNXB) {
        pnlCenter.removeAll();
        ArrayList<Sach> dsSach = SachBLL.getInstance().laySach(maNXB);

        for (int i = 0; i < dsSach.size(); i++) {
            JBookButton btn = new JBookButton(dsSach.get(i));
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JBookButton bookButton = (JBookButton) e.getSource();
                    Sach sach  = bookButton.getSach();
                    txtTenSach.setText(sach.getTenSach());
                    txtTacGia.setText(sach.getTacGia());
                    numSoTrang.setValue(sach.getSoTrang());
                    cmbNhaXuatBan.setSelectedIndex(sach.getMaNXB());

                }
            });
            pnlCenter.add(btn);
        }

        pnlCenter.revalidate();
    }

    private void addEvents() {
        cmbLoc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cmbLoc.getSelectedIndex() == -1)return;
                NhaXuatBan nhaXuatBan = (NhaXuatBan) cmbLoc.getSelectedItem();
                loadData(nhaXuatBan.getMaNXB());
            }
        });
    }
    @Override
    public JPanel getMainPanel() {
        return pnlMain;
    }

    @Override
    public String getName() {
        return "frmQuanLySach";
    }
}
