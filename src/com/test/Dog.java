package com.test;

public class Dog {
    private int age = 0;
    private String color = "white";
    private int food = 2;
    static int total = 0;

    public Dog(String color, int age) {
        this.age = age;
        this.color = color;
        this.total++;
    }

    public String barking() {

        if (hungry()) {
            return "시무룩";
        }

        this.food--;
        return "멍멍";
    }

    public boolean hungry() {
        if (food < 1) {
            return true;
        }
        return false;
    }

    public boolean sleeping() {
        return true;
    }

    public boolean sleeping(int food) {
        this.food += food;
        return false;
    }

    public int getTotal() {
        return total;
    }

    public int getFood() {
        return food;
    }
}