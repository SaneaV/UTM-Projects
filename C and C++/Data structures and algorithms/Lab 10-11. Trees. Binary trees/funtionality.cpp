#include <stack>
#include <queue>
#include <cstring>
#include "funtionality.h"

void insertRecursive(Node *&root, Country *country) {
    if (root == nullptr) {
        root = new Node(country);
        return;
    }

    if (strcmp(country->name, root->country.name) < 0) {
        insertRecursive(root->left, country);
    } else {
        insertRecursive(root->right, country);
    }
}

void insertIterative(Node *&root, Country *country) {
    Node *curr = root;
    Node *parent = nullptr;

    if (root == nullptr) {
        root = new Node(country);
        return;
    }

    while (curr != nullptr) {
        parent = curr;

        if (strcmp(country->name, curr->country.name) < 0) {
            curr = curr->left;
        } else {
            curr = curr->right;
        }
    }

    if (strcmp(country->name, parent->country.name) < 0) {
        parent->left = new Node(country);
    } else {
        parent->right = new Node(country);
    }
}

void showRecursiveLeftNodeRight(Node *root) {
    if (root == nullptr) {
        return;
    }
    showRecursiveLeftNodeRight(root->left);
    std::cout << std::left << std::setw(10) << root->country.name << "\t" << std::setw(10) << root->country.capital
              << "\t" << std::right << std::setw(10) << root->country.area << "\t" << std::setw(10)
              << root->country.population << "\t" << std::setw(10) << root->country.grossDomesticProduct << std::endl;
    showRecursiveLeftNodeRight(root->right);
}

void showIterativeNodeRight(Node *root) {
    std::stack<Node *> s;
    Node *curr = root;

    while (curr != nullptr || !s.empty()) {
        while (curr != nullptr) {
            s.push(curr);
            curr = curr->left;
        }
        curr = s.top();
        s.pop();

        std::cout << std::left << std::setw(10) << curr->country.name << "\t" << std::setw(10) << curr->country.capital
                  << "\t" << std::right << std::setw(10) << curr->country.area << "\t" << std::setw(10)
                  << curr->country.population << "\t" << std::setw(10) << curr->country.grossDomesticProduct
                  << std::endl;

        curr = curr->right;
    }
}

void showRecursiveRightNodeLeft(Node *root) {
    if (root == nullptr) {
        return;
    }
    showRecursiveRightNodeLeft(root->right);
    std::cout << std::left << std::setw(10) << root->country.name << "\t" << std::setw(10) << root->country.capital
              << "\t" << std::right << std::setw(10) << root->country.area << "\t" << std::setw(10)
              << root->country.population << "\t" << std::setw(10) << root->country.grossDomesticProduct << std::endl;
    showRecursiveRightNodeLeft(root->left);
}

void showIterativeRightNodeLeft(Node *root) {
    std::stack<Node *> s;
    Node *curr = root;

    while (curr != nullptr || !s.empty()) {
        while (curr != nullptr) {
            s.push(curr);
            curr = curr->right;
        }
        curr = s.top();
        s.pop();

        std::cout << std::left << std::setw(10) << curr->country.name << "\t" << std::setw(10) << curr->country.capital
                  << "\t" << std::right << std::setw(10) << curr->country.area << "\t" << std::setw(10)
                  << curr->country.population << "\t" << std::setw(10) << curr->country.grossDomesticProduct
                  << std::endl;

        curr = curr->left;
    }
}

void searchRecursive(Node *root, std::string country, Node *parent) {
    if (root == nullptr) {
        std::cout << "Key not found";
        return;
    }

    if (strcmp(root->country.name, country.c_str()) == 0) {
        if (parent == nullptr) {
            std::cout << "The node with key " << country << " is root node" << "\n\n";
        } else if (strcmp(country.c_str(), parent->country.name) < 0) {
            std::cout << "The given key is the left node of the node with key " << parent->country.name << "\n\n";
        } else {
            std::cout << "The given key is the right node of the node with key " << parent->country.name << "\n\n";
        }

        return;
    }

    if (strcmp(country.c_str(), root->country.name) < 0) {
        searchRecursive(root->left, country, root);
    } else {
        searchRecursive(root->right, country, root);
    }
}

