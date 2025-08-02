package com.twitter.search.common.schema;

import java.io.IOEtwitterception;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicLong;
import javatwitter.annotation.Nullable;
import javatwitter.annotation.concurrent.Immutable;
import javatwitter.annotation.concurrent.ThreadSafe;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.facet.FacetsConfig;
import org.apache.lucene.indetwitter.DocValuesType;
import org.apache.lucene.indetwitter.FieldInfos;
import org.apache.lucene.indetwitter.IndetwitterOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.common.collections.Pair;
import com.twitter.common.tetwittert.util.TokenStreamSerializer;
import com.twitter.search.common.features.EtwitterternalTweetFeature;
import com.twitter.search.common.features.SearchResultFeature;
import com.twitter.search.common.features.thrift.ThriftSearchFeatureSchema;
import com.twitter.search.common.features.thrift.ThriftSearchFeatureSchemaEntry;
import com.twitter.search.common.features.thrift.ThriftSearchFeatureSchemaSpecifier;
import com.twitter.search.common.features.thrift.ThriftSearchFeatureType;
import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.common.metrics.SearchLongGauge;
import com.twitter.search.common.schema.base.EarlybirdFieldType;
import com.twitter.search.common.schema.base.FeatureConfiguration;
import com.twitter.search.common.schema.base.FieldWeightDefault;
import com.twitter.search.common.schema.base.ImmutableSchemaInterface;
import com.twitter.search.common.schema.base.IndetwitteredNumericFieldSettings;
import com.twitter.search.common.schema.thriftjava.ThriftAnalyzer;
import com.twitter.search.common.schema.thriftjava.ThriftCSFFieldSettings;
import com.twitter.search.common.schema.thriftjava.ThriftCSFType;
import com.twitter.search.common.schema.thriftjava.ThriftCSFViewSettings;
import com.twitter.search.common.schema.thriftjava.ThriftFacetFieldSettings;
import com.twitter.search.common.schema.thriftjava.ThriftFieldConfiguration;
import com.twitter.search.common.schema.thriftjava.ThriftFieldSettings;
import com.twitter.search.common.schema.thriftjava.ThriftIndetwitteredFieldSettings;
import com.twitter.search.common.schema.thriftjava.ThriftSchema;
import com.twitter.search.common.schema.thriftjava.ThriftSearchFieldSettings;
import com.twitter.search.common.schema.thriftjava.ThriftTokenStreamSerializer;

/**
 * A schema instance that does not change at run time.
 */
@Immutable @ThreadSafe
public class ImmutableSchema implements ImmutableSchemaInterface {
  private static final Logger LOG = LoggerFactory.getLogger(ImmutableSchema.class);
  private static final ImmutableSet<ThriftCSFType> CAN_FACET_ON_CSF_TYPES =
      ImmutableSet.<ThriftCSFType>builder()
          .add(ThriftCSFType.BYTE)
          .add(ThriftCSFType.INT)
          .add(ThriftCSFType.LONG)
          .build();

  private static final SearchCounter FEATURES_EtwitterISTED_IN_OLD_SCHEMA =
      SearchCounter.etwitterport("features_etwitteristed_in_old_schema");

  // Currently our indetwitter uses 4 bits to store the facet field id.
  public static final int MAtwitter_FACET_FIELD_ID = 15;

  public static final String HF_TERM_PAIRS_FIELD = "hf_term_pairs";
  public static final String HF_PHRASE_PAIRS_FIELD = "hf_phrase_pairs";

  private final ImmutableMap<Integer, FieldInfo> fieldSettingsMapById;
  private final ImmutableMap<String, FieldInfo> fieldSettingsMapByName;
  private final ImmutableMap<String, FeatureConfiguration> featureConfigMapByName;
  private final ImmutableMap<Integer, FeatureConfiguration> featureConfigMapById;

  @Nullable
  private final ThriftAnalyzer defaultAnalyzer;
  private final AnalyzerFactory analyzerFactory;

  private final ImmutableMap<String, FieldWeightDefault> fieldWeightMap;
  private final Map<String, FieldInfo> facetNameToFieldMap = Maps.newHashMap();
  private final int numFacetFields;
  private final ImmutableSet<FieldInfo> csfFacetFields;

  // This is the search result feature schema - it has the definition for all the column stride
  // view fields.
  private final ThriftSearchFeatureSchema searchFeatureSchema;

  private final int majorVersionNumber;
  private final int minorVersionNumber;
  private final String versionDesc;
  private final boolean isVersionOfficial;

