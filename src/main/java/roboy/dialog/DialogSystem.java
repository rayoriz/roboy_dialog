package roboy.dialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonIOException;

import roboy.dialog.action.Action;
import roboy.dialog.action.ShutDownAction;
import roboy.dialog.personality.Personality;
import roboy.dialog.personality.SmallTalkPersonality;

import roboy.io.*;

import roboy.linguistics.sentenceanalysis.*;
import roboy.memory.Neo4jMemory;
import roboy.talk.Verbalizer;

import roboy.ros.RosMainNode;

/**
 * The dialog manager's main class.
 * 
 * Here, the used components are put together and executed using the main method. In the future,
 * the different combinations of components should probably be transfered to configuration files.
 * 
 * The workflow in the dialog manager is the following:
 * 1. Input devices produce an Input object
 * 2. The Input object is transformed into an Interpretation object containing
 *    the input sentence in the Linguistics.SENTENCE attribute and all other
 *    attributes of the Input object in the corresponding fields
 * 3. Linguistic Analyzers are used on the Interpretation object to add additional information
 * 4. The Personality class takes the Interpretation object and decides how to answer
 *    to this input
 * 5. The list of actions returned by Personality.answer is performed by the Output devices
 * 6. If one of these actions is a ShutDownAction the program terminates
 * 7. Otherwise repeat
 * 
 * Input devices:
 * - For testing from command line: CommandLineInput
 * - For speech to text: BingInput (requires internet)
 * - For combining multiple inputs: MultiInputDevice
 * - Others for specific tasks
 * 
 * Analyzers:
 * - Tokenization: SimpleTokenizer
 * - Part-of-speech-tagging: OpenNLPPOSTagger
 * - Semantic role labeling: OpenNLPParser
 * - DBpedia question answering: AnswerAnalyzer
 * - Other more stupid ones
 * 
 * Personalities:
 * - SmallTalkPersonality: main one
 * - Others for testing specific things
 * 
 * Output devices:
 * - For testing with command line: CommandLineOutput
 * - For text to speech: BingOutput (requires internet)
 * - For combining multiple outputs: MultiOutputDevice
 * - For text to speech + facial expressions: CerevoiceOutput
 * - For facial expressions: EmotionOutput
 * - For text to speech (worse quality): FreeTTSOutput
 * 
 * The easiest way to create ones own Roboy communication application is to pick the 
 * input and output devices provided here, use the tokenization, POS tagging and possibly
 * semantic role labeling (though still under development) if needed and write an own 
 * personality. If one wants to use the DBpedia, Protege, generative model or state machine
 * stuff, one has to dig deeper into the small talk personality and see how it is used there.
 */
public class DialogSystem {

    public static boolean SHUTDOWN_ON_ROS_FAILURE = false;
    private static final int AUDIO_EXTERNAL_BUFFER_SIZE = 128000;
	
