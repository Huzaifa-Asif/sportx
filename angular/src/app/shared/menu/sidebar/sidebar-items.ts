// Menu
export interface Menu {
  path?: string;
  title?: string;
  icon?: string;
  type?: string;
  headTitle?: string,
  badgeType?: string;
  badgeValue?: string;
  children?: Menu[];
}

export const MenuItemsAdmin: Menu[] = [
  {
    path: '/dashboard/admin', title: 'Dashboard', icon: 'icon-desktop', type: 'link'
  },
  {
    title: 'Management', icon: 'icon-package', type: 'sub', children: [
      { path: '/currencies/list', title: 'Currency Management', type: 'link' },
      { path: '/accounts/list', title: 'Account Management', type: 'link' },
      { path: '/users/list', title: 'Users Management', type: 'link' }
    ]
  },
  // {
  //   path: '/dashboard/admin', title: 'GoAML(GoAML-SBPCSV-Body)', icon: 'icon-files', type: 'link'
  // },
  // {
  //   path: '/dashboard/admin', title: 'SBP CSV(SBPCSV)', icon: 'icon-files', type: 'link'
  // },
  {
    headTitle: 'Reports'
  },
  {
    title: 'Accounts Reports', icon: 'icon-files', type: 'sub', children: [
      { path: '/report/trial-balance', title: 'Trial Balance', type: 'link' },
      { path: '/report/monthly-profit-loss', title: 'Monthly Profit & Loss', type: 'link' },
      { path: '/report/balance-sheet', title: 'Balance Sheet', type: 'link' },
    ]
  },
  {
    title: 'Currency Reports', icon: 'icon-files', type: 'sub', children: [
      { path: '/report/currency-stock-report', title: 'Currency Stock Report', type: 'link' },
      { path: '/report/daily-purchase-journal', title: 'Daily Purchase Journal', type: 'link' },
      { path: '/report/daily-sales-journal', title: 'Daily Sales Journal', type: 'link' },
      { path: '/report/ctr-report', title: 'CTR Report', type: 'link' },
    ]
  },
  {
    title: 'SBP Fortnightly', icon: 'icon-files', type: 'sub', children: [
      { path: '/report/weekly-d', title: 'Weekly D', type: 'link' },
      { path: '/report/sbp-daily-exposure', title: ' SBP Daily Exposure Report', type: 'link' },
      { path: '/report/sbp-daily-sale-and-purchase', title: 'SBP Daily Sale and Purchase', type: 'link' },
    ]
  },
  {
    title: 'SBP Statements', icon: 'icon-files', type: 'sub', children: [
      { path: '/report/annexure-e', title: 'Annexure E Report', type: 'link' },
      { path: '/report/statement-d', title: ' Statement-D Report', type: 'link' },
      { path: '/report/statement-l', title: 'Statement-L Report', type: 'link' },
      { path: '/report/statement-d-annexure', title: 'Statement D (Annexure)', type: 'link' },
      { path: '/report/statement-f', title: 'Statement-F', type: 'link' },
      // tslint:disable-next-line: max-line-length
      { path: '/report/branch-wise-consolidated-position-usd', title: 'Branch Wise Consolidated Position USD', type: 'link' },
      { path: '/report/branch-wise-consolidated-position', title: 'Branch Wise Consolidated Position', type: 'link' },
    ]
  },
  {
    title: 'Audit Report', icon: 'icon-files', type: 'sub', children: [
      { path: '/report/total-purchase-volume', title: 'Total Purchase Volume', type: 'link' },
      { path: '/report/total-sale-volume', title: ' Total Sale Volume', type: 'link' },
    ]
  },
  {
    title: 'SBP Appendix', icon: 'icon-files', type: 'sub', children: [
      { path: '/report/daily-trans', title: 'Daily Trans', type: 'link' },
    ]
  }
]


export const MenuItemsCashier: Menu[] = [
  {
    path: '/dashboard/cashier', title: 'Dashboard', icon: 'icon-desktop', type: 'link'
  },
  {
    path: '/currencies/list', title: 'Rates', icon: 'icon-bar-chart', type: 'link'
  },
  {
    headTitle: 'Currency'
  },
  {
    path: '/transactions/bill', title: 'Bill Transaction', icon: 'icon-direction-alt', type: 'link'
  },
  {
    path: '/voucher/generate', title: 'Transfer Voucher', icon: 'icon-agenda', type: 'link'
  },
  {
    headTitle: 'Expenses'
  },
  {
    path: '/voucher/generate', title: 'Double Entry', icon: 'icon-agenda', type: 'link'
  },
  {
    path: '/voucher/generate', title: 'Journal Voucher', icon: 'icon-agenda', type: 'link'
  }
];
