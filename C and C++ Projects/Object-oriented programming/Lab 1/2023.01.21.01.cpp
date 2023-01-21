/*

Создать класс Set – множество действительных чисел, используя динамическую память.
Определить конструкторы: по умолчанию и копий. Определить функции включения
нового элемента во множество, определения принадлежности элемента множеству,
сложения, вычитания и пересечения множест

*/

#include <iostream>

using namespace std;

class Set
{
private:
    double* data;
    int size;
    int capacity;

public:
    Set();                                // default constructor
    Set(const Set& other);                // copy constructor
    ~Set();                               // destructor
    void insert(double value);           // insert new element into set
    bool contains(double value) const;   // check if element is in set
    Set operator+(const Set& other);      // union of two sets
    Set operator-(const Set& other);      // difference of two sets
    Set operator*(const Set& other);      // intersection of two sets
    void show();                           // show set
};

Set::Set() : size(0), capacity(1)
{
    cout<<"\Default constructor was called";
    data = new double[capacity];
}

Set::Set(const Set& other) : size(other.size), capacity(other.capacity)
{
    cout<<"\nCopy constructor was called";
    data = new double[capacity];
    for (int i = 0; i < size; i++)
    {
        data[i] = other.data[i];
    }
}

Set::~Set()
{
    cout<<"\nDestructor was called";
    delete[] data;
}

void Set::insert(double value)
{
    if (size == capacity)
    {
        capacity *= 2;
        double* newData = new double[capacity];
        for (int i = 0; i < size; i++)
        {
            newData[i] = data[i];
        }
        delete[] data;
        data = newData;
    }
    data[size++] = value;
}

bool Set::contains(double value) const
{
    for (int i = 0; i < size; i++)
    {
        if (data[i] == value)
        {
            return true;
        }
    }
    return false;
}

Set Set::operator+(const Set& other)
{
    Set result;
    for (int i = 0; i < size; i++)
    {
        result.insert(data[i]);
    }
    for (int i = 0; i < other.size; i++)
    {
        result.insert(other.data[i]);
    }
    return result;
}

Set Set::operator-(const Set& other)
{
    Set result;
    for (int i = 0; i < size; i++)
    {
        if (!other.contains(data[i]))
        {
            result.insert(data[i]);
        }
    }
    return result;
}

Set Set::operator*(const Set& other)
{
    Set result;
    for (int i = 0; i < size; i++)
    {
        if (other.contains(data[i]))
        {
            result.insert(data[i]);
        }
    }
    return result;
}

void Set::show()
{
    for (int i = 0; i < size; i++)
    {
        std::cout << data[i] << " ";
    }
    std::cout << std::endl;
}

int main()
{
    cout << "Use default constructor to create set1" << endl;
    Set set1;
    cout << "\nInsert 1.5 to set1";
    set1.insert(1.5);
    cout << "\nInsert 2.5 to set1";
    set1.insert(2.5);
    cout << "\nInsert 3.5 to set1";
    set1.insert(3.5);

    cout << "\n\nUse default constructor to create set2" << endl;
    Set set2;
    cout << "\nInsert 2.5 to set1";
    set2.insert(2.5);
    cout << "\nInsert 3.5 to set1";
    set2.insert(3.5);
    cout << "\nInsert 4.5 to set1";
    set2.insert(4.5);

    cout << "\n\nCheck if 2.5 element is in set2: " << set2.contains(2.5);
    cout << "\nCheck if 5.5 element is in set2: " << set2.contains(5.5) << endl;

    cout << "\nUnion of set1 and set2: " << endl;
    Set set3 = set1 + set2;
    cout << endl;
    set3.show();

    cout << "\nDifference of set1 and set2: " << endl;
    Set set4 = set1 - set2;
    cout << endl;
    set4.show();

    cout << "\nIntersection of set1 and set2: " << endl;
    Set set5 = set1 * set2;
    cout << endl;
    set5.show();

    cout << "\nUse copy constructor to create set6";
    Set set6(set5);
    cout << endl;
    set6.show();

    cout << "\nUse destructor to delete set1, set2, set3, set4, set5" << endl;

    return 0;
}
