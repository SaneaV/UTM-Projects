% Скрипт FTRIUNGHI вычисляет преобразование Фурье
% для симметричного относительно начала треугольного импульса
% с параметрами: длительность a и угол наклона b и -b

a = 0.5;
b = 2;

syms x w f f1 f2 % объявление символьных переменных
wmax = 50;

% Вычисление интегралов Фурье для левой и правой частей
f1 = int(b * (x + a/2) * exp(-j*w*x), -a/2, 0);
f2 = int(b * (-x + a/2) * exp(-j*w*x), 0, a/2);

% Сборка выражений f1 и f2 вместе
z = strcat(char(f1), char(f2));

% Если вторая часть отрицательная, заменить минус на плюс
if strncmp(char(f2), '-', 1)
    z = strcat(char(f1), char(f2));
else
    z = strcat(char(f1), '+', char(f2));
end

f = sym(z); % возвращение к символьному виду: f='z'

% Построение графика
figure;
ezplot(f, [-wmax wmax]);
title('Преобразование Фурье для треугольного импульса');
xlabel('w');
ylabel('Amplitudine');
axis([-wmax wmax -1 1]);
grid on;

hold on;
u = -wmax:wmax:wmax;
y = zeros(size(u));
plot(u, y); % рисование горизонтальной линии y=0
hold off;

