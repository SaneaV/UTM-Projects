/*

Создать класс студент, у которого есть имя, специальность, год обучения и средний
балл. Определить функции установки, изменения данных, сравнения. Для задания
текстовых полей использовать оператор new. Определить конструкторы, деструктор и
другие необходимые функции. Создать производный класс студент-дипломник, для
которого определена тема дипломной работы. Так же, необходимо определить все
необходимые функции.

*/

#include <iostream>
#include <strings.h>

class Student
{
private:
    char* name;
    char* specialty;
    int year;
    double avg;

public:
    Student();
    Student(const char* name, const char* specialty, int year, double avg);
    Student(const Student& other);
    ~Student();

    void setName(const char* name);
    void setSpecialty(const char* specialty);
    void setYear(int year);
    void setAvg(double avg);

    const char* getName() const;
    const char* getSpecialty() const;
    int getYear() const;
    double getAvg() const;

    bool operator==(const Student& other) const;
    bool operator!=(const Student& other) const;
};

Student::Student()
{
    name = new char[1];
    name[0] = '\0';
    specialty = new char[1];
    specialty[0] = '\0';
    year = 0;
    avg = 0;
}

Student::Student(const char* name, const char* specialty, int year, double avg)
{
    this->name = new char[strlen(name) + 1];
    strcpy(this->name, name);
    this->specialty = new char[strlen(specialty) + 1];
    strcpy(this->specialty, specialty);
    this->year = year;
    this->avg = avg;
}

Student::Student(const Student& other)
{
    name = new char[strlen(other.name) + 1];
    strcpy(name, other.name);
    specialty = new char[strlen(other.specialty) + 1];
    strcpy(specialty, other.specialty);
    year = other.year;
    avg = other.avg;
}

Student::~Student()
{
    delete[] name;
    delete[] specialty;
}

void Student::setName(const char* name)
{
    delete[] this->name;
    this->name = new char[strlen(name) + 1];
    strcpy(this->name, name);
}

void Student::setSpecialty(const char* specialty)
{
    delete[] this->specialty;
    this->specialty = new char[strlen(specialty) + 1];
    strcpy(this->specialty, specialty);
}

void Student::setYear(int year)
{
    this->year = year;
}

void Student::setAvg(double avg)
{
    this->avg = avg;
}

const char* Student::getName() const
{
    return name;
}

const char* Student::getSpecialty() const
{
    return specialty;
}

int Student::getYear() const
{
    return year;
}

double Student::getAvg() const
{
    return avg;
}

bool Student::operator==(const Student& other) const
{
    return strcmp(name, other.name) == 0 && strcmp(specialty, other.specialty) == 0 && year == other.year && avg == other.avg;
}

bool Student::operator!=(const Student& other) const
{
    return !(*this == other);
}

class StudentDiplomat : public Student
{
private:
    char* thesis;

public:
    StudentDiplomat();
    StudentDiplomat(const char* name, const char* specialty, int year, double avg, const char* thesis);
    StudentDiplomat(const StudentDiplomat& other);
    ~StudentDiplomat();

    bool operator==(const StudentDiplomat& other);
    bool operator!=(const StudentDiplomat& other);

    void setThesis(const char* thesis);
    const char* getThesis() const;
};

StudentDiplomat::StudentDiplomat() : Student()
{
    thesis = new char[1];
    thesis[0] = '\0';
}

StudentDiplomat::StudentDiplomat(const char* name, const char* specialty, int year, double avg, const char* thesis) : Student(name, specialty, year, avg)
{
    this->thesis = new char[strlen(thesis) + 1];
    strcpy(this->thesis, thesis);
}

StudentDiplomat::StudentDiplomat(const StudentDiplomat& other) : Student(other)
{
    thesis = new char[strlen(other.thesis) + 1];
    strcpy(thesis, other.thesis);
}

StudentDiplomat::~StudentDiplomat()
{
    delete[] thesis;
}

void StudentDiplomat::setThesis(const char* thesis)
{
    delete[] this->thesis;
    this->thesis = new char[strlen(thesis) + 1];
    strcpy(this->thesis, thesis);
}

const char* StudentDiplomat::getThesis() const
{
    return thesis;
}

