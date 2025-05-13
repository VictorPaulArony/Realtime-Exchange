-- Sample data for testing
INSERT INTO conversions (from_currency, to_currency, amount, rate, result, timestamp)
VALUES 
    ('USD', 'EUR', 100.0000, 0.850000, 85.0000, CURRENT_TIMESTAMP),
    ('EUR', 'GBP', 50.0000, 0.860000, 43.0000, CURRENT_TIMESTAMP),
    ('GBP', 'JPY', 75.0000, 180.500000, 13537.5000, CURRENT_TIMESTAMP),
    ('USD', 'EUR', 200.0000, 0.850000, 170.0000, CURRENT_TIMESTAMP - INTERVAL '1 day'),
    ('EUR', 'USD', 150.0000, 1.180000, 177.0000, CURRENT_TIMESTAMP - INTERVAL '2 days');
