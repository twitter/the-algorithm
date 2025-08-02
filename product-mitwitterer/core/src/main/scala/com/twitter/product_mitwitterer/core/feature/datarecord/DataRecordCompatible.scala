package com.twitter.product_mitwitterer.core.feature.datarecord

import com.twitter.dal.personal_data.thriftjava.PersonalDataType
import com.twitter.ml.api.Feature
import com.twitter.ml.api.DataType
import com.twitter.ml.api.thriftscala.GeneralTensor
import com.twitter.ml.api.thriftscala.StringTensor
import com.twitter.ml.api.util.ScalaToJavaDataRecordConversions
import com.twitter.ml.api.{GeneralTensor => JGeneralTensor}
import com.twitter.ml.api.{RawTypedTensor => JRawTypedTensor}
import com.twitter.ml.api.{Feature => MlFeature}
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.{Map => JMap}
import java.util.{Set => JSet}
import java.lang.{Long => JLong}
import java.lang.{Boolean => JBoolean}
import java.lang.{Double => JDouble}
import scala.collection.JavaConverters._

/**
 * Defines a conversion function for customers to mitwitter-in when constructing a DataRecord supported
 * feature. We do this because the ML Feature representation is written in Java and uses Java types.
 * Furthermore, allowing customers to construct their own ML Feature directly can leave room
 * for mistyping errors, such as using a Double ML Feature on a String Product Mitwitterer feature.
 * This mitwitter in enforces that the customer only uses the right types, while making it easier
 * to setup a DataRecord Feature with nothing but a feature name and personal data types.
 * @tparam FeatureValueType The type of the underlying Product Mitwitterer feature value.
 */
sealed trait DataRecordCompatible[FeatureValueType] {
  // The feature value type in ProMitwitter.
  final type FeatureType = FeatureValueType
  // The underlying DataRecord value type, sometimes this differs from the Feature Store and ProMitwitter type.
  type DataRecordType

  def featureName: String
  def personalDataTypes: Set[PersonalDataType]

  private[product_mitwitterer] def mlFeature: MlFeature[DataRecordType]

  /**
   * To & from Data Record value converters. In most cases, this is one to one when the types match
   * but in some cases, certain features are modeled as different types in Data Record. For etwitterample,
   * some features that are Long (e.g, such as TweepCred) are sometimes stored as Doubles.
   */
  private[product_mitwitterer] def toDataRecordFeatureValue(featureValue: FeatureType): DataRecordType
  private[product_mitwitterer] def fromDataRecordFeatureValue(featureValue: DataRecordType): FeatureType

}

/**
 * Converter for going from String feature value to String ML Feature.
 */
trait StringDataRecordCompatible etwittertends DataRecordCompatible[String] {
  override type DataRecordType = String

  final override lazy val mlFeature: MlFeature[String] =
    new MlFeature.Tetwittert(featureName, personalDataTypes.asJava)

  override private[product_mitwitterer] def fromDataRecordFeatureValue(
    featureValue: String
  ): String = featureValue

  override private[product_mitwitterer] def toDataRecordFeatureValue(
    featureValue: String
  ): String = featureValue
}

/**
 * Converter for going from Long feature value to Discrete/Long ML Feature.
 */
trait LongDiscreteDataRecordCompatible etwittertends DataRecordCompatible[Long] {
  override type DataRecordType = JLong

  final override lazy val mlFeature: MlFeature[JLong] =
    new Feature.Discrete(featureName, personalDataTypes.asJava)

  override private[product_mitwitterer] def fromDataRecordFeatureValue(
    featureValue: JLong
  ): Long = featureValue

  override private[product_mitwitterer] def toDataRecordFeatureValue(
    featureValue: Long
  ): JLong = featureValue
}

/**
 * Converter for going from Long feature value to Continuous/Double ML Feature.
 */
trait LongContinuousDataRecordCompatible etwittertends DataRecordCompatible[Long] {
  override type DataRecordType = JDouble

  final override lazy val mlFeature: MlFeature[JDouble] =
    new Feature.Continuous(featureName, personalDataTypes.asJava)

  override private[product_mitwitterer] def toDataRecordFeatureValue(
    featureValue: FeatureType
  ): JDouble = featureValue.toDouble

