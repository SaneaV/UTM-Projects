% Создаем вектор времени от 0 до 50 с шагом 0.03
t = 0:0.03:50;

% Создаем вектор позиций от 0 до 50 с шагом 10
d = [0:10:50];

% Генерируем сигнал с использованием функции pulstran
% Сигнал представляет собой сумму прямоугольных импульсов с шириной 6 на указанных позициях
y = 0.19 * pulstran(t, d, 'rectpuls', 6);

% Строим график сгенерированного сигнала
plot(t, y), grid;

% Добавляем подписи к осям для ясности
xlabel('Время (с)');
ylabel('Амплитуда');
title('Сгенерированный сигнал');
