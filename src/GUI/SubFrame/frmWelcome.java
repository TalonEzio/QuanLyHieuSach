package GUI.SubFrame;

import javax.swing.*;
import java.awt.*;

public class frmWelcome extends JFrame implements IGetMainPanel{

    private JPanel pnlFull;
    private JLabel lblImage;

    public frmWelcome(boolean visible)
    {
        initComponents();
        this.setVisible(visible);
        customImage();

    }

    private void customImage() {
        ImageIcon icon = (ImageIcon) lblImage.getIcon();
        int labelWidth = lblImage.getPreferredSize().width;
        int labelHeight = lblImage.getPreferredSize().height;
        Image scaledImage = icon.getImage().getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        lblImage.setIcon(icon);
    }

    private void initComponents() {
        this.setContentPane(pnlFull);
        this.setSize(600, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    }

    @Override
    public JPanel getMainPanel() {
        return pnlFull;
    }

    @Override
    public String getName() {
        return "frmWelcome";
    }
}
