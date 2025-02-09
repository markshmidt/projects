import pygame
import sys

pygame.init()

# ----- Configuration Constants -----
BOARD_POS = (10, 10)          # Top-left corner where the board is drawn on screen
TILESIZE = 90                 # Size of each board cell
MARGIN = 15                   # Margin inside each tile for piece centering

# GRID_ORIGIN is the top-left pixel coordinate where pieces should be placed
GRID_ORIGIN = (BOARD_POS[0] + MARGIN, BOARD_POS[1] + MARGIN)

BOARD_WIDTH, BOARD_HEIGHT = 8, 8

# Colors used for board squares
COLORS = [(128, 0, 0), (48, 213, 200), (0, 0, 255), (255, 255, 0),
          (255, 105, 180), (0, 255, 0), (255, 0, 0), (255, 165, 0)]

# ----- Initialize the Screen -----
screen = pygame.display.set_mode((800, 800))

def create_board_surf():
    """Creates and returns a surface with the drawn board."""
    board_surf = pygame.Surface((BOARD_WIDTH * TILESIZE, BOARD_HEIGHT * TILESIZE))
    for row in range(BOARD_HEIGHT):
        for col in range(BOARD_WIDTH):
            color = COLORS[(row + col) % len(COLORS)]
            pygame.draw.rect(board_surf, color,
                             (col * TILESIZE, row * TILESIZE, TILESIZE, TILESIZE))
    return board_surf

# ----- Piece Class -----
class Piece(pygame.sprite.Sprite):
    def __init__(self, col, row, image_filename, direction, team):
        """
        col, row: initial board coordinates (0-indexed).
        image_filename: filename for the piece image.
        direction: "up" or "down" (allowed forward direction).
        team: "white" or "black" (for grouping and turn management).
        """
        super().__init__()
        self.image = pygame.image.load(image_filename).convert_alpha()
        # Scale the image so that it fits nicely in a board cell.
        self.image = pygame.transform.scale(self.image, (TILESIZE - 20, TILESIZE - 20))
        self.rect = self.image.get_rect()
        self.col = col
        self.row = row
        self.direction = direction  # "up" means moving toward row 0; "down" means increasing row.
        self.team = team
        self.snap()  # Place piece in the proper pixel location.

    def snap(self):
        """Snap the piece to its board cell using GRID_ORIGIN and TILESIZE."""
        self.rect.topleft = (GRID_ORIGIN[0] + self.col * TILESIZE,
                               GRID_ORIGIN[1] + self.row * TILESIZE)

    def update_position(self, new_col, new_row):
        """Update the board coordinates and snap the piece to its new cell."""
        self.col = new_col
        self.row = new_row
        self.snap()

def get_piece_at(col, row, pieces):
    """Return a piece at board position (col, row) from the list 'pieces', or None."""
    for p in pieces:
        if p.col == col and p.row == row:
            return p
    return None

def get_valid_moves(piece, pieces):
    """
    Returns a list of valid board coordinates (col, row) for the given piece.
    Allowed moves: along the allowed forward directions until the path is blocked.
    For pieces with direction "up": allowed vectors are (0,-1), (-1,-1), and (1,-1).
    For pieces with direction "down": allowed vectors are (0,1), (-1,1), and (1,1).
    """
    valid_moves = []
    if piece.direction == 'up':
        directions = [(0, -1), (-1, -1), (1, -1)]
    else:  # direction "down"
        directions = [(0, 1), (-1, 1), (1, 1)]
    for dx, dy in directions:
        step = 1
        while True:
            new_col = piece.col + dx * step
            new_row = piece.row + dy * step
            # Check board boundaries.
            if 0 <= new_col < BOARD_WIDTH and 0 <= new_row < BOARD_HEIGHT:
                # If the square is empty, it’s a valid move.
                if get_piece_at(new_col, new_row, pieces) is None:
                    valid_moves.append((new_col, new_row))
                    step += 1
                else:
                    # Blocked by another piece.
                    break
            else:
                break
    return valid_moves

