package com.tw ter.product_m xer.component_l brary.model.query.ads

 mport com.tw ter.adserver.{thr ftscala => ads}
 mport com.tw ter.dspb dder.commons.{thr ftscala => dsp}

/**
 * AdsQuery holds request-t   f elds requ red by   ads cand date p pel nes
 */
tra  AdsQuery {

  /**
   * T  l nes-spec f c context.
   *
   * @note used  n Ho  T  l nes
   */
  def t  l neRequestParams: Opt on[ads.T  l neRequestParams] = None

  /**
   * Nav gat on act on tr gger-type
   *
   * @note used  n Ho  T  l nes
   */
  def requestTr ggerType: Opt on[ads.RequestTr ggerType] = None

  /**
   * Autoplay sett ng
   *
   * @note used  n Ho  T  l nes
   */
  def autoplayEnabled: Opt on[Boolean] = None

  /**
   * D sable NSFW avo dance for ads m x ng
   *
   * @note used  n Ho  T  l nes
   */
  def d sableNsfwAvo dance: Opt on[Boolean] = None

  /**
   * DSP context for adwords
   *
   * @note used  n Ho  T  l nes
   */
  def dspCl entContext: Opt on[dsp.DspCl entContext] = None

  /**
   * User  D for t  User Prof le be ng v e d.
   *
   * @note used  n Prof le T  l nes
   */
  def userProf leV e dUser d: Opt on[Long] = None

  /**
   * Search-spec f c context.
   *
   * @note used  n Search T  l nes
   */
  def searchRequestContext: Opt on[ads.SearchRequestContext] = None
}
