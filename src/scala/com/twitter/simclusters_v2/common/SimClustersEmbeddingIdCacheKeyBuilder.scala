package com.tw ter.s mclusters_v2.common

 mport com.tw ter.s mclusters_v2.thr ftscala.S mClustersEmbedd ng d

/**
 * A common l brary to construct Cac  Key for S mClustersEmbedd ng d.
 */
case class S mClustersEmbedd ng dCac KeyBu lder(
  hash: Array[Byte] => Long,
  pref x: Str ng = "") {

  // Example: "CR:SCE:1:2:1234567890ABCDEF"
  def apply(embedd ng d: S mClustersEmbedd ng d): Str ng = {
    f"$pref x:SCE:${embedd ng d.embedd ngType.getValue()}%X:" +
      f"${embedd ng d.modelVers on.getValue()}%X" +
      f":${hash(embedd ng d. nternal d.toStr ng.getBytes)}%X"
  }

}
