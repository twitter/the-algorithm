require 'spec_helper'

describe InboxHelper do

  describe "commentable_description_link" do
    context "for Deleted Objects" do
      it "should return the String 'Deleted Object'" do
        @commentable = FactoryBot.build(:comment, commentable_id: nil, commentable_type: nil)
        expect(commentable_description_link(@commentable)).to eq "Deleted Object"
      end
    end

    context "for Tags" do
      it "should return a link to the Comment on the Tag" do
        @commentable = FactoryBot.create(:comment, :on_tag)
        string = commentable_description_link(@commentable)
        expect(string.gsub("%20", " ")).to eq "<a href=\"/tags/#{@commentable.ultimate_parent.name}/comments/#{@commentable.id}\">#{@commentable.ultimate_parent.name}</a>"
      end
    end

    context "for AdminPosts" do
      it "should return a link to the Comment on the Adminpost" do
        @commentable = FactoryBot.create(:comment, :on_admin_post)
        expect(commentable_description_link(@commentable)).to eq "<a href=\"/admin_posts/#{@commentable.ultimate_parent.id}/comments/#{@commentable.id}\">#{@commentable.ultimate_parent.title}</a>"
      end
    end

    context "for Works" do
      it "should return a link to the Comment on the Work" do
        @commentable = FactoryBot.create(:comment)
        expect(commentable_description_link(@commentable)).to eq "<a href=\"/works/#{@commentable.ultimate_parent.id}/comments/#{@commentable.id}\">#{@commentable.ultimate_parent.title}</a>"
      end
    end
  end
end
