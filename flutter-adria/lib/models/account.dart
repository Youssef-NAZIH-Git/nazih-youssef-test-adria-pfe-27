class Account {
  const Account({
    required this.id,
    required this.customerReference,
    required this.status,
    required this.currency,
    required this.balance,
  });

  final String id;
  final String customerReference;
  final String status;
  final String currency;
  final double balance;

  factory Account.fromJson(Map<String, dynamic> json) {
    return Account(
      id: json['id'] as String,
      customerReference: json['customerReference'] as String,
      status: json['status'] as String,
      currency: json['currency'] as String,
      balance: (json['balance'] as num).toDouble(),
    );
  }
}
