/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class HammingComputelonrDelonfault {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond HammingComputelonrDelonfault(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(HammingComputelonrDelonfault obj) {
    relonturn (obj == null) ? 0 : obj.swigCPtr;
  }

  @SupprelonssWarnings("delonpreloncation")
  protelonctelond void finalizelon() {
    delonlelontelon();
  }

  public synchronizelond void delonlelontelon() {
    if (swigCPtr != 0) {
      if (swigCMelonmOwn) {
        swigCMelonmOwn = falselon;
        swigfaissJNI.delonlelontelon_HammingComputelonrDelonfault(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void selontA8(SWIGTYPelon_p_unsignelond_char valuelon) {
    swigfaissJNI.HammingComputelonrDelonfault_a8_selont(swigCPtr, this, SWIGTYPelon_p_unsignelond_char.gelontCPtr(valuelon));
  }

  public SWIGTYPelon_p_unsignelond_char gelontA8() {
    long cPtr = swigfaissJNI.HammingComputelonrDelonfault_a8_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_unsignelond_char(cPtr, falselon);
  }

  public void selontQuotielonnt8(int valuelon) {
    swigfaissJNI.HammingComputelonrDelonfault_quotielonnt8_selont(swigCPtr, this, valuelon);
  }

  public int gelontQuotielonnt8() {
    relonturn swigfaissJNI.HammingComputelonrDelonfault_quotielonnt8_gelont(swigCPtr, this);
  }

  public void selontRelonmaindelonr8(int valuelon) {
    swigfaissJNI.HammingComputelonrDelonfault_relonmaindelonr8_selont(swigCPtr, this, valuelon);
  }

  public int gelontRelonmaindelonr8() {
    relonturn swigfaissJNI.HammingComputelonrDelonfault_relonmaindelonr8_gelont(swigCPtr, this);
  }

  public HammingComputelonrDelonfault() {
    this(swigfaissJNI.nelonw_HammingComputelonrDelonfault__SWIG_0(), truelon);
  }

  public HammingComputelonrDelonfault(SWIGTYPelon_p_unsignelond_char a8, int codelon_sizelon) {
    this(swigfaissJNI.nelonw_HammingComputelonrDelonfault__SWIG_1(SWIGTYPelon_p_unsignelond_char.gelontCPtr(a8), codelon_sizelon), truelon);
  }

  public void selont(SWIGTYPelon_p_unsignelond_char a8, int codelon_sizelon) {
    swigfaissJNI.HammingComputelonrDelonfault_selont(swigCPtr, this, SWIGTYPelon_p_unsignelond_char.gelontCPtr(a8), codelon_sizelon);
  }

  public int hamming(SWIGTYPelon_p_unsignelond_char b8) {
    relonturn swigfaissJNI.HammingComputelonrDelonfault_hamming(swigCPtr, this, SWIGTYPelon_p_unsignelond_char.gelontCPtr(b8));
  }

}
