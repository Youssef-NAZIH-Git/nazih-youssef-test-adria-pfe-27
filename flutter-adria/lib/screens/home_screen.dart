import 'package:flutter/material.dart';

import '../models/account.dart';
import '../models/transaction.dart';
import '../services/api_service.dart';
import '../widgets/api_list.dart';

class HomeScreen extends StatelessWidget {
  const HomeScreen({super.key});

  static const _api = ApiService();

  @override
  Widget build(BuildContext context) {
    return DefaultTabController(
      length: 2,
      child: Scaffold(
        appBar: AppBar(
          title: const Text('Adria'),
          bottom: const TabBar(
            tabs: [
              Tab(
                icon: Icon(Icons.account_balance_wallet_outlined),
                text: 'Accounts',
              ),
              Tab(icon: Icon(Icons.swap_horiz), text: 'Transactions'),
            ],
          ),
        ),
        body: SafeArea(
          child: TabBarView(
            children: [
              ApiList<Account>(
                loadItems: _api.getAccounts,
                emptyMessage: 'No accounts found.',
                itemBuilder: (context, account) => ListTile(
                  leading: const Icon(Icons.account_balance_outlined),
                  title: Text(account.customerReference),
                  subtitle: Text('${account.status}\n${account.id}'),
                  isThreeLine: true,
                  trailing: Text(
                    '${account.balance.toStringAsFixed(2)} ${account.currency}',
                    style: Theme.of(context).textTheme.titleSmall,
                  ),
                ),
              ),
              ApiList<Transaction>(
                loadItems: _api.getTransactions,
                emptyMessage: 'No transactions found.',
                itemBuilder: (context, transaction) {
                  final date = transaction.createdAt.toLocal();
                  final localizations = MaterialLocalizations.of(context);
                  return ListTile(
                    leading: const Icon(Icons.receipt_long_outlined),
                    title: Text(transaction.merchantCategory),
                    subtitle: Text(
                      '${transaction.country} · ${localizations.formatShortDate(date)} '
                      '${localizations.formatTimeOfDay(TimeOfDay.fromDateTime(date))}\n'
                      '${transaction.account?.customerReference ?? transaction.accountId}',
                    ),
                    isThreeLine: true,
                    trailing: Text(
                      '${transaction.amount.toStringAsFixed(2)} ${transaction.currency}',
                      style: Theme.of(context).textTheme.titleSmall,
                    ),
                  );
                },
              ),
            ],
          ),
        ),
      ),
    );
  }
}
