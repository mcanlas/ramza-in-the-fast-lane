use strict;
use warnings;

use 5.18.1;

chomp(my @lines = <STDIN>);

use Data::Dumper;
print Dumper \@lines;

__END__

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

my @classes;
my %classes;

use Data::Dumper;
print Dumper \@classes;
print Dumper \%classes;
