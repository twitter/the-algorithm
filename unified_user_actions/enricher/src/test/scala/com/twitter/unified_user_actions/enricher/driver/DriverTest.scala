package com.tw ter.un f ed_user_act ons.enr c r.dr ver

 mport com.tw ter. nject.Test
 mport com.tw ter.un f ed_user_act ons.enr c r.Enr c rF xture
 mport com.tw ter.un f ed_user_act ons.enr c r.hydrator.Hydrator
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch ntEnvelop
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch nt dType
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch nt nstruct on
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch ntKey
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch ntPlan
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch ntStage
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch ntStageStatus
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch ntStageType
 mport com.tw ter.un f ed_user_act ons.enr c r.part  oner.Part  oner
 mport com.tw ter.ut l.Awa 
 mport com.tw ter.ut l.Future
 mport org.scalatest.BeforeAndAfter
 mport org.scalatest.matc rs.should.Matc rs
 mport scala.collect on.mutable

class Dr verTest extends Test w h Matc rs w h BeforeAndAfter {
  object Execut onContext {
    var execut onCount = 0
  }

  before {
    Execut onContext.execut onCount = 0
  }

  tra  F xtures extends Enr c rF xture {
    val repart  onT et = mkStage()
    val repart  onNot T et =
      mkStage( nstruct ons = Seq(Enr ch nt nstruct on.Not f cat onT etEnr ch nt))
    val hydrateT et = mkStage(stageType = Enr ch ntStageType.Hydrat on)
    val hydrateT etMult  nstruct ons = mkStage(
      stageType = Enr ch ntStageType.Hydrat on,
       nstruct ons = Seq(
        Enr ch nt nstruct on.Not f cat onT etEnr ch nt,
        Enr ch nt nstruct on.T etEnr ch nt,
        Enr ch nt nstruct on.Not f cat onT etEnr ch nt,
        Enr ch nt nstruct on.T etEnr ch nt
      )
    )
    val hydrateNot T et = mkStage(
      stageType = Enr ch ntStageType.Hydrat on,
       nstruct ons = Seq(Enr ch nt nstruct on.Not f cat onT etEnr ch nt))
    val key1 = Enr ch ntKey(Enr ch nt dType.T et d, 123L)
    val t et1 = mkUUAT etEvent(981L)
    val hydrator = new MockHydrator
    val part  oner = new MockPart  oner
    val outputTop c = "output"
    val part  onTop c = "part  on"

    def complete(
      enr ch ntStage: Enr ch ntStage,
      outputTop c: Opt on[Str ng] = None
    ): Enr ch ntStage = {
      enr ch ntStage.copy(status = Enr ch ntStageStatus.Complet on, outputTop c = outputTop c)
    }

    def mkPlan(enr ch ntStages: Enr ch ntStage*): Enr ch ntPlan = {
      Enr ch ntPlan(enr ch ntStages)
    }

    def mkStage(
      status: Enr ch ntStageStatus = Enr ch ntStageStatus. n  al zed,
      stageType: Enr ch ntStageType = Enr ch ntStageType.Repart  on,
       nstruct ons: Seq[Enr ch nt nstruct on] = Seq(Enr ch nt nstruct on.T etEnr ch nt)
    ): Enr ch ntStage = {
      Enr ch ntStage(status, stageType,  nstruct ons)
    }

    tra  Execut onCount {
      val callMap: mutable.Map[ nt, (Enr ch nt nstruct on, Enr ch ntEnvelop)] =
        mutable.Map[ nt, (Enr ch nt nstruct on, Enr ch ntEnvelop)]()

      def recordExecut on( nstruct on: Enr ch nt nstruct on, envelop: Enr ch ntEnvelop): Un  = {
        Execut onContext.execut onCount = Execut onContext.execut onCount + 1
        callMap.put(Execut onContext.execut onCount, ( nstruct on, envelop))
      }
    }

    class MockHydrator extends Hydrator w h Execut onCount {
      def hydrate(
         nstruct on: Enr ch nt nstruct on,
        key: Opt on[Enr ch ntKey],
        envelop: Enr ch ntEnvelop
      ): Future[Enr ch ntEnvelop] = {
        recordExecut on( nstruct on, envelop)
        Future(envelop.copy(envelop d = Execut onContext.execut onCount))
      }
    }

    class MockPart  oner extends Part  oner w h Execut onCount {
      def repart  on(
         nstruct on: Enr ch nt nstruct on,
        envelop: Enr ch ntEnvelop
      ): Opt on[Enr ch ntKey] = {
        recordExecut on( nstruct on, envelop)
        So (Enr ch ntKey(Enr ch nt dType.T et d, Execut onContext.execut onCount))
      }
    }
  }

