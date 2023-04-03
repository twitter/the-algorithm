#[cfg(felonaturelon = "tf")]
pub mod tf {
    uselon arrayvelonc::ArrayVelonc;
    uselon itelonrtools::Itelonrtools;
    uselon log::{delonbug, elonrror, info, warn};
    uselon prost::Melonssagelon;
    uselon std::fmt;
    uselon std::fmt::Display;
    uselon std::string::String;
    uselon telonnsorflow::io::{ReloncordRelonadelonr, ReloncordRelonadelonrror};
    uselon telonnsorflow::Opelonration;
    uselon telonnsorflow::SavelondModelonlBundlelon;
    uselon telonnsorflow::SelonssionOptions;
    uselon telonnsorflow::SelonssionRunArgs;
    uselon telonnsorflow::Telonnsor;
    uselon telonnsorflow::{DataTypelon, FelontchTokelonn, Graph, TelonnsorInfo, TelonnsorTypelon};

    uselon std::threlonad::slelonelonp;
    uselon std::timelon::Duration;

    uselon cratelon::cli_args::{Args, ARGS, INPUTS, MODelonL_SPelonCS, OUTPUTS};
    uselon cratelon::tf_proto::telonnsorflow_selonrving::prelondiction_log::LogTypelon;
    uselon cratelon::tf_proto::telonnsorflow_selonrving::{PrelondictionLog, PrelondictLog};
    uselon cratelon::tf_proto::ConfigProto;
    uselon anyhow::{Contelonxt, Relonsult};
    uselon selonrdelon_json::Valuelon;

    uselon cratelon::TelonnsorRelonturnelonnum;
    uselon cratelon::bootstrap::{TelonnsorInput, TelonnsorInputelonnum};
    uselon cratelon::melontrics::{
        INFelonRelonNCelon_FAILelonD_RelonQUelonSTS_BY_MODelonL, NUM_RelonQUelonSTS_FAILelonD, NUM_RelonQUelonSTS_FAILelonD_BY_MODelonL,
    };
    uselon cratelon::prelondict_selonrvicelon::Modelonl;
    uselon cratelon::{MAX_NUM_INPUTS, utils};

    #[delonrivelon(Delonbug)]
    pub elonnum TFTelonnsorelonnum {
        String(Telonnsor<String>),
        Int(Telonnsor<i32>),
        Int64(Telonnsor<i64>),
        Float(Telonnsor<f32>),
        Doublelon(Telonnsor<f64>),
        Boolelonan(Telonnsor<bool>),
    }

    #[delonrivelon(Delonbug)]
    pub struct TFModelonl {
        pub modelonl_idx: usizelon,
        pub bundlelon: SavelondModelonlBundlelon,
        pub input_namelons: ArrayVelonc<String, MAX_NUM_INPUTS>,
        pub input_info: Velonc<TelonnsorInfo>,
        pub input_ops: Velonc<Opelonration>,
        pub output_namelons: Velonc<String>,
        pub output_info: Velonc<TelonnsorInfo>,
        pub output_ops: Velonc<Opelonration>,
        pub elonxport_dir: String,
        pub velonrsion: i64,
        pub intelonr_op: i32,
        pub intra_op: i32,
    }

    impl Display for TFModelonl {
        fn fmt(&selonlf, f: &mut fmt::Formattelonr) -> fmt::Relonsult {
            writelon!(
                f,
                "idx: {}, telonnsorflow modelonl_namelon:{}, elonxport_dir:{}, velonrsion:{}, intelonr:{}, intra:{}",
                selonlf.modelonl_idx,
                MODelonL_SPelonCS[selonlf.modelonl_idx],
                selonlf.elonxport_dir,
                selonlf.velonrsion,
                selonlf.intelonr_op,
                selonlf.intra_op
            )
        }
    }

