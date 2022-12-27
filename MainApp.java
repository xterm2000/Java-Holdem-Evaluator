@SuppressWarnings("unused")
class MainApp {
    public static void main(String args[]) {

        Deck d = new Deck();
        d.shuffle();

        // // Card c = new Card("5s");

        Card hand[] = { d.getCard(), d.getCard(), d.getCard(), d.getCard(),
                d.getCard() };

        PokerEvaluator.eval5(hand);
        TestClass.test();

        System.out.println("Exiting.");

    }
}