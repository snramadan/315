main:
   addi  $sp, $0, 4095
   add   $s7, $0, $0

   addi  $a0, $0, 30
   addi  $a1, $0, 100
   addi  $a2, $0, 20
   jal   Circle

   addi  $a0, $0, 30
   addi  $a1, $0, 80
   addi  $a2, $0, 30
   addi  $a3, $0, 30
   jal   Line

   addi  $a0, $0, 20
   addi  $a1, $0, 1
   addi  $a2, $0, 30
   addi  $a3, $0, 30
   jal   Line

   addi  $a0, $0, 40
   addi  $a1, $0, 1
   addi  $a2, $0, 30
   addi  $a3, $0, 30
   jal   Line

   addi  $a0, $0, 15
   addi  $a1, $0, 60
   addi  $a2, $0, 30
   addi  $a3, $0, 50
   jal   Line

   addi  $a0, $0, 30
   addi  $a1, $0, 50
   addi  $a2, $0, 45
   addi  $a3, $0, 60
   jal   Line

   addi  $a0, $0, 24
   addi  $a1, $0, 105
   addi  $a2, $0, 3
   jal   Circle

   addi  $a0, $0, 36
   addi  $a1, $0, 105
   addi  $a2, $0, 3
   jal   Circle

   addi  $a0, $0, 25
   addi  $a1, $0, 90
   addi  $a2, $0, 35
   addi  $a3, $0, 90
   jal   Line

   addi  $a0, $0, 25
   addi  $a1, $0, 90
   addi  $a2, $0, 20
   addi  $a3, $0, 95
   jal   Line

   addi  $a0, $0, 35
   addi  $a1, $0, 90
   addi  $a2, $0, 40
   addi  $a3, $0, 95
   jal   Line
   j     end

# t0 - x0
# t1 - y0
# t2 - x1
# t3 - y1
# t4 - abs(y1 - y0)
# t5 - abs(x1 - x0)

Line:
   addi  $sp, $sp, -1
   sw    $ra, 0($sp)
   add   $t0, $0, $a0
   add   $t1, $0, $a1
   add   $t2, $0, $a2
   add   $t3, $0, $a3
   sub   $t4, $t3, $t1
   slt   $t9, $t4, $0
   bne   $t9  $0,  changeY
   j     absolute

changeY:
   sub   $t4, $t1, $t3

absolute:
   sub   $t5, $t2, $t0
   slt   $t9, $t5, $0
   bne   $t9, $0, changeX
   j     setSt

changeX:
   sub   $t5, $t0, $t2

setStart:
   slt   $t9, $t5, $t4
   bne   $t9, $0,  set1
   addi  $a2, $0, 0
   j     check1

set1:
   addi  $a2, $0, 1

check1:
   bne   $a2, $0, swap
   j     check2

swap:
   add   $t8, $0, $t0
   add   $t0, $0, $t1
   add   $t1, $0, $t8

   add   $t8, $0, $t2
   add   $t2, $0, $t3
   add   $t3, $0, $t8

check2:
   slt   $t9, $t2, $t0
   beq   $t9, $0,  evaluateD
   add   $t8, $t0, $0
   add   $t0, $0,  $t2
   add   $t2, $0,  $t8

   add   $t8, $t1, $0
   add   $t1, $t3, $0
   add   $t3, $t8, $0

evaluateD:
   sub   $t6, $t2, $t0
   addi  $t7, $0,  0
   add   $t8, $0,  $t1
   sub   $t4, $t3, $t1
   slt   $t9, $t4, $0
   bne   $t9, $0, subsitute
   j     evaluateS

subsitute:
   sub   $t4, $t1, $t3

evaluateS:
   addi  $a3, $0,  -1
   slt   $t9, $t1, $t3
   beq   $t9, $0,  for
   addi  $a3, $0,  1

for:
   add   $t9, $t0, $0

start:
   slt   $t1, $t2, $t9
   bne   $t1, $0,  leave
   bne   $a2, $0,  plot
   add   $a0, $t9, $0
   add   $a1, $t8, $0
   jal   plot
   j     errors

plot:
   add   $a0, $t8, $0
   add   $a1, $t9, $0
   jal   plot
   j     errors

errors:
   add   $t7, $t7, $t4
   sll   $t3, $t7, 1
   addi  $t3, $t3, 1
   slt   $t1, $t6, $t3
   beq   $t1, $0,  endFor
   add   $t8, $t8, $a3
   sub   $t7, $t7, $t6

endFor:
   addi  $t9, $t9, 1
   j     start

leave:
   lw    $ra, 0($sp)
   addi  $sp,  $sp, 1
   jr    $ra

# t0 - xc
# t1 - yc
# t2 - r
# t3 - x
# t4 - y
# t5 - g
# t6 - diagonal
# t7 - right
# t8 - temp1
# t9 - temp2

Circle:
   addi  $sp, $sp, -1
   sw    $ra, 0($sp)
   add   $t0, $0, $a0
   add   $t1, $0, $a1
   add   $t2, $0, $a2
   add   $t3, $0, $0
   add   $t4, $0, $t2
   sll   $t8, $t2, 1
   addi  $t9, $0, 3
   sub   $t5, $t9, $t8
   sll   $t8, $t2, 2
   addi  $t9, $0, 10
   sub   $t6, $t9, $t8
   addi  $t7, $0, 6

while:
   addi  $t8, $t4, 1
   slt   $t9, $t3, $t8
   beq   $t9, $0, exitWhile
   add   $a0, $t0, $t3
   add   $a1, $t1, $t4
   jal   plot2
   add   $a0, $t0, $t3
   sub   $a1, $t1, $t4
   jal   plot2
   sub   $a0, $t0, $t3
   add   $a1, $t1, $t4
   jal   plot2
   sub   $a0, $t0, $t3
   sub   $a1, $t1, $t4
   jal   plot2
   add   $a0, $t0, $t4
   add   $a1, $t1, $t3
   jal   plot2
   add   $a0, $t0, $t4
   sub   $a1, $t1, $t3
   jal   plot2
   sub   $a0, $t0, $t4
   add   $a1, $t1, $t3
   jal   plot2
   sub   $a0, $t0, $t4
   sub   $a1, $t1, $t3
   jal   plot2
   addi  $t8, $0, 0
   slt   $t9, $t5, $t8
   bne   $t9, $0, less
   beq   $t9, $0, big
   j     endWhile

less:
   add   $t5, $t5, $t7
   addi  $t6, $t6, 4
   j     endWhile

big:
   add   $t5, $t5, $t6
   addi  $t6, $t6, 8
   addi  $t4, $t4, -1

endWhile:
   addi  $t7, $t7, 4
   addi  $t3, $t3, 1
   j     while

exitWhile:
   lw    $ra, 0($sp)
   addi  $sp, $sp, 1
   jr    $ra

plot2:
   sw    $a0, 0($s7)
   sw    $a1, 1($s7)
   addi  $s7, $s7, 2
   jr    $ra

end:
   add   $0, $0, $0
