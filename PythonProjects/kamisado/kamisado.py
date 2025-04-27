import pygame
import sys

pygame.init()

# board drawing starts from here
BOARD_POS = (10, 10)
# each square (tile) size
TILESIZE = 90
# margin used for positioning pieces inside each square
MARGIN = 10

BOARD_WIDTH, BOARD_HEIGHT = 8, 8

# colors used for board squares
COLORS = [(128, 0, 0),
          (48, 213, 200),
          (0, 0, 255),
          (255, 255, 0),
          (255, 105, 180),
          (0, 255, 0),
          (255, 0, 0),
          (255, 165, 0)]

# corresponding color names
COLOR_NAMES = ["brown", "turquoise", "blue", "yellow", "pink", "green", "red", "orange"]

# initializing screen and font
screen = pygame.display.set_mode((740, 740))
pygame.display.set_caption("Kamisado")
font = pygame.font.SysFont("Comic Sans MS", 72)  # for game-over text

def create_board_surf():
    # creates and return a board 8x8 format
    board_surf = pygame.Surface((BOARD_WIDTH * TILESIZE, BOARD_HEIGHT * TILESIZE))
    for row in range(BOARD_HEIGHT):
        for col in range(BOARD_WIDTH):
            color = COLORS[(row + col) % len(COLORS)] #to ensure that the computed index wraps around if the sum is greater than the number of colors available
            pygame.draw.rect(board_surf, color, (col * TILESIZE, row * TILESIZE, TILESIZE, TILESIZE))
    return board_surf


class Piece(pygame.sprite.Sprite):
    def __init__(self, col, row, piece_img, direction, team):
        """
        col, row: board coordinates
        piece_img: the filename of the piece image
        direction: "up" or "down"
        team: "white" or "black" (for turn management)
        """

        super().__init__()
        self.image = pygame.image.load(piece_img).convert_alpha()
        # scale the image to fit inside the square
        self.image = pygame.transform.scale(self.image, (TILESIZE - 20, TILESIZE - 20))
        self.rect = self.image.get_rect()
        self.col = col
        self.row = row
        self.direction = direction
        self.team = team

        # extract color from the filename
        filename_no_png = piece_img.split('.')[0]
        self.piece_color = filename_no_png.split('_')[0]

        # position the piece on board
        self.snap()

    def snap(self):
        # place the piece into its designated board cell
        self.rect.topleft = (BOARD_POS[0] + self.col * TILESIZE + MARGIN,
                               BOARD_POS[1] + self.row * TILESIZE + MARGIN)

    def update_position(self, new_col, new_row):
        # update board coordinates and place piece into board cell
        self.col = new_col
        self.row = new_row
        self.snap()

def get_piece_at(col, row, pieces):
    # return the piece at board position
    for p in pieces:
        if p.col == col and p.row == row:
            return p
    return None

def get_valid_moves(piece, pieces):
    # list of valid board positions (col, row) for the given piece
    # pieces can only move vertically or diagonally
    # for "up" pieces allowed vectors are (0,-1), (-1,-1), (1,-1)
    # for "down" pieces allowed vectors are (0,1), (-1,1), (1,1)
    valid_moves = []
    if piece.direction == 'up':
        directions = [(0, -1), (-1, -1), (1, -1)] # down, diagonally down-left, diagonally down-right
    else:
        directions = [(0, 1), (-1, 1), (1, 1)] # up, diagonally up-left, diagonally up-right
    for dx, dy in directions:
        step = 1
        while True:
            # new board coordinates
            new_col = piece.col + dx * step
            new_row = piece.row + dy * step

            # ensure that the new cell is within the limits of the board
            if 0 <= new_col < BOARD_WIDTH and 0 <= new_row < BOARD_HEIGHT:
                if get_piece_at(new_col, new_row, pieces) is None:
                    # if cell is empty, new position is a valid move
                    valid_moves.append((new_col, new_row))
                    step += 1
                else:
                    break
            else:
                break
    return valid_moves


# top row pieces (row 0) should be black and bottom row pieces (row 7) should be white
black_images = ["brown_black.png", "turquoise_black.png", "blue_black.png", "yellow_black.png",
                "pink_black.png", "green_black.png", "red_black.png", "orange_black.png"]

