package roboy.linguistics.sentenceanalysis;

import roboy.linguistics.Linguistics;
import roboy.util.MovieList;

import java.util.Arrays;
import java.util.List;

/**
 * Checks for movie references and adds the respective movie to the Interpretation.
 */
public class MovieAnalyzer implements Analyzer {

    private int WHATCOUNT = 0;

    public Interpretation analyze(Interpretation sentence)
    {
        List<String> tokens = Arrays.asList((String[]) sentence.getFeature(Linguistics.TOKENS));

       // sample movies which roboy can detect        
        if (tokens.contains("terminator")){
            sentence.getFeatures().put(Linguistics.QUOTATION, MovieList.TERMINATOR);
//            sentence.getFeatures().put(Linguistics.QUOTATION, MovieLists.TERMINATOR);
//            System.out.println("You said terminator, roboy understands and would trigger a quote - Hasta la vista baby!");
        }
        else if (tokens.contains("pulp") || tokens.contains("fiction")) {
            sentence.getFeatures().put(Linguistics.QUOTATION, MovieList.PULP_FICTION);
//            sentence.getFeatures().put(Linguistics.QUOTATION, MovieLists.PULP);
//            System.out.println("Say what one more time m#@$@#$r!");

        }
        else if (tokens.contains("what") ) {
            if(WHATCOUNT == 3) {
                WHATCOUNT = 0;
                sentence.getFeatures().put(Linguistics.QUOTATION, MovieList.PULP_FICTION);
//            sentence.getFeatures().put(Linguistics.QUOTATION,MovieLists.PULP);
//            System.out.println("Say what one more time m#@$@#$r!");
            } else {
                WHATCOUNT++;
            }
        }
        else if (tokens.contains("bugs") && tokens.contains("bunny") ) {
            sentence.getFeatures().put(Linguistics.QUOTATION, MovieList.BUGS_BUNNY);
//            sentence.getFeatures().put(Linguistics.QUOTATION, MovieLists.BUGS);
//            System.out.println("Hey whats up doc");
        }
        else if (tokens.contains("arnold") ) {
            sentence.getFeatures().put(Linguistics.QUOTATION, MovieList.TERMINATOR2);
//            sentence.getFeatures().put(Linguistics.QUOTATION, MovieLists.BUGS);
//            System.out.println("Hey whats up doc");
        }

        return sentence;
    }
}
