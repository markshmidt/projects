import pygame

pygame.init()

GREEN = (0, 255, 0)
BROWN = (128, 0, 0)
TURQUIOSE = (48, 213, 200)
BLUE = (0, 0, 255)
YELLOW = (255, 255, 0)
PINK = (255, 105, 180)
RED = (255, 0, 0)
ORANGE = (255, 165, 0)
WHITE = (255, 255, 255)
BLACK = (0, 0, 0)

TILESIZE = 90
BOARD_POS = (10, 10)

screen = pygame.display.set_mode((800, 800))


def create_board_surf():
    board_surf = pygame.Surface((730, 730))
    rect = pygame.Rect(0, 0, TILESIZE, TILESIZE)
    pygame.draw.rect(board_surf, BROWN, rect)
    pygame.draw.rect(board_surf, TURQUIOSE, (90, 0, 90, 90))
    pygame.draw.rect(board_surf, BLUE, (180, 0, 90, 90))
    pygame.draw.rect(board_surf, YELLOW, (270, 0, 90, 90))
    pygame.draw.rect(board_surf, PINK, (360, 0, 90, 90))
    pygame.draw.rect(board_surf, GREEN, (450, 0, 90, 90))
    pygame.draw.rect(board_surf, RED, (540, 0, 90, 90))
    pygame.draw.rect(board_surf, ORANGE, (630, 0, 90, 90))
    pygame.draw.rect(board_surf, GREEN, (0, 90, 90, 90))
    pygame.draw.rect(board_surf, BROWN, (90, 90, 90, 90))
    pygame.draw.rect(board_surf, YELLOW, (180, 90, 90, 90))
    pygame.draw.rect(board_surf, RED, (270, 90, 90, 90))
    pygame.draw.rect(board_surf, TURQUIOSE, (360, 90, 90, 90))
    pygame.draw.rect(board_surf, PINK, (450, 90, 90, 90))
    pygame.draw.rect(board_surf, ORANGE, (540, 90, 90, 90))
    pygame.draw.rect(board_surf, BLUE, (630, 90, 90, 90))

    pygame.draw.rect(board_surf, RED, (0, 180, 90, 90))
    pygame.draw.rect(board_surf, YELLOW, (90, 180, 90, 90))
    pygame.draw.rect(board_surf, BROWN, (180, 180, 90, 90))
    pygame.draw.rect(board_surf, GREEN, (270, 180, 90, 90))
    pygame.draw.rect(board_surf, BLUE, (360, 180, 90, 90))
    pygame.draw.rect(board_surf, ORANGE, (450, 180, 90, 90))
    pygame.draw.rect(board_surf, PINK, (540, 180, 90, 90))
    pygame.draw.rect(board_surf, TURQUIOSE, (630, 180, 90, 90))

    pygame.draw.rect(board_surf, YELLOW, (0, 270, 90, 90))
    pygame.draw.rect(board_surf, BLUE, (90, 270, 90, 90))
    pygame.draw.rect(board_surf, TURQUIOSE, (180, 270, 90, 90))
    pygame.draw.rect(board_surf, BROWN, (270, 270, 90, 90))
    pygame.draw.rect(board_surf, ORANGE, (360, 270, 90, 90))
    pygame.draw.rect(board_surf, RED, (450, 270, 90, 90))
    pygame.draw.rect(board_surf, GREEN, (540, 270, 90, 90))
    pygame.draw.rect(board_surf, PINK, (630, 270, 90, 90))

    pygame.draw.rect(board_surf, PINK, (0, 360, 90, 90))
    pygame.draw.rect(board_surf, GREEN, (90, 360, 90, 90))
    pygame.draw.rect(board_surf, RED, (180, 360, 90, 90))
    pygame.draw.rect(board_surf, ORANGE, (270, 360, 90, 90))
    pygame.draw.rect(board_surf, BROWN, (360, 360, 90, 90))
    pygame.draw.rect(board_surf, TURQUIOSE, (450, 360, 90, 90))
    pygame.draw.rect(board_surf, BLUE, (540, 360, 90, 90))
    pygame.draw.rect(board_surf, YELLOW, (630, 360, 90, 90))

    pygame.draw.rect(board_surf, TURQUIOSE, (0, 450, 90, 90))
    pygame.draw.rect(board_surf, PINK, (90, 450, 90, 90))
    pygame.draw.rect(board_surf, ORANGE, (180, 450, 90, 90))
    pygame.draw.rect(board_surf, BLUE, (270, 450, 90, 90))
    pygame.draw.rect(board_surf, GREEN, (360, 450, 90, 90))
    pygame.draw.rect(board_surf, BROWN, (450, 450, 90, 90))
    pygame.draw.rect(board_surf, YELLOW, (540, 450, 90, 90))
    pygame.draw.rect(board_surf, RED, (630, 450, 90, 90))

    pygame.draw.rect(board_surf, BLUE, (0, 540, 90, 90))
    pygame.draw.rect(board_surf, ORANGE, (90, 540, 90, 90))
    pygame.draw.rect(board_surf, PINK, (180, 540, 90, 90))
    pygame.draw.rect(board_surf, TURQUIOSE, (270, 540, 90, 90))
    pygame.draw.rect(board_surf, RED, (360, 540, 90, 90))
    pygame.draw.rect(board_surf, YELLOW, (450, 540, 90, 90))
    pygame.draw.rect(board_surf, BROWN, (540, 540, 90, 90))
    pygame.draw.rect(board_surf, GREEN, (630, 540, 90, 90))

    pygame.draw.rect(board_surf, ORANGE, (0, 630, 90, 90))
    pygame.draw.rect(board_surf, RED, (90, 630, 90, 90))
    pygame.draw.rect(board_surf, GREEN, (180, 630, 90, 90))
    pygame.draw.rect(board_surf, PINK, (270, 630, 90, 90))
    pygame.draw.rect(board_surf, YELLOW, (360, 630, 90, 90))
    pygame.draw.rect(board_surf, BLUE, (450, 630, 90, 90))
    pygame.draw.rect(board_surf, TURQUIOSE, (540, 630, 90, 90))
    pygame.draw.rect(board_surf, BROWN, (630, 630, 90, 90))
    return board_surf


