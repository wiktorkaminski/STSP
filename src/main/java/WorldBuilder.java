import com.github.javafaker.Faker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class WorldBuilder {

    public static List<City> createWorld() {
        List<City> myWorld = new LinkedList<City>();
        File citiesInputFile = new File("./cities.csv");
        try {
            Scanner scn = new Scanner(citiesInputFile);
            while (scn.hasNextLine()) {
                String[] inputLine = scn.nextLine().split(";");
                myWorld.add(new City(
                        inputLine[0],                   // city name
                        Float.parseFloat(inputLine[1]), // city longitude
                        Float.parseFloat(inputLine[2])  // city latitude
                ));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return myWorld;
    }

    public static float[][] computeDistance(List<City> myWorld) {
        float[][] distanceMatrix = new float[myWorld.size()][myWorld.size()];
        for (int i = 0; i < distanceMatrix.length; i++) {
            for (int j = i; j < distanceMatrix.length; j++) {
                if (i == j) {
                    distanceMatrix[i][j] = Float.POSITIVE_INFINITY;
                } else {
                    City a = myWorld.get(i);
                    City b = myWorld.get(j);
                    float ax = a.getLongitude();
                    float ay = a.getLatitude();
                    float bx = b.getLongitude();
                    float by = b.getLatitude();
                    distanceMatrix[i][j] = (float) Math.sqrt(Math.pow((ax - ay), 2) + Math.pow((bx - by), 2));
                    distanceMatrix[j][i] = distanceMatrix[i][j];
                }
            }
        }
        return distanceMatrix;
    }

    public static void generateCities(int amount) {
        if (amount <= 0) {
            System.out.println("Number of cities must be positive value. 10 cities was generated as default");
            cityGenerator(10);
        } else {
            cityGenerator(amount);
        }
    }

    private static void cityGenerator(int amount) {
        Faker faker = new Faker();
        Random r = new Random();
        try {
            FileWriter file = new FileWriter("cities.csv", false);
            for (int i = 0; i < amount; i++) {
                file.append(faker.zelda().character()).append(String.valueOf(r.nextInt(3000))).append(";");
                file.append(String.valueOf(r.nextInt(1000))).append(";");
                file.append(String.valueOf(r.nextInt(1000))).append("\n");
            }
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
