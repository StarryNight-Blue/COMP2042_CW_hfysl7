package test.View;

import test.Components.*;
import test.Controller.DebugConsole;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;

/**
 * This class is to show the view features in game board
 * @author Sue Sim
 */
public class GameBoardView extends JComponent{
    private static final String CONTINUE = "Continue";
    private static final String RESTART = "Restart";
    private static final String EXIT = "Exit";
    private static final String PAUSE = "Pause Menu";
    private static final int TEXT_SIZE = 30;
    private static final Color MENU_COLOR = new Color(0, 255, 0);


    public static final int DEF_WIDTH = 600;
    public static final int DEF_HEIGHT = 450;

    public static final Color BG_COLOR = Color.WHITE;

    public Timer gameTimer;
    public GameTimer timer = new GameTimer();

    public Wall wall;

    public String message;
    public String message2;
    public String message3;
    public String life;

    public String individualScore;

    public boolean showPauseMenu;

    public Font menuFont;

    public Rectangle continueButtonRect;
    public Rectangle exitButtonRect;
    public Rectangle restartButtonRect;
    public int strLen;

    public DebugConsole debugConsole;

    public GameBoardView(JFrame owner) {
        super();

        strLen = 0;
        showPauseMenu = false;


        menuFont = new Font("Monospaced", Font.PLAIN, TEXT_SIZE);


        message = "";
        message2 = "";
        message3 = "";
        individualScore = "";
        wall = new Wall(new Rectangle(0, 0, DEF_WIDTH, DEF_HEIGHT), 30, 3, 6 / 2, new Point(300, 430));

        debugConsole = new DebugConsole(owner, wall, this);
        //initialize the first level
        wall.nextLevel();
    }

    /**
     * Display total score in popup window
     */
    public void printHighScore() {
        StringBuilder text = new StringBuilder();

        ImageIcon icon = new ImageIcon("champagne.png");
        JOptionPane.showMessageDialog(null, "Your Score: " + wall.getTotalScore(), "High Score", JOptionPane.PLAIN_MESSAGE, icon);
    }

    /**
     * @param g Graphics on the elements in the game
     */
    public void paint(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        clear(g2d);

        g2d.setColor(Color.GREEN);
        g2d.drawString(message, 260, 190);
        g2d.drawString(message2, 260, 210);
        g2d.drawString(message3, 220, 230);
        g2d.drawString(individualScore, 525, 425);

        drawBall(wall.ball, g2d);

        for (Brick b : wall.bricks)
            if (!b.isBroken())
                drawBrick(b, g2d);

        drawPlayer(wall.player, g2d);

        if (showPauseMenu)
            drawMenu(g2d);

        Toolkit.getDefaultToolkit().sync();
    }

    /**
     * @param g2d Graphics of the background of game only
     */
    private void clear(Graphics2D g2d) {
        Color tmp = g2d.getColor();
        g2d.setColor(BG_COLOR);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.setColor(tmp);

        Image picture = Toolkit.getDefaultToolkit().getImage("sky.jpg");
        g2d.drawImage(picture, 0, 0, this);
    }

    /**
     * @param brick Allocation of brick
     * @param g2d Graphics of brick
     */
    private void drawBrick(Brick brick, Graphics2D g2d) {
        Color tmp = g2d.getColor();

        g2d.setColor(brick.getInnerColor());
        g2d.fill(brick.getBrick());

        g2d.setColor(brick.getBorderColor());
        g2d.draw(brick.getBrick());

        g2d.setColor(tmp);
    }

    /**
     * @param ball Allocation of ball
     * @param g2d Graphics of ball
     */
    private void drawBall(Ball ball, Graphics2D g2d) {
        Color tmp = g2d.getColor();

        Shape s = ball.getBallFace();

        g2d.setColor(ball.getInnerColor());
        g2d.fill(s);

        g2d.setColor(ball.getBorderColor());
        g2d.draw(s);

        g2d.setColor(tmp);
    }

    /**
     * @param p Allocation of player
     * @param g2d Graphics of player
     */
    private void drawPlayer(Player p, Graphics2D g2d) {
        Color tmp = g2d.getColor();

        Shape s = p.getPlayerFace();
        g2d.setColor(Player.INNER_COLOR);
        g2d.fill(s);

        g2d.setColor(Player.BORDER_COLOR);
        g2d.draw(s);

        g2d.setColor(tmp);
    }

    /**
     * @param g2d Graphics on  menu
     */
    private void drawMenu(Graphics2D g2d) {
        obscureGameBoard(g2d);
        drawPauseMenu(g2d);
    }

    /**
     * @return Displaying number of lives remaining
     */
    public String remainingLife(){
        life = "";
        for(int a = 0; a < wall.getBallCount(); a++)
            life += new String(Character.toChars(0x1F499));
        return life;
    }

    private void obscureGameBoard(Graphics2D g2d) {

        Composite tmp = g2d.getComposite();
        Color tmpColor = g2d.getColor();

        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.55f);
        g2d.setComposite(ac);

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, DEF_WIDTH, DEF_HEIGHT);

        g2d.setComposite(tmp);
        g2d.setColor(tmpColor);
    }

    /**
     * @param g2d Graphics of the pause (esc key) menu
     */
    private void drawPauseMenu(Graphics2D g2d) {
        Font tmpFont = g2d.getFont();
        Color tmpColor = g2d.getColor();


        g2d.setFont(menuFont);
        g2d.setColor(MENU_COLOR);

        if (strLen == 0) {
            FontRenderContext frc = g2d.getFontRenderContext();
            strLen = menuFont.getStringBounds(PAUSE, frc).getBounds().width;
        }

        int x = (this.getWidth() - strLen) / 2;
        int y = this.getHeight() / 10;

        g2d.drawString(PAUSE, x, y);

        x = this.getWidth() / 8;
        y = this.getHeight() / 4;


        if (continueButtonRect == null) {
            FontRenderContext frc = g2d.getFontRenderContext();
            continueButtonRect = menuFont.getStringBounds(CONTINUE, frc).getBounds();
            continueButtonRect.setLocation(x, y - continueButtonRect.height);
        }

        g2d.drawString(CONTINUE, x, y);

        y *= 2;

        if (restartButtonRect == null) {
            restartButtonRect = (Rectangle) continueButtonRect.clone();
            restartButtonRect.setLocation(x, y - restartButtonRect.height);
        }

        g2d.drawString(RESTART, x, y);

        y *= 3.0 / 2;

        if (exitButtonRect == null) {
            exitButtonRect = (Rectangle) continueButtonRect.clone();
            exitButtonRect.setLocation(x, y - exitButtonRect.height);
        }

        g2d.drawString(EXIT, x, y);


        g2d.setFont(tmpFont);
        g2d.setColor(tmpColor);
    }
}
