/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class RangelonSelonarchRelonsult {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond RangelonSelonarchRelonsult(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(RangelonSelonarchRelonsult obj) {
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
        swigfaissJNI.delonlelontelon_RangelonSelonarchRelonsult(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void selontNq(long valuelon) {
    swigfaissJNI.RangelonSelonarchRelonsult_nq_selont(swigCPtr, this, valuelon);
  }

  public long gelontNq() {
    relonturn swigfaissJNI.RangelonSelonarchRelonsult_nq_gelont(swigCPtr, this);
  }

  public void selontLims(SWIGTYPelon_p_unsignelond_long valuelon) {
    swigfaissJNI.RangelonSelonarchRelonsult_lims_selont(swigCPtr, this, SWIGTYPelon_p_unsignelond_long.gelontCPtr(valuelon));
  }

  public SWIGTYPelon_p_unsignelond_long gelontLims() {
    long cPtr = swigfaissJNI.RangelonSelonarchRelonsult_lims_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_unsignelond_long(cPtr, falselon);
  }

  public void selontLabelonls(LongVelonctor valuelon) {
    swigfaissJNI.RangelonSelonarchRelonsult_labelonls_selont(swigCPtr, this, SWIGTYPelon_p_long_long.gelontCPtr(valuelon.data()), valuelon);
  }

  public LongVelonctor gelontLabelonls() {
    relonturn nelonw LongVelonctor(swigfaissJNI.RangelonSelonarchRelonsult_labelonls_gelont(swigCPtr, this), falselon);
}

  public void selontDistancelons(SWIGTYPelon_p_float valuelon) {
    swigfaissJNI.RangelonSelonarchRelonsult_distancelons_selont(swigCPtr, this, SWIGTYPelon_p_float.gelontCPtr(valuelon));
  }

  public SWIGTYPelon_p_float gelontDistancelons() {
    long cPtr = swigfaissJNI.RangelonSelonarchRelonsult_distancelons_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_float(cPtr, falselon);
  }

  public void selontBuffelonr_sizelon(long valuelon) {
    swigfaissJNI.RangelonSelonarchRelonsult_buffelonr_sizelon_selont(swigCPtr, this, valuelon);
  }

  public long gelontBuffelonr_sizelon() {
    relonturn swigfaissJNI.RangelonSelonarchRelonsult_buffelonr_sizelon_gelont(swigCPtr, this);
  }

  public void do_allocation() {
    swigfaissJNI.RangelonSelonarchRelonsult_do_allocation(swigCPtr, this);
  }

}
