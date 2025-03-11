import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<Perceptron> perceptrons = new ArrayList<>();
        File learningFolder = new File("MPP_3/LEARN");
        File testFolder = new File("MPP_3/TEST");


        int numOfLeaningFiles = getNumOfTxt(learningFolder);

        System.out.println(numOfLeaningFiles);

        //Perceptron Creator
        if (learningFolder.isDirectory()) {
            File[] files = learningFolder.listFiles();
            for (File file : files) {
                perceptrons.add(new Perceptron(file.getName()));
            }
        }


        //Learning
        for (Perceptron perceptron : perceptrons) {
            for (int i = 0; i <100 ; i++) {
                double accuracy = 0.0;

                for(File file : learningFolder.listFiles()){
                    for(File f : file.listFiles()){

                        if(file.getName().equals(perceptron.name)){
                           Boolean a =  perceptron.train(getData(f), 1);
                          if(a) accuracy++;
                        }else{
                            Boolean a =  perceptron.train(getData(f), 0);
                            if(a) accuracy++;
                        }
                    }
                }
                System.out.println(perceptron.name + ": " + accuracy/numOfLeaningFiles * 100 + "%" );
                if(accuracy == numOfLeaningFiles) break;
            }
        }


        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Own text => 1 ");
            System.out.println("Testing directory => 2 ");
            System.out.println("Exit => 3 ");
             String choice = scanner.nextLine();

            if (choice.equals("3")) {
                System.out.println("Thanks For Using Our Software!");
                break;
            }
            if (choice.equals("1")) {
                System.out.println("write your text, then write !!! and press ENTER to continue");
                StringBuilder stringBuilder = new StringBuilder();
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (line.equals("!!!")) {
                        break;
                    }
                    stringBuilder.append(line).append("\n");
                }
                System.out.println();
                String a = stringBuilder.toString();
                for (Perceptron p : perceptrons) {
                    if (p.guess(getData(a)) == 1) {
                        System.out.println(p.name);
                        break;
                    }
                }
            }
            if (choice.equals("2")) {
                if (testFolder.isDirectory()) {
                    for (File txt : testFolder.listFiles()) {
                        System.out.println(txt.getName());
                        int token = 0;
                        for (Perceptron p : perceptrons) {
                            if (p.guess(getData(txt)) == 1) {
                                System.out.println(p.name);
                                token++;
                                break;
                            }
                        }
                        if (token == 0) System.out.println("\u001B[31m" + "ERROR" + "\u001B[0m");
                    }
                }
            }
        }













    }



    public static int getNumOfTxt(File learningFolder){
        int numOfTxt = 0;
        for(File f : learningFolder.listFiles()){
           for (File ff : f.listFiles()){
               numOfTxt++;
           }
        }
        return numOfTxt;
    }





    //Works
    public static HashMap<Character, Double> getData(File file) throws IOException {
        int numOfLetters = 0;
        HashMap<Character, Double> data = new HashMap<>();
        for (int i = 0; i < 26; i++) {
            data.put((char) (i + 97), 0.0);
        }

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                char[] temp = scanner.nextLine().toCharArray();
                for (char c : temp) {
                    if ((c < 97 || c > 122) && (c < 65 || c > 90)) {
                        continue;
                    }
                    numOfLetters++;
                    c = Character.toLowerCase(c);
                    Double a = data.get(c);
                    data.put(c, a + 1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        for (int i = 0; i < 26; i++) {
            Double a = data.get((char) (i + 97));
            a = a / numOfLetters;
            data.put((char) (i + 97), a);
        }


        return data;
    }

    public static HashMap<Character, Double> getData(String input) throws IOException {
        int numOfLetters = 0;
        char[] charArr = input.toCharArray();
        HashMap<Character, Double> data = new HashMap<>();
        for (int i = 0; i < 26; i++) {
            data.put((char) (i + 97), 0.0);
        }
        for (char c : charArr) {
            if ((c < 97 || c > 122) && (c < 65 || c > 90)) {
                continue;
            }
            numOfLetters++;
            c = Character.toLowerCase(c);
            Double a = data.get(c);
            data.put(c, a + 1);
        }

        for (int i = 0; i < 26; i++) {
            Double a = data.get((char) (i + 97));
            a = a / numOfLetters;
            data.put((char) (i + 97), a);
        }

        return data;
    }



}