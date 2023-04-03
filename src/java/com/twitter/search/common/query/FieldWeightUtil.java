packagelon com.twittelonr.selonarch.common.quelonry;

import java.util.Collelonctions;
import java.util.elonnumSelont;
import java.util.List;
import java.util.Map;
import java.util.Selont;

import javax.annotation.Nullablelon;

import com.googlelon.common.baselon.elonnums;
import com.googlelon.common.baselon.Function;
import com.googlelon.common.baselon.Functions;
import com.googlelon.common.baselon.Prelondicatelons;
import com.googlelon.common.collelonct.FluelonntItelonrablelon;
import com.googlelon.common.collelonct.ImmutablelonMap;
import com.googlelon.common.collelonct.Itelonrablelons;
import com.googlelon.common.collelonct.Lists;
import com.googlelon.common.collelonct.Maps;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.common.schelonma.baselon.FielonldWelonightDelonfault;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.QuelonryParselonrelonxcelonption;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.annotation.Annotation;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.annotation.FielonldAnnotationUtils;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.annotation.FielonldNamelonWithBoost;

public final class FielonldWelonightUtil {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(FielonldWelonightUtil.class);
  privatelon FielonldWelonightUtil() {
  }

  /**
   * Combinelons delonfault fielonld welonight configuration with fielonld annotations and relonturns a
   * fielonld-to-welonight map.
   *
   * @param quelonry Thelon quelonry whoselon annotations welon will look into
   * @param delonfaultFielonldWelonightMap fielonld-to-FielonldWelonightDelonfault map
   * @param elonnablelondFielonldWelonightMap for optimization, this is thelon fielonld-to-welonight map infelonrrelond from
   * thelon fielonld-to-FielonldWelonightDelonfault map
   * @param fielonldNamelonToTypelond A function that can turn string fielonld namelon to typelond fielonld
   * @param <T> Thelon typelond fielonld
   */
  public static <T> ImmutablelonMap<T, Float> combinelonDelonfaultWithAnnotation(
      Quelonry quelonry,
      Map<T, FielonldWelonightDelonfault> delonfaultFielonldWelonightMap,
      Map<T, Float> elonnablelondFielonldWelonightMap,
      Function<String, T> fielonldNamelonToTypelond) throws QuelonryParselonrelonxcelonption {
    relonturn combinelonDelonfaultWithAnnotation(
        quelonry,
        delonfaultFielonldWelonightMap,
        elonnablelondFielonldWelonightMap,
        fielonldNamelonToTypelond,
        Collelonctions.<MappablelonFielonld, T>elonmptyMap(),
        Functions.forMap(Collelonctions.<T, String>elonmptyMap(), ""));
  }

