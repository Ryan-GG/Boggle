package view.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;

import controller.GuiListener;
import controller.SpinnerListener;


/**.
 * BoggleGui - Visual display of Boggle
 * 
 * Acknowledgements: I acknowledge that I have neither given nor
 * received assistance for this assignment except as noted
 * 
 * @author Ryan Gross
 * @version 12/6/19
 */
public class BoggleGui extends JFrame {


    private static final long serialVersionUID = 1L;
    
    private JFrame boggle;
    
    private JButton start;
    private JButton quit; 
    private JButton restart; 
    private JButton reject; 
    
    private JSpinner pointsToWin;
    
    private JRadioButton[] difficulty; 
    
    private JButton[][] boggleButtons; 
    
    private JTextArea input; 
    
    private JPanel boardPanel;
    private JPanel northPanel;
    private JPanel southPanel;
    private JPanel eastPanel;
    private JPanel westPanel;
    private JPanel centerPanel;
    private JPanel subSouthPanel;
    private JPanel subNorthPanel;
    private JPanel timerPanel;
    private JPanel westSubRoundPanel;
    private JPanel westSubTotalPanel;
    private JPanel westComSubRoundPanel;
    private JPanel westComSubTotalPanel;
    
    private JList<String> uniqueWords; 
    private JList<String> commonWords; 
    private JList<String> comUniqueWords; 
    private JList<String> invalidWords;
    
    private Border southBorder;
    private Border northBorder;
    private Border eastBorder;
    private Border inputBorder;
    
    private JLabel timer;
    private JLabel playerRoundScore; 
    private JLabel playerTotalScore; 
    private JLabel computerRoundScore; 
    private JLabel computerTotalScore; 
    private JLabel roundNumber; 
    
    private GuiListener listener;
    private SpinnerListener spinListener;

    
    /**.
     * BoggleGui - constructor
     * @throws IOException - IoExceptions
     */
    public BoggleGui() throws IOException {
        constructFrame();
        constructComponents();
        constructPanels();
        addPanels();
        addComponents();
        boggle.setVisible( true );
    }
    
    /**.
     * constructFrame - constructs JFrame
     */
    
    private void constructFrame() {
        boggle = new JFrame();
        boggle.setSize( 750, 600 );
        boggle.setTitle( "Boggle" );
        boggle.setLayout( new BorderLayout() );
        
    }
    
    /**.
     * constructComponents - creates components;
     * @throws IOException - IOexceptions
     */
    
    private void constructComponents() throws IOException {

        int min = 1;
        int max = 10000;
        int step = 1;
        int y = 1;
        SpinnerModel value = new SpinnerNumberModel( y, min, max, step );
        
        boggleButtons = new JButton[4][4];
        
        roundNumber = new JLabel( "0" );
        
        input = new JTextArea();
        input.setLineWrap( true );
        input.setWrapStyleWord( true );
        input.setEnabled( false );
        
        listener = new GuiListener();
        listener.setLabelClass( this );
      
        
        start = new JButton( "Start" );
        start.addActionListener( listener );
        
        quit = new JButton( "Quit" );
        quit.addActionListener( listener );
        
        restart = new JButton( "Restart" );
        restart.addActionListener( listener );
        
        reject = new JButton( "Reject" );
        reject.addActionListener( listener );
        reject.setEnabled( false );
        
        pointsToWin = new JSpinner( value );
        pointsToWin.setValue( 100 );
        

        spinListener = new SpinnerListener();
        spinListener.setLabelClass( this );
        spinListener.setListenerClass( listener );
        
        pointsToWin.addChangeListener( spinListener );
        
        timer = new JLabel( "0:00" );
        
        difficulty = new JRadioButton[10];
        
        playerRoundScore = new JLabel( "0" );
        playerTotalScore = new JLabel( "0" );
        computerRoundScore = new JLabel( "0" );
        computerTotalScore = new JLabel( "0" );
        
        
        ButtonGroup buttonGroup = new ButtonGroup();
        for ( int i = 0; i < difficulty.length; i++ ) {
            difficulty[i] = new JRadioButton( "" + ( i + 1 ) );
            buttonGroup.add( difficulty[i] );
        }
        
        difficulty[4].setSelected( true );
        
        
        uniqueWords = new JList<String>();
        uniqueWords.setPrototypeCellValue( String.format( "%60s", "" ) );
        commonWords = new JList<String>();
        commonWords.setPrototypeCellValue( String.format( "%60s", "" ) );
        invalidWords = new JList<String>();
        invalidWords.setPrototypeCellValue( String.format( "%60s", "" ) );
        comUniqueWords = new JList<String>();
        comUniqueWords.setPrototypeCellValue( String.format( "%60s", "" ) );
        
        southBorder = BorderFactory.createTitledBorder( "Play Buttons" );
        eastBorder = BorderFactory.createTitledBorder(
                "Words Found By You Or The Computer" );
        northBorder = BorderFactory.createTitledBorder(
                "Difficulty / Points To Win" );
        inputBorder = BorderFactory.createTitledBorder( "Board/Timer/Input" );
        
    }
    
