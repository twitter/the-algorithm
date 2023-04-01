require 'spec_helper'

describe FavoriteTag do
  let(:noncanonical_favorite) { build(:favorite_tag,
                                      tag_id: create(:freeform).id) }
  let(:no_tag_favorite) { build(:favorite_tag, tag_id: nil) }
  let(:no_user_favorite) { build(:favorite_tag, user_id: nil) }

  it "has a valid factory" do
    expect(create(:favorite_tag)).to be_valid
  end

  it "is invalid without a tag_id" do
    expect(no_tag_favorite.valid?).to be_falsey
  end

  it "is invalid when the user has already favorited the tag" do
    user = create(:user)
    tag = create(:canonical_freeform)
    create(:favorite_tag, tag_id: tag.id, user_id: user.id)
    expect(build(:favorite_tag, tag_id: tag.id, user_id: user.id).save).to be_falsey
  end

  it "is invalid without a user_id" do
    expect(no_user_favorite.valid?).to be_falsey
  end

  it "is invalid with a non-canonical tag" do
    expect(noncanonical_favorite.valid?).to be_falsey
    expect(noncanonical_favorite.errors[:base].first).to include("Sorry, you can only add canonical tags to your favorite tags.")
  end

  context "when user has maximum number of favorite tags" do
    let(:user) { create(:user) }
    let(:favorite_tag) { build(:favorite_tag, user_id: user.id) }

    before do
      ArchiveConfig.MAX_FAVORITE_TAGS.times do
        create(:favorite_tag, user_id: user.id)
      end
    end

    it "is invalid" do
      expect(FavoriteTag.count).to eq(ArchiveConfig.MAX_FAVORITE_TAGS)
      expect(favorite_tag.valid?).to be_falsey
      expect(favorite_tag.errors[:base].first).to include("Sorry, you can only save #{ArchiveConfig.MAX_FAVORITE_TAGS} favorite tags.")
    end
  end
end
