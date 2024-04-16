package com.tw ter.product_m xer.core.feature.datarecord

 mport com.tw ter.dal.personal_data.thr ftjava.PersonalDataType
 mport com.tw ter.ml.ap .Feature
 mport com.tw ter.ml.ap .DataType
 mport com.tw ter.ml.ap .thr ftscala.GeneralTensor
 mport com.tw ter.ml.ap .thr ftscala.Str ngTensor
 mport com.tw ter.ml.ap .ut l.ScalaToJavaDataRecordConvers ons
 mport com.tw ter.ml.ap .{GeneralTensor => JGeneralTensor}
 mport com.tw ter.ml.ap .{RawTypedTensor => JRawTypedTensor}
 mport com.tw ter.ml.ap .{Feature => MlFeature}
 mport java.n o.ByteBuffer
 mport java.n o.ByteOrder
 mport java.ut l.{Map => JMap}
 mport java.ut l.{Set => JSet}
 mport java.lang.{Long => JLong}
 mport java.lang.{Boolean => JBoolean}
 mport java.lang.{Double => JDouble}
 mport scala.collect on.JavaConverters._

/**
 * Def nes a convers on funct on for custo rs to m x- n w n construct ng a DataRecord supported
 * feature.   do t  because t  ML Feature representat on  s wr ten  n Java and uses Java types.
 * Furt rmore, allow ng custo rs to construct t  r own ML Feature d rectly can leave room
 * for m styp ng errors, such as us ng a Double ML Feature on a Str ng Product M xer feature.
 * T  m x  n enforces that t  custo r only uses t  r ght types, wh le mak ng   eas er
 * to setup a DataRecord Feature w h noth ng but a feature na  and personal data types.
 * @tparam FeatureValueType T  type of t  underly ng Product M xer feature value.
 */
sealed tra  DataRecordCompat ble[FeatureValueType] {
  // T  feature value type  n ProM x.
  f nal type FeatureType = FeatureValueType
  // T  underly ng DataRecord value type, so t  s t  d ffers from t  Feature Store and ProM x type.
  type DataRecordType

  def featureNa : Str ng
  def personalDataTypes: Set[PersonalDataType]

  pr vate[product_m xer] def mlFeature: MlFeature[DataRecordType]

  /**
   * To & from Data Record value converters.  n most cases, t   s one to one w n t  types match
   * but  n so  cases, certa n features are modeled as d fferent types  n Data Record. For example,
   * so  features that are Long (e.g, such as T epCred) are so t  s stored as Doubles.
   */
  pr vate[product_m xer] def toDataRecordFeatureValue(featureValue: FeatureType): DataRecordType
  pr vate[product_m xer] def fromDataRecordFeatureValue(featureValue: DataRecordType): FeatureType

}

/**
 * Converter for go ng from Str ng feature value to Str ng ML Feature.
 */
tra  Str ngDataRecordCompat ble extends DataRecordCompat ble[Str ng] {
  overr de type DataRecordType = Str ng

  f nal overr de lazy val mlFeature: MlFeature[Str ng] =
    new MlFeature.Text(featureNa , personalDataTypes.asJava)

  overr de pr vate[product_m xer] def fromDataRecordFeatureValue(
    featureValue: Str ng
  ): Str ng = featureValue

  overr de pr vate[product_m xer] def toDataRecordFeatureValue(
    featureValue: Str ng
  ): Str ng = featureValue
}

/**
 * Converter for go ng from Long feature value to D screte/Long ML Feature.
 */
tra  LongD screteDataRecordCompat ble extends DataRecordCompat ble[Long] {
  overr de type DataRecordType = JLong

  f nal overr de lazy val mlFeature: MlFeature[JLong] =
    new Feature.D screte(featureNa , personalDataTypes.asJava)

  overr de pr vate[product_m xer] def fromDataRecordFeatureValue(
    featureValue: JLong
  ): Long = featureValue

  overr de pr vate[product_m xer] def toDataRecordFeatureValue(
    featureValue: Long
  ): JLong = featureValue
}

/**
 * Converter for go ng from Long feature value to Cont nuous/Double ML Feature.
 */
tra  LongCont nuousDataRecordCompat ble extends DataRecordCompat ble[Long] {
  overr de type DataRecordType = JDouble

  f nal overr de lazy val mlFeature: MlFeature[JDouble] =
    new Feature.Cont nuous(featureNa , personalDataTypes.asJava)

  overr de pr vate[product_m xer] def toDataRecordFeatureValue(
    featureValue: FeatureType
  ): JDouble = featureValue.toDouble

  overr de pr vate[product_m xer] def fromDataRecordFeatureValue(
    featureValue: JDouble
  ): Long = featureValue.longValue()
}

