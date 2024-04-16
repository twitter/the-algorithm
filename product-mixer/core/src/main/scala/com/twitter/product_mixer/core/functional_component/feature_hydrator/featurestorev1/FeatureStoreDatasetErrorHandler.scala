package com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.featurestorev1

 mport com.tw ter.ml.featurestore.l b.Ent y d
 mport com.tw ter.ml.featurestore.l b.data.DatasetErrorsBy d
 mport com.tw ter.ml.featurestore.l b.data.Hydrat onError
 mport com.tw ter.ml.featurestore.l b.dataset.Dataset d
 mport com.tw ter.product_m xer.core.feature.featurestorev1.BaseFeatureStoreV1Feature
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

object FeatureStoreDatasetErrorHandler {

  /**
   * T  funct on takes a set of feature store features and constructs a mapp ng from t  underly ng
   * feature store dataset back to t  features. T   s useful for look ng up what ProM x features
   * fa led based off of a fa led feature store dataset at request t  . A ProM x feature can be
   * po red by mult ple feature store datasets, and conversely, a dataset can be used by many features.
   */
  def datasetToFeaturesMapp ng[
    Query <: P pel neQuery,
     nput,
    FeatureType <: BaseFeatureStoreV1Feature[Query,  nput, _ <: Ent y d, _]
  ](
    features: Set[FeatureType]
  ): Map[Dataset d, Set[FeatureType]] = {
    val datasetsAndFeatures: Set[(Dataset d, FeatureType)] = features
      .flatMap { feature: FeatureType =>
        feature.boundFeatureSet.s ceDatasets.map(_. d).map { dataset d: Dataset d =>
          dataset d -> feature
        }
      }

    datasetsAndFeatures
      .groupBy { case (dataset d, _) => dataset d }.mapValues(_.map {
        case (_, feature) => feature
      })
  }

  /**
   * T  takes a mapp ng of Feature Store Dataset => ProM x Features, as  ll as t  dataset errors
   * from Pred ct onRecord and comput ng a f nal, deduped mapp ng from ProM x Feature to Except ons.
   */
  def featureToHydrat onErrors[
    Query <: P pel neQuery,
     nput,
    FeatureType <: BaseFeatureStoreV1Feature[Query,  nput, _ <: Ent y d, _]
  ](
    datasetToFeatures: Map[Dataset d, Set[
      FeatureType
    ]],
    errorsByDataset d: DatasetErrorsBy d
  ): Map[FeatureType, Set[Hydrat onError]] = {
    val hasError = errorsByDataset d.datasets.nonEmpty
     f (hasError) {
      val featuresAndErrors: Set[(FeatureType, Set[Hydrat onError])] = errorsByDataset d.datasets
        .flatMap {  d: Dataset d =>
          val errors: Set[Hydrat onError] = errorsByDataset d.get( d).values.toSet
           f (errors.nonEmpty) {
            val datasetFeatures: Set[FeatureType] = datasetToFeatures.getOrElse( d, Set.empty)
            datasetFeatures.map { feature =>
              feature -> errors
            }.toSeq
          } else {
            Seq.empty
          }
        }
      featuresAndErrors
        .groupBy { case (feature, _) => feature }.mapValues(_.flatMap {
          case (_, errors: Set[Hydrat onError]) => errors
        })
    } else {
      Map.empty
    }
  }
}
