# frozen_string_literal: true

require 'spec_helper'

describe Chapter do
  it "has a valid factory" do
    expect(build(:chapter)).to be_valid
  end

  it "is invalid without content" do
    expect(build(:chapter, content: nil)).to be_invalid
  end

  it "is posted by default when produced by a factory" do
    chapter = create(:chapter)
    expect(chapter.posted).to be_truthy
  end

  describe "save" do
    before(:each) do
      @work = FactoryBot.create(:work)
      @chapter = Chapter.new(work: @work, content: "Cool story, bro!")
    end

    it "should save minimalistic chapter" do
      expect(@chapter.save).to be_truthy
    end
  end

  describe "co-creator permissions" do
    let(:creator) { create(:user) }
    let(:co_creator) { create(:user) }
    let(:no_co_creator) { create(:user) }

    before do
      # In order to enable co-creator checks (instead of just having everything
      # be automatically approved), we need to make sure that User.current_user
      # is not nil.
      User.current_user = creator
      co_creator.preference.update(allow_cocreator: true)
      no_co_creator.preference.update(allow_cocreator: false)
    end

    it "allows normal users to invite others as chapter co-creators" do
      work = create(:work, authors: creator.pseuds)
      attributes = {
        content: "new chapter content",
        author_attributes: {
          ids: creator.pseud_ids,
          byline: co_creator.login
        }
      }
      chapter = work.chapters.build(attributes)
      expect(chapter).to be_valid
      expect(chapter.save).to be_truthy
      expect(chapter.user_has_creator_invite?(co_creator)).to be_truthy
    end

    it "doesn't allow users to invite others who disallow co-creators" do
      work = create(:work, authors: creator.pseuds)
      attributes = {
        content: "new chapter content",
        author_attributes: {
          ids: creator.pseud_ids,
          byline: no_co_creator.login
        }
      }
      chapter = work.chapters.build(attributes)
      expect(chapter).to be_invalid
      expect(chapter.save).to be_falsey
      expect(chapter.user_has_creator_invite?(no_co_creator)).to be_falsey
    end

    it "allows users to automatically add work co-creators as chapter co-creators" do
      # Set up a work co-created with a user that doesn't allow co-creators:
      no_co_creator.preference.update(allow_cocreator: true)
      work = create(:work, authors: creator.pseuds + no_co_creator.pseuds)
      work.creatorships.for_user(no_co_creator).each(&:accept!)
      no_co_creator.preference.update(allow_cocreator: false)

      attributes = {
        content: "new chapter content",
        author_attributes: {
          ids: creator.pseud_ids,
          coauthors: no_co_creator.pseud_ids
        }
      }
      chapter = work.reload.chapters.build(attributes)
      expect(chapter).to be_valid
      expect(chapter.save).to be_truthy
      expect(chapter.pseuds.reload).to include(*no_co_creator.pseuds)
    end

    it "doesn't allow users to automatically add invited work co-creators" do
      # Set up a work with an invitation for a user that doesn't allow co-creators:
      no_co_creator.preference.update(allow_cocreator: true)
      work = create(:work, authors: creator.pseuds + no_co_creator.pseuds)
      no_co_creator.preference.update(allow_cocreator: false)

      attributes = {
        content: "new chapter content",
        author_attributes: {
          ids: creator.pseud_ids,
          coauthors: no_co_creator.pseud_ids
        }
      }
      chapter = work.reload.chapters.build(attributes)
      expect(chapter).to be_invalid
      expect(chapter.save).to be_falsey
      expect(chapter.creatorships.for_user(no_co_creator)).to be_empty
    end
  end
end
