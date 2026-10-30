import 'package:flutter/material.dart';

class ApiList<T> extends StatefulWidget {
  const ApiList({
    super.key,
    required this.loadItems,
    required this.itemBuilder,
    this.emptyMessage = 'No items found.',
  });

  final Future<List<T>> Function() loadItems;
  final Widget Function(BuildContext, T) itemBuilder;
  final String emptyMessage;

  @override
  State<ApiList<T>> createState() => _ApiListState<T>();
}

class _ApiListState<T> extends State<ApiList<T>> {
  List<T> _items = [];
  bool _loading = true;
  Object? _error;

  @override
  void initState() {
    super.initState();
    _load();
  }

  Future<void> _load() async {
    setState(() {
      _loading = true;
      _error = null;
    });

    try {
      final items = await widget.loadItems();
      if (!mounted) return;
      setState(() => _items = items);
    } catch (error) {
      if (!mounted) return;
      setState(() => _error = error);
    } finally {
      if (mounted) setState(() => _loading = false);
    }
  }

  @override
  Widget build(BuildContext context) {
    if (_loading) {
      return const Center(child: CircularProgressIndicator());
    }

    if (_error != null) {
      return Center(
        child: Padding(
          padding: const EdgeInsets.all(24),
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              const Text('Could not load items.'),
              const SizedBox(height: 8),
              Text('$_error', textAlign: TextAlign.center),
              const SizedBox(height: 16),
              FilledButton(onPressed: _load, child: const Text('Retry')),
            ],
          ),
        ),
      );
    }

    return RefreshIndicator(
      onRefresh: _load,
      child: _items.isEmpty
          ? LayoutBuilder(
              builder: (context, constraints) => ListView(
                physics: const AlwaysScrollableScrollPhysics(),
                children: [
                  SizedBox(
                    height: constraints.maxHeight,
                    child: Center(child: Text(widget.emptyMessage)),
                  ),
                ],
              ),
            )
          : ListView.separated(
              physics: const AlwaysScrollableScrollPhysics(),
              padding: const EdgeInsets.symmetric(vertical: 8),
              itemCount: _items.length,
              separatorBuilder: (context, index) => const Divider(height: 1),
              itemBuilder: (context, index) =>
                  widget.itemBuilder(context, _items[index]),
            ),
    );
  }
}
