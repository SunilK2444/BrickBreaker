import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BrickBreakerextendsJFrame {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 600;
    private static final int PADDLE_WIDTH = 60;
    private static final int PADDLE_HEIGHT = 10;
    private static final int BALL_SIZE = 20;
    private static final int BRICK_WIDTH = 50;
    private static final int BRICK_HEIGHT = 20;
    
    private int paddleX = WIDTH / 2 - PADDLE_WIDTH / 2;
    private int ballX = WIDTH / 2 - BALL_SIZE / 2;
    private int ballY = HEIGHT - PADDLE_HEIGHT - BALL_SIZE;
    private int ballSpeedX = 2;
    private int ballSpeedY = -2;
    
    private boolean[] bricks; // Represents the bricks, true if a brick is present
    
    public BrickBreaker() {
        bricks = new boolean[WIDTH / BRICK_WIDTH];
        for (int i = 0; i < bricks.length; i++) {
            bricks[i] = true;
        }
        
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                movePaddle(e);
            }
        });
        
        setFocusable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setVisible(true);
        
        Timer timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
                repaint();
            }
        });
        timer.start();
    }
    
    private void movePaddle(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT && paddleX > 0) {
            paddleX -= 20;
        } else if (keyCode == KeyEvent.VK_RIGHT && paddleX < WIDTH - PADDLE_WIDTH) {
            paddleX += 20;
        }
    }
    
    private void update() {
        ballX += ballSpeedX;
        ballY += ballSpeedY;
        
        // Ball collisions with walls
        if (ballX <= 0 || ballX >= WIDTH - BALL_SIZE) {
            ballSpeedX = -ballSpeedX;
        }
        if (ballY <= 0) {
            ballSpeedY = -ballSpeedY;
        }
        
        // Ball collision with paddle
        if (ballY + BALL_SIZE >= HEIGHT - PADDLE_HEIGHT && ballX >= paddleX && ballX <= paddleX + PADDLE_WIDTH) {
            ballSpeedY = -ballSpeedY;
        }
        
        // Ball collision with bricks
        int brickColumn = ballX / BRICK_WIDTH;
        int brickRow = ballY / BRICK_HEIGHT;
        if (ballY >= 0 && brickRow < bricks.length && bricks[brickColumn]) {
            bricks[brickColumn] = false;
            ballSpeedY = -ballSpeedY;
        }
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.fillRect(paddleX, HEIGHT - PADDLE_HEIGHT, PADDLE_WIDTH, PADDLE_HEIGHT);
        
        g.setColor(Color.RED);
        g.fillOval(ballX, ballY, BALL_SIZE, BALL_SIZE);
        
        g.setColor(Color.BLUE);
        for (int i = 0; i < bricks.length; i++) {
            if (bricks[i]) {
                g.fillRect(i * BRICK_WIDTH, 0, BRICK_WIDTH, BRICK_HEIGHT);
            }
        }
    }
    
    public static void main(String[] args) {
        new BrickBreakerextendsJFrame();
    }
}