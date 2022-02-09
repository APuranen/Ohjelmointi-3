import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class WordGame{
    private ArrayList<String> wordList;
    private WordGameState game = null;
    
    WordGame(String wordFilename) throws FileNotFoundException, IOException{
        wordList = new ArrayList<String>();

        try(var file = new BufferedReader(new FileReader(wordFilename))){
            String word;
            while((word = file.readLine())!= null){
                wordList.add(word);
            }
        }
    }

    static class WordGameState{
        private int missingChars;
        private int mistakes;
        private int mistakeLimit;

        private String hiddenWord;
        private String displayWord;

        private ArrayList<Character> guessedChars;
        
        private WordGameState(String word, int allowedMistakes){
            mistakeLimit = allowedMistakes;
            hiddenWord = word;
            mistakes = 0;
            guessedChars = new ArrayList<Character>();

            missingChars = word.length();
            displayWord = new String(new char[word.length()]).replace('\0', '_');
            //System.out.println("init game, with hidden word of " + hiddenWord + " and a missing chars amount of: " + missingChars);
        }

        public String getWord(){
            return displayWord;
        }

        public int getMistakes(){return mistakes;}
        public int getMistakeLimit(){return mistakeLimit;}
        public int getMissingChars(){return missingChars;}
    }

    public void initGame(int wordIndex, int mistakeLimit){
        String newWord = wordList.get(wordIndex % wordList.size());
        this.game = new WordGameState(newWord, mistakeLimit);
    }

    public boolean isGameActive(){
        if(this.game != null && game.getMissingChars() == 0){
            this.game = null;
            return false;
        }
        if(this.game != null && game.getMistakes() > game.getMistakeLimit()){
            return false;
        }
        if(this.game != null && game.getMissingChars() != 0){
            return true;}
        
        return false;
    }

    public WordGameState getGameState() throws GameStateException{

        if(!isGameActive()){throw new GameStateException("There is currently no active word game!");}
        else{return game;}
    }
    
    public WordGameState guess(char c) throws GameStateException{
        if(!isGameActive()){throw new GameStateException("There is currently no active word game!");}
        Character guess = Character.toLowerCase(c);
        // underscores
        String word = game.hiddenWord;
        //System.out.print("hidden word is: "+word);

        if(!game.guessedChars.contains(guess)){
            game.guessedChars.add(guess);
        
        Integer length = word.length();
        Integer correct = 0;

        char[] arrayDisplay = game.displayWord.toCharArray();
        char[] arrayHidden = word.toCharArray();

        ArrayList<Character> charArr = new ArrayList<Character>();
        for (int i = 0; i < arrayHidden.length; i++){
            charArr.add(arrayDisplay[i]);
        }

        for( int i = 0; i < length ; i++){
            if(word.charAt(i) == guess){

                charArr.set(i, guess);
                correct =+ 1;
                game.missingChars -= 1;
            }
        }

        if(correct == 0){game.mistakes += 1;}

        char[] newDisplayword = new char[length];
        for (int i =0 ; i < charArr.size() ; i++){
            newDisplayword[i] = charArr.get(i);
        }

        word = new String(newDisplayword);
        game.displayWord = word;
        }

        else{
            game.mistakes += 1;
        }
        if(game.getMistakes() > game.getMistakeLimit()){
            game.displayWord = game.hiddenWord;
        }
        return game;
    }

    public WordGameState guess (String word) throws GameStateException{
        if(!isGameActive()){throw new GameStateException("There is currently no active word game!");}
        String hiddenWord = game.hiddenWord;
        if(word.equals(hiddenWord)){
            game.missingChars = 0;
            game.displayWord = word;
            return game;
        }
        game.mistakes += 1;
        if(game.getMistakes() > game.getMistakeLimit()){
            game.displayWord = game.hiddenWord;
        }
        return game;
    }
}