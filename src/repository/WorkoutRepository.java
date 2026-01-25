package repository;

import exception.DatabaseOperationException;
import model.CardioWorkout;
import model.StrengthWorkout;
import model.Workout;
import utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkoutRepository {

    // CREATE
    public int create(Workout w) {
        String sql = """
                INSERT INTO workouts(name, workout_type, duration_minutes, calories_burned, distance_km, total_sets, total_reps)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                RETURNING id
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, w.getName());
            ps.setString(2, w.getWorkoutType());
            ps.setInt(3, w.getDurationMinutes());
            ps.setInt(4, w.getCaloriesBurned());

            if (w instanceof CardioWorkout cw) {
                ps.setDouble(5, cw.getDistanceKm());
                ps.setNull(6, Types.INTEGER);
                ps.setNull(7, Types.INTEGER);
            } else if (w instanceof StrengthWorkout sw) {
                ps.setNull(5, Types.NUMERIC);
                ps.setInt(6, sw.getTotalSets());
                ps.setInt(7, sw.getTotalReps());
            } else {
                ps.setNull(5, Types.NUMERIC);
                ps.setNull(6, Types.INTEGER);
                ps.setNull(7, Types.INTEGER);
            }

            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt("id");

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to create workout", e);
        }
    }

    // READ ALL
    public List<Workout> getAll() {
        String sql = "SELECT * FROM workouts ORDER BY id";
        List<Workout> list = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRowToWorkout(rs));
            }
            return list;

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to get all workouts", e);
        }
    }

    // READ BY ID
    public Workout getById(int id) {
        String sql = "SELECT * FROM workouts WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) return null;
            return mapRowToWorkout(rs);

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to get workout by id=" + id, e);
        }
    }

    // UPDATE
    public boolean update(int id, Workout w) {
        String sql = """
                UPDATE workouts
                SET name=?, workout_type=?, duration_minutes=?, calories_burned=?, distance_km=?, total_sets=?, total_reps=?
                WHERE id=?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, w.getName());
            ps.setString(2, w.getWorkoutType());
            ps.setInt(3, w.getDurationMinutes());
            ps.setInt(4, w.getCaloriesBurned());

            if (w instanceof CardioWorkout cw) {
                ps.setDouble(5, cw.getDistanceKm());
                ps.setNull(6, Types.INTEGER);
                ps.setNull(7, Types.INTEGER);
            } else if (w instanceof StrengthWorkout sw) {
                ps.setNull(5, Types.NUMERIC);
                ps.setInt(6, sw.getTotalSets());
                ps.setInt(7, sw.getTotalReps());
            } else {
                ps.setNull(5, Types.NUMERIC);
                ps.setNull(6, Types.INTEGER);
                ps.setNull(7, Types.INTEGER);
            }

            ps.setInt(8, id);

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to update workout id=" + id, e);
        }
    }

    // DELETE
    public boolean delete(int id) {
        String sql = "DELETE FROM workouts WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to delete workout id=" + id, e);
        }
    }

    // helper: row -> object (полиморфизм)
    private Workout mapRowToWorkout(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String type = rs.getString("workout_type");
        int duration = rs.getInt("duration_minutes");
        int calories = rs.getInt("calories_burned");

        if ("CARDIO".equals(type)) {
            double distance = rs.getDouble("distance_km");
            return new CardioWorkout(id, name, duration, calories, distance);
        } else {
            int sets = rs.getInt("total_sets");
            int reps = rs.getInt("total_reps");
            return new StrengthWorkout(id, name, duration, calories, sets, reps);
        }
    }
}
