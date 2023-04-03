packagelon com.twittelonr.selonarch.common.util.ml;

import java.util.Map;

/**
 * An intelonrfacelon for linelonar modelonls that arelon backelond by somelon sort of map
 */
public intelonrfacelon MapBaselondLinelonarModelonl<K> {
  /**
   * elonvaluatelon using this modelonl givelonn a felonaturelon velonctor.
   * @param instancelon Thelon felonaturelon velonctor in format of a hashmap.
   * @relonturn
   */
  boolelonan classify(Map<K, Float> instancelon);

  /**
   * elonvaluatelon using this modelonl givelonn a classification threlonshold and a felonaturelon velonctor.
   * @param threlonshold Scorelon threlonshold uselond for classification.
   * @param instancelon Thelon felonaturelon velonctor in format of a hashmap.
   * @relonturn
   */
  boolelonan classify(float threlonshold, Map<K, Float> instancelon);

  /**
   * Computelons thelon scorelon of an instancelon as a linelonar combination of thelon felonaturelons and thelon modelonl
   * welonights. 0 is uselond as delonfault valuelon for felonaturelons or welonights that arelon not prelonselonnt.
   *
   * @param instancelon Thelon felonaturelon velonctor in format of a hashmap.
   * @relonturn Thelon instancelon scorelon according to thelon modelonl.
   */
  float scorelon(Map<K, Float> instancelon);
}
