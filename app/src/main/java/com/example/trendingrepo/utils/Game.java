package com.example.trendingrepo.utils;

/**
 * $ |-| ! V @ M
 * Created by Shivam Pokhriyal on 2019-10-10.
 */


/*
Implement "swipe left" on a single row of tiles in the popular mobile game "2048".

The row will be given to you as a list of n numbers (in the actual game, n is always 4); 0 represents an empty tile, otherwise the number shown to the player is used (2, 4, 8, 16).
  ex: [0, 2, 4, 8]
When a player swipes left, all tiles "fall" to the left
  ex: [0, 2, 4, 8] => [2, 4, 8, 0]
two tiles of the same value that end up next to each other "collide" and are replaced with a tile of twice the value
  ex: [0, 2, 8, 8] => [2, 16, 0, 0]
if two "collisions" are possible, the left most one takes precedence
  ex: [0, 2, 2, 2] => [4, 2, 0, 0]
a collided tile cannot be involved in a second collision
  ex: [2, 2, 4, 0] => [4, 4, 0, 0]
  ex: [2, 2, 2, 2] => [4, 4, 0, 0]
*/



//class Game {
//    public int[] swipeLeft(int[] nums) {
//        int left = nums[0];
//        int leftPos = 0;
//        for (int i = 1; i < nums.length; i++) {
//            if (nums[i] == left) {
//                nums[leftPos] = nums[leftPos] * 2;
//                leftPos++;
//                left = 0;
//                nums[i] = 0;
//            } else if (nums[i] != 0 && left == 0) {
//                nums[leftPos] = nums[i];
//                left = nums[i];
//                nums[i] = 0;
//            } else if (nums[i] != 0 && left != 0) {
//                leftPos++;
//                nums[leftPos] = nums[i];
//                left = nums[i];
//                nums[i] = 0;
//            } else if (nums[i] != 0) {
//                left = nums[i];
//                leftPos = i;
//            }
//        }
//        return nums;
//    }
//
//    public void printArray(int[] org, int[] nums) {
//        System.out.println("-----------");
//        for (int i = 0; i < nums.length; i++) {
//            System.out.println(org[i] + " " + nums[i] + " " );
//        }
//        System.out.println("-----------");
//    }
//
//    public void runTests() {
//        printArray(new int[]{2, 4, 8, 0}, swipeLeft(new int[]{0, 2, 4, 8}));
//        printArray(new int[]{2, 16, 0, 0}, swipeLeft(new int[]{0, 2, 8, 8}));
//        printArray(new int[]{4, 2, 0, 0}, swipeLeft(new int[]{0, 2, 2, 2}));
//        printArray(new int[]{4, 4, 0, 0}, swipeLeft(new int[]{2, 2, 2, 2}));
//        printArray(new int[]{4, 4, 0, 0}, swipeLeft(new int[]{2, 2, 4, 0}));
//    }
//}


public class Game {
    public int[] swipeLeft(int[] nums) {
        int left = nums[0];
        int leftPos = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == left) {
                nums[leftPos] = nums[leftPos] * 2;
                leftPos++;
                left = 0;
                nums[i] = 0;
            } else if (nums[i] != 0 && left == 0) {
                nums[leftPos] = nums[i];
                left = nums[i];
                nums[i] = 0;
            } else if (nums[i] != 0 && left != 0) {
                leftPos++;
                nums[leftPos] = nums[i];
                left = nums[i];
                nums[i] = 0;
            } else if (nums[i] != 0) {
                left = nums[i];
                leftPos = i;
            }
        }
        return nums;
    }

    public boolean printArray(int[] org, int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            if (org[i] != nums[i]) {
                return false;
            }
        }
        return true;
    }

    public void runTests() {
        System.out.println(printArray(new int[]{2, 4, 8, 0}, swipeLeft(new int[]{0, 2, 4, 8})));
        System.out.println(printArray(new int[]{2, 16, 0, 0}, swipeLeft(new int[]{0, 2, 8, 8})));
        System.out.println(printArray(new int[]{4, 2, 0, 0}, swipeLeft(new int[]{0, 2, 2, 2})));
        System.out.println(printArray(new int[]{4, 4, 0, 0}, swipeLeft(new int[]{2, 2, 2, 2})));
        System.out.println(printArray(new int[]{4, 4, 0, 0}, swipeLeft(new int[]{2, 2, 4, 0})));
    }
}
