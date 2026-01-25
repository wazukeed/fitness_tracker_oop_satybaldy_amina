package repository;

import exception.DatabaseOperationException;
import model.Exercise;
import utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExerciseRepository {

    public int create(Exercise e) {
        String sql = """
                INSERT INTO exercises(workout_id, name, sets, reps)
                VALUES (?, ?, ?, ?)
                RETURNING id
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, e.getWorkoutId());
            ps.setString(2, e.getName());
            ps.setInt(3, e.getSets());
            ps.setInt(4, e.getReps());

            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt("id");

        } catch (SQLException ex) {
            throw new DatabaseOperationException("Failed to create exercise", ex);
        }
    }

    public List<Exercise> getAll() {
        String sql = "SELECT * FROM exercises ORDER BY id";
        List<Exercise> list = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
            return list;

        } catch (SQLException ex) {
            throw new DatabaseOperationException("Failed to get all exercises", ex);
        }
    }

    public List<Exercise> getByWorkoutId(int workoutId) {
        String sql = "SELECT * FROM exercises WHERE workout_id=? ORDER BY id";
        List<Exercise> list = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, workoutId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
            return list;

        } catch (SQLException ex) {
            throw new DatabaseOperationException("Failed to get exercises for workoutId=" + workoutId, ex);
        }
    }

    public boolean update(int id, Exercise e) {
        String sql = """
                UPDATE exercises
                SET workout_id=?, name=?, sets=?, reps=?
                WHERE id=?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, e.getWorkoutId());
            ps.setString(2, e.getName());
            ps.setInt(3, e.getSets());
            ps.setInt(4, e.getReps());
            ps.setInt(5, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            throw new DatabaseOperationException("Failed to update exercise id=" + id, ex);
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM exercises WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            throw new DatabaseOperationException("Failed to delete exercise id=" + id, ex);
        }
    }

    private Exercise mapRow(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int workoutId = rs.getInt("workout_id");
        String name = rs.getString("name");
        int sets = rs.getInt("sets");
        int reps = rs.getInt("reps");
        return new Exercise(id, name, workoutId, sets, reps);
    }
}
