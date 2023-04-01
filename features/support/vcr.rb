require 'vcr'

VCR.configure do |c|
  c.ignore_localhost = true
  c.cassette_library_dir     = 'features/cassette_library'
  c.hook_into                :webmock
  c.allow_http_connections_when_no_cassette = true

  # Cassettes are now deleted and re-recorded after 30 days. This will ensure
  # that LJ/DW/DA don't update their HTML and break our story parser without us
  # knowing about it.
  c.default_cassette_options = {
    record: :once,
    re_record_interval: 30.days
  }
end

VCR.cucumber_tags do |t|
  t.tags "@archivist_import", use_scenario_name: true

  t.tags "@work_import_multi_work_backdate"
  t.tags "@work_import_multi_chapter_backdate"
  t.tags "@work_import_special_characters_auto_utf"
  t.tags "@work_import_special_characters_auto_latin"
  t.tags "@work_import_special_characters_man_latin"
  t.tags "@work_import_special_characters_man_cp"
  t.tags "@work_import_special_characters_man_utf"

  t.tags "@import_da_title_link"
  t.tags "@import_da_gallery_link"
  t.tags "@import_da_fic"

  t.tags "@import_dw"
  t.tags "@import_dw_tables"
  t.tags "@import_dw_tables_no_backdate"
  t.tags "@import_dw_comm"
  t.tags "@import_dw_multi_chapter"

  t.tags "@import_ffn"
  t.tags "@import_ffn_multi_chapter"

  t.tags "@import_lj"
  t.tags "@import_lj_tables"
  t.tags "@import_lj_no_backdate"
  t.tags "@import_lj_comm"
  t.tags "@import_lj_multi_chapter"
  t.tags "@import_lj_underscores"
end
