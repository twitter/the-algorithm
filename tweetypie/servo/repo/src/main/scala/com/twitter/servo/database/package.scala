package com.tw ter.servo

 mport com.tw ter.ut l.Future
 mport java.sql.ResultSet

package object database {
  type DatabaseFactory = (() => Database)

  /**
   * A funct on type for translat ng ResultSets  nto objects of t  result type A.
   */
  type Bu lder[A] = ResultSet => A

  /**
   * A funct on type for asynchronously translat ng ResultSets  nto objects
   * of t  result type A.
   */
  type FutureBu lder[A] = Bu lder[Future[A]]
}
