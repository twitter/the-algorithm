require 'spec_helper'

describe CollectionItem, :ready do
  it "can be created" do
    expect(create(:collection_item)).to be_valid
  end

  context "belonging to a bookmark" do
    it "can be revealed without erroring" do
      ci = CollectionItem.create(
        item_id: 1,
        item_type: 'Bookmark',
        collection_id: create(:collection).id,
        unrevealed: true)
      ci.unrevealed = false
      expect(ci.save).to be_truthy
    end
  end
end
