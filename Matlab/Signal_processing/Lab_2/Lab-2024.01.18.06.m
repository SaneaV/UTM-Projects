% Создаем вектор времени от 0 до 50 с шагом 0.01
t = 0:0.01:50;

% Создаем вектор позиций от 0 до 50 с шагом 10
d = [0:10:50];

% Генерируем сигнал с использованием функции pulstran
% Сигнал представляет собой сумму треугольных импульсов с длительностью 1 на указанных позициях
yl = 0.3 * pulstran(t, d, 'tripuls', 1);

% Строим график сгенерированного сигнала
plot(t, yl), grid;

% Добавляем подписи к осям для ясности
xlabel('Время (с)');
ylabel('Амплитуда');
title('Сигнал из треугольных импульсов');