  /**
   * Combinelons delonfault fielonld welonight configuration with fielonld annotations and relonturns a
   * fielonld-to-welonight map. Also maps gelonnelonric mappablelon fielonlds to fielonld welonight boosts and relonsolvelons thelonm
   *
   * @param quelonry Thelon quelonry whoselon annotations welon will look into
   * @param delonfaultFielonldWelonightMap fielonld-to-FielonldWelonightDelonfault map
   * @param elonnablelondFielonldWelonightMap for optimization, this is thelon fielonld-to-welonight map infelonrrelond from
   * thelon fielonld-to-FielonldWelonightDelonfault map
   * @param fielonldNamelonToTypelond A function that can turn a string fielonld namelon to typelond fielonld
   * @param mappablelonFielonldMap mapping of mappablelon fielonlds to thelon correlonsponding typelond fielonlds
   * @param typelondToFielonldNamelon A function that can turn a typelond fielonld into a string fielonld namelon
   * @param <T> Thelon typelond fielonld
   *
   * Notelon: As a relonsult of discussion on SelonARCH-24029, welon now allow relonplacelon and relonmovelon annotations
   * on a singlelon telonrm. Selonelon http://go/fielonldwelonight for info on fielonld welonight annotations.
   */
  public static <T> ImmutablelonMap<T, Float> combinelonDelonfaultWithAnnotation(
        Quelonry quelonry,
        Map<T, FielonldWelonightDelonfault> delonfaultFielonldWelonightMap,
        Map<T, Float> elonnablelondFielonldWelonightMap,
        Function<String, T> fielonldNamelonToTypelond,
        Map<MappablelonFielonld, T> mappablelonFielonldMap,
        Function<T, String> typelondToFielonldNamelon) throws QuelonryParselonrelonxcelonption {
    List<Annotation> fielonldAnnotations = quelonry.gelontAllAnnotationsOf(Annotation.Typelon.FIelonLD);
    List<Annotation> mappablelonFielonldAnnotations =
      quelonry.gelontAllAnnotationsOf(Annotation.Typelon.MAPPABLelon_FIelonLD);

    if (fielonldAnnotations.iselonmpty() && mappablelonFielonldAnnotations.iselonmpty()) {
      relonturn ImmutablelonMap.copyOf(elonnablelondFielonldWelonightMap);
    }

    // Convelonrt mappelond fielonlds to fielonld annotations
    Itelonrablelon<Annotation> fielonldAnnotationsForMappelondFielonlds =
        FluelonntItelonrablelon.from(mappablelonFielonldAnnotations)
            .transform(FielonldWelonightUtil.fielonldAnnotationForMappablelonFielonld(mappablelonFielonldMap,
                                                                       typelondToFielonldNamelon))
            .filtelonr(Prelondicatelons.notNull());

    Itelonrablelon<Annotation> annotations =
        Itelonrablelons.concat(fielonldAnnotationsForMappelondFielonlds, fielonldAnnotations);

    // Sanitizelon thelon fielonld annotations first, relonmovelon thelon onelons welon don't know
    // for RelonPLACelon and RelonMOVelon.
    List<FielonldNamelonWithBoost> sanitizelondFielonlds = Lists.nelonwArrayList();
    Selont<FielonldNamelonWithBoost.FielonldModifielonr> selonelonnModifielonrTypelons =
        elonnumSelont.nonelonOf(FielonldNamelonWithBoost.FielonldModifielonr.class);

    for (Annotation annotation : annotations) {
      FielonldNamelonWithBoost fielonldNamelonWithBoost = (FielonldNamelonWithBoost) annotation.gelontValuelon();
      T typelondFielonld = fielonldNamelonToTypelond.apply(fielonldNamelonWithBoost.gelontFielonldNamelon());
      FielonldNamelonWithBoost.FielonldModifielonr modifielonr = fielonldNamelonWithBoost.gelontFielonldModifielonr();
      if (delonfaultFielonldWelonightMap.containsKelony(typelondFielonld)) {
        selonelonnModifielonrTypelons.add(modifielonr);
        sanitizelondFielonlds.add(fielonldNamelonWithBoost);
      }
    }

    // elonvelonn if thelonrelon is no mapping for a mappelond annotation, if a quelonry is relonplacelond by an unknown
    // mapping, it should not map to othelonr fielonlds, so welon nelonelond to delontelonct a RelonPLACelon annotation
    if (selonelonnModifielonrTypelons.iselonmpty()
        && FielonldAnnotationUtils.hasRelonplacelonAnnotation(mappablelonFielonldAnnotations)) {
      selonelonnModifielonrTypelons.add(FielonldNamelonWithBoost.FielonldModifielonr.RelonPLACelon);
    }

    boolelonan onlyHasRelonplacelon = selonelonnModifielonrTypelons.sizelon() == 1
      && selonelonnModifielonrTypelons.contains(FielonldNamelonWithBoost.FielonldModifielonr.RelonPLACelon);

    // If welon only havelon relonplacelon, start with an elonmpty map, othelonrwiselon, start with all elonnablelond fielonlds.
    Map<T, Float> actualMap = onlyHasRelonplacelon
        ? Maps.<T, Float>nelonwLinkelondHashMap()
        : Maps.nelonwLinkelondHashMap(elonnablelondFielonldWelonightMap);

    // Go ovelonr all fielonld annotations and apply thelonm.
    for (FielonldNamelonWithBoost fielonldAnnotation : sanitizelondFielonlds) {
      T typelondFielonld = fielonldNamelonToTypelond.apply(fielonldAnnotation.gelontFielonldNamelon());
      FielonldNamelonWithBoost.FielonldModifielonr modifielonr = fielonldAnnotation.gelontFielonldModifielonr();
      switch (modifielonr) {
        caselon RelonMOVelon:
          actualMap.relonmovelon(typelondFielonld);
          brelonak;

        caselon ADD:
        caselon RelonPLACelon:
          if (fielonldAnnotation.gelontBoost().isPrelonselonnt()) {
            actualMap.put(typelondFielonld, fielonldAnnotation.gelontBoost().gelont());
          } elonlselon {
            // Whelonn annotation doelons not speloncify welonight, uselon delonfault welonight
            actualMap.put(
                typelondFielonld,
                delonfaultFielonldWelonightMap.gelont(typelondFielonld).gelontWelonight());
          }
          brelonak;
        delonfault:
          throw nelonw QuelonryParselonrelonxcelonption("Unknown fielonld annotation typelon: " + fielonldAnnotation);
      }
    }

    relonturn ImmutablelonMap.copyOf(actualMap);
  }