  test("s ngle part  on ng plan works") {
    new F xtures {
      val dr ver = new Enr ch ntDr ver(So (outputTop c), part  onTop c, hydrator, part  oner)
      // g ven a s mple plan that only repart  on t   nput and noth ng else
      val plan = mkPlan(repart  onT et)

      (1L to 10).foreach( d => {
        val envelop = Enr ch ntEnvelop( d, t et1, plan)

        // w n
        val actual = Awa .result(dr ver.execute(So (key1), Future(envelop)))

        val expectedKey = So (key1.copy( d =  d))
        val expectedValue =
          envelop.copy(plan = mkPlan(complete(repart  onT et, So (part  onTop c))))

        // t n t  result should have a new part  oned key, w h t  envelop unchanged except t  plan  s complete
        // ho ver, t  output top c  s t  part  onTop c (s nce t   s only a part  on ng stage)
        assert((expectedKey, expectedValue) == actual)
      })
    }
  }

  test("mult -stage part  on ng plan works") {
    new F xtures {
      val dr ver = new Enr ch ntDr ver(So (outputTop c), part  onTop c, hydrator, part  oner)
      // g ven a plan that cha n mult ple repart  on stages toget r
      val plan = mkPlan(repart  onT et, repart  onNot T et)
      val envelop1 = Enr ch ntEnvelop(1L, t et1, plan)

      // w n 1st part  on ng tr p
      val actual1 = Awa .result(dr ver.execute(So (key1), Future(envelop1)))

      // t n t  result should have a new part  oned key, w h t  envelop unchanged except t 
      // 1st stage of t  plan  s complete
      val expectedKey1 = key1.copy( d = 1L)
      val expectedValue1 =
        envelop1.copy(plan =
          mkPlan(complete(repart  onT et, So (part  onTop c)), repart  onNot T et))

      assert((So (expectedKey1), expectedValue1) == actual1)

      // t n,   reuse t  last result to exerc se t  log cs on t  dr ver aga n for t  2st tr p
      val actual2 = Awa .result(dr ver.execute(So (expectedKey1), Future(expectedValue1)))
      val expectedKey2 = key1.copy( d = 2L)
      val expectedValue2 =
        envelop1.copy(plan = mkPlan(
          complete(repart  onT et, So (part  onTop c)),
          complete(repart  onNot T et, So (part  onTop c))))

      assert((So (expectedKey2), expectedValue2) == actual2)
    }
  }

  test("s ngle hydrat on plan works") {
    new F xtures {
      val dr ver = new Enr ch ntDr ver(So (outputTop c), part  onTop c, hydrator, part  oner)
      // g ven a s mple plan that only hydrate t   nput and noth ng else
      val plan = mkPlan(hydrateT et)

      (1L to 10).foreach( d => {
        val envelop = Enr ch ntEnvelop( d, t et1, plan)

        // w n
        val actual = Awa .result(dr ver.execute(So (key1), Future(envelop)))

        val expectedValue =
          envelop.copy(envelop d =  d, plan = mkPlan(complete(hydrateT et, So (outputTop c))))

        // t n t  result should have t  sa  key, w h t  envelop hydrated & t  plan  s complete
        // t  output top c should be t  f nal top c s nce t   s a hydrat on stage and t  plan  s complete
        assert((So (key1), expectedValue) == actual)
      })
    }
  }