    /**.
     * constructPanels - created panels and adds subPanels
     */
    
    private void constructPanels() {

        subSouthPanel = new JPanel();
        subSouthPanel.setBorder( southBorder );
        
        
        subNorthPanel = new JPanel( new FlowLayout() );
        subNorthPanel.setBorder( northBorder );
        
        westPanel = new JPanel();
        westPanel.setLayout( new BoxLayout( westPanel, BoxLayout.Y_AXIS ) );
        
        westPanel.setPreferredSize( new Dimension( 175, boggle.getHeight() ) );
        westSubRoundPanel = new JPanel();
        westSubRoundPanel.setLayout( new BoxLayout( 
                westSubRoundPanel, BoxLayout.X_AXIS ) );
        
        westSubTotalPanel = new JPanel();
        westSubTotalPanel.setLayout( new BoxLayout( 
                westSubTotalPanel, BoxLayout.X_AXIS ) );
        
        
        westComSubRoundPanel = new JPanel();
        westComSubRoundPanel.setLayout( new BoxLayout( 
                westComSubRoundPanel, BoxLayout.X_AXIS ) );
        
        westComSubTotalPanel = new JPanel();
        westComSubTotalPanel.setLayout( new BoxLayout( 
                westComSubTotalPanel, BoxLayout.X_AXIS ) );
        
        northPanel = new JPanel();
        southPanel = new JPanel();
        
        eastPanel = new JPanel();
        eastPanel.setBorder( eastBorder );
        eastPanel.setLayout( new BoxLayout( eastPanel, BoxLayout.Y_AXIS ) );
        eastPanel.setPreferredSize( new Dimension( 250, boggle.getHeight() ) );
        
        centerPanel = new JPanel();
        centerPanel.setBorder( inputBorder );
        centerPanel.setLayout( new BoxLayout( centerPanel, BoxLayout.Y_AXIS ) );
        
        boardPanel = new JPanel();
        boardPanel.setBorder( BorderFactory.createEmptyBorder( 
                10, 10, 10, 10 ) );
        boardPanel.setLayout( new GridLayout( 4, 4, 5, 5 ) );
        
        timerPanel = new JPanel();
        timerPanel.setBorder( BorderFactory.createTitledBorder( "Timer" ) );
        
        constructBoard();
    }
    
    /**.
     * constructBoard - creates BoggleButtons
     */
    
    private void constructBoard() {
        int size = (int) boggle.getWidth() < boggle.getHeight()
                ? boggle.getHeight() : boggle.getWidth();
        
        size = size / 15;
        
        Dimension dim = new Dimension( size, size );
        
        for ( int r = 0; r < boggleButtons.length; r++ ) {
            for ( int c = 0; c < boggleButtons[r].length; c++ ) {
                boggleButtons[r][c] = new JButton( "---" );
                boggleButtons[r][c].setPreferredSize( dim );
                boggleButtons[r][c].setBackground( Color.WHITE );
                boardPanel.add( boggleButtons[r][c] );
                
            }
        }
    }
    
    /**.
     * addPanels - adds panels to Frame
     */
    private void addPanels() {
        boggle.add( northPanel, BorderLayout.NORTH );
        boggle.add( southPanel, BorderLayout.SOUTH );
        boggle.add( eastPanel, BorderLayout.EAST );
        boggle.add( westPanel, BorderLayout.WEST );
        boggle.add( centerPanel );
        
        centerPanel.add( boardPanel );
        centerPanel.add( timerPanel );
        
        southPanel.add( subSouthPanel );
        
        northPanel.add( subNorthPanel );
    }
    
