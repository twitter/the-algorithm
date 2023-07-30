package com.X.servo

import com.X.util.Future
import java.sql.ResultSet

package object database {
  type DatabaseFactory = (() => Database)

  /**
   * A function type for translating ResultSets into objects of the result type A.
   */
  type Builder[A] = ResultSet => A

  /**
   * A function type for asynchronously translating ResultSets into objects
   * of the result type A.
   */
  type FutureBuilder[A] = Builder[Future[A]]
}