	public static void main(String[] args) throws JsonIOException, IOException, InterruptedException {

//*** AUDIO FILE OUTPUT INITIALIZATION ***
        // Implementation from: http://www.jsresources.org/examples/SimpleAudioPlayer.java.html

//        String testFile = "/home/laura/Downloads/HackRoboy/roboy_dialog/resources/audio/pulpfiction.wav";
//        File soundFile = new File(testFile);
//        AudioInputStream audioInputStream = null;
//        try
//        {
//            audioInputStream = AudioSystem.getAudioInputStream(soundFile);
//        }
//        catch (Exception e)
//        {
//			System.out.println("AUDIO input stream setup FAILED:");
//            e.printStackTrace();
//            System.exit(1);
//        }
//        AudioFormat	audioFormat = audioInputStream.getFormat();
//        SourceDataLine line = null;
//        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
//        try
//        {
//            line = (SourceDataLine) AudioSystem.getLine(info);
//            line.open(audioFormat);
//        }
//        catch (LineUnavailableException e)
//        {
//            e.printStackTrace();
//            System.exit(1);
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//            System.exit(1);
//        }
//        line.start();
//        int	nBytesRead = 0;
//        byte[]	abData = new byte[AUDIO_EXTERNAL_BUFFER_SIZE];
//        while (nBytesRead != -1)
//        {
//            try
//            {
//                nBytesRead = audioInputStream.read(abData, 0, abData.length);
//            }
//            catch (IOException e)
//            {
//                e.printStackTrace();
//            }
//            if (nBytesRead >= 0)
//            {
//                int	nBytesWritten = line.write(abData, 0, nBytesRead);
//            }
//        }
//        line.drain();
//        line.close()
// *** END AUDIO ***


        // initialize ROS node
        RosMainNode rosMainNode = new RosMainNode();
        Neo4jMemory memory = Neo4jMemory.getInstance(rosMainNode);

//	     InputDevice input = new CommandLineInput();
		 InputDevice input = new BingInput(rosMainNode);
//        DatagramSocket ds = new DatagramSocket(55555);
//        InputDevice input = new UdpInput(ds);
//		InputDevice celebInput = new CelebritySimilarityInput();
//		InputDevice roboyDetectInput = new RoboyNameDetectionInput();
		InputDevice multiIn = new MultiInputDevice(input);//, celebInput, roboyDetectInput);

		OutputDevice output1 = new CerevoiceOutput(rosMainNode);
//        CerevoiceOutput output2 = new CerevoiceOutput(rosMainNode);
		// OutputDevice output = new BingOutput();
//        OutputDevice output2 = new UdpOutput(ds, "localhost", 55556);
		EmotionOutput emotion = new EmotionOutput(rosMainNode, (CerevoiceOutput) output1);
//        OutputDevice output = new CommandLineOutput();
//        OutputDevice output1 = new AudioOutput();
//       OutputDevice output1 = new CerevoiceOutput(rosMainNode);
//		OutputDevice multiOut = new MultiOutputDevice(output,output1);//, output2, emotion);
        OutputDevice multiOut = new MultiOutputDevice(emotion, output1);

		List<Analyzer> analyzers = new ArrayList<Analyzer>();
		analyzers.add(new Preprocessor());
		analyzers.add(new SimpleTokenizer());
		analyzers.add(new OpenNLPPPOSTagger());
		analyzers.add(new DictionaryBasedSentenceTypeDetector());
		analyzers.add(new SentenceAnalyzer());
		analyzers.add(new OpenNLPParser());
		analyzers.add(new OntologyNERAnalyzer());
		analyzers.add(new AnswerAnalyzer());
        analyzers.add(new EmotionAnalyzer());
        analyzers.add(new AnimationAnalyzer());

        // analyzers.add(new IntentAnalyzer(rosMainNode));

        // Race between main and rosMainNode threads, but there should be enough time.
        if (!rosMainNode.STARTUP_SUCCESS && SHUTDOWN_ON_ROS_FAILURE) {
            throw new RuntimeException("DialogSystem shutdown caused by ROS service initialization failure. " +
                    "Start the required services or set SHUTDOWN_ON_ROS_FAILURE to false.");
        }

        System.out.println("Initialized...");
        ((CerevoiceOutput) output1).say("La la la la la, hello world!");
        while(true) {

//            while (!Vision.getInstance().findFaces()) {
//                emotion.act(new FaceAction("angry"));
//            }
//            emotion.act(new FaceAction("neutral"));

//            while (!multiIn.listen().attributes.containsKey(Linguistics.ROBOYDETECTED)) {
//            }

            Personality smallTalk = new SmallTalkPersonality(new Verbalizer(), rosMainNode);
            Input raw;
            Interpretation interpretation;
            List<Action> actions = smallTalk.answer(new Interpretation(""));


            while (actions.isEmpty() || !(actions.get(0) instanceof ShutDownAction)) {
                multiOut.act(actions);
                raw = multiIn.listen();
                interpretation = new Interpretation(raw.sentence, raw.attributes); //TODO: Input devices should immediately produce Interpretation objects
                for (Analyzer a : analyzers) {
                    interpretation = a.analyze(interpretation);
                }
                actions = smallTalk.answer(interpretation);
            }
            List<Action> lastwords = ((ShutDownAction) actions.get(0)).getLastWords();
            multiOut.act(lastwords);
            actions.clear();
        }
	}

}
