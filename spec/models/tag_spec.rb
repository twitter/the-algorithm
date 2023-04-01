# encoding: UTF-8
require 'spec_helper'

describe Tag do
  after(:each) do
    User.current_user = nil
  end

  context 'checking count caching' do
    before(:each) do
      # Set the minimal amount of time a tag can be cached for.
      ArchiveConfig.TAGGINGS_COUNT_MIN_TIME = 1
      # Set so that we need few uses of a tag to start caching it.
      ArchiveConfig.TAGGINGS_COUNT_CACHE_DIVISOR = 2
      # Set the minimum number of uses needed for before caching is started.
      ArchiveConfig.TAGGINGS_COUNT_MIN_CACHE_COUNT = 3
      @fandom_tag = FactoryBot.create(:fandom)
    end

    context 'without updating taggings_count_cache' do
      it 'should not cache tags which are not used much' do
        FactoryBot.create(:work, fandom_string: @fandom_tag.name)
        @fandom_tag.reload
        expect(@fandom_tag.taggings_count_cache).to eq 0
        expect(@fandom_tag.taggings_count).to eq 1
      end

      it 'will start caching a when tag when that tag is used significantly' do
        (1..ArchiveConfig.TAGGINGS_COUNT_MIN_CACHE_COUNT).each do |try|
          FactoryBot.create(:work, fandom_string: @fandom_tag.name)
          @fandom_tag.reload
          expect(@fandom_tag.taggings_count_cache).to eq 0
          expect(@fandom_tag.taggings_count).to eq try
        end
        FactoryBot.create(:work, fandom_string: @fandom_tag.name)
        @fandom_tag.reload
        # This value should be cached and wrong
        expect(@fandom_tag.taggings_count_cache).to eq 0
        expect(@fandom_tag.taggings_count).to eq ArchiveConfig.TAGGINGS_COUNT_MIN_CACHE_COUNT
      end
    end

    context 'updating taggings_count_cache' do
      it 'should not cache tags which are not used much' do
        FactoryBot.create(:work, fandom_string: @fandom_tag.name)
        RedisSetJobSpawner.perform_now("TagCountUpdateJob")
        @fandom_tag.reload
        expect(@fandom_tag.taggings_count_cache).to eq 1
        expect(@fandom_tag.taggings_count).to eq 1
      end

      it 'will start caching a when tag when that tag is used significantly' do
        (1..ArchiveConfig.TAGGINGS_COUNT_MIN_CACHE_COUNT).each do |try|
          FactoryBot.create(:work, fandom_string: @fandom_tag.name)
          RedisSetJobSpawner.perform_now("TagCountUpdateJob")
          @fandom_tag.reload
          expect(@fandom_tag.taggings_count_cache).to eq try
          expect(@fandom_tag.taggings_count).to eq try
        end
        FactoryBot.create(:work, fandom_string: @fandom_tag.name)
        RedisSetJobSpawner.perform_now("TagCountUpdateJob")
        @fandom_tag.reload
        # This value should be cached and wrong
        expect(@fandom_tag.taggings_count_cache).to eq ArchiveConfig.TAGGINGS_COUNT_MIN_CACHE_COUNT
        expect(@fandom_tag.taggings_count).to eq ArchiveConfig.TAGGINGS_COUNT_MIN_CACHE_COUNT
      end

      it "Writes to the database do not happen immeadiately" do
        (1..40 * ArchiveConfig.TAGGINGS_COUNT_CACHE_DIVISOR - 1).each do |try|
          @fandom_tag.taggings_count = try
          @fandom_tag.reload
          expect(@fandom_tag.taggings_count_cache).to eq 0
        end
        @fandom_tag.taggings_count = 40 * ArchiveConfig.TAGGINGS_COUNT_CACHE_DIVISOR
        RedisSetJobSpawner.perform_now("TagCountUpdateJob")
        @fandom_tag.reload
        expect(@fandom_tag.taggings_count_cache).to eq 40 * ArchiveConfig.TAGGINGS_COUNT_CACHE_DIVISOR
      end
    end
  end

  it "should not be valid without a name" do
    tag = Tag.new
    expect(tag.save).not_to be_truthy

    tag.name = "something or other"
    expect(tag.save).to be_truthy
  end

  it "should not be valid if too long" do
    tag = Tag.new
    tag.name = "a" * 101
    expect(tag.save).not_to be_truthy
    expect(tag.errors[:name].join).to match(/too long/)
  end

  it "should not be valid with disallowed characters" do
    tag = Tag.new
    tag.name = "bad<tag"
    expect(tag.save).to be_falsey
    expect(tag.errors[:name].join).to match(/restricted characters/)
  end

  context "unwrangleable" do
    it "is not valid for a canonical tag" do
      tag = Freeform.create(name: "wrangled", canonical: true)
      tag.unwrangleable = true
      expect(tag).not_to be_valid
    end

    it "is not valid for an unsorted tag" do
      tag = FactoryBot.create(:unsorted_tag)
      tag.unwrangleable = true
      expect(tag).not_to be_valid
    end
  end

  context "when checking for synonym/name change" do
    context "when logged in as a regular user" do
      before(:each) do
        User.current_user = FactoryBot.create(:user)
      end

      it "should ignore capitalisation" do
        tag = Tag.new
        tag.name = "yuletide"
        tag.save

        tag.name = "Yuletide"
        tag.check_synonym
        expect(tag.errors).to be_empty
        expect(tag.save).to be_truthy
      end

      it "ignores accented characters" do
        tag = Tag.new
        tag.name = "Amelie"
        tag.save

        tag.name = "Amélie"
        tag.check_synonym
        expect(tag.errors).to be_empty
        expect(tag.save).to be_truthy
      end

      it "ignores the capitalization of ß" do
        tag = Tag.new
        tag.name = "Weiß Kreuz"
        tag.save

        tag.name = "WeiSS Kreuz"
        tag.check_synonym
        expect(tag.errors).to be_empty
        expect(tag.save).to be_truthy
      end

      it "should not ignore punctuation" do
        tag = Tag.new
        tag.name = "Snatch."
        tag.save

        tag.name = "Snatch"
        tag.check_synonym
        expect(tag.errors).not_to be_empty
        expect(tag.save).to be_falsey
      end

      it "should not ignore whitespace" do
        tag = Tag.new
        tag.name = "JohnSheppard"
        tag.save

        tag.name = "John Sheppard"
        tag.check_synonym
        expect(tag.errors).not_to be_empty
        expect(tag.save).to be_falsey
      end

      it 'autocomplete should work' do
        tag_character = FactoryBot.create(:character, canonical: true, name: 'kirk')
        tag_fandom = FactoryBot.create(:fandom, name: 'Star Trek', canonical: true)
        tag_fandom.add_to_autocomplete
        results = Tag.autocomplete_fandom_lookup(term: 'ki', fandom: 'Star Trek')
        expect(results.include?("#{tag_character.id}: #{tag_character.name}")).to be_truthy
        expect(results.include?("brave_sire_robin")).to be_falsey
      end

      it 'old tag maker still works' do
        tag_adult = Rating.create_canonical('adult', true)
        tag_normal = ArchiveWarning.create_canonical('other')
        expect(tag_adult.name).to eq('adult')
        expect(tag_normal.name).to eq('other')
        expect(tag_adult.adult).to be_truthy
        expect(tag_normal.adult).to be_falsey
      end
    end

    context "when logged in as an admin" do
      before do
        User.current_user = FactoryBot.create(:admin)
      end

      it "should allow any change" do
        tag = Tag.new
        tag.name = "yuletide.ssé"
        tag.save

        tag.name = "Yuletide ße something"
        tag.check_synonym
        expect(tag.errors).to be_empty
        expect(tag.save).to be_truthy
      end
    end
  end

  describe "unfilterable?" do
    it "is false for a canonical" do
      tag = Freeform.create(name: "canonical", canonical: true)
      expect(tag.unfilterable?).to be_falsey
    end

    it "is false for an unwrangleable" do
      tag = Tag.create(name: "unwrangleable", unwrangleable: true)
      expect(tag.unfilterable?).to be_falsey
    end

    it "is false for a synonym" do
      tag = Tag.create(name: "synonym")
      tag_merger = Tag.create(name: "merger")
      tag.merger = tag_merger
      tag.save
      expect(tag.unfilterable?).to be_falsey
    end

    it "is false for a merger tag" do
      tag = Tag.create(name: "merger")
      tag_syn = Tag.create(name: "synonym")
      tag_syn.merger = tag
      tag_syn.save
      expect(tag.unfilterable?).to be_falsey
    end

    it "is true for a tag with a Fandom parent" do
      tag_character = FactoryBot.create(:character, canonical: false)
      tag_fandom = FactoryBot.create(:fandom, canonical: true)
      tag_character.parents = [tag_fandom]
      tag_character.save

      expect(tag_character.unfilterable?).to be_truthy
    end
  end

  describe "has_posted_works?" do
    before do
      create(:work, fandom_string: "love live,jjba")
      create(:draft, fandom_string: "zombie land saga,jjba")
    end

    it "is true if used in posted works" do
      expect(Tag.find_by(name: "zombie land saga").has_posted_works?).to be_falsey
      expect(Tag.find_by(name: "love live").has_posted_works?).to be_truthy
      expect(Tag.find_by(name: "jjba").has_posted_works?).to be_truthy
    end
  end

  describe "can_change_type?" do
    it "should be false for a wrangled tag" do
      tag = Freeform.create(name: "wrangled", canonical: true)
      expect(tag.can_change_type?).to be_falsey
    end

    it "should be false for a tag used on a draft" do
      tag = Fandom.create(name: "Fandom")
      expect(tag.can_change_type?).to be_truthy

      FactoryBot.create(:work, fandom_string: tag.name)
      expect(tag.can_change_type?).to be_falsey
    end

    it "should be false for a tag used on a work" do
      tag = Fandom.create(name: "Fandom")
      expect(tag.can_change_type?).to be_truthy

      work = FactoryBot.create(:work, fandom_string: tag.name)
      work.posted = true
      work.save
      expect(tag.can_change_type?).to be_falsey
    end

    it "should be false for a tag used in a tag set"

    it "should be true for a tag used on a bookmark" do
      tag = FactoryBot.create(:unsorted_tag)
      expect(tag.can_change_type?).to be_truthy

      bookmark = FactoryBot.create(:bookmark, tag_string: tag.name)

      expect(bookmark.tags).to include(tag)
      expect(tag.can_change_type?).to be_truthy
    end

    it "should be true for a tag used on an external work" do
      FactoryBot.create(:external_work, character_string: "Jane Smith")
      tag = Tag.find_by_name("Jane Smith")

      expect(tag.can_change_type?).to be_truthy
    end
  end

  describe "type changes" do
    context "from Unsorted to Fandom" do
      before do
        @fandom_tag = FactoryBot.create(:unsorted_tag)
        @fandom_tag.type = "Fandom"
        @fandom_tag.save
        @fandom_tag = Tag.find(@fandom_tag.id)
      end

      it "should be a Fandom" do
        expect(@fandom_tag).to be_a(Fandom)
      end

      it "should have the Uncategorized Fandoms Media as a parent" do
        expect(@fandom_tag.parents).to eq([Media.uncategorized])
      end
    end

    context "from Unsorted to Character" do
      before do
        @character_tag = FactoryBot.create(:unsorted_tag)
        @character_tag.type = "Character"
        @character_tag.save
        @character_tag = Tag.find(@character_tag.id)
      end

      it "should be a Character" do
        expect(@character_tag).to be_a(Character)
      end

      it "should not have any parents" do
        expect(@character_tag.parents).to be_empty
      end
    end

    context "from Fandom to Unsorted" do
      before do
        @unsorted_tag = FactoryBot.create(:fandom, canonical: false)
        @unsorted_tag.type = "UnsortedTag"
        @unsorted_tag.save
        @unsorted_tag = Tag.find(@unsorted_tag.id)
      end

      it "should be an UnsortedTag" do
        expect(@unsorted_tag).to be_a(UnsortedTag)
      end

      it "should not have any parents" do
        expect(@unsorted_tag.parents).not_to eq([Media.uncategorized])
        expect(@unsorted_tag.parents).to be_empty
      end
    end

    context "from Fandom to Character" do
      before do
        @character_tag = FactoryBot.create(:fandom, canonical: false)
        @character_tag.type = "Character"
        @character_tag.save
        @character_tag = Tag.find(@character_tag.id)
      end

      it "should be a Character" do
        expect(@character_tag).to be_a(Character)
      end

      it "should not have any parents" do
        expect(@character_tag.parents).not_to eq([Media.uncategorized])
        expect(@character_tag.parents).to be_empty
      end
    end

    context "from Character to Unsorted" do
      before do
        @unsorted_tag = FactoryBot.create(:character, canonical: false)
        @unsorted_tag.type = "UnsortedTag"
        @unsorted_tag.save
        @unsorted_tag = Tag.find(@unsorted_tag.id)
      end

      it "should be an UnsortedTag" do
        expect(@unsorted_tag).to be_a(UnsortedTag)
      end

      it "should not have any parents" do
        expect(@unsorted_tag.parents).to be_empty
      end
    end

    context "from Character to Fandom" do
      before do
        @fandom_tag = FactoryBot.create(:character, canonical: false)
        @fandom_tag.type = "Fandom"
        @fandom_tag.save
        @fandom_tag = Tag.find(@fandom_tag.id)
      end

      it "should be a Fandom" do
        expect(@fandom_tag).to be_a(Fandom)
      end

      it "should have the Uncategorized Fandoms Media as a parent" do
        expect(@fandom_tag.parents).to eq([Media.uncategorized])
      end
    end

    context "when the Character had a Fandom attached" do
      before do
        @unsorted_tag = FactoryBot.create(:character, canonical: false)
        fandom_tag = FactoryBot.create(:fandom, canonical: true)
        @unsorted_tag.parents = [fandom_tag]
        @unsorted_tag.save
      end

      it "should drop the Fandom when changed to UnsortedTag" do
        @unsorted_tag.type = "UnsortedTag"
        @unsorted_tag.save
        @unsorted_tag = Tag.find(@unsorted_tag.id)

        expect(@unsorted_tag).to be_a(UnsortedTag)
        expect(@unsorted_tag.parents).to be_empty
      end

      it "should drop the Fandom and add to Uncategorized when changed to Fandom" do
        @unsorted_tag.type = "Fandom"
        @unsorted_tag.save
        @unsorted_tag = Tag.find(@unsorted_tag.id)

        expect(@unsorted_tag).to be_a(Fandom)
        expect(@unsorted_tag.parents).to eq([Media.uncategorized])
      end
    end
  end

  describe "find_or_create_by_name" do
    it "should sort unsorted tags that get used on works" do
      tag = FactoryBot.create(:unsorted_tag)
      FactoryBot.create(:work, character_string: tag.name)

      tag = Tag.find(tag.id)
      expect(tag).to be_a(Character)
    end

    it "should sort unsorted tags that get used on external works" do
      tag = FactoryBot.create(:unsorted_tag)
      FactoryBot.create(:external_work, character_string: tag.name)

      tag = Tag.find(tag.id)
      expect(tag).to be_a(Character)
    end
  end

  describe "multiple tags of the same type" do
    before do
      # set up four tags of the same type
      @canonical_tag = create(:canonical_fandom)
      @syn_tag = create(:fandom)
      @sub_tag = create(:canonical_fandom)
      @canonical_syn_tag = create(:canonical_fandom)
    end

    context "when logged in as admin" do
      it "lets you make a canonical tag the synonym of a canonical one" do
        User.current_user = create(:admin)
        @canonical_syn_tag.syn_string = @canonical_tag.name
        @canonical_syn_tag.save

        expect(@canonical_syn_tag.merger).to eq(@canonical_tag)
        @canonical_tag = Tag.find(@canonical_tag.id)
        expect(@canonical_tag.mergers).to eq([@canonical_syn_tag])
      end
    end

    it "lets you make a noncanonical tag the synonym of a canonical one" do
      @noncanonical_syn_tag = create(:fandom)
      @noncanonical_syn_tag.syn_string = @canonical_tag.name
      @noncanonical_syn_tag.save

      expect(@noncanonical_syn_tag.merger).to eq(@canonical_tag)
      @canonical_tag = Tag.find(@canonical_tag.id)
      expect(@canonical_tag.mergers).to eq([@noncanonical_syn_tag])
    end

    it "doesn't let you make a canonical tag the synonym of a canonical one" do
      @canonical_syn_tag.syn_string = @canonical_tag.name
      @canonical_syn_tag.save

      expect(@canonical_syn_tag.merger).to eq(nil)
      @canonical_tag = Tag.find(@canonical_tag.id)
      expect(@canonical_tag.mergers).to eq([])
    end

    it "should let you make a canonical tag the subtag of another canonical one" do
      @sub_tag.meta_tag_string = @canonical_tag.name

      expect(@canonical_tag.sub_tags).to eq([@sub_tag])
      expect(@sub_tag.meta_tags).to eq([@canonical_tag])
    end
  end
end
