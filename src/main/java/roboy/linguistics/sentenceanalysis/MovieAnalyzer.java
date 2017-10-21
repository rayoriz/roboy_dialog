package roboy.linguistics.sentenceanalysis;

import roboy.linguistics.Linguistics;

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
            sentence.getFeatures().put(Linguistics.QUOTATION, "terminator");
            System.out.println("You said terminator, roboy understands");
        }
        else if (tokens.contains("pulp") || tokens.contains("fiction"))
            sentence.getFeatures().put(Linguistics.EMOTION, "pulp_fiction");
        else if (tokens.contains("what") )
            sentence.getFeatures().put(Linguistics.EMOTION, "pulp_fiction");      
        else if (tokens.contains("bugs") || tokens.contains("bunny") )
            sentence.getFeatures().put(Linguistics.EMOTION, "bugs_bunny");      
        
        return sentence;
    }
}
