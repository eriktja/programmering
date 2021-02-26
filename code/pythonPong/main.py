import pygame
from tkinter import *
import time
from random import randrange
pygame.font.init()

# Settings
# Screen settings
screen_width = 1200  # Change to change the screen width
screen_height = 800  # Change to change the screen height
screen_size = (screen_width, screen_height)  # Size of the screen
# Background image
background = pygame.image.load('background.png')  # Import the background image I made in paint
background = pygame.transform.scale(background, screen_size)  # Adapt the image to the size of the screen

# Text color, size and font
text = pygame.font.Font("freesansbold.ttf", 50)

# Pygame window
window = pygame.display.set_mode(screen_size)
pygame.display.set_caption('pythonPong, by Erik Jarem')

# Ball size and color
ball_width = 25
ball_height = 25
ball_size = (ball_width, ball_height)
ball_color = (50, 50, 255)

# Player settings
player_width = 10
player_height = 75
player_size = (player_width, player_height)
# Individual player settings
# Red player
red_color = (255, 50, 50)
# Green player
green_color = (40, 200, 40)


def instructions():
    """Show instructions"""
    instructions_list = []  # Empty list for instructions
    with open('instructions.txt', 'rt') as the_instructions:
        instruction = the_instructions.read()
        for lines in instruction:
            instructions_list.append(lines)
    # Converting list to string
    instructions_string = "".join(instructions_list)
    # Instructions window
    score_win = Tk()
    score_win.title('pythonPong Instructions')
    Label(score_win, text=instructions_string, bg='black', fg='white').grid(row=0, column=0, sticky=W)


def open_scoreboard():
    """Open and show scoreboard"""
    recap = []  # Empty list for scores
    with open('scoreboard.txt', 'rt') as the_score:
        summary = the_score.read()
        for lines in summary:
            recap.append(lines)
    # Converting list to string and limiting length of the string to the last few scores
    my_recap1 = " ".join(recap[-762:-1])
    my_recap2 = " ".join(recap[-1517:-763])
    # Scoreboard window
    score_win = Tk()
    score_win.title('pythonPong scoreboard')
    Label(score_win, text=my_recap1, bg='black', fg='white') .grid(row=0, column=1, sticky=W)
    Label(score_win, text=my_recap2, bg='black', fg='white').grid(row=0, column=2, sticky=W)


def startup():
    """Start-up window"""
    win = Tk()
    win.title('pythonPong, by Erik Jarem')

    # Background image for the startup window
    bg_img = PhotoImage(file='background4.png')
    Label(win, image=bg_img, bg="black").grid(row=0, rowspan=8, column=0, columnspan=8, sticky=W)

    # Start button
    Button(win, text='Start Game', width=10, command=main) .grid(row=4, column=2, sticky=E)

    # Scoreboard button
    Button(win, text='Scoreboard', width=10, command=open_scoreboard).grid(row=4, column=3, sticky=E)

    # Score
    recap = []
    with open('scoreboard.txt', 'rt') as my_scoreboard:
        for lines in my_scoreboard:
            recap.append(lines)
        # This if-statement fixed the problem were the game crashed if scoreboard.txt was empty.
        if len(recap) > 2:
            Label(win, text=f'Previous result: \n{recap[-2]} and {recap[-1]}',
                  bg='grey', fg='black', font='none 12 bold') \
                .grid(row=5, column=2, columnspan=3, sticky=W)

    # Instructions button
    Button(win, text='Instructions', width=10, command=instructions).grid(row=5, column=3, sticky=E)

    win.mainloop()


