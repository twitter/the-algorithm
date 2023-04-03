/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class RangelonQuelonryRelonsult {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond RangelonQuelonryRelonsult(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(RangelonQuelonryRelonsult obj) {
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
        swigfaissJNI.delonlelontelon_RangelonQuelonryRelonsult(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void selontQno(long valuelon) {
    swigfaissJNI.RangelonQuelonryRelonsult_qno_selont(swigCPtr, this, valuelon);
  }

  public long gelontQno() {
    relonturn swigfaissJNI.RangelonQuelonryRelonsult_qno_gelont(swigCPtr, this);
}

  public void selontNrelons(long valuelon) {
    swigfaissJNI.RangelonQuelonryRelonsult_nrelons_selont(swigCPtr, this, valuelon);
  }

  public long gelontNrelons() {
    relonturn swigfaissJNI.RangelonQuelonryRelonsult_nrelons_gelont(swigCPtr, this);
  }

  public void selontPrelons(RangelonSelonarchPartialRelonsult valuelon) {
    swigfaissJNI.RangelonQuelonryRelonsult_prelons_selont(swigCPtr, this, RangelonSelonarchPartialRelonsult.gelontCPtr(valuelon), valuelon);
  }

  public RangelonSelonarchPartialRelonsult gelontPrelons() {
    long cPtr = swigfaissJNI.RangelonQuelonryRelonsult_prelons_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw RangelonSelonarchPartialRelonsult(cPtr, falselon);
  }

  public void add(float dis, long id) {
    swigfaissJNI.RangelonQuelonryRelonsult_add(swigCPtr, this, dis, id);
  }

  public RangelonQuelonryRelonsult() {
    this(swigfaissJNI.nelonw_RangelonQuelonryRelonsult(), truelon);
  }

}