  /**
   * Construct a Schema instance with the given ThriftSchema and AnalyzerFactory.
   */
  public ImmutableSchema(ThriftSchema thriftSchema,
                         AnalyzerFactory analyzerFactory,
                         String featureSchemaVersionPrefitwitter) throws SchemaValidationEtwitterception {
    Pair<Integer, String> versionPair = parseVersionString(thriftSchema.getVersion());
    this.majorVersionNumber = thriftSchema.getMajorVersionNumber();
    this.minorVersionNumber = thriftSchema.getMinorVersionNumber();
    this.versionDesc = versionPair.getSecond();
    this.isVersionOfficial = thriftSchema.isVersionIsOfficial();

    this.analyzerFactory = analyzerFactory;

    Map<Integer, FieldInfo> tmpMap = Maps.newLinkedHashMap();
    Set<FieldInfo> tmpSet = Sets.newHashSet();

    if (thriftSchema.isSetDefaultAnalyzer()) {
      this.defaultAnalyzer = thriftSchema.getDefaultAnalyzer().deepCopy();
    } else {
      this.defaultAnalyzer = null;
    }

    Map<Integer, ThriftFieldConfiguration> configs = thriftSchema.getFieldConfigs();

    // Collect all the CSF Views, so that we can later verify that they are appropriately
    // configured once we've processed all the other field settings.
    Map<Integer, ThriftFieldConfiguration> csfViewFields = Maps.newHashMap();
    boolean requiresHfPairFields = false;
    boolean hasHfTermPairField = false;
    boolean hasHfPhrasePairField = false;
    int numFacets = 0;
    for (Map.Entry<Integer, ThriftFieldConfiguration> entry : configs.entrySet()) {
      int fieldId = entry.getKey();

      if (tmpMap.containsKey(fieldId)) {
        throw new SchemaValidationEtwitterception("Duplicate field id " + fieldId);
      }

      ThriftFieldConfiguration config = entry.getValue();
      FieldInfo fieldInfo = parseThriftFieldSettings(fieldId, config, csfViewFields);
      validate(fieldInfo);
      if (fieldInfo.getFieldType().isFacetField()) {
        if (numFacets > MAtwitter_FACET_FIELD_ID) {
          throw new SchemaValidationEtwitterception(
              "Matwitterimum supported facet field ID is:  " + MAtwitter_FACET_FIELD_ID);
        }
        numFacets++;
        facetNameToFieldMap.put(fieldInfo.getFieldType().getFacetName(), fieldInfo);

        if (fieldInfo.getFieldType().isUseCSFForFacetCounting()) {
          tmpSet.add(fieldInfo);
        }
      }

      tmpMap.put(fieldId, fieldInfo);

      if (fieldInfo.getFieldType().isIndetwitterHFTermPairs()) {
        requiresHfPairFields = true;
      }
      if (fieldInfo.getName().equals(HF_TERM_PAIRS_FIELD)) {
        hasHfTermPairField = true;
      }
      if (fieldInfo.getName().equals(HF_PHRASE_PAIRS_FIELD)) {
        hasHfPhrasePairField = true;
      }
    }

    this.numFacetFields = numFacets;
    this.csfFacetFields = ImmutableSet.copyOf(tmpSet);

    // If any field requires high frequency term/phrase pair fields, make sure they etwitterist
    if (requiresHfPairFields) {
      if (!hasHfTermPairField || !hasHfPhrasePairField) {
        throw new SchemaValidationEtwitterception(
            "High frequency term/phrase pair fields do not etwitterist in the schema.");
      }
    }

    this.fieldSettingsMapById = ImmutableMap.copyOf(tmpMap);

    Pair<ImmutableMap<String, FeatureConfiguration>, ImmutableMap<Integer, FeatureConfiguration>>
        featureConfigMapPair = buildFeatureMaps(csfViewFields);
    this.featureConfigMapByName = featureConfigMapPair.getFirst();
    this.featureConfigMapById = featureConfigMapPair.getSecond();

    for (ThriftFieldConfiguration csfViewField : csfViewFields.values()) {
      SchemaBuilder.verifyCSFViewSettings(configs, csfViewField);
    }

    ImmutableMap.Builder<String, FieldInfo> builder = ImmutableMap.builder();

    for (FieldInfo info : fieldSettingsMapById.values()) {
      info.getFieldType().freeze();
      builder.put(info.getName(), info);
    }
    this.fieldSettingsMapByName = builder.build();

    ImmutableMap.Builder<String, FieldWeightDefault> fieldWeightMapBuilder = ImmutableMap.builder();

    for (FieldInfo fi : getFieldInfos()) {
      // CSF fields are not searchable. All other fields are.
      if (fi.getFieldType().isIndetwitteredField()) {
        fieldWeightMapBuilder.put(
            fi.getName(),
            new FieldWeightDefault(
                fi.getFieldType().isTetwittertSearchableByDefault(),
                fi.getFieldType().getTetwittertSearchableFieldWeight()));
      }
    }

    this.fieldWeightMap = fieldWeightMapBuilder.build();
    // Create features with etwittertra Earlybird derived fields, etwittertra fields won't change the version
    // but they do change the checksum.
    this.searchFeatureSchema = createSearchResultFeatureSchema(
        featureSchemaVersionPrefitwitter, fieldSettingsMapByName, featureConfigMapByName);
  }

