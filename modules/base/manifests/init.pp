# == Class: base
#
# Pure OS configuration class

class base{

package { ['git']:
    ensure => present;
}

package { ['java-1.8.0-openjdk']:
    ensure => "present";
}

#Ensures that iptables are stopped
service { "iptables":
    ensure => "stopped",
    hasstatus => "true",
    status => "true"
}


file {
    '/etc/wgetrc':
      source  => 'puppet:///modules/base/wgetrc',
      owner => "root",
      group => "root",
      mode => 644;
}




}