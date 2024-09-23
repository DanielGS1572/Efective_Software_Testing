package ch1;

import java.util.Arrays;
import java.util.List;

public class PlanningPokerByJohn {
    /*
    The method should receive a list of developers and their respective estimates and return the two developers with the most extreme estimates.
    Aniche, Maurizio. Effective Software Testing: A developer's guide (English Edition) (p. 36). Manning. Edición de Kindle.
    * */
    public List<String> identifyExtremes(List<Estimate> estimates) {

        Estimate lowestEstimate = null;
        Estimate highestEstimate = null;

        for(Estimate estimate: estimates) {
            if(highestEstimate == null ||
                    estimate.getEstimate() > highestEstimate.getEstimate()) {
                highestEstimate = estimate;
            }
            else if(lowestEstimate == null ||           //por el else es que truena el código
                    estimate.getEstimate() < lowestEstimate.getEstimate()) {
                //el test2 de OrderEstimates truena porque la lista esta ordenada y todos los valores serán máximos, para solucionarlo se tiene que quitar el else en el código
                lowestEstimate = estimate;
            }
        }

        return Arrays.asList(
                lowestEstimate.getDeveloper(),
                highestEstimate.getDeveloper()
        );
    }
}
