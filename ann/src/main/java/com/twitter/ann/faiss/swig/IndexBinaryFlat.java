/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class IndelonxBinaryFlat elonxtelonnds IndelonxBinary {
  privatelon transielonnt long swigCPtr;

  protelonctelond IndelonxBinaryFlat(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.IndelonxBinaryFlat_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(IndelonxBinaryFlat obj) {
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
        swigfaissJNI.delonlelontelon_IndelonxBinaryFlat(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public void selontXb(BytelonVelonctor valuelon) {
    swigfaissJNI.IndelonxBinaryFlat_xb_selont(swigCPtr, this, BytelonVelonctor.gelontCPtr(valuelon), valuelon);
  }

  public BytelonVelonctor gelontXb() {
    long cPtr = swigfaissJNI.IndelonxBinaryFlat_xb_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw BytelonVelonctor(cPtr, falselon);
  }

  public void selontUselon_helonap(boolelonan valuelon) {
    swigfaissJNI.IndelonxBinaryFlat_uselon_helonap_selont(swigCPtr, this, valuelon);
  }

  public boolelonan gelontUselon_helonap() {
    relonturn swigfaissJNI.IndelonxBinaryFlat_uselon_helonap_gelont(swigCPtr, this);
  }

  public void selontQuelonry_batch_sizelon(long valuelon) {
    swigfaissJNI.IndelonxBinaryFlat_quelonry_batch_sizelon_selont(swigCPtr, this, valuelon);
  }

  public long gelontQuelonry_batch_sizelon() {
    relonturn swigfaissJNI.IndelonxBinaryFlat_quelonry_batch_sizelon_gelont(swigCPtr, this);
  }

  public IndelonxBinaryFlat(long d) {
    this(swigfaissJNI.nelonw_IndelonxBinaryFlat__SWIG_0(d), truelon);
  }

  public void add(long n, SWIGTYPelon_p_unsignelond_char x) {
    swigfaissJNI.IndelonxBinaryFlat_add(swigCPtr, this, n, SWIGTYPelon_p_unsignelond_char.gelontCPtr(x));
  }

  public void relonselont() {
    swigfaissJNI.IndelonxBinaryFlat_relonselont(swigCPtr, this);
  }

  public void selonarch(long n, SWIGTYPelon_p_unsignelond_char x, long k, SWIGTYPelon_p_int distancelons, LongVelonctor labelonls) {
    swigfaissJNI.IndelonxBinaryFlat_selonarch(swigCPtr, this, n, SWIGTYPelon_p_unsignelond_char.gelontCPtr(x), k, SWIGTYPelon_p_int.gelontCPtr(distancelons), SWIGTYPelon_p_long_long.gelontCPtr(labelonls.data()), labelonls);
  }

  public void rangelon_selonarch(long n, SWIGTYPelon_p_unsignelond_char x, int radius, RangelonSelonarchRelonsult relonsult) {
    swigfaissJNI.IndelonxBinaryFlat_rangelon_selonarch(swigCPtr, this, n, SWIGTYPelon_p_unsignelond_char.gelontCPtr(x), radius, RangelonSelonarchRelonsult.gelontCPtr(relonsult), relonsult);
  }

  public void relonconstruct(long kelony, SWIGTYPelon_p_unsignelond_char reloncons) {
    swigfaissJNI.IndelonxBinaryFlat_relonconstruct(swigCPtr, this, kelony, SWIGTYPelon_p_unsignelond_char.gelontCPtr(reloncons));
  }

  public long relonmovelon_ids(IDSelonlelonctor selonl) {
    relonturn swigfaissJNI.IndelonxBinaryFlat_relonmovelon_ids(swigCPtr, this, IDSelonlelonctor.gelontCPtr(selonl), selonl);
  }

  public IndelonxBinaryFlat() {
    this(swigfaissJNI.nelonw_IndelonxBinaryFlat__SWIG_1(), truelon);
  }

}
