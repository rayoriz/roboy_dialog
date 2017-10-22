package roboy.linguistics.sentenceanalysis;

import roboy.linguistics.Linguistics;
import roboy.util.AnimationList;

import java.util.Arrays;
import java.util.List;

/**
 * Checks for movie references and adds the respective movie to the Interpretation.
 */
public class AnimationAnalyzer implements Analyzer {

    private int WHATCOUNT = 0;

    public Interpretation analyze(Interpretation sentence)
    {
        List<String> tokens = Arrays.asList((String[]) sentence.getFeature(Linguistics.TOKENS));

       // sample movies which roboy can detect
        AnimationList animation = null;
        if (tokens.contains("terminator") || (tokens.contains("arnold"))){
            animation = AnimationList.TERMINATOR;
        }
        else if (tokens.contains("pulp") || tokens.contains("fiction")) {
            animation = AnimationList.PULP_FICTION;
        }
        else if (tokens.contains("bugs") && tokens.contains("bunny") ) {
            animation = AnimationList.BUGS_BUNNY;
        }
        else if (tokens.contains("minion") || tokens.contains("minions")|| tokens.contains("banana")) {
            animation = AnimationList.MINIONS;
        }
        else if (tokens.contains("birthday") && tokens.contains("today")) {
            animation = AnimationList.BIRTHDAY;
        }
        else if (tokens.contains("what") ) {
            if(WHATCOUNT == 3) {
                WHATCOUNT = 0;
                animation = AnimationList.PULP_FICTION;
            } else {
                WHATCOUNT++;
            }
        }


        if(animation != null) {
            sentence.getFeatures().put(Linguistics.QUOTATION, animation);
        }
        return sentence;
    }
}
