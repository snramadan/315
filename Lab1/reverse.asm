# Name: Saba Ramadan
# Section: 315-01
# Description: Lab 01 
# Write a program which prints the number that represents reverse-ordered binary of the input number. 
# This means the your program will print the 32-bit number that is 
#  generated if the 32-bit input number's bits are written in reverse order

# java code
#
# public int reverse(int x)
# {
#    String str = " ";
#    String s = Integer.toBinaryString(x);
#    int ans = 0;
#    int i = 0;
#      
#    for (i = 0; i < s.length; i++)
#    {
#       if (s.charAt(i) == '1')
#       {
#            str = "0" + str;
#       }
#        else
#       {
#           str = "1" + str;
#       }
#    }
#    ans = Integer.parseInt(str);
#
#    return ans;
# }

.data
int: .word 0
integer: .asciiz "Please enter an integer: "

binInt: .asciiz "Your integer in binary code is:        "

reverse: .asciiz "And now in reverse:         "

newLine: .asciiz "\n"

.text
main:
    li $v0, 4           
    la $a0, integer
    syscall
    li $v0, 5
    syscall
    sw $v0, int
    li $t7, 31 
    j INTloop

INTloop:
    lw $t0, int 
    srlv $t1, $t0, $t7 
    bnez $t1, INT 
    beqz $t7, INT 
    addi $t7, $t7, -1
    j INTloop

INT: 
    move $t4, $t7 
    li $v0, 4
    la $a0, newLine
    syscall
    li $v0, 4
    la $a0, binInt
    syscall
    li $v0, 1
    li $t6, 32 
    sub $t7, $t6, $t7 
    j BINloop

BINloop:
    move $a0, $t1
    syscall
    beq $t7, $t6, BIN 
    lw $t0, int 
    sllv $t0, $t0, $t7 
    srl $t1, $t0, 31 
    addi $t7, $t7, 1 
    j BINloop

BIN:
    li $v0, 4
    la $a0, newLine
    syscall
    la $a0, reverse
    syscall
    addi $t6, $t6, -1 
    move $t7, $t4 
    li $t4, 0 
    li $v0, 1 
    li $t5, 0 
    lw $t0, int 

loop:
    sub $t4, $t6, $t5 
    sllv $t1, $t0, $t4 
    srl $a0, $t1, 31 
    syscall 
    beq $t5, $t7, end 
    addi $t4, $t4, 1 
    addi $t5, $t5, 1 
    j loop

end:
    li $v0, 4
    la $a0, newLine
    syscall

li $v0, 10 
syscall
