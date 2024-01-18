% Декомпозиция периодического сигнала в ряд Фурье

% Ввод параметров: период (T), количество гармоник (N), тип сигнала (sin, d, f)
T = input('Установите период T [сек]: ');
N = input('Установите количество гармоник: ');
tip = input('Выберите тип сигнала (sin[s], прямоугольный[d] или пилообразный[f]): ', 's');

W = 2 * pi / T; % Пульсация

t = 0:T / 1022:T + T / 1022; % Временной вектор

% Генерация сигнала в зависимости от выбранного типа
if strcmp(tip, 's')
    s = sin(W * t); % Синусоидальный сигнал
else
    for j = 1:1024
        if strcmp(tip, 'd')
            if j < 512
                s(j) = 1; % Прямоугольный сигнал
            else
                s(j) = -1;
            end
        elseif strcmp(tip, 'f')
            s(j) = j / 500 - 1; % Пилообразный сигнал
        end
    end
end

% Вычисление среднего значения и эффективного значения
val_medie = trapz(t, s) / T;
val_efectiva = sqrt(trapz(t, s.^2) / T);

% Подготовка к вычислению коэффициентов Фурье
timp = t - T / 2;
for i = 1:N
    a(i) = 2 * trapz(t, s .* cos(i * W * t)) / T;
    b(i) = 2 * trapz(t, s .* sin(i * W * t)) / T;
    A(i) = sqrt(a(i)^2 + b(i)^2);
    F(i) = atan2(b(i), a(i));
    f(i) = i / T;
end

% Восстановление сигнала из ряда Фурье
r = val_medie;
for j = 1:N
    r = r + A(j) * cos(j * W * t - F(j));
end

% Построение графиков
subplot(223);
plot(t, r);
title('Сигнал после восстановления');
xlabel('t [сек]');
axis([min(t) max(t) (min(r) - 0.02 * (max(r) - min(r))) (max(r) + 0.02 * (max(r) - min(r)))]);
grid;

subplot(221);
plot(t, s);
title('Исходный сигнал');
xlabel('t [сек]');
grid;
axis([min(t) max(t) (min(r) - 0.02 * (max(r) - min(r))) (max(r) + 0.02 * (max(r) - min(r)))]);

subplot(222);
stem(f, A);
title('Гармоники A(n)*cos[n*2*pi*f*t-Fi(n)]');
xlabel('f [Гц]');
grid;

subplot(224);
stem(f, F / pi);
title('Фазы Fi(f)');
xlabel('f [Гц]');
ylabel('x pi   [рад]');
grid;

