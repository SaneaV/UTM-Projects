#include <iostream>
#include "funtionality.h"

using namespace std;

int main() {
    bool isActive = true;
    int choice;
    Node *head = nullptr;

    do {
        cout << "\t\tMENU\n";
        cout << "1. Input country\n";
        cout << "2. Show countries (A-Z)\n";
        cout << "3. Show countries (Z-A)\n";
        cout << "4. Find a country\n";
        cout << "5. Number of countries\n";
        cout << "6. Tree height\n";
        cout << "7. Eliminate memory\n";
        cout << "8. Exit\n\n";

        cout << "Input your choice: ";
        cin >> choice;

        switch (choice) {
            case 1: {
                cout << "How do you want to input?\n";
                cout << "1. With recursion\n";
                cout << "2. With iteration\n\n";
                do {
                    cout << "Input your choice: ";
                    cin >> choice;
                } while (choice < 1 || choice > 2);

                choice == 1 ? insertRecursive(head, inputCountryData()) :
                insertIterative(head, inputCountryData());
                break;
            }
            case 2: {
                cout << "How do you want to print tree (A-Z)?\n";
                cout << "1. With recursion\n";
                cout << "2. With iteration\n\n";
                do {
                    cout << "Input your choice: ";
                    cin >> choice;
                } while (choice < 1 || choice > 2);

                choice == 1 ? showRecursiveLeftNodeRight(head) :
                showIterativeNodeRight(head);
                cout << endl;
                break;
            }
            case 3: {
                cout << "How do you want to print tree (Z-A)?\n";
                cout << "1. With recursion\n";
                cout << "2. With iteration\n\n";
                do {
                    cout << "Input your choice: ";
                    cin >> choice;
                } while (choice < 1 || choice > 2);

                choice == 1 ? showRecursiveRightNodeLeft(head) :
                showIterativeRightNodeLeft(head);
                cout << endl;
                break;
            }
            case 4: {
                cout << "How do you want to find a country?\n";
                cout << "1. With recursion\n";
                cout << "2. With iteration\n\n";
                do {
                    cout << "Input your choice: ";
                    cin >> choice;
                } while (choice < 1 || choice > 2);

                string countryName;
                cout << "Input country name: ";
                cin >> countryName;
                choice == 1 ? searchRecursive(head, countryName, nullptr) :
                searchIterative(head, countryName);
                break;
            }
            case 5: {
                cout << "How do you want to count numer of countries?\n";
                cout << "1. With recursion\n";
                cout << "2. With iteration\n\n";
                do {
                    cout << "Input your choice: ";
                    cin >> choice;
                } while (choice < 1 || choice > 2);

                int numberOfNodes = choice == 1 ? countNumberOfNodesRecursive(head) :
                                    countNumberOfNodesIterative(head);
                cout << "Number of countries is: " << numberOfNodes << "\n\n";
                break;
            }
            case 6: {
                cout << "How do you want to count calculate height of tree?\n";
                cout << "1. With recursion\n";
                cout << "2. With iteration\n\n";
                do {
                    cout << "Input your choice: ";
                    cin >> choice;
                } while (choice < 1 || choice > 2);

                int heightOfTree = choice == 1 ? calculateHeightRecursive(head) :
                                   calculateHeightIterative(head);
                cout << "Height of tree is: " << heightOfTree << "\n\n";
                break;
            }
            case 7: {
                cout << "How do you want to delete tree?\n";
                cout << "1. With recursion\n";
                cout << "2. With iteration\n\n";
                do {
                    cout << "Input your choice: ";
                    cin >> choice;
                } while (choice < 1 || choice > 2);

                choice == 1 ? deleteTreeRecursive(head) :
                deleteTreeIterative(head);
                head = nullptr;
                cout << endl;
                break;
            }
            case 8: {
                if (head != nullptr) deleteTreeRecursive(head);
                isActive = false;
                break;
            }
            default: {
                cout << "Please, input your choice between 1 - 8" << endl;
            }
        }

    } while (isActive);
    return 0;
}