class Item(pygame.sprite.Sprite):

    def __init__(self, x, y, filename):
        pygame.sprite.Sprite.__init__(self)
        self.image = pygame.image.load(filename).convert_alpha()
        self.size = self.image.get_size()
        self.image = pygame.transform.scale(self.image, (int(self.size[0] // 50), int(self.size[1] // 50)))
        self.x = x
        self.y = y
        self.rect = self.image.get_rect(x=x, y=y)

    def update(self, rel):
        self.rect.move_ip(rel)


items = pygame.sprite.Group(
    Item(15, 15, "маша_круги-10.png"),
    Item(105, 15, "маша_круги-11.png"),
    Item(195, 15, "маша_круги-12.png"),
    Item(285, 15, "маша_круги-16.png"),
    Item(375, 15, "маша_круги-15.png"),
    Item(465, 15, "маша_круги-09.png"),
    Item(555, 15, "маша_круги-14.png"),
    Item(645, 15, "маша_круги-13.png"),

    Item(15, 645, "маша_круги-05.png"),
    Item(105, 645, "маша_круги-06.png"),
    Item(195, 645, "маша_круги-01.png"),
    Item(285, 645, "маша_круги-07.png"),
    Item(375, 645, "маша_круги-08.png"),
    Item(465, 645, "маша_круги-04.png"),
    Item(555, 645, "маша_круги-03.png"),
    Item(645, 645, "маша_круги-02.png")
)

dragged = pygame.sprite.Group()

clock = pygame.time.Clock()

while True:
    board_surf = create_board_surf()
    clock = pygame.time.Clock()
    for event in pygame.event.get():

        if event.type == pygame.QUIT:
            exit()
        elif event.type == pygame.MOUSEBUTTONDOWN:
            dragged.add(x for x in items if x.rect.collidepoint(event.pos))
        elif event.type == pygame.MOUSEBUTTONUP:
            dragged.empty()
        elif event.type == pygame.MOUSEMOTION:
            dragged.update(event.rel)

    screen.fill(BLACK)
    screen.blit(board_surf, BOARD_POS)
    items.draw(screen)
    pygame.display.update()

    clock.tick(60)

pygame.quit()
