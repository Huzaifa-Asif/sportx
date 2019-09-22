package com.sport.x.Activities.ServiceProviderActivities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.sport.x.Adapters.ExpenseReportAdapter;
import com.sport.x.Adapters.RevenueReportAdapter;
import com.sport.x.Misc.Misc;
import com.sport.x.Models.ExpenseReport;
import com.sport.x.Models.RevenueReport;
import com.sport.x.R;
import com.sport.x.SharedPref.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class AccountsReportActivity extends Menu implements View.OnClickListener {
    Misc misc;
    SharedPref SharedPref;
    Context context;
    ArrayList<ExpenseReport> expenses = new ArrayList<ExpenseReport>();
    ArrayList<RevenueReport> revenues = new ArrayList<RevenueReport>();
    TextView expense,revenue,net;
    EditText startDate,endDate;
    Button submit;
    ImageButton btnStart,btnEnd;
    CardView datePicker;
    private RecyclerView expenseRecycler,revenueRecycler;
    private ExpenseReportAdapter expenseAdapter;
    private RevenueReportAdapter revenueAdapter;
    LinearLayout report;
    private int totalRevenue,totalExpense;
    String start,end;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.inflateView(R.layout.activity_accounts_report);
        setTitle("Accounts Report");
        context = this;
        SharedPref = new SharedPref(context);
        misc = new Misc(context);

        revenue=findViewById(R.id.revenue);
        expense=findViewById(R.id.expense);
        net=findViewById(R.id.net);
        startDate=findViewById(R.id.startDate);
        endDate=findViewById(R.id.endDate);
        btnStart=findViewById(R.id.btn_startDate);
        btnEnd=findViewById(R.id.btn_endDate);
        submit=findViewById(R.id.submit);
        datePicker=findViewById(R.id.datePicker);
        report=findViewById(R.id.report);
        btnEnd.setOnClickListener(this);
        btnStart.setOnClickListener(this);
        submit.setOnClickListener(this);
        report.setVisibility(View.GONE);


        expenseRecycler =  findViewById(R.id.reyclerview_expense_list);
        revenueRecycler =  findViewById(R.id.reyclerview_revenue_list);


        expenseAdapter = new ExpenseReportAdapter(this, expenses);
        expenseRecycler.setLayoutManager(new LinearLayoutManager(this));
        expenseRecycler.setAdapter(expenseAdapter);


        revenueAdapter = new RevenueReportAdapter(this, revenues);
        revenueRecycler.setLayoutManager(new LinearLayoutManager(this));
        revenueRecycler.setAdapter(revenueAdapter);





    }


    public void callExpenseReportWebservice()
    {


        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Fetching all reports");
        pd.setCancelable(false);
        if(expenses.size()==0) {
            pd.show();
        }

        Ion.with(this)
                .load("GET", misc.ROOT_PATH + "expense/expense_report?email=" + SharedPref.getEmail()+"&start="+start+"&end="+end)
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
                                misc.showToast("No Expenses Found");
                                pd.dismiss();
                                return;
                            }
                            pd.dismiss();
                            for (int i = 0; i < jsonArray.length(); i++) {


                                JSONObject jsonObjectExpense = (JSONObject) jsonArray.get(i);

                                String name = jsonObjectExpense.getString("_id");
                                int amount = jsonObjectExpense.getInt("categoryExpense");
                                totalExpense+=amount;
                                expenses.add(new ExpenseReport(name, amount));


                            }
                            setTextViews();
                            expenseAdapter.notifyDataSetChanged();


                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
//

                    }

                });
    }

    public void callRevenueReportWebservice()
    {


        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Fetching all reports");
        pd.setCancelable(false);
        if(revenues.size()==0) {
            pd.show();
        }


        Ion.with(this)
                .load("GET", misc.ROOT_PATH + "revenue/revenue_report?email=" + SharedPref.getEmail()+"&start="+start+"&end="+end)
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
                                misc.showToast("No Revenues Found");
                                pd.dismiss();
                                return;
                            }
                            pd.dismiss();
                            for (int i = 0; i < jsonArray.length(); i++) {


                                JSONObject jsonObjectExpense = (JSONObject) jsonArray.get(i);

                                String name = jsonObjectExpense.getString("_id");
                                int amount = jsonObjectExpense.getInt("categoryRevenue");
                                totalRevenue+=amount;
                                revenues.add(new RevenueReport(name, amount));

                            }
                            setTextViews();
                            revenueAdapter.notifyDataSetChanged();



                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
//

                    }

                });
    }

private void setTextViews()
{
    int calculatedNet=Math.abs(totalRevenue-totalExpense);
    expense.setText("Total Expense: "+totalExpense);
    revenue.setText("Total Revenue: "+totalRevenue);
    if(totalExpense>totalRevenue)
    {
        net.setText("Loss: "+calculatedNet);
    }
    else if(totalExpense<totalRevenue)
    {
        net.setText("Profit: "+calculatedNet);
    }
    else
    {
        net.setText("Net: 0");
    }
}


    public void onClick(View v) {

        if(v.getId() == btnStart.getId()) {
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DialogTheme,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            String dayString,monthString,yearString;
                            if(dayOfMonth<10)
                            {
                                dayString="0"+dayOfMonth;
                            }
                            else
                            {
                                dayString=""+dayOfMonth;
                            }
                            if(monthOfYear+1<10)
                            {
                                monthString="0"+(monthOfYear+1);
                            }
                            else
                            {
                                monthString=""+(monthOfYear+1);
                            }
                            yearString=""+year;
                            startDate.setText(dayString + "-" +monthString + "-" + yearString);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        else if(v.getId() == btnEnd.getId()) {
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DialogTheme,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            String dayString,monthString,yearString;
                            if(dayOfMonth<10)
                            {
                                dayString="0"+dayOfMonth;
                            }
                            else
                            {
                                dayString=""+dayOfMonth;
                            }
                            if(monthOfYear+1<10)
                            {
                                monthString="0"+(monthOfYear+1);
                            }
                            else
                            {
                                monthString=""+(monthOfYear+1);
                            }
                            yearString=""+year;
                            endDate.setText(dayString + "-" +monthString + "-" + yearString);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        else if(v.getId() == submit.getId())
        {
            start=startDate.getText().toString();
            end=endDate.getText().toString();
            int flag=start.compareTo(end);
            if(flag>0)
            {
                misc.showToast("Please Select Dates Properly");
            }
            else
            {
                totalExpense=0;
                totalRevenue=0;
                revenues.clear();
                expenses.clear();
                report.setVisibility(View.VISIBLE);
                datePicker.setVisibility(View.GONE);
                callExpenseReportWebservice();
                callRevenueReportWebservice();

            }

        }


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, AccountsActivity.class);
        startActivity(intent);
        finish();
    }

}
