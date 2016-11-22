# == Class: apache
#
# Installs apache server, sets config file, and sets provisioning dynamic site.
#
class apache{
  package { ['httpd']:
    ensure => present;
}

user {['apache user']:
    name => "apache",
    gid => "vagrant",
    require => Package['httpd'];
}


#Ensures that db started

#Replace configuration files for less restrictive connection options:
file {

    '/etc/httpd/conf/httpd.conf':
      source  => 'puppet:///modules/apache/httpd.conf',
      owner => "root",
      group => "root",
      mode => 644,
      require => Package['httpd'],
      notify => Service['httpd'];
}

service { "httpd":
    ensure => "running",
    hasstatus => "true",
    status => "true",
    subscribe => File["/etc/httpd/conf/httpd.conf"],
    require => User['apache user'];
    
}









}