/**
 * Converter for go ng from an  nteger feature value to Long/D screte ML Feature.
 */
tra   ntD screteDataRecordCompat ble extends DataRecordCompat ble[ nt] {
  overr de type DataRecordType = JLong

  f nal overr de lazy val mlFeature: MlFeature[JLong] =
    new MlFeature.D screte(featureNa , personalDataTypes.asJava)

  overr de pr vate[product_m xer] def fromDataRecordFeatureValue(
    featureValue: JLong
  ):  nt = featureValue.to nt

  overr de pr vate[product_m xer] def toDataRecordFeatureValue(
    featureValue:  nt
  ): JLong = featureValue.toLong
}

/**
 * Converter for go ng from  nteger feature value to Cont nuous/Double ML Feature.
 */
tra   ntCont nuousDataRecordCompat ble extends DataRecordCompat ble[ nt] {
  overr de type DataRecordType = JDouble

  f nal overr de lazy val mlFeature: MlFeature[JDouble] =
    new Feature.Cont nuous(featureNa , personalDataTypes.asJava)

  overr de pr vate[product_m xer] def toDataRecordFeatureValue(
    featureValue:  nt
  ): JDouble = featureValue.toDouble

  overr de pr vate[product_m xer] def fromDataRecordFeatureValue(
    featureValue: JDouble
  ):  nt = featureValue.to nt
}

/**
 * Converter for go ng from Double feature value to Cont nuous/Double ML Feature.
 */
tra  DoubleDataRecordCompat ble extends DataRecordCompat ble[Double] {
  overr de type DataRecordType = JDouble

  f nal overr de lazy val mlFeature: MlFeature[JDouble] =
    new MlFeature.Cont nuous(featureNa , personalDataTypes.asJava)

  overr de pr vate[product_m xer] def fromDataRecordFeatureValue(
    featureValue: JDouble
  ): Double = featureValue

  overr de pr vate[product_m xer] def toDataRecordFeatureValue(
    featureValue: Double
  ): JDouble = featureValue
}

/**
 * Converter for go ng from Boolean feature value to Boolean ML Feature.
 */
tra  BoolDataRecordCompat ble extends DataRecordCompat ble[Boolean] {
  overr de type DataRecordType = JBoolean

  f nal overr de lazy val mlFeature: MlFeature[JBoolean] =
    new MlFeature.B nary(featureNa , personalDataTypes.asJava)

  overr de pr vate[product_m xer] def fromDataRecordFeatureValue(
    featureValue: JBoolean
  ): Boolean = featureValue

  overr de pr vate[product_m xer] def toDataRecordFeatureValue(
    featureValue: Boolean
  ): JBoolean = featureValue
}

/**
 * Converter for go ng from a ByteBuffer feature value to ByteBuffer ML Feature.
 */
tra  BlobDataRecordCompat ble extends DataRecordCompat ble[ByteBuffer] {
  overr de type DataRecordType = ByteBuffer

  f nal overr de lazy val mlFeature: MlFeature[ByteBuffer] =
    new Feature.Blob(featureNa , personalDataTypes.asJava)

  overr de pr vate[product_m xer] def fromDataRecordFeatureValue(
    featureValue: ByteBuffer
  ): ByteBuffer = featureValue

  overr de pr vate[product_m xer] def toDataRecordFeatureValue(
    featureValue: ByteBuffer
  ): ByteBuffer = featureValue
}

/**
 * Converter for go ng from a Map[Str ng, Double] feature value to Sparse Double/Cont n ous ML Feature.
 */
tra  SparseCont nuousDataRecordCompat ble extends DataRecordCompat ble[Map[Str ng, Double]] {
  overr de type DataRecordType = JMap[Str ng, JDouble]

  f nal overr de lazy val mlFeature: MlFeature[JMap[Str ng, JDouble]] =
    new Feature.SparseCont nuous(featureNa , personalDataTypes.asJava)

  overr de pr vate[product_m xer] def toDataRecordFeatureValue(
    featureValue: Map[Str ng, Double]
  ): JMap[Str ng, JDouble] =
    featureValue.mapValues(_.as nstanceOf[JDouble]).asJava

  overr de pr vate[product_m xer] def fromDataRecordFeatureValue(
    featureValue: JMap[Str ng, JDouble]
  ) = featureValue.asScala.toMap.mapValues(_.doubleValue())
}

