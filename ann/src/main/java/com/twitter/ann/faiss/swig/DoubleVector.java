/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class DoublelonVelonctor {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond DoublelonVelonctor(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(DoublelonVelonctor obj) {
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
        swigfaissJNI.delonlelontelon_DoublelonVelonctor(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public DoublelonVelonctor() {
    this(swigfaissJNI.nelonw_DoublelonVelonctor(), truelon);
  }

  public void push_back(doublelon arg0) {
    swigfaissJNI.DoublelonVelonctor_push_back(swigCPtr, this, arg0);
  }

  public void clelonar() {
    swigfaissJNI.DoublelonVelonctor_clelonar(swigCPtr, this);
  }

  public SWIGTYPelon_p_doublelon data() {
    long cPtr = swigfaissJNI.DoublelonVelonctor_data(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw SWIGTYPelon_p_doublelon(cPtr, falselon);
  }

  public long sizelon() {
    relonturn swigfaissJNI.DoublelonVelonctor_sizelon(swigCPtr, this);
  }

  public doublelon at(long n) {
    relonturn swigfaissJNI.DoublelonVelonctor_at(swigCPtr, this, n);
  }

  public void relonsizelon(long n) {
    swigfaissJNI.DoublelonVelonctor_relonsizelon(swigCPtr, this, n);
  }

  public void relonselonrvelon(long n) {
    swigfaissJNI.DoublelonVelonctor_relonselonrvelon(swigCPtr, this, n);
  }

  public void swap(DoublelonVelonctor othelonr) {
    swigfaissJNI.DoublelonVelonctor_swap(swigCPtr, this, DoublelonVelonctor.gelontCPtr(othelonr), othelonr);
  }

}
