package radium.rx;

public class RandomNameOnSubscribe extends RandomWordOnSubscribe {

    public RandomNameOnSubscribe() {
        super();
    }

    @Override
    public String[] getWords() {
        return new String[]{"John", "Bob", "Peter", "Mike"};
    }

}
