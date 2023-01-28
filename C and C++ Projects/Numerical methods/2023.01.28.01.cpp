#include <iostream>
#include <math.h>

using namespace std;

double f(double x)
{
    return 2.0 * pow(x, 3) - 0.6 * pow (x, 2) + 0.6 * x - 0.2;
}

double fp1(double x)
{
    return 6 * pow (x, 2)  - 1.2 * x + 0.6;
}

double fp2(double x)
{
    return 12 * x   - 1.2;
}

bool rootExists(double a, double b)
{
    return f(a)*f(b)<0;
}

void newtonMethod(double a, double b, double eps)
{
    cout<<"We are looking for the root (zero) of the given function using Newton's method." <<endl;
    double x;

    if (f(a)*fp2(a) > 0)
    {
        cout<<"Since f(a) * f''(a) > 0, x = a =" << a <<endl;
        x = a;
    }
    else
    {
        cout<<"Since f(b) * f''(b) > 0, x = b = " << b <<endl;
        x = b;
    }

    int iteration = 1;
    double precision;

    do
    {
        iteration++;
        precision = f(x)/fp1(x);
        x = x - precision;
    }
    while (abs(f(x))>=eps);

    cout<<"Final answer: x = " <<x<< ", f(x) = " <<f(x)<< ", Number of iterations = " <<iteration<< "\n\n";
}

void chord2Method(double a, double b, double eps)
{
    cout<<"We are looking for the root (zero) of the given function using the Chord method (2 formula)." <<endl;

    double x0, xf;

    if (f(a)*fp2(a) < 0)
    {
        cout<<"Since f(a) * f''(a) < 0, x0 = a =" << a << ", xf = b = "<< b << endl;
        x0 = a;
        xf = b;
    }
    else
    {
        cout<<"Since f(b) * f''(b) < 0, x = b = " << b << ", xf = b = "<< b << endl;
        x0 = b;
        xf = a;
    }

    int iteration = 0;
    double x, xn = x0;
    bool stop = false;

    do
    {
        iteration++;
        x = xn - (f(xn) * (xf - xn) / (f(xf) - f(xn)));

        if (abs(x - xn) > eps)
        {
            xn = x;
        }
        else
        {
            stop = true;
        }
    }
    while (!stop);

    cout<<"Final answer: x = " <<x<< ", f(x) = " <<f(x)<< ", Number of iterations = " <<iteration<< "\n\n";
}

void secantMethod(double a, double b, double eps)
{
    cout<<"We are looking for the root (zero) of the given function using the secant method." <<endl;

    double x0, x1;

    if (f(a)*fp2(a) > 0)
    {
        cout<<"Since f(a) * f''(a) > 0, x0 = a =" << a << ", x1 = a + eps = "<< a+eps <<endl;
        x0 = a;
        x1 = a + eps;
    }
    else
    {
        cout<<"Since f(b) * f''(b) > 0, x0 = b = " << b << ", x1 = b + eps ="<< b+eps <<endl;
        x0 = b;
        x1 = b + eps;
    }

    int iteration = 0;
    double xnplus1, xn = x1, xnminus1 = x0;
    bool stop = false;

    do
    {
        iteration++;
        xnplus1 = xn - ((f(xn)*(xn-xnminus1) / (f(xn) - f(xnminus1))));

        if (abs(xnplus1 - xn) > eps)
        {
            xnminus1 = xn;
            xn = xnplus1;
        }
        else
        {
            stop = true;
        }
    }
    while(!stop);

    cout<<"Final answer: x = " <<xnplus1<< ", f(x) = " <<f(xnplus1)<< ", Number of iterations = " <<iteration<< "\n\n";
}

int main()
{
    double a, b, eps;
    bool permitInput = true;

    do
    {
        permitInput = true;

        cout<<"Input a: ";
        cin>>a;
        cout<<"Input b: ";
        cin>>b;
        cout<<"Input eps: ";
        cin>>eps;

        if (!rootExists(a, b))
        {
            cout<<"There is no root on the segment. Repeat input.";
            permitInput = false;
        }

        if (a>=b)
        {
            cout<<"a >= b. Repeat input.";
            permitInput = false;
        }

    }
    while (!permitInput);

    cout<<"\n\n";
    newtonMethod(a, b, eps);
    cout<<"\n\n";
    chord2Method(a, b, eps);
    cout<<"\n\n";
    secantMethod(a, b, eps);

    return 1;
}
