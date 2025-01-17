% Функция для применения алгоритма Савицкого-Голея
% signal - исходный сигнал
% window_size - размер окна для аппроксимации
% degree - порядок полинома для аппроксимации

function smoothed_signal = savitzkyGolay(signal, window_size, degree)
    half_win = (window_size - 1) / 2;
    smoothed_signal = zeros(size(signal));

    % Проход по каждой точке сигнала
    for i = 1:numel(signal)
        start_idx = max(1, i - half_win);
        end_idx = min(numel(signal), i + half_win);

        % Аппроксимация полиномом
        p = polyfit(start_idx:end_idx, signal(start_idx:end_idx), degree);

        % Вычисление значения аппроксимации в текущей точке
        smoothed_signal(i) = polyval(p, i);
    end
end

% Параметры сигнала
t = linspace(0, 1, 100);
signal = sin(2*pi*5*t) + 0.5*cos(2*pi*10*t) + 0.2*randn(size(t));

% Размер окна и порядок полинома
window_size = 11;
polynomial_degree = 3;

% Применение алгоритма Савицкого-Голея
smoothed_signal = savitzkyGolay(signal, window_size, polynomial_degree);

% Отображение результатов
figure;
plot(t, signal, 'b-', 'LineWidth', 1.5);
hold on;
plot(t, smoothed_signal, 'r-', 'LineWidth', 1.5);
legend('Исходный сигнал', 'Сглаженный сигнал');
xlabel('Время');
ylabel('Амплитуда');
title('Сглаживание сигнала с использованием фильтра Савицкого-Голея (Ручная реализация)');

