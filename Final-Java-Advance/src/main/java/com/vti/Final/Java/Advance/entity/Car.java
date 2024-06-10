//package com.vti.Final.Java.Advance.entity;
//
//import org.omg.CORBA.PUBLIC_MEMBER;
//
//public class Car {
//    private String color = "red";
//    private float price = 69000;
//
//    public void getName(){
//        System.out.println("Car");
//    }
//
//    public String getColor() {
//        return color;
//    }
//
//    public void setColor(String color) {
//        this.color = color;
//    }
//
//    public float getPrice() {
//        return price;
//    }
//
//    public void setPrice(float price) {
//        this.price = price;
//    }
//
//    public Car(){
//        color = "Blue";
//        price = 69;
//    }
//
//    public static class Toyota extends Car{
//        public void run_sound(){
//            System.out.println("Sound Toyota");
//        }
//
//        @Override
//        public void getName(){
//            System.out.println("Toyota");
//        }
//    }
//
//    public static void main(String[] args) {
//        Car car_vti = new Car();
//        System.out.println(car_vti.color);
//        car_vti.getName();
//
//        Toyota toyota = new Toyota();
//        System.out.println(toyota.color);
//        System.out.println(toyota.price);
//        toyota.getName();
//        toyota.run_sound();
//    }
//
//
//
//    public abstract class Animal {
//        // Phương thức trừu tượng
//        public abstract void makeSound();
//
//        // Phương thức thông thường
//        public void sleep() {
//            System.out.println("This animal sleeps.");
//        }
//    }
//
//    public class Fox extends Animal{
//        public void makeSound(){
//            System.out.println("Fox sound");
//        }
//    }
//
//
//}
//
//
//
//
