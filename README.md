README 

A. Project Overview

•Purpose of the API

	This project is a console-based Fitness Tracker API that allows users to manage
	workouts and exercises using a layered Java architecture and a PostgreSQL database 
	through JDBC.

	The system supports:

		•creating workouts (Cardio / Strength)
		•listing workouts
		•adding exercises to workouts
		•deleting workouts
		•validation + error handling using custom exceptions
	
•Summary of Entities and Their Relationships

	Entities:
		•Workout — base workout entity stored in DB
		•CardioWorkout — workout with distance (km)
		•StrengthWorkout — workout with sets/reps and exercises count
		•Exercise — an exercise attached to a workout
		
	Relationship:
	
		•One Workout can have many Exercises (Workout 1 -> * Exercise)
		•Each Exercise must reference an existing workout via workout_id (FK).
		
•OOP Design Overview (Layers)

	The program follows a multi-layer structure:
		controller: Main.java (menu + input + calls services)
		service: business logic + validation + exceptions
		repository: JDBC queries (CRUD, SQL)
		model: entities + interface
		exception: custom error types
		utils: DB connection helper (DatabaseConnection)
		

B. OOP Design Documentation

•Abstract Class and Subclasses (Inheritance)

	Workouts share common fields such as id, name, durationMinutes, 
	caloriesBurned, so the project uses a shared base design:
		•Workout (base class)
		•CardioWorkout extends Workout
			•adds: distanceKm
		•StrengthWorkout extends Workout
			•adds: sets, reps
			•also tracks exercises (count / list depending on your implementation)
			
•Interfaces and Implemented Methods

	The project includes:
	
	Validatable interface
	
	public interface Validatable {
    	void validate();
	}
	
	Implemented by:
		•Workout
		•Exercise
	Each class validates its own data, for example:
		•duration must be in range 1..300
		•calories must be positive
		•distance must be positive
		•sets/reps must be positive

•Composition / Aggregation

	The project uses composition:
		Workout HAS-A Exercises
	Meaning:
		•Exercises belong to workouts
		•A workout may contain multiple exercises
		•Exercises are linked by workout_id

•Polymorphism Examples

	Polymorphism happens when the system stores different workout types as Workout:
		List<Workout> workouts = workoutService.getAllWorkouts();

	This list can contain both:
		•CardioWorkout
		•StrengthWorkout

• UML diagram 

<img width="671" height="754" alt="uml" src="https://github.com/user-attachments/assets/ca994bde-a8ec-4930-bf23-808c10957770" />


		

•C. Database Description

	•Schema, Constraints, Foreign Keys
	Database tables used:
	Table: workouts
	Stores general workout info
	
	Example columns:
		•workout_id (PK)
		•name (unique recommended)
		•type (CARDIO / STRENGTH)
		•duration_minutes
		•calories_burned
		•distance_km (only for cardio; can be NULL)
		•sets, reps (only for strength; can be NULL)
		
		
	Table: exercises
	Stores exercises linked to workouts.
	Columns:
		•exercise_id (PK)
		•workout_id (FK → workouts.workout_id)
		•name
		•sets
		•reps
	Foreign key:
		•exercises.workout_id references workouts(workout_id)
		•If workout does not exist → FK error handled in service 
		
		
	•Sample SQL Inserts
	
		INSERT INTO workouts (name, type, duration_minutes, calories_burned, distance_km)
		VALUES ('Morning Run', 'CARDIO', 30, 300, 5.0);

		INSERT INTO workouts (name, type, duration_minutes, calories_burned, sets, reps)
		VALUES ('Leg Day', 'STRENGTH', 45, 450, 4, 12);

		INSERT INTO exercises (workout_id, name, sets, reps)
		VALUES (2, 'Squats', 4, 10);
		
		
		
•D. Controller (CRUD Summary with Examples)

	Controller: src/controller/Main.java
	Menu:
		•Add Cardio Workout
		•Add Strength Workout
		•List Workouts
		•Add Exercise to Workout
		•Delete Workout
		•Exit
	CRUD operations demonstrated:
	CREATE
		•Create cardio workout
		•Create strength workout
		•Create exercise (added to workout)
	READ
		•List all workouts
	DELETE
		•Delete workout by ID
		
		
	Example requests/responses (console)
	Create Cardio
	Input:
		•Name: run
		•Duration: 20
		•Calories: 200
		•Distance: 2
	Output:
		•Cardio workout added 
	Read workouts
	Output example:
		•[WORKOUT] 5: run | type=CARDIO | duration=20 | calories=200 | distanceKm=2.0
	Add Exercise
	Input:
		•Workout ID: 6
		•Exercise name: squats
		•Sets: 4
		•Reps: 10
	Output:
		•Exercise added
	Delete
	Input:
		•Workout ID to delete: 6
		•Output:
		•Workout deleted


•E. Instructions to Compile and Run 

	Option 1: Run in IntelliJ IDEA
		Open project in IntelliJ
		Make sure PostgreSQL driver is connected (or in project libraries)
		Set DB credentials in DatabaseConnection.java
		Run: Main.java
	Option 2: Compile + Run from Terminal
		From project root:
		
		javac -cp ".:postgresql.jar" src/controller/Main.java
		java  -cp ".:postgresql.jar" controller.Main


•F. Screenshots

<img width="2316" height="1652" alt="Image 25 01 2026 at 19 20" src="https://github.com/user-attachments/assets/36b3c0db-1c54-46ad-b928-608d8bb14e42" />

<img width="2646" height="1376" alt="Image 25 01 2026 at 19 28" src="https://github.com/user-attachments/assets/7d03b558-bb27-4a09-be06-48f2f453917e" />

<img width="1878" height="640" alt="Image 25 01 2026 at 19 29" src="https://github.com/user-attachments/assets/5326d288-0472-457d-ac8f-ea9b80ae0295" />

<img width="2224" height="604" alt="Image 25 01 2026 at 19 31" src="https://github.com/user-attachments/assets/a14aa6f0-be39-44ed-904e-06942f21628c" />

<img width="1608" height="612" alt="Image 25 01 2026 at 19 32" src="https://github.com/user-attachments/assets/b555b9a4-d0c3-40e4-ba09-030353120aac" />

<img width="2392" height="1102" alt="Image 25 01 2026 at 19 33" src="https://github.com/user-attachments/assets/3a0737bf-1f31-42a5-9d1b-96375600eb57" />

•G. Reflection Section

	  What I learned
  	During this project I learned how to:
		  •design a multi-layer Java application (controller/service/repository)
		  •implement CRUD using JDBC and SQL
		  •create and use custom exceptions for cleaner error handling
		  •apply OOP concepts such as inheritance, interfaces, composition, and polymorphism
	  	•build and explain UML diagrams for class relationships
	Challenges faced
	The main challenges were:
	  	•handling invalid input from the user (Scanner input mismatch)
		  •handling database errors (FK constraint, duplicates)
		  •separating responsibilities correctly between service and repository layers
	Benefits of JDBC and multi-layer design
	JDBC makes the program realistic because data is stored persistently in a database.
	Multi-layer architecture improves the design by:
		  •keeping SQL logic inside repository
		  •keeping validation and business rules inside service
		  •keeping menu/input logic inside controller
		  •This makes the code easier to maintain and expand.
