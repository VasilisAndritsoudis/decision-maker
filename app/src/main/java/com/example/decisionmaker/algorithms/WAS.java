package com.example.decisionmaker.algorithms;


import java.util.ArrayList;
import java.util.Arrays;


public class WAS {

    /**
     * Takes a 2-dimensional matrix in which every row is a possible choice and
     * every column is a criterion
     * Every element in the matrix has to be between 1-100 grading the choice i for criterion j
     * And an array weighting the importance of each criterion
     * @param grades the 2-d matrix with the grades of the choices for each criterion (1-100)
     * @param weights the array with the weights of the criteria (1-100)
     * @return an array with the grating of each choice (normalized 1-100)
     */
    public static ArrayList<Integer> logic(ArrayList<ArrayList<Integer>> grades, ArrayList<Integer> weights){
        //normalize weights 0-1
        int ws = 0;  // weight sum
        for (int w : weights) ws += w;

        ArrayList<Double> tempWeights = new ArrayList<>();
        for (int w : weights) {
            tempWeights.add(w/((double) ws));
        }

        // calculate scores
        ArrayList<Double> tempRes = new ArrayList<>();
        for (ArrayList<Integer> choice: grades) {
            double totalGrade = 0;
            for (int i = 0; i < choice.size(); i++) {
                // grades[i].size() should be equal to weights.size()
                totalGrade += choice.get(i) * tempWeights.get(i);
            }
            tempRes.add(totalGrade);
        }

        //first normalization of scores
        double ss = 0;  // score sum
        double m = 0; //max value (temp)
        int mIndex = 0; //index of max value
        for (int i = 0; i < tempRes.size(); i++) {
            double s = tempRes.get(i);
            ss += s;
            if (s > m) {
                m = s;
                mIndex = i;
            }
        }

        ArrayList<Integer> res = new ArrayList<>();
        for (double s : tempRes)
            res.add((int) (s*100/ss));

        //Give advantage to the best
        int rest = 100;
        for (int s : res)
            rest -= s;

        res.set(mIndex, res.get(mIndex) + rest);

        return res;
    }


    public static void main(String[] args){

        ArrayList<Integer> P1 = new ArrayList<>(
                Arrays.asList(43, 56, 77)
        );
        ArrayList<Integer> P2 = new ArrayList<>(
                Arrays.asList(72, 49, 91)
        );
        ArrayList<Integer> P3 = new ArrayList<>(
                Arrays.asList(78, 75, 70)
        );

        ArrayList<ArrayList<Integer>> choices = new ArrayList<>(
                Arrays.asList(P1, P2, P3)
        );

        ArrayList<Integer> weights = new ArrayList<>(
                Arrays.asList(25, 80, 38)
        );

        ArrayList<Integer> out = logic(choices, weights);

        System.out.println(out);
    }
}

