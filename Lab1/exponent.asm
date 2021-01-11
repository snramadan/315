# Name: Saba Ramadan
# Section: 315-01
# Description: Lab 01 
# Write a function which does exponentiation: x raised to the power of y.
# Your function should not use multiplies, but may use repeated addition.

# java code
# static int power(int x, int y) 
#{
#	 int result = x;
# 	 int inc = x;
#	 int i, j = 0;
#	
#    if (y == 0) 
#	 {
#       return 1;
#    }
#
#    for (i = 1; i < y; i++) 
#	 {
#        for (j = 1; j < x; j++) 
#		 {
#            result += inc;
#        }
#        inc = result;
#    }
#    return result;
# }

 .data
 first: .asciiz "Enter a number for X =  "

 sec: .asciiz "Enter a number for Y =  "

 answer: .asciiz "The result of the exponential function is = "

.text
main:
	li	$v0,4 		# Load address of the first
	la	$a0,first
	syscall
	li 	$v0,5 		# Move the integer into $s0
	syscall
	move $s0,$v0 	# Load address of the sec string
	li	$v0,4
	la	$a0,sec
	syscall
	li 	$v0,5
	syscall
	move 	$s1,$v0
	move	$a0,$s0
	move	$a1,$s1
	jal	exponVal
	move	$a0,$v0
	li	$v0,1
	syscall
	li 	$v0,10 
	syscall

exponVal:
	addi	$sp,$sp,-4
	sw	    $t0,4($sp)
	move	$t0,$zero
	li	$v0,1

loop:
	beq	$t0,$a1,end 	# Checks if $t0 == $a1 
	j  multiply

multiply:
	mul	$v0,$v0,$a0
	addi $t0,$t0,1
	j	loop

end:
	lw	$t0,4($sp) 		# restore $t0 and the stack
	addi $sp,$sp,4
	jr	$ra
