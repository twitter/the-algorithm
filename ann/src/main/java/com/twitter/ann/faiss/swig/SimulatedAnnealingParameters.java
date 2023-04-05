try {
/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 4.0.2
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.twitter.ann.faiss;

public class SimulatedAnnealingParameters {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected SimulatedAnnealingParameters(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(SimulatedAnnealingParameters obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  @SuppressWarnings("deprecation")
  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        swigfaissJNI.delete_SimulatedAnnealingParameters(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setInit_temperature(double value) {
    swigfaissJNI.SimulatedAnnealingParameters_init_temperature_set(swigCPtr, this, value);
  }

  public double getInit_temperature() {
    return swigfaissJNI.SimulatedAnnealingParameters_init_temperature_get(swigCPtr, this);
  }

  public void setTemperature_decay(double value) {
    swigfaissJNI.SimulatedAnnealingParameters_temperature_decay_set(swigCPtr, this, value);
  }

  public double getTemperature_decay() {
    return swigfaissJNI.SimulatedAnnealingParameters_temperature_decay_get(swigCPtr, this);
  }

  public void setN_iter(int value) {
    swigfaissJNI.SimulatedAnnealingParameters_n_iter_set(swigCPtr, this, value);
  }

  public int getN_iter() {
    return swigfaissJNI.SimulatedAnnealingParameters_n_iter_get(swigCPtr, this);
  }

  public void setN_redo(int value) {
    swigfaissJNI.SimulatedAnnealingParameters_n_redo_set(swigCPtr, this, value);
  }

  public int getN_redo() {
    return swigfaissJNI.SimulatedAnnealingParameters_n_redo_get(swigCPtr, this);
  }

  public void setSeed(int value) {
    swigfaissJNI.SimulatedAnnealingParameters_seed_set(swigCPtr, this, value);
  }

  public int getSeed() {
    return swigfaissJNI.SimulatedAnnealingParameters_seed_get(swigCPtr, this);
  }

  public void setVerbose(int value) {
    swigfaissJNI.SimulatedAnnealingParameters_verbose_set(swigCPtr, this, value);
  }

  public int getVerbose() {
    return swigfaissJNI.SimulatedAnnealingParameters_verbose_get(swigCPtr, this);
  }

  public void setOnly_bit_flips(boolean value) {
    swigfaissJNI.SimulatedAnnealingParameters_only_bit_flips_set(swigCPtr, this, value);
  }

  public boolean getOnly_bit_flips() {
    return swigfaissJNI.SimulatedAnnealingParameters_only_bit_flips_get(swigCPtr, this);
  }

  public void setInit_random(boolean value) {
    swigfaissJNI.SimulatedAnnealingParameters_init_random_set(swigCPtr, this, value);
  }

  public boolean getInit_random() {
    return swigfaissJNI.SimulatedAnnealingParameters_init_random_get(swigCPtr, this);
  }

  public SimulatedAnnealingParameters() {
    this(swigfaissJNI.new_SimulatedAnnealingParameters(), true);
  }

}

} catch (Exception e) {
}
