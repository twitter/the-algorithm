namespace :memcached do

 # For example
 # WORKS="posted = 0" rake memcached:clear_work
 #
 desc "Clear memcached"
 task :expire_work_blurbs => :environment  do
  works=ENV['WORKS'] || 'id=1'
  Work.where(works).find_each do |work|
    puts "Clear memcached #{work.id}"
    Work.expire_work_blurb_version(work.id)
  end 
 end

end
