package com.tw ter.un f ed_user_act ons.kafka.serde

 mport com.tw ter.f nagle.stats. n moryStatsRece ver
 mport com.tw ter. nject.Test
 mport com.tw ter.un f ed_user_act ons.thr ftscala._

class NullableScalaSerdesSpec extends Test {
  val counter = (new  n moryStatsRece ver).counter("nullCounts")
  val nullableDeser al zer = NullableScalaSerdes.Thr ft[Un f edUserAct onSpec](counter).deser al zer
  val ser al zer = NullableScalaSerdes.Thr ft[Un f edUserAct onSpec]().ser al zer
  val uua = Un f edUserAct onSpec(
    user d = 1L,
    payload = So ("test"),
  )

  test("serde") {
    nullableDeser al zer.deser al ze("", ser al zer.ser al ze("", uua)) should be(uua)
    nullableDeser al zer.deser al ze("", "Whatever".getBytes) should be(
      null.as nstanceOf[Un f edUserAct onSpec])
    counter.apply() should equal(1)
  }

  test("rate l m ed logger w n t re's an except on") {
    for (_ <- 1 to 10) {
      nullableDeser al zer.deser al ze("", "Whatever".getBytes) should be(
        null.as nstanceOf[Un f edUserAct onSpec])
    }

    TestLogAppender.events.s ze should (be(1) or be(2))
    counter.apply() should equal(11)
  }
}
