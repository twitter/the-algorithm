package com.tw ter.t etyp e.federated.columns

 mport com.tw ter.passb rd.b f eld.cl entpr v leges.thr ftscala.{Constants => Cl entAppPr v leges}
 mport com.tw ter.strato.access.Access.Aut nt catedTw terUserNotSuspended
 mport com.tw ter.strato.access.Access.Cl entAppl cat onPr v lege
 mport com.tw ter.strato.access.Access.Tw terUserNotSuspended
 mport com.tw ter.strato.access.Cl entAppl cat onPr v legeVar ant
 mport com.tw ter.strato.conf g._

object AccessPol cy {

  /**
   * All T et Mutat on operat ons requ re all of:
   *   - Tw ter user aut nt cat on
   *   - Tw ter user  s not suspended
   *   - Contr butor user,  f prov ded,  s not suspended
   *   - "Teams Access": user  s act ng t  r own behalf, or  s a
   *      contr butor us ng a cl ent w h Cl entAppPr v ledges.CONTR BUTORS
   *   - Wr e pr v leges
   */
  val T etMutat onCommonAccessPol c es: Pol cy =
    AllOf(
      Seq(
        AllowTw terUser d,
        Has(
          Tw terUserNotSuspended
        ),
        Has(
          Aut nt catedTw terUserNotSuspended
        ),
        AnyOf(
          Seq(
            Tw terUserContr but ngAsSelf,
            Has(pr nc pal = Cl entAppl cat onPr v lege(Cl entAppl cat onPr v legeVar ant
              .by d(Cl entAppPr v leges.CONTR BUTORS.toShort).get))
          )),
        AllowWr ableAccessToken
      )
    )

}
