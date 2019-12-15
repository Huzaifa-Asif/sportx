package com.sportx.pk.Adapters;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;

import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Response;
import com.koushikdutta.ion.Ion;
import com.sportx.pk.Misc.Misc;
import com.sportx.pk.Models.ExpenseCategory;
import com.sportx.pk.R;
import com.sportx.pk.SharedPref.SharedPref;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



public class ExpenseCategoryAdapter extends RecyclerView.Adapter<ExpenseCategoryAdapter.ExpenseCategoryViewHolder> {


    private ArrayList<ExpenseCategory> categories = new ArrayList<>();
    private Context context;
    Misc misc;
    SharedPref sharedPref;
    ImageButton delete;
    public ExpenseCategoryAdapter(Context context, ArrayList<ExpenseCategory> categories){
        this.context = context;
        this.categories = categories;
        misc = new Misc(context);

    }
    @NonNull
    @Override
    public ExpenseCategoryAdapter.ExpenseCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_expense_category, viewGroup, false);
        return new ExpenseCategoryAdapter.ExpenseCategoryViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ExpenseCategoryAdapter.ExpenseCategoryViewHolder ExpenseCategoryViewHolder, int i) {
        ExpenseCategoryViewHolder.setData(categories.get(i));


    }
    @Override
    public int getItemCount() {
        return categories.size();
    }


    public class ExpenseCategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView category;


        public ExpenseCategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            category = itemView.findViewById(R.id.category);
            delete=itemView.findViewById(R.id.delete);


        }

        public void setData(final ExpenseCategory expenseCategory)
        {
            final ExpenseCategory expenseCategory1=expenseCategory;
            category.setText(expenseCategory1.getName());
            delete.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    callDeleteExpenseCategoryWebservice(true,expenseCategory1.getExpenseCategoryId(),expenseCategory1);
                }
            });

        }

        public void callDeleteExpenseCategoryWebservice(boolean isShowDialog,String id,ExpenseCategory expenseCategory) {

            final ExpenseCategory deletedExpenseCategory=expenseCategory;

            Ion.with(context)
                    .load("DELETE", misc.ROOT_PATH + "expensecategory/delete_expenseCategory/" + id)
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
                                    categories.remove(deletedExpenseCategory);
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
