# Asks for 10 integers from the user and then
# prints the sum, avg, min, and max of the 
# numbers
# @author Aarav Borthakur
# @version 11/1/23

.text 0x00400000
.globl main

main:
    # load the address arr into t0
    la $t0, arr 
    	
addToArray:
    li $v0, 5
    syscall
    move $t1, $v0   # t1 now has the input

    # load the input into the head of the array
    sw $t1, ($t0)

    addu $t0, $t0, 4 # add 4 to t0
    addu $t2, $t2, 1 # add 1 to t2

    ble $t2, 9, addToArray # if t2 is less than 10, repeat addToArray

    # 10 values are now in the array

    # Print first val
    # li $v0, 1
	# move $a0, $t1
	# syscall

la $t0, arr          # set t0 to the address of the beginning of the array

lw $t1, ($t0)       # min
lw $t2, ($t0)       # max

li $t3, 0            # load counter into $t3
li $t4, 0            # load total into t4

iterate:
    bge $t3, 10, terminate  # if i == 10, terminate

    lw $t5, ($t0)           # load the current value of the array into t5

    addu $t3, $t3, 1        # add 1 to the counter
    addu $t0, $t0, 4        # update the position of the array by 4
    addu $t4, $t4, $t5      # update the total

    ble $t5, $t1, updateMin # if the current number < min, jump to updateMin
    bge $t5, $t2, updateMax # if the current number < min, jump to updateMax

updateMax:
    move $t2, $t5             # move the value of t2 into t3 to update the max
    j iterate                 # go back to iteration

updateMin:
    move $t1, $t5             # move the value of t2 into t3 to update the max
    j iterate                 # go back to iteration

terminate:

    # print max message
    li $v0, 4
    la $a0, minText
    syscall

    # print max value
    li $v0, 1
	move $a0, $t1
	syscall

    # print new line
    li $v0, 4
    la $a0, newLine
    syscall

    # print max message
    li $v0, 4
    la $a0, maxText
    syscall

    # print max value
    li $v0, 1
	move $a0, $t2
	syscall

    # print new line
    li $v0, 4
    la $a0, newLine
    syscall

    # divide total by 10
    li $t3, 10
    div $t4, $t3
    mflo $t7  # store quotient in t7

    # print average message
    li $v0, 4
    la $a0, avgText
    syscall

    # print average
    li $v0, 1
	move $a0, $t7
	syscall

    # print new line
    li $v0, 4
    la $a0, newLine
    syscall

    # print total message
    li $v0, 4
    la $a0, totalText
    syscall

    # print total
    li $v0, 1
	move $a0, $t4
	syscall

    # clean up
    li $v0, 10
    syscall

.data
    # arr must be the first label in data
    arr:     .space 40
    maxText: .asciiz "Max: "
    minText: .asciiz "Min: "
    avgText: .asciiz "Avg: "
    totalText: .asciiz "Total: "
    newLine:      .asciiz "\n"