package hu.unideb.inf.dejavu.objects;

import java.util.List;

public class Pack {
	Card[][] cards;
	int dimension;

	public Pack(){
		dimension=0;
	}
	public Pack(Card[][] cards, int dimension) {
		super();
		this.cards = cards;
		this.dimension = dimension;
	}

	public Pack(List<Card> cardList, int dimension) {
		super();

		this.dimension = dimension;

		cards = new Card[dimension][dimension];

		for (int i = 0; i < cardList.size(); i++) {
			cards[cardList.get(i).getPosition().getFirst()][cardList.get(i).getPosition().getSecond()] = cardList
					.get(i);
		}

	}

	public boolean isValid() {
		for (int i = 0; i < dimension; i++)
			for (int j = 0; j < dimension; j++)
				if (!cards[i][j].getValue().exists() || cards[i][j].getValue().isDirectory()) {
					return false;
				}
		return true;

	}

	public Card[][] getCards() {
		return cards;
	}

	public void setCards(Card[][] cards) {
		this.cards = cards;
	}

	public Card getCard(int x, int y) {
		return cards[x][y];
	}

	public void setCard(Card card, int x, int y) {
		this.cards[x][y] = card;
	}

	public int getDimension() {
		return dimension;
	}

	public void setDimension(int dimension) {
		this.dimension = dimension;
	}

}
