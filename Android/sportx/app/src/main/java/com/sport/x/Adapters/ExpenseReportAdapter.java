package com.sport.x.Adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sport.x.Misc.Misc;
import com.sport.x.Models.ExpenseReport;
import com.sport.x.R;

import java.util.ArrayList;


public class ExpenseReportAdapter extends RecyclerView.Adapter<ExpenseReportAdapter.ExpenseReportViewHolder> {


    private ArrayList<ExpenseReport> expenses = new ArrayList<>();
    private Context context;
    Misc misc;
    public ExpenseReportAdapter(Context context, ArrayList<ExpenseReport> expenses){
        this.context = context;
        this.expenses = expenses;
        misc = new Misc(context);

    }
    @NonNull
    @Override
    public ExpenseReportAdapter.ExpenseReportViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_expense_report, viewGroup, false);
        return new ExpenseReportAdapter.ExpenseReportViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ExpenseReportAdapter.ExpenseReportViewHolder ExpenseReportViewHolder, int i) {
        ExpenseReportViewHolder.setData(expenses.get(i));


    }
    @Override
    public int getItemCount() {
        return expenses.size();
    }


    public class ExpenseReportViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView category,amount;


        public ExpenseReportViewHolder(@NonNull View itemView) {
            super(itemView);

            category = itemView.findViewById(R.id.category);
            amount=itemView.findViewById(R.id.amount);



        }

        public void setData(final ExpenseReport ExpenseReport)
        {
            final ExpenseReport ExpenseReport1=ExpenseReport;
            category.setText(ExpenseReport1.getName());
            amount.setText(""+ExpenseReport1.getAmount());

        }



        @Override
        public void onClick(View v) {
//            Intent intent = new Intent(context, MessageActivity.class);
//            intent.putExtra("conversationId", expenses.get(getAdapterPosition()).getExpenseId());
//            context.startActivity(intent);
//            ((Activity) context).finish();
        }

    }


}
