package com.tw ter.servo.repos ory

 mport scala.collect on.SeqProxy

/**
 * R chQuery  s a m x n tra  for KeyValueRepos ory query objects that are more complex
 * than Seq[K].   extends SeqProxy to sat sfy servo's requ re nts but prov des Product-based
 *  mple ntat ons of equals and toStr ng. (T  query object  s expected to be a case class
 * and t refore  mple nt Product.)
 */
tra  R chQuery[K] extends SeqProxy[K] w h Product {
  // Compare to ot r R chQuery  nstances v a Product; ot rw se allow any sequence to
  // match   prox ed Seq (t reby match ng t  semant cs of a case class that s mply
  // extends SeqProxy).
  overr de def equals(any: Any) = {
    any match {
      case null => false

      case ot r: R chQuery[_] =>
        (
          t .productAr y == ot r.productAr y &&
            t .product erator.z p(ot r.product erator).foldLeft(true) {
              case (ok, (e1, e2)) =>
                ok && e1 == e2
            }
        )

      case ot r => ot r.equals(t )
    }
  }

  // Produce reasonable str ng for test ng
  overr de def toStr ng = "%s(%s)".format(t .productPref x, t .product erator.mkStr ng(","))
}
