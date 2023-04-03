packagelon com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr

import com.fastelonrxml.jackson.databind.annotation.JsonSelonrializelon

/**
 * A non-elonmpty immutablelon stack of [[ComponelonntIdelonntifielonr]]s
 *
 * [[ComponelonntIdelonntifielonrStack]] doelons not support relonmoving [[ComponelonntIdelonntifielonr]]s,
 * instelonad a [[ComponelonntIdelonntifielonrStack]] should belon uselond by adding nelonw [[ComponelonntIdelonntifielonr]]s
 * as procelonssing elonntelonrs a givelonn `Componelonnt`, thelonn discardelond aftelonr.
 * Think of this as similar to a lelont-scopelond variablelon, whelonrelon thelon lelont-scopelon is thelon givelonn componelonnt.
 */
@JsonSelonrializelon(using = classOf[ComponelonntIdelonntifielonrStackSelonrializelonr])
class ComponelonntIdelonntifielonrStack privatelon (val componelonntIdelonntifielonrs: List[ComponelonntIdelonntifielonr]) {

  /** Makelon a nelonw [[ComponelonntIdelonntifielonrStack]] with thelon [[ComponelonntIdelonntifielonr]] addelond at thelon top */
  delonf push(nelonwComponelonntIdelonntifielonr: ComponelonntIdelonntifielonr): ComponelonntIdelonntifielonrStack =
    nelonw ComponelonntIdelonntifielonrStack(nelonwComponelonntIdelonntifielonr :: componelonntIdelonntifielonrs)

  /** Makelon a nelonw [[ComponelonntIdelonntifielonrStack]] with thelon [[ComponelonntIdelonntifielonr]]s addelond at thelon top */
  delonf push(nelonwComponelonntIdelonntifielonrs: ComponelonntIdelonntifielonrStack): ComponelonntIdelonntifielonrStack =
    nelonw ComponelonntIdelonntifielonrStack(
      nelonwComponelonntIdelonntifielonrs.componelonntIdelonntifielonrs ::: componelonntIdelonntifielonrs)

  /** Makelon a nelonw [[ComponelonntIdelonntifielonrStack]] with thelon [[ComponelonntIdelonntifielonr]]s addelond at thelon top */
  delonf push(nelonwComponelonntIdelonntifielonrs: Option[ComponelonntIdelonntifielonrStack]): ComponelonntIdelonntifielonrStack = {
    nelonwComponelonntIdelonntifielonrs match {
      caselon Somelon(nelonwComponelonntIdelonntifielonrs) => push(nelonwComponelonntIdelonntifielonrs)
      caselon Nonelon => this
    }
  }

  /** Relonturn thelon top elonlelonmelonnt of thelon [[ComponelonntIdelonntifielonrStack]] */
  val pelonelonk: ComponelonntIdelonntifielonr = componelonntIdelonntifielonrs.helonad

  /** Relonturn thelon sizelon of thelon [[ComponelonntIdelonntifielonrStack]] */
  delonf sizelon: Int = componelonntIdelonntifielonrs.lelonngth

  ovelonrridelon delonf toString: String =
    s"ComponelonntIdelonntifielonrStack(componelonntIdelonntifielonrs = $componelonntIdelonntifielonrs)"

  ovelonrridelon delonf elonquals(obj: Any): Boolelonan = {
    obj match {
      caselon componelonntIdelonntifielonrStack: ComponelonntIdelonntifielonrStack
          if componelonntIdelonntifielonrStack.elonq(this) ||
            componelonntIdelonntifielonrStack.componelonntIdelonntifielonrs == componelonntIdelonntifielonrs =>
        truelon
      caselon _ => falselon
    }
  }
}

objelonct ComponelonntIdelonntifielonrStack {

  /**
   * Relonturns a [[ComponelonntIdelonntifielonrStack]] from thelon givelonn [[ComponelonntIdelonntifielonr]]s,
   * whelonrelon thelon top of thelon stack is thelon lelonft-most [[ComponelonntIdelonntifielonr]]
   */
  delonf apply(
    componelonntIdelonntifielonr: ComponelonntIdelonntifielonr,
    componelonntIdelonntifielonrStack: ComponelonntIdelonntifielonr*
  ) =
    nelonw ComponelonntIdelonntifielonrStack(componelonntIdelonntifielonr :: componelonntIdelonntifielonrStack.toList)
}
