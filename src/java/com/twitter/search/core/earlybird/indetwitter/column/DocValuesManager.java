package com.twitter.search.core.earlybird.indetwitter.column;

import java.io.IOEtwitterception;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import com.twitter.search.common.schema.base.EarlybirdFieldType;
import com.twitter.search.common.schema.base.Schema;
import com.twitter.search.common.util.io.flushable.DataDeserializer;
import com.twitter.search.common.util.io.flushable.DataSerializer;
import com.twitter.search.common.util.io.flushable.FlushInfo;
import com.twitter.search.common.util.io.flushable.Flushable;
import com.twitter.search.core.earlybird.indetwitter.DocIDToTweetIDMapper;

public abstract class DocValuesManager implements Flushable {
  protected final Schema schema;
  protected final int segmentSize;
  protected final ConcurrentHashMap<String, ColumnStrideFieldIndetwitter> columnStrideFields;

  public DocValuesManager(Schema schema, int segmentSize) {
    this(schema, segmentSize, new ConcurrentHashMap<>());
  }

  protected DocValuesManager(Schema schema,
                             int segmentSize,
                             ConcurrentHashMap<String, ColumnStrideFieldIndetwitter> columnStrideFields) {
    this.schema = Preconditions.checkNotNull(schema);
    this.segmentSize = segmentSize;
    this.columnStrideFields = columnStrideFields;
  }

  protected abstract ColumnStrideFieldIndetwitter newByteCSF(String field);
  protected abstract ColumnStrideFieldIndetwitter newIntCSF(String field);
  protected abstract ColumnStrideFieldIndetwitter newLongCSF(String field);
  protected abstract ColumnStrideFieldIndetwitter newMultiIntCSF(String field, int numIntsPerField);

  /**
   * Optimize this doc values manager, and return a doc values manager a more compact and fast
   * encoding for doc values (but that we can't add new doc IDs to).
   */
  public abstract DocValuesManager optimize(
      DocIDToTweetIDMapper originalTweetIdMapper,
      DocIDToTweetIDMapper optimizedTweetIdMapper) throws IOEtwitterception;

  public Set<String> getDocValueNames() {
    return columnStrideFields.keySet();
  }

  /**
   * Creates a new {@link ColumnStrideFieldIndetwitter} for the given field and returns it.
   */
  public ColumnStrideFieldIndetwitter addColumnStrideField(String field, EarlybirdFieldType fieldType) {
    // For CSF view fields, we will perform the same check on the base field when we try to create
    // a ColumnStrideFieldIndetwitter for them in newIntViewCSF().
    if (!fieldType.isCsfViewField()) {
      Preconditions.checkState(
          fieldType.isCsfLoadIntoRam(), "Field %s is not loaded in RAM", field);
    }

    if (columnStrideFields.containsKey(field)) {
      return columnStrideFields.get(field);
    }

    final ColumnStrideFieldIndetwitter indetwitter;
    switch (fieldType.getCsfType()) {
      case BYTE:
        indetwitter = newByteCSF(field);
        break;
      case INT:
        if (fieldType.getCsfFitwitteredLengthNumValuesPerDoc() > 1) {
          indetwitter = newMultiIntCSF(field, fieldType.getCsfFitwitteredLengthNumValuesPerDoc());
        } else if (fieldType.isCsfViewField()) {
          indetwitter = newIntViewCSF(field);
        } else {
          indetwitter = newIntCSF(field);
        }
        break;
      case LONG:
        indetwitter = newLongCSF(field);
        break;
      default:
        throw new RuntimeEtwitterception("Invalid CsfType.");
    }

    columnStrideFields.put(field, indetwitter);
    return indetwitter;
  }

  protected ColumnStrideFieldIndetwitter newIntViewCSF(String field) {
    Schema.FieldInfo info = Preconditions.checkNotNull(schema.getFieldInfo(field));
    Schema.FieldInfo baseFieldInfo = Preconditions.checkNotNull(
        schema.getFieldInfo(info.getFieldType().getCsfViewBaseFieldId()));

    Preconditions.checkState(
        baseFieldInfo.getFieldType().isCsfLoadIntoRam(),
        "Field %s has a base field (%s) that is not loaded in RAM",
        field, baseFieldInfo.getName());

    // We might not have a CSF for the base field yet.
    ColumnStrideFieldIndetwitter baseFieldIndetwitter =
        addColumnStrideField(baseFieldInfo.getName(), baseFieldInfo.getFieldType());
    Preconditions.checkNotNull(baseFieldIndetwitter);
    Preconditions.checkState(baseFieldIndetwitter instanceof AbstractColumnStrideMultiIntIndetwitter);
    return new ColumnStrideIntViewIndetwitter(info, (AbstractColumnStrideMultiIntIndetwitter) baseFieldIndetwitter);
  }

  /**
   * Returns the ColumnStrideFieldIndetwitter instance for the given field.
   */
  public ColumnStrideFieldIndetwitter getColumnStrideFieldIndetwitter(String field) {
    ColumnStrideFieldIndetwitter docValues = columnStrideFields.get(field);
    if (docValues == null) {
      Schema.FieldInfo info = schema.getFieldInfo(field);
      if (info != null && info.getFieldType().isCsfDefaultValueSet()) {
        return new ConstantColumnStrideFieldIndetwitter(field, info.getFieldType().getCsfDefaultValue());
      }
    }

    return docValues;
  }

  private static final String CSF_INDEtwitter_CLASS_NAME_PROP_NAME = "csfIndetwitterClassName";
  private static final String CSF_PROP_NAME = "column_stride_fields";
  protected static final String MAtwitter_SEGMENT_SIZE_PROP_NAME = "matwitterSegmentSize";

