package roboy.linguistics.sentenceanalysis;

import roboy.linguistics.Linguistics;
import roboy.util.MovieLists;

import java.util.Arrays;
import java.util.List;

/**
 * Checks for a handfull of keywords and stores more or less fitting emotions
 * in the Linguistics.EMOTION feature that is later read out and fed to the
 * facial expression output module.
 */
public class MovieAnalyzer implements Analyzer {

    public Interpretation analyze(Interpretation sentence)
    {
        List<String> tokens = Arrays.asList((String[]) sentence.getFeature(Linguistics.TOKENS));

       // sample movies which roboy can detect        
        if (tokens.contains("terminator")){
            sentence.getFeatures().put(Linguistics.QUOTATION, MovieLists.TERMINATOR);
//            System.out.println("You said terminator, roboy understands and would trigger a quote - Hasta la vista baby!");
        }
        else if (tokens.contains("pulp") || tokens.contains("fiction")) {
            sentence.getFeatures().put(Linguistics.QUOTATION, MovieLists.PULP);
//            System.out.println("Say what one more time m#@$@#$r!");

        }
        else if (tokens.contains("what") ) {
            sentence.getFeatures().put(Linguistics.QUOTATION,MovieLists.PULP);
//            System.out.println("Say what one more time m#@$@#$r!");
        }
        else if (tokens.contains("bugs") && tokens.contains("bunny") ) {
            sentence.getFeatures().put(Linguistics.QUOTATION, MovieLists.BUGS);
//            System.out.println("Hey whats up doc");
        }

        return sentence;
    }
}
