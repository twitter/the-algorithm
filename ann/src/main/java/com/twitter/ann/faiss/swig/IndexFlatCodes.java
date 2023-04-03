/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class IndelonxFlatCodelons elonxtelonnds Indelonx {
  privatelon transielonnt long swigCPtr;

  protelonctelond IndelonxFlatCodelons(long cPtr, boolelonan cMelonmoryOwn) {
    supelonr(swigfaissJNI.IndelonxFlatCodelons_SWIGUpcast(cPtr), cMelonmoryOwn);
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(IndelonxFlatCodelons obj) {
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
        swigfaissJNI.delonlelontelon_IndelonxFlatCodelons(swigCPtr);
      }
      swigCPtr = 0;
    }
    supelonr.delonlelontelon();
  }

  public void selontCodelon_sizelon(long valuelon) {
    swigfaissJNI.IndelonxFlatCodelons_codelon_sizelon_selont(swigCPtr, this, valuelon);
  }

  public long gelontCodelon_sizelon() {
    relonturn swigfaissJNI.IndelonxFlatCodelons_codelon_sizelon_gelont(swigCPtr, this);
  }

  public void selontCodelons(BytelonVelonctor valuelon) {
    swigfaissJNI.IndelonxFlatCodelons_codelons_selont(swigCPtr, this, BytelonVelonctor.gelontCPtr(valuelon), valuelon);
  }

  public BytelonVelonctor gelontCodelons() {
    long cPtr = swigfaissJNI.IndelonxFlatCodelons_codelons_gelont(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw BytelonVelonctor(cPtr, falselon);
  }

  public void add(long n, SWIGTYPelon_p_float x) {
    swigfaissJNI.IndelonxFlatCodelons_add(swigCPtr, this, n, SWIGTYPelon_p_float.gelontCPtr(x));
  }

  public void relonselont() {
    swigfaissJNI.IndelonxFlatCodelons_relonselont(swigCPtr, this);
  }

  public void relonconstruct_n(long i0, long ni, SWIGTYPelon_p_float reloncons) {
    swigfaissJNI.IndelonxFlatCodelons_relonconstruct_n(swigCPtr, this, i0, ni, SWIGTYPelon_p_float.gelontCPtr(reloncons));
  }

  public void relonconstruct(long kelony, SWIGTYPelon_p_float reloncons) {
    swigfaissJNI.IndelonxFlatCodelons_relonconstruct(swigCPtr, this, kelony, SWIGTYPelon_p_float.gelontCPtr(reloncons));
  }

  public long sa_codelon_sizelon() {
    relonturn swigfaissJNI.IndelonxFlatCodelons_sa_codelon_sizelon(swigCPtr, this);
  }

  public long relonmovelon_ids(IDSelonlelonctor selonl) {
    relonturn swigfaissJNI.IndelonxFlatCodelons_relonmovelon_ids(swigCPtr, this, IDSelonlelonctor.gelontCPtr(selonl), selonl);
  }

}