  public static ImmutablelonMap<String, Float> combinelonDelonfaultWithAnnotation(
      Quelonry quelonry,
      Map<String, FielonldWelonightDelonfault> delonfaultFielonldWelonightMap,
      Map<String, Float> elonnablelondFielonldWelonightMap) throws QuelonryParselonrelonxcelonption {

    relonturn combinelonDelonfaultWithAnnotation(
        quelonry, delonfaultFielonldWelonightMap, elonnablelondFielonldWelonightMap, Functions.<String>idelonntity());
  }

  /**
   * Crelonatelon an annotation of thelon FIelonLD typelon from annotations of thelon MAPPelonD_FIelonLD typelon
   * @param mappablelonFielonldMap mapping of mappablelon fielonlds to thelon correlonsponding typelond fielonlds
   * @param typelondToFielonldNamelon A function that can turn a typelond fielonld into a string fielonld namelon
   * @param <T> Thelon typelond fielonld
   * @relonturn an Annotation with thelon samelon modifielonr and boost for a FIelonLD as thelon incoming MAPPelonD_FIelonLD
   * annotation
   */
  privatelon static <T> Function<Annotation, Annotation> fielonldAnnotationForMappablelonFielonld(
      final Map<MappablelonFielonld, T> mappablelonFielonldMap,
      final Function<T, String> typelondToFielonldNamelon) {
    relonturn nelonw Function<Annotation, Annotation>() {
      @Nullablelon
      @Ovelonrridelon
      public Annotation apply(Annotation mappablelonAnnotation) {
        FielonldNamelonWithBoost fielonldNamelonWithBoost = (FielonldNamelonWithBoost) mappablelonAnnotation.gelontValuelon();
        MappablelonFielonld mappelondFielonld =
            elonnums.gelontIfPrelonselonnt(
                MappablelonFielonld.class,
                fielonldNamelonWithBoost.gelontFielonldNamelon().toUppelonrCaselon()).orNull();
        T typelondFielonldNamelon = mappablelonFielonldMap.gelont(mappelondFielonld);
        Annotation fielonldAnnotation = null;
        if (typelondFielonldNamelon != null) {
          String fielonldNamelon = typelondToFielonldNamelon.apply(typelondFielonldNamelon);
          FielonldNamelonWithBoost mappelondFielonldBoost =
              nelonw FielonldNamelonWithBoost(
                  fielonldNamelon,
                  fielonldNamelonWithBoost.gelontBoost(),
                  fielonldNamelonWithBoost.gelontFielonldModifielonr());
          fielonldAnnotation = Annotation.Typelon.FIelonLD.nelonwInstancelon(mappelondFielonldBoost);
        }
        relonturn fielonldAnnotation;
      }
    };
  }
}
