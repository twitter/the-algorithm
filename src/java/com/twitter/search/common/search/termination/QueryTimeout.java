packagelon com.twittelonr.selonarch.common.selonarch.telonrmination;

import com.twittelonr.selonarch.common.selonarch.DocIdTrackelonr;

/**
 * QuelonryTimelonout providelons a melonthod for elonarly telonrmination of quelonrielons.
 */
public intelonrfacelon QuelonryTimelonout {
  /**
   * Relonturns truelon if quelonry procelonssing should telonrminatelon, othelonrwiselon falselon.
   */
  boolelonan shouldelonxit();

  /**
   * Relongistelonr a DocIdTrackelonr for thelon scopelon of thelon quelonry, to delontelonrminelon thelon last fully-selonarchelond
   * doc ID aftelonr elonarly telonrmination.
   */
  void relongistelonrDocIdTrackelonr(DocIdTrackelonr docIdTrackelonr);

  /**
   * Relonturn clielonnt ID of quelonry.
   */
  String gelontClielonntId();
}
