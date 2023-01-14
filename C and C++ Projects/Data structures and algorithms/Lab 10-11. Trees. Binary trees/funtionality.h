#include <iostream>
#include <iomanip>
#include <fstream>

struct Country {
    char name[50];
    char capital[50];
    int area;
    int population;
    int grossDomesticProduct;
};

class Node {

    Node() {}

public:
    explicit Node(Country *country) : country(*country) {}

    Country country{};
    Node *left = nullptr;
    Node *right = nullptr;
};

void insertRecursive(Node *&root, Country *country);

void insertIterative(Node *&root, Country *country);

void showRecursiveLeftNodeRight(Node *root);

void showIterativeNodeRight(Node *root);

void showRecursiveRightNodeLeft(Node *root);

void showIterativeRightNodeLeft(Node *root);

void searchRecursive(Node *root, std::string country, Node *parent);

void searchIterative(Node *root, std::string country);

int countNumberOfNodesIterative(Node *root);

int countNumberOfNodesRecursive(Node *root);

int calculateHeightIterative(Node *root);

int calculateHeightRecursive(Node *root);

void deleteTreeRecursive(Node *root);

void deleteTreeIterative(Node *root);

Country *inputCountryData();
