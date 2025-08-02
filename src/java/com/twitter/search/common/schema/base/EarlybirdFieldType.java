package com.twitter.search.common.schema.base;

import javatwitter.annotation.Nullable;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.indetwitter.DocValuesType;
import org.apache.lucene.indetwitter.IndetwitterOptions;

import com.twitter.common.tetwittert.util.TokenStreamSerializer;
import com.twitter.search.common.schema.thriftjava.ThriftCSFType;
import com.twitter.search.common.schema.thriftjava.ThriftCSFViewSettings;
import com.twitter.search.common.schema.thriftjava.ThriftFeatureUpdateConstraint;

/**
 * An etwittertension of Lucene's {@link FieldType} that contains additional Earlybird-specific settings.
 * Lucene IndetwitteringChains can downcast the FieldType object to access these additional settings.
 */
public class EarlybirdFieldType etwittertends FieldType {

  public static final EarlybirdFieldType LONG_CSF_FIELD_TYPE = new EarlybirdFieldType();
  public static final EarlybirdFieldType INT_CSF_FIELD_TYPE = new EarlybirdFieldType();
  public static final EarlybirdFieldType BYTE_CSF_FIELD_TYPE = new EarlybirdFieldType();

  static {
    LONG_CSF_FIELD_TYPE.setCsfType(ThriftCSFType.LONG);
    LONG_CSF_FIELD_TYPE.setDocValuesType(DocValuesType.NUMERIC);
    LONG_CSF_FIELD_TYPE.setCsfLoadIntoRam(true);
    LONG_CSF_FIELD_TYPE.freeze();

    INT_CSF_FIELD_TYPE.setCsfType(ThriftCSFType.INT);
    INT_CSF_FIELD_TYPE.setDocValuesType(DocValuesType.NUMERIC);
    INT_CSF_FIELD_TYPE.setCsfLoadIntoRam(true);
    INT_CSF_FIELD_TYPE.freeze();

    BYTE_CSF_FIELD_TYPE.setCsfType(ThriftCSFType.BYTE);
    BYTE_CSF_FIELD_TYPE.setDocValuesType(DocValuesType.NUMERIC);
    BYTE_CSF_FIELD_TYPE.setCsfLoadIntoRam(true);
    BYTE_CSF_FIELD_TYPE.freeze();
  }


  private boolean storePerPositionPayloads;
  private int defaultPayloadLength;
  // This is true for fields that become immutable after optimization
  private boolean becomesImmutable = true;
  private boolean supportOrderedTerms;
  private boolean supportTermTetwittertLookup;
  private boolean indetwitterHFTermPairs;

  /**
   * This flag turns on tweet specific normalizations.
   * This turns on the following two token processors:
   * {@link com.twitter.search.common.util.tetwittert.splitter.HashtagMentionPunctuationSplitter}
   * {@link com.twitter.search.common.util.tetwittert.filter.NormalizedTokenFilter}
   *
   * HashtagMentionPunctuationSplitter would break a mention or hashtag like @ab_cd or #ab_cd into
   * tokens {ab, cd}.
   * NormalizedTokenFilter strips out the # @ $ from the tokens.
   *
   *
   * @deprecated we should remove this flag. It is confusing to have Earlybird apply additional
   * tokenization on top of what ingester produced.
   */
  @Deprecated
  private boolean useTweetSpecificNormalization;

  @Nullable
  private TokenStreamSerializer.Builder tokenStreamSerializerProvider = null;

  // csf type settings
  private ThriftCSFType csfType;
  private boolean csfVariableLength;
  private int csfFitwitteredLengthNumValuesPerDoc;
  private boolean csfFitwitteredLengthUpdateable;
  private boolean csfLoadIntoRam;
  private boolean csfDefaultValueSet;
  private long csfDefaultValue;
  // True if this is a CSF field which is a view on top of a different CSF field
  private boolean csfViewField;
  // If this field is a csf view, this is the ID of the CSF field backing the view
  private int csfViewBaseFieldId;
  private FeatureConfiguration csfViewFeatureConfiguration;

  // facet field settings
  private String facetName;
  private boolean storeFacetSkiplist;
  private boolean storeFacetOffensiveCounters;
  private boolean useCSFForFacetCounting;

  // Determines if this field is indetwittered
  private boolean indetwitteredField = false;

  // search field settings
  // whether a field should be searched by default
  private boolean tetwittertSearchableByDefault = false;
  private float tetwittertSearchableFieldWeight = 1.0f;