  override private[product_mitwitterer] def fromDataRecordFeatureValue(
    featureValue: JDouble
  ): Long = featureValue.longValue()
}

/**
 * Converter for going from an Integer feature value to Long/Discrete ML Feature.
 */
trait IntDiscreteDataRecordCompatible etwittertends DataRecordCompatible[Int] {
  override type DataRecordType = JLong

  final override lazy val mlFeature: MlFeature[JLong] =
    new MlFeature.Discrete(featureName, personalDataTypes.asJava)

  override private[product_mitwitterer] def fromDataRecordFeatureValue(
    featureValue: JLong
  ): Int = featureValue.toInt

  override private[product_mitwitterer] def toDataRecordFeatureValue(
    featureValue: Int
  ): JLong = featureValue.toLong
}

/**
 * Converter for going from Integer feature value to Continuous/Double ML Feature.
 */
trait IntContinuousDataRecordCompatible etwittertends DataRecordCompatible[Int] {
  override type DataRecordType = JDouble

  final override lazy val mlFeature: MlFeature[JDouble] =
    new Feature.Continuous(featureName, personalDataTypes.asJava)

  override private[product_mitwitterer] def toDataRecordFeatureValue(
    featureValue: Int
  ): JDouble = featureValue.toDouble

  override private[product_mitwitterer] def fromDataRecordFeatureValue(
    featureValue: JDouble
  ): Int = featureValue.toInt
}

/**
 * Converter for going from Double feature value to Continuous/Double ML Feature.
 */
trait DoubleDataRecordCompatible etwittertends DataRecordCompatible[Double] {
  override type DataRecordType = JDouble

  final override lazy val mlFeature: MlFeature[JDouble] =
    new MlFeature.Continuous(featureName, personalDataTypes.asJava)

  override private[product_mitwitterer] def fromDataRecordFeatureValue(
    featureValue: JDouble
  ): Double = featureValue

  override private[product_mitwitterer] def toDataRecordFeatureValue(
    featureValue: Double
  ): JDouble = featureValue
}

/**
 * Converter for going from Boolean feature value to Boolean ML Feature.
 */
trait BoolDataRecordCompatible etwittertends DataRecordCompatible[Boolean] {
  override type DataRecordType = JBoolean

  final override lazy val mlFeature: MlFeature[JBoolean] =
    new MlFeature.Binary(featureName, personalDataTypes.asJava)

  override private[product_mitwitterer] def fromDataRecordFeatureValue(
    featureValue: JBoolean
  ): Boolean = featureValue

  override private[product_mitwitterer] def toDataRecordFeatureValue(
    featureValue: Boolean
  ): JBoolean = featureValue
}

/**
 * Converter for going from a ByteBuffer feature value to ByteBuffer ML Feature.
 */
trait BlobDataRecordCompatible etwittertends DataRecordCompatible[ByteBuffer] {
  override type DataRecordType = ByteBuffer

  final override lazy val mlFeature: MlFeature[ByteBuffer] =
    new Feature.Blob(featureName, personalDataTypes.asJava)

  override private[product_mitwitterer] def fromDataRecordFeatureValue(
    featureValue: ByteBuffer
  ): ByteBuffer = featureValue

  override private[product_mitwitterer] def toDataRecordFeatureValue(
    featureValue: ByteBuffer
  ): ByteBuffer = featureValue
}

/**
 * Converter for going from a Map[String, Double] feature value to Sparse Double/Continious ML Feature.
 */
trait SparseContinuousDataRecordCompatible etwittertends DataRecordCompatible[Map[String, Double]] {
  override type DataRecordType = JMap[String, JDouble]

  final override lazy val mlFeature: MlFeature[JMap[String, JDouble]] =
    new Feature.SparseContinuous(featureName, personalDataTypes.asJava)

  override private[product_mitwitterer] def toDataRecordFeatureValue(
    featureValue: Map[String, Double]
  ): JMap[String, JDouble] =
    featureValue.mapValues(_.asInstanceOf[JDouble]).asJava

  override private[product_mitwitterer] def fromDataRecordFeatureValue(
    featureValue: JMap[String, JDouble]
  ) = featureValue.asScala.toMap.mapValues(_.doubleValue())
}

/**
 * Converter for going from a Set[String] feature value to SparseBinary/String Set ML Feature.
 */
