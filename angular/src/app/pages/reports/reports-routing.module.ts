import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { TrialBalanceComponent } from './accounts/trial-balance/trial-balance.component';
import { MonthlyProfitLossComponent } from './accounts/monthly-profit-loss/monthly-profit-loss.component';
import { BalanceSheetComponent } from './accounts/balance-sheet/balance-sheet.component';
import { CurrencyStockReportComponent } from './currency/currency-stock-report/currency-stock-report.component';
import { DailyPurchaseJournalComponent } from './currency/daily-purchase-journal/daily-purchase-journal.component';
import { DailySalesJournalComponent } from './currency/daily-sales-journal/daily-sales-journal.component';
import { CtrReportComponent } from './currency/ctr-report/ctr-report.component';
import { TotalPurchaseVolumeComponent } from './audit/total-purchase-volume/total-purchase-volume.component';
import { TotalSaleVolumeComponent } from './audit/total-sale-volume/total-sale-volume.component';
import { DailyTransComponent } from './sbp-appendix/daily-trans/daily-trans.component';
import { SbpDailyExposureComponent } from './sbp-fortnightly/sbp-daily-exposure/sbp-daily-exposure.component';
import { SbpDailySaleAndPurchaseComponent } from './sbp-fortnightly/sbp-daily-sale-and-purchase/sbp-daily-sale-and-purchase.component';
import { WeeklyDComponent } from './sbp-fortnightly/weekly-d/weekly-d.component';
import { AnnexureEComponent } from './sbp-statements/annexure-e/annexure-e.component';
import { StatementDComponent } from './sbp-statements/statement-d/statement-d.component';
import { StatementLComponent } from './sbp-statements/statement-l/statement-l.component';
import { StatementDAnnexureComponent } from './sbp-statements/statement-d-annexure/statement-d-annexure.component';
import { StatementFComponent } from './sbp-statements/statement-f/statement-f.component';
import { BranchWiseConsolidatedPositionUsdComponent } from './sbp-statements/branch-wise-consolidated-position-usd/branch-wise-consolidated-position-usd.component';
import { BranchWiseConsolidatedPositionComponent } from './sbp-statements/branch-wise-consolidated-position/branch-wise-consolidated-position.component';

const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'trial-balance',
        component: TrialBalanceComponent
      },
      {
        path: 'monthly-profit-loss',
        component: MonthlyProfitLossComponent
      },
      {
        path: 'balance-sheet',
        component: BalanceSheetComponent
      },
      {
        path: 'currency-stock-report',
        component: CurrencyStockReportComponent
      },
      {
        path: 'daily-purchase-journal',
        component: DailyPurchaseJournalComponent
      },
      {
        path: 'daily-sales-journal',
        component: DailySalesJournalComponent
      },
      {
        path: 'ctr-report',
        component: CtrReportComponent
      },
      {
        path: 'total-purchase-volume',
        component: TotalPurchaseVolumeComponent
      },
      {
        path: 'total-sale-volume',
        component: TotalSaleVolumeComponent
      },
      {
        path: 'daily-trans',
        component: DailyTransComponent
      },
      {
        path: 'sbp-daily-exposure',
        component: SbpDailyExposureComponent
      },
      {
        path: 'sbp-daily-sale-and-purchase',
        component: SbpDailySaleAndPurchaseComponent
      },
      {
        path: 'weekly-d',
        component: WeeklyDComponent
      },
      {
        path: 'annexure-e',
        component: AnnexureEComponent
      },
      {
        path: 'statement-d',
        component: StatementDComponent
      },
      {
        path: 'statement-l',
        component: StatementLComponent
      },
      {
        path: 'statement-d-annexure',
        component: StatementDAnnexureComponent
      },
      {
        path: 'statement-f',
        component: StatementFComponent
      },
      {
        path: 'branch-wise-consolidated-position-usd',
        component: BranchWiseConsolidatedPositionUsdComponent
      },
      {
        path: 'branch-wise-consolidated-position',
        component: BranchWiseConsolidatedPositionComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ReportsRoutingModule { }
