# nclude " nternal/thr ft.h"
# nclude " nternal/error.h"
# nclude <str ng>

# nclude <twml/TensorRecordReader.h>
# nclude <twml/RawTensor.h>

na space twml {

template<typena  T> struct TensorTra s;

#def ne  NSTANT ATE(TYPE, THR FT_TYPE, TWML_TYPE)   \
  template<> struct TensorTra s<TYPE> {            \
    stat c const TTYPES Thr ftType = THR FT_TYPE;   \
    stat c const twml_type TwmlType = TWML_TYPE;    \
  };                                                \

 NSTANT ATE( nt64_t, TTYPE_ 64, TWML_TYPE_ NT64)
 NSTANT ATE( nt32_t, TTYPE_ 32, TWML_TYPE_ NT32)
 NSTANT ATE(double, TTYPE_DOUBLE, TWML_TYPE_DOUBLE)
 NSTANT ATE(bool, TTYPE_BOOL, TWML_TYPE_BOOL)

stat c
std::vector<u nt64_t> calcStr des(const std::vector<u nt64_t> &shape) {
   nt nd ms = stat c_cast< nt>(shape.s ze());
  std::vector<u nt64_t> str des(nd ms);
  u nt64_t str de = 1;
  for ( nt   = nd ms-1;   >= 0;  --) {
    str des[ ] = str de;
    str de *= shape[ ];
  }
  return str des;
}

stat c twml_type getTwmlType( nt dtype) {
  // Convert tensor.thr ft enum to twml enum
  sw ch (dtype) {
    case DATA_TYPE_FLOAT:
      return TWML_TYPE_FLOAT;
    case DATA_TYPE_DOUBLE:
      return TWML_TYPE_DOUBLE;
    case DATA_TYPE_ NT64:
      return TWML_TYPE_ NT64;
    case DATA_TYPE_ NT32:
      return TWML_TYPE_ NT32;
    case DATA_TYPE_U NT8:
      return TWML_TYPE_U NT8;
    case DATA_TYPE_STR NG:
      return TWML_TYPE_STR NG;
    case DATA_TYPE_BOOL:
      return TWML_TYPE_BOOL;
  }
  return TWML_TYPE_UNKNOWN;
}

std::vector<u nt64_t> TensorRecordReader::readShape() {
   nt32_t length = read nt32();

  std::vector<u nt64_t> shape;
  shape.reserve(length);
  for ( nt32_t   = 0;   < length;  ++) {
    shape.push_back(stat c_cast<u nt64_t>(read nt64()));
  }

  return shape;
}

template<typena  T>
RawTensor TensorRecordReader::readTypedTensor() {
  std::vector<u nt64_t> shape;
   nt32_t length = 0;
  const u nt8_t *data = nullptr;
  u nt64_t raw_length = 0;
  u nt8_t f eld_type = TTYPE_STOP;

  wh le ((f eld_type = readByte()) != TTYPE_STOP) {
     nt16_t f eld_ d = read nt16();
    sw ch (f eld_ d) {
      case 1:
        CHECK_THR FT_TYPE(f eld_type, TTYPE_L ST, "data");
        CHECK_THR FT_TYPE(readByte(), TensorTra s<T>::Thr ftType, "data_type");
        length = getRawBuffer<T>(&data);
        raw_length = length * s zeof(T);
        break;
      case 2:
        CHECK_THR FT_TYPE(f eld_type, TTYPE_L ST, "shape");
        CHECK_THR FT_TYPE(readByte(), TTYPE_ 64, "shape_type");
        shape = readShape();
        break;
      default:
        throw Thr ft nval dF eld(f eld_ d, "TensorRecordReader::readTypedTensor");
    }
  }

  // data  s requ red
   f (data == nullptr) {
    throw twml::Error(TWML_ERR_THR FT, "data f eld not found for TypedTensor");
  }

  // shape  s opt onal
   f (shape.s ze() == 0) {
    shape.push_back((u nt64_t)length);
  }

  // TODO: Try avo d ng str de calculat on
  std::vector<u nt64_t> str des = calcStr des(shape);
  // F XME: Try to use const vo d *  n Tensors.
  return RawTensor(const_cast<vo d *>(stat c_cast<const vo d *>(data)),
                   shape, str des, (twml_type)TensorTra s<T>::TwmlType, true, raw_length);
}

RawTensor TensorRecordReader::readRawTypedTensor() {
  std::vector<u nt64_t> shape;
  const u nt8_t *data = nullptr;
  twml_type type = TWML_TYPE_UNKNOWN;
  u nt64_t raw_length = 0;
  u nt8_t f eld_type = TTYPE_STOP;

  wh le ((f eld_type = readByte()) != TTYPE_STOP) {
     nt16_t f eld_ d = read nt16();
    sw ch (f eld_ d) {
      case 1:
        CHECK_THR FT_TYPE(f eld_type, TTYPE_ 32, "DataType");
        type = getTwmlType(read nt32());
        break;
      case 2:
        CHECK_THR FT_TYPE(f eld_type, TTYPE_STR NG, "content");
        raw_length = getRawBuffer<u nt8_t>(&data);
        break;
      case 3:
        CHECK_THR FT_TYPE(f eld_type, TTYPE_L ST, "shape");
        CHECK_THR FT_TYPE(readByte(), TTYPE_ 64, "shape_type");
        shape = readShape();
        break;
      default:
        throw Thr ft nval dF eld(f eld_ d, "TensorRecordReader::readRawTypedTensor");
    }
  }

  // data type  s requ red
   f (type == TWML_TYPE_UNKNOWN) {
    throw twml::Error(TWML_ERR_THR FT, "DataType  s a requ red f eld for RawTypedTensor");
  }

  // data  s requ red
   f (data == nullptr) {
    throw twml::Error(TWML_ERR_THR FT, "content  s a requ red f eld for RawTypedTensor");
  }

  // shape  s opt onal  n t  thr ft f le, but    s really requ red for str ng types.
   f (shape.s ze() == 0) {
     f (type == TWML_TYPE_STR NG) {
      throw twml::Error(TWML_ERR_THR FT, "shape requ red for str ng types  n RawTypedTensor");
    }
    shape.push_back((u nt64_t)(raw_length / getS zeOf(type)));
  }

  // TODO: Try avo d ng str de calculat on
  std::vector<u nt64_t> str des = calcStr des(shape);
  // F XME: Try to use const vo d * data  ns de Tensors.
  return RawTensor(const_cast<vo d *>(stat c_cast<const vo d *>(data)),
                   shape, str des, type, false, raw_length);
}

RawTensor TensorRecordReader::readStr ngTensor() {
  std::vector<u nt64_t> shape;
   nt32_t length = 0;
  const u nt8_t *data = nullptr;
  u nt64_t raw_length = 0;
  u nt8_t f eld_type = TTYPE_STOP;
  const u nt8_t *dum  = nullptr;

  wh le ((f eld_type = readByte()) != TTYPE_STOP) {
     nt16_t f eld_ d = read nt16();
    sw ch (f eld_ d) {
      case 1:
        CHECK_THR FT_TYPE(f eld_type, TTYPE_L ST, "data");
        CHECK_THR FT_TYPE(readByte(), TTYPE_STR NG, "data_type");
        length = read nt32();
        // Store t  current locat on of t  byte stream.
        // Use t  at to "deocde str ngs" at a later po nt.
        data = getBuffer();
        for ( nt32_t   = 0;   < length;  ++) {
          // Sk p read ng t  str ngs
          getRawBuffer<u nt8_t>(&dum );
        }
        raw_length = length;
        break;
      case 2:
        CHECK_THR FT_TYPE(f eld_type, TTYPE_L ST, "shape");
        CHECK_THR FT_TYPE(readByte(), TTYPE_ 64, "shape_type");
        shape = readShape();
        break;
      default:
        throw Thr ft nval dF eld(f eld_ d, "TensorRecordReader::readTypedTensor");
    }
  }

  // data  s requ red
   f (data == nullptr) {
    throw twml::Error(TWML_ERR_THR FT, "data f eld not found for TypedTensor");
  }

  // shape  s opt onal
   f (shape.s ze() == 0) {
    shape.push_back((u nt64_t)length);
  }

  // TODO: Try avo d ng str de calculat on
  std::vector<u nt64_t> str des = calcStr des(shape);
  // F XME: Try to use const vo d *  n Tensors.
  return RawTensor(const_cast<vo d *>(stat c_cast<const vo d *>(data)),
                   shape, str des, TWML_TYPE_U NT8, false, raw_length);
}

RawTensor TensorRecordReader::readGeneralTensor() {
  // No loop  s requ red because GeneralTensor  s un on.    s go ng to conta n one f eld only.
  // All t  f elds are structs
  CHECK_THR FT_TYPE(readByte(), TTYPE_STRUCT, "type");
   nt16_t f eld_ d = read nt16();
  RawTensor output;

  sw ch (f eld_ d) {
    case GT_RAW:
      output = readRawTypedTensor();
      break;
    case GT_STR NG:
      output = readStr ngTensor();
      break;
    case GT_ NT32:
      output = readTypedTensor< nt32_t>();
      break;
    case GT_ NT64:
      output = readTypedTensor< nt64_t>();
      break;
    case GT_FLOAT:
    case GT_DOUBLE:
      // Store both FloatTensor and DoubleTensor as double tensor as both are l st of doubles.
      output = readTypedTensor<double>();
      break;
    case GT_BOOL:
      output = readTypedTensor<bool>();
      break;
    default:
      throw Thr ft nval dF eld(f eld_ d, "TensorRecordReader::readGeneralTensor()");
  }

  CHECK_THR FT_TYPE(readByte(), TTYPE_STOP, "stop");
  return output;
}

RawSparseTensor TensorRecordReader::readCOOSparseTensor() {
  std::vector<u nt64_t> shape;
  u nt8_t f eld_type = TTYPE_STOP;
  RawTensor  nd ces, values;

  wh le ((f eld_type = readByte()) != TTYPE_STOP) {
     nt16_t f eld_ d = read nt16();
    sw ch (f eld_ d) {
      case 1:
        CHECK_THR FT_TYPE(f eld_type, TTYPE_L ST, "shape");
        CHECK_THR FT_TYPE(readByte(), TTYPE_ 64, "shape_type");
        shape = readShape();
        break;
      case 2:
         nd ces = readTypedTensor< nt64_t>();
        break;
      case 3:
        values = readGeneralTensor();
        break;
      default:
        throw twml::Error(TWML_ERR_THR FT, " nval d f eld w n deoc dng COOSparseTensor");
    }
  }

  return RawSparseTensor( nd ces, values, shape);
}

vo d TensorRecordReader::readTensor(const  nt feature_type, TensorRecord *record) {
  CHECK_THR FT_TYPE(feature_type, TTYPE_MAP, "type");
  CHECK_THR FT_TYPE(readByte(), TTYPE_ 64, "key_type");
  CHECK_THR FT_TYPE(readByte(), TTYPE_STRUCT, "value_type");

   nt32_t length = read nt32();
  for ( nt32_t   = 0;   < length;  ++) {
     nt64_t  d = read nt64();
    record->m_tensors.emplace( d, readGeneralTensor());
  }
}

vo d TensorRecordReader::readSparseTensor(const  nt feature_type, TensorRecord *record) {
  CHECK_THR FT_TYPE(feature_type, TTYPE_MAP, "type");
  CHECK_THR FT_TYPE(readByte(), TTYPE_ 64, "key_type");
  CHECK_THR FT_TYPE(readByte(), TTYPE_STRUCT, "value_type");

   nt32_t length = read nt32();
  for ( nt32_t   = 0;   < length;  ++) {
     nt64_t  d = read nt64();

    // No loop  s requ red because SparseTensor  s un on.    s go ng to conta n one f eld only.
    // All t  f elds are structs
    CHECK_THR FT_TYPE(readByte(), TTYPE_STRUCT, "f eld");
     nt16_t f eld_ d = read nt16();
    RawSparseTensor output;

    // Only COOSparsetensor  s supported.
    sw ch (f eld_ d) {
      case SP_COO:
        output = readCOOSparseTensor();
        break;
      default:
        throw Thr ft nval dF eld(f eld_ d, "TensorRecordReader::readSparseTensor()");
    }

    // Read t  last byte of t  struct.
    CHECK_THR FT_TYPE(readByte(), TTYPE_STOP, "stop");

    // Add to t  map.
    record->m_sparse_tensors.emplace( d, output);
  }
}

}  // na space twml