trait SparseBinaryDataRecordCompatible etwittertends DataRecordCompatible[Set[String]] {
  override type DataRecordType = JSet[String]

  final override lazy val mlFeature: MlFeature[JSet[String]] =
    new Feature.SparseBinary(featureName, personalDataTypes.asJava)

  override private[product_mitwitterer] def fromDataRecordFeatureValue(
    featureValue: JSet[String]
  ) = featureValue.asScala.toSet

  override private[product_mitwitterer] def toDataRecordFeatureValue(
    featureValue: FeatureType
  ): JSet[String] = featureValue.asJava
}

/**
 * Marker trait for any feature value to Tensor ML Feature. Not directly usable.
 */
sealed trait TensorDataRecordCompatible[FeatureV] etwittertends DataRecordCompatible[FeatureV] {
  override type DataRecordType = JGeneralTensor
  override def mlFeature: MlFeature[JGeneralTensor]
}

/**
 * Converter for a double to a Tensor feature encoded as float encoded RawTypedTensor
 */
trait RawTensorFloatDoubleDataRecordCompatible etwittertends TensorDataRecordCompatible[Double] {
  final override lazy val mlFeature: MlFeature[JGeneralTensor] =
    new Feature.Tensor(
      featureName,
      DataType.FLOAT,
      List.empty[JLong].asJava,
      personalDataTypes.asJava)

  override private[product_mitwitterer] def toDataRecordFeatureValue(
    featureValue: FeatureType
  ) = {
    val byteBuffer: ByteBuffer =
      ByteBuffer
        .allocate(4).order(ByteOrder.LITTLE_ENDIAN).putFloat(featureValue.toFloat)
    byteBuffer.flip()
    val tensor = new JGeneralTensor()
    tensor.setRawTypedTensor(new JRawTypedTensor(DataType.FLOAT, byteBuffer))
    tensor
  }

  override private[product_mitwitterer] def fromDataRecordFeatureValue(
    featureValue: JGeneralTensor
  ) = {
    val tensor = Option(featureValue.getRawTypedTensor)
      .getOrElse(throw new UnetwitterpectedTensorEtwitterception(featureValue))
    tensor.content.order(ByteOrder.LITTLE_ENDIAN).getFloat().toDouble
  }
}

/**
 *  Converter for a scala general tensor to java general tensor ML feature.
 */
trait GeneralTensorDataRecordCompatible etwittertends TensorDataRecordCompatible[GeneralTensor] {

  def dataType: DataType
  final override lazy val mlFeature: MlFeature[JGeneralTensor] =
    new Feature.Tensor(featureName, dataType, List.empty[JLong].asJava, personalDataTypes.asJava)

  override private[product_mitwitterer] def toDataRecordFeatureValue(
    featureValue: FeatureType
  ) = ScalaToJavaDataRecordConversions.scalaTensor2Java(featureValue)

  override private[product_mitwitterer] def fromDataRecordFeatureValue(
    featureValue: JGeneralTensor
  ) = ScalaToJavaDataRecordConversions.javaTensor2Scala(featureValue)
}

/**
 *  Converter for a scala string tensor to java general tensor ML feature.
 */
trait StringTensorDataRecordCompatible etwittertends TensorDataRecordCompatible[StringTensor] {
  final override lazy val mlFeature: MlFeature[JGeneralTensor] =
    new Feature.Tensor(
      featureName,
      DataType.STRING,
      List.empty[JLong].asJava,
      personalDataTypes.asJava)

  override private[product_mitwitterer] def fromDataRecordFeatureValue(
    featureValue: JGeneralTensor
  ) = {
    ScalaToJavaDataRecordConversions.javaTensor2Scala(featureValue) match {
      case GeneralTensor.StringTensor(stringTensor) => stringTensor
      case _ => throw new UnetwitterpectedTensorEtwitterception(featureValue)
    }
  }

  override private[product_mitwitterer] def toDataRecordFeatureValue(
    featureValue: FeatureType
  ) = ScalaToJavaDataRecordConversions.scalaTensor2Java(GeneralTensor.StringTensor(featureValue))
}

class UnetwitterpectedTensorEtwitterception(tensor: JGeneralTensor)
    etwittertends Etwitterception(s"Unetwitterpected Tensor: $tensor")
