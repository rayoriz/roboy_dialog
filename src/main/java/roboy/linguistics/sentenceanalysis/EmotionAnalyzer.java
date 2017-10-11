package roboy.linguistics.sentenceanalysis;

import roboy.linguistics.Linguistics;

import java.util.Arrays;
import java.util.List;

/**
 * Checks for a handfull of keywords and stores more or less fitting emotions
 * in the Linguistics.EMOTION feature that is later read out and fed to the
 * facial expression output module.
 */
public class EmotionAnalyzer implements Analyzer {

    public Interpretation analyze(Interpretation sentence)
    {
        List<String> tokens = Arrays.asList((String[]) sentence.getFeature(Linguistics.TOKENS));
        if (tokens.contains("love") || tokens.contains("cute") )
            sentence.getFeatures().put(Linguistics.EMOTION, "shy");
        else if (tokens.contains("munich") || tokens.contains("robotics") || tokens.contains("germany") || tokens.contains("cologne"))
            sentence.getFeatures().put(Linguistics.EMOTION, "smileblink");
        else if (tokens.contains("left") )
            sentence.getFeatures().put(Linguistics.EMOTION, "lookleft");
        else if (tokens.contains("right") )
            sentence.getFeatures().put(Linguistics.EMOTION, "lookright");
        else if (tokens.contains("cat") || tokens.contains("cats") || tokens.contains("kittens") )
            sentence.getFeatures().put(Linguistics.EMOTION, "hearts");
        else if (tokens.contains("startup") || tokens.contains("cologne") || tokens.contains("ralf") || tokens.contains("conference"))
            sentence.getFeatures().put(Linguistics.EMOTION, "hearts");
        else if (tokens.contains("ciao") || tokens.contains("love") )
            sentence.getFeatures().put(Linguistics.EMOTION, "kiss");

        if (sentence.getFeatures().containsKey(Linguistics.ROBOYDETECTED))
        {
            sentence.getFeatures().put(Linguistics.EMOTION, "smileblink");
        }
        return sentence;
    }
}
