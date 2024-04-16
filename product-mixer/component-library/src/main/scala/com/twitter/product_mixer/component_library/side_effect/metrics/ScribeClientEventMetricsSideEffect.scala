package com.tw ter.product_m xer.component_l brary.s de_effect. tr cs

 mport com.tw ter.cl entapp.thr ftscala.LogEvent
 mport com.tw ter.logp pel ne.cl ent.common.EventPubl s r
 mport com.tw ter.product_m xer.component_l brary.s de_effect.Scr beCl entEventS deEffect
 mport com.tw ter.product_m xer.component_l brary.s de_effect.Scr beCl entEventS deEffect.EventNa space
 mport com.tw ter.product_m xer.component_l brary.s de_effect.Scr beCl entEventS deEffect.Cl entEvent
 mport com.tw ter.product_m xer.core.model.common. dent f er.S deEffect dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.marshall ng.HasMarshall ng
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

/**
 * Conf g of a cl ent event to be scr bed under certa n na space.
 * @param eventNa spaceOverr de overr des t  default eventNa space  n t  s de effect.
 *                               Note that  s f elds (sect on/component/ele nt/act on) w ll
 *                               overr de t  default na space f elds only  f t  f elds are not
 *                               None.  .e.  f   want to overr de t  "sect on" f eld  n t 
 *                               default na space w h an empty sect on,   must spec fy
 *                                  sect on = So ("")
 *                                n t  overr de  nstead of
 *                                  sect on = None
 *
 * @param  tr cFunct on t  funct on that extracts t   tr c value from a cand date.
 */
case class EventConf g(
  eventNa spaceOverr de: EventNa space,
   tr cFunct on: Cand date tr cFunct on)

/**
 * S de effect to log cl ent events server-s de and to bu ld  tr cs  n t   tr c center.
 * By default w ll return "requests" event conf g.
 */
class Scr beCl entEvent tr csS deEffect[
  Query <: P pel neQuery,
  UnmarshalledResponseType <: HasMarshall ng
](
  overr de val  dent f er: S deEffect dent f er,
  overr de val logP pel nePubl s r: EventPubl s r[LogEvent],
  overr de val page: Str ng,
  defaultEventNa space: EventNa space,
  eventConf gs: Seq[EventConf g])
    extends Scr beCl entEventS deEffect[Query, UnmarshalledResponseType] {

  overr de def bu ldCl entEvents(
    query: Query,
    selectedCand dates: Seq[Cand dateW hDeta ls],
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    droppedCand dates: Seq[Cand dateW hDeta ls],
    response: UnmarshalledResponseType
  ): Seq[Scr beCl entEventS deEffect.Cl entEvent] = {
    // count t  number of cl ent events of type "requests"
    val requestCl entEvent = Cl entEvent(
      na space = bu ldEventNa space(EventNa space(act on = So ("requests")))
    )

    eventConf gs
      .map { conf g =>
        Cl entEvent(
          na space = bu ldEventNa space(conf g.eventNa spaceOverr de),
          eventValue = So (selectedCand dates.map(conf g. tr cFunct on(_)).sum))
      }
      // scr be cl ent event only w n t   tr c sum  s non-zero
      .f lter(cl entEvent => cl entEvent.eventValue.ex sts(_ > 0L)) :+ requestCl entEvent
  }

  pr vate def bu ldEventNa space(eventNa spaceOverr de: EventNa space): EventNa space =
    EventNa space(
      sect on = eventNa spaceOverr de.sect on.orElse(defaultEventNa space.sect on),
      component = eventNa spaceOverr de.component.orElse(defaultEventNa space.component),
      ele nt = eventNa spaceOverr de.ele nt.orElse(defaultEventNa space.ele nt),
      act on = eventNa spaceOverr de.act on.orElse(defaultEventNa space.act on)
    )
}
