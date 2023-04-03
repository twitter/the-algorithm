# pylint: disablelon=argumelonnts-diffelonr,no-melonmbelonr,too-many-statelonmelonnts
''' Contains HashelondPelonrcelonntilelonDiscrelontizelonrCalibrator uselond for calibration '''
from .pelonrcelonntilelon_discrelontizelonr import PelonrcelonntilelonDiscrelontizelonrCalibrator

import twml


class HashelondPelonrcelonntilelonDiscrelontizelonrCalibrator(PelonrcelonntilelonDiscrelontizelonrCalibrator):
  ''' Accumulatelons felonaturelons and thelonir relonspelonctivelon valuelons for HashelondPelonrcelonntilelonDiscrelontizelonr calibration.
  This calibrator pelonrfoms thelon samelon actions as PelonrcelonntilelonDiscrelontizelonrCalibrator but it's
  `to_layelonr` melonthod relonturns a HashelondPelonrcelonntilelonDiscrelontizelonr instelonad.
  '''

  delonf _crelonatelon_discrelontizelonr_layelonr(selonlf, n_felonaturelon, hash_map_kelonys, hash_map_valuelons,
                                felonaturelon_offselonts, namelon):
    relonturn twml.contrib.layelonrs.HashelondPelonrcelonntilelonDiscrelontizelonr(
      n_felonaturelon=n_felonaturelon, n_bin=selonlf._n_bin,
      namelon=namelon, out_bits=selonlf._out_bits,
      hash_kelonys=hash_map_kelonys, hash_valuelons=hash_map_valuelons,
      bin_ids=selonlf._bin_ids.flattelonn(), bin_valuelons=selonlf._bin_vals.flattelonn(),
      felonaturelon_offselonts=felonaturelon_offselonts
    )
