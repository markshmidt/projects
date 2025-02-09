import pygame
import sys

pygame.init()

# ----- Configuration Constants -----
BOARD_POS = (10, 10)          # Top-left corner where the board is drawn
TILESIZE = 90                 # Size of each square
MARGIN = 15                   # Margin used for positioning pieces inside each square

BOARD_WIDTH, BOARD_HEIGHT = 8, 8

# Colors used for board squares (RGB tuples)
COLORS = [(128, 0, 0),       # brown
          (48, 213, 200),    # turquoise
          (0, 0, 255),       # blue
          (255, 255, 0),     # yellow
          (255, 105, 180),   # pink
          (0, 255, 0),       # green
          (255, 0, 0),       # red
          (255, 165, 0)]     # orange

# Corresponding color names (order must match COLORS)
COLOR_NAMES = ["brown", "turquoise", "blue", "yellow", "pink", "green", "red", "orange"]

# ----- Initialize the Screen and Font -----
screen = pygame.display.set_mode((800, 800))
pygame.display.set_caption("Kamisado")
font = pygame.font.SysFont(None, 72)  # For game-over text

def create_board_surf():
    """Creates and returns a surface with the drawn board (checkerboard style)."""
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
        image_filename: the filename of the piece image.
        direction: "up" or "down" (allowed forward direction).
        team: "white" or "black" (for turn management).
        """
        super().__init__()
        self.image = pygame.image.load(image_filename).convert_alpha()
        # Scale the image to fit inside the square (with some margin).
        self.image = pygame.transform.scale(self.image, (TILESIZE - 20, TILESIZE - 20))
        self.rect = self.image.get_rect()
        self.col = col
        self.row = row
        self.direction = direction  # "up" means decreasing row; "down" means increasing row.
        self.team = team

        # Extract the intrinsic color from the filename.
        # For example, "orange_white.png" -> intrinsic color is "orange"
        filename_no_ext = image_filename.split('.')[0]
        self.intrinsic_color = filename_no_ext.split('_')[0].lower()

        self.snap()  # Position the piece correctly on the board.

    def snap(self):
        """Snap the piece to its board cell using BOARD_POS and MARGIN."""
        self.rect.topleft = (BOARD_POS[0] + self.col * TILESIZE + MARGIN,
                               BOARD_POS[1] + self.row * TILESIZE + MARGIN)

    def update_position(self, new_col, new_row):
        """Update board coordinates and snap to the new cell."""
        self.col = new_col
        self.row = new_row
        self.snap()

def get_piece_at(col, row, pieces):
    """Return the piece at board position (col, row) from 'pieces', or None if empty."""
    for p in pieces:
        if p.col == col and p.row == row:
            return p
    return None

def get_valid_moves(piece, pieces):
    """
    Return a list of valid board positions (col, row) for the given piece.
    Allowed moves: forward vertical or diagonal (in the direction allowed).
      - For "up" pieces, allowed vectors are (0,-1), (-1,-1), (1,-1)
      - For "down" pieces, allowed vectors are (0,1), (-1,1), (1,1)
    The piece may move as far as unobstructed.
    """
    valid_moves = []
    if piece.direction == 'up':
        directions = [(0, -1), (-1, -1), (1, -1)]
    else:  # piece.direction == 'down'
        directions = [(0, 1), (-1, 1), (1, 1)]
    for dx, dy in directions:
        step = 1
        while True:
            new_col = piece.col + dx * step
            new_row = piece.row + dy * step
            # Check board boundaries.
            if 0 <= new_col < BOARD_WIDTH and 0 <= new_row < BOARD_HEIGHT:
                if get_piece_at(new_col, new_row, pieces) is None:
                    valid_moves.append((new_col, new_row))
                    step += 1
                else:
                    break
            else:
                break
    return valid_moves

# ----- Image Lists and Piece Creation -----
# The image lists now contain file names that indicate the piece's intrinsic color.
# Top row pieces (row 0) should be black; bottom row pieces (row 7) should be white.
# Note: The naming is swapped:
#   - Top row uses images from white_images (e.g. "brown_black.png")
#   - Bottom row uses images from black_images (e.g. "orange_white.png")

white_images = ["brown_black.png", "turquoise_black.png", "blue_black.png", "yellow_black.png",
                "pink_black.png", "green_black.png", "red_black.png", "orange_black.png"]

black_images = ["orange_white.png", "brown_white.png", "turquoise_white.png", "blue_white.png",
                "yellow_white.png", "pink_white.png", "green_white.png", "red_white.png"]

def reset_game():
    """Reinitialize all pieces and game state."""
    global black_pieces, white_pieces, all_pieces, turn, forced_color
    black_pieces = pygame.sprite.Group()  # Top row pieces (team "black")
    white_pieces = pygame.sprite.Group()  # Bottom row pieces (team "white")
    all_pieces = []

    # Create top row pieces: row 0, team "black", moving "down"
    for col in range(BOARD_WIDTH):
        p = Piece(col, 0, white_images[col], "down", "black")
        black_pieces.add(p)
        all_pieces.append(p)

    # Create bottom row pieces: row 7, team "white", moving "up"
    for col in range(BOARD_WIDTH):
        p = Piece(col, 7, black_images[col], "up", "white")
        white_pieces.add(p)
        all_pieces.append(p)

    turn = "white"         # White starts (i.e. bottom row moves first)
    forced_color = None

# Initialize game state for the first time.
reset_game()

# ----- Turn Management and Dragging -----
selected_piece = None
valid_moves = []       # Valid moves for the currently selected piece
drag_offset = (0, 0)   # Offset for smooth dragging

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
            for piece in all_pieces:
                if piece.rect.collidepoint(pos):
                    # Allow selection only if:
                    # 1. The piece belongs to the current turn.
                    # 2. If forced_color is active, the piece's intrinsic color must match.
                    if piece.team == turn and (forced_color is None or piece.intrinsic_color == forced_color):
                        selected_piece = piece
                        valid_moves = get_valid_moves(piece, all_pieces)
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
                # Compute board cell over which the piece was dropped.
                new_col = round((selected_piece.rect.x - BOARD_POS[0]) / TILESIZE)
                new_row = round((selected_piece.rect.y - BOARD_POS[1]) / TILESIZE)
                if (new_col, new_row) in valid_moves:
                    selected_piece.update_position(new_col, new_row)
                    # Determine the color of the destination tile.
                    tile_index = (new_row + new_col) % len(COLORS)
                    tile_color_name = COLOR_NAMES[tile_index]
                    forced_color = tile_color_name  # Force opponent to move piece of this color.
                    # Switch turn.
                    turn = "black" if turn == "white" else "white"

                    # --- Check for Game Over Condition ---
                    # For white pieces (team "white" moving up): reaching row 0 wins.
                    # For black pieces (team "black" moving down): reaching row BOARD_HEIGHT-1 wins.
                    if (selected_piece.team == "white" and selected_piece.row == 0) or \
                       (selected_piece.team == "black" and selected_piece.row == BOARD_HEIGHT - 1):
                        # Display "Game Over" message.
                        over_text = font.render("Game Over", True, (255, 255, 255))
                        text_rect = over_text.get_rect(center=(400, 400))
                        screen.blit(over_text, text_rect)
                        pygame.display.update()
                        pygame.time.delay(1000)  # Pause for 2 seconds.
                        reset_game()  # Restart the game.
                else:
                    # Invalid move: snap back.
                    selected_piece.snap()
                selected_piece = None
                valid_moves = []

    # ----- Drawing -----
    screen.fill((0, 0, 0))
    screen.blit(board_surf, BOARD_POS)

    # Draw valid moves as centered dots.
    if selected_piece:
        for (col, row) in valid_moves:
            cx = BOARD_POS[0] + col * TILESIZE + TILESIZE // 2
            cy = BOARD_POS[1] + row * TILESIZE + TILESIZE // 2
            # Dot color: red for white's turn, blue for black's turn.
            dot_color = (255, 0, 0) if turn == "white" else (0, 0, 255)
            pygame.draw.circle(screen, dot_color, (cx, cy), 8)

    # Draw the pieces.
    black_pieces.draw(screen)
    white_pieces.draw(screen)

    pygame.display.update()
    clock.tick(60)

pygame.quit()
sys.exit()
