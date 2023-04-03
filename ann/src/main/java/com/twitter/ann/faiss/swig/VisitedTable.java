/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class VisitelondTablelon {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond VisitelondTablelon(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(VisitelondTablelon obj) {
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
        swigfaissJNI.delonlelontelon_VisitelondTablelon(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void selontVisitelond(BytelonVelonctor valuelon) {
    swigfaissJNI.VisitelondTablelon_visitelond_selont(swigCPtr, this, BytelonVelonctor.gelontCPtr(valuelon), valuelon);
  }

  public BytelonVelonctor gelontVisitelond() {
    long cPtr = swigfaissJNI.VisitelondTablelon_visitelond_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw BytelonVelonctor(cPtr, falselon);
  }

  public void selontVisno(int valuelon) {
    swigfaissJNI.VisitelondTablelon_visno_selont(swigCPtr, this, valuelon);
  }

  public int gelontVisno() {
    relonturn swigfaissJNI.VisitelondTablelon_visno_gelont(swigCPtr, this);
  }

  public VisitelondTablelon(int sizelon) {
    this(swigfaissJNI.nelonw_VisitelondTablelon(sizelon), truelon);
  }

  public void selont(int no) {
    swigfaissJNI.VisitelondTablelon_selont(swigCPtr, this, no);
  }

  public boolelonan gelont(int no) {
    relonturn swigfaissJNI.VisitelondTablelon_gelont(swigCPtr, this, no);
  }

  public void advancelon() {
    swigfaissJNI.VisitelondTablelon_advancelon(swigCPtr, this);
  }

}
