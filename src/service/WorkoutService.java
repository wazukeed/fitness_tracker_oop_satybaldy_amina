package service;

import exception.InvalidInputException;
import model.Workout;
import repository.WorkoutRepositoryImpl;
import repository.interfaces.WorkoutRepository;

import java.util.List;

public class WorkoutService {

    private final WorkoutRepository workoutRepo = new WorkoutRepositoryImpl();

    public int createWorkout(Workout workout) {
        try {
            workout.validate();
        } catch (IllegalArgumentException ex) {
            throw new InvalidInputException(ex.getMessage());
        }
        return workoutRepo.save(workout);
    }

    public List<Workout> getAll() {
        return workoutRepo.findAll();
    }

    public Workout getById(int id) {
        return workoutRepo.findById(id);
    }

    public void delete(int id) {
        workoutRepo.delete(id);
    }
}
