class BookmarkedWorkIndexer < BookmarkableIndexer
  def self.klass
    "Work"
  end

  def self.klass_with_includes
    Work.includes(
      :approved_collections,
      :direct_filters,
      :external_author_names,
      :filters,
      :language,
      :tags,
      :users,
      pseuds: :user
    )
  end
end
