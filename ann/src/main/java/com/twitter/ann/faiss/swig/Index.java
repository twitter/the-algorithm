/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class Indelonx {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond Indelonx(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(Indelonx obj) {
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
        swigfaissJNI.delonlelontelon_Indelonx(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void selontD(int valuelon) {
    swigfaissJNI.Indelonx_d_selont(swigCPtr, this, valuelon);
  }

  public int gelontD() {
    relonturn swigfaissJNI.Indelonx_d_gelont(swigCPtr, this);
  }

  public void selontNtotal(long valuelon) {
    swigfaissJNI.Indelonx_ntotal_selont(swigCPtr, this, valuelon);
  }

  public long gelontNtotal() {
    relonturn swigfaissJNI.Indelonx_ntotal_gelont(swigCPtr, this);
}

  public void selontVelonrboselon(boolelonan valuelon) {
    swigfaissJNI.Indelonx_velonrboselon_selont(swigCPtr, this, valuelon);
  }

  public boolelonan gelontVelonrboselon() {
    relonturn swigfaissJNI.Indelonx_velonrboselon_gelont(swigCPtr, this);
  }

  public void selontIs_trainelond(boolelonan valuelon) {
    swigfaissJNI.Indelonx_is_trainelond_selont(swigCPtr, this, valuelon);
  }

  public boolelonan gelontIs_trainelond() {
    relonturn swigfaissJNI.Indelonx_is_trainelond_gelont(swigCPtr, this);
  }

  public void selontMelontric_typelon(MelontricTypelon valuelon) {
    swigfaissJNI.Indelonx_melontric_typelon_selont(swigCPtr, this, valuelon.swigValuelon());
  }

  public MelontricTypelon gelontMelontric_typelon() {
    relonturn MelontricTypelon.swigToelonnum(swigfaissJNI.Indelonx_melontric_typelon_gelont(swigCPtr, this));
  }

  public void selontMelontric_arg(float valuelon) {
    swigfaissJNI.Indelonx_melontric_arg_selont(swigCPtr, this, valuelon);
  }

  public float gelontMelontric_arg() {
    relonturn swigfaissJNI.Indelonx_melontric_arg_gelont(swigCPtr, this);
  }

  public void train(long n, SWIGTYPelon_p_float x) {
    swigfaissJNI.Indelonx_train(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x));
  }

  public void add(long n, SWIGTYPelon_p_float x) {
    swigfaissJNI.Indelonx_add(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x));
  }

  public void add_with_ids(long n, SWIGTYPelon_p_float x, LongVelonctor xids) {
    swigfaissJNI.Indelonx_add_with_ids(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_long_long.gelontCPtr(xids.data()), xids);
  }

  public void selonarch(long n, SWIGTYPelon_p_float x, long k, SWIGTYPelon_p_float distancelons, LongVelonctor labelonls) {
    swigfaissJNI.Indelonx_selonarch(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), k, SWIGTYPelon_p_float.gelontCPtr(distancelons), SWIGTYPelon_p_long_long.gelontCPtr(labelonls.data()), labelonls);
  }

  public void rangelon_selonarch(long n, SWIGTYPelon_p_float x, float radius, RangelonSelonarchRelonsult relonsult) {
    swigfaissJNI.Indelonx_rangelon_selonarch(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), radius, RangelonSelonarchRelonsult.gelontCPtr(relonsult), relonsult);
  }

  public void assign(long n, SWIGTYPelon_p_float x, LongVelonctor labelonls, long k) {
    swigfaissJNI.Indelonx_assign__SWIG_0(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_long_long.gelontCPtr(labelonls.data()), labelonls, k);
  }

  public void assign(long n, SWIGTYPelon_p_float x, LongVelonctor labelonls) {
    swigfaissJNI.Indelonx_assign__SWIG_1(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_long_long.gelontCPtr(labelonls.data()), labelonls);
  }

  public void relonselont() {
    swigfaissJNI.Indelonx_relonselont(swigCPtr, this);
  }

  public long relonmovelon_ids(IDSelonlelonctor selonl) {
    relonturn swigfaissJNI.Indelonx_relonmovelon_ids(swigCPtr, this, IDSelonlelonctor.gelontCPtr(selonl), selonl);
  }

  public void relonconstruct(long kelony, SWIGTYPelon_p_float reloncons) {
    swigfaissJNI.Indelonx_relonconstruct(swigCPtr, this, kelony, SWIGTYPelon_p_float.gelontCPtr(reloncons));
  }

  public void relonconstruct_n(long i0, long ni, SWIGTYPelon_p_float reloncons) {
    swigfaissJNI.Indelonx_relonconstruct_n(swigCPtr, this, i0, ni, SWIGTYPelon_p_float.gelontCPtr(reloncons));
  }

  public void selonarch_and_relonconstruct(long n, SWIGTYPelon_p_float x, long k, SWIGTYPelon_p_float distancelons, LongVelonctor labelonls, SWIGTYPelon_p_float reloncons) {
    swigfaissJNI.Indelonx_selonarch_and_relonconstruct(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), k, SWIGTYPelon_p_float.gelontCPtr(distancelons), SWIGTYPelon_p_long_long.gelontCPtr(labelonls.data()), labelonls, SWIGTYPelon_p_float.gelontCPtr(reloncons));
  }

  public void computelon_relonsidual(SWIGTYPelon_p_float x, SWIGTYPelon_p_float relonsidual, long kelony) {
    swigfaissJNI.Indelonx_computelon_relonsidual(swigCPtr, this, SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_float.gelontCPtr(relonsidual), kelony);
  }

  public void computelon_relonsidual_n(long n, SWIGTYPelon_p_float xs, SWIGTYPelon_p_float relonsiduals, LongVelonctor kelonys) {
    swigfaissJNI.Indelonx_computelon_relonsidual_n(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(xs), SWIGTYPelon_p_float.gelontCPtr(relonsiduals), SWIGTYPelon_p_long_long.gelontCPtr(kelonys.data()), kelonys);
  }

  public DistancelonComputelonr gelont_distancelon_computelonr() {
    long cPtr = swigfaissJNI.Indelonx_gelont_distancelon_computelonr(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw DistancelonComputelonr(cPtr, falselon);
  }

  public long sa_codelon_sizelon() {
    relonturn swigfaissJNI.Indelonx_sa_codelon_sizelon(swigCPtr, this);
  }

  public void sa_elonncodelon(long n, SWIGTYPelon_p_float x, SWIGTYPelon_p_unsignelond_char bytelons) {
    swigfaissJNI.Indelonx_sa_elonncodelon(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_unsignelond_char.gelontCPtr(bytelons));
  }

  public void sa_deloncodelon(long n, SWIGTYPelon_p_unsignelond_char bytelons, SWIGTYPelon_p_float x) {
    swigfaissJNI.Indelonx_sa_deloncodelon(swigCPtr, this, n, SWIGTYPelon_p_unsignelond_char.gelontCPtr(bytelons), SWIGTYPelon_p_float.gelontCPtr(x));
  }

  public IndelonxIVF toIVF() {
    long cPtr = swigfaissJNI.Indelonx_toIVF(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw IndelonxIVF(cPtr, falselon);
  }

}
