package com.sportx.pk.activities.serviceProviderActivities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.sportx.pk.activities.menu.Menu;
import com.sportx.pk.Adapters.ExpenseCategoryAdapter;
import com.sportx.pk.Misc.Misc;
import com.sportx.pk.Models.ExpenseCategory;
import com.sportx.pk.R;
import com.sportx.pk.SharedPref.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ExpenseCategoryActivity extends Menu {
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
        super.inflateView(R.layout.activity_sp_expense_category);
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
                        if(newCategory.getText().toString().isEmpty())
                        {
                            misc.showToast("Please Enter New Category Name");
                        }
                        else
                        {
                            callAddExpenseCategoryWebservice();
                            dialog.dismiss();
                        }



                    }
                });
                dialog.show();
            }
        });



        expenseCategoryRecycler =  findViewById(R.id.reyclerview_expense_category_list);
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
                .load(misc.ROOT_PATH+"expensecategory/add_expenseCategory")
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
                                misc.showToast("Expense Category Added");
                            }
                            String id=jsonObject1.getString("_id");
                            String name=jsonObject1.getString("name");
                            String serviceProviderEmail=jsonObject1.getString("serviceProviderEmail");
                            categories.add(new ExpenseCategory(id,name,serviceProviderEmail));
                            expenseCategoryAdapter.notifyDataSetChanged();

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
                .load("GET", misc.ROOT_PATH + "expensecategory/get_expenseCategory_by_serviceProvider/" + SharedPref.getEmail())
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
