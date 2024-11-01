% Очистка рабочего пространства и графического окна
clear all; clf;

% Параметры поезда импульсов
T = 5;           % Период поезда импульсов
tau = 0.7;       % Длительность каждого импульса
Amplit = 1;      % Амплитуда импульсов

% Количество гармоник для начальной аппроксимации
Ni = 3;

% Шаг выбора количества гармоник
n = Ni;

% Количество гармоник для конечной аппроксимации
Nf = 3 * n;

% Вычисление базовых параметров
w0 = 2 * pi / T;
f0 = 1 / T;
B = Nf + 1;

% Вычисление параметров модели спектра
A = zeros(1, B);
phi = zeros(1, B);
for i = 1:B
    alf = (i - 1) * w0 * tau / 2;
    alf = alf / pi;
    A(1, i) = abs(Amplit * tau * sinc(alf) / T);
    phi(1, i) = -angle(sinc(alf));
end

% Вычисление вектора ind для графического представления спектра
for i = 1:B
    ind(i) = (i - 1) * f0;
end

% Построение спектра СФК (только для положительных частот)
subplot(221);
stem(ind, A(1, :));
title('Спектр СФК поезда импульсов');
xlabel('f [Гц]');
grid;

subplot(222);
stem(ind, phi(1, :));
title('Фазы Fi(f)');
xlabel('f [Гц]');
ylabel('x pi [рад]');
grid;

% Генерация и визуализация поезда импульсов
x1 = zeros(1, ((T * 1000 / 2) - (tau * 1000 / 2)));
x2 = Amplit * ones(1, (tau * 1000));
x3 = zeros(1, ((T * 1000 / 2) - (tau * 1000 / 2)));
x = [x1 x2 x3];
dt = 0.001;
t = [-T / 2 + dt:dt:T / 2];

subplot(223);
h = plot(t, x);
axis([-T / 2 T / 2 -1.5 1.2 * Amplit]);
grid; hold on;

% Расчет и отображение сигналов, восстанавливаемых из спектра
for j = Ni:n:Nf
    xy = A(1) * ones(1, (T * 1000));
    for i = 1:j
        xy = xy + 2 * A(1, i + 1) * cos(i * w0 * t + phi(1, i + 1));
    end
    plot(t, xy, 'k');
    grid;
    title('Исходный и восстановленный сигнал');
    xlabel('t [сек]');
    axis([-T / 2 T / 2 -1.5 1.2 * Amplit]);
end
grid;

