package controller;

import java.awt.event.ActionEvent;


import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import view.gui.BoggleGui;

/**.
 * SpinnerListener - listens to Points to win spinner
 * 
 * * Acknowledgements: I acknowledge that I have neither given nor
 * received assistance for this assignment except as noted
 * 
 * @author Ryan Gross
 * @version 12/6/19
 */
public class SpinnerListener implements ChangeListener {

    private BoggleGui gui;
    private GuiListener listener;
    
    
    /**.
     * setLabelClass - links with BoggleGui
     * 
     * @param gui - BoggleGui
     */
    
    public void setLabelClass( BoggleGui gui ) {
        this.gui = gui;
    }
    
    /**.
     * setListenerClass - links with current listener
     * @param listener - gui listener
     */
    public void setListenerClass( GuiListener listener ) {
        this.listener = listener;
    }
    
    /**.
     * stateChanged - when spinner amt is changed, restart boggle
     */
    @Override
    public void stateChanged( ChangeEvent e ) {
        listener.actionPerformed( new ActionEvent( 
                gui.getRestartBtn(),
                ActionEvent.ACTION_PERFORMED, "Restart" ) );
    }
        
    

}
