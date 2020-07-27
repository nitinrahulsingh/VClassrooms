package com.vclassrooms.Adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vclassrooms.Common.AppUtils;
import com.vclassrooms.Common.Constatnts;
import com.vclassrooms.Entity.Question;
import com.vclassrooms.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul on 26,July,2020
 */
public class OnlineExamStartAdapter extends RecyclerView.Adapter<OnlineExamStartAdapter.ViewHolder> {
    Context context;
    public List<Question> onlineExaminationList;
    LayoutInflater inflter;
    private int row_index = -1;
    AppUtils appUtils;
    Constatnts constatnts;
    String strRoleid;
    public OnlineExamStartAdapter(Context context, List<Question> onlineExaminationList) {
        this.context = context;
        this.onlineExaminationList = onlineExaminationList;
    }

    @NonNull
    @Override
    public OnlineExamStartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.onlineexam_student_adapter, parent, false);
        return new OnlineExamStartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OnlineExamStartAdapter.ViewHolder viewHolde, final int position) {
        appUtils=new AppUtils();
        constatnts=new Constatnts();
        strRoleid = appUtils.getStringPrefrences(context, constatnts.SH_APPPREF, constatnts.SH_USERTYPEID);
        Handler mainHandler = new Handler(context.getMainLooper());
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                appUtils.setText(viewHolde.et_question,onlineExaminationList.get(position).getQuestion());
                appUtils.setText(viewHolde.et_optiona, String.valueOf(onlineExaminationList.get(position).getOpt_A()));
                appUtils.setText(viewHolde.et_optionb,onlineExaminationList.get(position).getOpt_B());
                appUtils.setText(viewHolde.et_optionc,onlineExaminationList.get(position).getOpt_C());
                appUtils.setText(viewHolde.et_optiond,onlineExaminationList.get(position).getOpt_D());
                appUtils.setText(viewHolde.Questno_tx, "Q"+String.valueOf(position+1)+".");

                viewHolde.aRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(b){
                            onlineExaminationList.get(viewHolde.getAdapterPosition()).setAnswer(onlineExaminationList.get(viewHolde.getAdapterPosition()).getOpt_A());
                        }
                    }
                });
                viewHolde.bRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(b){
                            onlineExaminationList.get(viewHolde.getAdapterPosition()).setAnswer(onlineExaminationList.get(viewHolde.getAdapterPosition()).getOpt_B());
                        }
                    }
                });
                viewHolde.cRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(b){
                            onlineExaminationList.get(viewHolde.getAdapterPosition()).setAnswer(onlineExaminationList.get(viewHolde.getAdapterPosition()).getOpt_C());
                        }
                    }
                });
                viewHolde.dRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(b){
                            onlineExaminationList.get(viewHolde.getAdapterPosition()).setAnswer(onlineExaminationList.get(viewHolde.getAdapterPosition()).getOpt_D());
                        }
                    }
                });
            }

        };
        mainHandler.post(myRunnable);
    }

    @Override
    public int getItemCount() {
        return onlineExaminationList.size();
    }
    public List<Question> getSubmitedAns()
    {
        return onlineExaminationList;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        EditText et_question,et_optiona,et_optionb,et_optionc,et_optiond;
        TextView Questno_tx;
        RadioButton aRadio,bRadio,cRadio,dRadio;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            et_question=(EditText) itemView.findViewById(R.id.et_question);
            et_optiona=(EditText) itemView.findViewById(R.id.et_optiona);
            et_optionb = (EditText) itemView.findViewById(R.id.et_optionb);
            et_optionc = (EditText) itemView.findViewById(R.id.et_optionc);
            et_optiond = (EditText) itemView.findViewById(R.id.et_optiond);
            aRadio = (RadioButton) itemView.findViewById(R.id.aRadio);
            bRadio = (RadioButton) itemView.findViewById(R.id.bRadio);
            cRadio = (RadioButton) itemView.findViewById(R.id.cRadio);
            dRadio = (RadioButton) itemView.findViewById(R.id.dRadio);
            Questno_tx = (TextView) itemView.findViewById(R.id.Questno_tx);


        }
    }


}
