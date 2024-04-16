package com.tw ter.v s b l y.bu lder.t ets

 mport com.tw ter.t etyp e.thr ftscala.T et
 mport com.tw ter.v s b l y.bu lder.FeatureMapBu lder
 mport com.tw ter.v s b l y.features.Commun yT etAuthor sRemoved
 mport com.tw ter.v s b l y.features.Commun yT etCommun yNotFound
 mport com.tw ter.v s b l y.features.Commun yT etCommun yDeleted
 mport com.tw ter.v s b l y.features.Commun yT etCommun ySuspended
 mport com.tw ter.v s b l y.features.Commun yT etCommun yV s ble
 mport com.tw ter.v s b l y.features.Commun yT et sH dden
 mport com.tw ter.v s b l y.features.T et sCommun yT et
 mport com.tw ter.v s b l y.features.V e r sCommun yAdm n
 mport com.tw ter.v s b l y.features.V e r sCommun y mber
 mport com.tw ter.v s b l y.features.V e r sCommun yModerator
 mport com.tw ter.v s b l y.features.V e r s nternalCommun  esAdm n
 mport com.tw ter.v s b l y.models.Commun yT et
 mport com.tw ter.v s b l y.models.V e rContext

tra  Commun yT etFeatures {

  def forT et(
    t et: T et,
    v e rContext: V e rContext
  ): FeatureMapBu lder => FeatureMapBu lder

  def forT etOnly(t et: T et): FeatureMapBu lder => FeatureMapBu lder = {
    _.w hConstantFeature(
      T et sCommun yT et,
      Commun yT et(t et). sDef ned
    )
  }

  protected def forNonCommun yT et(): FeatureMapBu lder => FeatureMapBu lder = { bu lder =>
    bu lder
      .w hConstantFeature(
        T et sCommun yT et,
        false
      ).w hConstantFeature(
        Commun yT etCommun yNotFound,
        false
      ).w hConstantFeature(
        Commun yT etCommun ySuspended,
        false
      ).w hConstantFeature(
        Commun yT etCommun yDeleted,
        false
      ).w hConstantFeature(
        Commun yT etCommun yV s ble,
        false
      ).w hConstantFeature(
        V e r s nternalCommun  esAdm n,
        false
      ).w hConstantFeature(
        V e r sCommun yAdm n,
        false
      ).w hConstantFeature(
        V e r sCommun yModerator,
        false
      ).w hConstantFeature(
        V e r sCommun y mber,
        false
      ).w hConstantFeature(
        Commun yT et sH dden,
        false
      ).w hConstantFeature(
        Commun yT etAuthor sRemoved,
        false
      )
  }
}
