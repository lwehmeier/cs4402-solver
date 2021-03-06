package uk.ac.standrews.cs.cs4402.solver.heuristics.values;

import uk.ac.standrews.cs.cs4402.solver.graphDataModel.VarNode;

public abstract class ValueOrderingHeuristic {

    public abstract int getNextVal(VarNode vn);


    protected long setup_time_us=0;
    protected long compute_time_us =0;

    public long getSetup_time_us() {
        return setup_time_us;
    }

    public long getCompute_time_us() {
        return compute_time_us;
    }
    //fixing java deficiencies...
    //https://stackoverflow.com/questions/182278/is-there-a-way-to-simulate-the-c-friend-concept-in-java
    public static final class Key { private Key() {} }
    protected static final Key key = new Key();
}
