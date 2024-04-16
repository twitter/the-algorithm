package com.tw ter.un f ed_user_act ons.serv ce

 mport com.tw ter.f natra.kafka.serde.ScalaSerdes
 mport com.tw ter.f natra.kafka.serde.UnKeyed
 mport com.tw ter.f natra.kafka.serde.UnKeyedSerde
 mport com.tw ter.f natra.kafka.test.EmbeddedKafka
 mport com.tw ter.f natra.kafkastreams.test.F natraTopologyTester
 mport com.tw ter.f natra.kafkastreams.test.TopologyFeatureTest
 mport com.tw ter.un f ed_user_act ons.enr c r.Enr c rF xture
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch ntEnvelop
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch nt dType
 mport com.tw ter.un f ed_user_act ons.enr c r. nternal.thr ftscala.Enr ch ntKey
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Un f edUserAct on
 mport org.apac .kafka.cl ents.consu r.Consu rRecord
 mport org.joda.t  .DateT  

/**
 * T   s to test t  log c w re t  serv ce reads and outputs to t  sa  Kafka cluster
 */
class Enr ch ntPlannerServ ceTest extends TopologyFeatureTest {
  val startT   = new DateT  ("2022-10-01T00:00:00Z")

  overr de protected lazy val topologyTester: F natraTopologyTester = F natraTopologyTester(
    "enr ch nt-planner-tester",
    new Enr ch ntPlannerServ ce,
    start ngWallClockT   = startT  ,
    flags = Map(
      "dec der.base" -> "/dec der.yml",
      "kafka.output.server" -> ""
    )
  )

  pr vate val  nputTop c = topologyTester.top c(
    na  = Enr ch ntPlannerServ ceMa n. nputTop c,
    keySerde = UnKeyedSerde,
    valSerde = ScalaSerdes.Thr ft[Un f edUserAct on]
  )

  pr vate val outputTop c = topologyTester.top c(
    na  = Enr ch ntPlannerServ ceMa n.OutputPart  onedTop c,
    keySerde = ScalaSerdes.Thr ft[Enr ch ntKey],
    valSerde = ScalaSerdes.Thr ft[Enr ch ntEnvelop]
  )

  test("can f lter unsupported events") {
    new Enr c rF xture {
      (1L to 10L).foreach( d => {
         nputTop c.p pe nput(UnKeyed, mkUUAProf leEvent( d))
      })

      assert(outputTop c.readAllOutput().s ze === 0)
    }
  }

  test("part  on key ser al zat on should be correct") {
    val key = Enr ch ntKey(Enr ch nt dType.T et d, 9999L)
    val ser al zer = ScalaSerdes.Thr ft[Enr ch ntKey].ser al zer

    val actual = ser al zer.ser al ze("test", key)
    val expected = Array[Byte](8, 0, 1, 0, 0, 0, 0, 10, 0, 2, 0, 0, 0, 0, 0, 0, 39, 15, 0)

    assert(actual.deep === expected.deep)
  }

  test("part  oned enr ch nt t et event  s constructed correctly") {
    new Enr c rF xture {
      val expected = mkUUAT etEvent(888L)
       nputTop c.p pe nput(UnKeyed, expected)

      val actual = outputTop c.readAllOutput(). ad

      assert(actual.key() === Enr ch ntKey(Enr ch nt dType.T et d, 888L))
      assert(
        actual
          .value() === Enr ch ntEnvelop(
          expected.hashCode,
          expected,
          plan = t et nfoEnr ch ntPlan
        ))
    }
  }

  test("part  oned enr ch nt t et not f cat on event  s constructed correctly") {
    new Enr c rF xture {
      val expected = mkUUAT etNot f cat onEvent(8989L)
       nputTop c.p pe nput(UnKeyed, expected)

      val actual = outputTop c.readAllOutput(). ad

      assert(actual.key() === Enr ch ntKey(Enr ch nt dType.T et d, 8989L))
      assert(
        actual
          .value() === Enr ch ntEnvelop(
          expected.hashCode,
          expected,
          plan = t etNot f cat onEnr ch ntPlan
        ))
    }
  }
}

/**
 * T   s tests t  bootstrap server log c  n prod. Don't add any new tests  re s nce    s slow.
 * Use t  tests above wh ch  s much qu cker to be executed and and test t  major y of prod log c.
 */
class Enr ch ntPlannerServ ceEmbeddedKafkaTest extends TopologyFeatureTest w h EmbeddedKafka {
  val startT   = new DateT  ("2022-10-01T00:00:00Z")

  overr de protected lazy val topologyTester: F natraTopologyTester = F natraTopologyTester(
    "enr ch nt-planner-tester",
    new Enr ch ntPlannerServ ce,
    start ngWallClockT   = startT  ,
    flags = Map(
      "dec der.base" -> "/dec der.yml",
      "kafka.output.server" -> kafkaCluster.bootstrapServers(),
      "kafka.output.enable.tls" -> "false"
    )
  )

  pr vate lazy val  nputTop c = topologyTester.top c(
    na  = Enr ch ntPlannerServ ceMa n. nputTop c,
    keySerde = UnKeyedSerde,
    valSerde = ScalaSerdes.Thr ft[Un f edUserAct on]
  )

  pr vate val outputTop c = kafkaTop c(
    na  = Enr ch ntPlannerServ ceMa n.OutputPart  onedTop c,
    keySerde = ScalaSerdes.Thr ft[Enr ch ntKey],
    valSerde = ScalaSerdes.Thr ft[Enr ch ntEnvelop]
  )

  test("toCluster should output to expected top c & embeded cluster") {
    new Enr c rF xture {
       nputTop c.p pe nput(UnKeyed, mkUUAT etEvent(t et d = 1))
      val records: Seq[Consu rRecord[Array[Byte], Array[Byte]]] = outputTop c.consu Records(1)

      assert(records.s ze === 1)
      assert(records. ad.top c() == Enr ch ntPlannerServ ceMa n.OutputPart  onedTop c)
    }
  }
}