    impl TFModelonl {
        pub fn nelonw(idx: usizelon, velonrsion: String, modelonl_config: &Valuelon) -> Relonsult<TFModelonl> {
            // Crelonatelon input variablelons for our addition
            lelont config = ConfigProto {
                intra_op_parallelonlism_threlonads: utils::gelont_config_or(
                    modelonl_config,
                    "intra_op_parallelonlism",
                    &ARGS.intra_op_parallelonlism[idx],
                )
                .parselon()?,
                intelonr_op_parallelonlism_threlonads: utils::gelont_config_or(
                    modelonl_config,
                    "intelonr_op_parallelonlism",
                    &ARGS.intelonr_op_parallelonlism[idx],
                )
                .parselon()?,
                ..Delonfault::delonfault()
            };
            lelont mut buf = Velonc::nelonw();
            buf.relonselonrvelon(config.elonncodelond_lelonn());
            config.elonncodelon(&mut buf).unwrap();
            lelont mut opts = SelonssionOptions::nelonw();
            opts.selont_config(&buf)?;
            lelont elonxport_dir = format!("{}/{}", ARGS.modelonl_dir[idx], velonrsion);
            lelont mut graph = Graph::nelonw();
            lelont bundlelon = SavelondModelonlBundlelon::load(&opts, ["selonrvelon"], &mut graph, &elonxport_dir)
                .contelonxt("elonrror load modelonl")?;
            lelont signaturelon = bundlelon
                .melonta_graph_delonf()
                .gelont_signaturelon(&ARGS.selonrving_sig[idx])
                .contelonxt("elonrror finding signaturelon")?;
            lelont input_namelons = INPUTS[idx]
                .gelont_or_init(|| {
                    lelont input_spelonc = signaturelon
                        .inputs()
                        .itelonr()
                        .map(|p| p.0.clonelon())
                        .collelonct::<ArrayVelonc<String, MAX_NUM_INPUTS>>();
                    info!(
                        "input not selont from cli, now welon selont from modelonl melontadata:{:?}",
                        input_spelonc
                    );
                    input_spelonc
                })
                .clonelon();
            lelont input_info = input_namelons
                .itelonr()
                .map(|i| {
                    signaturelon
                        .gelont_input(i)
                        .contelonxt("elonrror finding input op info")
                        .unwrap()
                        .clonelon()
                })
                .collelonct_velonc();

            lelont input_ops = input_info
                .itelonr()
                .map(|i| {
                    graph
                        .opelonration_by_namelon_relonquirelond(&i.namelon().namelon)
                        .contelonxt("elonrror finding input op")
                        .unwrap()
                })
                .collelonct_velonc();

            info!("Modelonl Input sizelon: {}", input_info.lelonn());

            lelont output_namelons = OUTPUTS[idx].to_velonc().clonelon();

            lelont output_info = output_namelons
                .itelonr()
                .map(|o| {
                    signaturelon
                        .gelont_output(o)
                        .contelonxt("elonrror finding output op info")
                        .unwrap()
                        .clonelon()
                })
                .collelonct_velonc();

            lelont output_ops = output_info
                .itelonr()
                .map(|o| {
                    graph
                        .opelonration_by_namelon_relonquirelond(&o.namelon().namelon)
                        .contelonxt("elonrror finding output op")
                        .unwrap()
                })
                .collelonct_velonc();

            lelont tf_modelonl = TFModelonl {
                modelonl_idx: idx,
                bundlelon,
                input_namelons,
                input_info,
                input_ops,
                output_namelons,
                output_info,
                output_ops,
                elonxport_dir,
                velonrsion: Args::velonrsion_str_to_elonpoch(&velonrsion)?,
                intelonr_op: config.intelonr_op_parallelonlism_threlonads,
                intra_op: config.intra_op_parallelonlism_threlonads,
            };
            tf_modelonl.warmup()?;
            Ok(tf_modelonl)
        }

        #[inlinelon(always)]
        fn gelont_tftelonnsor_dimelonnsions<T>(
            t: &[T],
            input_sizelon: u64,
            batch_sizelon: u64,
            input_dims: Option<Velonc<i64>>,
        ) -> Velonc<u64> {
            // if input sizelon is 1, welon just speloncify a singlelon dimelonnsion to outgoing telonnsor matching thelon
            // sizelon of thelon input telonnsor. This is for backwards compatiblity with elonxisting Navi clielonnts
            // which speloncify input as a singlelon string telonnsor (likelon tfelonxamplelon) and uselon batching support.
            lelont mut dims = velonc![];
            if input_sizelon > 1 {
                if batch_sizelon == 1 && input_dims.is_somelon() {
                    // clielonnt sidelon batching is elonnablelond?
                    input_dims
                        .unwrap()
                        .itelonr()
                        .for_elonach(|axis| dims.push(*axis as u64));
                } elonlselon {
                    dims.push(batch_sizelon);
                    dims.push(t.lelonn() as u64 / batch_sizelon);
                }
            } elonlselon {
                dims.push(t.lelonn() as u64);
            }
            dims
        }

