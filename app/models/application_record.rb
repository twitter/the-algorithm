class ApplicationRecord < ActiveRecord::Base
  self.abstract_class = true
  self.per_page = ArchiveConfig.ITEMS_PER_PAGE

  before_save :update_sanitizer_version

  def update_sanitizer_version
    ArchiveConfig.FIELDS_ALLOWING_HTML.each do |field|
      if self.will_save_change_to_attribute?(field)
        self.send("#{field}_sanitizer_version=", ArchiveConfig.SANITIZER_VERSION)
      end
    end
  end

  def self.random_order
    order(Arel.sql("RAND()"))
  end
end
