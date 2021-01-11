# Name: Saba Ramadan
# Section: 315-01
# Description: Lab 01 
# Write a function which divides a 64-bit unsigned number with a 31-bit unsigned number. 

# java code
# public class divide 
# {
#	public static void main(String[] args)
#	{
# 		long div;
#		long x;
#		long high;
#		long low;
#
#		Scanner scan = new Scanner(System.in);
#		System.out.println("Enter high: ");
#		high = scan.nextLong();
#		System.out.println("Enter low: ");
#		low = scan.nextLong();
#		System.out.println("Enter div: ");
#		div = scan.nextLong();
#
#		while (div > 1)
#		{
#			low >>= 1;
#			if(high > 0)
#			{
#				x = (high & 0x01) << 31;
#				high >>= 1;
#				low |= x;
#			}
#		    div >>= 1;
#		}
#		System.out.println("\nHigh: " + high + "\nLow: " + low + "\n");
#	}
#}

.globl intro
.globl high
.globl low
.globl divisor
.globl answer
.globl space

.data
#79 characters 0x00
intro:
	.asciiz "This program calculates the quotient of a 64 bit number and a 31 bit number\n\n"
#58 characters 0x4F
high:
	.asciiz "\nEnter the upper 32 bits of the dividend, as an integer= "
#58 characters 0x89
low:
	.asciiz "\nEnter the lower 32 bits of the dividend, as an integer= "
#51 characters 0xC3
divisor:
	.asciiz "\nEnter the 31 bits of the divisor, as an integer= "
#10 characters 0xF6
answer:
	.asciiz "\nResult= "
#3 characters 0x100
space:
	.asciiz "\n, "

.text
main:
	ori		$v0, $0, 4 		# display welcomePrompt message
	lui		$a0, 0x1001 	# display higher dividend
	syscall
	ori		$v0, $0, 4
	lui 	$a0, 0x1001
	ori 	$a0, $a0, 0x4F
	syscall
	ori 	$v0, $0, 5
	syscall
	ori		$s0, $0, 0
	add 	$s0, $v0, $0
	ori 	$v0, $0, 4 		# display lower dividend
	lui 	$a0, 0x1001
	ori 	$a0, $a0, 0x89
	syscall
	ori 	$v0, $0, 5
	syscall
	ori 	$s1, $0, 0
	add 	$s1, $v0, $0
	ori 	$v0, $0, 4
	lui 	$a0, 0x1001
	ori 	$a0, $a0, 0xC3
	syscall
	ori		$v0, $0, 5
	syscall
	ori 	$s2, $0, 0
	add 	$s2, $v0, $0

loop:
	srl 	$s1, $s1, 1 	# skip if the loop2 contains all 0's
	beq		$s0, 0, loop2 	# clear $t1
	ori		$t1, $0, 0
	and		$t1, $s0, 0x01
	sll		$t1, $t1, 0x1F
	srl 	$s0, $s0, 0x01
	or		$s1, $s1, $t1

loop2:
	srl		$s2, $s2, 1
	bne		$s2, 0x01, loop
	ori 	$v0, $0, 4
	lui		$a0, 0x1001
	ori		$a0, $a0, 0xF6
	syscall
	ori 	$v0, $0, 1
	add		$a0, $0, $s0
	syscall
	ori		$v0, $0, 4
	lui		$a0, 0x1001
	ori		$a0, $a0, 0x100
	syscall
	ori		$v0, $0, 1
	add 	$a0, $0, $s1
	syscall
	ori		$v0, $0, 0 
	ori 	$v0, $0, 10
	syscall
