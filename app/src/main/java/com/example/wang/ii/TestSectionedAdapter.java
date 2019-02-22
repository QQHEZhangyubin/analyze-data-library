package com.example.wang.ii;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;



public class TestSectionedAdapter extends SectionedBaseAdapter {

    Context context;
    private List<TaskModelBean> taskList;
    //用于控制后面的对号是不是显示（通过点击得到它在那个组的第几个）
    int sectionNow = 0;
    int positionNow = 0;

    public TestSectionedAdapter(Context context, List<TaskModelBean> taskList,
                                List<String> title,ImageView Header1Img) {
        this.context = context;
        this.taskList = taskList;
    }

    public void reference(int section, int position) {
        this.sectionNow = section;
        this.positionNow = position;
        notifyDataSetChanged();
    }

    @Override
    public Object getItem(int section, int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int section, int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getSectionCount() {
        return taskList.size();
    }

    @Override
    public int getCountForSection(int section) {

        return taskList.get(section).getItemName().size();
    }


    @Override
    public View getItemView(final int section, final int position, View convertView,
                            ViewGroup parent) {

        LayoutInflater layoutInflater  = LayoutInflater.from(context);
        ViewHolderChild childHoler = null;
        if(convertView == null){
            childHoler = new ViewHolderChild();
            convertView = layoutInflater.inflate(R.layout.oa_nzl_main_view_task_model_list_item_view,parent,false);
            childHoler.cName = (TextView) convertView.findViewById(R.id.task_model_text);
            childHoler.cImg = (ImageView) convertView.findViewById(R.id.task_model_img);

            convertView.setTag(childHoler);
        }else{
            childHoler = (ViewHolderChild)convertView.getTag();
        }



        childHoler.cName.setText(taskList.get(section).getItemName().get(position));
        childHoler.cName.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                sectionNow = section;
                positionNow = position;
                notifyDataSetChanged();
                Toast.makeText(context, "position item "+position, Toast.LENGTH_SHORT).show();
            }
        });
        if(sectionNow == section && positionNow == position){
            childHoler.cImg.setVisibility(View.VISIBLE);

        }else{
            childHoler.cImg.setVisibility(View.GONE);
        }

        return convertView;
    }

    @Override
    public View getSectionHeaderView(final int section, View convertView,
                                     ViewGroup parent) {
        LinearLayout layout = null;
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) parent.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) inflator.inflate(
                    R.layout.oa_nzl_main_view_task_model_item_header, null);
        } else {
            layout = (LinearLayout) convertView;
        }
        TextView tv = ((TextView) layout.findViewById(R.id.textItem));
        tv.setText(taskList.get(section).getGroupName());
        tv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "section group"+section, Toast.LENGTH_SHORT).show();
            }
        });
        return layout;
    }


    public final class ViewHolderChild{

        public TextView cName;
        public ImageView cImg;
    }


}