def main():
    """Main game loop and player-/ball-/game-speed settings"""
    # Booleans keeping the game running
    run = True
    game_won = False

    # Ball
    ball_x = int(screen_width / 2 - ball_width / 2)
    ball_y = int(screen_height / 2 - ball_height / 2)
    # Different starting positions for the ball, depending on scenario
    ball_start = (ball_x, ball_y)
    red_ball_start = (screen_width / 4, screen_height / 2)
    green_ball_start = (screen_width / 1.33, screen_height / 2)

    # Player
    green_player_score = 0
    red_player_score = 0
    # Red player starting position
    red_y = int(screen_height / 2 - player_height / 2)
    red_x = 10
    # Green player starting position
    green_y = int(screen_height / 2 - player_height / 2)
    green_x = screen_width - player_width - 10

    # Make the ball and players rectangles for precise collision-detection.
    red = pygame.Rect((red_x, red_y), player_size)
    green = pygame.Rect((green_x, green_y), player_size)
    ball = pygame.Rect((ball_x, ball_y), ball_size)

    # Game settings:
    fps = 60  # Game speed
    count_down = 5  # Countdown at beginning
    winning_score = 5  # The score needed to win the game

    # Ball- and player-speed and ball-direction change at restart
    movement_speed = 5  # Movement speed for players
    x_vel = 8 * (randrange(-1, 2, 2))  # The randrange makes the direction at beginning random
    y_vel = 8 * (randrange(-1, 2, 2))

    clock = pygame.time.Clock()

    def score():
        """Reset the ball, and add point to scoreboard.txt when a point is scored"""
        pygame.time.delay(500)  # Small pause when scoring
        ball.topleft = ball_start  # Reset the ball
        with open('scoreboard.txt', 'a') as the_score:
            the_score.write(f'\nPoints to {player_scored}!\n'
                            f'Green player: {green_player_score}\n'
                            f'Red player: {red_player_score}\n')

    def redraw():
        """Redraw the game-window"""
        # Draw and position background image.
        window.blit(background, (0, 0))

        # Draw ball
        pygame.draw.ellipse(window, ball_color, ball)
        # Draw players
        pygame.draw.rect(window, red_color, red)
        pygame.draw.rect(window, green_color, green)

        # Display a message when game is won
        if game_won:
            win_message = text.render(message, True, color)
            window.blit(win_message, (screen_width / 2 - win_message.get_width() / 2, screen_height / 2))

        # Scoreboard
        scoreboard_red = text.render(f'{red_player_score}', True, red_color)
        scoreboard_green = text.render(f'{green_player_score}', True, green_color)
        # Green position
        scoreboard_green_pos_x = int(screen_width / 2 + 20)
        scoreboard_green_pos = (scoreboard_green_pos_x, 10)
        # Red position
        scoreboard_red_pos_x = int(screen_width / 2 - scoreboard_red.get_width() - 20)
        scoreboard_red_pos = (scoreboard_red_pos_x, 10)
        # Draw both scoreboards
        window.blit(scoreboard_green, scoreboard_green_pos)
        window.blit(scoreboard_red, scoreboard_red_pos)

        # Countdown before game start.
        count = text.render(f'{count_down}', True, red_color)
        count_pos = (screen_width / 2 - count.get_width() / 2, screen_height / 2 - count.get_height() / 2)
        if count_down > 0:  # Only draw countdown when count_down is bigger than zero
            window.blit(count, count_pos)  # blit countdown to screen

        # Redraw window
        pygame.display.update()

    # Countdown before the game starts
    while count_down > 0:
        redraw()
        time.sleep(1)
        count_down -= 1

    # The loop which keeps the game running
    while run and count_down <= 0:  # Loops while run = True
        clock.tick(fps)
        redraw()

        # WINNING
        if red_player_score == winning_score:
            game_won = True
            message = 'Red player won the game'
            color = red_color

        if green_player_score == winning_score:
            game_won = True
            message = 'Green player won the game'
            color = green_color

        # Ball movement
        ball.x += x_vel  # Ball velocity
        ball.y += y_vel  # Ball velocity
        if ball.top <= 0 or ball.bottom >= screen_height:  # Top and bottom borders
            y_vel *= -1  # Turn the ball direction

        # Registering points
        if ball.right > screen_width:
            red_player_score += 1
            player_scored = 'Red Player'
            ball_start = red_ball_start
            score()
        if ball.left < 0:
            green_player_score += 1
            player_scored = 'Green Player'
            ball_start = green_ball_start
            score()
        # Collision detection between ball and players
        if ball.colliderect(red) or ball.colliderect(green):
            x_vel *= -1

        # Moving the players
        key = pygame.key.get_pressed()
        # Green player movement keys
        if key[pygame.K_DOWN] and green.bottom <= screen_height:  # Move down
            green.y += movement_speed
        if key[pygame.K_UP] and green.top >= 0:  # Move up
            green.y -= movement_speed
        # Red player movement keys
        if key[pygame.K_s] and red.bottom <= screen_height:  # Move down
            red.y += movement_speed
        if key[pygame.K_w] and red.top >= 0:  # Move down
            red.y -= movement_speed

        # QUIT
        if key[pygame.K_ESCAPE]:
            run = False
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                run = False
        # Stops the game if the game is won, but not before redrawing the window to get the win-message
        if game_won:
            redraw()
            run = False

        # Reset the game
        if key[pygame.K_RETURN]:
            green_player_score = 0  # Return green score to 0
            red_player_score = 0  # Return red score to 0
            ball_start = (ball_x, ball_y)  # Changes ball_start back to center of the screen
            ball.topleft = ball_start  # Place ball in center of screen


startup()
