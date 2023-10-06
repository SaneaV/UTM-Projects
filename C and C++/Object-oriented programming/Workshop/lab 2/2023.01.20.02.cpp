#include <iostream>
using namespace std;

class Int
{
private:
    int n;

public:
    Int(int n = 0)
    {
        this->n = n;
    }

    Int& operator++()
    {
        n++;
        return *this;
    }

    Int operator++(int a)
    {
        Int r;
        r = *this;
        n++;
        return r;
    }

    Int operator+(Int& right)
    {
        return Int(n + right.n);
    }

    Int operator+(int right)
    {
        return Int(n + right);
    }

    Int& operator+()
    {
        return *this;
    }

    friend Int& operator--(Int& ob)
    {
        ob.n--;
        return ob;
    }

    friend Int operator--(Int& ob, int a)
    {
        Int r;
        r = ob;
        ob.n++;
        return r;
    }

    friend Int operator-(Int& left, Int& right)
    {
        return Int(left.n - right.n);
    }

    friend Int operator-(Int& left, int right)
    {
        return Int(left.n - right);
    }

    friend Int operator-(int left, Int& right)
    {
        return Int(left - right.n);
    }

    friend Int operator-(Int& ob)
    {
        return -ob.n;
    }

    friend ostream& operator<<(ostream& os, const Int& right);
    friend istream& operator>>(istream& is, Int& right);

};

ostream& operator<<(ostream& os, const Int& right) {
  os << "Int: " << right.n << endl;
  return os;
}

istream& operator>>(istream& is, Int& right) {
  cout << "Input int: ";
  is >> right.n;
  return is;
}


int main()
{
    Int a(-6), b(11), c;
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
    return 0;
}
