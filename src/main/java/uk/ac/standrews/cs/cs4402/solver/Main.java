package uk.ac.standrews.cs.cs4402.solver;

import uk.ac.standrews.cs.cs4402.solver.dataModel.BinaryCSP;
import uk.ac.standrews.cs.cs4402.solver.graphDataModel.BinaryCSPGraph;
import uk.ac.standrews.cs.cs4402.solver.graphDataModel.NoSolutionException;
import uk.ac.standrews.cs.cs4402.solver.graphDataModel.VarNode;
import uk.ac.standrews.cs.cs4402.solver.input.BinaryCSPReader;

public class Main {
    static String inputFile = "langfords2_3.csp";
    public static void main(String args[]){
        if(args.length==1){
            inputFile = args[0];
        }
//        try {
//            Thread.sleep(25000);
//            System.out.println("starting in 1s");
//            Thread.sleep(1000);
//        }catch (InterruptedException ex){}

        long startTime, readTime, graphBuildTime, solverSetupTime, solveTime;

        startTime = System.nanoTime();

        BinaryCSPReader reader = new BinaryCSPReader() ;
        //System.out.println(reader.readBinaryCSP(inputFile)) ;

        BinaryCSP bcsp = reader.readBinaryCSP(inputFile);
        readTime = System.nanoTime();

        BinaryCSPGraph bcspg = BinaryCSPGraph.buildGraph(bcsp);
        graphBuildTime = System.nanoTime();
        if (bcsp.getNoVariables() < 15)
            bcspg.draw();
        /*bcspg.pruneFromVariableDomain(0, 3);
        bcspg.pruneFromVariableDomain(0, 0);
        bcspg.reviseArcs(0);
        //bcspg.push();
        bcspg.pruneFromVariableDomain(1, 0);
        bcspg.pruneFromVariableDomain(1, 3);
        bcspg.pruneFromVariableDomain(1, 2);
        bcspg.reviseArcs(1);
        //bcspg.push();
        //bcspg.pop();
        //bcspg.pop();
        */

        Solver solver = new ForwardSolver();
        solver.setCSP(bcspg);
        boolean SAT = false;
        solverSetupTime = System.nanoTime();
        solver.displaySearchTree(true);
        try {
            SAT = solver.step(true);
        } catch (NoSolutionException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        solveTime = System.nanoTime();
        //if (solver.getNumNodes() < 250)
        //    solver.displaySearchTree(false);
        System.out.println(SAT);
        if (SAT) {
            System.out.println("Assignments: ");
            for (Integer var : solver.getAssignments().keySet()) {
                System.out.print("Var_" + var.toString());
                System.out.println(": " + solver.getAssignments().get(var).toString());
            }
        }
        System.out.println("Read took " + Double.toString((readTime - startTime) / 1000000) + "ms");
        System.out.println("Graph building took " + Double.toString((graphBuildTime - readTime) / 1000000) + "ms");
        System.out.println("Solver setup took " + Double.toString((solverSetupTime - graphBuildTime) / 1000000) + "ms");
        System.out.println("Solving took " + Double.toString((solveTime - solverSetupTime) / 1000000) + "ms");

        System.out.println("SolverNodes: " + Integer.toString(solver.getNumNodes()));
    }
}
