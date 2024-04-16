# nclude " nternal/error.h"
# nclude <twml/Tensor.h>
# nclude <twml/Type.h>
# nclude <type_tra s>
# nclude <algor hm>
# nclude <nu r c>

na space twml {

us ng std::vector;

Tensor::Tensor(vo d *data,  nt nd ms, const u nt64_t *d ms, const u nt64_t *str des, twml_type type) :
    m_type(type), m_data(data),
    m_d ms(d ms, d ms + nd ms),
    m_str des(str des, str des + nd ms) {
}

Tensor::Tensor(vo d *data,
               const vector<u nt64_t> &d ms,
               const vector<u nt64_t> &str des,
               twml_type type) :
    m_type(type), m_data(data),
    m_d ms(d ms.beg n(), d ms.end()),
    m_str des(str des.beg n(), str des.end()) {
   f (d ms.s ze() != str des.s ze()) {
    throw twml::Error(TWML_ERR_S ZE, "T  number s ze of d ms and str des don't match");
  }
}

 nt Tensor::getNumD ms() const {
  return stat c_cast< nt>(m_d ms.s ze());
}

u nt64_t Tensor::getD m( nt  d) const {
   f ( d >= t ->getNumD ms()) {
    throw twml::Error(TWML_ERR_S ZE, "Requested d  ns on exceeds tensor d  ns on");
  }
  return m_d ms[ d];
}

u nt64_t Tensor::getStr de( nt  d) const {
   f ( d >= t ->getNumD ms()) {
    throw twml::Error(TWML_ERR_S ZE, "Requested d  ns on exceeds tensor d  ns on");
  }
  return m_str des[ d];
}

u nt64_t Tensor::getNumEle nts() const {
  return std::accumulate(m_d ms.beg n(), m_d ms.end(), 1, std::mult pl es< nt>());
}

twml_type Tensor::getType() const {
  return m_type;
}

twml_tensor Tensor::getHandle() {
  return re nterpret_cast<twml_tensor>(t );
}

const twml_tensor Tensor::getHandle() const {
  return re nterpret_cast<const twml_tensor>(const_cast<Tensor *>(t ));
}

const Tensor *getConstTensor(const twml_tensor t) {
  return re nterpret_cast<const Tensor *>(t);
}

Tensor *getTensor(twml_tensor t) {
  return re nterpret_cast<Tensor *>(t);
}

#def ne  NSTANT ATE(T)                                  \
  template<> TWMLAP  T *Tensor::getData() {             \
     f ((twml_type)Type<T>::type != m_type) {           \
      throw twml::Error(TWML_ERR_TYPE,                  \
                        "Requested  nval d type");      \
    }                                                   \
    return re nterpret_cast<T *>(m_data);               \
  }                                                     \
  template<> TWMLAP  const T *Tensor::getData() const { \
     f ((twml_type)Type<T>::type != m_type) {           \
      throw twml::Error(TWML_ERR_TYPE,                  \
                        "Requested  nval d type");      \
    }                                                   \
    return (const T *)m_data;                           \
  }                                                     \

 NSTANT ATE( nt32_t)
 NSTANT ATE( nt64_t)
 NSTANT ATE( nt8_t)
 NSTANT ATE(u nt8_t)
 NSTANT ATE(float)
 NSTANT ATE(double)
 NSTANT ATE(bool)
 NSTANT ATE(std::str ng)

// T   s used for t  C ap . No c cks needed for vo d.
template<> TWMLAP  vo d *Tensor::getData() {
  return m_data;
}
template<> TWMLAP  const vo d *Tensor::getData() const {
  return (const vo d *)m_data;
}

std::str ng getTypeNa (twml_type type) {
  sw ch (type) {
    case TWML_TYPE_FLOAT32 : return "float32";
    case TWML_TYPE_FLOAT64 : return "float64";
    case TWML_TYPE_ NT32   : return " nt32";
    case TWML_TYPE_ NT64   : return " nt64";
    case TWML_TYPE_ NT8    : return " nt8";
    case TWML_TYPE_U NT8   : return "u nt8";
    case TWML_TYPE_BOOL    : return "bool";
    case TWML_TYPE_STR NG  : return "str ng";
    case TWML_TYPE_UNKNOWN : return "Unknown type";
  }
  throw twml::Error(TWML_ERR_TYPE, "Uknown type");
}

u nt64_t getS zeOf(twml_type dtype) {
  sw ch (dtype) {
    case TWML_TYPE_FLOAT  : return 4;
    case TWML_TYPE_DOUBLE : return 8;
    case TWML_TYPE_ NT64  : return 8;
    case TWML_TYPE_ NT32  : return 4;
    case TWML_TYPE_U NT8  : return 1;
    case TWML_TYPE_BOOL   : return 1;
    case TWML_TYPE_ NT8   : return 1;
    case TWML_TYPE_STR NG :
      throw twml::Error(TWML_ERR_THR FT, "getS zeOf not supported for str ngs");
    case TWML_TYPE_UNKNOWN:
      throw twml::Error(TWML_ERR_THR FT, "Can't get s ze of unknown types");
  }
  throw twml::Error(TWML_ERR_THR FT, " nval d twml_type");
}

}  // na space twml

twml_err twml_tensor_create(twml_tensor *t, vo d *data,  nt nd ms, u nt64_t *d ms,
              u nt64_t *str des, twml_type type) {
  HANDLE_EXCEPT ONS(
    twml::Tensor *res =  new twml::Tensor(data, nd ms, d ms, str des, type);
    *t = re nterpret_cast<twml_tensor>(res););
  return TWML_ERR_NONE;
}

twml_err twml_tensor_delete(const twml_tensor t) {
  HANDLE_EXCEPT ONS(
    delete twml::getConstTensor(t););
  return TWML_ERR_NONE;
}

twml_err twml_tensor_get_type(twml_type *type, const twml_tensor t) {
  HANDLE_EXCEPT ONS(
    *type = twml::getConstTensor(t)->getType(););
  return TWML_ERR_NONE;
}

twml_err twml_tensor_get_data(vo d **data, const twml_tensor t) {
  HANDLE_EXCEPT ONS(
    *data = twml::getTensor(t)->getData<vo d>(););
  return TWML_ERR_NONE;
}

twml_err twml_tensor_get_d m(u nt64_t *d m, const twml_tensor t,  nt  d) {
  HANDLE_EXCEPT ONS(
    const twml::Tensor *tensor = twml::getConstTensor(t);
    *d m = tensor->getD m( d););
  return TWML_ERR_NONE;
}

twml_err twml_tensor_get_str de(u nt64_t *str de, const twml_tensor t,  nt  d) {
  HANDLE_EXCEPT ONS(
    const twml::Tensor *tensor = twml::getConstTensor(t);
    *str de = tensor->getStr de( d););
  return TWML_ERR_NONE;
}

twml_err twml_tensor_get_num_d ms( nt *nd m, const twml_tensor t) {
  HANDLE_EXCEPT ONS(
    const twml::Tensor *tensor = twml::getConstTensor(t);
    *nd m = tensor->getNumD ms(););
  return TWML_ERR_NONE;
}

twml_err twml_tensor_get_num_ele nts(u nt64_t *nele nts, const twml_tensor t) {
  HANDLE_EXCEPT ONS(
    const twml::Tensor *tensor = twml::getConstTensor(t);
    *nele nts = tensor->getNumEle nts(););
  return TWML_ERR_NONE;
}
