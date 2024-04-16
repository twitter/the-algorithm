use std::collect ons::BTreeSet;
use std::fmt::{self, Debug, D splay};
use std::fs;

use crate::all_conf g;
use crate::all_conf g::AllConf g;
use anyhow::{ba l, Context};
use bpr_thr ft::data::DataRecord;
use bpr_thr ft::pred ct on_serv ce::BatchPred ct onRequest;
use bpr_thr ft::tensor::GeneralTensor;
use log::debug;
use ndarray::Array2;
use once_cell::sync::OnceCell;
use ort::tensor:: nputTensor;
use pro t us::{ togramOpts,  togramVec};
use segdense::mapper::{FeatureMapper, MapReader};
use segdense::segdense_transform_spec_ho _recap_2022::{Dens f cat onTransformSpec, Root};
use segdense::ut l;
use thr ft::protocol::{TB nary nputProtocol, TSer al zable};
use thr ft::transport::TBufferChannel;

pub fn log_feature_match(
    dr: &DataRecord,
    seg_dense_conf g: &Dens f cat onTransformSpec,
    dr_type: Str ng,
) {
    // Note t  follow ng algor hm matc s features from conf g us ng l near search.
    // Also t  record s ce  s M nDataRecord. T   ncludes only b nary and cont nous features for now.

    for (feature_ d, feature_value)  n dr.cont nuous_features.as_ref().unwrap() {
        debug!(
            "{} - Cont nous Datarecord => Feature  D: {}, Feature value: {}",
            dr_type, feature_ d, feature_value
        );
        for  nput_feature  n &seg_dense_conf g.cont. nput_features {
             f  nput_feature.feature_ d == *feature_ d {
                debug!("Match ng  nput feature: {:?}",  nput_feature)
            }
        }
    }

    for feature_ d  n dr.b nary_features.as_ref().unwrap() {
        debug!(
            "{} - B nary Datarecord => Feature  D: {}",
            dr_type, feature_ d
        );
        for  nput_feature  n &seg_dense_conf g.b nary. nput_features {
             f  nput_feature.feature_ d == *feature_ d {
                debug!("Found  nput feature: {:?}",  nput_feature)
            }
        }
    }
}

pub fn log_feature_matc s(drs: &Vec<DataRecord>, seg_dense_conf g: &Dens f cat onTransformSpec) {
    for dr  n drs {
        log_feature_match(dr, seg_dense_conf g, Str ng::from(" nd v dual"));
    }
}

pub tra  Converter: Send + Sync + Debug + 'stat c + D splay {
    fn convert(&self,  nput: Vec<Vec<u8>>) -> (Vec< nputTensor>, Vec<us ze>);
}

