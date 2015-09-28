import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Comparator;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceProcessException;

import type.Passage;
import type.Question;

/**
 * This CAS Consumer serves as a writer to generate your output. This is just template code, so you
 * need to implement actual code.
 */
public class PassageRankingWriter extends CasConsumer_ImplBase {

	// Parameters.
	public static final String PARAM_OUTPUT_DIRECTORY = "OutputDir";
	
	// Name out output file.
	private static final String OUTPUT_FILE = "passageRanking.txt";
	
	// Instance variable for writing the output.
	private File mFile;
	
	@Override
	public void initialize() throws ResourceInitializationException {
		File outputDirectory = new File((String) getConfigParameterValue(PARAM_OUTPUT_DIRECTORY));
		mFile = new File(outputDirectory, OUTPUT_FILE);
	}

	@Override
	public void processCas(CAS arg0) throws ResourceProcessException {
		// Convert to a JCas.
		JCas jcas;
		try {
			jcas = arg0.getJCas();
		} catch (CASException e) {
			throw new ResourceProcessException(e);
		}
		
		ArrayList<Question> questions = new ArrayList<Question>();
		FSIndex<Question> qIndex = jcas.getAnnotationIndex(Question.type);
		Iterator<Question> qIter = qIndex.iterator();
		while (qIter.hasNext())
			questions.add(qIter.next());
		
		// Comparators for sorting Questions and Passages.
		Comparator<Question> qComparator = new Comparator<Question>() {
			public int compare(Question o1, Question o2) {
				return o1.getId().compareTo(o2.getId());
			}
		};
		Comparator<Passage> pComparator = new Comparator<Passage>() {
			public int compare(Passage o1, Passage o2) {
				if (o1.getScore() > o2.getScore())
					return -1;
				else if (o1.getScore() == o2.getScore())
					return 0;
				else
					return 1;
			}
		};
		
		// Sort Questions by question ID and write output to file.
		try {
			Collections.sort(questions, qComparator);
			BufferedWriter bw = new BufferedWriter(new FileWriter(mFile));
			for (Question question : questions) {
				// Get the Passages answering the question.
				ArrayList<Passage> passages = new ArrayList<Passage>();
				for (Passage passage : Utility.destructFSList(question.getPassages(), new Passage[0]))
					passages.add(passage);

				// Sort the Passages by score.
				Collections.sort(passages, pComparator);
				for (Passage passage : passages) {
					String output = String.format("%s %s %.3f %s\n", question.getId(), passage.getSourceDocId(),
							passage.getScore(), passage.getOriginalText());
					bw.write(output);
				}
			}
			bw.close();
		} catch (IOException e) {
			throw new ResourceProcessException(e);
		}
	}
}