  /**
   * Add a set of features to a schema if they don't etwitterist yet, and update the schema checksum.
   * if there's conflict, RuntimeEtwitterception will be thrown.
   * Old map won't be touched, a new map will be returned will old and new data combined.
   */
  public static Map<Integer, ThriftSearchFeatureSchemaEntry> appendToFeatureSchema(
      Map<Integer, ThriftSearchFeatureSchemaEntry> oldEntryMap,
      Set<? etwittertends SearchResultFeature> features) throws SchemaValidationEtwitterception {
    if (oldEntryMap == null) {
      throw new SchemaValidationEtwitterception(
          "Cannot append features to schema, the entryMap is null");
    }
    // make a copy of the etwitteristing map
    ImmutableMap.Builder<Integer, ThriftSearchFeatureSchemaEntry> builder =
        ImmutableSortedMap.<Integer, ThriftSearchFeatureSchemaEntry>naturalOrder()
            .putAll(oldEntryMap);

    for (SearchResultFeature feature : features) {
      if (oldEntryMap.containsKey(feature.getId())) {
        FEATURES_EtwitterISTED_IN_OLD_SCHEMA.increment();
      } else {
        builder.put(feature.getId(), new ThriftSearchFeatureSchemaEntry()
            .setFeatureName(feature.getName())
            .setFeatureType(feature.getType()));
      }
    }
    return builder.build();
  }

  /**
   * Append etwitterternal features to create a new schema.
   * @param oldSchema The old schema to build on top of
   * @param features a list of features to be appended to the schema
   * @param versionSuffitwitter the version suffitwitter, if not-null, it will be attached to the end of
   * original schema's version.
   * @return A new schema object with the appended fields
   * @throws SchemaValidationEtwitterception thrown when the checksum cannot be computed
   */
  public static ThriftSearchFeatureSchema appendToCreateNewFeatureSchema(
      ThriftSearchFeatureSchema oldSchema,
      Set<EtwitterternalTweetFeature> features,
      @Nullable String versionSuffitwitter) throws SchemaValidationEtwitterception {

    ThriftSearchFeatureSchema newSchema = new ThriftSearchFeatureSchema();
    // copy over all the entries plus the new ones
    newSchema.setEntries(appendToFeatureSchema(oldSchema.getEntries(), features));

    ThriftSearchFeatureSchemaSpecifier spec = new ThriftSearchFeatureSchemaSpecifier();
    // the version is directly inherited or with a suffitwitter
    Preconditions.checkArgument(versionSuffitwitter == null || !versionSuffitwitter.isEmpty());
    spec.setVersion(versionSuffitwitter == null
        ? oldSchema.getSchemaSpecifier().getVersion()
        : oldSchema.getSchemaSpecifier().getVersion() + versionSuffitwitter);
    spec.setChecksum(getChecksum(newSchema.getEntries()));
    newSchema.setSchemaSpecifier(spec);
    return newSchema;
  }

  @Override
  public FieldInfos getLuceneFieldInfos(Predicate<String> acceptedFields) {
    List<org.apache.lucene.indetwitter.FieldInfo> acceptedFieldInfos = Lists.newArrayList();
    for (FieldInfo fi : getFieldInfos()) {
      if (acceptedFields == null || acceptedFields.apply(fi.getName())) {
        acceptedFieldInfos.add(convert(fi.getName(), fi.getFieldId(), fi.getFieldType()));
      }
    }
    return new FieldInfos(acceptedFieldInfos.toArray(
        new org.apache.lucene.indetwitter.FieldInfo[acceptedFieldInfos.size()]));
  }

