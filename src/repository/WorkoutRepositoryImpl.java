package repository;

import exception.DatabaseOperationException;
import exception.ResourceNotFoundException;
import model.CardioWorkout;
import model.StrengthWorkout;
import model.Workout;
import repository.interfaces.WorkoutRepository;
import utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkoutRepositoryImpl implements WorkoutRepository {

    private final Connection connection;

    public WorkoutRepositoryImpl() {
        try {
            this.connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Cannot connect to database: " + e.getMessage(), e);
        }
    }

    @Override
    public int save(Workout workout) {
        String sql = """
                INSERT INTO workouts(name, workout_type, duration_minutes, calories_burned, distance, total_sets, total_reps)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, workout.getName());
            ps.setString(2, workout.getWorkoutType());
            ps.setInt(3, workout.getDurationMinutes());
            ps.setInt(4, workout.getCaloriesBurned());

            if (workout instanceof CardioWorkout cw) {
                ps.setDouble(5, cw.getDistanceKm());
                ps.setNull(6, Types.INTEGER);
                ps.setNull(7, Types.INTEGER);
            } else if (workout instanceof StrengthWorkout sw) {
                ps.setNull(5, Types.NUMERIC);
                ps.setInt(6, sw.getTotalSets());
                ps.setInt(7, sw.getTotalReps());
            } else {
                throw new DatabaseOperationException("Unknown workout subtype: " + workout.getClass().getSimpleName());
            }

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }

            throw new DatabaseOperationException("Failed to get generated id");
        } catch (SQLException e) {
            throw new DatabaseOperationException("Save workout failed: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Workout> findAll() {
        String sql = "SELECT * FROM workouts ORDER BY id";
        List<Workout> workouts = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                workouts.add(mapRowToWorkout(rs));
            }
            return workouts;

        } catch (SQLException e) {
            throw new DatabaseOperationException("Find all workouts failed: " + e.getMessage(), e);
        }
    }

    @Override
    public Workout findById(int id) {
        String sql = "SELECT * FROM workouts WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRowToWorkout(rs);
            }

            throw new ResourceNotFoundException("Workout with id=" + id + " not found");
        } catch (SQLException e) {
            throw new DatabaseOperationException("Find workout by id failed: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(int id) {
        // ensure exists
        findById(id);

        String sql = "DELETE FROM workouts WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Delete workout failed: " + e.getMessage(), e);
        }
    }

    private Workout mapRowToWorkout(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String type = rs.getString("workout_type");

        int durationMinutes = rs.getInt("duration_minutes");
        int caloriesBurned = rs.getInt("calories_burned");

        if ("CARDIO".equalsIgnoreCase(type)) {
            double distance = rs.getDouble("distance"); // ✅ правильное имя
            return new CardioWorkout(id, name, durationMinutes, caloriesBurned, distance);
        }

        if ("STRENGTH".equalsIgnoreCase(type)) {
            int totalSets = rs.getInt("total_sets");
            int totalReps = rs.getInt("total_reps");
            return new StrengthWorkout(id, name, durationMinutes, caloriesBurned, totalSets, totalReps);
        }

        throw new DatabaseOperationException("Unknown workout type in DB: " + type);
    }

}
