package coins.state;

import org.junit.jupiter.api.Test;
import org.apache.commons.math3.util.CombinatoricsUtils;
import java.util.BitSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CoinsTest {

    private Coins state1 = new Coins(7, 3); // the original initial state

    private Coins state2; // the goal state
    {
        BitSet bs = new BitSet(7);
        bs.set(0, 7);
        state2 = new Coins(7, 3, bs);
    }

    @Test
    public void testIsGoal(){
        Coins state3 = new Coins(7,3);
        BitSet bs2= new BitSet(7);
        bs2.set(0,7,true);
        state3.flip(bs2);
        assertTrue(state3.isGoal());
    }
    @Test
    public void testCanFlipWhenCantFlip(){
        BitSet bs2=new BitSet(10);

        bs2.set(0,4,true);
        bs2.set(4,10,false);
        assertFalse(state1.canFlip(bs2));
    }

    @Test
    public void testCanFlipWhenCanFlip(){
        BitSet bs2=new BitSet(6);
        bs2.set(0,3,true);
        assertTrue(state1.canFlip(bs2));
    }

    @Test
    public void testFlip(){
        Coins state3 = new Coins(7, 3);
        state3.flip(state1.getFlips().get(0));
        assertEquals(state3.getFlips().get(0),state1.getFlips().get(0));
    }
    @Test
    public void testGenerateFlip(){
        assertEquals(CombinatoricsUtils.binomialCoefficient(7,3),Coins.generateFlips(7,3).size());
    }

    @Test
    public void testGetFlips(){
        assertEquals(CombinatoricsUtils.binomialCoefficient(state1.getN(),state1.getM()),state1.getFlips().size());

    }



    @Test
    public void testEquals(){
        assertTrue(state1.equals(new Coins(7,3)));
        assertTrue(state1.equals(new Coins(7,3,new BitSet(7))));
    }
    @Test
    public void testHashCode(){
        Coins x = new Coins(7,3);
        Coins y = new Coins(7,3);
        assertTrue(x.equals(y) && y.equals(x));
        assertTrue(x.hashCode() == y.hashCode());
    }

    @Test
    public void testClone(){
        Coins original = new Coins(7,3);
        Coins cloned = original.clone();

        assertEquals(original.getFlips(), cloned.getFlips());
        assertNotSame(original.getFlips(), cloned.getFlips());
    }
    @Test
    public void testToString(){
        String result="O|O|O|O|O|O|O";
        assertEquals(result,state1.toString());

        String result2="O|O|O|O|O|O|O|O|O|O";
        assertEquals(result2,new Coins(10,3).toString());

    }

    @Test
    public void testCheckArgumentsWithWrongArguments(){
        assertThrows(IllegalArgumentException.class,()->new Coins(5,10));
        assertThrows(IllegalArgumentException.class,()->new Coins(0,10));
        assertThrows(IllegalArgumentException.class,()->new Coins(100,0));

        BitSet bs2 = new BitSet(7);
        bs2.set(0,7);
        assertThrows(IllegalArgumentException.class,()->new Coins(6,3,bs2));
    }

    @Test
    public void testDeepCopy(){
        List<BitSet> state1bitSets=state1.getFlips();
        assertNotSame( state1bitSets,state1.getFlips()); //they should not have the same reference.
    }


}