t=0:0.01:10;
y=1*tripuls(t-1,0.9)+1.1*tripuls(t-3,0.8,1)+0.5*tripuls(t-5,0.5,-0.9);
plot(t,y); grid
xlabel('timpul(s)')
ylabel('y(t)')
