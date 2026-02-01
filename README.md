README 

A. Project Overview


•Purpose of the API
	
		This project is a console-based Fitness Tracker API that allows users to manage
		workouts and exercises using a structured, multi-layer Java architecture and a 
		PostgreSQL database through JDBC.
	
		The system supports:
			•Creating workouts (Cardio / Strength)
			•Listing workouts
			•Adding exercises to workouts
			•Deleting workouts
			•Input validation and error handling using custom exceptions

•Technologies Used
	
		•Java
		•JDBC
		•PostgreSQL
		•OOP (Inheritance, Interfaces, Polymorphism, Composition)
		•SOLID Principles
		•IntelliJ IDEA
		
•Summary of Entities and Relationships
	
		•Entities:
		•Workout — base workout entity
		•CardioWorkout — workout with distance (km)
		•StrengthWorkout — workout with sets/reps
		•Exercise — exercise attached to a workout
			
•Relationship:
	
		•One Workout can have many Exercises (Workout 1 → * Exercise)
		•Each Exercise references a Workout via workout_id (Foreign Key)
B. Application Architecture (Layered Design)

	•Controller	Handles user input and menu logic (Main.java)
	•Service	Business logic, validation, error handling
	•Repository	JDBC + SQL operations
	•Model	Entities and OOP hierarchy
	•Exception	Custom exception classes
	•Utils	Database connection helper
	
C. SOLID Principles Applied

•S — Single Responsibility Principle
	
	Each class has one responsibility:
		•Controller handles input
		•Service handles logic
		•Repository handles SQL
		•Models store data
		•Exceptions handle errors
		
•O — Open/Closed Principle
	
	•The system is extendable without modifying existing code.
	•New workout types can be added via inheritance.
	
	
•L — Liskov Substitution Principle
	
		•Subclasses can replace the base class:
			List<Workout> workouts = workoutService.getAllWorkouts();
			
•I — Interface Segregation Principle
	
		public interface Validatable {
  		  void validate();
		}
		
		Classes implement only required behavior.
		
•D — Dependency Inversion Principle
	
		High-level logic does not depend directly on database details.
		Controller → Service → Repository → DB
		

• UML diagram 

<img width="671" height="754" alt="uml2 drawio" src="https://github.com/user-attachments/assets/c60e3bfe-a69e-4ea0-a4c5-70cbaab0f54b" />

D. OOP Design Details

	Inheritance
		•Workout (base class)
		•CardioWorkout extends Workout
		•StrengthWorkout extends Workout
	Composition
		•Workout HAS-A Exercises.
	Polymorphism
		•Workouts are handled via base class references.
		
E. Database Description
	Tables
	
		•workouts
		
			•workout_id (PK)
			•name
			•type
			•duration_minutes
			•calories_burned
			•distance_km (cardio only)
			•sets, reps (strength only)
			
		•exercises
		
			•exercise_id (PK)
			•workout_id (FK)
			•name
			•sets
			•reps
			
		•Constraints
		
			•PKs for both tables
			•FK from exercises → workouts
			•Validation in service layer
			
		•Sample SQL
		
		
			INSERT INTO workouts (name, type, duration_minutes, calories_burned, distance_km)
			VALUES ('Morning Run', 'CARDIO', 30, 300, 5.0);
			
F. CRUD Operations
		
		CREATE	Add workouts and exercises
		READ	List workouts
		DELETE	Delete workout by ID
		


•G. Screenshots

<img width="452" height="814" alt="docs:screenshots:01_structure" src="https://github.com/user-attachments/assets/3501b991-6bf3-4d62-b91d-756396d42955" />

<img width="307" height="686" alt="docs:screenshots:02_db_tables" src="https://github.com/user-attachments/assets/8e182bf9-91ab-4cfe-80e4-f74852360f3f" />

<img width="968" height="417" alt="docs:screenshots:03_run_menu" src="https://github.com/user-attachments/assets/d378caf4-69a9-45e2-99fe-f520e9f0bdaf" />

<img width="458" height="341" alt="docs:screenshots:04_add_cardio" src="https://github.com/user-attachments/assets/3935c78f-72e9-4fb2-8f48-c2b664805e19" />

<img width="572" height="418" alt="docs:screenshots:05_add_strength" src="https://github.com/user-attachments/assets/967a2e4b-a5c0-4714-a9f3-a4c51529fb79" />

<img width="1578" height="774" alt="docs:screenshots:06_list_workouts" src="https://github.com/user-attachments/assets/64a3b813-8b47-4a6f-bbbf-05bbb50a6616" />

<img width="487" height="312" alt="docs:screenshots:07_add_exercise" src="https://github.com/user-attachments/assets/c371bdbc-6c3d-4f03-abf0-84402412e904" />

<img width="840" height="549" alt="docs:screenshots:08_delete_workout" src="https://github.com/user-attachments/assets/5fd22bd2-c5fd-4c9f-92c9-85b187dff80e" />


H. How to Run


•IntelliJ
	
		•Open project
		•Add PostgreSQL driver
		•Configure DB credentials in DatabaseConnection.java
		•Run Main.java
			
•Terminal
	
		javac -cp ".:postgresql.jar" src/controller/Main.java
		java -cp ".:postgresql.jar" controller.Main
		

 I. Reflection
 
What I learned
	
		•Designing layered architecture
		•Applying SOLID principles
		•Implementing JDBC CRUD
		•Using OOP patterns
		•Creating UML diagrams
		
Challenges
	
		•Input validation
		•Handling DB constraints
		•Proper responsibility separation
		
Benefits
	
		•Clean architecture
		•Easier maintenance
		•Expandable system



		
