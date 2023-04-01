require 'spec_helper'

describe UnsortedTag do

  it "should be created from a bookmark" do
    FactoryBot.create(:bookmark, tag_string: "bookmark tag")
    tag = Tag.find_by_name("bookmark tag")
    expect(tag).to be_a(UnsortedTag)
  end

  describe "recategorize" do
    %w(Fandom Character Relationship Freeform).each do |new_type|
      it "should return a tag of type #{new_type}" do
        tag = FactoryBot.create(:unsorted_tag)
        expect(tag.recategorize(new_type)).to be_a(new_type.constantize)
      end
    end
  end

end
