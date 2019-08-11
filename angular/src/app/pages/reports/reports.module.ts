import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ReportsRoutingModule } from './reports-routing.module';
import { TrialBalanceComponent } from './accounts/trial-balance/trial-balance.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MonthlyProfitLossComponent } from './accounts/monthly-profit-loss/monthly-profit-loss.component';
import { BalanceSheetComponent } from './accounts/balance-sheet/balance-sheet.component';
import { CurrencyStockReportComponent } from './currency/currency-stock-report/currency-stock-report.component';
import { DailyPurchaseJournalComponent } from './currency/daily-purchase-journal/daily-purchase-journal.component';
import { DailySalesJournalComponent } from './currency/daily-sales-journal/daily-sales-journal.component';
import { CtrReportComponent } from './currency/ctr-report/ctr-report.component';
import { TotalPurchaseVolumeComponent } from './audit/total-purchase-volume/total-purchase-volume.component';
import { TotalSaleVolumeComponent } from './audit/total-sale-volume/total-sale-volume.component';
import { DailyTransComponent } from './sbp-appendix/daily-trans/daily-trans.component';
import { WeeklyDComponent } from './sbp-fortnightly/weekly-d/weekly-d.component';
import { SbpDailyExposureComponent } from './sbp-fortnightly/sbp-daily-exposure/sbp-daily-exposure.component';
import { SbpDailySaleAndPurchaseComponent } from './sbp-fortnightly/sbp-daily-sale-and-purchase/sbp-daily-sale-and-purchase.component';
import { AnnexureEComponent } from './sbp-statements/annexure-e/annexure-e.component';
import { StatementDComponent } from './sbp-statements/statement-d/statement-d.component';
import { StatementLComponent } from './sbp-statements/statement-l/statement-l.component';
import { StatementDAnnexureComponent } from './sbp-statements/statement-d-annexure/statement-d-annexure.component';
import { StatementFComponent } from './sbp-statements/statement-f/statement-f.component';
import { BranchWiseConsolidatedPositionComponent } from './sbp-statements/branch-wise-consolidated-position/branch-wise-consolidated-position.component';
import { BranchWiseConsolidatedPositionUsdComponent } from './sbp-statements/branch-wise-consolidated-position-usd/branch-wise-consolidated-position-usd.component';

@NgModule({
  // tslint:disable-next-line: max-line-length
  declarations: [TrialBalanceComponent, MonthlyProfitLossComponent, BalanceSheetComponent, CurrencyStockReportComponent, DailyPurchaseJournalComponent, DailySalesJournalComponent, CtrReportComponent, TotalPurchaseVolumeComponent, TotalSaleVolumeComponent, DailyTransComponent, WeeklyDComponent, SbpDailyExposureComponent, SbpDailySaleAndPurchaseComponent, SbpDailyExposureComponent, SbpDailySaleAndPurchaseComponent, WeeklyDComponent, AnnexureEComponent, StatementDComponent, StatementLComponent, StatementDAnnexureComponent, StatementFComponent, BranchWiseConsolidatedPositionComponent, BranchWiseConsolidatedPositionUsdComponent],
  imports: [
    FormsModule,
    ReactiveFormsModule,
    CommonModule,
    ReportsRoutingModule
  ]
})
export class ReportsModule { }