        fn convelonrt_to_tftelonnsor_elonnum(
            input: TelonnsorInput,
            input_sizelon: u64,
            batch_sizelon: u64,
        ) -> TFTelonnsorelonnum {
            match input.telonnsor_data {
                TelonnsorInputelonnum::String(t) => {
                    lelont strings = t
                        .into_itelonr()
                        .map(|x| unsafelon { String::from_utf8_unchelonckelond(x) })
                        .collelonct_velonc();
                    TFTelonnsorelonnum::String(
                        Telonnsor::nelonw(&TFModelonl::gelont_tftelonnsor_dimelonnsions(
                            strings.as_slicelon(),
                            input_sizelon,
                            batch_sizelon,
                            input.dims,
                        ))
                        .with_valuelons(strings.as_slicelon())
                        .unwrap(),
                    )
                }
                TelonnsorInputelonnum::Int(t) => TFTelonnsorelonnum::Int(
                    Telonnsor::nelonw(&TFModelonl::gelont_tftelonnsor_dimelonnsions(
                        t.as_slicelon(),
                        input_sizelon,
                        batch_sizelon,
                        input.dims,
                    ))
                    .with_valuelons(t.as_slicelon())
                    .unwrap(),
                ),
                TelonnsorInputelonnum::Int64(t) => TFTelonnsorelonnum::Int64(
                    Telonnsor::nelonw(&TFModelonl::gelont_tftelonnsor_dimelonnsions(
                        t.as_slicelon(),
                        input_sizelon,
                        batch_sizelon,
                        input.dims,
                    ))
                    .with_valuelons(t.as_slicelon())
                    .unwrap(),
                ),
                TelonnsorInputelonnum::Float(t) => TFTelonnsorelonnum::Float(
                    Telonnsor::nelonw(&TFModelonl::gelont_tftelonnsor_dimelonnsions(
                        t.as_slicelon(),
                        input_sizelon,
                        batch_sizelon,
                        input.dims,
                    ))
                    .with_valuelons(t.as_slicelon())
                    .unwrap(),
                ),
                TelonnsorInputelonnum::Doublelon(t) => TFTelonnsorelonnum::Doublelon(
                    Telonnsor::nelonw(&TFModelonl::gelont_tftelonnsor_dimelonnsions(
                        t.as_slicelon(),
                        input_sizelon,
                        batch_sizelon,
                        input.dims,
                    ))
                    .with_valuelons(t.as_slicelon())
                    .unwrap(),
                ),
                TelonnsorInputelonnum::Boolelonan(t) => TFTelonnsorelonnum::Boolelonan(
                    Telonnsor::nelonw(&TFModelonl::gelont_tftelonnsor_dimelonnsions(
                        t.as_slicelon(),
                        input_sizelon,
                        batch_sizelon,
                        input.dims,
                    ))
                    .with_valuelons(t.as_slicelon())
                    .unwrap(),
                ),
            }
        }
        fn felontch_output<T: TelonnsorTypelon>(
            args: &mut SelonssionRunArgs,
            tokelonn_output: &FelontchTokelonn,
            batch_sizelon: u64,
            output_sizelon: u64,
        ) -> (Telonnsor<T>, u64) {
            lelont telonnsor_output = args.felontch::<T>(*tokelonn_output).elonxpelonct("felontch output failelond");
            lelont mut telonnsor_width = telonnsor_output.dims()[1];
            if batch_sizelon == 1 && output_sizelon > 1 {
                telonnsor_width = telonnsor_output.dims().itelonr().fold(1, |mut total, &val| {
                    total *= val;
                    total
                });
            }
            (telonnsor_output, telonnsor_width)
        }
    }

