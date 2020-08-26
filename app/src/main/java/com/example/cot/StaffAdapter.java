package com.example.cot;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class StaffAdapter extends BaseAdapter {
    private staffActivity context;
    private int layout;
    private List<Staff> staffList;
    public StaffAdapter(staffActivity context, int layout, List<Staff> staffList) {
        this.context = context;
        this.layout = layout;
        this.staffList = staffList;
    }
    @Override
    public int getCount() {
        return staffList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private class ViewHolder {
        TextView txtName, txtTaiKhoan,txtMatKhau,txtid,txtLuong,txtViTri;
        ImageView imgEdit,imgDelete;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            holder = new StaffAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);

            holder.txtName = (TextView) view.findViewById(R.id.tv_namestaff);
            holder.txtLuong = (TextView) view.findViewById(R.id.tv_luong);
            holder.txtViTri = (TextView) view.findViewById(R.id.tv_vitri);
            holder.txtTaiKhoan = (TextView) view.findViewById(R.id.tv_taikhoan);
            holder.txtMatKhau = (TextView) view.findViewById(R.id.tv_matkhau);
            holder.imgEdit = (ImageView) view.findViewById(R.id.imageviewEdit);
            holder.imgDelete = (ImageView) view.findViewById(R.id.imageviewDelete);
            holder.txtid = (TextView) view.findViewById(R.id.tv_idstaff);
            view.setTag(holder);
        }else{
            holder=(StaffAdapter.ViewHolder)view.getTag();
        }
        final Staff staff=staffList.get(position);

        holder.txtName.setText(staff.getName());
        holder.txtViTri.setText("Vị trí: "+staff.getViTri());
        holder.txtLuong.setText("Lương: "+ staff.getLuong());
        holder.txtTaiKhoan.setText("Tài khoản: "+staff.getTaiKhoan());
        holder.txtMatKhau.setText("Mật khẩu: "+ staff.getMatKhau());
        holder.txtid.setText(staff.getId()+"");


        //Xoa sua.
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateTKActivity.class);
                intent.putExtra("dataStaff",staff);
                context.startActivity(intent);
            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            XacNhanXoa(staff.getId());
            }
        });
        Animation animation= AnimationUtils.loadAnimation(context,R.anim.scale_list);
        view.startAnimation(animation);
        return view;
    }
    private  void XacNhanXoa(final int id){
        AlertDialog.Builder dialogXoa= new AlertDialog.Builder(context);
        dialogXoa.setMessage("Bạn có chắc muốn làm chuyện này không?");
        dialogXoa.setPositiveButton("Chắc như bắp!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.DeleteStaff(id);
            }
        });
        dialogXoa.setNegativeButton("Thôi! Nghĩ lại.", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogXoa.show();
    }

}
