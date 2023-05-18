package GUI.Custom;

import DTO.HoaDon;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class HoaDonTableModel extends AbstractTableModel {
    public void setDataSource(ArrayList<HoaDon> dataSource) {
        this.dataSource = dataSource;
    }

    private ArrayList<HoaDon> dataSource = new ArrayList<>();
    private final String[] columnNames = {"Mã hóa đơn", "Ngày tạo", "Số lượng sách", "Tổng tiền", "Mô tả", "Trạng thái"};

    public HoaDonTableModel(ArrayList<HoaDon> dataSource) {
        this.dataSource = dataSource;
    }

    public HoaDon getData(int rowIndex) {
        return dataSource.get(rowIndex);
    }

    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public int getRowCount() {
        if (dataSource == null) return 0;
        return dataSource.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        HoaDon hoaDon = dataSource.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return hoaDon.getMaHoaDon();
            case 1:
                return hoaDon.getNgayTaoTable();
            case 2:
                return hoaDon.getSoLuongSach();
            case 3:
                return hoaDon.getThanhTien();
            case 4:
                return hoaDon.getMoTa();
            case 5:
                return hoaDon.getDaThanhToanTable();
            default:
                return null;
        }
    }
}
