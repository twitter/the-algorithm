package com.tw ter.product_m xer.component_l brary.cand date_s ce.flex ble_ nject on_p pel ne

 mport com.tw ter. nject.Logg ng
 mport com.tw ter.onboard ng. nject ons.{thr ftscala =>  nject onsthr ft}
 mport com.tw ter.onboard ng.task.serv ce.{thr ftscala => serv cethr ft}
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.st ch.St ch
 mport javax. nject. nject
 mport javax. nject.S ngleton

/**
 * Returns a l st of prompts to  nsert  nto a user's t  l ne ( nl ne prompt, cover modals, etc)
 * from go/fl p (t  prompt ng platform for Tw ter).
 */
@S ngleton
class PromptCand dateS ce @ nject() (taskServ ce: serv cethr ft.TaskServ ce. thodPerEndpo nt)
    extends Cand dateS ce[serv cethr ft.Get nject onsRequest,  nter d atePrompt]
    w h Logg ng {

  overr de val  dent f er: Cand dateS ce dent f er = Cand dateS ce dent f er(
    " nject onP pel nePrompts")

  overr de def apply(
    request: serv cethr ft.Get nject onsRequest
  ): St ch[Seq[ nter d atePrompt]] = {
    St ch
      .callFuture(taskServ ce.get nject ons(request)).map {
        _. nject ons.flatMap {
          // T  ent re carousel  s gett ng added to each  nter d atePrompt  em w h a
          // correspond ng  ndex to be unpacked later on to populate  s T  l neEntry counterpart.
          case  nject on:  nject onsthr ft. nject on.T lesCarousel =>
             nject on.t lesCarousel.t les.z pW h ndex.map {
              case (t le:  nject onsthr ft.T le,  ndex:  nt) =>
                 nter d atePrompt( nject on, So ( ndex), So (t le))
            }
          case  nject on => Seq( nter d atePrompt( nject on, None, None))
        }
      }
  }
}

/**
 * G ves an  nter d ate step to  lp 'explos on' of t le carousel t les due to T  l neModule
 * not be ng an extens on of T  l ne em
 */
case class  nter d atePrompt(
   nject on:  nject onsthr ft. nject on,
  offset nModule: Opt on[ nt],
  carouselT le: Opt on[ nject onsthr ft.T le])
