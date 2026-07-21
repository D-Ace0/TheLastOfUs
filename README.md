# The Last of Us — Java OOP Game

A grid-based survival game built in Java as an object-oriented programming exercise.

## Game model

- Multiple hero specializations: Explorer, Fighter, and Medic
- Zombie enemies
- Vaccine and supply collectibles
- Character, collectible, and trap cells
- Movement, targeting, action-point, and resource rules
- CSV-based hero/game data loading
- Desktop GUI screens for introduction, champion selection, alerts, and gameplay

## Architecture

```text
sol/src/
├── engine/             # Game state and core rules
├── exceptions/         # Domain-specific invalid-action exceptions
├── model/
│   ├── characters/     # Heroes and zombies
│   ├── collectibles/   # Supplies and vaccines
│   └── world/          # Grid cell types
├── newWindowViews/     # Desktop UI windows
├── views/              # Application entry/view layer
└── assets/             # Game resources
```

## OOP concepts demonstrated

- Inheritance and polymorphism across character and cell types
- Encapsulation of game state and actions
- Abstract domain modeling
- Custom checked/unchecked gameplay exceptions
- Separation between engine, model, and presentation packages

## Getting started

1. Install a compatible Java Development Kit.
2. Import the `sol` folder as a Java project in Eclipse or another IDE.
3. Ensure the CSV data files remain in the expected working directory.
4. Run the application entry point under `views` or the provided start window.

## Notes

The game demonstrates deterministic OOP game logic. The repository does not implement or claim machine-learning/AI-driven enemies.