white_images = ["orange_white.png", "brown_white.png", "turquoise_white.png", "blue_white.png",
                "yellow_white.png", "pink_white.png", "green_white.png", "red_white.png"]

def reset_game():
    # initialize pieces and game state
    global black_pieces, white_pieces, all_pieces, turn, forced_color
    black_pieces = pygame.sprite.Group()
    white_pieces = pygame.sprite.Group()
    all_pieces = []

    # create top row black pieces
    for col in range(BOARD_WIDTH):
        p = Piece(col, 0, black_images[col], "down", "black")
        black_pieces.add(p)
        all_pieces.append(p)

    # create bottom row white pieces
    for col in range(BOARD_WIDTH):
        p = Piece(col, BOARD_HEIGHT - 1, white_images[col], "up", "white")
        white_pieces.add(p)
        all_pieces.append(p)

    turn = "white"  # white moves first
    forced_color = None

# initialize game state for the first time
reset_game()
selected_piece = None
valid_moves = []
drag_offset = (0, 0)   # Offset for smooth dragging

clock = pygame.time.Clock()

# main game loop
running = True
while running:
    board_surf = create_board_surf()

    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            running = False

        # mouse clicked
        elif event.type == pygame.MOUSEBUTTONDOWN:
            pos = event.pos
            for piece in all_pieces:
                # if mouse click occurred inside the rectangle that represents a piece’s current position on the screen
                if piece.rect.collidepoint(pos):
                    # if piece belongs to the current turn and if forced_color is active, the piece color must match
                    if piece.team == turn and (forced_color is None or piece.piece_color == forced_color):
                        selected_piece = piece
                        valid_moves = get_valid_moves(piece, all_pieces)
                        # the difference between where the piece is drawn and where player clicked on it
                        drag_offset = (piece.rect.x - pos[0], piece.rect.y - pos[1])
                    break

        # mouse motion & dragging piece
        elif event.type == pygame.MOUSEMOTION:
            if selected_piece:
                pos = event.pos
                selected_piece.rect.x = pos[0] + drag_offset[0]
                selected_piece.rect.y = pos[1] + drag_offset[1]

        # mouse button up & droppin piece
        elif event.type == pygame.MOUSEBUTTONUP:
            if selected_piece:
                # divide the board-relative coordinate by tilesize to convert pixel distance into a cell index
                # rounding the result finds the nearest cell index to where the piece currently is
                new_col = round((selected_piece.rect.x - BOARD_POS[0]) / TILESIZE)
                new_row = round((selected_piece.rect.y - BOARD_POS[1]) / TILESIZE)

                if (new_col, new_row) in valid_moves:
                    selected_piece.update_position(new_col, new_row)
                    # determine the color of the tile
                    tile_index = (new_row + new_col) % len(COLORS)
                    tile_color_name = COLOR_NAMES[tile_index]
                    forced_color = tile_color_name  # force opponent to move piece of this color
                    turn = "black" if turn == "white" else "white"

                    # game over condition
                    if (selected_piece.team == "white" and selected_piece.row == 0) or \
                       (selected_piece.team == "black" and selected_piece.row == BOARD_HEIGHT - 1):
                        # display "game over" message
                        over_text = font.render("Game Over!", True, (255, 255, 255), (128, 0, 128))
                        text_rect = over_text.get_rect(center=(400, 400))
                        screen.blit(over_text, text_rect)
                        pygame.display.update()
                        pygame.time.delay(1000)
                        reset_game()
                else:
                    # invalid move
                    selected_piece.snap()
                selected_piece = None
                valid_moves = []

    # drawing the game
    screen.fill((0, 0, 0))
    screen.blit(board_surf, BOARD_POS)

    # draw valid moves as dots
    if selected_piece:
        for (col, row) in valid_moves:
            cx = BOARD_POS[0] + col * TILESIZE + TILESIZE // 2
            cy = BOARD_POS[1] + row * TILESIZE + TILESIZE // 2
            dot_color = (200, 0, 255) if turn == "white" else (0, 255, 255)
            pygame.draw.circle(screen, dot_color, (cx, cy), 8)

    # draw pieces
    black_pieces.draw(screen)
    white_pieces.draw(screen)

    pygame.display.update()
    clock.tick(60)

pygame.quit()
sys.exit()
