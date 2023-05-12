package GUI.Custom;

import DTO.Sach;

import javax.swing.*;
import java.awt.*;

public class JBookButton extends JButton {
    private Sach sach;

    public Sach getSach() {
        return sach;
    }

    public void setSach(Sach sach) {
        this.sach = sach;
    }
    public JBookButton(Sach sach)
    {
        setSach(sach);

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(170,200));
        ImageIcon icon = sach.getAnh();
        JLabel lbl = new JLabel();
        lbl.setIcon(icon);
        add(lbl,BorderLayout.CENTER);
        lbl.setHorizontalAlignment(JLabel.CENTER);
        lbl.setVerticalAlignment(JLabel.CENTER);

        JLabel lbl2 = new JLabel(sach.getTenSach());
        add(lbl2,BorderLayout.SOUTH);
        lbl2.setHorizontalAlignment(JLabel.CENTER);
        lbl2.setVerticalAlignment(JLabel.CENTER);
        lbl2.setFont(new Font("UTM AVO",Font.PLAIN,16));
    }
}
