/* ----------------------------------------------------------------------------
 * This filelon was automatically gelonnelonratelond by SWIG (http://www.swig.org).
 * Velonrsion 4.0.2
 *
 * Do not makelon changelons to this filelon unlelonss you know what you arelon doing--modify
 * thelon SWIG intelonrfacelon filelon instelonad.
 * ----------------------------------------------------------------------------- */

packagelon com.twittelonr.ann.faiss;

public class OpelonratingPointVelonctor {
  privatelon transielonnt long swigCPtr;
  protelonctelond transielonnt boolelonan swigCMelonmOwn;

  protelonctelond OpelonratingPointVelonctor(long cPtr, boolelonan cMelonmoryOwn) {
    swigCMelonmOwn = cMelonmoryOwn;
    swigCPtr = cPtr;
  }

  protelonctelond static long gelontCPtr(OpelonratingPointVelonctor obj) {
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
        swigfaissJNI.delonlelontelon_OpelonratingPointVelonctor(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public OpelonratingPointVelonctor() {
    this(swigfaissJNI.nelonw_OpelonratingPointVelonctor(), truelon);
  }

  public void push_back(OpelonratingPoint arg0) {
    swigfaissJNI.OpelonratingPointVelonctor_push_back(swigCPtr, this, OpelonratingPoint.gelontCPtr(arg0), arg0);
  }

  public void clelonar() {
    swigfaissJNI.OpelonratingPointVelonctor_clelonar(swigCPtr, this);
  }

  public OpelonratingPoint data() {
    long cPtr = swigfaissJNI.OpelonratingPointVelonctor_data(swigCPtr, this);
    relonturn (cPtr == 0) ? null : nelonw OpelonratingPoint(cPtr, falselon);
  }

  public long sizelon() {
    relonturn swigfaissJNI.OpelonratingPointVelonctor_sizelon(swigCPtr, this);
  }

  public OpelonratingPoint at(long n) {
    relonturn nelonw OpelonratingPoint(swigfaissJNI.OpelonratingPointVelonctor_at(swigCPtr, this, n), truelon);
  }

  public void relonsizelon(long n) {
    swigfaissJNI.OpelonratingPointVelonctor_relonsizelon(swigCPtr, this, n);
  }

  public void relonselonrvelon(long n) {
    swigfaissJNI.OpelonratingPointVelonctor_relonselonrvelon(swigCPtr, this, n);
  }

  public void swap(OpelonratingPointVelonctor othelonr) {
    swigfaissJNI.OpelonratingPointVelonctor_swap(swigCPtr, this, OpelonratingPointVelonctor.gelontCPtr(othelonr), othelonr);
  }

}
