package com.tw ter.t etyp e
package hydrator

/**
 * Ret ets should never have t  r own  d a, and should never be cac d w h a  d a
 * ent y.
 */
object Ret et d aRepa rer extends Mutat on[T et] {
  def apply(t et: T et): Opt on[T et] = {
     f ( sRet et(t et) && get d a(t et).nonEmpty)
      So (T etLenses. d a.set(t et, N l))
    else
      None
  }
}
