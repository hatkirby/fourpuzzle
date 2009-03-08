/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fourisland.fourpuzzle;

import com.fourisland.fourpuzzle.database.Database;
import com.fourisland.fourpuzzle.database.Vocabulary;
import com.fourisland.fourpuzzle.gamestate.TitleScreenGameState;
import com.fourisland.fourpuzzle.util.Interval;
import com.fourisland.fourpuzzle.window.SystemGraphic;
import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.Semaphore;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.jdesktop.application.Application;

/**
 *
 * @author hatkirby
 */
public class PuzzleApplication extends Application {

    public static PuzzleApplication INSTANCE;
    public static boolean debugSpeed = false;
    public static boolean stretchScreen = true;
    public static boolean gameSleep = false;
    private static JDialog gameDialog = new JDialog(new JFrame(), false);
    private static Semaphore gameDialogHandler = new Semaphore(1);
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(PuzzleApplication.class, args);
    }
    
    private void initGameDialog(boolean undecorated)
    {
        /* Because the game form is accessed from many places at once, a
         * Semaphore is used to control access to it */
        gameDialogHandler.acquireUninterruptibly();
        
        /* If the dialog is already visible (for instance, when changing the
         * from full screen mode to windowed or vice versa while the program is
         * running), it has to be closed so it can be reinitalized */
        gameDialog.setVisible(false);
        
        // Set up the actual dialog
        Container contentPane = gameDialog.getContentPane();
        gameDialog = new JDialog(new JFrame(), false);
        gameDialog.setContentPane(contentPane);
        gameDialog.setTitle(Database.getVocab(Vocabulary.Title));
        gameDialog.setSize(Game.WIDTH * 2, Game.HEIGHT * 2);
        gameDialog.setResizable(false);
        gameDialog.setUndecorated(undecorated);
        gameDialog.setLocation(GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint().x-Game.WIDTH, GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint().y-Game.HEIGHT);
        gameDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }

            @Override
            public void windowActivated(WindowEvent e) {
                gameSleep = false;
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                gameSleep = true;
            }
        });
        gameDialog.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e)
            {
                if (e.getKeyCode() == KeyEvent.VK_F4)
                {
                    /* The user is trying to switch the full screen mode; flip
                     * the status switch, reinitalize the dialog and then tell
                     * the GraphicsEnvironment what's happening */
                    stretchScreen = !stretchScreen;

                    if (stretchScreen)
                    {
                        initGameDialog(true);
                        GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(gameDialog);
                    } else {
                        initGameDialog(false);
                        GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(null);
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_SHIFT)
                {
                    /* If debug mode is enabled, holding Shift down should put
                     * the game into hyperactive mode */
                    if (INSTANCE.getContext().getResourceMap().getBoolean("debugMode"))
                    {
                        debugSpeed = true;
                    }
                } else {
                    // If anything else is pressed, let the GameState handle it
                    KeyboardInput.getKey().keyInput(e);
                }
            }

            @Override
            public void keyReleased(KeyEvent e)
            {
                if (e.getKeyCode() == KeyEvent.VK_SHIFT)
                {
                    // If Shift is let go of, hyperactive mode should end
                    debugSpeed = false;
                } else {
                    KeyboardInput.getKey().letGo();
                }
            }
        });
        gameDialog.setVisible(true);
        
        // As we're done with the game dialog, we can release the permit
        gameDialogHandler.release();
    }

    @Override
    protected void startup()
    {
        INSTANCE = this;
        
        // Create the game form
        initGameDialog(true);

        // The game should start out in full screen mode
        GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(gameDialog);

        // Create the game cycle and run it
        new Thread(new Runnable() {
            public void run() {
                try {
                    Audio.init();
                    SystemGraphic.initalize();
                    
                    // The game starts with the Title Screen
                    Game.setGameState(new TitleScreenGameState());

                    /* The game cycle should run every tick (unless hyperactive
                     * mode is enabled, when it should run constantly */
                    Interval in = Interval.createTickInterval(1);
                    while (true)
                    {
                        /* If the game window is deactivated, the game should
                         * pause execution */
                        if (in.isElapsed() && !gameSleep)
                        {
                            /* If there is currently a transition running, the
                             * only necessary function is rendering, otherwise
                             * process keyboard input and run GameState-specific
                             * game cycle code too */
                            if (!Display.isTransitionRunning())
                            {
                                KeyboardInput.processInput();

                                Game.getGameState().doGameCycle();
                            }

                            /* Now, render to the game dialog. Note that as it
                             * is used in many places, a Semaphore permit is
                             * required before rendering can start */
                            gameDialogHandler.acquireUninterruptibly();
                            Display.render(gameDialog.getContentPane());
                            gameDialogHandler.release();
                        }
                    }
                } catch (RuntimeException ex) {
                    reportError(ex);
                } catch (Error ex) {
                    reportError(ex);
                }
            }
        },"GameCycle").start();
    }
    
    public String getGamePackage()
    {
        return INSTANCE.getContext().getResourceMap().getString("Application.package");
    }
    
    public void reportError(Throwable ex)
    {
        if ((ex instanceof Exception) && !(ex instanceof RuntimeException))
        {
            return;
         }
        
        JFrame errorBox = new JFrame(ex.getClass().getSimpleName());
        JLabel text = new JLabel();
        text.setText("<HTML><CENTER>I'm sorry, but " + Database.getVocab(Vocabulary.Title) +
                " has run into an error and been forced to quit.<BR>Your save file has not been kept. The error was:<BR><BR>" +
                ex.getMessage() + "</CENTER>");
        if (ex instanceof Error)
        {
            text.setText(text.getText() + "<P><CENTER>We have identified this problem as a serious error in the game.");
        }
        errorBox.add(text);
        errorBox.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        errorBox.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        errorBox.pack();
        errorBox.setVisible(true);
        ex.printStackTrace();
    }
}
