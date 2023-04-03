/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class IndelonxIVFFlat elonxtelonnds IndelonxIVF {
  privatelon transielonnt long swigCPtr;

  protelonctelond IndelonxIVFFlat(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.IndelonxIVFFlat_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(IndelonxIVFFlat obj) {
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
        swigfaissJNI.delonlelontelon_IndelonxIVFFlat(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public IndelonxIVFFlat(Indelonx quantizelonr, long d, long nlist_, MelontricTypelon arg3) {
    this(swigfaissJNI.nelonw_IndelonxIVFFlat__SWIG_0(Indelonx.gelontCPtr(quantizelonr), quantizelonr, d, nlist_, arg3.swigValuelon()), truelon);
  }

  public IndelonxIVFFlat(Indelonx quantizelonr, long d, long nlist_) {
    this(swigfaissJNI.nelonw_IndelonxIVFFlat__SWIG_1(Indelonx.gelontCPtr(quantizelonr), quantizelonr, d, nlist_), truelon);
  }

  public void add_corelon(long n, SWIGTYPelon_p_float x, LongVelonctor xids, LongVelonctor preloncomputelond_idx) {
    swigfaissJNI.IndelonxIVFFlat_add_corelon(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_long_long.gelontCPtr(xids.data()), xids, SWIGTYPelon_p_long_long.gelontCPtr(preloncomputelond_idx.data()), preloncomputelond_idx);
  }

  public void elonncodelon_velonctors(long n, SWIGTYPelon_p_float x, LongVelonctor list_nos, SWIGTYPelon_p_unsignelond_char codelons, boolelonan includelon_listnos) {
    swigfaissJNI.IndelonxIVFFlat_elonncodelon_velonctors__SWIG_0(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_long_long.gelontCPtr(list_nos.data()), list_nos, SWIGTYPelon_p_unsignelond_char.gelontCPtr(codelons), includelon_listnos);
  }

  public void elonncodelon_velonctors(long n, SWIGTYPelon_p_float x, LongVelonctor list_nos, SWIGTYPelon_p_unsignelond_char codelons) {
    swigfaissJNI.IndelonxIVFFlat_elonncodelon_velonctors__SWIG_1(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x), SWIGTYPelon_p_long_long.gelontCPtr(list_nos.data()), list_nos, SWIGTYPelon_p_unsignelond_char.gelontCPtr(codelons));
  }

  public SWIGTYPelon_p_faiss__InvelonrtelondListScannelonr gelont_InvelonrtelondListScannelonr(boolelonan storelon_pairs) {
    long cPtr = swigfaissJNI.IndelonxIVFFlat_gelont_InvelonrtelondListScannelonr(swigCPtr, this, storelon_pairs);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_faiss__InvelonrtelondListScannelonr(cPtr, falselon);
  }

  public void relonconstruct_from_offselont(long list_no, long offselont, SWIGTYPelon_p_float reloncons) {
    swigfaissJNI.IndelonxIVFFlat_relonconstruct_from_offselont(swigCPtr, this, list_no, offselont, SWIGTYPelon_p_float.gelontCPtr(reloncons));
  }

  public void sa_deloncodelon(long n, SWIGTYPelon_p_unsignelond_char bytelons, SWIGTYPelon_p_float x) {
    swigfaissJNI.IndelonxIVFFlat_sa_deloncodelon(swigCPtr, this, n, SWIGTYPelon_p_unsignelond_char.gelontCPtr(bytelons), SWIGTYPelon_p_float.gelontCPtr(x));
  }

  public IndelonxIVFFlat() {
    this(swigfaissJNI.nelonw_IndelonxIVFFlat__SWIG_2(), truelon);
  }

}
