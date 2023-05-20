package GUI.SubFrame;

import BLL.NhaXuatBanBLL;
import BLL.SachBLL;
import DTO.NhaXuatBan;
import DTO.Sach;
import DTO.TrangThai;
import GUI.Custom.JBookButton;
import GUI.Custom.WrapLayout;
import Resources.ResourceClass;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
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
    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JLabel lblAnh;
    private JPanel pnlAnh;
    private  Sach current;
    private JTextField txtTimKiem;
    private JButton btnXoaHinh;

    public frmQuanLySach()
    {
        initComponents();
        loadComboBox();
        createControls();
        loadData(SachBLL.getInstance().laySach(0));
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
        NhaXuatBan defaultNXB = new NhaXuatBan(0,"Tất cả");
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
    private void loadData(ArrayList<Sach> dsSach) {
        pnlCenter.removeAll();
        for (int i = 0; i < dsSach.size(); i++) {
            JBookButton bookButton = new JBookButton(dsSach.get(i));
            bookButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JBookButton bookButton = (JBookButton) e.getSource();
                    Sach sach  = bookButton.getSach();
                    txtTenSach.setText(sach.getTenSach());
                    txtTacGia.setText(sach.getTacGia());
                    numSoTrang.setValue(sach.getSoTrang());
                    cmbNhaXuatBan.setSelectedIndex(sach.getMaNXB() - 1);
                    lblAnh.setIcon(sach.getAnh());

                    current = bookButton.getSach();
                }
            });
            pnlCenter.add(bookButton);
        }
        pnlCenter.revalidate();
        pnlCenter.repaint();

    }
    private void addEvents() {
        cmbLoc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cmbLoc.getSelectedIndex() == -1)return;

                NhaXuatBan nhaXuatBan = (NhaXuatBan) cmbLoc.getSelectedItem();
                resetInput(nhaXuatBan.getMaNXB());
                loadData(SachBLL.getInstance().laySach(nhaXuatBan.getMaNXB()));
            }
        });
        btnAnh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "Hình ảnh (png, jpeg, bmp)", "png", "jpeg", "jpg", "bmp");
                chooser.setFileFilter(filter);
                int returnValue = chooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File imageFile = chooser.getSelectedFile();
                    ImageIcon imageIcon;
                    try {
                        byte[] imageData = Files.readAllBytes(imageFile.toPath());
                        if(imageData == null)
                        {
                            imageIcon = getDefaultImage();
                        }
                        else
                        {
                            ByteArrayInputStream inputStream = new ByteArrayInputStream(imageData);
                            BufferedImage bufferedImage = ImageIO.read(inputStream);
                            int width = bufferedImage.getWidth();
                            int height = bufferedImage.getHeight();
                            if (width > 160 || height > 160) {
                                Image scaledImage = bufferedImage.getScaledInstance(160, 200, Image.SCALE_SMOOTH);
                                imageIcon = new ImageIcon(scaledImage);
                            }
                            else
                            {
                                imageIcon = new ImageIcon(bufferedImage);
                            }
                        }
                        lblAnh.setIcon(imageIcon);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                }
            }
        });
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(validateBefore() == false)return;

                Sach sach = getFromInput();

                TrangThai trangThai = SachBLL.getInstance().themSachMoi(sach);
                if(trangThai == TrangThai.THANH_CONG)
                {
                    JOptionPane.showMessageDialog(null,"Thêm sách mới thành công","Trạng thái",JOptionPane.INFORMATION_MESSAGE);
                    resetInput(sach.getMaNXB());
                    pnlCenter.revalidate();
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"Thêm sách mới thất bại, hãy xem lại!","Trạng thái",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(validateBefore() == false)return;

                Sach sach = getFromInput();
                sach.setMaSach(current.getMaSach());
                TrangThai trangThai = SachBLL.getInstance().capNhatSach(sach);
                if(trangThai == TrangThai.THANH_CONG)
                {
                    JOptionPane.showMessageDialog(null,"Cập nhật sách thành công","Trạng thái",JOptionPane.INFORMATION_MESSAGE);
                    resetInput(sach.getMaNXB());
                    current = null;
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"Cập nhật sách thất bại, hãy xem lại!","Trạng thái",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choose  = JOptionPane.showConfirmDialog(null,
                        "Xóa sách ở đây sẽ xóa hết sách trong các hóa đơn!\nBạn vẫn muốn xóa chứ?",
                        "Cảnh báo",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                if(choose == JOptionPane.NO_OPTION)return;
                TrangThai trangThai = SachBLL.getInstance().xoaSach(current);
                if(trangThai == TrangThai.THANH_CONG)
                {
                    JOptionPane.showMessageDialog(null,"Xóa sách thành công","Trạng thái",JOptionPane.INFORMATION_MESSAGE);
                    resetInput(current.getMaNXB());
                    pnlCenter.revalidate();
                    current = null;
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"Xoá sách thất bại, hãy xem lại!","Trạng thái",JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnXoaHinh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                current.setAnh(null);
                lblAnh.setIcon(null);
            }
        });
        txtTimKiem.getDocument().addDocumentListener(textChangedListener());
    }
    private  Sach getFromInput()
    {
        Sach sach = new Sach();
        sach.setTenSach(txtTenSach.getText());
        sach.setTacGia(txtTacGia.getText());
        //sach.setMaNXB(cmbNhaXuatBan.getSelectedIndex() + 1);
        sach.setMaNXB(((NhaXuatBan)cmbNhaXuatBan.getSelectedItem()).getMaNXB());
        sach.setSoTrang((int)numSoTrang.getValue());
        sach.setAnh((ImageIcon) lblAnh.getIcon());
        return sach;
    }

    private boolean validateBefore() {
        if(txtTenSach.getText().length() < 6)
        {
            JOptionPane.showMessageDialog(null,"Tên sách phải trên 5 ký tự","Trạng thái",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(txtTacGia.getText().length() < 6)
        {
            JOptionPane.showMessageDialog(null,"Tên tác giả trên 5 ký tự","Trạng thái",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(cmbNhaXuatBan.getSelectedIndex() == -1)
        {
            JOptionPane.showMessageDialog(null,"Phải chọn nhà xuất bản","Trạng thái",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        int soTrang = (int) numSoTrang.getValue();
        if(soTrang < 20 || soTrang > 5000)
        {
            JOptionPane.showMessageDialog(null,"Số trang phải trong khoảng [20..5000]","Trạng thái",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    DocumentListener textChangedListener()
    {
        DocumentListener documentListener = new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                loadData(SachBLL.getInstance().timKiemSach(cmbLoc.getSelectedIndex() ,txtTimKiem.getText()));
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                loadData(SachBLL.getInstance().timKiemSach(cmbLoc.getSelectedIndex() ,txtTimKiem.getText()));
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                loadData(SachBLL.getInstance().timKiemSach(cmbLoc.getSelectedIndex() ,txtTimKiem.getText()));

            }
        };
        return documentListener;
    }

    private void resetInput(int maNXB) {
        txtTacGia.setText("");
        txtTimKiem.setText("");
        txtTenSach.setText("");
        numSoTrang.setValue(0);
        lblAnh.setIcon(null);
        cmbNhaXuatBan.setSelectedIndex(maNXB - 1);
        cmbLoc.setSelectedIndex(maNXB);

    }

    private ImageIcon getDefaultImage() {
        URL imageURL = ResourceClass.class.getResource("SachmacDinh.jpg");
        ImageIcon defaultImage = new ImageIcon(imageURL);
        Image newImg = defaultImage.getImage();
        newImg = newImg.getScaledInstance(160,200,Image.SCALE_SMOOTH);
        defaultImage = new ImageIcon(newImg);
        return defaultImage;
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
