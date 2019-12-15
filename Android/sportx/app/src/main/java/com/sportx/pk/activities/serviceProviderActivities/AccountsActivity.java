package com.sportx.pk.activities.serviceProviderActivities;

import android.content.Intent;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.View;

import com.sportx.pk.activities.menu.Menu;
import com.sportx.pk.R;

public class AccountsActivity extends Menu implements View.OnClickListener  {
    private CardView expense, expenseCategory,revenue,revenueCategory,report;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.inflateView(R.layout.activity_sp_accounts);
        setTitle("Accounts");

        expense = findViewById(R.id.expense);
        revenue = findViewById(R.id.revenue);
        expenseCategory = findViewById(R.id.expense_category);
        revenueCategory = findViewById(R.id.revenue_category);
        report=findViewById(R.id.report);

        expense.setOnClickListener(this);
        revenue.setOnClickListener(this);
        expenseCategory.setOnClickListener(this);
        revenueCategory.setOnClickListener(this);
        report.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        if(v.getId() == expense.getId()){
            Intent intent = new Intent(this, ExpenseActivity.class);
            startActivity(intent);
            finish();
        }
        else if(v.getId() == revenue.getId()){
            Intent intent = new Intent(this, RevenueActivity.class);
            startActivity(intent);
            finish();
        }
        else if(v.getId() == expenseCategory.getId()){
            Intent intent = new Intent(this, ExpenseCategoryActivity.class);
            startActivity(intent);
            finish();
        }
        else if(v.getId() == revenueCategory.getId()){
            Intent intent = new Intent(this, RevenueCategoryActivity.class);
            startActivity(intent);
            finish();
        }
        else if(v.getId() == report.getId()){
            Intent intent = new Intent(this, AccountsReportActivity.class);
            startActivity(intent);
            finish();
        }
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
