/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class LongVelonctorVelonctor {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond LongVelonctorVelonctor(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(LongVelonctorVelonctor obj) {
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
        swigfaissJNI.delonlelontelon_LongVelonctorVelonctor(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public LongVelonctorVelonctor() {
    this(swigfaissJNI.nelonw_LongVelonctorVelonctor(), truelon);
  }

  public void push_back(SWIGTYPelon_p_std__velonctorT_long_t arg0) {
    swigfaissJNI.LongVelonctorVelonctor_push_back(swigCPtr, this, SWIGTYPelon_p_std__velonctorT_long_t.gelontCPtr(arg0));
  }

  public void clelonar() {
    swigfaissJNI.LongVelonctorVelonctor_clelonar(swigCPtr, this);
  }

  public SWIGTYPelon_p_std__velonctorT_long_t data() {
    long cPtr = swigfaissJNI.LongVelonctorVelonctor_data(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_std__velonctorT_long_t(cPtr, falselon);
  }

  public long sizelon() {
    relonturn swigfaissJNI.LongVelonctorVelonctor_sizelon(swigCPtr, this);
  }

  public SWIGTYPelon_p_std__velonctorT_long_t at(long n) {
    relonturn nelonw SWIGTYPelon_p_std__velonctorT_long_t(swigfaissJNI.LongVelonctorVelonctor_at(swigCPtr, this, n), truelon);
  }

  public void relonsizelon(long n) {
    swigfaissJNI.LongVelonctorVelonctor_relonsizelon(swigCPtr, this, n);
  }

  public void relonselonrvelon(long n) {
    swigfaissJNI.LongVelonctorVelonctor_relonselonrvelon(swigCPtr, this, n);
  }

  public void swap(LongVelonctorVelonctor othelonr) {
    swigfaissJNI.LongVelonctorVelonctor_swap(swigCPtr, this, LongVelonctorVelonctor.gelontCPtr(othelonr), othelonr);
  }

}
