ActiveRecord::SchemaDumper.ignore_tables += [
  # MySQL/MariaDB internals
  "innodb_monitor"
]
