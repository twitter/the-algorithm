package com.tw ter.servo.ut l

 mport com.tw ter.ut l.{Return, Throw, Try}

object TryOrder ng {

  /**
   * Creates an Order ng of Try objects.  Throws are ordered before Returns, and two Returns
   * are ordered accord ng to t  g ven value order ng.
   */
  def apply[A](valueOrder ng: Order ng[A]) = new Order ng[Try[A]] {
    def compare(x: Try[A], y: Try[A]):  nt = {
      x match {
        case Throw(_) =>  f (y. sReturn) -1 else 0
        case Return(xValue) =>
          y match {
            case Throw(_) => 1
            case Return(yValue) => valueOrder ng.compare(xValue, yValue)
          }
      }
    }
  }
}
