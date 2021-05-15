package com.example.decisionmaker.algorithms;

import java.util.ArrayList;
import java.util.Arrays;


public class WAS {

    /**
     * Takes a 2-dimensional matrix in which every row is a possible choice and
     * every column is a criterion
     * Every element in the matrix has to be between 0-1 grading the choice i for criterion j
     * And an array weighting the importance of each criterion (normalized to 1)
     * @param grades the 2-d matrix with the grades of the choices for each criterion
     * @param weights the array with the weights of the criteria
     * @return an array with the grating of each choice (normalized to 1)?
     */
    public static ArrayList<Double> logic(ArrayList<ArrayList<Double>> grades, ArrayList<Double> weights){
        ArrayList<Double> res = new ArrayList<>();
        for (ArrayList<Double> choice: grades) {
            double totalGrade = 0;
            for (int i = 0; i < choice.size(); i++) {
                // grades[i].size() should be equal to weights.size()
                totalGrade += choice.get(i) * weights.get(i);
            }
            res.add(totalGrade);
        }

        return res;
    }


    /**
     * Normalizes a set of values in order to sum up to 1
     * with 2 digit accuracy
     * @param values a set of values
     */
    public static void normalize(ArrayList<Double> values) {
        double s = 0; // the sum of the values
        for (double x : values)
            s += x;

        for (int i = 0; i < values.size(); i++)
            values.set(i, values.get(i) / s); // first normalization
        //*
        ArrayList<Double> balances = new ArrayList<>();
        double firstDigits;
        for (int i = 0; i < values.size(); i++) {
            // keep the first two digits
            firstDigits = Math.round(values.get(i) * 100) / 100.0;
            // store the rest digits
            balances.add(values.get(i) - firstDigits);
            values.set(i, firstDigits);
        }

        // if the new set doesn't sum up tο 1
        while (values.stream().mapToDouble(a -> a).sum() != 1.00) {
            int maxIndex = 0;
            for (double x: balances )
                if (x > balances.get(maxIndex))
                    maxIndex = balances.indexOf(x);
            balances.set(maxIndex, 0d);

            // correct the error
            values.set(maxIndex, values.get(maxIndex) + 0.01);
        }
        //*/ θα πρεπει να το κανω ετσι ώστε πάντα να γυρνάει ένα μεγαλύτερο έστω για 1% γιαφρά
    }


    public static void main(String[] args){

        ArrayList<Double> P1 = new ArrayList<>(
                Arrays.asList(0.4, 0.9, 0.5)
        );
        ArrayList<Double> P2 = new ArrayList<>(
                Arrays.asList(0.7, 0.5, 0.9)
        );
        ArrayList<Double> P3 = new ArrayList<>(
                Arrays.asList(0.8, 0.7, 0.7)
        );

        ArrayList<ArrayList<Double>> choices = new ArrayList<>(
                Arrays.asList(P1, P2, P3)
        );

        ArrayList<Double> weights = new ArrayList<>(
                Arrays.asList(0.15, 0.62, 0.23)
        );

        ArrayList<Double> out = logic(choices, weights);

        System.out.println(out);
        normalize(out);
        System.out.println(out);
    }
}
