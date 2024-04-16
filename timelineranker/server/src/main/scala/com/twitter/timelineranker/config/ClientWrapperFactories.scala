package com.tw ter.t  l neranker.conf g

 mport com.tw ter.servo.ut l.Gate
 mport com.tw ter.t  l neranker.cl ents.ScopedCortexT etQueryServ ceCl entFactory
 mport com.tw ter.t  l nes.cl ents.g zmoduck.ScopedG zmoduckCl entFactory
 mport com.tw ter.t  l nes.cl ents.manhattan.ScopedUser tadataCl entFactory
 mport com.tw ter.t  l nes.cl ents.soc algraph.ScopedSoc alGraphCl entFactory
 mport com.tw ter.t  l nes.cl ents.strato.realgraph.ScopedRealGraphCl entFactory
 mport com.tw ter.t  l nes.cl ents.t etyp e.Add  onalF eldConf g
 mport com.tw ter.t  l nes.cl ents.t etyp e.ScopedT etyP eCl entFactory
 mport com.tw ter.t  l nes.v s b l y.V s b l yEnforcerFactory
 mport com.tw ter.t  l nes.v s b l y.V s b l yProf leHydratorFactory
 mport com.tw ter.t etyp e.thr ftscala.{T et => TT et}

class Cl entWrapperFactor es(conf g: Runt  Conf gurat on) {
  pr vate[t ] val statsRece ver = conf g.statsRece ver

  val cortexT etQueryServ ceCl entFactory: ScopedCortexT etQueryServ ceCl entFactory =
    new ScopedCortexT etQueryServ ceCl entFactory(
      conf g.underly ngCl ents.cortexT etQueryServ ceCl ent,
      statsRece ver = statsRece ver
    )

  val g zmoduckCl entFactory: ScopedG zmoduckCl entFactory = new ScopedG zmoduckCl entFactory(
    conf g.underly ngCl ents.g zmoduckCl ent,
    statsRece ver = statsRece ver
  )

  val soc alGraphCl entFactory: ScopedSoc alGraphCl entFactory = new ScopedSoc alGraphCl entFactory(
    conf g.underly ngCl ents.sgsCl ent,
    statsRece ver
  )

  val v s b l yEnforcerFactory: V s b l yEnforcerFactory = new V s b l yEnforcerFactory(
    g zmoduckCl entFactory,
    soc alGraphCl entFactory,
    statsRece ver
  )

  val t etyP eAdd  onalF eldsToD sable: Seq[Short] = Seq(
    TT et. d aTagsF eld. d,
    TT et.Sc dul ng nfoF eld. d,
    TT et.Esc rb rdEnt yAnnotat onsF eld. d,
    TT et.CardReferenceF eld. d,
    TT et.SelfPermal nkF eld. d,
    TT et.ExtendedT et tadataF eld. d,
    TT et.Commun  esF eld. d,
    TT et.V s bleTextRangeF eld. d
  )

  val t etyP eH ghQoSCl entFactory: ScopedT etyP eCl entFactory =
    new ScopedT etyP eCl entFactory(
      t etyP eCl ent = conf g.underly ngCl ents.t etyP eH ghQoSCl ent,
      add  onalF eldConf g = Add  onalF eldConf g(
        f eldD sabl ngGates = t etyP eAdd  onalF eldsToD sable.map(_ -> Gate.False).toMap
      ),
       ncludePart alResults = Gate.False,
      statsRece ver = statsRece ver
    )

  val t etyP eLowQoSCl entFactory: ScopedT etyP eCl entFactory = new ScopedT etyP eCl entFactory(
    t etyP eCl ent = conf g.underly ngCl ents.t etyP eLowQoSCl ent,
    add  onalF eldConf g = Add  onalF eldConf g(
      f eldD sabl ngGates = t etyP eAdd  onalF eldsToD sable.map(_ -> Gate.False).toMap
    ),
     ncludePart alResults = Gate.False,
    statsRece ver = statsRece ver
  )

  val user tadataCl entFactory: ScopedUser tadataCl entFactory =
    new ScopedUser tadataCl entFactory(
      conf g.underly ngCl ents.manhattanStarbuckCl ent,
      T  l neRankerConstants.ManhattanStarbuckApp d,
      statsRece ver
    )

  val v s b l yProf leHydratorFactory: V s b l yProf leHydratorFactory =
    new V s b l yProf leHydratorFactory(
      g zmoduckCl entFactory,
      soc alGraphCl entFactory,
      statsRece ver
    )

  val realGraphCl entFactory =
    new ScopedRealGraphCl entFactory(conf g.underly ngCl ents.stratoCl ent, statsRece ver)
}
