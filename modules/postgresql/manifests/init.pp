# == Class: postgresql
#
# Installs Postgresql server, sets config file, and loads database for dynamic site.
#
class postgresql{
  package { ['postgresql','postgresql-contrib','postgresql-server']:
    ensure => present;
}

#Ensures that db started

exec { 'initialize db':
    command => 'postgresql-setup initdb',
    require => Package['postgresql-contrib','postgresql-server','postgresql'],
    onlyif  => "test `find /var/lib/pgsql/data | wc -l` -le 1",
    path    => ['/usr/bin','/usr/sbin','/bin','/sbin'],
    user => 'root'
}

#Replace configuration files for less restrictive connection options:
file {

    '/var/lib/pgsql/data/postgresql.conf':
      source  => 'puppet:///modules/postgresql/postgresql.conf',
      owner => "postgres",
      group => "postgres",
      mode => 600,
      require => Exec['initialize db'],
      notify => Service['postgresql'];

    '/var/lib/pgsql/data/pg_hba.conf':
      source  => 'puppet:///modules/postgresql/pg_hba.conf',
      owner => "postgres",
      group => "postgres",
      mode => 600,
      require => Exec['initialize db'],
      notify => Service['postgresql'];
}

service { "postgresql":
    ensure => "running",
    hasstatus => "true",
    status => "true",
    subscribe => [
      File["/var/lib/pgsql/data/postgresql.conf"],
      File["/var/lib/pgsql/data/pg_hba.conf"]
    ]
}

exec { 'set up pass for postgres user':
    command => 'psql -c "ALTER ROLE postgres WITH PASSWORD \'postgres\';"',
    require => [Exec['initialize db'],Service['postgresql']],
    path    => ['/usr/bin','/usr/sbin','/bin','/sbin'],
    user => 'postgres'
}

exec { 'create carpark user':
    command => 'psql -c "CREATE ROLE carpark WITH LOGIN SUPERUSER PASSWORD \'carpark\';"',
    require => [Exec['initialize db'],Service['postgresql']],
    path    => ['/usr/bin','/usr/sbin','/bin','/sbin'],
    user => 'postgres'
}

exec { 'set up pass for carpark user':
    command => 'psql -c "CREATE DATABASE carparks OWNER carpark;"',
    require => [Exec['create carpark user']],
    path    => ['/usr/bin','/usr/sbin','/bin','/sbin'],
    user => 'postgres'
}

exec { 'create weather user':
    command => 'psql -c "CREATE ROLE weather WITH LOGIN SUPERUSER PASSWORD \'weather\';"',
    require => [Exec['initialize db'],Service['postgresql']],
    path    => ['/usr/bin','/usr/sbin','/bin','/sbin'],
    user => 'postgres'
}

exec { 'set up pass for weather user':
    command => 'psql -c "CREATE DATABASE weather OWNER weather;"',
    require => [Exec['create weather user']],
    path    => ['/usr/bin','/usr/sbin','/bin','/sbin'],
    user => 'postgres'
}





}