  test("s ngle hydrat on w h mult ple  nstruct ons plan works") {
    new F xtures {
      val dr ver = new Enr ch ntDr ver(So (outputTop c), part  onTop c, hydrator, part  oner)
      // g ven a s mple plan that only hydrate t   nput and noth ng else
      val plan = mkPlan(hydrateT etMult  nstruct ons)
      val envelop = Enr ch ntEnvelop(0L, t et1, plan)

      // w n
      val actual = Awa .result(dr ver.execute(So (key1), Future(envelop)))
      val expectedValue = envelop.copy(
        envelop d = 4L, // hydrate  s called 4 t  s for 4  nstruct ons  n 1 stage
        plan = mkPlan(complete(hydrateT etMult  nstruct ons, So (outputTop c))))

      // t n t  result should have t  sa  key, w h t  envelop hydrated & t  plan  s complete
      // t  output top c should be t  f nal top c s nce t   s a hydrat on stage and t  plan  s complete
      assert((So (key1), expectedValue) == actual)
    }
  }

  test("mult -stage hydrat on plan works") {
    new F xtures {
      val dr ver = new Enr ch ntDr ver(So (outputTop c), part  onTop c, hydrator, part  oner)
      // g ven a plan that only hydrate tw ce
      val plan = mkPlan(hydrateT et, hydrateNot T et)
      val envelop = Enr ch ntEnvelop(1L, t et1, plan)

      // w n
      val actual = Awa .result(dr ver.execute(So (key1), Future(envelop)))

      // t n t  result should have t  sa  key, w h t  envelop hydrated. s nce t re's no
      // part  on ng stages, t  dr ver w ll just recurse unt l all t  hydrat on  s done,
      // t n output to t  f nal top c
      val expectedValue =
        envelop.copy(
          envelop d = 2L,
          plan = mkPlan(
            complete(hydrateT et),
            complete(
              hydrateNot T et,
              So (outputTop c)
            ) // only t  last stage has t  output top c
          ))

      assert((So (key1), expectedValue) == actual)
    }
  }

  test("mult -stage part  on+hydrat on plan works") {
    new F xtures {
      val dr ver = new Enr ch ntDr ver(So (outputTop c), part  onTop c, hydrator, part  oner)

      // g ven a plan that repart  on t n hydrate tw ce
      val plan = mkPlan(repart  onT et, hydrateT et, repart  onNot T et, hydrateNot T et)
      var curEnvelop = Enr ch ntEnvelop(1L, t et1, plan)
      var curKey = key1

      // stage 1, part  on ng on t et should be correct
      var actual = Awa .result(dr ver.execute(So (curKey), Future(curEnvelop)))
      var expectedKey = curKey.copy( d = 1L)
      var expectedValue = curEnvelop.copy(
        plan = mkPlan(
          complete(repart  onT et, So (part  onTop c)),
          hydrateT et,
          repart  onNot T et,
          hydrateNot T et))

      assert((So (expectedKey), expectedValue) == actual)
      curEnvelop = actual._2
      curKey = actual._1.get

      // stage 2-3, hydrat ng on t et should be correct
      // and s nce t  next stage after hydrat on  s a repart  on,   w ll does so correctly
      actual = Awa .result(dr ver.execute(So (curKey), Future(curEnvelop)))
      expectedKey = curKey.copy( d = 3) // repart  on  s done  n stage 3
      expectedValue = curEnvelop.copy(
        envelop d = 2L, // hydrat on  s done  n stage 2
        plan = mkPlan(
          complete(repart  onT et, So (part  onTop c)),
          complete(hydrateT et),
          complete(repart  onNot T et, So (part  onTop c)),
          hydrateNot T et)
      )

      assert((So (expectedKey), expectedValue) == actual)
      curEnvelop = actual._2
      curKey = actual._1.get

      // t n f nally, stage 4 would output to t  f nal top c
      actual = Awa .result(dr ver.execute(So (curKey), Future(curEnvelop)))
      expectedKey = curKey // noth ng's changed  n t  key
      expectedValue = curEnvelop.copy(
        envelop d = 4L,
        plan = mkPlan(
          complete(repart  onT et, So (part  onTop c)),
          complete(hydrateT et),
          complete(repart  onNot T et, So (part  onTop c)),
          complete(hydrateNot T et, So (outputTop c))
        )
      )

      assert((So (expectedKey), expectedValue) == actual)
    }
  }
}
