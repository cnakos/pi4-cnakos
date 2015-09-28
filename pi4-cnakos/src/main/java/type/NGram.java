

/* First created by JCasGen Mon Sep 28 11:00:53 EDT 2015 */
package type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSList;


/** Annotation pointing to a span of text containing an n-gram.
 * Updated by JCasGen Mon Sep 28 11:00:53 EDT 2015
 * XML source: /home/constantine/Documents/Academics/11-791/pi4-cnakos/pi4-cnakos/src/main/resources/descriptors/typeSystem.xml
 * @generated */
public class NGram extends ComponentAnnotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(NGram.class);
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
  protected NGram() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public NGram(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public NGram(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public NGram(JCas jcas, int begin, int end) {
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
  //* Feature: n

  /** getter for n - gets The number of tokens in the n-gram.
   * @generated
   * @return value of the feature 
   */
  public int getN() {
    if (NGram_Type.featOkTst && ((NGram_Type)jcasType).casFeat_n == null)
      jcasType.jcas.throwFeatMissing("n", "type.NGram");
    return jcasType.ll_cas.ll_getIntValue(addr, ((NGram_Type)jcasType).casFeatCode_n);}
    
  /** setter for n - sets The number of tokens in the n-gram. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setN(int v) {
    if (NGram_Type.featOkTst && ((NGram_Type)jcasType).casFeat_n == null)
      jcasType.jcas.throwFeatMissing("n", "type.NGram");
    jcasType.ll_cas.ll_setIntValue(addr, ((NGram_Type)jcasType).casFeatCode_n, v);}    
   
    
  //*--------------*
  //* Feature: tokens

  /** getter for tokens - gets The tokens in the n-gram.
   * @generated
   * @return value of the feature 
   */
  public FSList getTokens() {
    if (NGram_Type.featOkTst && ((NGram_Type)jcasType).casFeat_tokens == null)
      jcasType.jcas.throwFeatMissing("tokens", "type.NGram");
    return (FSList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((NGram_Type)jcasType).casFeatCode_tokens)));}
    
  /** setter for tokens - sets The tokens in the n-gram. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setTokens(FSList v) {
    if (NGram_Type.featOkTst && ((NGram_Type)jcasType).casFeat_tokens == null)
      jcasType.jcas.throwFeatMissing("tokens", "type.NGram");
    jcasType.ll_cas.ll_setRefValue(addr, ((NGram_Type)jcasType).casFeatCode_tokens, jcasType.ll_cas.ll_getFSRef(v));}    
  }

    