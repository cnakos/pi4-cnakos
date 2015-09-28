import java.util.Iterator;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.jcas.JCas;

import type.NGram;
import type.Passage;
import type.Question;

public class ScoreAnnotator extends JCasAnnotator_ImplBase {
	// The component ID of the class.
	private static final String COMPONENT_ID = ScoreAnnotator.class.getName();
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		FSIndex<Question> qIndex = aJCas.getAnnotationIndex(Question.type);
		Iterator<Question> qIter = qIndex.iterator();
		while (qIter.hasNext()) {
			Question question = qIter.next();
			Passage[] passages = Utility.destructFSList(question.getPassages(), new Passage[0]);
			NGram[] qNGrams = Utility.destructFSList(question.getNgrams(), new NGram[0]);
			for (Passage passage : passages) {
				passage.setComponentId(COMPONENT_ID);
				NGram[] pNGrams = Utility.destructFSList(passage.getNgrams(), new NGram[0]);
				// Calculate percentage of Question n-grams in Passage.
				// There are many more advanced ways to do this.
				double score = 0.0;
				for (NGram qNGram : qNGrams) {
					for (NGram pNGram : pNGrams) {
						if (Utility.nGramEquals(qNGram, pNGram)) {
							score += 1.0;
							break;
						}
					}
				}
				score /= qNGrams.length;
				passage.setScore(score);
			}
		}
	}
}
