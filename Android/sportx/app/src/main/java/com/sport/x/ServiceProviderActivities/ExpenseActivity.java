package com.sport.x.ServiceProviderActivities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.sport.x.Adapters.ExpenseAdapter;
import com.sport.x.Misc.Misc;
import com.sport.x.Models.Expense;
import com.sport.x.R;
import com.sport.x.SharedPref.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ExpenseActivity extends Menu {

    Misc misc;
    SharedPref SharedPref;
    Context context;
    ArrayList<Expense> expenses = new ArrayList<Expense>();
    private RecyclerView expenseRecycler;
    private ExpenseAdapter expenseAdapter;
    FloatingActionButton add;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.inflateView(R.layout.activity_expense);

        setTitle("Expenses");
        context = this;
        SharedPref = new SharedPref(context);
        misc = new Misc(context);
        callExpenseWebservice(true);
        add=findViewById(R.id.fab);

        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ExpenseActivity.this, AddExpenseActivity.class);
                startActivity(intent);
                finish();
            }
        });



        expenseRecycler =  findViewById(R.id.reyclerview_expense_list);
        expenseRecycler.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                if (dy<0 && !add.isShown())
                    add.show();
                else if(dy>0 && add.isShown())
                    add.hide();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        expenseAdapter = new ExpenseAdapter(this, expenses);
        expenseRecycler.setLayoutManager(new LinearLayoutManager(this));
        expenseRecycler.setAdapter(expenseAdapter);

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, AccountsActivity.class);
        startActivity(intent);
        finish();
    }

    public void callExpenseWebservice(boolean isShowDialog)
    {


        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Fetching all expenses");
        pd.setCancelable(false);
        if(expenses.size()==0) {
            pd.show();
        }
        final int expenseSize = expenses.size();

        Ion.with(this)
                .load("GET", misc.ROOT_PATH + "get_expense_by_serviceProvider/" + SharedPref.getEmail())
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> result) {
                        if (e != null) {
                            pd.dismiss();
                            misc.showToast("Please check your connection");
                            pd.dismiss();
                            return;
                        }

                        try {

                            JSONArray jsonArray = new JSONArray(result.getResult());
                            if (jsonArray.length() < 1) {
                                misc.showToast("No expenses Found");
                                pd.dismiss();
                                return;
                            }
                            pd.dismiss();
                            for (int i = 0; i < jsonArray.length(); i++) {


                                JSONObject jsonObjectExpense = (JSONObject) jsonArray.get(i);

                                String expenseId = jsonObjectExpense.getString("_id");
                                String expenseCategory = jsonObjectExpense.getString("expenseCategory");
                                int amount = jsonObjectExpense.getInt("amount");
                                String serviceProviderEmail = jsonObjectExpense.getString("serviceProviderEmail");
                                String date = jsonObjectExpense.getString("date");
                                String description = jsonObjectExpense.getString("description");
                                expenses.add(new Expense(expenseId, expenseCategory, amount, serviceProviderEmail, date, description));

                            }

                            expenseAdapter.notifyDataSetChanged();


                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
//

                    }

                });
    }






}
