package com.X.visibility.features

import com.X.visibility.util.NamingUtils

abstract class Feature[T] protected ()(implicit val manifest: Manifest[T]) {

  lazy val name: String = NamingUtils.getFriendlyName(this)

  override lazy val toString: String =
    "Feature[%s](name=%s)".format(manifest, getClass.getSimpleName)
}