bool StudentDiplomat::operator==(const StudentDiplomat& other)
{
    return Student::operator==(other) && strcmp(thesis, other.thesis) == 0;
}

bool StudentDiplomat::operator!=(const StudentDiplomat& other)
{
    return !(*this == other);
}

std::istream& operator>>(std::istream& stream, Student& student)
{
    char buffer[256];
    stream >> buffer;
    student.setName(buffer);
    stream >> buffer;
    student.setSpecialty(buffer);
    int year;
    stream >> year;
    student.setYear(year);
    double avg;
    stream >> avg;
    student.setAvg(avg);
    return stream;
}

std::ostream& operator<<(std::ostream& stream, const Student& student)
{
    stream << student.getName() << ' ' << student.getSpecialty() << ' ' << student.getYear() << ' ' << student.getAvg();
    return stream;
}

std::istream& operator>>(std::istream& stream, StudentDiplomat& student)
{
    stream >> static_cast<Student&>(student);
    char buffer[256];
    stream >> buffer;
    student.setThesis(buffer);
    return stream;
}

std::ostream& operator<<(std::ostream& stream, const StudentDiplomat& student)
{
    stream << static_cast<const Student&>(student) << ' ' << student.getThesis();
    return stream;
}

int main()
{
    Student s1("John Doe", "Computer Science", 2020, 3.5);
    Student s2("Jane Smith", "Computer Science", 2020, 4.0);

    if (s1 == s2)
    {
        std::cout << "s1 and s2 are equal" << std::endl;
    }
    else if (s1 != s2)
    {
        std::cout << "s1 and s2 are not equal" << std::endl;
    }

    StudentDiplomat sd1("John Doe", "Computer Science", 2020, 3.5, "Machine Learning");
    StudentDiplomat sd2("Jane Smith", "Computer Science", 2020, 4.0, "Computer Vision");

    if (sd1 == sd2)
    {
        std::cout << "sd1 and sd2 are equal" << std::endl;
    }
    else if (sd1 != sd2)
    {
        std::cout << "sd1 and sd2 are not equal" << std::endl;
    }

    std::cout << "\n\n\n";

    Student s;
    std::cout << "Enter name: ";
    std::string name;
    std::getline(std::cin, name);
    s.setName(name.c_str());

    std::cout << "Enter specialty: ";
    std::string specialty;
    std::getline(std::cin, specialty);
    s.setSpecialty(specialty.c_str());

    std::cout << "Enter year: ";
    int year;
    std::cin >> year;
    s.setYear(year);

    std::cout << "Enter avg: ";
    double avg;
    std::cin >> avg;
    s.setAvg(avg);

    std::cout << "\n\n\n";
    std::cout << "Student: " << std::endl;
    std::cout << "Name: " << s.getName() << std::endl;
    std::cout << "Specialty: " << s.getSpecialty() << std::endl;
    std::cout << "Year: " << s.getYear() << std::endl;
    std::cout << "Avg: " << s.getAvg() << std::endl;
    std::cout << "\n\n\n";

    StudentDiplomat sd;
    std::cin.ignore();
    std::cout << "Enter name: ";
    std::string sdName;
    std::getline(std::cin, sdName);
    sd.setName(sdName.c_str());

    std::cout << "Enter specialty: ";
    std::string sdSpecialty;
    std::getline(std::cin, sdSpecialty);
    sd.setSpecialty(sdSpecialty.c_str());

    std::cout << "Enter year: ";
    int sdYear;
    std::cin >> sdYear;
    sd.setYear(sdYear);

    std::cout << "Enter avg: ";
    double sdAvg;
    std::cin >> sdAvg;
    sd.setAvg(sdAvg);

    std::cout << "Enter thesis: ";
    std::string thesis;
    std::cin.ignore();
    std::getline(std::cin, thesis);
    sd.setThesis(thesis.c_str());

    std::cout << "\n\n\n";
    std::cout << "Student-Diplomat: " << std::endl;
    std::cout << "Name: " << sd.getName() << std::endl;
    std::cout << "Specialty: " << sd.getSpecialty() << std::endl;
    std::cout << "Year: " << sd.getYear() << std::endl;
    std::cout << "Avg: " << sd.getAvg() << std::endl;
    std::cout << "Thesis: " << sd.getThesis() << std::endl;

    return 0;
}
