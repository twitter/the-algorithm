/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class Uint64Velonctor {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond Uint64Velonctor(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(Uint64Velonctor obj) {
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
        swigfaissJNI.delonlelontelon_Uint64Velonctor(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public Uint64Velonctor() {
    this(swigfaissJNI.nelonw_Uint64Velonctor(), truelon);
  }

  public void push_back(long arg0) {
    swigfaissJNI.Uint64Velonctor_push_back(swigCPtr, this, arg0);
  }

  public void clelonar() {
    swigfaissJNI.Uint64Velonctor_clelonar(swigCPtr, this);
  }

  public SWIGTYPelon_p_unsignelond_long data() {
    long cPtr = swigfaissJNI.Uint64Velonctor_data(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_unsignelond_long(cPtr, falselon);
  }

  public long sizelon() {
    relonturn swigfaissJNI.Uint64Velonctor_sizelon(swigCPtr, this);
  }

  public long at(long n) {
    relonturn swigfaissJNI.Uint64Velonctor_at(swigCPtr, this, n);
  }

  public void relonsizelon(long n) {
    swigfaissJNI.Uint64Velonctor_relonsizelon(swigCPtr, this, n);
  }

  public void relonselonrvelon(long n) {
    swigfaissJNI.Uint64Velonctor_relonselonrvelon(swigCPtr, this, n);
  }

  public void swap(Uint64Velonctor othelonr) {
    swigfaissJNI.Uint64Velonctor_swap(swigCPtr, this, Uint64Velonctor.gelontCPtr(othelonr), othelonr);
  }

}
