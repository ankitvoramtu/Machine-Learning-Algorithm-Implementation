package optProblems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import opt.ga.NQueensFitnessFunction;
import dist.DiscreteDependencyTree;
import dist.DiscretePermutationDistribution;
import dist.DiscreteUniformDistribution;
import dist.Distribution;
import opt.DiscreteChangeOneNeighbor;
import opt.EvaluationFunction;
import opt.GenericHillClimbingProblem;
import opt.HillClimbingProblem;
import opt.NeighborFunction;
import opt.OptimizationAlgorithm;
import opt.RandomizedHillClimbing;
import opt.SimulatedAnnealing;
import opt.SwapNeighbor;
import opt.example.*;
import opt.ga.CrossoverFunction;
import opt.ga.DiscreteChangeOneMutation;
import opt.ga.SingleCrossOver;
import opt.ga.GenericGeneticAlgorithmProblem;
import opt.ga.GeneticAlgorithmProblem;
import opt.ga.MutationFunction;
import opt.ga.StandardGeneticAlgorithm;
import opt.ga.SwapMutation;
import opt.prob.GenericProbabilisticOptimizationProblem;
import opt.prob.MIMIC;
import opt.prob.ProbabilisticOptimizationProblem;
import shared.FixedIterationTrainer;

/**
 * @author kmanda1
 * @version 1.0
 */
public class NQueensProblem {
    /** The n value */
    private static final int N = 10;
    /** The t value */
    
    /**
     * method to generate optimization fitness values 
     * data for n-queens problems
     * @param name
     */
    public static void experiment(String name) {
    	String probName = "NQueensProblem";
    	List<OptimizationAlgorithm> algs = new ArrayList<>();
        int[] ranges = new int[N];
        Random random = new Random(N);
        for (int i = 0; i < N; i++) {
        	ranges[i] = random.nextInt();
        }
        NQueensFitnessFunction ef = new NQueensFitnessFunction();
        Distribution odd = new DiscretePermutationDistribution(N);
        NeighborFunction nf = new SwapNeighbor();
        MutationFunction mf = new SwapMutation();
        CrossoverFunction cf = new SingleCrossOver();
        Distribution df = new DiscreteDependencyTree(.1); 
        
        HillClimbingProblem hcp = new GenericHillClimbingProblem(ef, odd, nf);
        GeneticAlgorithmProblem gap = new GenericGeneticAlgorithmProblem(ef, odd, mf, cf);
        ProbabilisticOptimizationProblem pop = new GenericProbabilisticOptimizationProblem(ef, odd, df);
        
        RandomizedHillClimbing rhc = new RandomizedHillClimbing(hcp);      
        SimulatedAnnealing sa = new SimulatedAnnealing(1E1, .1, hcp);
        StandardGeneticAlgorithm ga = new StandardGeneticAlgorithm(200, 0, 10, gap);
        MIMIC mimic = new MIMIC(200, 10, pop);
        
        algs.add(rhc);
        algs.add(sa);
        algs.add(ga);
        algs.add(mimic);
        Experiment newExp = new Experiment();
        
        newExp.experiments(algs, ef, name);
        
        // test temperature for SA
        String paramName = "Temperature";
        String algName = "SA";
        List<Double> params2 = new ArrayList<>();
        List<OptimizationAlgorithm> algsTest = new ArrayList<>();
        for (double i = 1.0; i < 100.5; i+=1.0){
        	params2.add(i);
        	sa = new SimulatedAnnealing(i, .1, hcp);
        	algsTest.add(sa);
        }
        newExp.optParams(algsTest, ef, paramName, null, params2, algName, probName);
        
        // test cooling rate for SA
        paramName = "CoolingRate";
        params2 = new ArrayList<>();
        algsTest = new ArrayList<>();
        for (double i = 0.01; i < 0.51; i+=0.005){
        	params2.add(i);
        	sa = new SimulatedAnnealing(1E1, i, hcp);
        	algsTest.add(sa);
        }
        newExp.optParams(algsTest, ef, paramName, null, params2, algName, probName);
        
        // test populationSize for GA
        paramName = "populationSize";
        algName = "GA";
        List<Integer> params1 = new ArrayList<>();
        algsTest = new ArrayList<>();
        for (int i = 10; i < 1001; i+=10){
        	params1.add(i);
        	ga = new StandardGeneticAlgorithm(i, 0, 10, gap);
        	algsTest.add(ga);
        }
        newExp.optParams(algsTest, ef, paramName, params1, null, algName, probName);
        
        // test toMate for GA
        paramName = "toMate";
        params1 = new ArrayList<>();
        algsTest = new ArrayList<>();
        for (int i = 0; i < 100; i+=1){
        	params1.add(i);
        	ga = new StandardGeneticAlgorithm(200, i, 10, gap);
        	algsTest.add(ga);
        }
        newExp.optParams(algsTest, ef, paramName, params1, null, algName, probName);
        
        // test toMutate for GA
        paramName = "toMutate";
        params1 = new ArrayList<>();
        algsTest = new ArrayList<>();
        for (int i = 10; i < 1001; i+=10){
        	params1.add(i);
        	ga = new StandardGeneticAlgorithm(200, 0, i, gap);
        	algsTest.add(ga);
        }
        newExp.optParams(algsTest, ef, paramName, params1, null, algName, probName);
        
        // test samples for MIMIC
        paramName = "samples";
        algName = "MIMIC";
        params1 = new ArrayList<>();
        algsTest = new ArrayList<>();
        for (int i = 100; i < 1101; i += 10){
        	params1.add(i);
        	mimic = new MIMIC(i, 10, pop);
        	algsTest.add(mimic);
        }
        newExp.optParams(algsTest, ef, paramName, params1, null, algName, probName);
        
        // test tokeep for MIMIC
        paramName = "tokeeep";
        params1 = new ArrayList<>();
        algsTest = new ArrayList<>();
        for (int i = 1; i < 101; i+=1){
        	params1.add(i);
        	mimic = new MIMIC(200, i, pop);
        	algsTest.add(mimic);
        }
        newExp.optParams(algsTest, ef, paramName, params1, null, algName, probName);
        
        
        // test different algorithms with various NQueensProblems
        // set up algorithms
        algs = new ArrayList<>();
        ga = new StandardGeneticAlgorithm(150, 98, 70, gap);
        algs.add(rhc);
        algs.add(sa);
        algs.add(ga);
        algs.add(mimic);
        // set up different efs
        List<EvaluationFunction> efs = new ArrayList<>();
        for (int i = 0; i < 50; i++){
        	ef = new NQueensFitnessFunction();
        	efs.add(ef);
        }
        newExp.voteBest(algs, efs, 2000, probName);
  
    }
}