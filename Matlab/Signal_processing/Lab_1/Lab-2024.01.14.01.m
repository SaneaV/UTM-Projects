% Программа P1_1
% Генерация последовательностей элементов
clf;
% Генерация вектора от -10 до 20
n=-10:20;
% Генерация последовательности
u=[0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0.4 0.4 0.4 0.4 0.4 0 0 0 0 1.1 0 0 0 0 0];
% Отображение на экран последовательностей
stem(n,u);
xlabel('Время n'); ylabel('Амплитуда');
title('Последовательность элементов');
axis([-10 20 0 1.2]);
