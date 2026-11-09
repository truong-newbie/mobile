package com.example.nhasach;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class PhieuMuonAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<PhieuMuon> phieuMuonList;
    private int selectedPosition = -1;

    public PhieuMuonAdapter(Context context, int layout, List<PhieuMuon> phieuMuonList) {
        this.context = context;
        this.layout = layout;
        this.phieuMuonList = phieuMuonList;
    }

    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
        notifyDataSetChanged();
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    @Override
    public int getCount() {
        return phieuMuonList != null ? phieuMuonList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return phieuMuonList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder {
        TextView tvMaSach;
        TextView tvNhaXB;
        TextView tvLoaiSach;
        TextView tvChuDe;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.tvMaSach = convertView.findViewById(R.id.tvItemMaSach);
            holder.tvNhaXB = convertView.findViewById(R.id.tvItemNhaXB);
            holder.tvLoaiSach = convertView.findViewById(R.id.tvItemLoaiSach);
            holder.tvChuDe = convertView.findViewById(R.id.tvItemChuDe);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        PhieuMuon pm = phieuMuonList.get(position);
        holder.tvMaSach.setText(pm.getMaSach());
        holder.tvNhaXB.setText(pm.getNhaXB());
        holder.tvLoaiSach.setText(pm.getLoaiSach());
        holder.tvChuDe.setText(pm.getChuDe());

        // Highlight dòng được chọn
        if (position == selectedPosition) {
            convertView.setBackgroundColor(Color.parseColor("#D1E7DD"));
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }

        return convertView;
    }
}
