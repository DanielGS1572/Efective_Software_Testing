package ch1;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class PlanningPokerByJohnTest {

    @Test
    void test1() {
        List<String> developers = new PlanningPokerByJohn().identifyExtremes(
                Arrays.asList(
                        new Estimate("Mauricio", 16),
                        new Estimate("Frank", 8),
                        new Estimate("Arie", 2),
                        new Estimate("Andy", 4)));

        System.out.println(developers);
    }
    @Test
    void test2(){
        List<String> developers = new PlanningPokerByJohn().identifyExtremes(
                Arrays.asList(
                        new Estimate("Ross", 2),
                        new Estimate("Phoebe", 4),
                        new Estimate("Monica", 8),
                        new Estimate("Chandler", 16)));

        System.out.println(developers);
    }
}
