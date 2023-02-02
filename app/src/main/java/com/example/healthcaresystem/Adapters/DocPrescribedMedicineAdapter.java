package com.example.healthcaresystem.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.healthcaresystem.R;

import org.json.JSONArray;
import org.json.JSONObject;


public class DocPrescribedMedicineAdapter extends RecyclerView.Adapter<DocPrescribedMedicineAdapter.ViewHolder>{
    private Context context;
    private JSONArray data;
    private int type;
    private LayoutInflater inflater;


    public DocPrescribedMedicineAdapter(Context context, JSONArray data, LayoutInflater inflater, int type) {
        this.context = context;
        this.data = data;
        this.inflater = inflater;
        this.type = type;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public DocPrescribedMedicineAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_doc_prescribed_medicine,
                parent,false);
        return new DocPrescribedMedicineAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull DocPrescribedMedicineAdapter.ViewHolder holder, int position) {
        try{
            JSONObject x = data.getJSONObject(position);
            holder.diagnosis.setText(x.getString("diagnosis"));
            if (x.getString("advice").equals("")){
                holder.advice.setVisibility(View.GONE);
            }
            else{
                holder.advice.setText("Advice : " + x.getString("advice"));
            }
            if (type==1){
                holder.prescribedTo.setText("Prescribed To : "+x.getString("prescribed_to"));
                holder.orderNow.setVisibility(View.GONE);
            }else{
                holder.prescribedTo.setText("Prescribed By : "+x.getString("prescribed_by"));
                holder.orderNow.setVisibility(View.GONE);
            }

            holder.orderNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            holder.prescribedOn.setText("Prescribed On : "+x.getString("date"));

            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.view.getText().equals("View Details")){
                        holder.view.setText("Hide Details");
                        holder.dynamicAddMedicineLayout.setVisibility(View.VISIBLE);
                    }
                    else{
                        holder.view.setText("View Details");
                        holder.dynamicAddMedicineLayout.setVisibility(View.GONE);
                    }
                }
            });

            JSONArray y = x.getJSONArray("data");

            for (int i=0;i<y.length();i++){

                View medicine = inflater.inflate(R.layout.dynamic_doc_prescribed_medicine,null,
                        false);

                JSONObject z = y.getJSONObject(i);
                TextView medicineNumber = medicine.findViewById(R.id.dDocMedicineNumber);
                TextView medicineType = medicine.findViewById(R.id.dDocMedicinetype);
                TextView medicineName = medicine.findViewById(R.id.dDocMedicineName);
                TextView tablets = medicine.findViewById(R.id.dDocMedicineTablets);
                TextView duration = medicine.findViewById(R.id.dDocMedicineDuration);
                TextView beforeBreakFast = medicine.findViewById(R.id.dDocMedicineBeforeBreakFast);
                TextView afterBreakFast = medicine.findViewById(R.id.dDocMedicineAfterBreakFast);
                TextView beforeLunch = medicine.findViewById(R.id.dDocMedicineBeforeLunch);
                TextView afterLunch = medicine.findViewById(R.id.dDocMedicineAfterLunch);
                TextView beforeDinner = medicine.findViewById(R.id.dDocMedicineBeforeDinner);
                TextView afterDinner = medicine.findViewById(R.id.dDocMedicineAfterDinner);
                TextView evening = medicine.findViewById(R.id.dDocMedicineEvening);

                medicineNumber.setText("Medicine " + (i+1));

                String temp = z.getString("medicine_type");
                if (temp.equals("Capsule") || temp.equals("Tablet")){
                    temp = " "+temp;
                }
                else{
                    temp = " ml";
                }

                medicineType.setText("Medicine Type : "+z.getString("medicine_type"));
                medicineName.setText("Medicine Name : "+z.getString("medicine_name"));
                if (z.getInt("tablets")>1){
                    tablets.setText(z.getString("medicine_type")+" : "+z.getInt("tablets")+temp+"s");
                }
                else{
                    tablets.setText(z.getString("medicine_type")+" : "+z.getInt("tablets")+temp);
                }
                if (z.getInt("duration")>1){
                    duration.setText("Duration : "+z.getInt("duration")+" days");
                }
                else{
                    duration.setText("Duration : "+z.getInt("duration")+" day");
                }

                if (z.getDouble("before_breakfast")>0){
                    if (z.getDouble("before_breakfast")>1 && !temp.equals(" ml")){
                        beforeBreakFast.setText("Before BreakFast : "+z.getDouble(
                                "before_breakfast")+temp+"s");
                    }
                    else{
                        beforeBreakFast.setText("Before BreakFast : "+z.getDouble(
                                "before_breakfast")+temp);
                    }
                }else{
                    beforeBreakFast.setVisibility(View.GONE);
                }

                if (z.getDouble("after_breakfast")>0){
                    if (z.getDouble("after_breakfast")>1 && !temp.equals(" ml")){
                        afterBreakFast.setText("After BreakFast : "+z.getDouble(
                                "after_breakfast")+temp+"s");
                    }
                    else{
                        afterBreakFast.setText("After BreakFast : "+z.getDouble(
                                "after_breakfast")+temp);
                    }
                }else{
                    afterBreakFast.setVisibility(View.GONE);
                }

                if (z.getDouble("before_lunch")>0){
                    if (z.getDouble("before_lunch")>1 && !temp.equals(" ml")){
                        beforeLunch.setText("Before Lunch : "+z.getDouble(
                                "before_lunch")+temp+"s");
                    }
                    else{
                        beforeLunch.setText("Before Lunch : "+z.getDouble(
                                "before_lunch")+temp);
                    }
                }else{
                    beforeLunch.setVisibility(View.GONE);
                }

                if (z.getDouble("after_lunch")>0){
                    if (z.getDouble("after_lunch")>1 && !temp.equals(" ml")){
                        afterLunch.setText("After Lunch : "+z.getDouble(
                                "after_lunch")+temp+"s");
                    }
                    else{
                        afterLunch.setText("After Lunch : "+z.getDouble(
                                "after_lunch")+temp);
                    }
                }else{
                    afterLunch.setVisibility(View.GONE);
                }

                if (z.getDouble("before_dinner")>0){
                    if (z.getDouble("before_dinner")>1 && !temp.equals(" ml")){
                        beforeDinner.setText("Before Dinner : "+z.getDouble(
                                "before_dinner")+temp+"s");
                    }
                    else{
                        beforeDinner.setText("Before Dinner : "+z.getDouble(
                                "before_dinner")+temp);
                    }
                }else{
                    beforeDinner.setVisibility(View.GONE);
                }

                if (z.getDouble("after_dinner")>0){
                    if (z.getDouble("after_dinner")>1 && !temp.equals(" ml")){
                        afterDinner.setText("After Dinner : "+z.getDouble(
                                "after_dinner")+temp+"s");
                    }
                    else{
                        afterDinner.setText("After Dinner : "+z.getDouble(
                                "after_dinner")+temp);
                    }
                }else{
                    afterDinner.setVisibility(View.GONE);
                }

                if (z.getDouble("evening")>0){
                    if (z.getDouble("evening")>1 && !temp.equals(" ml")){
                        evening.setText("Evening : "+z.getDouble(
                                "evening")+temp+"s");
                    }
                    else{
                        evening.setText("Evening : "+z.getDouble(
                                "evening")+temp);
                    }
                }else{
                    evening.setVisibility(View.GONE);
                }
                holder.dynamicAddMedicineLayout.addView(medicine);
            }


        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return data.length();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView diagnosis, advice, prescribedTo, prescribedOn;
        public LinearLayout dynamicAddMedicineLayout;
        public Button view, orderNow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            diagnosis = itemView.findViewById(R.id.rcDocPresDiagnosis);
            advice = itemView.findViewById(R.id.rcDocPresAdvice);
            prescribedTo = itemView.findViewById(R.id.rcDocPresPrescribedTo);
            prescribedOn = itemView.findViewById(R.id.rcDocPresPrescribedOn);
            dynamicAddMedicineLayout = itemView.findViewById(R.id.rcDocPresDynamicAddMedicineLayout);
            view = itemView.findViewById(R.id.rcDocPresView);
            orderNow = itemView.findViewById(R.id.rcDocPresOrderNow);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
