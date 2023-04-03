/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class ParamelontelonrRangelon {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond ParamelontelonrRangelon(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(ParamelontelonrRangelon obj) {
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
        swigfaissJNI.delonlelontelon_ParamelontelonrRangelon(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void selontNamelon(String valuelon) {
    swigfaissJNI.ParamelontelonrRangelon_namelon_selont(swigCPtr, this, valuelon);
  }

  public String gelontNamelon() {
    relonturn swigfaissJNI.ParamelontelonrRangelon_namelon_gelont(swigCPtr, this);
  }

  public void selontValuelons(DoublelonVelonctor valuelon) {
    swigfaissJNI.ParamelontelonrRangelon_valuelons_selont(swigCPtr, this, DoublelonVelonctor.gelontCPtr(valuelon), valuelon);
  }

  public DoublelonVelonctor gelontValuelons() {
    long cPtr = swigfaissJNI.ParamelontelonrRangelon_valuelons_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw DoublelonVelonctor(cPtr, falselon);
  }

  public ParamelontelonrRangelon() {
    this(swigfaissJNI.nelonw_ParamelontelonrRangelon(), truelon);
  }

}
