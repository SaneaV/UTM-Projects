t=0:0.01:10;
y=0.7*rectpuls(t-3.2, 2)+1.4*rectpuls(t-5.1)+0.5*rectpuls(t-8.04);
plot(t,y),grid;
xlabel('timpul(s)');
ylabel('procesul de iesire y(t)');
