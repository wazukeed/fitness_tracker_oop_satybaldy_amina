package model;

public interface Validatable {
    void validate();

    default boolean isValid() {
        try {
            validate();
            return true;
        } catch (RuntimeException ex) {
            return false;
        }
    }

    static void requireNonBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be empty");
        }
    }
}

