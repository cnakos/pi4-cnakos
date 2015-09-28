import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.EmptyFSList;
import org.apache.uima.jcas.cas.FSList;
import org.apache.uima.jcas.cas.NonEmptyFSList;
import org.apache.uima.jcas.cas.TOP;

import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.ling.WordTag;
import edu.stanford.nlp.process.TokenizerFactory;
import type.NGram;
import type.Passage;
import type.Question;
import type.Token;
import edu.stanford.nlp.process.PTBTokenizer.PTBTokenizerFactory;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.process.Morphology;

/*
 * Utility class to hold helper functions.
 */
public class Utility {
	// Helper function to construct an FSList from an array.
	public static FSList constructFSList(TOP[] array, JCas jcas) {
		if (array.length == 0)
			return new EmptyFSList(jcas);
		FSList list = new NonEmptyFSList(jcas);
		NonEmptyFSList head = (NonEmptyFSList) list;
		for (int i = 0; i < array.length; i++) {
			head.setHead(array[i]);
			if (i == array.length - 1)
				head.setTail(new EmptyFSList(jcas));
			else {
				head.setTail(new NonEmptyFSList(jcas));
				head = (NonEmptyFSList) head.getTail();
			}
		}
		return list;
	}
	
	// Helper function to construct an FSList from an array.
	public static <T> T[] destructFSList(FSList fsList, T[] a) {
		ArrayList<T> objectList = new ArrayList<T>();
		FSList head = fsList;
		while (!(head instanceof EmptyFSList)) {
			Object object = ((NonEmptyFSList) head).getHead();
			objectList.add((T) object);
			head = ((NonEmptyFSList) head).getTail();
		}
		return objectList.toArray(a);
	}
	
	// Return an array of Tokens added to the jcas corresponding to the given text.
	public static Token[] tokenize(String text, int offset, JCas jcas, String componentId) {
		TokenizerFactory<Word> factory = PTBTokenizerFactory.newTokenizerFactory();
	    Tokenizer<Word> tokenizer = factory.getTokenizer(new StringReader(text));
		List<Word> words = tokenizer.tokenize();
		
		Token[] tokens = new Token[words.size()];
		for (int i = 0; i < words.size(); i++) {
			Word word = words.get(i);
			int begin = word.beginPosition();
			int end = word.endPosition();
			
			Token token = new Token(jcas);
			token.setComponentId(componentId);
			token.setScore(1.0);
			
			token.setBegin(begin + offset);
			token.setEnd(end + offset);
			
			String wordText = text.substring(begin, end);
			WordTag stemTag = Morphology.stemStatic(wordText, null);
			token.setText(stemTag.word());
			
			token.addToIndexes();
			tokens[i] = token;
		}
		return tokens;
	}

	// Return true if the NGrams are equal and false otherwise.
	public static boolean nGramEquals(NGram ngram1, NGram ngram2) {
		Token[] array1 = destructFSList(ngram1.getTokens(), new Token[0]);
		Token[] array2 = destructFSList(ngram2.getTokens(), new Token[0]);
		if (array1.length != array2.length)
			return false;
		for (int i = 0; i < array1.length; i++)
			if (i >= array2.length || !array1[i].getText().equals(array2[i].getText()))
				return false;
		return true;
	}
	
	// Return the offset of the Question's sentence text within the document.
	public static int getQuestionOffset(Question question) {
		return question.getBegin() + question.getCoveredText().lastIndexOf(question.getSentence());
	}

	// Return the offset of the Passage's sentence text within the document.
	public static int getPassageOffset(Passage passage) {
		return passage.getBegin() + passage.getCoveredText().lastIndexOf(passage.getText());
	}	
}
