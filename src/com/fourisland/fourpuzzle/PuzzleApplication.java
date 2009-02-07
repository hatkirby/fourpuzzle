/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fourisland.fourpuzzle;

import com.fourisland.fourpuzzle.gamestate.TitleScreenGameState;
import com.fourisland.fourpuzzle.gamestate.mapview.ChipSet;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
    public static JDialog gameFrame;
    public static boolean debugSpeed = false;
    public static boolean stretchScreen = true;
    public static boolean gameSleep = false;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(PuzzleApplication.class, args);
    }

    @Override
    protected void startup() {
        INSTANCE = this;
        
        gameFrame = new JDialog(new JFrame(), false);
        gameFrame.setTitle(Database.getVocab("title"));
        gameFrame.setSize(Game.WIDTH * 2, Game.HEIGHT * 2);
        gameFrame.setResizable(false);
        gameFrame.addWindowListener(new WindowAdapter() {
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
        gameFrame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e)
            {
                if (e.getKeyCode() == KeyEvent.VK_F4)
                {
                    GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(gameFrame);
                } else if (e.getKeyCode() == KeyEvent.VK_F5)
                {
                    stretchScreen = !stretchScreen;

                    if (stretchScreen)
                    {
                        gameFrame.setSize(Game.WIDTH * 2, Game.HEIGHT * 2);
                    } else {
                        gameFrame.setSize(Game.WIDTH, Game.HEIGHT);
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_SHIFT)
                {
                    if (INSTANCE.getContext().getResourceMap().getBoolean("debugMode"))
                    {
                        debugSpeed = true;
                    }
                } else {
                    Game.setKey(e);
                }
            }
            
            @Override
            public void keyReleased(KeyEvent e)
            {
                if (e.getKeyCode() == KeyEvent.VK_SHIFT)
                {
                    debugSpeed = false;
                } else {
                    e.setKeyCode(KeyEvent.VK_UNDEFINED);
                    Game.setKey(e);
                }
            }
        });
        gameFrame.setVisible(true);

        new Thread(new Runnable() {
            public void run() {
                try {
                    Audio.init();
                    ChipSet.initalize();
                    Game.setGameState(new TitleScreenGameState());
                    
                    long iTickCount = System.currentTimeMillis();
                    long iTickTrigger = iTickCount + Game.FPS;
                    
                    while (true)
                    {
                        iTickCount = System.currentTimeMillis();
                        
                        if ((iTickCount > iTickTrigger) && (!gameSleep))
                        {
                            if (!Display.isTransitionRunning())
                            {
                                if (Game.getKey() != null)
                                {
                                    Game.getGameState().processInput();
                                }

                                Game.getGameState().doGameCycle();
                            }
                            
                            Display.render(gameFrame);
                            
                            if (!debugSpeed)
                            {
                                iTickTrigger = iTickCount + Game.FPS;
                            }
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
        text.setText("<HTML><CENTER>I'm sorry, but " + Database.getVocab("Title") +
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
