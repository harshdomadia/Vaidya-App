package com.sihmaxhealth.drawerfragmeny;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ChatMessageAdapter extends ArrayAdapter<ChatMessage> {
    private static final int MY_MESSAGE = 0, OTHER_MESSAGE = 1, MY_IMAGE = 2, OTHER_IMAGE = 3;
    public ChatMessageAdapter(Context context, List<ChatMessage> data) {
        super(context, R.layout.item_mine_message, data);
    }
    @Override
    public int getViewTypeCount() {
        // my message, other message, my image, other image
        return 4;
    }
    @Override
    public int getItemViewType(int position) {
        ChatMessage item = getItem(position);
        if (item.isMine() && !item.isImage()) return MY_MESSAGE;
        else if (!item.isMine() && !item.isImage()) return OTHER_MESSAGE;
        else if (item.isMine() && item.isImage()) return MY_IMAGE;
        else return OTHER_IMAGE;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int viewType = getItemViewType(position);
        if (viewType == MY_MESSAGE) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_mine_message, parent, false);
            TextView textView = (TextView) convertView.findViewById(R.id.text);
            textView.setText(getItem(position).getContent());
        } else if (viewType == OTHER_MESSAGE) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout1, parent, false);
            TextView textView = (TextView) convertView.findViewById(R.id.text);
            String imageAdderString=getItem(position).getContent();
            String[] imageName=null;
            if(imageAdderString.contains("do you have")) {
                imageName=imageAdderString.split(" ");
                String uri="@drawable/"+imageName[3];
                System.out.println(uri);
                int resId;
                try {

                    resId = convertView.getResources().getIdentifier(uri, "drawable", parent.getContext().getPackageName());
                    if(resId!=0) {
                        SpannableStringBuilder builder = new SpannableStringBuilder(" Do You Have " +imageName[3]+ "?");
                        builder.setSpan(new ImageSpan(getContext(), resId), 0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        //textView.setCompoundDrawablesWithIntrinsicBounds(resId,0,0,0);
                        textView.setText(builder, TextView.BufferType.SPANNABLE);
                    }
                    else
                    {
                        textView.setText(imageAdderString);
                    }
                }
                catch (RuntimeException e)
                {
                    textView.setText(imageAdderString);
                }
                //System.out.println("resId "+resId);

                //textView.setCompoundDrawablePadding(10);


            }
            else {
                //builder.append(imageAdderString).append();
                textView.setText(imageAdderString);
            }
            //textView.setText(getItem(position).getContent());
        } else if (viewType == MY_IMAGE) {
            //convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_mine_image, parent, false);
        } else {

            // convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_other_image, parent, false);
        }

        convertView.findViewById(R.id.chatMessageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "onClick", Toast.LENGTH_LONG).show();
            }
        });
        return convertView;
    }
}