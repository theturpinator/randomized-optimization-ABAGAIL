package opt;

import java.util.HashSet;

import dist.Distribution;

import shared.Instance;

/**
 * A simulated annealing hill climbing algorithm
 * @author Andrew Guillory gtg008g@mail.gatech.edu
 * @version 1.0
 */
public class SimulatedAnnealing extends OptimizationAlgorithm {
    
    /**
     * The current optimiation data
     */
    private Instance cur;
    
    /**
     * The current optimization value
     */
    private double curVal;
    
    /**
     * The current temperature
     */
    private double t;
    
    /**
     * The cooling parameter
     */
    private double cooling;
    private HashSet<String> seenCombinations = new HashSet<String>();
    
    /**
     * Make a new simulated annealing hill climbing
     * @param t the starting temperature
     * @param cooling the cooling exponent
     * @param hcp the problem to solve
     */
    public SimulatedAnnealing(double t, double cooling, HillClimbingProblem hcp) {
        super(hcp);
        this.t = t;
        this.cooling = cooling;
        this.cur = hcp.random();
        this.curVal = hcp.value(cur);
        
        seenCombinations.add(cur.toString());
    }

    /**
     * @see shared.Trainer#train()
     */
    public double train() {
        HillClimbingProblem p = (HillClimbingProblem) getOptimizationProblem();
        Instance neigh = p.neighbor(cur);
        
        seenCombinations.add(neigh.toString());
        
        double neighVal = p.value(neigh);
        
        if (neighVal > curVal || Distribution.random.nextDouble() < 
                Math.exp((neighVal - curVal) / t)) {
            curVal = neighVal;
            cur = neigh;
            
        }
        //System.out.println(Distribution.random.nextDouble() + " < " + Math.exp((neighVal - curVal) / t));
        t *= cooling;
        
       
        
        
        return curVal;
    }

    /**
     * @see opt.OptimizationAlgorithm#getOptimal()
     */
    public Instance getOptimal() {
        return cur;
    }
    
    public int getSeenCombinations() {
    	return seenCombinations.size();
    }

}