package com.calebkip.contactsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ContactAdapter extends ArrayAdapter<Contact> {
    private List<Contact> Contacts;
    private Context context;
    public  ContactAdapter(Context context,List<Contact>list){
        super(context,R.layout.row_layout,list);
       this.context=context;
       this.Contacts=list;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView= inflater.inflate(R.layout.row_layout,parent,false);
       TextView tvChar=convertView.findViewById(R.id.tvChar);
           TextView     tvName=convertView.findViewById(R.id.tvName);
             TextView   tvPhone= convertView.findViewById(R.id.tvPhone);
             tvChar.setText(Contacts.get(position).getName().toUpperCase().charAt(0)+"");
             tvName.setText(Contacts.get(position).getName());
             tvPhone.setText(Contacts.get(position).getPhone());


        return super.getView(position, convertView, parent);
    }
}
