package opt.test;

import java.util.Arrays;

import dist.DiscreteDependencyTree;
import dist.DiscreteUniformDistribution;
import dist.Distribution;

import opt.DiscreteChangeOneNeighbor;
import opt.EvaluationFunction;
import opt.GenericHillClimbingProblem;
import opt.HillClimbingProblem;
import opt.NeighborFunction;
import opt.RandomizedHillClimbing;
import opt.SimulatedAnnealing;
import opt.example.*;
import opt.ga.CrossoverFunction;
import opt.ga.DiscreteChangeOneMutation;
import opt.ga.GenericGeneticAlgorithmProblem;
import opt.ga.GeneticAlgorithmProblem;
import opt.ga.MutationFunction;
import opt.ga.StandardGeneticAlgorithm;
import opt.ga.UniformCrossOver;
import opt.prob.GenericProbabilisticOptimizationProblem;
import opt.prob.MIMIC;
import opt.prob.ProbabilisticOptimizationProblem;
import shared.FixedIterationTrainer;

/**
 * 
 * @author Andrew Guillory gtg008g@mail.gatech.edu
 * @version 1.0
 */
public class CountOnesTest {
    /** The n value */
    private static final int N = 80;
    
    public static void main(String[] args) {
        int[] ranges = new int[N];
        Arrays.fill(ranges, 2);
        CountOnesEvaluationFunction ef = new CountOnesEvaluationFunction();
        Distribution odd = new DiscreteUniformDistribution(ranges);
        NeighborFunction nf = new DiscreteChangeOneNeighbor(ranges);
        MutationFunction mf = new DiscreteChangeOneMutation(ranges);
        CrossoverFunction cf = new UniformCrossOver();
        Distribution df = new DiscreteDependencyTree(.1, ranges); 
        HillClimbingProblem hcp = new GenericHillClimbingProblem(ef, odd, nf);
        GeneticAlgorithmProblem gap = new GenericGeneticAlgorithmProblem(ef, odd, mf, cf);
        ProbabilisticOptimizationProblem pop = new GenericProbabilisticOptimizationProblem(ef, odd, df);
        
        RandomizedHillClimbing rhc = new RandomizedHillClimbing(hcp);      
        FixedIterationTrainer fit = new FixedIterationTrainer(rhc, 1000);
        
        long starttime = System.currentTimeMillis();
        fit.train();
        
        System.out.println("\n\n");
        System.out.println("Time : " + (System.currentTimeMillis() - starttime));
        System.out.println("fEvals : " + ef.getFunctionEvaluations());
        System.out.println("seenCombinations : " + rhc.getSeenCombinations());
        System.out.println("accuracy : " + ef.value(rhc.getOptimal()));
        System.out.println("============================");
        ef.resetFunctionEvaluationCount();
        
        
        SimulatedAnnealing sa = new SimulatedAnnealing(100, .95, hcp);
        fit = new FixedIterationTrainer(sa, 1000);
        starttime = System.currentTimeMillis();
        fit.train();
        System.out.println("\n\n");
        System.out.println("Time : " + (System.currentTimeMillis() - starttime));
        System.out.println("fEvals : " + ef.getFunctionEvaluations());
        System.out.println("seenCombinations : " + sa.getSeenCombinations());
        System.out.println("accuracy : " + ef.value(sa.getOptimal()));
        System.out.println("============================");
        ef.resetFunctionEvaluationCount();
        
        
        
        StandardGeneticAlgorithm ga = new StandardGeneticAlgorithm(200, 20, 0, gap);
        fit = new FixedIterationTrainer(ga, 300);
        starttime = System.currentTimeMillis();
        fit.train();
        System.out.println("\n\n");
        System.out.println("Time : " + (System.currentTimeMillis() - starttime));
        System.out.println("fEvals : " + ef.getFunctionEvaluations());
        System.out.println("seenCombinations : " + ga.getSeenCombinations());
        System.out.println("accuracy : " + ef.value(ga.getOptimal()));
        System.out.println("============================");
        ef.resetFunctionEvaluationCount();
        
        
        
        MIMIC mimic = new MIMIC(50, 10, pop);
        fit = new FixedIterationTrainer(mimic, 100);
        starttime = System.currentTimeMillis();
        fit.train();
        System.out.println("\n\n");
        System.out.println("Time : " + (System.currentTimeMillis() - starttime));
        System.out.println("fEvals : " + ef.getFunctionEvaluations());
        System.out.println("seenCombinations : " + mimic.getSeenCombinations());
        System.out.println("accuracy : " + ef.value(mimic.getOptimal()));
        System.out.println("============================");
        ef.resetFunctionEvaluationCount();
    }
}