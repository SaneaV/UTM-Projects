/*

Создать абстрактный базовый класс Figure с виртуальной функцией - объем. Создать
производные классы параллелепипед, пирамида, тетраэдр и шар, в которых данная
функция переопределена. В функции main определить массив указателей на абстрактный
класс, которым присваиваются адреса различных объектов.
Объем параллелепипеда - V=xyz (x,y,z – стороны).
Объем пирамиды: V=xyh (x,y, - стороны, h - высота).
Объем тетраэдра: V=a^3 * sqrt(2)/12
Объем шара: V= 4*pi*r^3/3

*/

#include <iostream>
#include <math.h>
#include <string.h>

class Figure
{
public:
    virtual double volume() = 0;
};

class Parallelepiped : public Figure
{
private:
    double length, width, height;
public:
    Parallelepiped(double l, double w, double h) : length(l), width(w), height(h) {}
    double volume()
    {
        return length * width * height;
    }
};

class Pyramid : public Figure
{
private:
    double base, height;
public:
    Pyramid(double b, double h) : base(b), height(h) {}
    double volume()
    {
        return (base * height) / 3;
    }
};

class Tetrahedron : public Figure
{
private:
    double edge;
public:
    Tetrahedron(double e) : edge(e) {}
    double volume()
    {
        return (edge * edge * edge) / (6 * sqrt(2));
    }
};

class Sphere : public Figure
{
private:
    double radius;
public:
    Sphere(double r) : radius(r) {}
    double volume()
    {
        return (4 / 3) * 3.14 * (radius * radius * radius);
    }
};

int main()
{
    Figure* figures2[4];
    figures2[0] = new Parallelepiped(10, 20, 30);
    figures2[1] = new Pyramid(5, 10);
    figures2[2] = new Tetrahedron(3);
    figures2[3] = new Sphere(5);

    std::string figure_names[4] = {"Parallelepiped", "Pyramid", "Tetrahedron", "Sphere"};

    for (int i = 0; i < 4; i++)
    {
        std::cout << "Volume of " << figure_names[i] << " is: " << figures2[i]->volume() << std::endl;
    }

    std::string name;
    double l,w,h,r;
    std::cout<<"Enter the name of the figure you want to create: ";
    std::cin>>name;
    if(name == "Parallelepiped")
    {
        std::cout<<"Enter the length: ";
        std::cin>>l;
        std::cout<<"Enter the width: ";
        std::cin>>w;
        std::cout<<"Enter the height: ";
        std::cin>>h;
        figures2[4] = new Parallelepiped(l, w, h);
        std::cout<<"Volume of parallelepiped is: " << figures2[4]->volume() << std::endl;
    }
    else if(name == "Pyramid")
    {
        std::cout<<"Enter the base length: ";
        std::cin>>l;
        std::cout<<"Enter the height: ";
        std::cin>>h;
        figures2[4] = new Pyramid(l, h);
        std::cout<<"Volume of pyramid is: " << figures2[4]->volume() << std::endl;
    }
    else if(name == "Tetrahedron")
    {
        std::cout<<"Enter the edge length: ";
        std::cin>>l;
        figures2[4] = new Tetrahedron(l);
        std::cout<<"Volume of tetrahedron is: " << figures2[4]->volume() << std::endl;
    }
    else if(name == "Sphere")
    {
        std::cout<<"Enter the radius: ";
        std::cin>>r;
        figures2[4] = new Sphere(r);
        std::cout<<"Volume of Sphere is: " << figures2[4]->volume() << std::endl;
    }
    else
    {
        std::cout<<"Invalid figure name entered."<<std::endl;
    }

    return 0;
}
