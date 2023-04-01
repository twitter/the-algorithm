require 'spec_helper'

describe WorkSearchForm, work_search: true do
  describe "#process_options" do
    it "removes blank options" do
      options = { foo: nil, bar: '', baz: false, boo: true }
      searcher = WorkSearchForm.new(options)
      expect(searcher.options.keys).to include(:boo)
      expect(searcher.options.keys).not_to include(:foo, :bar, :baz)
    end
  end

  describe "#standardize_creator_queries" do
    it "renames old creator option" do
      options = { query: "creator: alice" }
      searcher = WorkSearchForm.new(options)
      expect(searcher.options[:query]).to eq("creators: alice")
    end
  end

  describe "#clean_up_angle_brackets" do
    it "unescapes angle brackets for numeric and date fields" do
      options = {
        word_count: "&lt;2000",
        hits: "&gt; 100",
        kudos_count: "&gt;10",
        comments_count: "&lt; 100",
        bookmarks_count: "&gt;50",
        revised_at: "&lt;1 week ago",
        query: "a &gt; b &lt; c",
        title: "&lt;3"
      }
      searcher = WorkSearchForm.new(options)
      expect(searcher.options[:word_count]).to eq("<2000")
      expect(searcher.options[:hits]).to eq("> 100")
      expect(searcher.options[:kudos_count]).to eq(">10")
      expect(searcher.options[:comments_count]).to eq("< 100")
      expect(searcher.options[:bookmarks_count]).to eq(">50")
      expect(searcher.options[:revised_at]).to eq("<1 week ago")
      expect(searcher.options[:query]).to eq("a > b < c")
      expect(searcher.options[:title]).to eq("&lt;3")
    end
  end

  describe "#rename_warning_field" do
    it "renames a legacy field" do
      options = { warning_ids: [12] }
      searcher = WorkSearchForm.new(options)
      expect(searcher.options).not_to have_key(:warning_ids)
      expect(searcher.options[:archive_warning_ids]).to eq([12])
    end
  end

  describe "#set_sorting" do
    it "does not override provided sort column" do
      options = { sort_column: "authors_to_sort_on" }
      searcher = WorkSearchForm.new(options)
      expect(searcher.options[:sort_column]).to eq("authors_to_sort_on")
    end

    it "does not override provided sort direction" do
      options = { sort_direction: "asc" }
      searcher = WorkSearchForm.new(options)
      expect(searcher.options[:sort_direction]).to eq("asc")
    end

    it "sorts by relevance by default" do
      searcher = WorkSearchForm.new({})
      expect(searcher.options[:sort_column]).to eq("_score")
    end

    context "when filtering" do
      it "sorts by date" do
        options = { faceted: true }
        searcher = WorkSearchForm.new(options)
        expect(searcher.options[:sort_column]).to eq("revised_at")
      end
    end

    context "when viewing collected works" do
      it "sorts by date" do
        options = { collected: true }
        searcher = WorkSearchForm.new(options)
        expect(searcher.options[:sort_column]).to eq("revised_at")
      end
    end

    context "when sorting by author" do
      it "sets the sort direction to ascending" do
        options = { sort_column: "authors_to_sort_on" }
        searcher = WorkSearchForm.new(options)
        expect(searcher.options[:sort_direction]).to eq("asc")
      end
    end

    context "when sorting by title" do
      it "sets the sort direction to ascending" do
        options = { sort_column: "title_to_sort_on" }
        searcher = WorkSearchForm.new(options)
        expect(searcher.options[:sort_direction]).to eq("asc")
      end
    end

    context "when sorting by other fields" do
      it "sets the sort direction to descending" do
        options = { sort_column: "word_count" }
        searcher = WorkSearchForm.new(options)
        expect(searcher.options[:sort_direction]).to eq("desc")
      end
    end
  end

  describe "searching" do
    let!(:collection) do
      FactoryBot.create(:collection, id: 1)
    end

    let(:language) { create(:language, short: "ptPT") }

    let!(:work) do
      FactoryBot.create(:work,
                         title: "There and back again",
                         authors: [Pseud.find_by(name: "JRR Tolkien") || FactoryBot.create(:pseud, name: "JRR Tolkien")],
                         summary: "An unexpected journey",
                         fandom_string: "The Hobbit",
                         character_string: "Bilbo Baggins",
                         expected_number_of_chapters: 3,
                         complete: false)
    end

    let!(:second_work) do
      FactoryBot.create(:work,
                         title: "Harry Potter and the Sorcerer's Stone",
                         authors: [Pseud.find_by(name: "JK Rowling") || FactoryBot.create(:pseud, name: "JK Rowling")],
                         summary: "Mr and Mrs Dursley, of number four Privet Drive...",
                         fandom_string: "Harry Potter",
                         character_string: "Harry Potter, Ron Weasley, Hermione Granger",
                         language_id: language.id)
    end

    before(:each) do
      # This doesn't work properly in the factory.
      second_work.collection_ids = [collection.id]
      second_work.save

      work.stat_counter.update(kudos_count: 1200, comments_count: 120, bookmarks_count: 12)
      second_work.stat_counter.update(kudos_count: 999, comments_count: 99, bookmarks_count: 9)
      run_all_indexing_jobs
    end

    it "finds works that match" do
      results = WorkSearchForm.new(query: "Hobbit").search_results
      expect(results).to include work
      expect(results).not_to include second_work
    end

    it "finds works with tags having numbers" do
      work.freeform_string = "Episode: s01e01,Season/Series 01,Brooklyn 99"
      work.save

      second_work.freeform_string = "Episode: s02e01,Season/Series 99"
      second_work.save

      run_all_indexing_jobs

      # The colon is a reserved character we cannot automatically escape
      # without breaking all the hidden search operators.
      # We just have to quote it.
      results = WorkSearchForm.new(query: "\"Episode: s01e01\"").search_results
      expect(results).to include work
      expect(results).not_to include second_work

      # Quote the search term since it has a space.
      results = WorkSearchForm.new(query: "\"Season/Series 99\"").search_results
      expect(results).not_to include work
      expect(results).to include second_work
    end

    describe "when searching using user_ids in the query" do
      let(:user_id) { second_work.user_ids.first }

      context "when the work is in an anonymous collection" do
        let(:collection) { create(:anonymous_collection) }

        it "doesn't include the work" do
          work_search = WorkSearchForm.new(query: "user_ids: #{user_id}")
          expect(work_search.search_results).not_to include second_work
        end
      end

      context "when the work is in an unrevealed collection" do
        let(:collection) { create(:unrevealed_collection) }

        it "doesn't include the work" do
          work_search = WorkSearchForm.new(query: "user_ids: #{user_id}")
          expect(work_search.search_results).not_to include second_work
        end
      end

      context "when the work is neither anonymous or unrevealed" do
        it "includes the work" do
          work_search = WorkSearchForm.new(query: "user_ids: #{user_id}")
          expect(work_search.search_results).to include second_work
        end
      end
    end

    describe "when searching using pseud_ids in the query" do
      let(:pseud_id) { second_work.pseud_ids.first }

      context "when the work is in an anonymous collection" do
        let(:collection) { create(:anonymous_collection) }

        it "doesn't include the work" do
          work_search = WorkSearchForm.new(query: "pseud_ids: #{pseud_id}")
          expect(work_search.search_results).not_to include second_work
        end
      end

      context "when the work is in an unrevealed collection" do
        let(:collection) { create(:unrevealed_collection) }

        it "doesn't include the work" do
          work_search = WorkSearchForm.new(query: "pseud_ids: #{pseud_id}")
          expect(work_search.search_results).not_to include second_work
        end
      end

      context "when the work is neither anonymous or unrevealed" do
        it "includes the work" do
          work_search = WorkSearchForm.new(query: "pseud_ids: #{pseud_id}")
          expect(work_search.search_results).to include second_work
        end
      end
    end

    describe "when searching unposted works" do
      before(:each) do
        work.update_attribute(:posted, false)
        run_all_indexing_jobs
      end

      it "should not return them by default" do
        work_search = WorkSearchForm.new(query: "Hobbit")
        expect(work_search.search_results).not_to include work
      end
    end

    describe "when searching restricted works" do
      before(:each) do
        work.update_attribute(:restricted, true)
        run_all_indexing_jobs
      end

      it "should not return them by default" do
        work_search = WorkSearchForm.new(query: "Hobbit")
        expect(work_search.search_results).not_to include work
      end

      it "should return them when asked" do
        work_search = WorkSearchForm.new(query: "Hobbit", show_restricted: true)
        expect(work_search.search_results).to include work
      end
    end

    describe "when searching incomplete works" do
      it "should not return them when asked for complete works" do
        work_search = WorkSearchForm.new(query: "Hobbit", complete: true)
        expect(work_search.search_results).not_to include work
      end
    end

    describe "when searching by title" do
      it "should match partial titles" do
        work_search = WorkSearchForm.new(title: "back again")
        expect(work_search.search_results).to include work
      end

      it "should not match fields other than titles" do
        work_search = WorkSearchForm.new(title: "Privet Drive")
        expect(work_search.search_results).not_to include second_work
      end
    end

    describe "when searching by author" do
      it "should match partial author names" do
        work_search = WorkSearchForm.new(creators: "Rowling")
        expect(work_search.search_results).to include second_work
      end

      it "should not match fields other than authors" do
        work_search = WorkSearchForm.new(creators: "Baggins")
        expect(work_search.search_results).not_to include work
      end

      it "should turn - into NOT" do
        work_search = WorkSearchForm.new(creators: "-Tolkien")
        expect(work_search.search_results).not_to include work
      end
    end

    describe "when searching by language" do
      let(:unused_language) { create(:language, short: "tlh") }

      it "should only return works in that language" do
        # "Language" dropdown, with short names
        results = WorkSearchForm.new(language_id: "ptPT").search_results
        expect(results).not_to include work
        expect(results).to include second_work

        # "Language" dropdown, with IDs (backward compatibility)
        wsf = WorkSearchForm.new(language_id: language.id)
        expect(wsf.language_id).to eq("ptPT")
        results = wsf.search_results
        expect(results).not_to include work
        expect(results).to include second_work

        # "Any field" or "Search within results", with short names
        results = WorkSearchForm.new(query: "language_id: ptPT").search_results
        expect(results).not_to include work
        expect(results).to include second_work

        # "Any field" or "Search within results", with IDs (backward compatibility)
        wsf = WorkSearchForm.new(query: "language_id: #{language.id} OR language_id: #{unused_language.id}")
        expect(wsf.query).to eq("language_id: ptPT OR language_id: tlh")
        results = wsf.search_results
        expect(results).not_to include work
        expect(results).to include second_work
      end
    end

    describe "when searching by fandom" do
      it "should only return works in that fandom" do
        work_search = WorkSearchForm.new(fandom_names: "Harry Potter")
        expect(work_search.search_results).not_to include work
        expect(work_search.search_results).to include second_work
      end

      it "should not choke on exclamation points" do
        work_search = WorkSearchForm.new(fandom_names: "Potter!")
        expect(work_search.search_results).to include second_work
        expect(work_search.search_results).not_to include work
      end
    end

    describe "when searching by collection" do
      it "should only return works in that collection" do
        work_search = WorkSearchForm.new(collection_ids: [1])
        expect(work_search.search_results).to include second_work
        expect(work_search.search_results).not_to include work
      end
    end

    describe "when searching by series title" do
      let!(:main_series) { create(:series, title: "Persona: Dancing in Starlight", works: [work]) }
      let!(:spinoff_series) { create(:series, title: "Persona 5", works: [second_work]) }
      let!(:standalone_work) { create(:work) }

      context "using the \"series_titles\" field" do
        before { run_all_indexing_jobs }

        it "returns only works in matching series" do
          results = WorkSearchForm.new(series_titles: "dancing").search_results
          expect(results).to include(work)
          expect(results).not_to include(second_work, standalone_work)
        end

        it "returns only works in matching series with numbers in titles" do
          results = WorkSearchForm.new(series_titles: "persona 5").search_results
          expect(results).to include(second_work)
          expect(results).not_to include(work, standalone_work)
        end

        it "returns all works in series for wildcard queries" do
          results = WorkSearchForm.new(series_titles: "*").search_results
          expect(results).to include(work, second_work)
          expect(results).not_to include(standalone_work)
        end
      end

      context "using the \"query\" field" do
        before { run_all_indexing_jobs }

        it "works with general queries" do
          results = WorkSearchForm.new(query: "dancing").search_results
          expect(results).to include(work)
          expect(results).not_to include(second_work, standalone_work)
        end

        it "returns only works in matching series" do
          results = WorkSearchForm.new(query: "series.title: dancing").search_results
          expect(results).to include(work)
          expect(results).not_to include(second_work, standalone_work)
        end

        it "returns only works in matching series with numbers in titles" do
          results = WorkSearchForm.new(query: "series.title: \"persona 5\"").search_results
          expect(results).to include(second_work)
          expect(results).not_to include(work, standalone_work)
        end

        it "returns all works in series for wildcard queries" do
          results = WorkSearchForm.new(query: "series.title: *").search_results
          expect(results).to include(work, second_work)
          expect(results).not_to include(standalone_work)
        end
      end

      context "after a series is renamed" do
        before do
          main_series.update!(title: "Megami Tensei")
          run_all_indexing_jobs
        end

        it "returns only works in matching series" do
          results = WorkSearchForm.new(series_titles: "megami").search_results
          expect(results).to include(work)
          expect(results).not_to include(second_work, standalone_work)
        end
      end

      context "after a work is removed from a series" do
        before do
          work.serial_works.first.destroy!
          run_all_indexing_jobs
        end

        it "returns only works in matching series" do
          results = WorkSearchForm.new(series_titles: "persona").search_results
          expect(results).to include(second_work)
          expect(results).not_to include(work, standalone_work)
        end
      end

      context "after a series is deleted" do
        before do
          spinoff_series.destroy!
          run_all_indexing_jobs
        end

        it "returns only works in matching series" do
          results = WorkSearchForm.new(series_titles: "persona").search_results
          expect(results).to include(work)
          expect(results).not_to include(second_work, standalone_work)
        end
      end
    end

    describe "when searching by word count" do
      before(:each) do
        work.chapters.first.update(content: "This is a work with a word count of ten.")
        work.save

        second_work.chapters.first.update(content: "This is a work with a word count of fifteen which is more than ten.")
        second_work.save

        run_all_indexing_jobs
      end

      it "should find the right works less than a given number" do
        work_search = WorkSearchForm.new(word_count: "<13")

        expect(work_search.search_results).to include work
        expect(work_search.search_results).not_to include second_work
      end
      it "should find the right works more than a given number" do
        work_search = WorkSearchForm.new(word_count: "> 10")
        expect(work_search.search_results).not_to include work
        expect(work_search.search_results).to include second_work
      end

      it "should find the right works within a range" do
        work_search = WorkSearchForm.new(word_count: "0-10")
        expect(work_search.search_results).to include work
        expect(work_search.search_results).not_to include second_work
      end
    end

    describe "when searching by kudos count" do
      it "should find the right works less than a given number" do
        work_search = WorkSearchForm.new(kudos_count: "< 1,000")
        expect(work_search.search_results).to include second_work
        expect(work_search.search_results).not_to include work
      end
      it "should find the right works more than a given number" do
        work_search = WorkSearchForm.new(kudos_count: "> 999")
        expect(work_search.search_results).to include work
        expect(work_search.search_results).not_to include second_work
      end

      it "should find the right works within a range" do
        work_search = WorkSearchForm.new(kudos_count: "1,000-2,000")
        expect(work_search.search_results).to include work
        expect(work_search.search_results).not_to include second_work
      end
    end

    describe "when searching by comments count" do
      it "should find the right works less than a given number" do
        work_search = WorkSearchForm.new(comments_count: "< 100")
        expect(work_search.search_results).to include second_work
        expect(work_search.search_results).not_to include work
      end
      it "should find the right works more than a given number" do
        work_search = WorkSearchForm.new(comments_count: "> 99")
        expect(work_search.search_results).to include work
        expect(work_search.search_results).not_to include second_work
      end

      it "should find the right works within a range" do
        work_search = WorkSearchForm.new(comments_count: "100-2,000")
        expect(work_search.search_results).to include work
        expect(work_search.search_results).not_to include second_work
      end
    end

    describe "when searching by bookmarks count" do
      it "should find the right works less than a given number" do
        work_search = WorkSearchForm.new(bookmarks_count: "< 10")
        expect(work_search.search_results).to include second_work
        expect(work_search.search_results).not_to include work
      end
      it "should find the right works more than a given number" do
        work_search = WorkSearchForm.new(bookmarks_count: ">9")
        expect(work_search.search_results).to include work
        expect(work_search.search_results).not_to include second_work
      end

      it "should find the right works within a range" do
        work_search = WorkSearchForm.new(bookmarks_count: "10-20")
        expect(work_search.search_results).to include work
        expect(work_search.search_results).not_to include second_work
      end
    end
  end

  describe "searching for authors who changes username" do
    let!(:user) { create(:user, login: "81_white_chain") }
    let!(:second_pseud) { create(:pseud, name: "peacekeeper", user: user) }
    let!(:work_by_default_pseud) { create(:work, authors: [user.default_pseud]) }
    let!(:work_by_second_pseud) { create(:work, authors: [second_pseud]) }

    before { run_all_indexing_jobs }

    it "matches only on their current username" do
      results = WorkSearchForm.new(creators: "81_white_chain").search_results
      expect(results).to include(work_by_default_pseud)
      expect(results).to include(work_by_second_pseud)

      user.reload
      user.login = "82_white_chain"
      user.save!
      run_all_indexing_jobs

      results = WorkSearchForm.new(creators: "81_white_chain").search_results
      expect(results).to be_empty

      results = WorkSearchForm.new(creators: "82_white_chain").search_results
      expect(results).to include(work_by_default_pseud)
      expect(results).to include(work_by_second_pseud)
    end
  end

  describe "sorting results" do
    describe "by authors" do
      before do
        %w(21st_wombat 007aardvark).each do |pseud_name|
          create(:work, authors: [create(:pseud, name: pseud_name)])
        end
        run_all_indexing_jobs
      end

      it "returns all works in the correct order of sortable pseud values" do
        sorted_pseuds_asc = ["007aardvark", "21st_wombat"]

        work_search = WorkSearchForm.new(sort_column: "authors_to_sort_on")
        expect(work_search.search_results.map(&:authors_to_sort_on)).to eq sorted_pseuds_asc

        work_search = WorkSearchForm.new(sort_column: "authors_to_sort_on", sort_direction: "asc")
        expect(work_search.search_results.map(&:authors_to_sort_on)).to eq sorted_pseuds_asc

        work_search = WorkSearchForm.new(sort_column: "authors_to_sort_on", sort_direction: "desc")
        expect(work_search.search_results.map(&:authors_to_sort_on)).to eq sorted_pseuds_asc.reverse
      end
    end

    describe "by authors who changes username" do
      let!(:user_1) { create(:user, login: "cioelle") }
      let!(:user_2) { create(:user, login: "ruth") }

      before do
        create(:work, authors: [user_1.default_pseud])
        create(:work, authors: [user_2.default_pseud])
        run_all_indexing_jobs
      end

      it "returns all works in the correct order of sortable pseud values" do
        work_search = WorkSearchForm.new(sort_column: "authors_to_sort_on")
        expect(work_search.search_results.map(&:authors_to_sort_on)).to eq ["cioelle", "ruth"]

        user_1.login = "yabalchoath"
        user_1.save!
        run_all_indexing_jobs

        work_search = WorkSearchForm.new(sort_column: "authors_to_sort_on")
        expect(work_search.search_results.map(&:authors_to_sort_on)).to eq ["ruth", "yabalchoath"]
      end
    end

    it "keeps sort order of tied works the same when work info is updated" do
      user = FactoryBot.create(:user)
      work1 = FactoryBot.create(:work, authors: [user.default_pseud])
      work2 = FactoryBot.create(:work, authors: [user.default_pseud])
      q = WorkQuery.new(sort_column: "authors_to_sort_on", sort_direction: "desc")

      run_all_indexing_jobs
      res = q.search_results.map(&:id)

      [work1, work2].each do |work|
        work.update(summary: "Updated")
        run_all_indexing_jobs
        expect(q.search_results.map(&:id)).to eq(res)
      end
    end
  end
end