  private FieldInfo parseThriftFieldSettings(int fieldId, ThriftFieldConfiguration fieldConfig,
                                             Map<Integer, ThriftFieldConfiguration> csfViewFields)
      throws SchemaValidationEtwitterception {
    FieldInfo fieldInfo
        = new FieldInfo(fieldId, fieldConfig.getFieldName(), new EarlybirdFieldType());
    ThriftFieldSettings fieldSettings = fieldConfig.getSettings();


    boolean settingFound = false;

    if (fieldSettings.isSetIndetwitteredFieldSettings()) {
      if (fieldSettings.isSetCsfFieldSettings() || fieldSettings.isSetCsfViewSettings()) {
        throw new SchemaValidationEtwitterception("ThriftFieldSettings: Only one of "
            + "'indetwitteredFieldSettings', 'csfFieldSettings', 'csfViewSettings' can be set.");
      }

      applyIndetwitteredFieldSettings(fieldInfo, fieldSettings.getIndetwitteredFieldSettings());
      settingFound = true;
    }

    if (fieldSettings.isSetCsfFieldSettings()) {
      if (fieldSettings.isSetIndetwitteredFieldSettings() || fieldSettings.isSetCsfViewSettings()) {
        throw new SchemaValidationEtwitterception("ThriftFieldSettings: Only one of "
            + "'indetwitteredFieldSettings', 'csfFieldSettings', 'csfViewSettings' can be set.");
      }

      applyCsfFieldSettings(fieldInfo, fieldSettings.getCsfFieldSettings());
      settingFound = true;
    }

    if (fieldSettings.isSetFacetFieldSettings()) {
      if (!fieldSettings.isSetIndetwitteredFieldSettings() && !(fieldSettings.isSetCsfFieldSettings()
          && fieldSettings.getFacetFieldSettings().isUseCSFForFacetCounting()
          && CAN_FACET_ON_CSF_TYPES.contains(fieldSettings.getCsfFieldSettings().getCsfType()))) {
        throw new SchemaValidationEtwitterception("ThriftFieldSettings: 'facetFieldSettings' can only be "
            + "used in combination with 'indetwitteredFieldSettings' or with 'csfFieldSettings' "
            + "where 'isUseCSFForFacetCounting' was set to true and ThriftCSFType is a type that "
            + "can be faceted on.");
      }

      applyFacetFieldSettings(fieldInfo, fieldSettings.getFacetFieldSettings());
      settingFound = true;
    }

    if (fieldSettings.isSetCsfViewSettings()) {
      if (fieldSettings.isSetIndetwitteredFieldSettings() || fieldSettings.isSetCsfFieldSettings()) {
        throw new SchemaValidationEtwitterception("ThriftFieldSettings: Only one of "
            + "'indetwitteredFieldSettings', 'csfFieldSettings', 'csfViewSettings' can be set.");
      }

      // add this field now, but apply settings later to make sure the base field was added properly
      // before
      csfViewFields.put(fieldId, fieldConfig);
      settingFound = true;
    }

    if (!settingFound) {
      throw new SchemaValidationEtwitterception("ThriftFieldSettings: One of 'indetwitteredFieldSettings', "
          + "'csfFieldSettings' or 'facetFieldSettings' must be set.");
    }

    // search field settings are optional
    if (fieldSettings.isSetSearchFieldSettings()) {
      if (!fieldSettings.isSetIndetwitteredFieldSettings()) {
        throw new SchemaValidationEtwitterception(
            "ThriftFieldSettings: 'searchFieldSettings' can only be "
                + "used in combination with 'indetwitteredFieldSettings'");
      }

      applySearchFieldSettings(fieldInfo, fieldSettings.getSearchFieldSettings());
    }

    return fieldInfo;
  }

  private void applyCsfFieldSettings(FieldInfo fieldInfo, ThriftCSFFieldSettings settings)
      throws SchemaValidationEtwitterception {
    // csfType is required - no need to check if it's set
    fieldInfo.getFieldType().setDocValuesType(DocValuesType.NUMERIC);
    fieldInfo.getFieldType().setCsfType(settings.getCsfType());

    if (settings.isVariableLength()) {
      fieldInfo.getFieldType().setDocValuesType(DocValuesType.BINARY);
      fieldInfo.getFieldType().setCsfVariableLength();
    } else {
      if (settings.isSetFitwitteredLengthSettings()) {
        fieldInfo.getFieldType().setCsfFitwitteredLengthSettings(
            settings.getFitwitteredLengthSettings().getNumValuesPerDoc(),
            settings.getFitwitteredLengthSettings().isUpdateable());
        if (settings.getFitwitteredLengthSettings().getNumValuesPerDoc() > 1) {
          fieldInfo.getFieldType().setDocValuesType(DocValuesType.BINARY);
        }
      } else {
        throw new SchemaValidationEtwitterception(
            "ThriftCSFFieldSettings: Either variableLength should be set to 'true', "
                + "or fitwitteredLengthSettings should be set.");
      }
    }

    fieldInfo.getFieldType().setCsfLoadIntoRam(settings.isLoadIntoRAM());
    if (settings.isSetDefaultValue()) {
      fieldInfo.getFieldType().setCsfDefaultValue(settings.getDefaultValue());
    }
  }

  private void applyCsfViewFieldSettings(FieldInfo fieldInfo, FieldInfo baseField,
                                         ThriftCSFViewSettings settings)
      throws SchemaValidationEtwitterception {
    // csfType is required - no need to check if it's set
    fieldInfo.getFieldType().setDocValuesType(DocValuesType.NUMERIC);
    fieldInfo.getFieldType().setCsfType(settings.getCsfType());

    fieldInfo.getFieldType().setCsfFitwitteredLengthSettings(1 /* numValuesPerDoc*/,
        false /* updateable*/);

    fieldInfo.getFieldType().setCsfViewSettings(fieldInfo.getName(), settings, baseField);
  }

  private void applyFacetFieldSettings(FieldInfo fieldInfo, ThriftFacetFieldSettings settings) {
    if (settings.isSetFacetName()) {
      fieldInfo.getFieldType().setFacetName(settings.getFacetName());
    } else {
      // fall back to field name if no facet name is etwitterplicitly provided
      fieldInfo.getFieldType().setFacetName(fieldInfo.getName());
    }
    fieldInfo.getFieldType().setStoreFacetSkiplist(settings.isStoreSkiplist());
    fieldInfo.getFieldType().setStoreFacetOffensiveCounters(settings.isStoreOffensiveCounters());
    fieldInfo.getFieldType().setUseCSFForFacetCounting(settings.isUseCSFForFacetCounting());
  }

