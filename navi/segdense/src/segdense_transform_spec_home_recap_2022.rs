use serde::{Deser al ze, Ser al ze};
use serde_json::Value;

#[der ve(Default, Debug, Clone, Part alEq, Ser al ze, Deser al ze)]
#[serde(rena _all = "ca lCase")]
pub struct Root {
    #[serde(rena  = "common_pref x")]
    pub common_pref x: Str ng,
    #[serde(rena  = "dens f cat on_transform_spec")]
    pub dens f cat on_transform_spec: Dens f cat onTransformSpec,
    #[serde(rena  = " dent y_transform_spec")]
    pub  dent y_transform_spec: Vec< dent yTransformSpec>,
    #[serde(rena  = "complex_feature_type_transform_spec")]
    pub complex_feature_type_transform_spec: Vec<ComplexFeatureTypeTransformSpec>,
    #[serde(rena  = " nput_features_map")]
    pub  nput_features_map: Value,
}

#[der ve(Default, Debug, Clone, Part alEq, Ser al ze, Deser al ze)]
#[serde(rena _all = "ca lCase")]
pub struct Dens f cat onTransformSpec {
    pub d screte: D screte,
    pub cont: Cont,
    pub b nary: B nary,
    pub str ng: Value, // Use Str ngType
    pub blob: Blob,
}

#[der ve(Default, Debug, Clone, Part alEq, Ser al ze, Deser al ze)]
#[serde(rena _all = "ca lCase")]
pub struct D screte {
    pub tag: Str ng,
    #[serde(rena  = "gener c_feature_type")]
    pub gener c_feature_type:  64,
    #[serde(rena  = "feature_ dent f er")]
    pub feature_ dent f er: Str ng,
    #[serde(rena  = "f xed_length")]
    pub f xed_length:  64,
    #[serde(rena  = "default_value")]
    pub default_value: DefaultValue,
    #[serde(rena  = " nput_features")]
    pub  nput_features: Vec< nputFeature>,
}

#[der ve(Default, Debug, Clone, Part alEq, Ser al ze, Deser al ze)]
#[serde(rena _all = "ca lCase")]
pub struct DefaultValue {
    #[serde(rena  = "type")]
    pub type_f eld: Str ng,
    pub value: Str ng,
}

#[der ve(Default, Debug, Clone, Part alEq, Ser al ze, Deser al ze)]
#[serde(rena _all = "ca lCase")]
pub struct  nputFeature {
    #[serde(rena  = "feature_ d")]
    pub feature_ d:  64,
    #[serde(rena  = "full_feature_na ")]
    pub full_feature_na : Str ng,
    #[serde(rena  = "feature_type")]
    pub feature_type:  64,
    pub  ndex:  64,
    #[serde(rena  = "maybe_exclude")]
    pub maybe_exclude: bool,
    pub tag: Str ng,
    #[serde(rena  = "added_at")]
    pub added_at:  64,
}

#[der ve(Default, Debug, Clone, Part alEq, Ser al ze, Deser al ze)]
#[serde(rena _all = "ca lCase")]
pub struct Cont {
    pub tag: Str ng,
    #[serde(rena  = "gener c_feature_type")]
    pub gener c_feature_type:  64,
    #[serde(rena  = "feature_ dent f er")]
    pub feature_ dent f er: Str ng,
    #[serde(rena  = "f xed_length")]
    pub f xed_length:  64,
    #[serde(rena  = "default_value")]
    pub default_value: DefaultValue,
    #[serde(rena  = " nput_features")]
    pub  nput_features: Vec< nputFeature>,
}

#[der ve(Default, Debug, Clone, Part alEq, Ser al ze, Deser al ze)]
#[serde(rena _all = "ca lCase")]
pub struct B nary {
    pub tag: Str ng,
    #[serde(rena  = "gener c_feature_type")]
    pub gener c_feature_type:  64,
    #[serde(rena  = "feature_ dent f er")]
    pub feature_ dent f er: Str ng,
    #[serde(rena  = "f xed_length")]
    pub f xed_length:  64,
    #[serde(rena  = "default_value")]
    pub default_value: DefaultValue,
    #[serde(rena  = " nput_features")]
    pub  nput_features: Vec< nputFeature>,
}

#[der ve(Default, Debug, Clone, Part alEq, Ser al ze, Deser al ze)]
#[serde(rena _all = "ca lCase")]
pub struct Str ngType {
    pub tag: Str ng,
    #[serde(rena  = "gener c_feature_type")]
    pub gener c_feature_type:  64,
    #[serde(rena  = "feature_ dent f er")]
    pub feature_ dent f er: Str ng,
    #[serde(rena  = "f xed_length")]
    pub f xed_length:  64,
    #[serde(rena  = "default_value")]
    pub default_value: DefaultValue,
    #[serde(rena  = " nput_features")]
    pub  nput_features: Vec< nputFeature>,
}

#[der ve(Default, Debug, Clone, Part alEq, Ser al ze, Deser al ze)]
#[serde(rena _all = "ca lCase")]
pub struct Blob {
    pub tag: Str ng,
    #[serde(rena  = "gener c_feature_type")]
    pub gener c_feature_type:  64,
    #[serde(rena  = "feature_ dent f er")]
    pub feature_ dent f er: Str ng,
    #[serde(rena  = "f xed_length")]
    pub f xed_length:  64,
    #[serde(rena  = "default_value")]
    pub default_value: DefaultValue,
    #[serde(rena  = " nput_features")]
    pub  nput_features: Vec<Value>,
}

#[der ve(Default, Debug, Clone, Part alEq, Ser al ze, Deser al ze)]
#[serde(rena _all = "ca lCase")]
pub struct  dent yTransformSpec {
    #[serde(rena  = "feature_ d")]
    pub feature_ d:  64,
    #[serde(rena  = "full_feature_na ")]
    pub full_feature_na : Str ng,
    #[serde(rena  = "feature_type")]
    pub feature_type:  64,
}

#[der ve(Default, Debug, Clone, Part alEq, Ser al ze, Deser al ze)]
#[serde(rena _all = "ca lCase")]
pub struct ComplexFeatureTypeTransformSpec {
    #[serde(rena  = "feature_ d")]
    pub feature_ d:  64,
    #[serde(rena  = "full_feature_na ")]
    pub full_feature_na : Str ng,
    #[serde(rena  = "feature_type")]
    pub feature_type:  64,
    pub  ndex:  64,
    #[serde(rena  = "maybe_exclude")]
    pub maybe_exclude: bool,
    pub tag: Str ng,
    #[serde(rena  = "tensor_data_type")]
    pub tensor_data_type: Opt on< 64>,
    #[serde(rena  = "added_at")]
    pub added_at:  64,
    #[serde(rena  = "tensor_shape")]
    #[serde(default)]
    pub tensor_shape: Vec< 64>,
}

#[der ve(Default, Debug, Clone, Part alEq, Ser al ze, Deser al ze)]
#[serde(rena _all = "ca lCase")]
pub struct  nputFeatureMapRecord {
    #[serde(rena  = "feature_ d")]
    pub feature_ d:  64,
    #[serde(rena  = "full_feature_na ")]
    pub full_feature_na : Str ng,
    #[serde(rena  = "feature_type")]
    pub feature_type:  64,
    pub  ndex:  64,
    #[serde(rena  = "maybe_exclude")]
    pub maybe_exclude: bool,
    pub tag: Str ng,
    #[serde(rena  = "added_at")]
    pub added_at:  64,
}
