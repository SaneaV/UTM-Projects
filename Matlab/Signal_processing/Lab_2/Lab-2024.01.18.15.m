% Задаем значения параметров
A = 3;                  % Амплитуда синусоиды
omega = 2.1 * pi / 11.3; % Частота синусоиды (в радианах)
n = -10:10;             % Диапазон значений времени n

% Генерируем дискретную синусоиду
y = A * sin(omega * n);

% Строим график сигнала в виде дискретных точек
stem(n, y);

% Добавляем подписи к осям для ясности
xlabel('n');
ylabel('Амплитуда');
title('Дискретная синусоида');