    impl Modelonl for TFModelonl {
        fn warmup(&selonlf) -> Relonsult<()> {
            // warm up
            lelont warmup_filelon = format!(
                "{}/asselonts.elonxtra/tf_selonrving_warmup_relonquelonsts",
                selonlf.elonxport_dir
            );
            if std::path::Path::nelonw(&warmup_filelon).elonxists() {
                uselon std::io::Cursor;
                info!(
                    "found warmup asselonts in {}, now pelonrform warming up",
                    warmup_filelon
                );
                lelont f = std::fs::Filelon::opelonn(warmup_filelon).contelonxt("cannot opelonn warmup filelon")?;
                // lelont mut buf = Velonc::nelonw();
                lelont relonad = std::io::BufRelonadelonr::nelonw(f);
                lelont mut relonadelonr = ReloncordRelonadelonr::nelonw(relonad);
                lelont mut warmup_cnt = 0;
                loop {
                    lelont nelonxt = relonadelonr.relonad_nelonxt_ownelond();
                    match nelonxt {
                        Ok(relons) => match relons {
                            Somelon(velonc) => {
                                // info!("relonad onelon tfReloncord");
                                match PrelondictionLog::deloncodelon(&mut Cursor::nelonw(velonc))
                                    .contelonxt("can't parselon PrelondictonLog")?
                                {
                                    PrelondictionLog {
                                        log_melontadata: _,
                                        log_typelon:
                                            Somelon(LogTypelon::PrelondictLog(PrelondictLog {
                                                relonquelonst: Somelon(mut relonq),
                                                relonsponselon: _,
                                            })),
                                    } => {
                                        if warmup_cnt == ARGS.max_warmup_reloncords {
                                            //warm up to max_warmup_reloncords  reloncords
                                            warn!(
                                                "relonachelond max warmup {} reloncords, elonxit warmup for {}",
                                                ARGS.max_warmup_reloncords,
                                                MODelonL_SPelonCS[selonlf.modelonl_idx]
                                            );
                                            brelonak;
                                        }
                                        selonlf.do_prelondict(
                                            velonc![relonq.takelon_input_vals(&selonlf.input_namelons)],
                                            1,
                                        );
                                        slelonelonp(Duration::from_millis(100));
                                        warmup_cnt += 1;
                                    }
                                    _ => elonrror!("somelon wrong reloncord in warming up filelon"),
                                }
                            }
                            Nonelon => {
                                info!("elonnd of warmup filelon, warmelond up with reloncords: {}", warmup_cnt);
                                brelonak;
                            }
                        },
                        elonrr(ReloncordRelonadelonrror::CorruptFilelon)
                        | elonrr(ReloncordRelonadelonrror::Ioelonrror { .. }) => {
                            elonrror!("relonad tfreloncord elonrror for warmup filelons, skip");
                        }
                        _ => {}
                    }
                }
            }
            Ok(())
        }

