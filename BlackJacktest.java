//Jacob Gaylord
//jigaylord16@ole.augie.edu
//BlackJacktest.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
class BlackJackFrame extends JFrame implements ActionListener
{
  private DeckOfCards deck = new DeckOfCards();
  private Vector<String> player = new Vector<String>();
	private Vector<String> dealer = new Vector<String>();

  private JLabel[] lblPCard = new JLabel[7];
  private JLabel[] lblDCard = new JLabel[7];
  private JPanel pnlCards = new JPanel();

  private JButton btnDeal = new JButton("Deal");
  private JButton btnPlayer = new JButton("Player");
  private JButton btnDealer = new JButton("Dealer");
  private JButton btnNew = new JButton("New");
  private JPanel pnlSouth = new JPanel();

  public BlackJackFrame()
  {
    for (int i=0; i<7; ++i)
      lblPCard[i]=new JLabel("Player");
    for (int i=0; i<7; ++i)
      lblDCard[i]=new JLabel("Dealer");
    addPnlCards();
    addControlsPnlSouth();
    registerListeners();
    add(pnlCards, BorderLayout.CENTER);
    add(pnlSouth, BorderLayout.SOUTH);
  }
  public void addPnlCards()
  {
    pnlCards.setLayout(new GridLayout(2,7));
    for(int i=0; i<7; ++i)
      pnlCards.add(lblPCard[i]);
    for(int i=0; i<7; ++i)
      pnlCards.add(lblDCard[i]);
  }
  public void addControlsPnlSouth()
  {
    pnlSouth.add(btnDeal);
    pnlSouth.add(btnPlayer);
    pnlSouth.add(btnDealer);
    pnlSouth.add(btnNew);
  }
  public void registerListeners()
	{
		btnDeal.addActionListener(this);
		btnPlayer.addActionListener(this);
		btnDealer.addActionListener(this);
    btnNew.addActionListener(this);
  }
  //Return: An integer representing the rank of a dealt card from a DeckOfCards
  //        object.
  private int findRank(String card)
  {
    int rank = 0;
    if(card.startsWith("Two")) rank = 2;
    else if(card.startsWith("Three")) rank = 3;
    else if(card.startsWith("Four")) rank = 4;
    else if(card.startsWith("Five")) rank = 5;
    else if(card.startsWith("Six")) rank = 6;
    else if(card.startsWith("Seven")) rank = 7;
    else if(card.startsWith("Eight")) rank = 8;
    else if(card.startsWith("Nine")) rank = 9;
    else if(card.startsWith("Ten")) rank = 10;
    else if(card.startsWith("Jack")) rank = 10;
    else if(card.startsWith("Queen")) rank = 10;
    else if(card.startsWith("King")) rank = 10;
    else rank = 11;
    return rank;
  }
  //Pre: v contains playing cards
  //Return: The numeric total of all the cards in v
  private int total(Vector<String> v)
  {
    int total = 0;
    for(int i = 0 ; i < v.size() ; ++i)
      total += findRank(v.get(i));
    return total;
  }
  public void actionPerformed(ActionEvent e)
  {
    ImageIcon icon=null;
	  String s=null, filename=null;
    if(e.getSource() == btnDeal)
    {
      deck.shuffle();
      player.add(deck.deal());
      player.add(deck.deal());
      dealer.add(deck.deal());
      dealer.add(deck.deal());
      for(int i = 0 ; i < 2 ; ++i)
      {
        String card = player.get(i);
        lblPCard[i].setEnabled(true);
        lblPCard[i].setIcon("cardImages/"+card+".gif");
        lblPCard[i].setText("");
        String card = dealer.get(0);
        lblDCard[0].setEnabled(true);
        lblDCard[0].setIcon("cardImages/"+card+".gif");
        lblDCard[0].setText("");
        lblDCard[1].setEnabled(true);
        lblDCard[1].setIcon("cardImages/card.gif");
        lblDCard[1].setText("");
      }
      btnPlayer.setEnabled(true);
    }
    if(e.getSource() == btnPlayer)
    {
      int pHitNum = 0;
      s = JOptionPane.showInputDialog(null, "You have "+total(player)+". Hit or stay H/S:");
			if (s==null) return;
      if (s.equalsIgnoreCase("H"))
			{
				player.add(deck.deal());
        pHitNum++;
        String card = player.get(1+pHitNum);
        lblPCard[1+pHitNum].setEnabled(true);
        lblPCard[1+pHitNum].setIcon("cardImages/"+card+".gif");
        lblPCard[1+pHitNum].setText("");
        if(total(player)>21)
        {
          JOptionPane.showMessageDialog(null, "You busted.");
          btnDeal.setEnabled(false);
          btnPlayer.setEnabled(false);
          btnDealer.setEnabled(false);
          btnNew.setEnabled(true);
          System.exit(0);
        }
        else if(total(player)<=21)
        {
          btnDeal.setEnabled(false);
          btnPlayer.setEnabled(true);
          btnDealer.setEnabled(true);
          btnNew.setEnabled(false);
          System.exit(0);
        }
      }
      else if (s.equalsIgnoreCase("S"))
      {
        btnDeal.setEnabled(false);
        btnPlayer.setEnabled(false);
        btnDealer.setEnabled(true);
        btnNew.setEnabled(false);
        System.exit(0);
      }
    }
    int dHitNum = 0;
    if(e.getSource() == btnDealer)
    {
      if(total(dealer)<17)
      {
        dealer.add(deck.deal());
        dHitNum++;
        String card = dealer.get(1+dHitNum);
        lblDCard[1+dHitNum].setEnabled(true);
        lblDCard[1+dHitNum].setIcon("cardImages/"+card+".gif");
        lblDCard[1+dHitNum].setText("");
      }
      else if(total(dealer)>17 && total(dealer)<21)
      {
        btnDeal.setEnabled(false);
        btnPlayer.setEnabled(false);
        btnDealer.setEnabled(false);
        btnNew.setEnabled(true);
        System.exit(0);
      }
      else if(total(dealer)>21)
      {
        JOptionPane.showMessageDialog(null, "Dealer busted.");
        btnDeal.setEnabled(false);
        btnPlayer.setEnabled(false);
        btnDealer.setEnabled(false);
        btnNew.setEnabled(true);
        System.exit(0);
      }

    }
  }
}
public class BlackJack
{
  public static void main(String[] args)
  {
    BlackJackFrame b=new BlackJackFrame();
    b.setTitle("BlackJack");
    b.setSize(600, 300);
    b.setLocationRelativeTo(null);
    b.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    b.setVisible(true);
  }
}
