namespace :opendoors do
  
  class UrlUpdater
    def update_work(row)
      begin
        work = Work.find(row["AO3 id"])
        if work&.imported_from_url.blank? || work&.imported_from_url == row["URL Imported From"]
          work.imported_from_url = row["Original URL"]
          work.save!
          "#{work.id}\twas updated: its import url is now #{work.imported_from_url}"
        else
          "#{work.id}\twas not changed: its import url is #{work.imported_from_url}"
        end
      rescue StandardError => e
        "#{row["AO3 id"]}\twas not changed: #{e}"
      end
    end
  end
  
  desc "Map import urls based on spreadsheet data - required fields: 'AO3 id', 'URL Imported From', 'Original URL'"
  task :import_url_mapping, [:csv] => :environment do |_t, args|
    loc = if args[:csv].nil?
            puts "Where is the Open Doors CSV located?"
             STDIN.gets.chomp
          else
            args[:csv]
          end

    begin
      f = File.open("opendoors_result.txt", "w")
      url_updater = UrlUpdater.new
      
      CSV.foreach(loc, headers: true) do |row|
        result = url_updater.update_work(row)
        puts result
        f.write(result)
      end
    rescue TypeError => e # No or invalid CSV file
      puts "Error parsing CSV file #{loc}: #{e.message}"
    ensure
      f.close
    end
  end
end
