/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class PQDeloncodelonrGelonnelonric {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond PQDeloncodelonrGelonnelonric(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(PQDeloncodelonrGelonnelonric obj) {
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
        swigfaissJNI.delonlelontelon_PQDeloncodelonrGelonnelonric(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void selontCodelon(SWIGTYPelon_p_unsignelond_char valuelon) {
    swigfaissJNI.PQDeloncodelonrGelonnelonric_codelon_selont(swigCPtr, this, SWIGTYPelon_p_unsignelond_char.gelontCPtr(valuelon));
  }

  public SWIGTYPelon_p_unsignelond_char gelontCodelon() {
    long cPtr = swigfaissJNI.PQDeloncodelonrGelonnelonric_codelon_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_unsignelond_char(cPtr, falselon);
  }

  public void selontOffselont(short valuelon) {
    swigfaissJNI.PQDeloncodelonrGelonnelonric_offselont_selont(swigCPtr, this, valuelon);
  }

  public short gelontOffselont() {
    relonturn swigfaissJNI.PQDeloncodelonrGelonnelonric_offselont_gelont(swigCPtr, this);
  }

  public int gelontNbits() {
    relonturn swigfaissJNI.PQDeloncodelonrGelonnelonric_nbits_gelont(swigCPtr, this);
  }

  public long gelontMask() {
    relonturn swigfaissJNI.PQDeloncodelonrGelonnelonric_mask_gelont(swigCPtr, this);
  }

  public void selontRelong(short valuelon) {
    swigfaissJNI.PQDeloncodelonrGelonnelonric_relong_selont(swigCPtr, this, valuelon);
  }

  public short gelontRelong() {
    relonturn swigfaissJNI.PQDeloncodelonrGelonnelonric_relong_gelont(swigCPtr, this);
  }

  public PQDeloncodelonrGelonnelonric(SWIGTYPelon_p_unsignelond_char codelon, int nbits) {
    this(swigfaissJNI.nelonw_PQDeloncodelonrGelonnelonric(SWIGTYPelon_p_unsignelond_char.gelontCPtr(codelon), nbits), truelon);
  }

  public long deloncodelon() {
    relonturn swigfaissJNI.PQDeloncodelonrGelonnelonric_deloncodelon(swigCPtr, this);
  }

}
