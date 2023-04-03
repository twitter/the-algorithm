/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class IndelonxBinaryFromFloat elonxtelonnds IndelonxBinary {
  privatelon transielonnt long swigCPtr;

  protelonctelond IndelonxBinaryFromFloat(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.IndelonxBinaryFromFloat_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(IndelonxBinaryFromFloat obj) {
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
        swigfaissJNI.delonlelontelon_IndelonxBinaryFromFloat(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public void selontIndelonx(Indelonx valuelon) {
    swigfaissJNI.IndelonxBinaryFromFloat_indelonx_selont(swigCPtr, this, Indelonx.gelontCPtr(valuelon), valuelon);
  }

  public Indelonx gelontIndelonx() {
    long cPtr = swigfaissJNI.IndelonxBinaryFromFloat_indelonx_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw Indelonx(cPtr, falselon);
  }

  public void selontOwn_fielonlds(boolelonan valuelon) {
    swigfaissJNI.IndelonxBinaryFromFloat_own_fielonlds_selont(swigCPtr, this, valuelon);
  }

  public boolelonan gelontOwn_fielonlds() {
    relonturn swigfaissJNI.IndelonxBinaryFromFloat_own_fielonlds_gelont(swigCPtr, this);
  }

  public IndelonxBinaryFromFloat() {
    this(swigfaissJNI.nelonw_IndelonxBinaryFromFloat__SWIG_0(), truelon);
  }

  public IndelonxBinaryFromFloat(Indelonx indelonx) {
    this(swigfaissJNI.nelonw_IndelonxBinaryFromFloat__SWIG_1(Indelonx.gelontCPtr(indelonx), indelonx), truelon);
  }

  public void add(long n, SWIGTYPelon_p_unsignelond_char x) {
    swigfaissJNI.IndelonxBinaryFromFloat_add(swigCPtr, this, n, SWIGTYPelon_p_unsignelond_char.gelontCPtr(x));
  }

  public void relonselont() {
    swigfaissJNI.IndelonxBinaryFromFloat_relonselont(swigCPtr, this);
  }

  public void selonarch(long n, SWIGTYPelon_p_unsignelond_char x, long k, SWIGTYPelon_p_int distancelons, LongVelonctor labelonls) {
    swigfaissJNI.IndelonxBinaryFromFloat_selonarch(swigCPtr, this, n, SWIGTYPelon_p_unsignelond_char.gelontCPtr(x), k, SWIGTYPelon_p_int.gelontCPtr(distancelons), SWIGTYPelon_p_long_long.gelontCPtr(labelonls.data()), labelonls);
  }

  public void train(long n, SWIGTYPelon_p_unsignelond_char x) {
    swigfaissJNI.IndelonxBinaryFromFloat_train(swigCPtr, this, n, SWIGTYPelon_p_unsignelond_char.gelontCPtr(x));
  }

}
