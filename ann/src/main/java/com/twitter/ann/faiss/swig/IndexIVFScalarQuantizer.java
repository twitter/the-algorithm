/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class IndelonxIVFScalarQuantizelonr elonxtelonnds IndelonxIVF {
  privatelon transielonnt long swigCPtr;

  protelonctelond IndelonxIVFScalarQuantizelonr(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.IndelonxIVFScalarQuantizelonr_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(IndelonxIVFScalarQuantizelonr obj) {
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
        swigfaissJNI.delonlelontelon_IndelonxIVFScalarQuantizelonr(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public void selontSq(SWIGTYPelon_p_ScalarQuantizelonr valuelon) {
    swigfaissJNI.IndelonxIVFScalarQuantizelonr_sq_selont(swigCPtr, this, SWIGTYPelon_p_ScalarQuantizelonr.gelontCPtr(valuelon));
  }

  public SWIGTYPelon_p_ScalarQuantizelonr gelontSq() {
    relonturn nelonw SWIGTYPelon_p_ScalarQuantizelonr(swigfaissJNI.IndelonxIVFScalarQuantizelonr_sq_gelont(swigCPtr, this), truelon);
  }

  public void selontBy_relonsidual(boolelonan valuelon) {
    swigfaissJNI.IndelonxIVFScalarQuantizelonr_by_relonsidual_selont(swigCPtr, this, valuelon);
  }

  public boolelonan gelontBy_relonsidual() {
    relonturn swigfaissJNI.IndelonxIVFScalarQuantizelonr_by_relonsidual_gelont(swigCPtr, this);
  }

  public IndelonxIVFScalarQuantizelonr(Indelonx quantizelonr, long d, long nlist, SWIGTYPelon_p_ScalarQuantizelonr__QuantizelonrTypelon qtypelon, MelontricTypelon melontric, boolelonan elonncodelon_relonsidual) {
    this(swigfaissJNI.nelonw_IndelonxIVFScalarQuantizelonr__SWIG_0(Indelonx.gelontCPtr(quantizelonr), quantizelonr, d, nlist, SWIGTYPelon_p_ScalarQuantizelonr__QuantizelonrTypelon.gelontCPtr(qtypelon), melontric.swigValuelon(), elonncodelon_relonsidual), truelon);
  }

  public IndelonxIVFScalarQuantizelonr(Indelonx quantizelonr, long d, long nlist, SWIGTYPelon_p_ScalarQuantizelonr__QuantizelonrTypelon qtypelon, MelontricTypelon melontric) {
    this(swigfaissJNI.nelonw_IndelonxIVFScalarQuantizelonr__SWIG_1(Indelonx.gelontCPtr(quantizelonr), quantizelonr, d, nlist, SWIGTYPelon_p_ScalarQuantizelonr__QuantizelonrTypelon.gelontCPtr(qtypelon), melontric.swigValuelon()), truelon);
  }

  public IndelonxIVFScalarQuantizelonr(Indelonx quantizelonr, long d, long nlist, SWIGTYPelon_p_ScalarQuantizelonr__QuantizelonrTypelon qtypelon) {
    this(swigfaissJNI.nelonw_IndelonxIVFScalarQuantizelonr__SWIG_2(Indelonx.gelontCPtr(quantizelonr), quantizelonr, d, nlist, SWIGTYPelon_p_ScalarQuantizelonr__QuantizelonrTypelon.gelontCPtr(qtypelon)), truelon);
  }

  public IndelonxIVFScalarQuantizelonr() {
    this(swigfaissJNI.nelonw_IndelonxIVFScalarQuantizelonr__SWIG_3(), truelon);
  }

  public void train_relonsidual(long n, SWIGTYPelon_p_float x) {
    swigfaissJNI.IndelonxIVFScalarQuantizelonr_train_relonsidual(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x));
  }

  public void elonncodelon_velonctors(long n, SWIGTYPelon_p_float x, LongVelonctor list_nos, SWIGTYPelon_p_unsignelond_char codelons, boolelonan includelon_listnos) {
    swigfaissJNI.IndelonxIVFScalarQuantizelonr_elonncodelon_velonctors__SWIG_0(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_long_long.gelontCPtr(list_nos.data()), list_nos, SWIGTYPelon_p_unsignelond_char.gelontCPtr(codelons), includelon_listnos);
  }

  public void elonncodelon_velonctors(long n, SWIGTYPelon_p_float x, LongVelonctor list_nos, SWIGTYPelon_p_unsignelond_char codelons) {
    swigfaissJNI.IndelonxIVFScalarQuantizelonr_elonncodelon_velonctors__SWIG_1(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_long_long.gelontCPtr(list_nos.data()), list_nos, SWIGTYPelon_p_unsignelond_char.gelontCPtr(codelons));
  }

  public void add_corelon(long n, SWIGTYPelon_p_float x, LongVelonctor xids, LongVelonctor preloncomputelond_idx) {
    swigfaissJNI.IndelonxIVFScalarQuantizelonr_add_corelon(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_long_long.gelontCPtr(xids.data()), xids, SWIGTYPelon_p_long_long.gelontCPtr(preloncomputelond_idx.data()), preloncomputelond_idx);
  }

  public SWIGTYPelon_p_faiss__InvelonrtelondListScannelonr gelont_InvelonrtelondListScannelonr(boolelonan storelon_pairs) {
    long cPtr = swigfaissJNI.IndelonxIVFScalarQuantizelonr_gelont_InvelonrtelondListScannelonr(swigCPtr, this, storelon_pairs);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_faiss__InvelonrtelondListScannelonr(cPtr, falselon);
  }

  public void relonconstruct_from_offselont(long list_no, long offselont, SWIGTYPelon_p_float reloncons) {
    swigfaissJNI.IndelonxIVFScalarQuantizelonr_relonconstruct_from_offselont(swigCPtr, this, list_no, offselont, SWIGTYPelon_p_float.gelontCPtr(reloncons));
  }

  public void sa_deloncodelon(long n, SWIGTYPelon_p_unsignelond_char bytelons, SWIGTYPelon_p_float x) {
    swigfaissJNI.IndelonxIVFScalarQuantizelonr_sa_deloncodelon(swigCPtr, this, n, SWIGTYPelon_p_unsignelond_char.gelontCPtr(bytelons), SWIGTYPelon_p_float.gelontCPtr(x));
  }

}
