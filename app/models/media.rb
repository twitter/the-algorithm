class Media < Tag
  NAME = ArchiveConfig.MEDIA_CATEGORY_NAME

  has_many :common_taggings, as: :filterable
  has_many :fandoms, -> { where(type: 'Fandom') }, through: :common_taggings, source: :common_tag

  def child_types
    ['Fandom']
  end

  # The media tag for unwrangled fandoms
  def self.uncategorized
    tag = self.find_or_create_by_name(ArchiveConfig.MEDIA_UNCATEGORIZED_NAME)
    tag.update(canonical: true) unless tag.canonical
    tag
  end
end
