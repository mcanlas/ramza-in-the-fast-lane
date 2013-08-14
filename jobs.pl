use strict;
use warnings;

use 5.18.1;

my @classes;
my %classes;

while (<STDIN>) {
  chomp;

  my($class, @prerequisites) = split /, /;

  push @classes, $class;
  $classes{ $class } = $#classes;

  for (@prerequisites) {
    my($class, $level) = /(\D+) (\d|\*)/;

#    $classes{$class}++;
  }
}

use Data::Dumper;
print Dumper \@classes;
print Dumper \%classes;
