package GUI.Custom;

import DTO.ChiTietHoaDon;
import DTO.KhachHang;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ChiTietHoaDonModel extends AbstractTableModel {
    public void setDataSource(ArrayList<ChiTietHoaDon> dataSource) {
        this.dataSource = dataSource;
    }

    public ArrayList<ChiTietHoaDon> getDataSource() {
        return dataSource;
    }

    private ArrayList<ChiTietHoaDon> dataSource;
    private final String[] columnNames = {"Mã sách","Tên sách","Số lượng","Đơn giá"};
    public ChiTietHoaDonModel(ArrayList<ChiTietHoaDon> dataSource) {
        this.dataSource = dataSource;
    }
    public ChiTietHoaDon getData(int rowIndex)
    {
        return dataSource.get(rowIndex);
    }
    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }
    @Override
    public int getRowCount() {
        if(dataSource == null)return 0;
        return dataSource.size();
    }
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ChiTietHoaDon chiTietHoaDon = dataSource.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return chiTietHoaDon.getMaSach();
            case 1:
                return chiTietHoaDon.getTenSach();
            case 2:
                return chiTietHoaDon.getSoLuong();
            case 3:
                return chiTietHoaDon.getDonGia();
            default:
                return null;
        }
    }
}
