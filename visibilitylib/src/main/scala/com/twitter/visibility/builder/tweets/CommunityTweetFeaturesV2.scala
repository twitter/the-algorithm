package com.tw ter.v s b l y.bu lder.t ets

 mport com.tw ter.commun  es.moderat on.thr ftscala.Commun yT etModerat onState
 mport com.tw ter.commun  es.moderat on.thr ftscala.Commun yUserModerat onState
 mport com.tw ter.commun  es.v s b l y.thr ftscala.Commun yV s b l yFeatures
 mport com.tw ter.commun  es.v s b l y.thr ftscala.Commun yV s b l yFeaturesV1
 mport com.tw ter.commun  es.v s b l y.thr ftscala.Commun yV s b l yResult
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.thr ftscala.T et
 mport com.tw ter.v s b l y.bu lder.FeatureMapBu lder
 mport com.tw ter.v s b l y.common.Commun  esS ce
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

class Commun yT etFeaturesV2(commun  esS ce: Commun  esS ce)
    extends Commun yT etFeatures {
  pr vate[t ] def forCommun yT et(
    commun yT et: Commun yT et
  ): FeatureMapBu lder => FeatureMapBu lder = { bu lder: FeatureMapBu lder =>
    {
      val commun yV s b l yFeaturesSt ch =
        commun  esS ce.getCommun yV s b l yFeatures(commun yT et.commun y d)
      val commun yT etModerat onStateSt ch =
        commun  esS ce.getT etModerat onState(commun yT et.t et. d)
      val commun yT etAuthorModerat onStateSt ch =
        commun  esS ce.getUserModerat onState(
          commun yT et.author d,
          commun yT et.commun y d
        )

      def getFlagFromFeatures(f: Commun yV s b l yFeaturesV1 => Boolean): St ch[Boolean] =
        commun yV s b l yFeaturesSt ch.map {
          case So (Commun yV s b l yFeatures.V1(v1)) => f(v1)
          case _ => false
        }

      def getFlagFromCommun yV s b l yResult(
        f: Commun yV s b l yResult => Boolean
      ): St ch[Boolean] = getFlagFromFeatures { v =>
        f(v.commun yV s b l yResult)
      }

      bu lder
        .w hConstantFeature(
          T et sCommun yT et,
          true
        )
        .w hFeature(
          Commun yT etCommun yNotFound,
          getFlagFromCommun yV s b l yResult {
            case Commun yV s b l yResult.NotFound => true
            case _ => false
          }
        )
        .w hFeature(
          Commun yT etCommun ySuspended,
          getFlagFromCommun yV s b l yResult {
            case Commun yV s b l yResult.Suspended => true
            case _ => false
          }
        )
        .w hFeature(
          Commun yT etCommun yDeleted,
          getFlagFromCommun yV s b l yResult {
            case Commun yV s b l yResult.Deleted => true
            case _ => false
          }
        )
        .w hFeature(
          Commun yT etCommun yV s ble,
          getFlagFromCommun yV s b l yResult {
            case Commun yV s b l yResult.V s ble => true
            case _ => false
          }
        )
        .w hFeature(
          V e r s nternalCommun  esAdm n,
          getFlagFromFeatures { _.v e r s nternalAdm n }
        )
        .w hFeature(
          V e r sCommun yAdm n,
          getFlagFromFeatures { _.v e r sCommun yAdm n }
        )
        .w hFeature(
          V e r sCommun yModerator,
          getFlagFromFeatures { _.v e r sCommun yModerator }
        )
        .w hFeature(
          V e r sCommun y mber,
          getFlagFromFeatures { _.v e r sCommun y mber }
        )
        .w hFeature(
          Commun yT et sH dden,
          commun yT etModerat onStateSt ch.map {
            case So (Commun yT etModerat onState.H dden(_)) => true
            case _ => false
          }
        )
        .w hFeature(
          Commun yT etAuthor sRemoved,
          commun yT etAuthorModerat onStateSt ch.map {
            case So (Commun yUserModerat onState.Removed(_)) => true
            case _ => false
          }
        )
    }
  }

  def forT et(
    t et: T et,
    v e rContext: V e rContext
  ): FeatureMapBu lder => FeatureMapBu lder = {
    Commun yT et(t et) match {
      case None => forNonCommun yT et()
      case So (commun yT et) => forCommun yT et(commun yT et)
    }
  }
}
