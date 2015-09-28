import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSList;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.apache.uima.util.ProgressImpl;

import type.InputDocument;
import type.Passage;
import type.Question;

/**
 * This Collection Reader serves as a reader to parse your input. This is just template code, so you
 * need to implement actual code.
 */
public class QuestionPassageReader extends CollectionReader_ImplBase {
	
	// Parameters.
	public static final String PARAM_INPUT_DIRECTORY = "InputDir";
	
	// The string that appears in the second field of a line containing a question. 
	private static final String QUESTION_TAG = "QUESTION";
	
	// The component ID of the class.
	private static final String COMPONENT_ID = QuestionPassageReader.class.getName();
	
	// Instance variables for reading the input.
	private ArrayList<File> mInputFiles;
	private int mCurrentFile;
	
	@Override
	public void initialize() throws ResourceInitializationException {
		File inputDirectory = new File((String) getConfigParameterValue(PARAM_INPUT_DIRECTORY));
		mInputFiles = new ArrayList<File>();
		for (File file : inputDirectory.listFiles())
			if (file.isFile())
				mInputFiles.add(file);
		mCurrentFile = 0;
	}

	@Override
	public void getNext(CAS aCAS) throws IOException, CollectionException {
		// Generate a CAS for the next input file.
		// This should contain a single InputDocument with its Questions and Passages.
		// The InputDocument should contain the cleaned text for annotation purposes.
		// Each Passage should contain its original text for archival purposes.
		
		// Convert to a JCas.
		JCas jcas;
		try {
			jcas = aCAS.getJCas();
		} catch (CASException e) {
			throw new CollectionException(e);
		}
		
		// Set up our data structures so that we can create a cleaned document to index.
		int offset = 0;
		ArrayList<String> cleanedLines = new ArrayList<String>();
		HashMap<String, Question> questions = new HashMap<String, Question>();
		HashMap<String, ArrayList<Passage>> passages = new HashMap<String, ArrayList<Passage>>();
		
		// Read the document, clean it, and save it to memory.
		try {
			BufferedReader br = new BufferedReader(new FileReader(mInputFiles.get(mCurrentFile)));
			String line = br.readLine();
			while (line != null) {
				String[] parts = line.split("\\s+", 3);
				if (parts.length != 3) {
					// Improperly formatted line.
				} else if (QUESTION_TAG.equals(parts[1])) {
					// Found a question. 
					Question question = new Question(jcas);
					
					// Set component ID and score.
					question.setComponentId(COMPONENT_ID);
					question.setScore(1.0);
					
					// No cleaning necessary.
					cleanedLines.add(line);
					
					// Set indexes and update offset.
					question.setBegin(offset);
					offset += line.length();
					question.setEnd(offset);
					
					// Set question ID and setence.
					question.setId(parts[0]);
					question.setSentence(parts[2]);
					
					// Add the question to the map.
					questions.put(parts[0], question);
				} else {
					// Found a passage. 
					Passage passage = new Passage(jcas);
					parts = line.split("\\s+", 4);
					
					// Set component ID and score.
					passage.setComponentId(COMPONENT_ID);
					passage.setScore(1.0);
					
					// Clean the line.
					String cleanedText = cleanPassage(parts[3]);
					String cleanedLine = String.join(" ", parts[0], parts[1], parts[2], cleanedText);
					cleanedLines.add(cleanedLine);
					
					// Set indexes and update offset.
					passage.setBegin(offset);
					offset += cleanedLine.length();
					passage.setEnd(offset);
					
					// Set document ID, text, and original text.
					passage.setSourceDocId(parts[1]);
					passage.setLabel(Integer.parseInt(parts[2]) > 0);
					passage.setText(cleanedText);
					passage.setOriginalText(parts[3]);
					
					// Add the passage to the map.
					if (!passages.containsKey(parts[0]))
						passages.put(parts[0], new ArrayList<Passage>());
					passages.get(parts[0]).add(passage);					
				}
				
				line = br.readLine();
			}
			br.close();
		} catch (IOException e) {
			throw new CollectionException(e);
		}
		
		// Link the Questions and Passages and add them to the annotation index.
		// Note: Passages for a nonexistent question will be left out.
		ArrayList<Passage> fullPassageList = new ArrayList<Passage>();
		for (String key : questions.keySet()) {
			Question question = questions.get(key);
			Passage[] passageArray = new Passage[0];
			if (passages.containsKey(key))
				passageArray = passages.get(key).toArray(passageArray);
			FSList passageList = Utility.constructFSList(passageArray, jcas);
			
			question.setPassages(passageList);
			question.addToIndexes();
			for (Passage passage : passageArray) {
				passage.setQuestion(question);
				passage.addToIndexes();
				fullPassageList.add(passage);
			}
		}
		
		// Create an InputDocument, set its features, and add it to the index.
		InputDocument inputDocument = new InputDocument(jcas);
		
		// Set component ID and score.
		inputDocument.setComponentId(COMPONENT_ID);
		inputDocument.setScore(1.0);
		
		// Set indexes.
		inputDocument.setBegin(0);
		inputDocument.setEnd(offset);
		
		// Set question and passage lists.
		FSList questionList = Utility.constructFSList(questions.values().toArray(new Question[0]), jcas);
		FSList passageList = Utility.constructFSList(fullPassageList.toArray(new Passage[0]), jcas);
		inputDocument.setQuestions(questionList);
		inputDocument.setPassages(passageList);
		
		// Add to annotation index.
		inputDocument.addToIndexes();
		
		// Set document text.
		StringBuilder builder = new StringBuilder(offset);
		for (String line : cleanedLines)
			builder.append(line);
		jcas.setDocumentText(builder.toString());		
		
		mCurrentFile++;
	}

	@Override
	public void close() throws IOException {
	}

	@Override
	public Progress[] getProgress() {
		return new Progress[] { new ProgressImpl(mCurrentFile, mInputFiles.size(), Progress.ENTITIES) };
	}

	@Override
	public boolean hasNext() throws IOException, CollectionException {
		return mCurrentFile < mInputFiles.size();
	}
	
	// Helper function to clean an input passage by removing HTML tags.
	private String cleanPassage(String text) {
		// All instances of < or > adjacent to some text are tags or broken tags.
		// Therefore we can discard all words that begin with < or end with >.
		Pattern htmlTags = Pattern.compile("(<\\S+\\s*|\\S+>\\s*)");
		Matcher matcher = htmlTags.matcher(text);
		return matcher.replaceAll("");
	}
}
