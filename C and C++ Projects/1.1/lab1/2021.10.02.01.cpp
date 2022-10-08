#include <iostream>
#include <math.h>

using namespace std;

int main()
{
    double a, b, c, xBegin, xEnd, dX;

    cout<<"Input a: ";
    cin>>a;

    cout<<"Input b: ";
    cin>>b;

    cout<<"Input c: ";
    cin>>c;

    cout<<"Input xBegin: ";
    cin>>xBegin;

    cout<<"Input xEnd: ";
    cin>>xEnd;

    cout<<"Input dx: ";
    cin>>dX;

    double f = 0;
    double x = xBegin;

    cout<<"\nCycle while: \n\n";

    while(xEnd >= x)
    {
        double f = 0;

        if(x<3 && b!=0)
        {
            f = (a * x * x) - (b * x) + c;

            if(!(~((long)floor(a) ^ (long)floor(b)) & ((long)floor(a) ^ (long)floor(c))!=0))
            {
                f = int(f);
            }

            cout<<"x = "<<x<<"\t f = (a * x * x) - (b * x) + c = "<<f<<"\n";
        }
        else if(x>3 && b==0)
        {
            if(x-c!=0)
            {
                f = (x - a) / (x - c);
                if(!(~((long)floor(a) ^ (long)floor(b)) & ((long)floor(a) ^ (long)floor(c))!=0))
                {
                    f = int(f);
                }

                cout<<"x = "<<x<<"\t f = (x - a) / (x - c) = "<<f<<"\n";
            }
            else
            {
                cout<<"x = "<<x<<"\tFunction not found f = (x - a) / (x - c)"<<endl;
            }
        }
        else
        {
            if(c!=0)
            {
                f = x / c;

                if(!(~((long)floor(a) ^ (long)floor(b)) & ((long)floor(a) ^ (long)floor(c))!=0))
                {
                    f = int(f);
                }

                cout<<"x = "<<x<<"\t f = (x - a) / (x - c) = "<<f<<"\n";
            }
            else
            {
                cout<<"x = "<<x<<"\tFunction not found f = (x/c)"<<endl;
            }
        }

        x += dX;
    }

    cout<<"\nCycle for: \n\n";
    for(x=xBegin; xEnd>= x; x+=dX)
    {
        double f = 0;

        if(x<3 && b!=0)
        {
            f = (a * x * x) - (b * x) + c;

            if(!(~((long)floor(a) ^ (long)floor(b)) & ((long)floor(a) ^ (long)floor(c))!=0))
            {
                f = int(f);
            }

            cout<<"x = "<<x<<"\t f = (a * x * x) - (b * x) + c = "<<f<<"\n";
        }
        else if(x>3 && b==0)
        {
            if(x-c!=0)
            {
                f = (x - a) / (x - c);
                if(!(~((long)floor(a) ^ (long)floor(b)) & ((long)floor(a) ^ (long)floor(c))!=0))
                {
                    f = int(f);
                }

                cout<<"x = "<<x<<"\t f = (x - a) / (x - c) = "<<f<<"\n";
            }
            else
            {
                cout<<"x = "<<x<<"\tFunction not found f = (x - a) / (x - c)"<<endl;
            }
        }
        else
        {
            if(c!=0)
            {
                f = x / c;

                if(!(~((long)floor(a) ^ (long)floor(b)) & ((long)floor(a) ^ (long)floor(c))!=0))
                {
                    f = int(f);
                }

                cout<<"x = "<<x<<"\t f = (x - a) / (x - c) = "<<f<<"\n";
            }
            else
            {
                cout<<"x = "<<x<<"\tFunction not found f = (x/c)"<<endl;
            }
        }
    }

    cout<<"\nCycle do...while: \n\n";
    x=xBegin;
    if(xEnd>= x)
    {
        do
        {
            double f = 0;

            if(x<3 && b!=0)
            {
                f = (a * x * x) - (b * x) + c;

                if(!(~((long)floor(a) ^ (long)floor(b)) & ((long)floor(a) ^ (long)floor(c))!=0))
                {
                    f = int(f);
                }

                cout<<"x = "<<x<<"\t f = (a * x * x) - (b * x) + c = "<<f<<"\n";
            }
            else if(x>3 && b==0)
            {
                if(x-c!=0)
                {
                    f = (x - a) / (x - c);
                    if(!(~((long)floor(a) ^ (long)floor(b)) & ((long)floor(a) ^ (long)floor(c))!=0))
                    {
                        f = int(f);
                    }

                    cout<<"x = "<<x<<"\t f = (x - a) / (x - c) = "<<f<<"\n";
                }
                else
                {
                    cout<<"x = "<<x<<"\tFunction not found f = (x - a) / (x - c)"<<endl;
                }
            }
            else
            {
                if(c!=0)
                {
                    f = x / c;

                    if(!(~((long)floor(a) ^ (long)floor(b)) & ((long)floor(a) ^ (long)floor(c))!=0))
                    {
                        f = int(f);
                    }

                    cout<<"x = "<<x<<"\t f = (x - a) / (x - c) = "<<f<<"\n";
                }
                else
                {
                    cout<<"x = "<<x<<"\tFunction not found f = (x/c)"<<endl;
                }
            }

            x += dX;
        }
        while(xEnd>=x);
    }
}
