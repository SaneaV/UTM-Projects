#include <iostream>
using namespace std;

class Real
{
private:
    float n;

public:
    Real(int n = 0)
    {
        this->n = n;
    }

    Real operator+(Real& right)
    {
        return Real(n + right.n);
    }

    Real operator+(float right)
    {
        return Real(n + right);
    }

    Real& operator+()
    {
        return *this;
    }

    friend Real operator-(Real& left, Real& right)
    {
        return Real(left.n - right.n);
    }

    friend Real operator-(Real& left, float right)
    {
        return Real(left.n - right);
    }

    friend Real operator-(float left, Real& right)
    {
        return Real(left - right.n);
    }

    friend ostream& operator<<(ostream& os, const Real& right);
    friend istream& operator>>(istream& is, Real& right);
    friend Real operator-(Real& ob)
    {
        return -ob.n;
    }
    int addFive();
};

int Real::addFive() {
    this -> n += 5;
    return this -> n;
}

ostream& operator<<(ostream& os, const Real& right)
{
    os << "Real: " << right.n << endl;
    return os;
}

istream& operator>>(istream& is, Real& right)
{
    cout << "Input real: ";
    is >> right.n;
    return is;
}


int main()
{
    Real a, b,c;
    cin>>a;
    cin>>b;

    cout<<a<<b<<c;
    c=a+b;
    cout<<c;
    c=a+7;
    cout<<c;
    c=+c;
    cout<<c;
    c=a-b;
    cout<<c;
    cout<<a-6;
    cout<<c;
    c=7-a;
    cout<<c;
    c=-c;
    cout<<c;
    cin>>c;
    cout<<c;

    cout<<c.addFive();
    return 0;
}
