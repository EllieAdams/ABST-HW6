import tester.Tester;
import java.util.Comparator; 

public abstract class ABST<T> {
  Comparator<T> order;
  
  ABST(Comparator<T> order) { 
    this.order = order;
  }
  
  //inserts object where it belongs based on comparisons
  abstract ABST<T> insert(T object);
  
  //is the object given in the BST?
  abstract boolean present(T object);
  
  //get leftmost object contained in the BST 
  abstract T getLeftMost();

  //helper for getting the leftmost object
  abstract T getLeftMostHelper(T data);
  
  //returns all but the leftmost object in the BST
  abstract ABST<T> getRight();
  
  //helper for getting all but the leftmost object in BST
  abstract ABST<T> getRightHelper(ABST<T> right, T data);
  
  //is this ABST<T> the same as the given one?
  abstract boolean sameTree(ABST<T> other);
}

//to represent a book 
class Book {
  String title;
  String author;
  int price; 
  
  Book(String title, String author, int price) {
    this.title = title;
    this.author = author;
    this.price = price;
  }
}

//to order books by their titles alphabetically
class BooksByTitle implements Comparator<Book> {

  //sorts titles of two books alphabetically
  public int compare(Book o1, Book o2) {
    return o1.title.compareTo(o2.title);
  }
  
}

//to order books by their authors alphabetically
class BooksByAuthor implements Comparator<Book> {

  //sorts two books by their authors alphabetically
  public int compare(Book o1, Book o2) {
    return o1.author.compareTo(o2.author);
  }
  
}

//to order books by their prices low to high
class BooksByPrice implements Comparator<Book> {

  //sorts two books by their prices low to high
  public int compare(Book o1, Book o2) {
    if (o1.price > o2.price) {
      return 1;
    }
    else if (o1.price == o2.price) {
      return 0;
    }
    else {
      return -1;
    }
  }
}

// represents a leaf in a binary search tree
class Leaf<T> extends ABST<T> {
 
  Leaf(Comparator<T> order) {
    super(order);
  }

  // end of the tree, so return the tree with the object as its data
  ABST<T> insert(T object) {
    return new Node<T>(this.order, object, this, this);
  }

  // reached the end of the tree so the object is not there
  boolean present(T object) {
    return false;
  }

  // throws an exception because there is no left object in an empty BST
  T getLeftMost() {
    throw new RuntimeException("No leftmost item of an empty tree");
  }

  // end of the left side so returns the data
  T getLeftMostHelper(T data) {
    return data;
  }

  //throws an exception because there is no right of an empty BST
  ABST<T> getRight() {
    throw new RuntimeException("No right of an empty tree");
  }

  // reached the leftmost, so return the right data
  ABST<T> getRightHelper(ABST<T> right, T data) {
    return right;
  }

  @Override
  boolean sameTree(ABST<T> other) {
    // TODO Auto-generated method stub
    return false;
  }
}

// represents a node in a binary search tree
class Node<T> extends ABST<T> {
  T data;
  ABST<T> left;
  ABST<T> right;
  
  Node(Comparator<T> order, T data, ABST<T> left, ABST<T> right) {
    super(order);
    this.data = data;
    this.left = left;
    this.right = right;
    
  }

  // inserts the object on either the right or left node of the BST
  ABST<T> insert(T object) {
    if (order.compare(this.data, object) < 0) {
      return this.left.insert(object);
    }
    else {
      return this.right.insert(object); // do we need to add a <> to parameterize the return statement??
    }
  }

  // checks if the object is in the BST
  boolean present(T object) {
    if (order.compare(this.data,object) == 0) {
      return true;
    }
    else if (order.compare(this.data,object) < 0) {
      return this.left.present(object);
    }
    else {
      return this.right.present(object);
    }
  }

  // finds the left most object in a BST
  T getLeftMost() {
    return this.left.getLeftMostHelper(this.data);
  }

  // goes though the left of the BST until a leaf is found
  T getLeftMostHelper(T data) {
    return this.left.getLeftMostHelper(this.data);
  }

  //returns all but the leftmost item in the BST
  ABST<T> getRight() {
    return this.left.getRightHelper(this.right, this.data);
  }

  // helper for getRight
  ABST<T> getRightHelper(ABST<T> right, T data) {
    // TODO Auto-generated method stub
    return new Node<T>(this.order, data, this.left.getRight(), right);
  }

  @Override
  boolean sameTree(ABST<T> other) {
    // TODO Auto-generated method stub
    return false;
  }
}

class ExamplesBST {
  // book examples 
  Book harryPotter1 = new Book("Sorcerer's Stone", "Kellie Laflin", 10);
  Book harryPotter2 = new Book("Chamber of Secrets", "Ellie Adams", 12);
  Book harryPotter3 = new Book("Prisoner of Askaban", "JK Rowling", 9);
  // same author as harryPotter3
  Book harryPotter4 = new Book("Goblet of Fire", "JK Rowling", 11);
  // same price as harryPotter2
  Book harryPotter5 = new Book("Order of the Pheonix", "Sally Smith", 12);
  // same price as harryPotter1, same author as harryPotter3, and harryPotter4
  Book harryPotter6 = new Book("Half Blood Price", "JK Rowling", 10);
  // same title as harryPotter1, same author as harryPotter3, harryPotter4, and harryPotter6
  Book harryPotter7 = new Book("Sorcerer's Stone", "JK Rowling", 15);
  
  // leaf examples
  // comparator for titles
  Leaf<Book> leafCompTitles = new Leaf<Book>(new BooksByTitle());
  // comparator for authors
  Leaf<Book> leafCompAuthors = new Leaf<Book>(new BooksByAuthor());
  // comparator for prices
  Leaf<Book> leafCompPrices = new Leaf<Book>(new BooksByPrice());
  
  
  // examples for using title comparator 
  // a node for hp7 with two leaves
  Node<Book> hp7 = new Node<Book>(new BooksByTitle(), this.harryPotter7, this.leafCompTitles, this.leafCompTitles);
  // a node that contains books of the same title
  Node<Book> sameTitle = new Node<Book>(new BooksByTitle(), this.harryPotter1, this.leafCompTitles, this.hp7);
  // a node that contains books of all different titles
  Node<Book> difTitles1 = new Node<Book>(new BooksByTitle(), this.harryPotter2, this.leafCompTitles, this.hp7);
  Node<Book> difTitles2 = new Node<Book>(new BooksByTitle(), this.harryPotter5, this.leafCompTitles, this.difTitles1);
  

  
}