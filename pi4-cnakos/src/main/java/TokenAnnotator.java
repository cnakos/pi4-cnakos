import java.util.Iterator;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.jcas.JCas;

import type.Passage;
import type.Question;
import type.Token;

public class TokenAnnotator extends JCasAnnotator_ImplBase {
	// The component ID of the class.
	private static final String COMPONENT_ID = TokenAnnotator.class.getName();

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		FSIndex<Question> qIndex = aJCas.getAnnotationIndex(Question.type);
		Iterator<Question> qIter = qIndex.iterator();
		while (qIter.hasNext()) {
			Question question = qIter.next();
			int offset = Utility.getQuestionOffset(question);
			Token[] tokens = Utility.tokenize(question.getSentence(), offset, aJCas, COMPONENT_ID);
			question.setTokens(Utility.constructFSList(tokens, aJCas));
		}
		
		FSIndex<Passage> pIndex = aJCas.getAnnotationIndex(Passage.type);
		Iterator<Passage> pIter = pIndex.iterator();
		while (pIter.hasNext()) {
			Passage passage = pIter.next();
			int offset = Utility.getPassageOffset(passage);
			Token[] tokens = Utility.tokenize(passage.getText(), offset, aJCas, COMPONENT_ID);
			passage.setTokens(Utility.constructFSList(tokens, aJCas));
		}
	}
}
