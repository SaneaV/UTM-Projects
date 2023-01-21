/*

Создать класс комната, имеющая площадь. Определить конструкторы и методы
доступа. Создать класс однокомнатных квартир, содержащий комнату и кухню (ее
площадь), этаж (комната содержится в классе однокомнатная квартира). Определить
конструкторы, методы доступа. Определить производный класс однокомнатные квартиры
с адресом (дополнительное поле - адрес). Определить конструкторы, деструктор и вывод в
поток.

*/

#include <iostream>

class Room
{
    double area;
public:
    Room();
    Room(double area);
    double getArea() const;
    void setArea(double area);
};

Room::Room() : area(0) {}

Room::Room(double area) : area(area) {}

double Room::getArea() const
{
    return area;
}

void Room::setArea(double area)
{
    this->area = area;
}

class StudioApartment
{
    Room room;
    double kitchenArea;
    int floor;
public:
    StudioApartment();
    StudioApartment(double roomArea, double kitchenArea, int floor);
    Room getRoom() const;
    double getKitchenArea() const;
    int getFloor() const;
    void setRoom(const Room& room);
    void setKitchenArea(double kitchenArea);
    void setFloor(int floor);
};

StudioApartment::StudioApartment() : room(), kitchenArea(0), floor(0) {}

StudioApartment::StudioApartment(double roomArea, double kitchenArea, int floor) : room(roomArea), kitchenArea(kitchenArea), floor(floor) {}

Room StudioApartment::getRoom() const
{
    return room;
}

double StudioApartment::getKitchenArea() const
{
    return kitchenArea;
}

int StudioApartment::getFloor() const
{
    return floor;
}

void StudioApartment::setRoom(const Room& room)
{
    this->room = room;
}

void StudioApartment::setKitchenArea(double kitchenArea)
{
    this->kitchenArea = kitchenArea;
}

void StudioApartment::setFloor(int floor)
{
    this->floor = floor;
}

class StudioApartmentWithAddress : public StudioApartment
{
    std::string address;
public:
    StudioApartmentWithAddress();
    StudioApartmentWithAddress(double roomArea, double kitchenArea, int floor, std::string address);
    StudioApartmentWithAddress(const StudioApartmentWithAddress& other);
    std::string getAddress() const;
    void setAddress(std::string address);
    friend std::ostream& operator<<(std::ostream& stream, const StudioApartmentWithAddress& apartment);
};

StudioApartmentWithAddress::StudioApartmentWithAddress() : StudioApartment(), address() {}

StudioApartmentWithAddress::StudioApartmentWithAddress(double roomArea, double kitchenArea, int floor, std::string address) : StudioApartment(roomArea, kitchenArea, floor), address(address) {}

StudioApartmentWithAddress::StudioApartmentWithAddress(const StudioApartmentWithAddress& other) : StudioApartment(other), address(other.address) {}

std::string StudioApartmentWithAddress::getAddress() const
{
    return address;
}

void StudioApartmentWithAddress::setAddress(std::string address)
{
    this->address = address;
}

std::ostream& operator<<(std::ostream& stream, const StudioApartmentWithAddress& apartment)
{
    stream << "Room area : " << apartment.getRoom().getArea() << " kitchen area : " << apartment.getKitchenArea() << " floor : " << apartment.getFloor() << " address : " << apartment.getAddress();
    return stream;
}

int main()
{
    StudioApartmentWithAddress apartment1(20.5, 10.5, 3, "New York");
    std::cout << apartment1 << std::endl;

    StudioApartmentWithAddress apartment2;

    std::cout << "Enter room area : ";
    double roomArea;
    std::cin >> roomArea;

    std::cout << "Enter kitchen area : ";
    double kitchenArea;
    std::cin >> kitchenArea;

    std::cout << "Enter floor : ";
    int floor;
    std::cin >> floor;

    std::cout << "Enter address : ";
    std::string address;
    std::cin >> address;

    apartment2.setRoom(Room(roomArea));
    apartment2.setKitchenArea(kitchenArea);
    apartment2.setFloor(floor);
    apartment2.setAddress(address);

    std::cout << apartment2 << std::endl;

    return 0;
}
