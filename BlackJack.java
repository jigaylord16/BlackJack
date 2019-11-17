//Jacob Gaylord
//jigaylord16@ole.augie.edu
//BlackJack.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
public class BlackJack extends JFrame implements ActionListener
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

  public BlackJack()
  {
    for (int i=0; i<7; ++i)
    {
      lblPCard[i]=new JLabel("Player");
      lblPCard[i].setEnabled(true);
    }
    for (int i=0; i<7; ++i)
    {
      lblDCard[i]=new JLabel("Dealer");
      lblDCard[i].setEnabled(true);
    }
    addPnlCards();
    addControls();
    registerListeners();
    add(pnlCards, BorderLayout.CENTER);
    add(pnlSouth, BorderLayout.SOUTH);
    setTitle("BlackJack");
    setSize(600, 300);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }
  public void addPnlCards()
  {
    pnlCards.setLayout(new GridLayout(2,7));
    for(int i=0; i<7; ++i)
      pnlCards.add(lblPCard[i]);
    for(int i=0; i<7; ++i)
      pnlCards.add(lblDCard[i]);
  }
  public void addControls()
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
  public void play()
  {
    for (int i=0; i<7; ++i)
    {
      lblPCard[i].setIcon(null);
      lblPCard[i].setText("Player");
      player.clear();
    }
    for (int i=0; i<7; ++i)
    {
      lblDCard[i].setIcon(null);
      lblDCard[i].setText("Dealer");
      dealer.clear();
    }
    btnDeal.setEnabled(true);
    btnPlayer.setEnabled(false);
    btnDealer.setEnabled(false);
    btnNew.setEnabled(false);
  }
  public void deal()
  {
    deck.shuffle();
    player.add(deck.deal());
    player.add(deck.deal());
    dealer.add(deck.deal());
    dealer.add(deck.deal());
  }
  public void displayPlayer()
  {
    for(int i = 0 ; i < player.size() ; ++i)
    {
      lblPCard[i].setIcon(new ImageIcon("cardImages/"+player.get(i)+".gif"));
      lblPCard[i].setText("");
    }
  }
  public void displayDealer(boolean first)
  {
    if(first)
    {
      lblDCard[0].setIcon(new ImageIcon("cardImages/"+dealer.get(0)+".gif"));
      lblDCard[0].setText("");
      lblDCard[1].setIcon(new ImageIcon("cardImages/card.gif"));
      lblDCard[1].setText("");
    }
    else
    {
      for(int i = 0 ; i < dealer.size() ; ++i)
      {
        lblDCard[i].setIcon(new ImageIcon("cardImages/"+dealer.get(i)+".gif"));
        lblDCard[i].setText("");
      }
    }
  }
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
  private int total(Vector<String> v)
	{
    int total = 0;
    for(int i = 0 ; i < v.size() ; ++i)
      total += findRank(v.get(i));
    return total;
	}
  public void actionPerformed(ActionEvent e)
  {
    if(e.getSource() == btnDeal)
    {
      deal();
      boolean first = true;
      displayPlayer();
      displayDealer(first);
      first = false;
      btnDeal.setEnabled(false);
      btnPlayer.setEnabled(true);
      btnDealer.setEnabled(false);
      btnNew.setEnabled(false);
    }
    else if(e.getSource() == btnPlayer)
    {
      while(true)
      {
        String s = null;
        s = JOptionPane.showInputDialog(null, "You have "+total(player)+". Hit or Stay H/S:");
        if(s==null) return;
        else if(s.equalsIgnoreCase("H"))
        {
          int hit = player.size()-1;
          player.add(deck.deal());
          lblPCard[hit].setEnabled(true);
          lblPCard[hit].setIcon(new ImageIcon("cardImages/"+player.get(hit)+".gif"));
          lblPCard[hit].setText("");
          displayPlayer();
          if(total(player)>21)
          {
            JOptionPane.showMessageDialog(null, "You busted. You lose.");
            displayPlayer();
            btnPlayer.setEnabled(false);
            btnNew.setEnabled(true);
            break;
          }
          else if(total(player)==21)
          {
            JOptionPane.showMessageDialog(null, "You have 21. You win.");
            displayPlayer();
            btnPlayer.setEnabled(false);
            btnNew.setEnabled(true);
            break;
          }
          else continue;
        }
        else if(s.equalsIgnoreCase("S"))
        {
          btnPlayer.setEnabled(false);
          btnDealer.setEnabled(true);
          break;
        }
      }
    }
    else if(e.getSource() == btnDealer)
    {
      displayDealer(false);
      while(total(dealer)<17)
      {
        int hit = dealer.size()-1;
        dealer.add(deck.deal());
        lblDCard[hit].setEnabled(true);
        lblDCard[hit].setIcon(new ImageIcon("cardImages/"+dealer.get(hit)+".gif"));
        lblDCard[hit].setText("");
        displayDealer(false);
      }
      if(total(dealer)>21)
      {
        JOptionPane.showMessageDialog(null, "Dealer busted. You win.");
        btnDealer.setEnabled(false);
        btnNew.setEnabled(true);
      }
      else if(total(player) > total(dealer))
      {
        JOptionPane.showMessageDialog(null, "You win. Dealer has "+total(dealer));
        btnDealer.setEnabled(false);
        btnNew.setEnabled(true);
      }
      else if(total(player) < total(dealer))
      {
        JOptionPane.showMessageDialog(null, "You lose. Dealer has "+total(dealer));
        btnDealer.setEnabled(false);
        btnNew.setEnabled(true);
      }
      else if(total(player) == total(dealer))
      {
        JOptionPane.showMessageDialog(null, "You tied. Dealer has "+total(dealer));
        btnDealer.setEnabled(false);
        btnNew.setEnabled(true);
      }
    }
    else if(e.getSource() == btnNew)
    {
      play();
    }
  }
  public static void main(String[] args)
  {
    BlackJack game = new BlackJack();
    game.play();
  }
}
