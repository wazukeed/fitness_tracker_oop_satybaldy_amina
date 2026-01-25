package service;

import exception.DuplicateResourceException;
import exception.InvalidInputException;
import exception.ResourceNotFoundException;
import model.Workout;
import repository.WorkoutRepository;

import java.util.List;

public class WorkoutService {

    private final WorkoutRepository repo = new WorkoutRepository();

    public int createWorkout(Workout w) {
        // 1) validation
        try {
            w.validate();
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException(e.getMessage());
        }

        // 2) if name already exists -> UPDATE instead of throwing error
        var existing = repo.getAll().stream()
                .filter(x -> x.getName().equalsIgnoreCase(w.getName()))
                .findFirst();

        if (existing.isPresent()) {
            int existingId = existing.get().getId();
            repo.update(existingId, w);
            return existingId; // returns id of updated workout
        }

        // 3) otherwise create new
        return repo.create(w);
    }

    public List<Workout> getAll() {
        return repo.getAll();
    }

    public Workout getById(int id) {
        Workout w = repo.getById(id);
        if (w == null) throw new ResourceNotFoundException("Workout id=" + id + " not found");
        return w;
    }

    public void update(int id, Workout w) {
        try {
            w.validate();
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException(e.getMessage());
        }

        boolean ok = repo.update(id, w);
        if (!ok) throw new ResourceNotFoundException("Workout id=" + id + " not found for update");
    }

    public void delete(int id) {
        boolean ok = repo.delete(id);
        if (!ok) throw new ResourceNotFoundException("Workout id=" + id + " not found for delete");
    }
}
