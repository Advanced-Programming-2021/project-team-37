package model;

 class Board
{
    private HashMap<Integer, MonsterCard> monsterCardZone;		
    private HashMap<Integer, SpellAndTrapCard> spellAndTrapCardZone;		
    private HashMap<Integer, MonsterCard> opponentMonsterCardZone;		
    private HashMap<Integer, SpellAndTrapCard> opponentSpellAndTrapCardZone;		
    private ArrayList<SpellAndTrap> fieldZone;		
    private ArrayList<SpellAndTrap> opponentFieldZone;		
    private ArrayList<Card> mainDeck;		
    private ArrayList<Card> sideDeck;		
}
