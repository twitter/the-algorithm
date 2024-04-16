package com.tw ter.v s b l y. nterfaces

 mport com.tw ter.servo.repos ory.KeyValueRepos ory
 mport com.tw ter.spam.rtf.thr ftscala.SafetyLabel
 mport com.tw ter.spam.rtf.thr ftscala.SafetyLabelType
 mport scala.collect on.Map

package object conversat ons {
  type BatchSafetyLabelRepos ory =
    KeyValueRepos ory[(Long, Seq[Long]), Long, Map[SafetyLabelType, SafetyLabel]]
}
