/*

Создайте общий (параметризованный) класс Matrix.
Класс должен содержать конструкторы, деструкторы и функции getRows, getCols, +=, -=, *= и функции ввода/вывода.

*/

#include <iostream>

template <typename T>
class Matrix
{
public:
    // Constructors
    Matrix();
    Matrix(int rows, int cols);
    Matrix(const Matrix<T>& other);
    ~Matrix();

    // Accessors
    int getRows() const;
    int getCols() const;

    // Operators
    Matrix<T>& operator+=(const Matrix<T>& other);
    Matrix<T>& operator-=(const Matrix<T>& other);
    Matrix<T>& operator*=(const Matrix<T>& other);

    void set(int row, int col, T value);
    void show();
    void input();

private:
    int rows;
    int cols;
    T** data;
};

template <typename T>
Matrix<T>::Matrix()
{
    rows = 0;
    cols = 0;
    data = nullptr;
}

template <typename T>
Matrix<T>::Matrix(int rows, int cols)
{
    this->rows = rows;
    this->cols = cols;
    data = new T*[rows];
    for (int i = 0; i < rows; i++)
    {
        data[i] = new T[cols];
    }
}

template <typename T>
Matrix<T>::Matrix(const Matrix<T>& other)
{
    rows = other.rows;
    cols = other.cols;
    data = new T*[rows];
    for (int i = 0; i < rows; i++)
    {
        data[i] = new T[cols];
        for (int j = 0; j < cols; j++)
        {
            data[i][j] = other.data[i][j];
        }
    }
}

template <typename T>
Matrix<T>::~Matrix()
{
    for (int i = 0; i < rows; i++)
    {
        delete[] data[i];
    }
    delete[] data;
}

template <typename T>
int Matrix<T>::getRows() const
{
    return rows;
}

template <typename T>
int Matrix<T>::getCols() const
{
    return cols;
}

template <typename T>
Matrix<T>& Matrix<T>::operator+=(const Matrix<T>& other)
{
    if (rows != other.rows || cols != other.cols)
    {
        throw "Cannot add matrices with different dimensions.";
    }
    for (int i = 0; i < rows; i++)
    {
        for (int j = 0; j < cols; j++)
        {
            data[i][j] += other.data[i][j];
        }
    }
    return *this;
}

template <typename T>
Matrix<T>& Matrix<T>::operator-=(const Matrix<T>& other)
{
    if (rows != other.rows || cols != other.cols)
    {
        throw "Cannot subtract matrices with different dimensions.";
    }
    for (int i = 0; i < rows; i++)
    {
        for (int j = 0; j < cols; j++)
        {
            data[i][j] -= other.data[i][j];
        }
    }
    return *this;
}

template <typename T>
Matrix<T>& Matrix<T>::operator*=(const Matrix<T>& other)
{
    if (cols != other.rows)
    {
        throw "Cannot multiply matrices with incompatible dimensions.";
    }
    Matrix<T> result(rows, other.cols);
    for(int i=0; i<result.rows; i++)
    {
        for(int j=0; j<result.cols; j++)
        {
            result.data[i][j] = 0;
        }
    }
    for (int i = 0; i < rows; i++)
    {
        for (int j = 0; j < other.cols; j++)
        {
            for (int k = 0; k < cols; k++)
            {
                result.data[i][j] += data[i][k] * other.data[k][j];
            }
        }
    }
//deallocate memory of the original matrix's data
    for (int i = 0; i < rows; i++)
    {
        delete[] data[i];
    }
    delete[] data;
    //set new data
    data = new T*[result.rows];
    for (int i = 0; i < result.rows; i++)
    {
        data[i] = new T[result.cols];
        for (int j = 0; j < result.cols; j++)
        {
            data[i][j] = result.data[i][j];
        }
    }
    rows = result.rows;
    cols = result.cols;
    return *this;
}

template <typename T>
void Matrix<T>::set(int row, int col, T value)
{
    this -> data[row][col] = value;
}

template <typename T>
void Matrix<T>::show()
{
    for (int i = 0; i < rows; i++)
    {
        for (int j = 0; j < cols; j++)
            std::cout<<data[i][j]<<" ";
        std::cout<<std::endl;
    }
}

template <typename T>
void Matrix<T>::input()
{
    int element;
    for (int i = 0; i < rows; i++)
    {
        for (int j = 0; j < cols; j++)
        {
            std::cout<<"Input element: ";
            std::cin>>element;
            this -> data[i][j] = element;
        }
    }
}

int main()
{
    Matrix<int> m1(3,3);
    for(int i=0; i<3; i++)
        for(int j=0; j<3; j++)
            m1.set(i,j, i+j+1);

    Matrix<int> m2(3,3);
    for(int i=0; i<3; i++)
        for(int j=0; j<3; j++)
            m2.set(i,j, i*j+1);

    std::cout << "Matrix 1:" << std::endl;
    m1.show();
    std::cout<< std::endl;
    std::cout << "Matrix 2:" << std::endl;
    m2.show();
    std::cout<< std::endl;

    std::cout << "Matrix 1 + Matrix 2:" << std::endl;
    (m1+=m2).show();
    std::cout<< std::endl;

    std::cout << "Matrix 1 - Matrix 2:" << std::endl;
    (m1-=m2).show();
    std::cout<< std::endl;

    std::cout << "Matrix 1 * Matrix 2:" << std::endl;
    (m1*=m2).show();
    std::cout<< std::endl;

    Matrix<int> m3(3,3);
    m3.input();

    std::cout<<"\n\n";
    m3.show();

    std::cout<<"\n\n";
    Matrix<int> m4(3,3);
    m4.input();

    std::cout<<"\n\n";
    m4.show();
    std::cout<<"\n\n";

    std::cout << "Matrix 3 + Matrix 4:" << std::endl;
    (m3+=m4).show();
    std::cout<< std::endl;

    std::cout << "Matrix 3 - Matrix 4:" << std::endl;
    (m3-=m4).show();
    std::cout<< std::endl;

    std::cout << "Matrix 3 * Matrix 4:" << std::endl;
    (m3*=m4).show();
    std::cout<< std::endl;

    return 0;
}
