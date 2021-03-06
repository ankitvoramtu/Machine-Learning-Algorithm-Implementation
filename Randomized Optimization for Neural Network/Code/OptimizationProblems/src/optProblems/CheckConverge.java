/**
 * 
 */
package optProblems;

import java.util.ArrayList;
import java.util.List;

import opt.EvaluationFunction;
import opt.OptimizationAlgorithm;
import opt.RandomizedHillClimbing;
import opt.example.TravelingSalesmanEvaluationFunction;
import shared.FixedIterationTrainer;
import shared.Trainer;

/**
 * @author zhihuixie
 *
 */
public class CheckConverge {
	OptimizationAlgorithm alg;
	EvaluationFunction ef;
	int iters;
	/**
	 * constructor
	 * @param alg
	 * @param ef
	 * @param iters
	 */
	public CheckConverge(OptimizationAlgorithm alg, EvaluationFunction ef, int iters) {
		// TODO Auto-generated constructor stub
		this.alg = alg;
		this.ef = ef;
		this.iters = iters;
	}
    
	/**
	 * method to get fitness values
	 * @return
	 */
	public List<Double> fitnessValue() {
		// TODO Auto-generated method stub
		List<Double> fitnessScore = new ArrayList<>();
        for (int i = 10; i< iters; i += 10){
            FixedIterationTrainer fit = new FixedIterationTrainer(this.alg, i);
            fit.train();
            fitnessScore.add(this.ef.value(alg.getOptimal()));
        }
		return fitnessScore;
	}

}
