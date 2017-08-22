package com.csecu.amrit.checkup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Amrit on 15-07-17.
 */
public class OptionItemGridAdapter extends BaseAdapter{




        private Context context;
        private ArrayList<Option> options;


        public OptionItemGridAdapter(Context context, ArrayList<Option> options) {

            this.context = context;
            this.options = options;
        }

        @Override
        public int getCount() {
            return options.size();
        }

        @Override
        public Object getItem(int position) {
            return options.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if(convertView == null){
                convertView = inflater.inflate(R.layout.option_item, parent, false);
            }

            ImageView optionImage = (ImageView) convertView.findViewById(R.id.optionImage);
            TextView optionText = (TextView) convertView.findViewById(R.id.optionText);

            optionImage.setImageResource(options.get(position).getImg());
            optionText.setText(options.get(position).getName());

            return convertView;
        }


}
