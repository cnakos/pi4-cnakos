

/* First created by JCasGen Sun Sep 27 12:46:17 EDT 2015 */
package type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSList;


/** Stores the information about a question.
 * Updated by JCasGen Mon Sep 28 11:00:53 EDT 2015
 * XML source: /home/constantine/Documents/Academics/11-791/pi4-cnakos/pi4-cnakos/src/main/resources/descriptors/typeSystem.xml
 * @generated */
public class Question extends ComponentAnnotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Question.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Question() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Question(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Question(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Question(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: id

  /** getter for id - gets The identifier for the question.
   * @generated
   * @return value of the feature 
   */
  public String getId() {
    if (Question_Type.featOkTst && ((Question_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "type.Question");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Question_Type)jcasType).casFeatCode_id);}
    
  /** setter for id - sets The identifier for the question. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setId(String v) {
    if (Question_Type.featOkTst && ((Question_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "type.Question");
    jcasType.ll_cas.ll_setStringValue(addr, ((Question_Type)jcasType).casFeatCode_id, v);}    
   
    
  //*--------------*
  //* Feature: sentence

  /** getter for sentence - gets The text of the question.
   * @generated
   * @return value of the feature 
   */
  public String getSentence() {
    if (Question_Type.featOkTst && ((Question_Type)jcasType).casFeat_sentence == null)
      jcasType.jcas.throwFeatMissing("sentence", "type.Question");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Question_Type)jcasType).casFeatCode_sentence);}
    
  /** setter for sentence - sets The text of the question. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setSentence(String v) {
    if (Question_Type.featOkTst && ((Question_Type)jcasType).casFeat_sentence == null)
      jcasType.jcas.throwFeatMissing("sentence", "type.Question");
    jcasType.ll_cas.ll_setStringValue(addr, ((Question_Type)jcasType).casFeatCode_sentence, v);}    
   
    
  //*--------------*
  //* Feature: passages

  /** getter for passages - gets Passages associated with this question.
   * @generated
   * @return value of the feature 
   */
  public FSList getPassages() {
    if (Question_Type.featOkTst && ((Question_Type)jcasType).casFeat_passages == null)
      jcasType.jcas.throwFeatMissing("passages", "type.Question");
    return (FSList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Question_Type)jcasType).casFeatCode_passages)));}
    
  /** setter for passages - sets Passages associated with this question. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setPassages(FSList v) {
    if (Question_Type.featOkTst && ((Question_Type)jcasType).casFeat_passages == null)
      jcasType.jcas.throwFeatMissing("passages", "type.Question");
    jcasType.ll_cas.ll_setRefValue(addr, ((Question_Type)jcasType).casFeatCode_passages, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: tokens

  /** getter for tokens - gets The tokens in the Question's sentence.
   * @generated
   * @return value of the feature 
   */
  public FSList getTokens() {
    if (Question_Type.featOkTst && ((Question_Type)jcasType).casFeat_tokens == null)
      jcasType.jcas.throwFeatMissing("tokens", "type.Question");
    return (FSList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Question_Type)jcasType).casFeatCode_tokens)));}
    
  /** setter for tokens - sets The tokens in the Question's sentence. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setTokens(FSList v) {
    if (Question_Type.featOkTst && ((Question_Type)jcasType).casFeat_tokens == null)
      jcasType.jcas.throwFeatMissing("tokens", "type.Question");
    jcasType.ll_cas.ll_setRefValue(addr, ((Question_Type)jcasType).casFeatCode_tokens, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: ngrams

  /** getter for ngrams - gets The n-grams in the Question's sentence.
   * @generated
   * @return value of the feature 
   */
  public FSList getNgrams() {
    if (Question_Type.featOkTst && ((Question_Type)jcasType).casFeat_ngrams == null)
      jcasType.jcas.throwFeatMissing("ngrams", "type.Question");
    return (FSList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Question_Type)jcasType).casFeatCode_ngrams)));}
    
  /** setter for ngrams - sets The n-grams in the Question's sentence. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setNgrams(FSList v) {
    if (Question_Type.featOkTst && ((Question_Type)jcasType).casFeat_ngrams == null)
      jcasType.jcas.throwFeatMissing("ngrams", "type.Question");
    jcasType.ll_cas.ll_setRefValue(addr, ((Question_Type)jcasType).casFeatCode_ngrams, jcasType.ll_cas.ll_getFSRef(v));}    
  }

    