class CollectionMailer < ApplicationMailer
  helper :application
  helper :tags
  helper :works
  helper :series

  def item_added_notification(creation_id, collection_id, item_type)
    @item_type = item_type
    @item_type == "Work" ? @creation = Work.find(creation_id) : @creation = Bookmark.find(creation_id)
    @collection = Collection.find(collection_id)
    mail(
      to: @collection.email,
      subject: "[#{ArchiveConfig.APP_SHORT_NAME}] #{@item_type.capitalize} added to " + @collection.title.gsub("&gt;", ">").gsub("&lt;", "<")
    )
  end
end
