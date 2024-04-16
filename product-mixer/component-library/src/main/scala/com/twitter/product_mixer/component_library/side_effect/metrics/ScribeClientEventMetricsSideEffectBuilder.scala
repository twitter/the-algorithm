package com.tw ter.product_m xer.component_l brary.s de_effect. tr cs

 mport com.tw ter.cl entapp.thr ftscala.LogEvent
 mport com.tw ter.logp pel ne.cl ent.common.EventPubl s r
 mport com.tw ter.product_m xer.component_l brary.s de_effect.Scr beCl entEventS deEffect.EventNa space
 mport com.tw ter.product_m xer.core.model.common. dent f er.S deEffect dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.HasMarshall ng
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

/**
 * Bu ld [[Scr beCl entEvent tr csS deEffect]] w h extra [[EventConf g]]
 */
case class Scr beCl entEvent tr csS deEffectBu lder(
  eventConf gs: Seq[EventConf g] = Seq.empty) {

  /**
   * Append extra [[EventConf g]] to [[Scr beCl entEvent tr csS deEffectBu lder]]
   */
  def w hEventConf g(
    eventConf g: EventConf g
  ): Scr beCl entEvent tr csS deEffectBu lder =
    t .copy(eventConf gs = t .eventConf gs :+ eventConf g)

  /**
   * Bu ld [[EventConf g]] w h custom zed [[EventNa space]] and custom zed [[Cand date tr cFunct on]]
   * @param eventNa spaceOverr de Overr de t  default event na space  n [[Scr beCl entEvent tr csS deEffect]]
   * @param  tr cFunct on [[Cand date tr cFunct on]]
   */
  def w hEventConf g(
    eventNa spaceOverr de: EventNa space,
     tr cFunct on: Cand date tr cFunct on
  ): Scr beCl entEvent tr csS deEffectBu lder =
    w hEventConf g(EventConf g(eventNa spaceOverr de,  tr cFunct on))

  /**
   * Log served t ets events server s de and bu ld  tr cs  n t   tr c center.
   * Default event na  space act on  s "served_t ets", default  tr c funct on  s [[DefaultServedT etsSumFunct on]]
   * @param eventNa spaceOverr de Overr de t  default event na space  n [[Scr beCl entEvent tr csS deEffect]]
   * @param  tr cFunct on [[Cand date tr cFunct on]]
   */
  def w hServedT ets(
    eventNa spaceOverr de: EventNa space = EventNa space(act on = So ("served_t ets")),
     tr cFunct on: Cand date tr cFunct on = DefaultServedT etsSumFunct on
  ): Scr beCl entEvent tr csS deEffectBu lder = w hEventConf g(
    eventNa spaceOverr de = eventNa spaceOverr de,
     tr cFunct on =  tr cFunct on)

  /**
   * Log served users events server s de and bu ld  tr cs  n t   tr c center.
   * Default event na  space act on  s "served_users", default  tr c funct on  s [[DefaultServedUsersSumFunct on]]
   * @param eventNa spaceOverr de Overr de t  default event na space  n [[Scr beCl entEvent tr csS deEffect]]
   * @param  tr cFunct on [[Cand date tr cFunct on]]
   */
  def w hServedUsers(
    eventNa spaceOverr de: EventNa space = EventNa space(act on = So ("served_users")),
     tr cFunct on: Cand date tr cFunct on = DefaultServedUsersSumFunct on
  ): Scr beCl entEvent tr csS deEffectBu lder = w hEventConf g(
    eventNa spaceOverr de = eventNa spaceOverr de,
     tr cFunct on =  tr cFunct on)

  /**
   * Bu ld [[Scr beCl entEvent tr csS deEffect]]
   * @param  dent f er un que  dent f er of t  s de effect
   * @param defaultEventNa space default event na space to log
   * @param logP pel nePubl s r [[EventPubl s r]] to publ sh events
   * @param page T  page wh ch w ll be def ned  n t  na space. T   s typ cally t  serv ce na  that's scr b ng
   * @tparam Query [[P pel neQuery]]
   * @tparam UnmarshalledResponseType [[HasMarshall ng]]
   * @return [[Scr beCl entEvent tr csS deEffect]]
   */
  def bu ld[Query <: P pel neQuery, UnmarshalledResponseType <: HasMarshall ng](
     dent f er: S deEffect dent f er,
    defaultEventNa space: EventNa space,
    logP pel nePubl s r: EventPubl s r[LogEvent],
    page: Str ng
  ): Scr beCl entEvent tr csS deEffect[Query, UnmarshalledResponseType] = {
    new Scr beCl entEvent tr csS deEffect[Query, UnmarshalledResponseType](
       dent f er =  dent f er,
      logP pel nePubl s r = logP pel nePubl s r,
      defaultEventNa space = defaultEventNa space,
      page = page,
      eventConf gs = eventConf gs)
  }
}
