package com.sport.x.ServiceProviderActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import com.sport.x.LoginActivity;
import com.sport.x.R;
import com.sport.x.RegisterActivity;

public class AccountsActivity extends Menu implements View.OnClickListener  {
    private CardView expense, expenseCategory,revenue,revenueCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.inflateView(R.layout.activity_accounts);
        setTitle("Accounts");

        expense = findViewById(R.id.expense);
        revenue = findViewById(R.id.revenue);
        expenseCategory = findViewById(R.id.expense_category);
        revenueCategory = findViewById(R.id.revenue_category);

        expense.setOnClickListener(this);
        revenue.setOnClickListener(this);
        expenseCategory.setOnClickListener(this);
        revenueCategory.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        if(v.getId() == expense.getId()){
            Intent intent = new Intent(this, ExpenseActivity.class);
            startActivity(intent);
            finish();
        }
        if(v.getId() == revenue.getId()){
            Intent intent = new Intent(this, RevenueActivity.class);
            startActivity(intent);
            finish();
        }
        if(v.getId() == expenseCategory.getId()){
            Intent intent = new Intent(this, ExpenseCategoryActivity.class);
            startActivity(intent);
            finish();
        }
        if(v.getId() == revenueCategory.getId()){
            Intent intent = new Intent(this, RevenueCategoryActivity.class);
            startActivity(intent);
            finish();
        }
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ServiceHomeActivity.class);
        startActivity(intent);
        finish();
    }
}