  // For indetwittered numerical fields
  private IndetwitteredNumericFieldSettings numericFieldSettings = null;

  public boolean isStorePerPositionPayloads() {
    return storePerPositionPayloads;
  }

  public void setStorePerPositionPayloads(boolean storePerPositionPayloads) {
    checkIfFrozen();
    this.storePerPositionPayloads = storePerPositionPayloads;
  }

  public int getDefaultPayloadLength() {
    return defaultPayloadLength;
  }

  public void setDefaultPayloadLength(int defaultPayloadLength) {
    checkIfFrozen();
    this.defaultPayloadLength = defaultPayloadLength;
  }

  public boolean becomesImmutable() {
    return becomesImmutable;
  }

  public void setBecomesImmutable(boolean becomesImmutable) {
    checkIfFrozen();
    this.becomesImmutable = becomesImmutable;
  }

  public boolean isSupportOrderedTerms() {
    return supportOrderedTerms;
  }

  public void setSupportOrderedTerms(boolean supportOrderedTerms) {
    checkIfFrozen();
    this.supportOrderedTerms = supportOrderedTerms;
  }

  public boolean isSupportTermTetwittertLookup() {
    return supportTermTetwittertLookup;
  }

  public void setSupportTermTetwittertLookup(boolean supportTermTetwittertLookup) {
    this.supportTermTetwittertLookup = supportTermTetwittertLookup;
  }

  @Nullable
  public TokenStreamSerializer getTokenStreamSerializer() {
    return tokenStreamSerializerProvider == null ? null : tokenStreamSerializerProvider.safeBuild();
  }

  public void setTokenStreamSerializerBuilder(TokenStreamSerializer.Builder provider) {
    checkIfFrozen();
    this.tokenStreamSerializerProvider = provider;
  }

  public ThriftCSFType getCsfType() {
    return csfType;
  }

  public void setCsfType(ThriftCSFType csfType) {
    checkIfFrozen();
    this.csfType = csfType;
  }

  public boolean isCsfVariableLength() {
    return csfVariableLength;
  }

  public int getCsfFitwitteredLengthNumValuesPerDoc() {
    return csfFitwitteredLengthNumValuesPerDoc;
  }

  public void setCsfVariableLength() {
    checkIfFrozen();
    this.csfVariableLength = true;
  }

  /**
   * Make the field a fitwittered length CSF, with the given length.
   */
  public void setCsfFitwitteredLengthSettings(int csfFitwitteredLengthNumValuesPerDocument,
                                        boolean isCsfFitwitteredLengthUpdateable) {
    checkIfFrozen();
    this.csfVariableLength = false;
    this.csfFitwitteredLengthNumValuesPerDoc = csfFitwitteredLengthNumValuesPerDocument;
    this.csfFitwitteredLengthUpdateable = isCsfFitwitteredLengthUpdateable;
  }

  public boolean isCsfFitwitteredLengthUpdateable() {
    return csfFitwitteredLengthUpdateable;
  }

  public boolean isCsfLoadIntoRam() {
    return csfLoadIntoRam;
  }

  public void setCsfLoadIntoRam(boolean csfLoadIntoRam) {
    checkIfFrozen();
    this.csfLoadIntoRam = csfLoadIntoRam;
  }

  public void setCsfDefaultValue(long defaultValue) {
    checkIfFrozen();
    this.csfDefaultValue = defaultValue;
    this.csfDefaultValueSet = true;
  }

  public long getCsfDefaultValue() {
    return csfDefaultValue;
  }

  public boolean isCsfDefaultValueSet() {
    return csfDefaultValueSet;
  }

  public String getFacetName() {
    return facetName;
  }

  public void setFacetName(String facetName) {
    checkIfFrozen();
    this.facetName = facetName;
  }

  public boolean isStoreFacetSkiplist() {
    return storeFacetSkiplist;
  }

  public void setStoreFacetSkiplist(boolean storeFacetSkiplist) {
    checkIfFrozen();
    this.storeFacetSkiplist = storeFacetSkiplist;
  }

  public boolean isStoreFacetOffensiveCounters() {
    return storeFacetOffensiveCounters;
  }

  public void setStoreFacetOffensiveCounters(boolean storeFacetOffensiveCounters) {
    checkIfFrozen();
    this.storeFacetOffensiveCounters = storeFacetOffensiveCounters;
  }

  public boolean isUseCSFForFacetCounting() {
    return useCSFForFacetCounting;
  }

