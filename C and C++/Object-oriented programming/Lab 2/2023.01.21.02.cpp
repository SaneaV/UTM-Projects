/*

Создать класс Bool – логические переменные. Определить операторы "+" – логическое
ИЛИ, "*" – логическое И "^" – ИСКЛЮЧИТЕЛЬНОЕ ИЛИ, как дружественные
функции, а операторы "==" и "!=" как методы класса. Операторы должны позволять
осуществления операций, как с переменными данного класса, так и с переменными
встроенного int. (Если целое число отлично от нуля, считается что переменная истинна,
в противном случае ложна). Перегрузить операторы «<<» и «>>».

*/

#include <iostream>

using namespace std;

class Bool
{
public:
    Bool() : value(false) {}
    Bool(bool val) : value(val) {}
    friend Bool operator+(const Bool& lhs, const Bool& rhs);
    friend Bool operator*(const Bool& lhs, const Bool& rhs);
    friend Bool operator^(const Bool& lhs, const Bool& rhs);
    bool operator==(const Bool& rhs) const
    {
        return value == rhs.value;
    }
    bool operator!=(const Bool& rhs) const
    {
        return value != rhs.value;
    }
    std::ostream& operator<<(std::ostream& os) const
    {
        os << value;
        return os;
    }
    std::istream& operator>>(std::istream& is)
    {
        int val;
        is >> val;
        value = (val != 0);
        return is;
    }
    bool value;
};

Bool operator+(const Bool& lhs, const Bool& rhs)
{
    return Bool(lhs.value || rhs.value);
}
Bool operator*(const Bool& lhs, const Bool& rhs)
{
    return Bool(lhs.value && rhs.value);
}
Bool operator^(const Bool& lhs, const Bool& rhs)
{
    return Bool(lhs.value != rhs.value);
}
bool operator==(const Bool& lhs, const Bool& rhs)
{
    return lhs.value == rhs.value;
}
bool operator!=(const Bool& lhs, const Bool& rhs)
{
    return lhs.value != rhs.value;
}
std::ostream& operator<<(std::ostream& os, const Bool& obj)
{
    os << obj.value;
    return os;
}
std::istream& operator>>(std::istream& is, Bool& obj)
{
    int val;
    is >> val;
    obj.value = (val != 0);
    return is;
}


int main()
{
    Bool a(true);
    Bool b(false);
    Bool c = a + b;
    std::cout << "a OR b = " << c << std::endl;
    Bool d = a * b;
    std::cout << "a AND b = " << d << std::endl;
    Bool e = a ^ b;
    std::cout << "a XOR b = " << e << std::endl;
    std::cout << "x == " << a << " y == " << b << std::endl;
    if (a == b)
    {
        std::cout << "x and y are equal" << std::endl;
    }
    else if (a != b)
    {
        std::cout << "x and y are not equal" << std::endl;
    }

    cout<< "\n\n";

    int x, y;
    cout << "Input x (0 = false, 0 != true): ";
    cin>>x;
    cout << "Input y (0 = false, 0 != true): ";
    cin>>y;


    Bool f(x);
    Bool g(y);

    Bool c2 = f + g;
    std::cout << "f OR g = " << c2 << std::endl;
    Bool d2 = f * g;
    std::cout << "f AND g = " << d2 << std::endl;
    Bool e2 = f ^ g;
    std::cout << "f XOR g = " << e2 << std::endl;

    std::cout << "f == " << f << " g == " << g << std::endl;
    if (f == g)
    {
        std::cout << "f and g are equal" << std::endl;
    }
    else if (f != g)
    {
        std::cout << "f and g are not equal" << std::endl;
    }
    return 0;
}
