//Jacob Gaylord
//jigaylord16@ole.augie.edu
//DeckOfCards.java
//A DeckOfCards object represents a deck of ordinary playing cards. The top card
//is dealt each time the method deal is called. A dealt card will not be reused
//until the DeckOfCards is shuffled.
import java.util.Random;
public class DeckOfCards
{
  private int top;
  private String[] rank;
  private String[] suit;
  //Post: This DeckOfCards initialized to a new deck; the cards are ordered from
  //      AceSpade to KingDiamond as in a new deck; top initialized to 0.
  public DeckOfCards()
  {
    rank = new String[52];
    suit = new String[52];
    for(int i = 0 ; i < 4 ; ++i)
    {
      rank[0 + 13 * i] = "Ace";
			rank[1 + 13 * i] = "Two";
			rank[2 + 13 * i] = "Three";
			rank[3 + 13 * i] = "Four";
			rank[4 + 13 * i] = "Five";
			rank[5 + 13 * i] = "Six";
			rank[6 + 13 * i] = "Seven";
			rank[7 + 13 * i] = "Eight";
			rank[8 + 13 * i] = "Nine";
			rank[9 + 13 * i] = "Ten";
			rank[10 + 13 * i] = "Jack";
			rank[11 + 13 * i] = "Queen";
			rank[12 + 13 * i] = "King";
    }
    for(int i = 0 ; i < 13 ; ++i)
      suit[i] = "Spade";
    for(int i = 13 ; i < 26 ; ++i)
      suit[i] = "Heart";
    for(int i = 26 ; i < 39 ; ++i)
      suit[i] = "Club";
    for(int i = 39 ; i < 52 ; ++i)
      suit[i] = "Diamond";
    top = 0;
  }
  //Post: This DeckOfCards thoroughly shuffled; top set to 0.
  public void shuffle()
  {
    Random r = new Random();
    for(int i = 0 ; i < 1000 ; ++i)
    {
      int x = r.nextInt(52);
      int y = r.nextInt(52);
      String temp = rank[x];
      rank[x] = rank[y];
      rank[y] = temp;
      temp = suit[x];
      suit[x] = suit[y];
      suit[y] = temp;
    }
    top = 0;
  }
  //Post:   Top incremented by 1.
  //Return: The top card of this DeckOfCards as a String such as "AceHeart",
  //        "TwoSpade", "TenDiamond", "KingClub". If top >= 52, "NoMoreCard" is
  //        returned.
  public String deal()
  {
    String result="";
    if(top < 52) result = rank[top] + suit[top];
    else result = "NoMoreCard";
    top++;
    return result;
  }
}
