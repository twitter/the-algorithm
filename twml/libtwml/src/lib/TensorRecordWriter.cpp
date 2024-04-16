# nclude " nternal/error.h"
# nclude " nternal/thr ft.h"

# nclude <map>
# nclude <twml/Thr ftWr er.h>
# nclude <twml/TensorRecordWr er.h>
# nclude <twml/ o/ OError.h>

us ng na space twml:: o;

na space twml {

stat c  nt32_t getRawThr ftType(twml_type dtype) {
  // convert twml enum to tensor.thr ft enum
  sw ch (dtype) {
    case TWML_TYPE_FLOAT:
      return DATA_TYPE_FLOAT;
    case TWML_TYPE_DOUBLE:
      return DATA_TYPE_DOUBLE;
    case TWML_TYPE_ NT64:
      return DATA_TYPE_ NT64;
    case TWML_TYPE_ NT32:
      return DATA_TYPE_ NT32;
    case TWML_TYPE_U NT8:
      return DATA_TYPE_U NT8;
    case TWML_TYPE_STR NG:
      return DATA_TYPE_STR NG;
    case TWML_TYPE_BOOL:
      return DATA_TYPE_BOOL;
    default:
      throw  OError( OError::UNSUPPORTED_OUTPUT_TYPE);
  }
}

vo d TensorRecordWr er::wr eTensor(const RawTensor &tensor) {
   f (tensor.getType() == TWML_TYPE_ NT32) {
    m_thr ft_wr er.wr eStructF eld ader(TTYPE_STRUCT, GT_ NT32);
    m_thr ft_wr er.wr eStructF eld ader(TTYPE_L ST, 1);
    m_thr ft_wr er.wr eL st ader(TTYPE_ 32, tensor.getNumEle nts());

    const  nt32_t *data = tensor.getData< nt32_t>();

    for (u nt64_t   = 0;   < tensor.getNumEle nts();  ++)
      m_thr ft_wr er.wr e nt32(data[ ]);

  } else  f (tensor.getType() == TWML_TYPE_ NT64) {
    m_thr ft_wr er.wr eStructF eld ader(TTYPE_STRUCT, GT_ NT64);
    m_thr ft_wr er.wr eStructF eld ader(TTYPE_L ST, 1);
    m_thr ft_wr er.wr eL st ader(TTYPE_ 64, tensor.getNumEle nts());

    const  nt64_t *data = tensor.getData< nt64_t>();

    for (u nt64_t   = 0;   < tensor.getNumEle nts();  ++)
      m_thr ft_wr er.wr e nt64(data[ ]);

  } else  f (tensor.getType() == TWML_TYPE_FLOAT) {
    m_thr ft_wr er.wr eStructF eld ader(TTYPE_STRUCT, GT_FLOAT);
    m_thr ft_wr er.wr eStructF eld ader(TTYPE_L ST, 1);
    m_thr ft_wr er.wr eL st ader(TTYPE_DOUBLE, tensor.getNumEle nts());

    const float *data = tensor.getData<float>();

    for (u nt64_t   = 0;   < tensor.getNumEle nts();  ++)
      m_thr ft_wr er.wr eDouble(stat c_cast<double>(data[ ]));

  } else  f (tensor.getType() == TWML_TYPE_DOUBLE) {
    m_thr ft_wr er.wr eStructF eld ader(TTYPE_STRUCT, GT_DOUBLE);
    m_thr ft_wr er.wr eStructF eld ader(TTYPE_L ST, 1);
    m_thr ft_wr er.wr eL st ader(TTYPE_DOUBLE, tensor.getNumEle nts());

    const double *data = tensor.getData<double>();

    for (u nt64_t   = 0;   < tensor.getNumEle nts();  ++)
      m_thr ft_wr er.wr eDouble(data[ ]);

  } else  f (tensor.getType() == TWML_TYPE_STR NG) {
    m_thr ft_wr er.wr eStructF eld ader(TTYPE_STRUCT, GT_STR NG);
    m_thr ft_wr er.wr eStructF eld ader(TTYPE_L ST, 1);
    m_thr ft_wr er.wr eL st ader(TTYPE_STR NG, tensor.getNumEle nts());

    const std::str ng *data = tensor.getData<std::str ng>();

    for (u nt64_t   = 0;   < tensor.getNumEle nts();  ++)
      m_thr ft_wr er.wr eStr ng(data[ ]);

  } else  f (tensor.getType() == TWML_TYPE_BOOL) {
    m_thr ft_wr er.wr eStructF eld ader(TTYPE_STRUCT, GT_BOOL);
    m_thr ft_wr er.wr eStructF eld ader(TTYPE_L ST, 1);
    m_thr ft_wr er.wr eL st ader(TTYPE_BOOL, tensor.getNumEle nts());

    const bool *data = tensor.getData<bool>();

    for (u nt64_t   = 0;   < tensor.getNumEle nts();  ++)
      m_thr ft_wr er.wr eBool(data[ ]);

  } else {
    throw  OError( OError::UNSUPPORTED_OUTPUT_TYPE);
  }

  // wr e tensor shape f eld
  m_thr ft_wr er.wr eStructF eld ader(TTYPE_L ST, 2);
  m_thr ft_wr er.wr eL st ader(TTYPE_ 64, tensor.getNumD ms());

  for (u nt64_t   = 0;   < tensor.getNumD ms();  ++)
    m_thr ft_wr er.wr e nt64(tensor.getD m( ));

  m_thr ft_wr er.wr eStructStop();
  m_thr ft_wr er.wr eStructStop();
}

vo d TensorRecordWr er::wr eRawTensor(const RawTensor &tensor) {
  m_thr ft_wr er.wr eStructF eld ader(TTYPE_STRUCT, GT_RAW);

  // dataType f eld
  m_thr ft_wr er.wr eStructF eld ader(TTYPE_ 32, 1);
  m_thr ft_wr er.wr e nt32(getRawThr ftType(tensor.getType()));

  // content f eld
  u nt64_t type_s ze = getS zeOf(tensor.getType());
  m_thr ft_wr er.wr eStructF eld ader(TTYPE_STR NG, 2);
  const u nt8_t *data = re nterpret_cast<const u nt8_t *>(tensor.getData<vo d>());
  m_thr ft_wr er.wr eB nary(data, tensor.getNumEle nts() * type_s ze);

  // shape f eld
  m_thr ft_wr er.wr eStructF eld ader(TTYPE_L ST, 3);
  m_thr ft_wr er.wr eL st ader(TTYPE_ 64, tensor.getNumD ms());

  for (u nt64_t   = 0;   < tensor.getNumD ms();  ++)
    m_thr ft_wr er.wr e nt64(tensor.getD m( ));

  m_thr ft_wr er.wr eStructStop();
  m_thr ft_wr er.wr eStructStop();
}

TWMLAP  u nt32_t TensorRecordWr er::getRecordsWr ten() {
  return m_records_wr ten;
}

// Caller (usually DataRecordWr er) must precede w h struct  ader f eld
// l ke thr ft_wr er.wr eStructF eld ader(TTYPE_MAP, DR_GENERAL_TENSOR)
TWMLAP  u nt64_t TensorRecordWr er::wr e(twml::TensorRecord &record) {
  u nt64_t bytes_wr ten_before = m_thr ft_wr er.getBytesWr ten();

  m_thr ft_wr er.wr eMap ader(TTYPE_ 64, TTYPE_STRUCT, record.getRawTensors().s ze());

  for (auto  d_tensor_pa rs : record.getRawTensors()) {
    m_thr ft_wr er.wr e nt64( d_tensor_pa rs.f rst);

    // all tensors wr ten as RawTensor Thr ft except for Str ngTensors
    // t  avo ds t  over ad of convert ng l tle end an to b g end an
     f ( d_tensor_pa rs.second.getType() == TWML_TYPE_STR NG)
      wr eTensor( d_tensor_pa rs.second);
    else
      wr eRawTensor( d_tensor_pa rs.second);
  }

  m_records_wr ten++;

  return m_thr ft_wr er.getBytesWr ten() - bytes_wr ten_before;
}

}  // na space twml