#[der ve(Debug)]
#[allow(dead_code)]
pub struct BatchPred ct onRequestToTorchTensorConverter {
    all_conf g: AllConf g,
    seg_dense_conf g: Root,
    all_conf g_path: Str ng,
    seg_dense_conf g_path: Str ng,
    feature_mapper: FeatureMapper,
    user_embedd ng_feature_ d:  64,
    user_eng_embedd ng_feature_ d:  64,
    author_embedd ng_feature_ d:  64,
    d screte_features_to_report: BTreeSet< 64>,
    cont nuous_features_to_report: BTreeSet< 64>,
    d screte_feature_ tr cs: &'stat c  togramVec,
    cont nuous_feature_ tr cs: &'stat c  togramVec,
}

 mpl D splay for BatchPred ct onRequestToTorchTensorConverter {
    fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
        wr e!(
            f,
            "all_conf g_path: {}, seg_dense_conf g_path:{}",
            self.all_conf g_path, self.seg_dense_conf g_path
        )
    }
}

 mpl BatchPred ct onRequestToTorchTensorConverter {
    pub fn new(
        model_d r: &str,
        model_vers on: &str,
        report ng_feature_ ds: Vec<( 64, &str)>,
        reg ster_ tr c_fn: Opt on< mpl Fn(& togramVec)>,
    ) -> anyhow::Result<BatchPred ct onRequestToTorchTensorConverter> {
        let all_conf g_path = format!("{}/{}/all_conf g.json", model_d r, model_vers on);
        let seg_dense_conf g_path = format!(
            "{}/{}/segdense_transform_spec_ho _recap_2022.json",
            model_d r, model_vers on
        );
        let seg_dense_conf g = ut l::load_conf g(&seg_dense_conf g_path)?;
        let all_conf g = all_conf g::parse(
            &fs::read_to_str ng(&all_conf g_path)
                .w h_context(|| "error load ng all_conf g.json - ")?,
        )?;

        let feature_mapper = ut l::load_from_parsed_conf g(seg_dense_conf g.clone())?;

        let user_embedd ng_feature_ d = Self::get_feature_ d(
            &all_conf g
                .tra n_data
                .seg_dense_sc ma
                .rena d_features
                .user_embedd ng,
            &seg_dense_conf g,
        );
        let user_eng_embedd ng_feature_ d = Self::get_feature_ d(
            &all_conf g
                .tra n_data
                .seg_dense_sc ma
                .rena d_features
                .user_eng_embedd ng,
            &seg_dense_conf g,
        );
        let author_embedd ng_feature_ d = Self::get_feature_ d(
            &all_conf g
                .tra n_data
                .seg_dense_sc ma
                .rena d_features
                .author_embedd ng,
            &seg_dense_conf g,
        );
        stat c METR CS: OnceCell<( togramVec,  togramVec)> = OnceCell::new();
        let (d screte_feature_ tr cs, cont nuous_feature_ tr cs) = METR CS.get_or_ n (|| {
            let d screte =  togramVec::new(
                 togramOpts::new(":nav :feature_ d:d screte", "D screte Feature  D values")
                    .buckets(Vec::from(&[
                        0.0, 10.0, 20.0, 30.0, 40.0, 50.0, 60.0, 70.0, 80.0, 90.0, 100.0, 110.0,
                        120.0, 130.0, 140.0, 150.0, 160.0, 170.0, 180.0, 190.0, 200.0, 250.0,
                        300.0, 500.0, 1000.0, 10000.0, 100000.0,
                    ] as &'stat c [f64])),
                &["feature_ d"],
            )
            .expect(" tr c cannot be created");
            let cont nuous =  togramVec::new(
                 togramOpts::new(
                    ":nav :feature_ d:cont nuous",
                    "cont nuous Feature  D values",
                )
                .buckets(Vec::from(&[
                    0.0, 10.0, 20.0, 30.0, 40.0, 50.0, 60.0, 70.0, 80.0, 90.0, 100.0, 110.0, 120.0,
                    130.0, 140.0, 150.0, 160.0, 170.0, 180.0, 190.0, 200.0, 250.0, 300.0, 500.0,
                    1000.0, 10000.0, 100000.0,
                ] as &'stat c [f64])),
                &["feature_ d"],
            )
            .expect(" tr c cannot be created");
            reg ster_ tr c_fn.map(|r| {
                r(&d screte);
                r(&cont nuous);
            });
            (d screte, cont nuous)
        });

        let mut d screte_features_to_report = BTreeSet::new();
        let mut cont nuous_features_to_report = BTreeSet::new();

        for (feature_ d, feature_type)  n report ng_feature_ ds. er() {
            match *feature_type {
                "d screte" => d screte_features_to_report. nsert(feature_ d.clone()),
                "cont nuous" => cont nuous_features_to_report. nsert(feature_ d.clone()),
                _ => ba l!(
                    " nval d feature type {} for report ng  tr cs!",
                    feature_type
                ),
            };
        }

        Ok(BatchPred ct onRequestToTorchTensorConverter {
            all_conf g,
            seg_dense_conf g,
            all_conf g_path,
            seg_dense_conf g_path,
            feature_mapper,
            user_embedd ng_feature_ d,
            user_eng_embedd ng_feature_ d,
            author_embedd ng_feature_ d,
            d screte_features_to_report,
            cont nuous_features_to_report,
            d screte_feature_ tr cs,
            cont nuous_feature_ tr cs,
        })
    }

    fn get_feature_ d(feature_na : &str, seg_dense_conf g: &Root) ->  64 {
        // g ven a feature na ,   get t  complex feature type  d
        for feature  n &seg_dense_conf g.complex_feature_type_transform_spec {
             f feature.full_feature_na  == feature_na  {
                return feature.feature_ d;
            }
        }
        -1
    }

    fn parse_batch_pred ct on_request(bytes: Vec<u8>) -> BatchPred ct onRequest {
        // parse batch pred ct on request  nto a struct from byte array repr.
        let mut bc = TBufferChannel::w h_capac y(bytes.len(), 0);
        bc.set_readable_bytes(&bytes);
        let mut protocol = TB nary nputProtocol::new(bc, true);
        BatchPred ct onRequest::read_from_ n_protocol(&mut protocol).unwrap()
    }

    fn get_embedd ng_tensors(
        &self,
        bprs: &[BatchPred ct onRequest],
        feature_ d:  64,
        batch_s ze: &[us ze],
    ) -> Array2<f32> {
        // g ven an embedd ng feature  d, extract t  float tensor array  nto tensors.
        let cols: us ze = 200;
        let rows: us ze = batch_s ze[batch_s ze.len() - 1];
        let total_s ze = rows * cols;

        let mut work ng_set = vec![0 as f32; total_s ze];
        let mut bpr_start = 0;
        for (bpr, &bpr_end)  n bprs. er().z p(batch_s ze) {
             f bpr.common_features. s_so () {
                 f bpr.common_features.as_ref().unwrap().tensors. s_so () {
                     f bpr
                        .common_features
                        .as_ref()
                        .unwrap()
                        .tensors
                        .as_ref()
                        .unwrap()
                        .conta ns_key(&feature_ d)
                    {
                        let s ce_tensor = bpr
                            .common_features
                            .as_ref()
                            .unwrap()
                            .tensors
                            .as_ref()
                            .unwrap()
                            .get(&feature_ d)
                            .unwrap();
                        let tensor = match s ce_tensor {
                            GeneralTensor::FloatTensor(float_tensor) =>
                            //Tensor::of_sl ce(
                            {
                                float_tensor
                                    .floats
                                    . er()
                                    .map(|x| x. nto_ nner() as f32)
                                    .collect::<Vec<_>>()
                            }
                            _ => vec![0 as f32; cols],
                        };

                        // s nce t  tensor  s found  n common feature, add    n all batc s
                        for row  n bpr_start..bpr_end {
                            for col  n 0..cols {
                                work ng_set[row * cols + col] = tensor[col];
                            }
                        }
                    }
                }
            }
            // f nd t  feature  n  nd v dual feature l st and add to correspond ng batch.
            for ( ndex, datarecord)  n bpr. nd v dual_features_l st. er().enu rate() {
                 f datarecord.tensors. s_so ()
                    && datarecord
                        .tensors
                        .as_ref()
                        .unwrap()
                        .conta ns_key(&feature_ d)
                {
                    let s ce_tensor = datarecord
                        .tensors
                        .as_ref()
                        .unwrap()
                        .get(&feature_ d)
                        .unwrap();
                    let tensor = match s ce_tensor {
                        GeneralTensor::FloatTensor(float_tensor) => float_tensor
                            .floats
                            . er()
                            .map(|x| x. nto_ nner() as f32)
                            .collect::<Vec<_>>(),
                        _ => vec![0 as f32; cols],
                    };
                    for col  n 0..cols {
                        work ng_set[(bpr_start +  ndex) * cols + col] = tensor[col];
                    }
                }
            }
            bpr_start = bpr_end;
        }
        Array2::<f32>::from_shape_vec([rows, cols], work ng_set).unwrap()
    }

    // Todo : Refactor, create a gener c vers on w h d fferent type and f eld accessors
    //   Example paramter ze and t n  nst ant ate t  follow ng
    //           (FLOAT --> FLOAT, DataRecord.cont nuous_feature)
    //           (BOOL -->  NT64, DataRecord.b nary_feature)
    //           ( NT64 -->  NT64, DataRecord.d screte_feature)
    fn get_cont nuous(&self, bprs: &[BatchPred ct onRequest], batch_ends: &[us ze]) ->  nputTensor {
        // T se need to be part of model sc ma
        let rows: us ze = batch_ends[batch_ends.len() - 1];
        let cols: us ze = 5293;
        let full_s ze: us ze = rows * cols;
        let default_val = f32::NAN;

        let mut tensor = vec![default_val; full_s ze];

        let mut bpr_start = 0;
        for (bpr, &bpr_end)  n bprs. er().z p(batch_ends) {
            // Common features
             f bpr.common_features. s_so ()
                && bpr
                    .common_features
                    .as_ref()
                    .unwrap()
                    .cont nuous_features
                    . s_so ()
            {
                let common_features = bpr
                    .common_features
                    .as_ref()
                    .unwrap()
                    .cont nuous_features
                    .as_ref()
                    .unwrap();

                for feature  n common_features {
                    match self.feature_mapper.get(feature.0) {
                        So (f_ nfo) => {
                            let  dx = f_ nfo. ndex_w h n_tensor as us ze;
                             f  dx < cols {
                                // Set value  n each row
                                for r  n bpr_start..bpr_end {
                                    let flat_ ndex: us ze = r * cols +  dx;
                                    tensor[flat_ ndex] = feature.1. nto_ nner() as f32;
                                }
                            }
                        }
                        None => (),
                    }
                     f self.cont nuous_features_to_report.conta ns(feature.0) {
                        self.cont nuous_feature_ tr cs
                            .w h_label_values(&[feature.0.to_str ng().as_str()])
                            .observe(feature.1. nto_ nner())
                    } else  f self.d screte_features_to_report.conta ns(feature.0) {
                        self.d screte_feature_ tr cs
                            .w h_label_values(&[feature.0.to_str ng().as_str()])
                            .observe(feature.1. nto_ nner())
                    }
                }
            }

            // Process t  batch of datarecords
            for r  n bpr_start..bpr_end {
                let dr: &DataRecord =
                    &bpr. nd v dual_features_l st[us ze::try_from(r - bpr_start).unwrap()];
                 f dr.cont nuous_features. s_so () {
                    for feature  n dr.cont nuous_features.as_ref().unwrap() {
                        match self.feature_mapper.get(&feature.0) {
                            So (f_ nfo) => {
                                let  dx = f_ nfo. ndex_w h n_tensor as us ze;
                                let flat_ ndex: us ze = r * cols +  dx;
                                 f flat_ ndex < tensor.len() &&  dx < cols {
                                    tensor[flat_ ndex] = feature.1. nto_ nner() as f32;
                                }
                            }
                            None => (),
                        }
                         f self.cont nuous_features_to_report.conta ns(feature.0) {
                            self.cont nuous_feature_ tr cs
                                .w h_label_values(&[feature.0.to_str ng().as_str()])
                                .observe(feature.1. nto_ nner() as f64)
                        } else  f self.d screte_features_to_report.conta ns(feature.0) {
                            self.d screte_feature_ tr cs
                                .w h_label_values(&[feature.0.to_str ng().as_str()])
                                .observe(feature.1. nto_ nner() as f64)
                        }
                    }
                }
            }
            bpr_start = bpr_end;
        }

         nputTensor::FloatTensor(
            Array2::<f32>::from_shape_vec([rows, cols], tensor)
                .unwrap()
                . nto_dyn(),
        )
    }

    fn get_b nary(&self, bprs: &[BatchPred ct onRequest], batch_ends: &[us ze]) ->  nputTensor {
        // T se need to be part of model sc ma
        let rows: us ze = batch_ends[batch_ends.len() - 1];
        let cols: us ze = 149;
        let full_s ze: us ze = rows * cols;
        let default_val:  64 = 0;

        let mut v = vec![default_val; full_s ze];

        let mut bpr_start = 0;
        for (bpr, &bpr_end)  n bprs. er().z p(batch_ends) {
            // Common features
             f bpr.common_features. s_so ()
                && bpr
                    .common_features
                    .as_ref()
                    .unwrap()
                    .b nary_features
                    . s_so ()
            {
                let common_features = bpr
                    .common_features
                    .as_ref()
                    .unwrap()
                    .b nary_features
                    .as_ref()
                    .unwrap();

                for feature  n common_features {
                    match self.feature_mapper.get(feature) {
                        So (f_ nfo) => {
                            let  dx = f_ nfo. ndex_w h n_tensor as us ze;
                             f  dx < cols {
                                // Set value  n each row
                                for r  n bpr_start..bpr_end {
                                    let flat_ ndex: us ze = r * cols +  dx;
                                    v[flat_ ndex] = 1;
                                }
                            }
                        }
                        None => (),
                    }
                }
            }

            // Process t  batch of datarecords
            for r  n bpr_start..bpr_end {
                let dr: &DataRecord = &bpr. nd v dual_features_l st[r - bpr_start];
                 f dr.b nary_features. s_so () {
                    for feature  n dr.b nary_features.as_ref().unwrap() {
                        match self.feature_mapper.get(&feature) {
                            So (f_ nfo) => {
                                let  dx = f_ nfo. ndex_w h n_tensor as us ze;
                                let flat_ ndex: us ze = r * cols +  dx;
                                v[flat_ ndex] = 1;
                            }
                            None => (),
                        }
                    }
                }
            }
            bpr_start = bpr_end;
        }
         nputTensor:: nt64Tensor(
            Array2::< 64>::from_shape_vec([rows, cols], v)
                .unwrap()
                . nto_dyn(),
        )
    }

    #[allow(dead_code)]
    fn get_d screte(&self, bprs: &[BatchPred ct onRequest], batch_ends: &[us ze]) ->  nputTensor {
        // T se need to be part of model sc ma
        let rows: us ze = batch_ends[batch_ends.len() - 1];
        let cols: us ze = 320;
        let full_s ze: us ze = rows * cols;
        let default_val:  64 = 0;

        let mut v = vec![default_val; full_s ze];

        let mut bpr_start = 0;
        for (bpr, &bpr_end)  n bprs. er().z p(batch_ends) {
            // Common features
             f bpr.common_features. s_so ()
                && bpr
                    .common_features
                    .as_ref()
                    .unwrap()
                    .d screte_features
                    . s_so ()
            {
                let common_features = bpr
                    .common_features
                    .as_ref()
                    .unwrap()
                    .d screte_features
                    .as_ref()
                    .unwrap();

                for feature  n common_features {
                    match self.feature_mapper.get(feature.0) {
                        So (f_ nfo) => {
                            let  dx = f_ nfo. ndex_w h n_tensor as us ze;
                             f  dx < cols {
                                // Set value  n each row
                                for r  n bpr_start..bpr_end {
                                    let flat_ ndex: us ze = r * cols +  dx;
                                    v[flat_ ndex] = *feature.1;
                                }
                            }
                        }
                        None => (),
                    }
                     f self.d screte_features_to_report.conta ns(feature.0) {
                        self.d screte_feature_ tr cs
                            .w h_label_values(&[feature.0.to_str ng().as_str()])
                            .observe(*feature.1 as f64)
                    }
                }
            }

            // Process t  batch of datarecords
            for r  n bpr_start..bpr_end {
                let dr: &DataRecord = &bpr. nd v dual_features_l st[us ze::try_from(r).unwrap()];
                 f dr.d screte_features. s_so () {
                    for feature  n dr.d screte_features.as_ref().unwrap() {
                        match self.feature_mapper.get(&feature.0) {
                            So (f_ nfo) => {
                                let  dx = f_ nfo. ndex_w h n_tensor as us ze;
                                let flat_ ndex: us ze = r * cols +  dx;
                                 f flat_ ndex < v.len() &&  dx < cols {
                                    v[flat_ ndex] = *feature.1;
                                }
                            }
                            None => (),
                        }
                         f self.d screte_features_to_report.conta ns(feature.0) {
                            self.d screte_feature_ tr cs
                                .w h_label_values(&[feature.0.to_str ng().as_str()])
                                .observe(*feature.1 as f64)
                        }
                    }
                }
            }
            bpr_start = bpr_end;
        }
         nputTensor:: nt64Tensor(
            Array2::< 64>::from_shape_vec([rows, cols], v)
                .unwrap()
                . nto_dyn(),
        )
    }

    fn get_user_embedd ng(
        &self,
        bprs: &[BatchPred ct onRequest],
        batch_ends: &[us ze],
    ) ->  nputTensor {
         nputTensor::FloatTensor(
            self.get_embedd ng_tensors(bprs, self.user_embedd ng_feature_ d, batch_ends)
                . nto_dyn(),
        )
    }

    fn get_eng_embedd ng(
        &self,
        bpr: &[BatchPred ct onRequest],
        batch_ends: &[us ze],
    ) ->  nputTensor {
         nputTensor::FloatTensor(
            self.get_embedd ng_tensors(bpr, self.user_eng_embedd ng_feature_ d, batch_ends)
                . nto_dyn(),
        )
    }

    fn get_author_embedd ng(
        &self,
        bpr: &[BatchPred ct onRequest],
        batch_ends: &[us ze],
    ) ->  nputTensor {
         nputTensor::FloatTensor(
            self.get_embedd ng_tensors(bpr, self.author_embedd ng_feature_ d, batch_ends)
                . nto_dyn(),
        )
    }
}

 mpl Converter for BatchPred ct onRequestToTorchTensorConverter {
    fn convert(&self, batc d_bytes: Vec<Vec<u8>>) -> (Vec< nputTensor>, Vec<us ze>) {
        let bprs = batc d_bytes
            . nto_ er()
            .map(|bytes| {
                BatchPred ct onRequestToTorchTensorConverter::parse_batch_pred ct on_request(bytes)
            })
            .collect::<Vec<_>>();
        let batch_ends = bprs
            . er()
            .map(|bpr| bpr. nd v dual_features_l st.len())
            .scan(0us ze, |acc, e| {
                //runn ng total
                *acc = *acc + e;
                So (*acc)
            })
            .collect::<Vec<_>>();

        let t1 = self.get_cont nuous(&bprs, &batch_ends);
        let t2 = self.get_b nary(&bprs, &batch_ends);
        //let _t3 = self.get_d screte(&bprs, &batch_ends);
        let t4 = self.get_user_embedd ng(&bprs, &batch_ends);
        let t5 = self.get_eng_embedd ng(&bprs, &batch_ends);
        let t6 = self.get_author_embedd ng(&bprs, &batch_ends);

        (vec![t1, t2, t4, t5, t6], batch_ends)
    }
}
