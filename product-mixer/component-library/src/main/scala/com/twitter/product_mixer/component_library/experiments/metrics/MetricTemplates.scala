package com.tw ter.product_m xer.component_l brary.exper  nts. tr cs

 mport com.tw ter.product_m xer.component_l brary.exper  nts. tr cs.PlaceholderConf g.PlaceholdersMap
 mport reflect.ClassTag
 mport scala.reflect.runt  .un verse._
 mport scala.ut l.match ng.Regex

case class Matc dPlaceholder(outerKey: Str ng,  nnerKey: Opt on[Str ng] = None)

object  tr cTemplates {
  // Matc s "${placeholder}" w re `placeholder`  s  n a matc d group
  val PlaceholderPattern: Regex = "\\$\\{([^\\}]+)\\}".r.unanchored
  // Matc s "${placeholder[ ndex]}" w re `placeholder` and ` ndex` are  n d fferent matc d groups
  val  ndexedPlaceholderPattern: Regex = "\\$\\{([^\\[]+)\\[([^\\]]+)\\]\\}".r.unanchored
  val DefaultF eldNa  = "na "

  def  nterpolate( nputTemplate: Str ng, placeholders: PlaceholdersMap): Seq[Str ng] = {
    val matc dPlaceholders = getMatc dPlaceholders( nputTemplate)
    val groupedPlaceholders = matc dPlaceholders.groupBy(_.outerKey)
    val placeholderKeyValues = getPlaceholderKeyValues(groupedPlaceholders, placeholders)
    val (keys, values) = (placeholderKeyValues.map(_._1), placeholderKeyValues.map(_._2))
    val cross: Seq[L st[Na d]] = crossProduct(values)
    val m rror = runt  M rror(getClass.getClassLoader) // necessary for reflect on
    for {
       nterpolatables <- cross
    } y eld {
      assert(
        keys.length ==  nterpolatables.length,
        s"Unexpected length m smatch bet en $keys and $ nterpolatables")
      var replace ntStr =  nputTemplate
      keys.z p( nterpolatables).foreach {
        case (key,  nterpolatable) =>
          val accessors = caseAccessors(m rror,  nterpolatable)
          groupedPlaceholders(key).foreach { placeholder: Matc dPlaceholder =>
            val templateKey = generateTemplateKey(placeholder)
            val f eldNa  = placeholder. nnerKey.getOrElse(DefaultF eldNa )
            val f eldValue = getF eldValue(m rror,  nterpolatable, accessors, f eldNa )
            replace ntStr = replace ntStr.replaceAll(templateKey, f eldValue)
          }
      }
      replace ntStr
    }
  }

  def getMatc dPlaceholders( nputTemplate: Str ng): Seq[Matc dPlaceholder] = {
    for {
      matc d <- PlaceholderPattern.f ndAll n( nputTemplate).toSeq
    } y eld {
      val matc dW h ndexOpt =  ndexedPlaceholderPattern.f ndF rstMatch n(matc d)
      val (outer,  nner) = matc dW h ndexOpt
        .map { matc dW h ndex =>
          (matc dW h ndex.group(1), So (matc dW h ndex.group(2)))
        }.getOrElse((matc d, None))
      val outerKey = unwrap(outer)
      val  nnerKey =  nner.map(unwrap(_))
      Matc dPlaceholder(outerKey,  nnerKey)
    }
  }

  def unwrap(str: Str ng): Str ng =
    str.str pPref x("${").str pSuff x("}")

  def wrap(str: Str ng): Str ng =
    "\\$\\{" + str + "\\}"

  def getPlaceholderKeyValues(
    groupedPlaceholders: Map[Str ng, Seq[Matc dPlaceholder]],
    placeholders: PlaceholdersMap
  ): Seq[(Str ng, Seq[Na d])] = {
    groupedPlaceholders.toSeq
      .map {
        case (outerKey, _) =>
          val placeholderValues = placeholders.getOrElse(
            outerKey,
            throw new Runt  Except on(s"Fa led to f nd values of $outerKey  n placeholders"))
          outerKey -> placeholderValues
      }
  }

  def crossProduct[T](seqOfSeqOf ems: Seq[Seq[T]]): Seq[L st[T]] = {
     f (seqOfSeqOf ems. sEmpty) {
      L st(N l)
    } else {
      for {
        // for every  em  n t   ad l st
         em <- seqOfSeqOf ems. ad
        // for every result (L st) based on t  cross-product of ta l
        resultL st <- crossProduct(seqOfSeqOf ems.ta l)
      } y eld {
         em :: resultL st
      }
    }
  }

  def generateTemplateKey(matc d: Matc dPlaceholder): Str ng = {
    matc d. nnerKey match {
      case None => wrap(matc d.outerKey)
      case So ( nnerKeyStr ng) => wrap(matc d.outerKey + "\\[" +  nnerKeyStr ng + "\\]")
    }
  }

  // G ven an  nstance and a f eld na , use reflect on to get  s value.
  def getF eldValue[T: ClassTag](
    m rror: M rror,
    cls: T,
    accessors: Map[Str ng,  thodSymbol],
    f eldNa : Str ng
  ): Str ng = {
    val  nstance:  nstanceM rror = m rror.reflect(cls)
    val accessor = accessors.getOrElse(
      f eldNa ,
      throw new Runt  Except on(s"Fa led to f nd value of $f eldNa  for $cls"))
     nstance.reflectF eld(accessor).get.toStr ng // .get  s safe due to c ck above
  }

  // G ven an  nstance, use reflect on to get a mapp ng for f eld na  -> symbol
  def caseAccessors[T: ClassTag](m rror: M rror, cls: T): Map[Str ng,  thodSymbol] = {
    val classSymbol = m rror.classSymbol(cls.getClass)
    classSymbol.toType. mbers.collect {
      case m:  thodSymbol  f m. sCaseAccessor => (m.na .toStr ng -> m)
    }.toMap
  }
}
