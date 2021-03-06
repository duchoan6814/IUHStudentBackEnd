package com.iuh.IUHStudent.util;

import com.iuh.IUHStudent.entity.HocPhan;
import com.iuh.IUHStudent.entity.MonHoc;
import com.iuh.IUHStudent.entity.SinhVienLopHocPhan;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Helper {
    private static Helper instance = null;

    public static List<Date> getDatesInWeek(Date refDate, int firstDayOfWeek) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(refDate);
        calendar.set(Calendar.DAY_OF_WEEK, firstDayOfWeek);
        calendar.set(Calendar.WEEK_OF_YEAR, calendar.get(Calendar.WEEK_OF_YEAR) - 1);
        List<Date> dates = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            dates.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return dates;
    }

    public static Double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static Double tinhDiemTrungBinhh(SinhVienLopHocPhan sinhVienLopHocPhan) {
        if(sinhVienLopHocPhan.getDiemCuoiKy() == null) {
            return null;
        }

        double _tongLyThuyet = (sinhVienLopHocPhan.getDiemThuongKy().stream().mapToDouble(a -> a).average().getAsDouble() * 20 + sinhVienLopHocPhan.getDiemGiuaKy() * 30 + sinhVienLopHocPhan.getDiemCuoiKy() * 50) / 100;

        MonHoc _monHoc = sinhVienLopHocPhan.getLopHocPhan().getHocPhan().getMonHoc();

        if (_monHoc.getSoTinChiThucHanh() <= 0) {
            return _tongLyThuyet;
        }

        return ((_tongLyThuyet * _monHoc.getSoTinChiLyThuyet()) + (sinhVienLopHocPhan.getDiemThucHanh().stream().mapToDouble(a -> a).average().getAsDouble() * _monHoc.getSoTinChiThucHanh())) / (_monHoc.getSoTinChiLyThuyet() + _monHoc.getSoTinChiThucHanh());
    }

    public static Helper getInstance() {
        if (instance == null) {
            instance = new Helper();
        }

        return instance;
    }
}