  private void applyIndetwitteredFieldSettings(FieldInfo fieldInfo, ThriftIndetwitteredFieldSettings settings)
      throws SchemaValidationEtwitterception {
    fieldInfo.getFieldType().setIndetwitteredField(true);
    fieldInfo.getFieldType().setStored(settings.isStored());
    fieldInfo.getFieldType().setTokenized(settings.isTokenized());
    fieldInfo.getFieldType().setStoreTermVectors(settings.isStoreTermVectors());
    fieldInfo.getFieldType().setStoreTermVectorOffsets(settings.isStoreTermVectorOffsets());
    fieldInfo.getFieldType().setStoreTermVectorPositions(settings.isStoreTermVectorPositions());
    fieldInfo.getFieldType().setStoreTermVectorPayloads(settings.isStoreTermVectorPayloads());
    fieldInfo.getFieldType().setOmitNorms(settings.isOmitNorms());
    fieldInfo.getFieldType().setIndetwitterHFTermPairs(settings.isIndetwitterHighFreqTermPairs());
    fieldInfo.getFieldType().setUseTweetSpecificNormalization(
        settings.deprecated_performTweetSpecificNormalizations);

    if (settings.isSetIndetwitterOptions()) {
      switch (settings.getIndetwitterOptions()) {
        case DOCS_ONLY :
          fieldInfo.getFieldType().setIndetwitterOptions(IndetwitterOptions.DOCS);
          break;
        case DOCS_AND_FREQS :
          fieldInfo.getFieldType().setIndetwitterOptions(IndetwitterOptions.DOCS_AND_FREQS);
          break;
        case DOCS_AND_FREQS_AND_POSITIONS :
          fieldInfo.getFieldType().setIndetwitterOptions(IndetwitterOptions.DOCS_AND_FREQS_AND_POSITIONS);
          break;
        case DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS :
          fieldInfo.getFieldType().setIndetwitterOptions(
              IndetwitterOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
          break;
        default:
          throw new SchemaValidationEtwitterception("Unknown value for IndetwitterOptions: "
              + settings.getIndetwitterOptions());
      }
    } else if (settings.isIndetwittered()) {
      // default for backward-compatibility
      fieldInfo.getFieldType().setIndetwitterOptions(IndetwitterOptions.DOCS_AND_FREQS_AND_POSITIONS);
    }

    fieldInfo.getFieldType().setStorePerPositionPayloads(settings.isStorePerPositionPayloads());
    fieldInfo.getFieldType().setDefaultPayloadLength(
        settings.getDefaultPerPositionPayloadLength());
    fieldInfo.getFieldType().setBecomesImmutable(!settings.isSupportOutOfOrderAppends());
    fieldInfo.getFieldType().setSupportOrderedTerms(settings.isSupportOrderedTerms());
    fieldInfo.getFieldType().setSupportTermTetwittertLookup(settings.isSupportTermTetwittertLookup());

    if (settings.isSetNumericFieldSettings()) {
      fieldInfo.getFieldType().setNumericFieldSettings(
          new IndetwitteredNumericFieldSettings(settings.getNumericFieldSettings()));
    }

    if (settings.isSetTokenStreamSerializer()) {
      fieldInfo.getFieldType().setTokenStreamSerializerBuilder(
          buildTokenStreamSerializerProvider(settings.getTokenStreamSerializer()));
    }
  }

  private void applySearchFieldSettings(FieldInfo fieldInfo, ThriftSearchFieldSettings settings)
      throws SchemaValidationEtwitterception {
    fieldInfo.getFieldType().setTetwittertSearchableFieldWeight(
        (float) settings.getTetwittertSearchableFieldWeight());
    fieldInfo.getFieldType().setTetwittertSearchableByDefault(settings.isTetwittertDefaultSearchable());
  }

  private void validate(FieldInfo fieldInfo) throws SchemaValidationEtwitterception {
  }

  private TokenStreamSerializer.Builder buildTokenStreamSerializerProvider(
      final ThriftTokenStreamSerializer settings) {
    TokenStreamSerializer.Builder builder = TokenStreamSerializer.builder();
    for (String serializerName : settings.getAttributeSerializerClassNames()) {
      try {
        builder.add((TokenStreamSerializer.AttributeSerializer) Class.forName(serializerName)
            .newInstance());
      } catch (InstantiationEtwitterception e) {
        throw new RuntimeEtwitterception(
            "Unable to instantiate AttributeSerializer for name " + serializerName);
      } catch (IllegalAccessEtwitterception e) {
        throw new RuntimeEtwitterception(
            "Unable to instantiate AttributeSerializer for name " + serializerName);
      } catch (ClassNotFoundEtwitterception e) {
        throw new RuntimeEtwitterception(
            "Unable to instantiate AttributeSerializer for name " + serializerName);
      }
    }
    return builder;
  }

  @Override
  public FacetsConfig getFacetsConfig() {
    FacetsConfig facetsConfig = new FacetsConfig();

    for (String facetName : facetNameToFieldMap.keySet()) {
      // set multiValued = true as default, since we're using SortedSetDocValues facet, in which,
      // there is no difference between multiValued true or false for the real facet, but only the
      // checking of the values.
      facetsConfig.setMultiValued(facetName, true);
    }

    return facetsConfig;
  }

  @Override
  public Analyzer getDefaultAnalyzer(ThriftAnalyzer override) {
    if (override != null) {
      return analyzerFactory.getAnalyzer(override);
    }

    if (defaultAnalyzer != null) {
      return analyzerFactory.getAnalyzer(defaultAnalyzer);
    }

    return new SearchWhitespaceAnalyzer();
  }

  @Override
  public ImmutableCollection<FieldInfo> getFieldInfos() {
    return fieldSettingsMapById.values();
  }

  /**
   * This is the preferred method to check whether a field configuration is in schema.
   * One can also use getFieldInfo and do null checks, but should be careful about etwittercessive
   * warning logging resulting from looking up fields not in schema.
   */
  @Override
  public boolean hasField(int fieldConfigId) {
    return fieldSettingsMapById.containsKey(fieldConfigId);
  }

  /**
   * This is the preferred method to check whether a field configuration is in schema.
   * One can also use getFieldInfo and do null checks, but should be careful about etwittercessive
   * warning logging resulting from looking up fields not in schema.
   */
  @Override
  public boolean hasField(String fieldName) {
    return fieldSettingsMapByName.containsKey(fieldName);
  }

  /**
   * Get FieldInfo for the given field id.
   * If the goal is to check whether a field is in the schema, use {@link #hasField(int)} instead.
   * This method logs a warning whenever it returns null.
   */
  @Override
  @Nullable
  public FieldInfo getFieldInfo(int fieldConfigId) {
    return getFieldInfo(fieldConfigId, null);
  }

  private org.apache.lucene.indetwitter.FieldInfo convert(String fieldName,
                                                    int indetwitter,
                                                    EarlybirdFieldType type) {
    return new org.apache.lucene.indetwitter.FieldInfo(
        fieldName,                          // String name
        indetwitter,                              // int number
        type.storeTermVectors(),            // boolean storeTermVector
        type.omitNorms(),                   // boolean omitNorms
        type.isStorePerPositionPayloads(),  // boolean storePayloads
        type.indetwitterOptions(),                // IndetwitterOptions indetwitterOptions
        type.docValuesType(),               // DocValuesType docValues
        -1,                                 // long dvGen
        Maps.<String, String>newHashMap(),  // Map<String, String> attributes
        0,                                  // int pointDataDimensionCount
        0,                                  // int pointIndetwitterDimensionCount
        0,                                  // int pointNumBytes
        false);                             // boolean softDeletesField
  }

  /**
   * Get FieldInfo for the given field name, or null if the field does not etwitterist.
   */
  @Override
  @Nullable
  public FieldInfo getFieldInfo(String fieldName) {
    return fieldSettingsMapByName.get(fieldName);
  }

  @Override
  public String getFieldName(int fieldConfigId) {
    FieldInfo fieldInfo = fieldSettingsMapById.get(fieldConfigId);
    return fieldInfo != null ? fieldInfo.getName() : null;
  }

  @Override
  public FieldInfo getFieldInfo(int fieldConfigId, ThriftFieldConfiguration override) {
    FieldInfo fieldInfo = fieldSettingsMapById.get(fieldConfigId);
    if (fieldInfo == null) {
      // This method is used to check the availability of fields by IDs,
      // so no warning is logged here (would be too verbose otherwise).
      return null;
    }

    if (override != null) {
      try {
        return merge(fieldConfigId, fieldInfo, override);
      } catch (SchemaValidationEtwitterception e) {
        throw new RuntimeEtwitterception(e);
      }
    }

    return fieldInfo;
  }

  @Override
  public int getNumFacetFields() {
    return numFacetFields;
  }

  @Override
  public FieldInfo getFacetFieldByFacetName(String facetName) {
    return facetNameToFieldMap.get(facetName);
  }

  @Override
  public FieldInfo getFacetFieldByFieldName(String fieldName) {
    FieldInfo fieldInfo = getFieldInfo(fieldName);
    return fieldInfo != null && fieldInfo.getFieldType().isFacetField() ? fieldInfo : null;
  }

  @Override
  public Collection<FieldInfo> getFacetFields() {
    return facetNameToFieldMap.values();
  }

  @Override
  public Collection<FieldInfo> getCsfFacetFields() {
    return csfFacetFields;
  }

  @Override
  public String getVersionDescription() {
    return versionDesc;
  }

  @Override
  public int getMajorVersionNumber() {
    return majorVersionNumber;
  }

  @Override
  public int getMinorVersionNumber() {
    return minorVersionNumber;
  }

  @Override
  public boolean isVersionOfficial() {
    return isVersionOfficial;
  }

  /**
   * Parses a version string like "16: renamed field twitter into y" into a version number and
   * a string description.
   * @return a Pair of the version number and the description
   */
  private static Pair<Integer, String> parseVersionString(String version)
      throws SchemaValidationEtwitterception {
    Preconditions.checkNotNull(version, "Schema must have a version number and description.");
    int colonIndetwitter = version.indetwitterOf(':');
    if (colonIndetwitter == -1) {
      throw new SchemaValidationEtwitterception("Malformed version string: " + version);
    }
    try {
      int versionNumber = Integer.parseInt(version.substring(0, colonIndetwitter));
      String versionDesc = version.substring(colonIndetwitter + 1);
      return Pair.of(versionNumber, versionDesc);
    } catch (Etwitterception e) {
      throw new SchemaValidationEtwitterception("Malformed version string: " + version, e);
    }
  }

  @Override
  public Map<String, FieldWeightDefault> getFieldWeightMap() {
    return fieldWeightMap;
  }

  /**
   * Build the feature maps so that we can use feature name to get the feature configuration.
   * @return: an immutable map keyed on fieldName.
   */
  private Pair<ImmutableMap<String, FeatureConfiguration>,
      ImmutableMap<Integer, FeatureConfiguration>> buildFeatureMaps(
      final Map<Integer, ThriftFieldConfiguration> csvViewFields)
      throws SchemaValidationEtwitterception {

    final ImmutableMap.Builder<String, FeatureConfiguration> featureConfigMapByNameBuilder =
        ImmutableMap.builder();
    final ImmutableMap.Builder<Integer, FeatureConfiguration> featureConfigMapByIdBuilder =
        ImmutableMap.builder();

    for (final Map.Entry<Integer, ThriftFieldConfiguration> entry : csvViewFields.entrySet()) {
      ThriftFieldSettings fieldSettings = entry.getValue().getSettings();
      FieldInfo fieldInfo = getFieldInfo(entry.getKey());
      FieldInfo baseFieldInfo =
          getFieldInfo(fieldSettings.getCsfViewSettings().getBaseFieldConfigId());
      if (baseFieldInfo == null) {
        throw new SchemaValidationEtwitterception("Base field (id="
            + fieldSettings.getCsfViewSettings().getBaseFieldConfigId() + ") not found.");
      }
      applyCsfViewFieldSettings(fieldInfo, baseFieldInfo, fieldSettings.getCsfViewSettings());

      FeatureConfiguration featureConfig = fieldInfo.getFieldType()
          .getCsfViewFeatureConfiguration();
      if (featureConfig != null) {
        featureConfigMapByNameBuilder.put(fieldInfo.getName(), featureConfig);
        featureConfigMapByIdBuilder.put(fieldInfo.getFieldId(), featureConfig);
      }
    }

    return Pair.of(featureConfigMapByNameBuilder.build(), featureConfigMapByIdBuilder.build());
  }

  @Override
  public FeatureConfiguration getFeatureConfigurationByName(String featureName) {
    return featureConfigMapByName.get(featureName);
  }

  @Override
  public FeatureConfiguration getFeatureConfigurationById(int featureFieldId) {
    return Preconditions.checkNotNull(featureConfigMapById.get(featureFieldId),
        "Field ID: " + featureFieldId);
  }

  @Override
  @Nullable
  public ThriftCSFType getCSFFieldType(String fieldName) {
    FieldInfo fieldInfo = getFieldInfo(fieldName);
    if (fieldInfo == null) {
      return null;
    }

    EarlybirdFieldType fieldType = fieldInfo.getFieldType();
    if (fieldType.docValuesType() != org.apache.lucene.indetwitter.DocValuesType.NUMERIC) {
      return null;
    }

    return fieldType.getCsfType();
  }

  @Override
  public ImmutableSchemaInterface getSchemaSnapshot() {
    return this;
  }

  private FieldInfo merge(int fieldConfigId,
                          FieldInfo fieldInfo,
                          ThriftFieldConfiguration overrideConfig)
      throws SchemaValidationEtwitterception {

    throw new UnsupportedOperationEtwitterception("Field override config not supported");
  }

  @Override
  public ThriftSearchFeatureSchema getSearchFeatureSchema() {
    return searchFeatureSchema;
  }

  @Override
  public ImmutableMap<Integer, FeatureConfiguration> getFeatureIdToFeatureConfig() {
    return featureConfigMapById;
  }

  @Override
  public ImmutableMap<String, FeatureConfiguration> getFeatureNameToFeatureConfig() {
    return featureConfigMapByName;
  }

  private ThriftSearchFeatureSchema createSearchResultFeatureSchema(
      String featureSchemaVersionPrefitwitter,
      Map<String, FieldInfo> allFieldSettings,
      Map<String, FeatureConfiguration> featureConfigurations) throws SchemaValidationEtwitterception {
    final ImmutableMap.Builder<Integer, ThriftSearchFeatureSchemaEntry> builder =
        new ImmutableMap.Builder<>();

    for (Map.Entry<String, FieldInfo> field : allFieldSettings.entrySet()) {
      FeatureConfiguration featureConfig = featureConfigurations.get(field.getKey());
      if (featureConfig == null) {
        // This is either a not csf related field or a csf field.
        continue;
      }

      // This is a csfView field.
      if (featureConfig.getOutputType() == null) {
        LOG.info("Skip unused fieldschemas: {} for search feature schema.", field.getKey());
        continue;
      }

      ThriftSearchFeatureType featureType = getResultFeatureType(featureConfig.getOutputType());
      if (featureType != null) {
        builder.put(
            field.getValue().getFieldId(),
            new ThriftSearchFeatureSchemaEntry(field.getKey(), featureType));
      } else {
        LOG.error("Invalid CSFType encountered for csf field: {}", field.getKey());
      }
    }
    Map<Integer, ThriftSearchFeatureSchemaEntry> indetwitterOnlySchemaEntries = builder.build();

    // Add earlybird derived features, they are defined in EtwitterternalTweetFeatures and used in the
    // scoring function. They are no different from those auto-generated indetwitter-based features
    // viewed from outside Earlybird.
    Map<Integer, ThriftSearchFeatureSchemaEntry> entriesWithEBFeatures =
        appendToFeatureSchema(
            indetwitterOnlySchemaEntries, EtwitterternalTweetFeature.EARLYBIRD_DERIVED_FEATURES);

    // Add other features needed for tweet ranking from EarlybirdRankingDerivedFeature.
    Map<Integer, ThriftSearchFeatureSchemaEntry> allSchemaEntries = appendToFeatureSchema(
        entriesWithEBFeatures, EtwitterternalTweetFeature.EARLYBIRD_RANKING_DERIVED_FEATURES);

    long schemaEntriesChecksum = getChecksum(allSchemaEntries);
    SearchLongGauge.etwitterport("feature_schema_checksum", new AtomicLong(schemaEntriesChecksum));

    String schemaVersion = String.format(
        "%s.%d.%d", featureSchemaVersionPrefitwitter, majorVersionNumber, minorVersionNumber);
    ThriftSearchFeatureSchemaSpecifier schemaSpecifier =
        new ThriftSearchFeatureSchemaSpecifier(schemaVersion, schemaEntriesChecksum);

    ThriftSearchFeatureSchema schema = new ThriftSearchFeatureSchema();
    schema.setSchemaSpecifier(schemaSpecifier);
    schema.setEntries(allSchemaEntries);

    return schema;
  }

  // Serializes schemaEntries to a byte array, and computes a CRC32 checksum of the array.
  // The serialization needs to be stable: if schemaEntries1.equals(schemaEntries2), we want
  // this method to produce the same checksum for schemaEntrie1 and schemaEntrie2, even if
  // the checksums are computed in different JVMs, etc.
  private static long getChecksum(Map<Integer, ThriftSearchFeatureSchemaEntry> schemaEntries)
      throws SchemaValidationEtwitterception {
    SortedMap<Integer, ThriftSearchFeatureSchemaEntry> sortedSchemaEntries =
        new TreeMap<Integer, ThriftSearchFeatureSchemaEntry>(schemaEntries);

    CRC32OutputStream crc32OutputStream = new CRC32OutputStream();
    ObjectOutputStream objectOutputStream = null;
    try {
      objectOutputStream = new ObjectOutputStream(crc32OutputStream);
      for (Integer fieldId : sortedSchemaEntries.keySet()) {
        objectOutputStream.writeObject(fieldId);
        ThriftSearchFeatureSchemaEntry schemaEntry = sortedSchemaEntries.get(fieldId);
        objectOutputStream.writeObject(schemaEntry.getFeatureName());
        objectOutputStream.writeObject(schemaEntry.getFeatureType());
      }
      objectOutputStream.flush();
      return crc32OutputStream.getValue();
    } catch (IOEtwitterception e) {
      throw new SchemaValidationEtwitterception("Could not serialize feature schema entries.", e);
    } finally {
      Preconditions.checkNotNull(objectOutputStream);
      try {
        objectOutputStream.close();
      } catch (IOEtwitterception e) {
        throw new SchemaValidationEtwitterception("Could not close ObjectOutputStream.", e);
      }
    }
  }

  /**
   * Get the search feature type based on the csf type.
   * @param csfType the column stride field type for the data
   * @return the corresponding search feature type
   */
  @VisibleForTesting
  public static ThriftSearchFeatureType getResultFeatureType(ThriftCSFType csfType) {
    switch (csfType) {
      case INT:
      case BYTE:
        return ThriftSearchFeatureType.INT32_VALUE;
      case BOOLEAN:
        return ThriftSearchFeatureType.BOOLEAN_VALUE;
      case FLOAT:
      case DOUBLE:
        return ThriftSearchFeatureType.DOUBLE_VALUE;
      case LONG:
        return ThriftSearchFeatureType.LONG_VALUE;
      default:
        return null;
    }
  }
}
