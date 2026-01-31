package service;

import exception.DuplicateResourceException;
import exception.InvalidInputException;
import exception.ResourceNotFoundException;
import model.Exercise;
import repository.ExerciseRepositoryImpl;
import repository.WorkoutRepositoryImpl;
import repository.interfaces.ExerciseRepository;
import repository.interfaces.WorkoutRepository;

public class ExerciseService {

    private final ExerciseRepository exRepo = new ExerciseRepositoryImpl();
    private final WorkoutRepository workoutRepo = new WorkoutRepositoryImpl();

    public int createExercise(Exercise e) {
        try {
            e.validate();
        } catch (IllegalArgumentException ex) {
            throw new InvalidInputException(ex.getMessage());
        }

        // FK check: workout must exist
        try {
            workoutRepo.findById(e.getWorkoutId());
        } catch (ResourceNotFoundException rnfe) {
            throw new ResourceNotFoundException("Workout id=" + e.getWorkoutId() + " not found (FK)");
        }

        // duplicate check: same exercise name inside same workout
        boolean duplicate = exRepo.getByWorkoutId(e.getWorkoutId()).stream()
                .anyMatch(x -> x.getName().equalsIgnoreCase(e.getName()));

        if (duplicate) {
            throw new DuplicateResourceException("Exercise '" + e.getName() + "' already exists in workout " + e.getWorkoutId());
        }

        return exRepo.create(e);
    }

    public void deleteExercise(int id) {
        exRepo.delete(id);
    }
}
