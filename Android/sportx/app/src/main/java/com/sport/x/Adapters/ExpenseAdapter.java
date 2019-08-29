package com.sport.x.Adapters;

import android.app.Dialog;
import android.support.v7.widget.RecyclerView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Response;
import com.sport.x.Models.Expense;
import com.koushikdutta.ion.Ion;
import com.sport.x.Misc.Misc;
import com.sport.x.R;
import com.sport.x.SharedPref.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {


    private ArrayList<Expense> expenses = new ArrayList<>();
    private Context context;
    Misc misc;
    SharedPref sharedPref;
    ImageButton details;
    public ExpenseAdapter(Context context, ArrayList<Expense> expenses){
        this.context = context;
        this.expenses = expenses;
        misc = new Misc(context);

    }
    @NonNull
    @Override
    public ExpenseAdapter.ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_expense, viewGroup, false);
        return new ExpenseAdapter.ExpenseViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ExpenseAdapter.ExpenseViewHolder ExpenseViewHolder, int i) {
        ExpenseViewHolder.setData(expenses.get(i));

    }
    @Override
    public int getItemCount() {
        return expenses.size();
    }


    public class ExpenseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView category,amount,date;


        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);

            category = itemView.findViewById(R.id.category);
            amount = itemView.findViewById(R.id.amount);
            date = itemView.findViewById(R.id.date);
            details=itemView.findViewById(R.id.details);

            details.setOnClickListener(this);

        }

        public void setData(final Expense expense)
        {
            final Expense expenseDetails=expense;
            category.setText(expense.getExpenseCategory());
            amount.setText(""+expense.getAmount());
            date.setText(expense.getDate());
            details.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v)
                {
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.expense_details);
                    TextView expenseCategory = dialog.findViewById(R.id.category);
                    expenseCategory.setText(expenseDetails.getExpenseCategory());
                    TextView amount = dialog.findViewById(R.id.amount);
                    amount.setText(""+expenseDetails.getAmount());
                    TextView date = dialog.findViewById(R.id.date);
                    date.setText(expenseDetails.getDate());
                    TextView description = dialog.findViewById(R.id.description);
                    description.setText(expenseDetails.getDescription());
                    Button delete=dialog.findViewById(R.id.delete);
                    delete.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            callDeleteExpenseWebservice(true,expenseDetails.getExpenseId(),expense);
                            dialog.dismiss();

                        }
                    });
                    dialog.show();
                }
            });

        }

        public void callDeleteExpenseWebservice(boolean isShowDialog,String id,Expense expense) {

            final Expense deletedExpense=expense;

            Ion.with(context)
                    .load("DELETE", misc.ROOT_PATH + "delete_expense/" + id)
                    .asString()
                    .withResponse()
                    .setCallback(new FutureCallback<Response<String>>() {
                        @Override
                        public void onCompleted(Exception e, Response<String> result) {
                            if (e != null) {

                                misc.showToast("Please check your connection");

                                return;
                            }

                            try {
                                JSONObject jsonObjectExpenseDeleted = new JSONObject(result.getResult());
                                Boolean status = jsonObjectExpenseDeleted.getBoolean("status");
                                if(status) {
                                    expenses.remove(deletedExpense);
                                    notifyDataSetChanged();
                                }
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
//

                        }

                    });
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
