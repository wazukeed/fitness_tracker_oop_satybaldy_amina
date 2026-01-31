package controller;

import exception.DuplicateResourceException;
import exception.InvalidInputException;
import exception.ResourceNotFoundException;
import model.CardioWorkout;
import model.Exercise;
import model.StrengthWorkout;
import model.Workout;
import service.ExerciseService;
import service.WorkoutService;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final WorkoutService workoutService = new WorkoutService();
    private static final ExerciseService exerciseService = new ExerciseService();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n=== FITNESS TRACKER API ===");
            System.out.println("1) Add Cardio Workout");
            System.out.println("2) Add Strength Workout");
            System.out.println("3) List Workouts");
            System.out.println("4) Add Exercise to Workout");
            System.out.println("5) Delete Workout");
            System.out.println("0) Exit");

            int choice = readInt("Choose: ");

            try {
                switch (choice) {
                    case 1 -> addCardio();
                    case 2 -> addStrength();
                    case 3 -> listWorkouts();
                    case 4 -> addExercise();
                    case 5 -> deleteWorkout();
                    case 0 -> System.exit(0);
                    default -> System.out.println("Unknown option");
                }
            } catch (InvalidInputException | ResourceNotFoundException | DuplicateResourceException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.next().trim();
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.next().trim();
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a decimal number");
            }
        }
    }

    private static void addCardio() {
        System.out.print("Name: ");
        String name = sc.next();
        int duration = readInt("Duration: ");
        int calories = readInt("Calories: ");
        double dist = readDouble("Distance km: ");

        workoutService.createWorkout(new CardioWorkout(0, name, duration, calories, dist));
        System.out.println("Cardio workout added");
    }

    private static void addStrength() {
        System.out.print("Name: ");
        String name = sc.next();
        int duration = readInt("Duration: ");
        int calories = readInt("Calories: ");
        int sets = readInt("Sets: ");
        int reps = readInt("Reps: ");

        workoutService.createWorkout(new StrengthWorkout(0, name, duration, calories, sets, reps));
        System.out.println("Strength workout added");
    }

    private static void listWorkouts() {
        List<Workout> list = workoutService.getAll();
        if (list.isEmpty()) {
            System.out.println("(empty)");
            return;
        }
        list.forEach(w -> System.out.println(w.describe()));
    }

    private static void addExercise() {
        int wid = readInt("Workout ID: ");
        System.out.print("Exercise name: ");
        String name = sc.next();
        int sets = readInt("Sets: ");
        int reps = readInt("Reps: ");

        exerciseService.createExercise(new Exercise(0, name, wid, sets, reps));
        System.out.println("Exercise added");
    }

    private static void deleteWorkout() {
        int id = readInt("Workout ID to delete: ");
        workoutService.delete(id);
        System.out.println("Workout deleted");
    }
}
