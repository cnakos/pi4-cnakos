import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import type.ComponentAnnotation;
import type.NGram;
import type.Passage;
import type.Question;
import type.Token;

public class NGramAnnotator extends JCasAnnotator_ImplBase {
	
	// Parameters.
	public static final String PARAM_N = "N";
	
	// The component ID of the class.
	private static final String COMPONENT_ID = NGramAnnotator.class.getName();
	
	// Instance variables.
	private int mN;
	
	@Override
	public void initialize(UimaContext aContext) throws ResourceInitializationException {
		super.initialize(aContext);
		mN = (int) aContext.getConfigParameterValue(PARAM_N);
	}

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		FSIndex<ComponentAnnotation> index = aJCas.getAnnotationIndex(ComponentAnnotation.type);
		Iterator<ComponentAnnotation> iter = index.iterator();
		while (iter.hasNext()) {
			ComponentAnnotation annotation = iter.next();
			Token[] tokens;
			if (annotation instanceof Question)
				tokens = Utility.destructFSList(((Question) annotation).getTokens(), new Token[0]);
			else if (annotation instanceof Passage)
				tokens = Utility.destructFSList(((Passage) annotation).getTokens(), new Token[0]);
			else
				continue;
			Token[] ngramTokens;
			ArrayList<NGram> ngrams = new ArrayList<NGram>();
			for (int i = 0; i <= tokens.length - mN; i++) {
				// Set up NGram annotation.
				NGram ngram = new NGram(aJCas);
				ngramTokens = Arrays.copyOfRange(tokens, i, i + mN);
				ngram.setComponentId(COMPONENT_ID);
				ngram.setBegin(ngramTokens[0].getBegin());
				ngram.setEnd(ngramTokens[mN - 1].getEnd());
				ngram.setN(mN);
				ngram.setTokens(Utility.constructFSList(ngramTokens, aJCas));
				
				// Propagate any doubts.
				double score = 1.0;
				for (int j = 0; j < mN; j++)
					score *= ngramTokens[j].getScore();
				ngram.setScore(score);
				
				// Add the NGram to the indexes. 
				ngram.addToIndexes();
				ngrams.add(ngram);
			}
			if (annotation instanceof Question)
				((Question) annotation).setNgrams(Utility.constructFSList(ngrams.toArray(new NGram[0]), aJCas));
			else if (annotation instanceof Passage)
				((Passage) annotation).setNgrams(Utility.constructFSList(ngrams.toArray(new NGram[0]), aJCas));
		}
	}
}
