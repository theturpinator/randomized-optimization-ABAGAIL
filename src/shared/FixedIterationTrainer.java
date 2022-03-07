package shared;

/**
 * A fixed iteration trainer
 * @author Andrew Guillory gtg008g@mail.gatech.edu
 * @version 1.0
 */
public class FixedIterationTrainer implements Trainer {
    
    /**
     * The inner trainer
     */
    private Trainer trainer;
    
    /**
     * The number of iterations to train
     */
    private int iterations;
    
    /**
     * Make a new fixed iterations trainer
     * @param t the trainer
     * @param iter the number of iterations
     */
    public FixedIterationTrainer(Trainer t, int iter) {
        trainer = t;
        iterations = iter;
    }

    /**
     * @see shared.Trainer#train()
     */
    public double train() {
  
    	double curMinus3 = -3;
    	double curMinus2 = -2;
    	double curMinus1 = -1;
    	double curVal = 0;
    	Instance cur;
    	
        double sum = 0;
        for (int i = 0; i < iterations; i++) {
        	
        	curMinus3 = curMinus2;
        	curMinus2 = curMinus1;
        	curMinus1 = curVal;
        	
        	curVal = trainer.train();
            sum += curVal;
            
            
            //comment out for simulated annealing
            /*
            if (curVal == curMinus1 && curVal == curMinus2 && curVal == curMinus3) {
            	break;
            }
            */
            
            
            
        }
        return sum / iterations;
    }
    

}
