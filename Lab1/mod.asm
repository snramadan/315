# Name: Saba Ramadan
# Section: 315-01
# Description: Lab 01
# Calculates the remainder of 2 numbers
# This function uses no modulus operator, multiplication, or division - it uses only basic arithmetic/logical operations (add, sub, and...). 

# java code
# public static int mod(int num, int div) {
#
#	 int ans = 0;
#    div = div - 1;
#	 ans = num & div;
#    return ans;
# }

# global variable declaration
.globl intro

.globl question1

.globl question2

.globl question3

#  Data Area (this area contains strings to be displayed during the program)
.data

intro:
	.asciiz " This program calculates mod of two numbers \n\n"
question1:
	.asciiz " Enter num: "
question2:
    .asciiz " Enter div: "
question3:
	.asciiz " \n Mod = "

.text

main:
	ori     $v0, $0, 4 		# display intro message
	lui     $a0, 0x1001 	# loads 4 into $v0 to display
	syscall 				# Address of intro message gets generated.
	ori     $v0, $0, 4
	lui     $a0, 0x1001
	ori     $a0, $a0,0x2f
	syscall
	ori     $v0, $0, 5 		# 5 is loaded into $v0
	syscall
	ori     $s0, $0, 0 		# Clear $s0 then add num to $s0
	addu    $s0, $v0, $s0
	ori     $v0, $0, 4
	lui     $a0, 0x1001
	ori     $a0, $a0,0x3c
	syscall
	ori		$v0, $0, 5
	syscall
	addu    $s1, $v0, $s1 	# Add div to $s1
    ori 	$t0, $0, 1 		# div = div - 1;
    sub 	$s1, $s1, $t0 	# Subtract 1 from div ($s1)
    and 	$s1, $s0, $s1 	# div = num & div;
	ori     $v0, $0, 4
	lui     $a0, 0x1001
	ori     $a0, $a0,0x49
	syscall
	ori     $v0, $0, 1
	add 	$a0, $s1, $0
	syscall
	ori     $v0, $0, 10
	syscall
