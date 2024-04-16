package com.tw ter.t  l neranker.para ters

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.servo.dec der.Dec derGateBu lder
 mport com.tw ter.t  l neranker.para ters.ent y_t ets.Ent yT etsProduct on
 mport com.tw ter.t  l neranker.para ters.recap.RecapProduct on
 mport com.tw ter.t  l neranker.para ters.recap_author.RecapAuthorProduct on
 mport com.tw ter.t  l neranker.para ters.recap_hydrat on.RecapHydrat onProduct on
 mport com.tw ter.t  l neranker.para ters. n_network_t ets. nNetworkT etProduct on
 mport com.tw ter.t  l neranker.para ters.revchron.ReverseChronProduct on
 mport com.tw ter.t  l neranker.para ters.uteg_l ked_by_t ets.UtegL kedByT etsProduct on
 mport com.tw ter.t  l neranker.para ters.mon or ng.Mon or ngProduct on
 mport com.tw ter.t  l nes.conf gap .Compos eConf g
 mport com.tw ter.t  l nes.conf gap .Conf g

/**
 * Bu lds global compos e conf g conta n ng pr or  zed "layers" of para ter overr des
 * based on wh el sts, exper  nts, and dec ders. Generated conf g can be used  n tests w h
 * mocked dec der and wh el st.
 */
class Conf gBu lder(dec derGateBu lder: Dec derGateBu lder, statsRece ver: StatsRece ver) {

  /**
   * Product on conf g wh ch  ncludes all conf gs wh ch contr bute to product on behav or. At
   * m n mum,   should  nclude all conf gs conta n ng dec der-based param overr des.
   *
   *    s  mportant that t  product on conf g  nclude all product on param overr des as    s
   * used to bu ld holdback exper  nt conf gs;  f t  product on conf g doesn't  nclude all param
   * overr des support ng product on behav or t n holdback exper  nt "product on" buckets w ll
   * not reflect product on behav or.
   */
  val prodConf g: Conf g = new Compos eConf g(
    Seq(
      new RecapProduct on(dec derGateBu lder, statsRece ver).conf g,
      new  nNetworkT etProduct on(dec derGateBu lder).conf g,
      new ReverseChronProduct on(dec derGateBu lder).conf g,
      new Ent yT etsProduct on(dec derGateBu lder).conf g,
      new RecapAuthorProduct on(dec derGateBu lder).conf g,
      new RecapHydrat onProduct on(dec derGateBu lder).conf g,
      new UtegL kedByT etsProduct on(dec derGateBu lder).conf g,
      Mon or ngProduct on.conf g
    ),
    "prodConf g"
  )

  val wh el stConf g: Conf g = new Compos eConf g(
    Seq(
      // No wh el sts conf gured at present.
    ),
    "wh el stConf g"
  )

  val rootConf g: Conf g = new Compos eConf g(
    Seq(
      wh el stConf g,
      prodConf g
    ),
    "rootConf g"
  )
}
