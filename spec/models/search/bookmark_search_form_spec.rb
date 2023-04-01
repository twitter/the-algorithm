# frozen_string_literal: true

require "spec_helper"

describe BookmarkSearchForm, bookmark_search: true do
  describe "options" do
    it "includes flags set to false" do
      bsf = BookmarkSearchForm.new(show_restricted: false, show_private: false)
      expect(bsf.options).to include(show_restricted: false)
      expect(bsf.options).to include(show_private: false)
    end
  end

  describe "bookmarkable_search_results" do
    describe "sorting" do
      context "when everything is created at a different time" do
        let(:tag) { create(:canonical_fandom) }

        let!(:work1) do
          Delorean.time_travel_to 40.minutes.ago do
            create(:work, title: "One", fandom_string: tag.name)
          end
        end

        let!(:work2) do
          Delorean.time_travel_to 60.minutes.ago do
            create(:work, title: "Two", fandom_string: tag.name)
          end
        end

        let!(:work3) do
          Delorean.time_travel_to 50.minutes.ago do
            create(:work, title: "Three", fandom_string: tag.name)
          end
        end

        let!(:bookmark1) do
          Delorean.time_travel_to 30.minutes.ago do
            create(:bookmark, bookmarkable: work1)
          end
        end

        let!(:bookmark2) do
          Delorean.time_travel_to 10.minutes.ago do
            create(:bookmark, bookmarkable: work2)
          end
        end

        let!(:bookmark3) do
          Delorean.time_travel_to 20.minutes.ago do
            create(:bookmark, bookmarkable: work3)
          end
        end

        before { run_all_indexing_jobs }

        context "by Date Updated" do
          it "returns bookmarkables in the correct order" do
            results = BookmarkSearchForm.new(
              parent: tag, sort_column: "bookmarkable_date"
            ).bookmarkable_search_results
            expect(results.map(&:title)).to eq ["One", "Three", "Two"]
          end

          it "changes when the work is updated" do
            work2.update_attribute(:revised_at, Time.current)
            run_all_indexing_jobs
            results = BookmarkSearchForm.new(
              parent: tag, sort_column: "bookmarkable_date"
            ).bookmarkable_search_results
            expect(results.map(&:title)).to eq ["Two", "One", "Three"]
          end
        end

        context "by Date Bookmarked" do
          it "returns bookmarkables in the correct order" do
            results = BookmarkSearchForm.new(
              parent: tag, sort_column: "created_at"
            ).bookmarkable_search_results
            expect(results.map(&:title)).to eq ["Two", "Three", "One"]
          end

          it "changes when a new bookmark is created" do
            create(:bookmark, bookmarkable: work1)
            run_all_indexing_jobs
            results = BookmarkSearchForm.new(
              parent: tag, sort_column: "created_at"
            ).bookmarkable_search_results
            expect(results.map(&:title)).to eq ["One", "Two", "Three"]
          end
        end
      end

      context "when everything is created and updated at the same time" do
        before { freeze_time }

        let(:tag) { create(:canonical_fandom) }
        let!(:work1) { create(:work, fandom_string: tag.name) }
        let!(:work2) { create(:work, fandom_string: tag.name) }
        let!(:bookmark1) { create(:bookmark, bookmarkable: work1) }
        let!(:bookmark2) { create(:bookmark, bookmarkable: work2) }

        context "doesn't change tied bookmarkables order on work update" do
          it "when sorted by Date Updated" do
            search = BookmarkSearchForm.new(
              parent: tag, sort_column: "bookmarkable_date"
            )
            run_all_indexing_jobs
            res = search.bookmarkable_search_results.map(&:id)

            [work1, work2].each do |work|
              work.update(summary: "Updated")
              run_all_indexing_jobs
              expect(search.bookmarkable_search_results.map(&:id)).to eq(res)
            end
          end

          it "when sorted by Date Bookmarked" do
            run_all_indexing_jobs
            search = BookmarkSearchForm.new(
              parent: tag, sort_column: "created_at"
            )
            run_all_indexing_jobs
            res = search.bookmarkable_search_results.map(&:id)

            [work1, work2].each do |work|
              work.update(summary: "Updated")
              run_all_indexing_jobs
              expect(search.bookmarkable_search_results.map(&:id)).to eq(res)
            end
          end
        end
      end
    end

    describe "searching" do
      context "by work language" do
        let(:language) { create(:language, short: "ptBR") }

        let(:work1) { create(:work) }
        let(:work2) { create(:work, language_id: language.id) }

        let!(:bookmark1) { create(:bookmark, bookmarkable: work1) }
        let!(:bookmark2) { create(:bookmark, bookmarkable: work2) }

        let(:unused_language) { create(:language, short: "tlh") }

        before { run_all_indexing_jobs }

        it "returns work bookmarkables with specified language" do
          # "Work language" dropdown, with short names
          results = BookmarkSearchForm.new(language_id: "ptBR").bookmarkable_search_results
          expect(results).not_to include work1
          expect(results).to include work2

          # "Work language" dropdown, with IDs (backward compatibility)
          bsf = BookmarkSearchForm.new(language_id: language.id)
          expect(bsf.language_id).to eq("ptBR")
          results = bsf.bookmarkable_search_results
          expect(results).not_to include work1
          expect(results).to include work2

          # "Any field on work" or "Search within results", with short names
          results = BookmarkSearchForm.new(bookmarkable_query: "language_id: ptBR").bookmarkable_search_results
          expect(results).not_to include work1
          expect(results).to include work2

          # "Any field on work" or "Search within results", with IDs (backward compatibility)
          bsf = BookmarkSearchForm.new(bookmarkable_query: "language_id: #{language.id} OR language_id: #{unused_language.id}")
          expect(bsf.bookmarkable_query).to eq("language_id: ptBR OR language_id: tlh")
          results = bsf.bookmarkable_search_results
          expect(results).not_to include work1
          expect(results).to include work2
        end
      end

      context "using pseud_ids in the bookmarkable query" do
        let(:pseud) { create(:pseud) }
        let(:collection) { create(:collection) }

        let(:work) { create(:work, authors: [pseud], collections: [collection]) }
        let(:series) { create(:series, authors: [pseud], works: [work]) }

        let!(:bookmark1) { create(:bookmark, bookmarkable: work) }
        let!(:bookmark2) { create(:bookmark, bookmarkable: series) }

        before { run_all_indexing_jobs }

        context "when a work & series are anonymous" do
          let(:collection) { create(:anonymous_collection) }

          it "doesn't include the work or the series" do
            results = BookmarkSearchForm.new(bookmarkable_query: "pseud_ids: #{pseud.id}").bookmarkable_search_results
            expect(results).not_to include work
            expect(results).not_to include series
          end
        end

        context "when a work & series are unrevealed" do
          let(:collection) { create(:unrevealed_collection) }

          it "doesn't include the work or the series" do
            results = BookmarkSearchForm.new(bookmarkable_query: "pseud_ids: #{pseud.id}").bookmarkable_search_results
            expect(results).not_to include work
            expect(results).not_to include series
          end
        end

        context "when a work & series are neither unrevealed nor anonymous" do
          it "includes the work and the series" do
            results = BookmarkSearchForm.new(bookmarkable_query: "pseud_ids: #{pseud.id}").bookmarkable_search_results
            expect(results).to include work
            expect(results).to include series
          end
        end
      end

      context "using user_ids in the bookmarkable query" do
        let(:user) { create(:user) }
        let(:collection) { create(:collection) }

        let(:work) { create(:work, authors: [user.default_pseud], collections: [collection]) }
        let(:series) { create(:series, authors: [user.default_pseud], works: [work]) }

        let!(:bookmark1) { create(:bookmark, bookmarkable: work) }
        let!(:bookmark2) { create(:bookmark, bookmarkable: series) }

        before { run_all_indexing_jobs }

        context "when a work & series are anonymous" do
          let(:collection) { create(:anonymous_collection) }

          it "doesn't include the work or the series" do
            results = BookmarkSearchForm.new(bookmarkable_query: "user_ids: #{user.id}").bookmarkable_search_results
            expect(results).not_to include work
            expect(results).not_to include series
          end
        end

        context "when a work & series are unrevealed" do
          let(:collection) { create(:unrevealed_collection) }

          it "doesn't include the work or the series" do
            results = BookmarkSearchForm.new(bookmarkable_query: "user_ids: #{user.id}").bookmarkable_search_results
            expect(results).not_to include work
            expect(results).not_to include series
          end
        end

        context "when a work & series are neither unrevealed nor anonymous" do
          it "includes the work and the series" do
            results = BookmarkSearchForm.new(bookmarkable_query: "user_ids: #{user.id}").bookmarkable_search_results
            expect(results).to include work
            expect(results).to include series
          end
        end
      end
    end
  end

  describe "search_results" do
    describe "sorting" do
      before { freeze_time }

      let!(:work1) { create(:work) }
      let!(:work2) { create(:work) }
      let(:bookmarker) { create(:user) }
      let!(:bookmark1) { create(:bookmark, bookmarkable: work1, pseud: bookmarker.default_pseud) }
      let!(:bookmark2) { create(:bookmark, bookmarkable: work2, pseud: bookmarker.default_pseud) }

      context "doesn't change tied bookmark order on work/bookmark update" do
        %w[created_at bookmarkable_date].each do |sort_column|
          it "when sorted by #{sort_column}" do
            search = BookmarkSearchForm.new(
              bookmarker: bookmarker.default_pseud.name, sort_column: sort_column
            )
            run_all_indexing_jobs
            res = search.search_results.map(&:id)

            [work1, work2].each do |work|
              work.update(summary: "Updated")
              run_all_indexing_jobs
              expect(search.search_results.map(&:id)).to eq(res)
            end

            [bookmark1, bookmark2].each do |bookmark|
              bookmark.update(bookmarker_notes: "Updated")
              run_all_indexing_jobs
              expect(search.search_results.map(&:id)).to eq(res)
            end
          end
        end
      end
    end
  end

  describe "when searching by bookmarker" do
    let(:bookmarker) { create(:user, login: "yabalchoath") }

    {
      Work: :work,
      Series: :series_with_a_work,
      ExternalWork: :external_work
    }.each_pair do |type, factory|
      it "returns the correct bookmarked #{type.to_s.pluralize} when bookmarker changes username" do
        bookmarkable = create(factory)
        bookmark = create(:bookmark,
                          bookmarkable_id: bookmarkable.id,
                          bookmarkable_type: type,
                          pseud: bookmarker.default_pseud)
        run_all_indexing_jobs

        result = BookmarkSearchForm.new(bookmarker: "yabalchoath").search_results.first
        expect(result).to eq bookmark

        bookmarker.login = "cioelle"
        bookmarker.save!
        run_all_indexing_jobs

        result = BookmarkSearchForm.new(bookmarker: "yabalchoath").search_results.first
        expect(result).to be_nil
        result = BookmarkSearchForm.new(bookmarker: "cioelle").search_results.first
        expect(result).to eq bookmark
      end
    end
  end

  describe "when searching any bookmarkable field for author of bookmarkable" do
    let(:author) { create(:user, login: "yabalchoath") }

    {
      Work: :work,
      Series: :series_with_a_work
    }.each_pair do |type, factory|
      it "returns the correct bookmarked #{type.to_s.pluralize} when author changes username" do
        bookmarkable = create(factory, authors: [author.default_pseud])
        bookmark = create(:bookmark, bookmarkable_id: bookmarkable.id, bookmarkable_type: type)
        run_all_indexing_jobs

        result = BookmarkSearchForm.new(bookmarkable_query: "yabalchoath").search_results.first
        expect(result).to eq bookmark

        author.login = "cioelle"
        author.save!
        run_all_indexing_jobs

        result = BookmarkSearchForm.new(bookmarkable_query: "yabalchoath").search_results.first
        expect(result).to be_nil
        result = BookmarkSearchForm.new(bookmarkable_query: "cioelle").search_results.first
        expect(result).to eq bookmark
      end
    end
  end

  describe "#processed_options" do
    it "removes blank options" do
      options = { foo: nil, bar: false }
      searcher = BookmarkSearchForm.new(options)
      expect(searcher.options).to have_key(:bar)
      expect(searcher.options).not_to have_key(:foo)
    end

    it "renames the notes field" do
      options = { bookmark_notes: "Mordor" }
      searcher = BookmarkSearchForm.new(options)
      expect(searcher.options[:notes]).to eq("Mordor")
    end

    it "unescapes angle brackets for date fields" do
      options = {
        date: "&lt;1 week ago",
        bookmarkable_date: "&gt;1 year ago",
        title: "escaped &gt;.&lt; field"
      }
      searcher = BookmarkSearchForm.new(options)
      expect(searcher.options[:date]).to eq("<1 week ago")
      expect(searcher.options[:bookmarkable_date]).to eq(">1 year ago")
      expect(searcher.options[:title]).to eq("escaped &gt;.&lt; field")
    end

    it "renames old warning_ids fields" do
      options = { warning_ids: [13] }
      searcher = BookmarkSearchForm.new(options)
      expect(searcher.options[:archive_warning_ids]).to eq([13])
    end
  end
end
