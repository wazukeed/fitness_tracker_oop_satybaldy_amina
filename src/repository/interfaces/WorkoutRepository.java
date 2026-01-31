package repository.interfaces;

import model.Workout;
import java.util.List;

public interface WorkoutRepository {
    int save(Workout workout);         // INSERT -> returns generated id
    List<Workout> findAll();
    Workout findById(int id);
    void delete(int id);
}