/**
 * Converter for go ng from a Set[Str ng] feature value to SparseB nary/Str ng Set ML Feature.
 */
tra  SparseB naryDataRecordCompat ble extends DataRecordCompat ble[Set[Str ng]] {
  overr de type DataRecordType = JSet[Str ng]

  f nal overr de lazy val mlFeature: MlFeature[JSet[Str ng]] =
    new Feature.SparseB nary(featureNa , personalDataTypes.asJava)

  overr de pr vate[product_m xer] def fromDataRecordFeatureValue(
    featureValue: JSet[Str ng]
  ) = featureValue.asScala.toSet

  overr de pr vate[product_m xer] def toDataRecordFeatureValue(
    featureValue: FeatureType
  ): JSet[Str ng] = featureValue.asJava
}

/**
 * Marker tra  for any feature value to Tensor ML Feature. Not d rectly usable.
 */
sealed tra  TensorDataRecordCompat ble[FeatureV] extends DataRecordCompat ble[FeatureV] {
  overr de type DataRecordType = JGeneralTensor
  overr de def mlFeature: MlFeature[JGeneralTensor]
}

/**
 * Converter for a double to a Tensor feature encoded as float encoded RawTypedTensor
 */
tra  RawTensorFloatDoubleDataRecordCompat ble extends TensorDataRecordCompat ble[Double] {
  f nal overr de lazy val mlFeature: MlFeature[JGeneralTensor] =
    new Feature.Tensor(
      featureNa ,
      DataType.FLOAT,
      L st.empty[JLong].asJava,
      personalDataTypes.asJava)

  overr de pr vate[product_m xer] def toDataRecordFeatureValue(
    featureValue: FeatureType
  ) = {
    val byteBuffer: ByteBuffer =
      ByteBuffer
        .allocate(4).order(ByteOrder.L TTLE_END AN).putFloat(featureValue.toFloat)
    byteBuffer.fl p()
    val tensor = new JGeneralTensor()
    tensor.setRawTypedTensor(new JRawTypedTensor(DataType.FLOAT, byteBuffer))
    tensor
  }

  overr de pr vate[product_m xer] def fromDataRecordFeatureValue(
    featureValue: JGeneralTensor
  ) = {
    val tensor = Opt on(featureValue.getRawTypedTensor)
      .getOrElse(throw new UnexpectedTensorExcept on(featureValue))
    tensor.content.order(ByteOrder.L TTLE_END AN).getFloat().toDouble
  }
}

/**
 *  Converter for a scala general tensor to java general tensor ML feature.
 */
tra  GeneralTensorDataRecordCompat ble extends TensorDataRecordCompat ble[GeneralTensor] {

  def dataType: DataType
  f nal overr de lazy val mlFeature: MlFeature[JGeneralTensor] =
    new Feature.Tensor(featureNa , dataType, L st.empty[JLong].asJava, personalDataTypes.asJava)

  overr de pr vate[product_m xer] def toDataRecordFeatureValue(
    featureValue: FeatureType
  ) = ScalaToJavaDataRecordConvers ons.scalaTensor2Java(featureValue)

  overr de pr vate[product_m xer] def fromDataRecordFeatureValue(
    featureValue: JGeneralTensor
  ) = ScalaToJavaDataRecordConvers ons.javaTensor2Scala(featureValue)
}

/**
 *  Converter for a scala str ng tensor to java general tensor ML feature.
 */
tra  Str ngTensorDataRecordCompat ble extends TensorDataRecordCompat ble[Str ngTensor] {
  f nal overr de lazy val mlFeature: MlFeature[JGeneralTensor] =
    new Feature.Tensor(
      featureNa ,
      DataType.STR NG,
      L st.empty[JLong].asJava,
      personalDataTypes.asJava)

  overr de pr vate[product_m xer] def fromDataRecordFeatureValue(
    featureValue: JGeneralTensor
  ) = {
    ScalaToJavaDataRecordConvers ons.javaTensor2Scala(featureValue) match {
      case GeneralTensor.Str ngTensor(str ngTensor) => str ngTensor
      case _ => throw new UnexpectedTensorExcept on(featureValue)
    }
  }

  overr de pr vate[product_m xer] def toDataRecordFeatureValue(
    featureValue: FeatureType
  ) = ScalaToJavaDataRecordConvers ons.scalaTensor2Java(GeneralTensor.Str ngTensor(featureValue))
}

class UnexpectedTensorExcept on(tensor: JGeneralTensor)
    extends Except on(s"Unexpected Tensor: $tensor")
