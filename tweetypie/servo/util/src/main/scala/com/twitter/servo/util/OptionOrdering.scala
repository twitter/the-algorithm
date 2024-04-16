package com.tw ter.servo.ut l

object Opt onOrder ng {

  /**
   * Creates an Order ng of Opt on objects.  Nones are ordered before So s, and two So s
   * are ordered accord ng to t  g ven value order ng.
   */
  def apply[A](valueOrder ng: Order ng[A]) = new Order ng[Opt on[A]] {
    // Nones before So s, for two So s, use valueOrder ng
    def compare(x: Opt on[A], y: Opt on[A]):  nt = {
      x match {
        case None =>  f (y.nonEmpty) -1 else 0
        case So (xValue) =>
          y match {
            case None => 1
            case So (yValue) => valueOrder ng.compare(xValue, yValue)
          }
      }
    }
  }
}
