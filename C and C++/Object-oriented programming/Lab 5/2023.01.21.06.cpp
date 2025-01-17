/*

Создайте общую функцию (шаблон) для поиска второго наименьшего элемента по размеру в элементах списка.
Например: список - 0 2 3 4 3 6, результат - 2. Функция должна работать с одномерными массивами разной длины

*/
#include <iostream>
#include <limits>

template <typename T>
T secondSmallest(T arr[], int size)
{
    T first = std::numeric_limits<T>::max(); // initializing first with maximum value
    T second = std::numeric_limits<T>::max(); // initializing second with maximum value

    for (int i = 0; i < size; i++)
    {
        if (arr[i] < first)
        {
            second = first;
            first = arr[i];
        }
        else if (arr[i] < second && arr[i] != first)
        {
            second = arr[i];
        }
    }
    if (second == std::numeric_limits<T>::max())
    {
        return -1; // if all elements are same or size < 2
    }
    return second;
}

int main()
{
    int arr[] = {0, 5, 3, 4, 3, 1};
    std::cout << "Second smallest element: " << secondSmallest(arr, sizeof(arr)) << std::endl;

    int size = 0;
    std::cout << "Enter size of array: ";
    std::cin >> size;
    int *arr2 = new int[size];
    for (int i = 0; i < size; i++)
    {
        std::cout << "Enter elements of array["<<i<<"]: ";
        std::cin >> arr2[i];
    }
    std::cout << "Second smallest element: " << secondSmallest(arr2, size) << std::endl;
    delete[] arr;

    return 0;
}
