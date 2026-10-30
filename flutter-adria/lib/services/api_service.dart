import 'dart:convert';

import 'package:http/http.dart' as http;

import '../models/account.dart';
import '../models/transaction.dart';

class ApiService {
  const ApiService({
    this.baseUrl = const String.fromEnvironment(
      'API_BASE_URL',
      defaultValue: 'http://localhost:9999',
    ),
  });

  final String baseUrl;

  Future<List<Account>> getAccounts() {
    return getList('/ACCOUNT-SERVICE/accounts', Account.fromJson);
  }

  Future<List<Transaction>> getTransactions() {
    return getList('/TRANSACTION-SERVICE/transactions', Transaction.fromJson);
  }

  Future<List<T>> getList<T>(
    String path,
    T Function(Map<String, dynamic>) fromJson,
  ) async {
    final response = await http
        .get(
          Uri.parse('$baseUrl$path'),
          headers: {'Accept': 'application/json'},
        )
        .timeout(const Duration(seconds: 15));

    if (response.statusCode < 200 || response.statusCode >= 300) {
      throw Exception('Request failed (${response.statusCode}).');
    }

    final data = jsonDecode(utf8.decode(response.bodyBytes));
    final items = data is List ? data : data['content'] as List;
    return items.map((item) => fromJson(item as Map<String, dynamic>)).toList();
  }
}
