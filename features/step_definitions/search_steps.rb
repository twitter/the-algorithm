Given /^all indexing jobs have been run$/ do
  %w[main background stats].each do |reindex_type|
    ScheduledReindexJob.perform(reindex_type)
  end
  Indexer.all.map(&:refresh_index)
end

Given /^the max search result count is (\d+)$/ do |max|
  stub_const("ArchiveConfig", OpenStruct.new(ArchiveConfig))
  ArchiveConfig.MAX_SEARCH_RESULTS = max.to_i
end

Given /^(\d+) item(?:s)? (?:is|are) displayed per page$/ do |per_page|
  stub_const("ArchiveConfig", OpenStruct.new(ArchiveConfig))
  ArchiveConfig.ITEMS_PER_PAGE = per_page.to_i
end

Given /^(\d+) tag(?:s)? (?:is|are) displayed per search page$/ do |per_page|
  stub_const("ArchiveConfig", OpenStruct.new(ArchiveConfig))
  ArchiveConfig.TAGS_PER_SEARCH_PAGE = per_page.to_i
end

Given /^dashboard counts expire after (\d+) seconds?$/ do |seconds|
  stub_const("ArchiveConfig", OpenStruct.new(ArchiveConfig))
  ArchiveConfig.SECONDS_UNTIL_DASHBOARD_COUNTS_EXPIRE = seconds.to_i
end
