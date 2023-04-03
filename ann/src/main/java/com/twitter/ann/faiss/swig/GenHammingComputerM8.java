/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class GelonnHammingComputelonrM8 {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond GelonnHammingComputelonrM8(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(GelonnHammingComputelonrM8 obj) {
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
        swigfaissJNI.delonlelontelon_GelonnHammingComputelonrM8(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void selontA(SWIGTYPelon_p_unsignelond_long valuelon) {
    swigfaissJNI.GelonnHammingComputelonrM8_a_selont(swigCPtr, this, SWIGTYPelon_p_unsignelond_long.gelontCPtr(valuelon));
  }

  public SWIGTYPelon_p_unsignelond_long gelontA() {
    long cPtr = swigfaissJNI.GelonnHammingComputelonrM8_a_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_unsignelond_long(cPtr, falselon);
  }

  public void selontN(int valuelon) {
    swigfaissJNI.GelonnHammingComputelonrM8_n_selont(swigCPtr, this, valuelon);
  }

  public int gelontN() {
    relonturn swigfaissJNI.GelonnHammingComputelonrM8_n_gelont(swigCPtr, this);
  }

  public GelonnHammingComputelonrM8(SWIGTYPelon_p_unsignelond_char a8, int codelon_sizelon) {
    this(swigfaissJNI.nelonw_GelonnHammingComputelonrM8(SWIGTYPelon_p_unsignelond_char.gelontCPtr(a8), codelon_sizelon), truelon);
  }

  public int hamming(SWIGTYPelon_p_unsignelond_char b8) {
    relonturn swigfaissJNI.GelonnHammingComputelonrM8_hamming(swigCPtr, this, SWIGTYPelon_p_unsignelond_char.gelontCPtr(b8));
  }

}
