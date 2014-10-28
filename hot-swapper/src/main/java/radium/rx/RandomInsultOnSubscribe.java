package radium.rx;

import java.util.concurrent.TimeUnit;

public class RandomInsultOnSubscribe extends RandomWordOnSubscribe {

    public RandomInsultOnSubscribe() {
        super();
    }

    @Override
    public String[] getWords() {
        return new String[]{"Slut", "Bitch", "Fuck", "Diarrhea"};
    }

}
