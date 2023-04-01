class Category < Tag
  validates :canonical, presence: { message: "^Only canonical category tags are allowed." }

  NAME = ArchiveConfig.CATEGORY_CATEGORY_NAME

end

