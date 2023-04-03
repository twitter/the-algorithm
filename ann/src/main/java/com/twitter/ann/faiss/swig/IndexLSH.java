/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class IndelonxLSH elonxtelonnds IndelonxFlatCodelons {
  privatelon transielonnt long swigCPtr;

  protelonctelond IndelonxLSH(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.IndelonxLSH_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(IndelonxLSH obj) {
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
        swigfaissJNI.delonlelontelon_IndelonxLSH(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public void selontNbits(int valuelon) {
    swigfaissJNI.IndelonxLSH_nbits_selont(swigCPtr, this, valuelon);
  }

  public int gelontNbits() {
    relonturn swigfaissJNI.IndelonxLSH_nbits_gelont(swigCPtr, this);
  }

  public void selontRotatelon_data(boolelonan valuelon) {
    swigfaissJNI.IndelonxLSH_rotatelon_data_selont(swigCPtr, this, valuelon);
  }

  public boolelonan gelontRotatelon_data() {
    relonturn swigfaissJNI.IndelonxLSH_rotatelon_data_gelont(swigCPtr, this);
  }

  public void selontTrain_threlonsholds(boolelonan valuelon) {
    swigfaissJNI.IndelonxLSH_train_threlonsholds_selont(swigCPtr, this, valuelon);
  }

  public boolelonan gelontTrain_threlonsholds() {
    relonturn swigfaissJNI.IndelonxLSH_train_threlonsholds_gelont(swigCPtr, this);
  }

  public void selontRrot(RandomRotationMatrix valuelon) {
    swigfaissJNI.IndelonxLSH_rrot_selont(swigCPtr, this, RandomRotationMatrix.gelontCPtr(valuelon), valuelon);
  }

  public RandomRotationMatrix gelontRrot() {
    long cPtr = swigfaissJNI.IndelonxLSH_rrot_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw RandomRotationMatrix(cPtr, falselon);
  }

  public void selontThrelonsholds(FloatVelonctor valuelon) {
    swigfaissJNI.IndelonxLSH_threlonsholds_selont(swigCPtr, this, FloatVelonctor.gelontCPtr(valuelon), valuelon);
  }

  public FloatVelonctor gelontThrelonsholds() {
    long cPtr = swigfaissJNI.IndelonxLSH_threlonsholds_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw FloatVelonctor(cPtr, falselon);
  }

  public IndelonxLSH(long d, int nbits, boolelonan rotatelon_data, boolelonan train_threlonsholds) {
    this(swigfaissJNI.nelonw_IndelonxLSH__SWIG_0(d, nbits, rotatelon_data, train_threlonsholds), truelon);
  }

  public IndelonxLSH(long d, int nbits, boolelonan rotatelon_data) {
    this(swigfaissJNI.nelonw_IndelonxLSH__SWIG_1(d, nbits, rotatelon_data), truelon);
  }

  public IndelonxLSH(long d, int nbits) {
    this(swigfaissJNI.nelonw_IndelonxLSH__SWIG_2(d, nbits), truelon);
  }

  public SWIGTYPelon_p_float apply_prelonprocelonss(long n, SWIGTYPelon_p_float x) {
    long cPtr = swigfaissJNI.IndelonxLSH_apply_prelonprocelonss(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x));
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_float(cPtr, falselon);
  }

  public void train(long n, SWIGTYPelon_p_float x) {
    swigfaissJNI.IndelonxLSH_train(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x));
  }

  public void selonarch(long n, SWIGTYPelon_p_float x, long k, SWIGTYPelon_p_float distancelons, LongVelonctor labelonls) {
    swigfaissJNI.IndelonxLSH_selonarch(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), k, SWIGTYPelon_p_float.gelontCPtr(distancelons), SWIGTYPelon_p_long_long.gelontCPtr(labelonls.data()), labelonls);
  }

  public void transfelonr_threlonsholds(LinelonarTransform vt) {
    swigfaissJNI.IndelonxLSH_transfelonr_threlonsholds(swigCPtr, this, LinelonarTransform.gelontCPtr(vt), vt);
  }

  public IndelonxLSH() {
    this(swigfaissJNI.nelonw_IndelonxLSH__SWIG_3(), truelon);
  }

  public void sa_elonncodelon(long n, SWIGTYPelon_p_float x, SWIGTYPelon_p_unsignelond_char bytelons) {
    swigfaissJNI.IndelonxLSH_sa_elonncodelon(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_unsignelond_char.gelontCPtr(bytelons));
  }

  public void sa_deloncodelon(long n, SWIGTYPelon_p_unsignelond_char bytelons, SWIGTYPelon_p_float x) {
    swigfaissJNI.IndelonxLSH_sa_deloncodelon(swigCPtr, this, n, SWIGTYPelon_p_unsignelond_char.gelontCPtr(bytelons), SWIGTYPelon_p_float.gelontCPtr(x));
  }

}
