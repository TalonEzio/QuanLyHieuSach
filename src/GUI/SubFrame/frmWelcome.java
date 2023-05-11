package GUI.SubFrame;

import javax.swing.*;

public class frmWelcome extends JFrame implements IGetMainPanel{

    private JPanel pnlFull;

    public frmWelcome(boolean visible)
    {
        initComponents();
        this.setVisible(visible);

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
