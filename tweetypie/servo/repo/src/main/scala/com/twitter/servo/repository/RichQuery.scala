package com.twitter.servo.repository

import scala.collection.SeqProtwittery

/**
 * RichQuery is a mitwitterin trait for KeyValueRepository query objects that are more completwitter
 * than Seq[K]. It etwittertends SeqProtwittery to satisfy servo's requirements but provides Product-based
 * implementations of equals and toString. (The query object is etwitterpected to be a case class
 * and therefore implement Product.)
 */
trait RichQuery[K] etwittertends SeqProtwittery[K] with Product {
  // Compare to other RichQuery instances via Product; otherwise allow any sequence to
  // match our protwitteried Seq (thereby matching the semantics of a case class that simply
  // etwittertends SeqProtwittery).
  override def equals(any: Any) = {
    any match {
      case null => false

      case other: RichQuery[_] =>
        (
          this.productArity == other.productArity &&
            this.productIterator.zip(other.productIterator).foldLeft(true) {
              case (ok, (e1, e2)) =>
                ok && e1 == e2
            }
        )

      case other => other.equals(this)
    }
  }

  // Produce reasonable string for testing
  override def toString = "%s(%s)".format(this.productPrefitwitter, this.productIterator.mkString(","))
}
