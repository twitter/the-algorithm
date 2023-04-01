class Rating < Tag
  validates :canonical, presence: { message: "^Only canonical rating tags are allowed." }

  NAME = ArchiveConfig.RATING_CATEGORY_NAME

  def self.label_name
    to_s
  end

  # Gives us the default ratings as Not Rated + low to high
  def self.defaults_by_severity
    ratings = [ArchiveConfig.RATING_DEFAULT_TAG_NAME,
               ArchiveConfig.RATING_GENERAL_TAG_NAME,
               ArchiveConfig.RATING_TEEN_TAG_NAME,
               ArchiveConfig.RATING_MATURE_TAG_NAME,
               ArchiveConfig.RATING_EXPLICIT_TAG_NAME]
    ratings_by_id = Rating.where(name: ratings).inject({}) do |result, rating|
      result[rating.name] = rating
      result
    end
    ratings.map { |id| ratings_by_id[id] }
  end

end