    /**.
     * addComponenets - adds components to Panels;
     */
    private void addComponents() {
        
        centerPanel.add( input );
        subSouthPanel.add( start );
        subSouthPanel.add( quit );
        subSouthPanel.add( restart );
        subSouthPanel.add( reject );
        
        subNorthPanel.add( new JLabel( "Round Number: " ) );
        subNorthPanel.add( roundNumber );
        
        subNorthPanel.add( new JLabel( "Difficulty: " ) );
        for ( int i = 0; i < 10; i++ ) {
            subNorthPanel.add( difficulty[i] );
        }
        
        subNorthPanel.add( new JLabel( "Needed to Win: " ) );
        subNorthPanel.add( pointsToWin );

        eastPanel.add( new JLabel( "You" ) );
        eastPanel.add( new JScrollPane( uniqueWords ) );
        eastPanel.add( new JLabel( "Common" ) );
        eastPanel.add( new JScrollPane( commonWords ) );
        eastPanel.add( new JLabel( "Computer" ) );
        eastPanel.add( new JScrollPane( comUniqueWords ) );
        
        westPanel.add( new JLabel( "Player" ) );
        westSubRoundPanel.setAlignmentX( Component.LEFT_ALIGNMENT );
        westSubTotalPanel.setAlignmentX( Component.LEFT_ALIGNMENT );
        westPanel.add( westSubRoundPanel );
        westPanel.add( westSubTotalPanel );
        westSubRoundPanel.add( new JLabel( "player round score: " ) );
        westSubRoundPanel.add( playerRoundScore );
        westSubTotalPanel.add( new JLabel( "player total score: " ) );
        westSubTotalPanel.add( playerTotalScore );
        
        westPanel.add( Box.createVerticalStrut( 50 ) );
        
        westPanel.add( new JLabel( "Computer" ) );
        westComSubRoundPanel.setAlignmentX( Component.LEFT_ALIGNMENT );
        westComSubTotalPanel.setAlignmentX( Component.LEFT_ALIGNMENT );
        westPanel.add( westComSubRoundPanel );
        westPanel.add( westComSubTotalPanel );
        westComSubRoundPanel.add( new JLabel( "computer round score: " ) );
        westComSubRoundPanel.add( computerRoundScore );
        westComSubTotalPanel.add( new JLabel( "computer total score: " ) );
        westComSubTotalPanel.add( computerTotalScore );
        
        westPanel.add( Box.createVerticalStrut( 50 ) );
        
        westPanel.add( new JLabel( "Invalid" ) );
        westPanel.add( new JScrollPane( invalidWords ) );
        
        timerPanel.add( timer );

    }
    
    /**.
     * getBoggleButtons -returns boggle board
     * @return JButton[][] - boggleButtons
     */
    public JButton[][] getBoggleButtons() {
        return boggleButtons;
    }
    
    /**.
     * getStartBtn - gets start button
     * @return JButton - the start button
     */
    public JButton getStartBtn() {
        return start;
    }
    
    /**.
     * getRejectBtn - gets reject button
     * @return JButton - the reject button
     */
    
    public JButton getRejectBtn() {
        return reject;
    }
    
    /**.
     * getRestartBtn - gets restart button
     * @return JButton - restart btn
     */
    public JButton getRestartBtn() {
        return restart;
    }
    
    /**.
     * getTimer - gets the label timer is put into
     * @return JLabel - timer label
     */
    public JLabel getTimer() {
        return timer;
    }
    
    /**.
     * getPointsToWin - the PTW set by user in JSpinner
     * @return int - points to win
     */
    
    public int getPointsToWin() {
        return (int) pointsToWin.getValue();
    }
    
    /**.
     * getDifficultyLevel - gets set radiobutton of difficulty
     * @return int - gets difficulty
     */
    
    public int getDifficultyLevel() {
        int difficultyLevel = 5;
        for ( int i = 0; i < difficulty.length; i++ ) {
            if ( difficulty[i].isEnabled() ) {
                difficultyLevel = i + 1;
            }
        }
        return difficultyLevel;
    }
    
    /**.
     * getWords - gets users input
     * @return JTextArea - inputed words
     */
    
    public JTextArea getWords() {
        return input;
    }
    
    /**.
     * getUniqueList - gets the Unique JList
     * @return JList<String> - unique list 
     */
    
    public JList<String> getUniqueList() {
        return uniqueWords;
    }
    
    /**.
     * getCommonList - gets the common JList
     * @return JList<String> - common list 
     */
    public JList<String> getCommonList() {
        return commonWords;
    }
    
    /**.
     * getComputerList - gets the computer Unique JList
     * @return JList<String> - computer unique list 
     */
    public JList<String> getComputerList() {
        return comUniqueWords;
    }
    
    /**.
     * getInvalidList - gets the invalid Jlist
     * @return JList<String> - invalid list
     */
    public JList<String> getInvalidList() {
        return invalidWords;
    }
    
    /**.
     * getRoundScoreLabel - gets the players round score lbl
     * @return JLabel - display of player round score
     */
    public JLabel getRoundScoreLabel() {
        return playerRoundScore;
    }
    
    /**.
     * getTotalScoreLabel - gets the players total score lbl
     * @return JLabel - display of players total score
     */
    public JLabel getTotalScoreLabel() {
        return playerTotalScore;
    }
    
    /**.
     * getComRoundScoreLabel - gets the coms round score lbl
     * @return JLabel - display of the com round score
     */
    public JLabel getComRoundScoreLabel() {
        return computerRoundScore;
    }
    
    /**.
     * getComTotalScoreLabel - gets the com total score lbl
     * @return JLabel - display of the com total score
     */
    public JLabel getComTotalScoreLabel() {
        return computerTotalScore;
    }
    
    /**.
     * getRoundNuber - gets the roundNumber lbl
     * @return JLabel - displayer of current round number;
     */
    public JLabel getRoundNumber() {
        return roundNumber;
    }
    
        
}
