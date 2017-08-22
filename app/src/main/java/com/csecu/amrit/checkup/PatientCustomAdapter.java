package com.csecu.amrit.checkup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Amrit on 17-07-2017.
 */

public class PatientCustomAdapter extends ArrayAdapter<Patient> {
    public class ViewHolder {
        TextView tvname;
        TextView tvcontact;
        TextView tvgender;
        TextView tvage;
    }

    public PatientCustomAdapter(Context context, List<Patient> objects) {
        super(context, R.layout.patient_list, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        PatientCustomAdapter.ViewHolder viewHolder;

        if(convertView == null) {
            row = LayoutInflater.from(getContext()).inflate(R.layout.patient_list, parent, false);

            viewHolder = new PatientCustomAdapter.ViewHolder();
            viewHolder.tvname = (TextView) row.findViewById(R.id.tvpatientname);
            viewHolder.tvcontact = (TextView) row.findViewById(R.id.tvpatientcontact);
            viewHolder.tvgender = (TextView) row.findViewById(R.id.tvpatientsex);
            viewHolder.tvage = (TextView) row.findViewById(R.id.tvpatientage);

            row.setTag(viewHolder);
        } else {
            row = convertView;
            viewHolder = (PatientCustomAdapter.ViewHolder) row.getTag();
        }

        Patient item = getItem(position);

        viewHolder.tvname.setText(item.getName());
        viewHolder.tvcontact.setText(item.getContact());
        viewHolder.tvgender.setText("Gender: " +item.getGender());
        viewHolder.tvage.setText("Age: " +item.getAge());

        return row;
    }
}