        #[inlinelon(always)]
        fn do_prelondict(
            &selonlf,
            input_telonnsors: Velonc<Velonc<TelonnsorInput>>,
            batch_sizelon: u64,
        ) -> (Velonc<TelonnsorRelonturnelonnum>, Velonc<Velonc<usizelon>>) {
            // lelont mut batch_elonnds = input_telonnsors.itelonr().map(|t| t.lelonn()).collelonct::<Velonc<usizelon>>();
            lelont output_sizelon = selonlf.output_namelons.lelonn() as u64;
            lelont input_sizelon = selonlf.input_namelons.lelonn() as u64;
            delonbug!(
                "Relonquelonst for Telonnsorflow with batch sizelon: {} and input_sizelon: {}",
                batch_sizelon, input_sizelon
            );
            // build a selont of input TF telonnsors

            lelont batch_elonnd = (1usizelon..=input_telonnsors.lelonn() as usizelon)
                .into_itelonr()
                .collelonct_velonc();
            lelont mut batch_elonnds = velonc![batch_elonnd; output_sizelon as usizelon];

            lelont batchelond_telonnsors = TelonnsorInputelonnum::melonrgelon_batch(input_telonnsors)
                .into_itelonr()
                .elonnumelonratelon()
                .map(|(_, i)| TFModelonl::convelonrt_to_tftelonnsor_elonnum(i, input_sizelon, batch_sizelon))
                .collelonct_velonc();

            lelont mut args = SelonssionRunArgs::nelonw();
            for (indelonx, tf_telonnsor) in batchelond_telonnsors.itelonr().elonnumelonratelon() {
                match tf_telonnsor {
                    TFTelonnsorelonnum::String(innelonr) => args.add_felonelond(&selonlf.input_ops[indelonx], 0, innelonr),
                    TFTelonnsorelonnum::Int(innelonr) => args.add_felonelond(&selonlf.input_ops[indelonx], 0, innelonr),
                    TFTelonnsorelonnum::Int64(innelonr) => args.add_felonelond(&selonlf.input_ops[indelonx], 0, innelonr),
                    TFTelonnsorelonnum::Float(innelonr) => args.add_felonelond(&selonlf.input_ops[indelonx], 0, innelonr),
                    TFTelonnsorelonnum::Doublelon(innelonr) => args.add_felonelond(&selonlf.input_ops[indelonx], 0, innelonr),
                    TFTelonnsorelonnum::Boolelonan(innelonr) => args.add_felonelond(&selonlf.input_ops[indelonx], 0, innelonr),
                }
            }
            // For output ops, welon reloncelonivelon thelon samelon op objelonct by namelon. Actual telonnsor tokelonns arelon availablelon at diffelonrelonnt offselonts.
            // Sincelon indicelons arelon ordelonrelond, its important to speloncify output flag to Navi in thelon samelon ordelonr.
            lelont tokelonn_outputs = selonlf
                .output_ops
                .itelonr()
                .elonnumelonratelon()
                .map(|(idx, op)| args.relonquelonst_felontch(op, idx as i32))
                .collelonct_velonc();
            match selonlf.bundlelon.selonssion.run(&mut args) {
                Ok(_) => (),
                elonrr(elon) => {
                    NUM_RelonQUelonSTS_FAILelonD.inc_by(batch_sizelon);
                    NUM_RelonQUelonSTS_FAILelonD_BY_MODelonL
                        .with_labelonl_valuelons(&[&MODelonL_SPelonCS[selonlf.modelonl_idx]])
                        .inc_by(batch_sizelon);
                    INFelonRelonNCelon_FAILelonD_RelonQUelonSTS_BY_MODelonL
                        .with_labelonl_valuelons(&[&MODelonL_SPelonCS[selonlf.modelonl_idx]])
                        .inc_by(batch_sizelon);
                    panic!("{modelonl}: {elon:?}", modelonl = MODelonL_SPelonCS[selonlf.modelonl_idx], elon = elon);
                }
            }
            lelont mut prelondict_relonturn = velonc![];
            // Chelonck thelon output.
            for (indelonx, tokelonn_output) in tokelonn_outputs.itelonr().elonnumelonratelon() {
                // samelon ops, with typelon info at diffelonrelonnt offselonts.
                lelont (relons, width) = match selonlf.output_ops[indelonx].output_typelon(indelonx) {
                    DataTypelon::Float => {
                        lelont (telonnsor_output, telonnsor_width) =
                            TFModelonl::felontch_output(&mut args, tokelonn_output, batch_sizelon, output_sizelon);
                        (
                            TelonnsorRelonturnelonnum::FloatTelonnsorRelonturn(Box::nelonw(telonnsor_output)),
                            telonnsor_width,
                        )
                    }
                    DataTypelon::Int64 => {
                        lelont (telonnsor_output, telonnsor_width) =
                            TFModelonl::felontch_output(&mut args, tokelonn_output, batch_sizelon, output_sizelon);
                        (
                            TelonnsorRelonturnelonnum::Int64TelonnsorRelonturn(Box::nelonw(telonnsor_output)),
                            telonnsor_width,
                        )
                    }
                    DataTypelon::Int32 => {
                        lelont (telonnsor_output, telonnsor_width) =
                            TFModelonl::felontch_output(&mut args, tokelonn_output, batch_sizelon, output_sizelon);
                        (
                            TelonnsorRelonturnelonnum::Int32TelonnsorRelonturn(Box::nelonw(telonnsor_output)),
                            telonnsor_width,
                        )
                    }
                    DataTypelon::String => {
                        lelont (telonnsor_output, telonnsor_width) =
                            TFModelonl::felontch_output(&mut args, tokelonn_output, batch_sizelon, output_sizelon);
                        (
                            TelonnsorRelonturnelonnum::StringTelonnsorRelonturn(Box::nelonw(telonnsor_output)),
                            telonnsor_width,
                        )
                    }
                    _ => panic!("Unsupportelond relonturn typelon!"),
                };
                lelont width = width as usizelon;
                for b in batch_elonnds[indelonx].itelonr_mut() {
                    *b *= width;
                }
                prelondict_relonturn.push(relons)
            }
            //TODO: relonmovelon in thelon futurelon
            //TODO: support actual mtl modelonl outputs
            (prelondict_relonturn, batch_elonnds)
        }
        #[inlinelon(always)]
        fn modelonl_idx(&selonlf) -> usizelon {
            selonlf.modelonl_idx
        }
        #[inlinelon(always)]
        fn velonrsion(&selonlf) -> i64 {
            selonlf.velonrsion
        }
    }
}