void searchIterative(Node *root, std::string country) {
    Node *curr = root;

    Node *parent = nullptr;

    while (curr != nullptr && strcmp(curr->country.name, country.c_str()) != 0) {
        parent = curr;

        if (strcmp(country.c_str(), curr->country.name) < 0) {
            curr = curr->left;
        } else {
            curr = curr->right;
        }
    }

    if (curr == nullptr) {
        std::cout << "Key not found";
        return;
    }

    if (parent == nullptr) {
        std::cout << "The node with key " << country << " is root node" << "\n\n";
    } else if (strcmp(country.c_str(), parent->country.name) < 0) {
        std::cout << "The given key is the left node of the node with key " << parent->country.name << "\n\n";
    } else {
        std::cout << "The given key is the right node of the node with key " << parent->country.name << "\n\n";
    }
}

int countNumberOfNodesIterative(Node *root) {
    if (!root) {
        return 0;
    }

    int counter = 0;
    std::stack<Node *> v;
    v.push(root);
    while (v.size() > 0) {
        Node *n = v.top();
        v.pop();
        if (n != nullptr) {
            counter++;
            v.push(n->left);
            v.push(n->right);
        }
    }
    return counter;
}

int countNumberOfNodesRecursive(Node *root) {
    if (root == nullptr) {
        return 0;
    }

    return 1 + countNumberOfNodesRecursive(root->left) + countNumberOfNodesRecursive(root->right);
}

void deleteTreeRecursive(Node *root) {
    if (root == nullptr) { return; }

    deleteTreeRecursive(root->left);
    deleteTreeRecursive(root->right);

    std::cout << "\n Deleting node: " << root->country.name;
    delete root;
}

void deleteTreeIterative(Node *root) {
    if (root == nullptr) {
        return;
    }
    std::queue<Node *> q;

    q.push(root);
    while (!q.empty()) {
        Node *node = q.front();
        q.pop();

        if (node->left != nullptr)
            q.push(node->left);
        if (node->right != nullptr)
            q.push(node->right);

        std::cout << "\n Deleting node: " << node->country.name;
        free(node);
    }
}

int calculateHeightIterative(Node *root) {
    std::queue<Node *> nodesInLevel;
    int height = 0;
    int nodeCount = 0;
    Node *currentNode;
    if (root == nullptr) {
        return -1;
    }
    nodesInLevel.push(root);
    while (!nodesInLevel.empty()) {
        height++;

        nodeCount = nodesInLevel.size();
        while (nodeCount--) {
            currentNode = nodesInLevel.front();

            if (currentNode->left != nullptr) {
                nodesInLevel.push(currentNode->left);
            }

            if (currentNode->right != NULL) {
                nodesInLevel.push(currentNode->right);
            }

            nodesInLevel.pop();
        }
    }
    return height;
}

int calculateHeightRecursive(Node *root) {
    if (root == nullptr) { return 0; }
    else {
        int lDepth = calculateHeightRecursive(root->left);
        int rDepth = calculateHeightRecursive(root->right);

        if (lDepth > rDepth) { return lDepth + 1; }
        else { return rDepth + 1; }
    }
}

Country *inputCountryData() {
    auto *country = new Country;

    std::cout << "Input country name: ";
    std::cin >> country->name;

    std::cout << "Input country capital: ";
    std::cin >> country->capital;

    std::cout << "Input country area: ";
    std::cin >> country->area;

    std::cout << "Input country population: ";
    std::cin >> country->population;

    std::cout << "Input country gross domestic product: ";
    std::cin >> country->grossDomesticProduct;

    return country;
}