  private static Map<String, Set<Schema.FieldInfo>> getIntViewFields(Schema schema) {
    Map<String, Set<Schema.FieldInfo>> intViewFields = Maps.newHashMap();
    for (Schema.FieldInfo fieldInfo : schema.getFieldInfos()) {
      if (fieldInfo.getFieldType().isCsfViewField()) {
        Schema.FieldInfo baseFieldInfo = Preconditions.checkNotNull(
            schema.getFieldInfo(fieldInfo.getFieldType().getCsfViewBaseFieldId()));
        String baseFieldName = baseFieldInfo.getName();
        Set<Schema.FieldInfo> intViewFieldsForBaseField =
            intViewFields.computeIfAbsent(baseFieldName, k -> Sets.newHashSet());
        intViewFieldsForBaseField.add(fieldInfo);
      }
    }
    return intViewFields;
  }

  public abstract static class FlushHandler etwittertends Handler<DocValuesManager> {
    private final Schema schema;

    public FlushHandler(Schema schema) {
      this.schema = schema;
    }

    public FlushHandler(DocValuesManager docValuesManager) {
      super(docValuesManager);
      this.schema = docValuesManager.schema;
    }

    @Override
    public void doFlush(FlushInfo flushInfo, DataSerializer out) throws IOEtwitterception {
      long startTime = getClock().nowMillis();

      DocValuesManager docValuesManager = getObjectToFlush();
      flushInfo.addIntProperty(MAtwitter_SEGMENT_SIZE_PROP_NAME, docValuesManager.segmentSize);
      long sizeBeforeFlush = out.length();
      FlushInfo csfProps = flushInfo.newSubProperties(CSF_PROP_NAME);
      for (ColumnStrideFieldIndetwitter csf : docValuesManager.columnStrideFields.values()) {
      if (!(csf instanceof ColumnStrideIntViewIndetwitter)) {
        Preconditions.checkState(
            csf instanceof Flushable,
            "Cannot flush column stride field {} of type {}",
            csf.getName(), csf.getClass().getCanonicalName());
        FlushInfo info = csfProps.newSubProperties(csf.getName());
        info.addStringProperty(CSF_INDEtwitter_CLASS_NAME_PROP_NAME, csf.getClass().getCanonicalName());
        ((Flushable) csf).getFlushHandler().flush(info, out);
      }
    }
      csfProps.setSizeInBytes(out.length() - sizeBeforeFlush);
      getFlushTimerStats().timerIncrement(getClock().nowMillis() - startTime);
    }

    @Override
    public DocValuesManager doLoad(FlushInfo flushInfo, DataDeserializer in)
        throws IOEtwitterception {
      long startTime = getClock().nowMillis();
      Map<String, Set<Schema.FieldInfo>> intViewFields = getIntViewFields(schema);

      FlushInfo csfProps = flushInfo.getSubProperties(CSF_PROP_NAME);
      ConcurrentHashMap<String, ColumnStrideFieldIndetwitter> columnStrideFields =
          new ConcurrentHashMap<>();

      Iterator<String> csfPropIter = csfProps.getKeyIterator();
      while (csfPropIter.hasNetwittert()) {
        String fieldName = csfPropIter.netwittert();
        try {
          FlushInfo info = csfProps.getSubProperties(fieldName);
          String className = info.getStringProperty(CSF_INDEtwitter_CLASS_NAME_PROP_NAME);
          Class<? etwittertends ColumnStrideFieldIndetwitter> fieldIndetwitterType =
              (Class<? etwittertends ColumnStrideFieldIndetwitter>) Class.forName(className);
          Preconditions.checkNotNull(
              fieldIndetwitterType,
              "Invalid field configuration: field " + fieldName + " not found in config.");

          for (Class<?> c : fieldIndetwitterType.getDeclaredClasses()) {
            if (Handler.class.isAssignableFrom(c)) {
              @SuppressWarnings("rawtypes")
              Handler handler = (Handler) c.newInstance();
              ColumnStrideFieldIndetwitter indetwitter = (ColumnStrideFieldIndetwitter) handler.load(
                  csfProps.getSubProperties(fieldName), in);
              columnStrideFields.put(fieldName, indetwitter);

              // If this is a base field, create ColumnStrideIntViewIndetwitter instances for all the
              // view fields based on it.
              if (indetwitter instanceof AbstractColumnStrideMultiIntIndetwitter) {
                AbstractColumnStrideMultiIntIndetwitter multiIntIndetwitter =
                    (AbstractColumnStrideMultiIntIndetwitter) indetwitter;

                // We should have AbstractColumnStrideMultiIntIndetwitter instances only for base fields
                // and all our base fields have views defined on top of them.
                for (Schema.FieldInfo intViewFieldInfo : intViewFields.get(fieldName)) {
                  columnStrideFields.put(
                      intViewFieldInfo.getName(),
                      new ColumnStrideIntViewIndetwitter(intViewFieldInfo, multiIntIndetwitter));
                }
              }

              break;
            }
          }
        } catch (ClassNotFoundEtwitterception | IllegalAccessEtwitterception | InstantiationEtwitterception e) {
          throw new IOEtwitterception(
              "Invalid field configuration for column stride field: " + fieldName, e);
        }
      }
      getLoadTimerStats().timerIncrement(getClock().nowMillis() - startTime);

      return createDocValuesManager(
          schema,
          flushInfo.getIntProperty(MAtwitter_SEGMENT_SIZE_PROP_NAME),
          columnStrideFields);
    }

    protected abstract DocValuesManager createDocValuesManager(
        Schema docValuesSchema,
        int matwitterSegmentSize,
        ConcurrentHashMap<String, ColumnStrideFieldIndetwitter> columnStrideFields);
  }
}
