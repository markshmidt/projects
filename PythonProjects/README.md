# Kamisado – Two Player Game (Pygame)

A Python implementation of the strategic board game **Kamisado**, built using **Pygame**. This version supports two human players taking turns on the same machine. The game follows the core rules of Kamisado: no luck, just pure strategy.


## Gameplay Overview

Kamisado is a two-player abstract strategy game. The goal is to reach your opponent’s home row first with one of your pieces. The twist? Your opponent’s move is determined by the **color of the square** you land on.

### Key Features

- Turn-based two-player gameplay  
- Fully functional board with color-coded tiles  
- Legal move enforcement and win condition  
- Built with **Python** and **Pygame**

## Requirements

- Python 3.8+
- Pygame (`pip install pygame`)

##  Controls

- **Mouse**: Click to select and move pieces
- Players alternate turns; moves are constrained based on tile colors as per the rules

##  Rules Summary

- Each player starts with 8 unique towers, each assigned a different color.
- The color of the square your tower lands on determines which color tower your opponent must move next.
- First player to reach the opponent's home row **wins**.

##  To Do / Future Enhancements

- [ ] Add AI opponent (Minimax or Reinforcement Learning)
- [ ] Add animations and sound effects
- [ ] Support for saving/loading games
- [ ] Web version.

