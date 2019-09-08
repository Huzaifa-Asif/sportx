package com.sport.x.ServiceProviderActivities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.sport.x.Adapters.ExpenseAdapter;
import com.sport.x.Adapters.ExpenseCategoryAdapter;
import com.sport.x.Misc.Misc;
import com.sport.x.Models.Expense;
import com.sport.x.Models.ExpenseCategory;
import com.sport.x.R;
import com.sport.x.SharedPref.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ExpenseCategoryActivity extends ServiceProviderMenu {
    Misc misc;
    SharedPref SharedPref;
    Context context;
    ArrayList<ExpenseCategory> categories = new ArrayList<ExpenseCategory>();
    private RecyclerView expenseCategoryRecycler;
    private ExpenseCategoryAdapter expenseCategoryAdapter;
    FloatingActionButton add;
    EditText newCategory;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.inflateView(R.layout.activity_expense_category);
        setTitle("Expense Category");
        context = this;
        SharedPref = new SharedPref(context);
        misc = new Misc(context);
        callExpenseCategoryWebservice(true);
        add=findViewById(R.id.fab);

        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.add_expense_category);
                newCategory = dialog.findViewById(R.id.category);
                Button add=dialog.findViewById(R.id.add);
                add.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        callAddExpenseCategoryWebservice();
                        dialog.dismiss();

                    }
                });
                dialog.show();
            }
        });



        expenseCategoryRecycler =  findViewById(R.id.reyclerview_expense_list);
        expenseCategoryRecycler.addOnScrollListener(new RecyclerView.OnScrollListener(){
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
        expenseCategoryAdapter = new ExpenseCategoryAdapter(this, categories);
        expenseCategoryRecycler.setLayoutManager(new LinearLayoutManager(this));
        expenseCategoryRecycler.setAdapter(expenseCategoryAdapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, AccountsActivity.class);
        startActivity(intent);
        finish();
    }

    private void callAddExpenseCategoryWebservice(){

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Adding Expense Category...");
        pd.setCancelable(false);
        pd.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", newCategory.getText().toString());
        jsonObject.addProperty("serviceProviderEmail", SharedPref.getEmail());



        Ion.with(this)
                .load(misc.ROOT_PATH+"add_expenseCategory")
                .setJsonObjectBody(jsonObject)
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


                        try{
                            JSONObject jsonObject1 = new JSONObject(result.getResult());

                            Boolean status = jsonObject1.getBoolean("status");


                            if (!status) {
                                String Message = jsonObject1.getString("Message");
                                pd.dismiss();
                                misc.showToast(Message);
                                return;
                            }
                            else if (status) {
                                pd.dismiss();
                                misc.showToast("Expense Added");



                            }

                        }
                        catch (JSONException e1) {
                            e1.printStackTrace();
                        }



                    }
                });
    }


    public void callExpenseCategoryWebservice(boolean isShowDialog)
    {


        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Fetching all expense categories");
        pd.setCancelable(false);
        if(categories.size()==0) {
            pd.show();
        }
        final int expenseCategorySize = categories.size();

        Ion.with(this)
                .load("GET", misc.ROOT_PATH + "get_expenseCategory_by_serviceProvider/" + SharedPref.getEmail())
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
                                misc.showToast("No expense Categories Found");
                                pd.dismiss();
                                return;
                            }
                            pd.dismiss();
                            for (int i = 0; i < jsonArray.length(); i++) {


                                JSONObject jsonObjectExpenseCategory = (JSONObject) jsonArray.get(i);

                                String expenseCategoryId = jsonObjectExpenseCategory.getString("_id");
                                String name = jsonObjectExpenseCategory.getString("name");
                                String serviceProviderEmail = jsonObjectExpenseCategory.getString("serviceProviderEmail");
                                categories.add(new ExpenseCategory(expenseCategoryId, name, serviceProviderEmail));

                            }

                            expenseCategoryAdapter.notifyDataSetChanged();


                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
//

                    }

                });
    }


}
