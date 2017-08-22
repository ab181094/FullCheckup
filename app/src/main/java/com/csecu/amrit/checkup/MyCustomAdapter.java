package com.csecu.amrit.checkup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Amrit on 27-10-2016.
 */

public class MyCustomAdapter extends ArrayAdapter<Doctors> {

    public class ViewHolder {
        TextView tvname;
        TextView tvdept;
        TextView tvchamber;
        TextView tvqualifications;
    }

    public MyCustomAdapter(Context context,List<Doctors> objects) {
        super(context, R.layout.doctor_item, objects);
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        View row;
        ViewHolder viewHolder;

        if(convertView == null) {
            row = LayoutInflater.from(getContext()).inflate(R.layout.doctor_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.tvname = (TextView) row.findViewById(R.id.tvname);
            viewHolder.tvdept = (TextView) row.findViewById(R.id.tvdepartment);
            viewHolder.tvchamber = (TextView) row.findViewById(R.id.tvchamber);
            viewHolder.tvqualifications = (TextView) row.findViewById(R.id.tvqualifications);

            row.setTag(viewHolder);
        } else {
            row = convertView;
            viewHolder = (ViewHolder) row.getTag();
        }

        Doctors item = getItem(position);

        viewHolder.tvname.setText(item.getName());
        viewHolder.tvdept.setText(item.getDepartment());
        viewHolder.tvchamber.setText(item.getChamber());
        viewHolder.tvqualifications.setText(item.getQualification());

        return row;
    }
}