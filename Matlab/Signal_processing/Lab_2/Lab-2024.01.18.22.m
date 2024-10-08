% Создаем вектор времени n от 0 до 25
n = 0:25;

% Создаем дискретную экспоненциальную последовательность
x = exp(1j * n / 3);  % j - мнимая единица

% Создаем первый подграфик для действительной части
subplot(3, 2, 2);
stem(n, real(x));
title('Действительная часть');
xlabel('Индекс (n)');

% Создаем второй подграфик для мнимой части
subplot(3, 2, 3);
stem(n, imag(x));
title('Мнимая часть');
xlabel('Индекс (n)');

