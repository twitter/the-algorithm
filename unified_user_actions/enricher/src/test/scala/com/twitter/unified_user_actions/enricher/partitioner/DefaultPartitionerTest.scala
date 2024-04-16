package com.tw ter.un f ed_user_act ons.enr c r.part  oner

 mport com.tw ter. nject.Test
 mport com.tw ter.un f ed_user_act ons.enr c r.Enr c rF xture
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch ntEnvelop
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch nt dType
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch nt nstruct on
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch nt nstruct on.Not f cat onT etEnr ch nt
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch nt nstruct on.T etEnr ch nt
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch ntKey
 mport com.tw ter.un f ed_user_act ons.enr c r.part  oner.DefaultPart  oner.NullKey
 mport org.scalatest.prop.TableDr venPropertyC cks

class DefaultPart  onerTest extends Test w h TableDr venPropertyC cks {
  test("default part  oner should work") {
    new Enr c rF xture {
      val part  oner = new DefaultPart  oner

      val  nstruct ons = Table(
        (" nstruct on", "envelop", "expected"),
        // t et  nfo
        (
          T etEnr ch nt,
          Enr ch ntEnvelop(1L, mkUUAT etEvent(123L), t et nfoEnr ch ntPlan),
          So (Enr ch ntKey(Enr ch nt dType.T et d, 123L))),
        // not f cat on t et  nfo
        (
          Not f cat onT etEnr ch nt,
          Enr ch ntEnvelop(2L, mkUUAT etNot f cat onEvent(234L), t etNot f cat onEnr ch ntPlan),
          So (Enr ch ntKey(Enr ch nt dType.T et d, 234L))),
        // not f cat on w h mult ple t et  nfo
        (
          Not f cat onT etEnr ch nt,
          Enr ch ntEnvelop(
            3L,
            mkUUAMult T etNot f cat onEvent(22L, 33L),
            t etNot f cat onEnr ch ntPlan),
          So (Enr ch ntKey(Enr ch nt dType.T et d, 22L))
        ) // only t  f rst t et  d  s part  oned
      )

      forEvery( nstruct ons) {
        (
           nstruct on: Enr ch nt nstruct on,
          envelop: Enr ch ntEnvelop,
          expected: So [Enr ch ntKey]
        ) =>
          val actual = part  oner.repart  on( nstruct on, envelop)
          assert(expected === actual)
      }
    }
  }

  test("unsupported events shouldn't be part  oned") {
    new Enr c rF xture {
      val part  oner = new DefaultPart  oner

      val  nstruct ons = Table(
        (" nstruct on", "envelop", "expected"),
        // prof le uua event
        (
          T etEnr ch nt,
          Enr ch ntEnvelop(1L, mkUUAProf leEvent(111L), t et nfoEnr ch ntPlan),
          NullKey),
        // unknown not f cat on (not a t et)
        (
          Not f cat onT etEnr ch nt,
          Enr ch ntEnvelop(1L, mkUUAT etNot f cat onUnknownEvent(), t et nfoEnr ch ntPlan),
          NullKey),
      )

      forEvery( nstruct ons) {
        (
           nstruct on: Enr ch nt nstruct on,
          envelop: Enr ch ntEnvelop,
          expected: Opt on[Enr ch ntKey]
        ) =>
          val actual = part  oner.repart  on( nstruct on, envelop)
          assert(expected === actual)
      }
    }
  }
}
