import 'account.dart';

class Transaction {
  const Transaction({
    required this.id,
    required this.accountId,
    this.account,
    required this.amount,
    required this.currency,
    required this.merchantCategory,
    required this.country,
    required this.createdAt,
  });

  final String id;
  final String accountId;
  final Account? account;
  final double amount;
  final String currency;
  final String merchantCategory;
  final String country;
  final DateTime createdAt;

  factory Transaction.fromJson(Map<String, dynamic> json) {
    return Transaction(
      id: json['id'] as String,
      accountId: json['accountId'] as String,
      account: json['account'] == null
          ? null
          : Account.fromJson(json['account'] as Map<String, dynamic>),
      amount: (json['amount'] as num).toDouble(),
      currency: json['currency'] as String,
      merchantCategory: json['merchantCategory'] as String,
      country: json['country'] as String,
      createdAt: DateTime.parse(json['createdAt'] as String),
    );
  }
}
