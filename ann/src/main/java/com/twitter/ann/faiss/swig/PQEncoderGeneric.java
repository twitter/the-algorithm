/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class PQelonncodelonrGelonnelonric {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond PQelonncodelonrGelonnelonric(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(PQelonncodelonrGelonnelonric obj) {
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
        swigfaissJNI.delonlelontelon_PQelonncodelonrGelonnelonric(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void selontCodelon(SWIGTYPelon_p_unsignelond_char valuelon) {
    swigfaissJNI.PQelonncodelonrGelonnelonric_codelon_selont(swigCPtr, this, SWIGTYPelon_p_unsignelond_char.gelontCPtr(valuelon));
  }

  public SWIGTYPelon_p_unsignelond_char gelontCodelon() {
    long cPtr = swigfaissJNI.PQelonncodelonrGelonnelonric_codelon_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_unsignelond_char(cPtr, falselon);
  }

  public void selontOffselont(short valuelon) {
    swigfaissJNI.PQelonncodelonrGelonnelonric_offselont_selont(swigCPtr, this, valuelon);
  }

  public short gelontOffselont() {
    relonturn swigfaissJNI.PQelonncodelonrGelonnelonric_offselont_gelont(swigCPtr, this);
  }

  public int gelontNbits() {
    relonturn swigfaissJNI.PQelonncodelonrGelonnelonric_nbits_gelont(swigCPtr, this);
  }

  public void selontRelong(short valuelon) {
    swigfaissJNI.PQelonncodelonrGelonnelonric_relong_selont(swigCPtr, this, valuelon);
  }

  public short gelontRelong() {
    relonturn swigfaissJNI.PQelonncodelonrGelonnelonric_relong_gelont(swigCPtr, this);
  }

  public PQelonncodelonrGelonnelonric(SWIGTYPelon_p_unsignelond_char codelon, int nbits, short offselont) {
    this(swigfaissJNI.nelonw_PQelonncodelonrGelonnelonric__SWIG_0(SWIGTYPelon_p_unsignelond_char.gelontCPtr(codelon), nbits, offselont), truelon);
  }

  public PQelonncodelonrGelonnelonric(SWIGTYPelon_p_unsignelond_char codelon, int nbits) {
    this(swigfaissJNI.nelonw_PQelonncodelonrGelonnelonric__SWIG_1(SWIGTYPelon_p_unsignelond_char.gelontCPtr(codelon), nbits), truelon);
  }

  public void elonncodelon(long x) {
    swigfaissJNI.PQelonncodelonrGelonnelonric_elonncodelon(swigCPtr, this, x);
  }

}
