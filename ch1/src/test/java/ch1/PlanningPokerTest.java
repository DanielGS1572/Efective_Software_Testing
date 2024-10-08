package ch1;

import net.jqwik.api.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class PlanningPokerTest {

    @Test
    void rejectNullInput() {
        assertThatThrownBy(() -> new PlanningPoker().identifyExtremes(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectEmptyList() {
        assertThatThrownBy(() -> {
            List<Estimate> emptyList = Collections.emptyList();
            new PlanningPoker().identifyExtremes(emptyList);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectSingleEstimate() {
        assertThatThrownBy(() -> {
            List<Estimate> list = Collections.singletonList(new Estimate("Eleanor", 1));
            new PlanningPoker().identifyExtremes(list);
        }).isInstanceOf(IllegalArgumentException.class);
    }
/*
a list with exactly two elements and a list with more than two elements.
Why two?
- A list with two elements is the smallest for which the method should work.
There is a boundary between a list with one element (which does not work) and two elements (which does work).
" bugs love boundaries, so she decides to also have a dedicated test for it "
*/
    @Test
    void twoEstimates() {
        List<Estimate> list = Arrays.asList(
                new Estimate("Mauricio", 10),
                new Estimate("Frank", 5)
        );

        List<String> devs = new PlanningPoker().identifyExtremes(list);

        assertThat(devs)
                .containsExactlyInAnyOrder("Mauricio", "Frank");
    }
/*
    Ver que este test es para validar el orden del output, después será eliminado por property based testing
*/
    // this test was later deleted by Eleanor, as the property based testing
    // replaces this one.
    @Test
    void manyEstimates() {
        List<Estimate> list = Arrays.asList(
                new Estimate("Mauricio", 10),
                new Estimate("Arie", 5),
                new Estimate("Frank", 7)
        );

        List<String> devs = new PlanningPoker().identifyExtremes(list);

        assertThat(devs)
                .containsExactlyInAnyOrder("Mauricio", "Arie");
    }

    /*
    Aquí es donde se usa property based testing
    Ver que los test anteriores no hubieran cachado el defecton con el ELSE if
    Descomentar el else y ver que truena en estimatesInAnyOrder
     */

    @Property
    void estimatesInAnyOrder(@ForAll("estimates") List<Estimate> estimates) {       //estimates es el nombre del metodo que generará los datos random
//[A] Se genera una lista de estimates y se meten valores que se espera que se cachen al llamar identifyExtremes
        //System.out.println(estimates.size());
        estimates.add(new Estimate("MrLowEstimate", 1));
        estimates.add(new Estimate("MsHighEstimate", 100));
        Collections.shuffle(estimates);

        List<String> dev = new PlanningPoker().identifyExtremes(estimates);

        //A pesar de que se hizo shuffle y datos aleatorios se tienen dos valores que son conocidos y es lo que se espera que se tenga por resultado
        assertThat(dev)
                .containsExactlyInAnyOrder("MrLowEstimate", "MsHighEstimate");
    }

    @Provide
    Arbitrary<List<Estimate>> estimates() {
        Arbitrary<String> names = Arbitraries.strings().withCharRange('a', 'z').ofLength(5);
        Arbitrary<Integer> values = Arbitraries.integers().between(2, 99); //Aqui no se definen por completo si no que en [A] les pasa los valores min/max

        Arbitrary<Estimate> estimates = Combinators.combine(names, values)
                .as((name, value) -> new Estimate(name, value));            //hace la combinación de names y values

        return estimates.list().ofMinSize(1);
    }

    @Test
    void developersWithSameEstimates() {
        List<Estimate> list = Arrays.asList(
                new Estimate("Mauricio", 10),
                new Estimate("Arie", 5),
                new Estimate("Andy", 10),
                new Estimate("Frank", 7),
                new Estimate("Annibale", 5)
        );

        List<String> devs = new PlanningPoker().identifyExtremes(list);

        assertThat(devs)
                .containsExactlyInAnyOrder("Mauricio", "Arie");
    }

    @Test
    void allDevelopersWithTheSameEstimate() {
        List<Estimate> list = Arrays.asList(
                new Estimate("Mauricio", 10),
        new Estimate("Arie", 10),
                new Estimate("Andy", 10),
                new Estimate("Frank", 10),
                new Estimate("Annibale", 10)
  );
        List<String> devs = new PlanningPoker().identifyExtremes(list);

        assertThat(devs).isEmpty();

    }

}
