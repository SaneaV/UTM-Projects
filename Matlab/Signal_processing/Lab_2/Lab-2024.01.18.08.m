% Создаем вектор времени от 0 до 1 с шагом 0.01
t = 0:0.01:1;

% Генерируем сигнал с использованием функции chirp
% Сигнал представляет собой чирп-сигнал с параметрами по умолчанию
y = 0.8 * chirp(t);

% Строим график сгенерированного сигнала
plot(t, y), grid;

% Добавляем подписи к осям для ясности
xlabel('Время (с)');
ylabel('Амплитуда');
title('Чирп-сигнал');

