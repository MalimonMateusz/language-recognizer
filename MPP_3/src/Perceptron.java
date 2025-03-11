import java.nio.file.Path;
import java.util.HashMap;
import java.util.Random;

public class Perceptron {
    String name;
    Double[] weightVector = new Double[26];

    Perceptron(String name){
        this.name = name;
        Random random = new Random();
        for (int i = 0; i <26 ; i++) {
          weightVector[i] = random.nextDouble() * 0.2 - 0.1;
        }
    }


    public int guess(HashMap<Character, Double> a){
        double sum = 0;
        for (int i = 0; i < weightVector.length; i++) {
            sum += a.get((char) (i+97))*weightVector[i];
        }
        if(sum > 0) return 1;
        else return 0;
    }

    public boolean train(HashMap<Character, Double> data, int target){

        int guess = guess(data);
        int error = target - guess;

        for (int i = 0; i < weightVector.length; i++) {
            weightVector[i] +=  error * data.get((char)(i+97));
        }


        return guess == target;
    }




}
