package GUI.Custom;

import DTO.KhachHang;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class KhachHangTableModel extends AbstractTableModel {
    public void setDataSource(ArrayList<KhachHang> dataSource) {
        this.dataSource = dataSource;
    }

    private ArrayList<KhachHang> dataSource;
    private final String[] columnNames = {"Mã khách hàng", "Tên khách hàng", "Ngày sinh", "Địa chỉ", "Số điện thoại"};
    public KhachHangTableModel(ArrayList<KhachHang> dataSource) {
        this.dataSource = dataSource;
    }
    public KhachHang getData(int rowIndex)
    {
        return dataSource.get(rowIndex);
    }
    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }
    @Override
    public int getRowCount() {
        return dataSource.size();
    }
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        KhachHang khachHang = dataSource.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return khachHang.getMaKhachHang();
            case 1:
                return khachHang.getTenKhachHang();
            case 2:
                return khachHang.getNgaySinhTable();
            case 3:
                return khachHang.getDiaChi();
            case 4:
                return khachHang.getSoDienThoai();
            default:
                return null;
        }
    }
}
