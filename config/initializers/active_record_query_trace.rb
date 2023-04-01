if Rails.env.development?
  ActiveRecordQueryTrace.enabled = true
  ActiveRecordQueryTrace.ignore_cached_queries = true
  ActiveRecordQueryTrace.colorize = :light_purple
end
