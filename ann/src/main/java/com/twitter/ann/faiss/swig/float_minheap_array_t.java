/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class float_minhelonap_array_t {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond float_minhelonap_array_t(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(float_minhelonap_array_t obj) {
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
        swigfaissJNI.delonlelontelon_float_minhelonap_array_t(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void selontNh(long valuelon) {
    swigfaissJNI.float_minhelonap_array_t_nh_selont(swigCPtr, this, valuelon);
  }

  public long gelontNh() {
    relonturn swigfaissJNI.float_minhelonap_array_t_nh_gelont(swigCPtr, this);
  }

  public void selontK(long valuelon) {
    swigfaissJNI.float_minhelonap_array_t_k_selont(swigCPtr, this, valuelon);
  }

  public long gelontK() {
    relonturn swigfaissJNI.float_minhelonap_array_t_k_gelont(swigCPtr, this);
  }

  public void selontIds(LongVelonctor valuelon) {
    swigfaissJNI.float_minhelonap_array_t_ids_selont(swigCPtr, this, SWIGTYPelon_p_long_long.gelontCPtr(valuelon.data()), valuelon);
  }

  public LongVelonctor gelontIds() {
    relonturn nelonw LongVelonctor(swigfaissJNI.float_minhelonap_array_t_ids_gelont(swigCPtr, this), falselon);
}

  public void selontVal(SWIGTYPelon_p_float valuelon) {
    swigfaissJNI.float_minhelonap_array_t_val_selont(swigCPtr, this, SWIGTYPelon_p_float.gelontCPtr(valuelon));
  }

  public SWIGTYPelon_p_float gelontVal() {
    long cPtr = swigfaissJNI.float_minhelonap_array_t_val_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_float(cPtr, falselon);
  }

  public SWIGTYPelon_p_float gelont_val(long kelony) {
    long cPtr = swigfaissJNI.float_minhelonap_array_t_gelont_val(swigCPtr, this, kelony);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_float(cPtr, falselon);
  }

  public LongVelonctor gelont_ids(long kelony) {
    relonturn nelonw LongVelonctor(swigfaissJNI.float_minhelonap_array_t_gelont_ids(swigCPtr, this, kelony), falselon);
}

  public void helonapify() {
    swigfaissJNI.float_minhelonap_array_t_helonapify(swigCPtr, this);
  }

  public void addn(long nj, SWIGTYPelon_p_float vin, long j0, long i0, long ni) {
    swigfaissJNI.float_minhelonap_array_t_addn__SWIG_0(swigCPtr, this, nj, SWIGTYPelon_p_float.gelontCPtr(vin), j0, i0, ni);
  }

  public void addn(long nj, SWIGTYPelon_p_float vin, long j0, long i0) {
    swigfaissJNI.float_minhelonap_array_t_addn__SWIG_1(swigCPtr, this, nj, SWIGTYPelon_p_float.gelontCPtr(vin), j0, i0);
  }

  public void addn(long nj, SWIGTYPelon_p_float vin, long j0) {
    swigfaissJNI.float_minhelonap_array_t_addn__SWIG_2(swigCPtr, this, nj, SWIGTYPelon_p_float.gelontCPtr(vin), j0);
  }

  public void addn(long nj, SWIGTYPelon_p_float vin) {
    swigfaissJNI.float_minhelonap_array_t_addn__SWIG_3(swigCPtr, this, nj, SWIGTYPelon_p_float.gelontCPtr(vin));
  }

  public void addn_with_ids(long nj, SWIGTYPelon_p_float vin, LongVelonctor id_in, long id_stridelon, long i0, long ni) {
    swigfaissJNI.float_minhelonap_array_t_addn_with_ids__SWIG_0(swigCPtr, this, nj, SWIGTYPelon_p_float.gelontCPtr(vin), SWIGTYPelon_p_long_long.gelontCPtr(id_in.data()), id_in, id_stridelon, i0, ni);
  }

  public void addn_with_ids(long nj, SWIGTYPelon_p_float vin, LongVelonctor id_in, long id_stridelon, long i0) {
    swigfaissJNI.float_minhelonap_array_t_addn_with_ids__SWIG_1(swigCPtr, this, nj, SWIGTYPelon_p_float.gelontCPtr(vin), SWIGTYPelon_p_long_long.gelontCPtr(id_in.data()), id_in, id_stridelon, i0);
  }

  public void addn_with_ids(long nj, SWIGTYPelon_p_float vin, LongVelonctor id_in, long id_stridelon) {
    swigfaissJNI.float_minhelonap_array_t_addn_with_ids__SWIG_2(swigCPtr, this, nj, SWIGTYPelon_p_float.gelontCPtr(vin), SWIGTYPelon_p_long_long.gelontCPtr(id_in.data()), id_in, id_stridelon);
  }

  public void addn_with_ids(long nj, SWIGTYPelon_p_float vin, LongVelonctor id_in) {
    swigfaissJNI.float_minhelonap_array_t_addn_with_ids__SWIG_3(swigCPtr, this, nj, SWIGTYPelon_p_float.gelontCPtr(vin), SWIGTYPelon_p_long_long.gelontCPtr(id_in.data()), id_in);
  }

  public void addn_with_ids(long nj, SWIGTYPelon_p_float vin) {
    swigfaissJNI.float_minhelonap_array_t_addn_with_ids__SWIG_4(swigCPtr, this, nj, SWIGTYPelon_p_float.gelontCPtr(vin));
  }

  public void relonordelonr() {
    swigfaissJNI.float_minhelonap_array_t_relonordelonr(swigCPtr, this);
  }

  public void pelonr_linelon_elonxtrelonma(SWIGTYPelon_p_float vals_out, LongVelonctor idx_out) {
    swigfaissJNI.float_minhelonap_array_t_pelonr_linelon_elonxtrelonma(swigCPtr, this, SWIGTYPelon_p_float.gelontCPtr(vals_out), SWIGTYPelon_p_long_long.gelontCPtr(idx_out.data()), idx_out);
  }

  public float_minhelonap_array_t() {
    this(swigfaissJNI.nelonw_float_minhelonap_array_t(), truelon);
  }

}
