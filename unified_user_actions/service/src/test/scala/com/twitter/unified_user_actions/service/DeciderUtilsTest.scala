package com.tw ter.un f ed_user_act ons.serv ce

 mport com.tw ter.dec der.MockDec der
 mport com.tw ter. nject.Test
 mport com.tw ter.un f ed_user_act ons.serv ce.module.Cl entEventDec derUt ls
 mport com.tw ter.un f ed_user_act ons.serv ce.module.DefaultDec derUt ls
 mport com.tw ter.un f ed_user_act ons.thr ftscala._
 mport com.tw ter.ut l.T  
 mport com.tw ter.ut l.mock.Mock o
 mport org.jun .runner.RunW h
 mport org.scalatestplus.jun .JUn Runner

@RunW h(classOf[JUn Runner])
class Dec derUt lsTest extends Test w h Mock o {
  tra  F xture {
    val frozenT   = T  .fromM ll seconds(1658949273000L)

    val publ shAct onTypes =
      Set[Act onType](Act onType.ServerT etFav, Act onType.Cl entT etRender mpress on)

    def dec der(
      features: Set[Str ng] = publ shAct onTypes.map { act on =>
        s"Publ sh${act on.na }"
      }
    ) = new MockDec der(features = features)

    def mkUUA(act onType: Act onType) = Un f edUserAct on(
      user dent f er = User dent f er(user d = So (91L)),
       em =  em.T et nfo(
        T et nfo(
          act onT et d = 1L,
          act onT etAuthor nfo = So (Author nfo(author d = So (101L))),
        )
      ),
      act onType = act onType,
      event tadata = Event tadata(
        s ceT  stampMs = 1001L,
        rece vedT  stampMs = frozenT  . nM ll seconds,
        s ceL neage = S ceL neage.ServerTlsFavs,
        trace d = So (31L)
      )
    )

    val uuaServerT etFav = mkUUA(Act onType.ServerT etFav)
    val uuaCl entT etFav = mkUUA(Act onType.Cl entT etFav)
    val uuaCl entT etRender mpress on = mkUUA(Act onType.Cl entT etRender mpress on)
  }

  test("Dec der Ut ls") {
    new F xture {
      T  .w hT  At(frozenT  ) { _ =>
        DefaultDec derUt ls.shouldPubl sh(
          dec der = dec der(),
          uua = uuaServerT etFav,
          s nkTop c = "") shouldBe true
        DefaultDec derUt ls.shouldPubl sh(
          dec der = dec der(),
          uua = uuaCl entT etFav,
          s nkTop c = "") shouldBe false
        Cl entEventDec derUt ls.shouldPubl sh(
          dec der = dec der(),
          uua = uuaCl entT etRender mpress on,
          s nkTop c = "un f ed_user_act ons_engage nts") shouldBe false
        Cl entEventDec derUt ls.shouldPubl sh(
          dec der = dec der(),
          uua = uuaCl entT etFav,
          s nkTop c = "un f ed_user_act ons_engage nts") shouldBe false
        Cl entEventDec derUt ls.shouldPubl sh(
          dec der = dec der(features = Set[Str ng](s"Publ sh${Act onType.Cl entT etFav.na }")),
          uua = uuaCl entT etFav,
          s nkTop c = "un f ed_user_act ons_engage nts") shouldBe true
      }
    }
  }
}
