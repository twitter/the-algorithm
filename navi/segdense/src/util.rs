use log::debug;
use std::fs;

use serde_json::{Map, Value};

use crate::error::SegDenseError;
use crate::mapper::{Feature nfo, FeatureMapper, MapWr er};
use crate::segdense_transform_spec_ho _recap_2022::{self as seg_dense,  nputFeature};

pub fn load_conf g(f le_na : &str) -> Result<seg_dense::Root, SegDenseError> {
    let json_str = fs::read_to_str ng(f le_na )?;
    // &format!("Unable to load segdense f le {}", f le_na ));
    let seg_dense_conf g = parse(&json_str)?;
    // &format!("Unable to parse segdense f le {}", f le_na ));
    Ok(seg_dense_conf g)
}

pub fn parse(json_str: &str) -> Result<seg_dense::Root, SegDenseError> {
    let root: seg_dense::Root = serde_json::from_str(json_str)?;
    Ok(root)
}

/**
 * G ven a json str ng conta n ng a seg dense sc ma create a feature mapper
 * wh ch  s essent ally:
 *
 *   {feature- d -> (Tensor  ndex,  ndex of feature w h n t  tensor)}
 *
 *   Feature  d : 64 b  hash of t  feature na  used  n DataRecords.
 *
 *   Tensor  ndex : A vector of tensors  s passed to t  model. Tensor
 *      ndex refers to t  tensor t  feature  s part of.
 *
 *    ndex of feature  n tensor : T  tensors are vectors, t   ndex of
 *     feature  s t  pos  on to put t  feature value.
 *
 * T re are many assumpt ons made  n t  funct on that  s very model spec f c.
 * T se assumpt ons are called out below and need to be sc mat zed eventually.
 *
 * Call t  once for each segdense sc ma and cac  t  FeatureMapper.
 */
pub fn safe_load_conf g(json_str: &str) -> Result<FeatureMapper, SegDenseError> {
    let root = parse(json_str)?;
    load_from_parsed_conf g(root)
}

// Perf note : make 'root' un-owned
pub fn load_from_parsed_conf g(root: seg_dense::Root) -> Result<FeatureMapper, SegDenseError> {
    let v = root. nput_features_map;

    // Do error c ck
    let map: Map<Str ng, Value> = match v {
        Value::Object(map) => map,
        _ => return Err(SegDenseError::JsonM ss ngObject),
    };

    let mut fm: FeatureMapper = FeatureMapper::new();

    let  ems = map.values();

    // Perf : Cons der a way to avo d clone  re
    for  em  n  ems.cloned() {
        let mut vec = match  em {
            Value::Array(v) => v,
            _ => return Err(SegDenseError::JsonM ss ngArray),
        };

         f vec.len() != 1 {
            return Err(SegDenseError::JsonArrayS ze);
        }

        let val = vec.pop().unwrap();

        let  nput_feature: seg_dense:: nputFeature = serde_json::from_value(val)?;
        let feature_ d =  nput_feature.feature_ d;
        let feature_ nfo = to_feature_ nfo(& nput_feature);

        match feature_ nfo {
            So ( nfo) => {
                debug!("{:?}",  nfo);
                fm.set(feature_ d,  nfo)
            }
            None => (),
        }
    }

    Ok(fm)
}
#[allow(dead_code)]
fn add_feature_ nfo_to_mapper(
    feature_mapper: &mut FeatureMapper,
     nput_features: &Vec< nputFeature>,
) {
    for  nput_feature  n  nput_features. er() {
        let feature_ d =  nput_feature.feature_ d;
        let feature_ nfo = to_feature_ nfo( nput_feature);

        match feature_ nfo {
            So ( nfo) => {
                debug!("{:?}",  nfo);
                feature_mapper.set(feature_ d,  nfo)
            }
            None => (),
        }
    }
}

pub fn to_feature_ nfo( nput_feature: &seg_dense:: nputFeature) -> Opt on<Feature nfo> {
     f  nput_feature.maybe_exclude {
        return None;
    }

    // T  part needs to be sc ma dr ven
    //
    //   tensor  ndex : Wh ch of t se tensors t  feature  s part of
    //      [Cont n ous, B nary, D screte, User_embedd ng, user_eng_embedd ng, author_embedd ng]
    //      Note that t  order  s f xed/hardcoded  re, and need to be sc mat zed
    //
    let tensor_ dx:  8 = match  nput_feature.feature_ d {
        // user.t  l nes.twh n_user_follow_embedd ngs.twh n_user_follow_embedd ngs
        // Feature na   s mapped to a feature- d value. T  hardcoded values below correspond to a spec f c feature na .
        -2550691008059411095 => 3,

        // user.t  l nes.twh n_user_engage nt_embedd ngs.twh n_user_engage nt_embedd ngs
        5390650078733277231 => 4,

        // or g nal_author.t  l nes.twh n_author_follow_embedd ngs.twh n_author_follow_embedd ngs
        3223956748566688423 => 5,

        _ => match  nput_feature.feature_type {
            //   feature_type : src/thr ft/com/tw ter/ml/ap /data.thr ft
            //       B NARY = 1, CONT NUOUS = 2, D SCRETE = 3,
            //    Map to slots  n [Cont n ous, B nary, D screte, ..]
            1 => 1,
            2 => 0,
            3 => 2,
            _ => -1,
        },
    };

     f  nput_feature. ndex < 0 {
        return None;
    }

    // Handle t  case later
     f tensor_ dx == -1 {
        return None;
    }

    So (Feature nfo {
        tensor_ ndex: tensor_ dx,
         ndex_w h n_tensor:  nput_feature. ndex,
    })
}
