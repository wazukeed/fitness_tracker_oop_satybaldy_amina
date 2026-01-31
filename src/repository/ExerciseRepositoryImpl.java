package repository;

import exception.DatabaseOperationException;
import exception.ResourceNotFoundException;
import model.Exercise;
import repository.interfaces.ExerciseRepository;
import utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExerciseRepositoryImpl implements ExerciseRepository {

    private final Connection connection;

    public ExerciseRepositoryImpl() {
        try {
            this.connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Cannot connect to database: " + e.getMessage(), e);
        }
    }

    @Override
    public int create(Exercise e) {
        String sql = """
                INSERT INTO exercises(name, workout_id, sets, reps)
                VALUES (?, ?, ?, ?)
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, e.getName());
            ps.setInt(2, e.getWorkoutId());
            ps.setInt(3, e.getSets());
            ps.setInt(4, e.getReps());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
            throw new DatabaseOperationException("Failed to get generated id");
        } catch (SQLException ex) {
            throw new DatabaseOperationException("Create exercise failed: " + ex.getMessage(), ex);
        }
    }

    @Override
    public List<Exercise> getByWorkoutId(int workoutId) {
        String sql = "SELECT * FROM exercises WHERE workout_id = ? ORDER BY id";
        List<Exercise> list = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, workoutId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Exercise(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("workout_id"),
                            rs.getInt("sets"),
                            rs.getInt("reps")
                    ));
                }
            }
            return list;
        } catch (SQLException ex) {
            throw new DatabaseOperationException("Get exercises by workoutId failed: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void delete(int id) {
        String check = "SELECT id FROM exercises WHERE id = ?";
        String del = "DELETE FROM exercises WHERE id = ?";

        try (PreparedStatement ps1 = connection.prepareStatement(check)) {
            ps1.setInt(1, id);
            try (ResultSet rs = ps1.executeQuery()) {
                if (!rs.next()) throw new ResourceNotFoundException("Exercise with id=" + id + " not found");
            }
        } catch (SQLException ex) {
            throw new DatabaseOperationException("Check exercise failed: " + ex.getMessage(), ex);
        }

        try (PreparedStatement ps2 = connection.prepareStatement(del)) {
            ps2.setInt(1, id);
            ps2.executeUpdate();
        } catch (SQLException ex) {
            throw new DatabaseOperationException("Delete exercise failed: " + ex.getMessage(), ex);
        }
    }
}
