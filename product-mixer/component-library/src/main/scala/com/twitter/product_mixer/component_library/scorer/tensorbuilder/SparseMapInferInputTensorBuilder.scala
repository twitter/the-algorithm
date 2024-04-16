package com.tw ter.product_m xer.component_l brary.scorer.tensorbu lder

 mport  nference.GrpcServ ce. nferTensorContents
 mport  nference.GrpcServ ce.Model nferRequest. nfer nputTensor

case object SparseMap nfer nputTensorBu lder
    extends  nfer nputTensorBu lder[Opt on[Map[ nt, Double]]] {

  pr vate f nal val batchFeatureNa Suff x: Str ng = "batch"
  pr vate f nal val keyFeatureNa Suff x: Str ng = "key"
  pr vate f nal val valueFeatureNa Suff x: Str ng = "value"

  def apply(
    featureNa : Str ng,
    featureValues: Seq[Opt on[Map[ nt, Double]]]
  ): Seq[ nfer nputTensor] = {
    val batch dsTensorContents =  nferTensorContents.newBu lder()
    val sparseKeysTensorContents =  nferTensorContents.newBu lder()
    val sparseValuesTensorContents =  nferTensorContents.newBu lder()
    featureValues.z pW h ndex.foreach {
      case (featureValueOpt on, batch ndex) =>
        featureValueOpt on.foreach { featureValue =>
          featureValue.foreach {
            case (sparseKey, sparseValue) =>
              batch dsTensorContents.add nt64Contents(batch ndex.toLong)
              sparseKeysTensorContents.add nt64Contents(sparseKey.toLong)
              sparseValuesTensorContents.addFp32Contents(sparseValue.floatValue)
          }
        }
    }

    val batch ds nputTensor =  nfer nputTensor
      .newBu lder()
      .setNa (Seq(featureNa , batchFeatureNa Suff x).mkStr ng("_"))
      .addShape(batch dsTensorContents.get nt64ContentsCount)
      .addShape(1)
      .setDatatype(" NT64")
      .setContents(batch dsTensorContents)
      .bu ld()

    val sparseKeys nputTensor =  nfer nputTensor
      .newBu lder()
      .setNa (Seq(featureNa , keyFeatureNa Suff x).mkStr ng("_"))
      .addShape(sparseKeysTensorContents.get nt64ContentsCount)
      .addShape(1)
      .setDatatype(" NT64")
      .setContents(sparseKeysTensorContents)
      .bu ld()

    val sparseValues nputTensor =  nfer nputTensor
      .newBu lder()
      .setNa (Seq(featureNa , valueFeatureNa Suff x).mkStr ng("_"))
      .addShape(sparseValuesTensorContents.getFp32ContentsCount)
      .addShape(1)
      .setDatatype("FP32")
      .setContents(sparseValuesTensorContents)
      .bu ld()

    Seq(batch ds nputTensor, sparseKeys nputTensor, sparseValues nputTensor)
  }
}
