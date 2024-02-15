package main;

import model.Product;
import utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Market {
    private static Scanner input = new Scanner(System.in);
    private static ArrayList<Product> products;
    private static Map<Product, Integer> cart;

    public static void main(String[] args) {
        products = new ArrayList<>();
        cart = new HashMap<>();
        menu();
    }

    private static void menu() {
        System.out.println("-----------------------------------------------------------------");
        System.out.println("----------------Welcome to Buy More Market ----------------------");
        System.out.println("-----------------------------------------------------------------");
        System.out.println("************** Select the operation you want to do **************");
        System.out.println("-----------------------------------------------------------------");
        System.out.println("|   Option 1 - Register   |");
        System.out.println("|   Option 2 - List   |");
        System.out.println("|   Option 3 - Buy   |");
        System.out.println("|   Option 4 - Cart   |");
        System.out.println("|   Option 5 - Leave   |");

        int option = input.nextInt();

        switch (option) {
            case 1:
                registerProducts();
                break;
            case 2:
                listProducts();
                break;
            case 3:
                buyProducts();
                break;
            case 4:
                seeCart();
                break;
            case 5:
                System.out.println("See you later :)");
                System.exit(0);
            default:
                System.out.println("Invalid Optiont");
                menu();
                break;
        }
    }

    private static void registerProducts() {
        System.out.println("Product name");
        String name = input.next();

        System.out.println("Product price");
        Double price = input.nextDouble();

        Product product = new Product(name, price);
        products.add(product);

        System.out.println(product.getName() + "Has been registered successfully");
        menu();
    }

    private static void listProducts() {
        if (products.size() > 0) {
            System.out.println("Product list! \n");
            for (Product p : products) {
                System.out.println(p);
            }
        } else {
            System.out.println("No products registered");
        }
        menu();
    }

    private static void buyProducts() {
        if (products.size() > 0) {
            System.out.println("Product code: \n");
            System.out.println("----------------------------- Available products -----------------------------");
            for (Product p : products) {
                System.out.println(p + "\n");
                System.out.println("\nEnter ID product:");
            }
            int id = Integer.parseInt(input.next());
            boolean isPresent = false;

            for (Product p : products) {
                if (p.getId() == id) {
                    int qtt = 0;
                    try {
                        qtt = cart.get(p);
                        // checks if the product is in the cart, increment quantity
                        cart.put(p, qtt + 1);
                    } catch (NullPointerException e) {
                        // if the product it the first on the cart
                        cart.put(p, 1);
                    }

                    System.out.println(p.getName() + " Added to cart.");
                    isPresent = true;

                    if (isPresent) {
                        System.out.println("Do you want to add another product to the cart ?");
                        System.out.println("Enter 1 for Yes or 0 to finally purchase. \n");
                        int option = Integer.parseInt(input.next());

                        if (option == 1) {
                            buyProducts();
                        } else {
                            finalizePurchase();
                        }
                    }
                } else {
                    System.out.println("Product not found");
                    menu();
                }
            }
        }else{
            System.out.println("There are no registered products");
            menu();
        }
    }

    private static void seeCart(){
        System.out.println("--- Products on your cart ---");
        if (cart.size() > 0){
            for (Product p : cart.keySet()){
                System.out.println("Product: " + p + "\nQuantity: " + cart.get(p));
            }
        }else {
            System.out.println("Empty cart");
            menu();
        }
    }
    private static void finalizePurchase(){
        Double purchaseValue = 0.0;
        System.out.println("Your products!");

        for (Product p : cart.keySet()){
            int qtt = cart.get(p);
            purchaseValue += p.getPrice() * qtt;
            System.out.println(p);
            System.out.println("Quantity: " + qtt);
            System.out.println("---------------");
        }
        System.out.println("The value of your purchase is: " + Utils.doubleToString(purchaseValue));
        cart.clear();

        System.out.println("Thx for your preference");
    }
}