# ----- Create the Pieces -----
# For Kamisado it’s common to have 8 pieces per side.
# Here we assign:
#   - Top–row pieces (row 0) move "down" and we assign them to team "white".
#   - Bottom–row pieces (row 7) move "up" and we assign them to team "black".

white_images = ["маша_круги-10.png", "маша_круги-11.png", "маша_круги-12.png", "маша_круги-16.png",
                "маша_круги-15.png", "маша_круги-09.png", "маша_круги-14.png", "маша_круги-13.png"]

black_images = ["маша_круги-05.png", "маша_круги-02.png", "маша_круги-03.png", "маша_круги-04.png",
                "маша_круги-08.png", "маша_круги-07.png", "маша_круги-01.png", "маша_круги-06.png"]

# We use pygame.sprite.Group for easier drawing.
white_pieces = pygame.sprite.Group()
black_pieces = pygame.sprite.Group()
all_pieces = []  # This list contains all pieces for occupancy checks.

# Create white pieces at row 0.
for i in range(8):
    p = Piece(i, 0, white_images[i], "down", "white")
    white_pieces.add(p)
    all_pieces.append(p)

# Create black pieces at row 7.
for i in range(8):
    p = Piece(i, 7, black_images[i], "up", "black")
    black_pieces.add(p)
    all_pieces.append(p)

# ----- Turn Management and Selection -----
turn = "white"  
selected_piece = None
valid_moves = []  # List of (col, row) valid moves for the currently selected piece.
drag_offset = (0, 0)  # Mouse offset for dragging

clock = pygame.time.Clock()

# ----- Main Game Loop -----
running = True
while running:
    board_surf = create_board_surf()

    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            running = False

        # --- Mouse Button Down: Select a Piece ---
        elif event.type == pygame.MOUSEBUTTONDOWN:
            pos = event.pos
            # Loop through all pieces and select the one clicked if it belongs to the active turn.
            for piece in all_pieces:
                if piece.rect.collidepoint(pos):
                    if piece.team == turn:
                        selected_piece = piece
                        # Compute valid moves for this piece.
                        valid_moves = get_valid_moves(piece, all_pieces)
                        # Calculate the offset so that the piece image doesn't jump.
                        drag_offset = (piece.rect.x - pos[0], piece.rect.y - pos[1])
                    break

        # --- Mouse Motion: Drag the Selected Piece ---
        elif event.type == pygame.MOUSEMOTION:
            if selected_piece:
                pos = event.pos
                selected_piece.rect.x = pos[0] + drag_offset[0]
                selected_piece.rect.y = pos[1] + drag_offset[1]

        # --- Mouse Button Up: Drop and Validate the Move ---
        elif event.type == pygame.MOUSEBUTTONUP:
            if selected_piece:
                # Determine which board cell the piece was dropped over.
                new_col = round((selected_piece.rect.x - GRID_ORIGIN[0]) / TILESIZE)
                new_row = round((selected_piece.rect.y - GRID_ORIGIN[1]) / TILESIZE)
                # Check if the drop cell is a valid move.
                if (new_col, new_row) in valid_moves:
                    selected_piece.update_position(new_col, new_row)
                    # Change the turn.
                    turn = "black" if turn == "white" else "white"
                else:
                    # Invalid move: revert to its original (snapped) position.
                    selected_piece.snap()
                selected_piece = None
                valid_moves = []

    # ----- Drawing -----
    screen.fill((0, 0, 0))
    screen.blit(board_surf, BOARD_POS)

    # If a piece is selected, show valid moves as small circles.
    if selected_piece:
        for (col, row) in valid_moves:
            cx = GRID_ORIGIN[0] + col * TILESIZE + TILESIZE // 2
            cy = GRID_ORIGIN[1] + row * TILESIZE + TILESIZE // 2
            # Use red for white's turn and blue for black's turn.
            color = (255, 0, 0) if turn == "white" else (0, 0, 255)
            pygame.draw.circle(screen, color, (cx, cy), 8)

    # Draw pieces. (The groups are drawn in the order they were added.)
    white_pieces.draw(screen)
    black_pieces.draw(screen)

    pygame.display.update()
    clock.tick(60)

pygame.quit()
sys.exit()
