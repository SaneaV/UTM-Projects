% Простой метод усреднения

% Параметры сигнала
R = 100; % Длина сигнала
m = 0:R-1;

% Генерация сигнала s[n]
s = sin(0.1 * m) + 0.5 * cos(0.3 * m);

% Генерация случайного шума d[n]
d = 0.2 * randn(1, R);

% Искаженный сигнал x[n]
x = s + d;

% Алгоритм усреднения
y = (circshift(x, [0, -1]) + x + circshift(x, [0, 1])) / 3;

% Отображение результатов
subplot(2,1,1);
plot(m, s, 'g--', m, x, 'b-.');
xlabel('Время n'); ylabel('Амплитуда');
legend('Исходный сигнал s[n]', 'Искаженный сигнал x[n]');
title('Исходный сигнал и искаженный сигнал');

subplot(2,1,2);
plot(m, x, 'b-.', m, y, 'r-');
xlabel('Время n'); ylabel('Амплитуда');
legend('Искаженный сигнал x[n]', 'Сглаженный сигнал y[n]');
title('Искаженный сигнал и сглаженный сигнал');
