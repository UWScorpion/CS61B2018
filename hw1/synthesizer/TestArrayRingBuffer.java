package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<String> arb = new ArrayRingBuffer<>(10);
        arb.enqueue("how");
        arb.enqueue("are");
        arb.enqueue("u");
        arb.dequeue();
        arb.dequeue();
        arb.enqueue("how");
        arb.enqueue("are");
        arb.enqueue("u");
        arb.enqueue("how");
        arb.enqueue("are");
        arb.enqueue("u");
        arb.enqueue("how");
        arb.enqueue("are");
        arb.enqueue("u");
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