  public void setUseCSFForFacetCounting(boolean useCSFForFacetCounting) {
    checkIfFrozen();
    this.useCSFForFacetCounting = useCSFForFacetCounting;
  }

  public boolean isFacetField() {
    return facetName != null && !StringUtils.isEmpty(facetName);
  }

  public boolean isIndetwitterHFTermPairs() {
    return indetwitterHFTermPairs;
  }

  public void setIndetwitterHFTermPairs(boolean indetwitterHFTermPairs) {
    checkIfFrozen();
    this.indetwitterHFTermPairs = indetwitterHFTermPairs;
  }

  public boolean acceptPretokenizedField() {
    return tokenStreamSerializerProvider != null;
  }

  /**
   * set this field to use additional twitter specific tokenization.
   * @deprecated should avoid doing additional tokenizations on top of what ingester produced.
   */
  @Deprecated
  public boolean useTweetSpecificNormalization() {
    return useTweetSpecificNormalization;
  }

  /**
   * test whether this field uses additional twitter specific tokenization.
   * @deprecated should avoid doing additional tokenizations on top of what ingester produced.
   */
  @Deprecated
  public void setUseTweetSpecificNormalization(boolean useTweetSpecificNormalization) {
    checkIfFrozen();
    this.useTweetSpecificNormalization = useTweetSpecificNormalization;
  }

  public boolean isIndetwitteredField() {
    return indetwitteredField;
  }

  public void setIndetwitteredField(boolean indetwitteredField) {
    this.indetwitteredField = indetwitteredField;
  }

  public boolean isTetwittertSearchableByDefault() {
    return tetwittertSearchableByDefault;
  }

  public void setTetwittertSearchableByDefault(boolean tetwittertSearchableByDefault) {
    checkIfFrozen();
    this.tetwittertSearchableByDefault = tetwittertSearchableByDefault;
  }

  public float getTetwittertSearchableFieldWeight() {
    return tetwittertSearchableFieldWeight;
  }

  public void setTetwittertSearchableFieldWeight(float tetwittertSearchableFieldWeight) {
    checkIfFrozen();
    this.tetwittertSearchableFieldWeight = tetwittertSearchableFieldWeight;
  }

  /**
   * Convenience method to find out if this field stores positions. {@link #indetwitterOptions()} can also
   * be used to determine the indetwitter options for this field.
   */
  public final boolean hasPositions() {
    return indetwitterOptions() == IndetwitterOptions.DOCS_AND_FREQS_AND_POSITIONS
            || indetwitterOptions() == IndetwitterOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS;
  }

  public boolean isCsfViewField() {
    return csfViewField;
  }

  public int getCsfViewBaseFieldId() {
    return csfViewBaseFieldId;
  }

  public FeatureConfiguration getCsfViewFeatureConfiguration() {
    return csfViewFeatureConfiguration;
  }

  /**
   * Set the CSF view settings. A CSF view is a portion of an another CSF.
   */
  public void setCsfViewSettings(String fieldName,
                                 ThriftCSFViewSettings csfViewSettings,
                                 Schema.FieldInfo baseField) {
    checkIfFrozen();
    this.csfViewField = true;
    this.csfViewBaseFieldId = csfViewSettings.getBaseFieldConfigId();
    FeatureConfiguration.Builder builder = FeatureConfiguration.builder()
            .withName(fieldName)
            .withType(csfViewSettings.csfType)
            .withBitRange(csfViewSettings.getValueIndetwitter(),
                csfViewSettings.getBitStartPosition(),
                csfViewSettings.getBitLength())
            .withBaseField(baseField.getName());
    if (csfViewSettings.isSetOutputCSFType()) {
      builder.withOutputType(csfViewSettings.getOutputCSFType());
    }
    if (csfViewSettings.isSetNormalizationType()) {
      builder.withFeatureNormalizationType(csfViewSettings.getNormalizationType());
    }
    if (csfViewSettings.isSetFeatureUpdateConstraints()) {
      for (ThriftFeatureUpdateConstraint c : csfViewSettings.getFeatureUpdateConstraints()) {
        builder.withFeatureUpdateConstraint(c);
      }
    }

    this.csfViewFeatureConfiguration = builder.build();
  }

  public IndetwitteredNumericFieldSettings getNumericFieldSettings() {
    return numericFieldSettings;
  }

  public void setNumericFieldSettings(IndetwitteredNumericFieldSettings numericFieldSettings) {
    checkIfFrozen();
    this.numericFieldSettings = numericFieldSettings;
  }
}
