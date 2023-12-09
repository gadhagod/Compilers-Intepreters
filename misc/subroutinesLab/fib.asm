# Recursively computes the fibonacci value given a number to compute.
# Essentially, does the following:
# 
# fib(n):
#   if (n <= 1) return n                    # fibBase label
#   else return fib(n - 1) + fib(n - 2)
# 
# print fib(input)
#
# @author Aarav Borthakur
# @version 12/9/23

.text 0x00400000

# print prompt
li $v0, 4 
la $a0, prompt
syscall

# read integer
li $v0, 5
syscall

# set a0 to v0
move $a0, $v0

# add current address to stack
subu $sp, $sp, 4
sw $ra, ($sp)

# push input onto the stack
subu $sp, $sp, 4
sw $a0, ($sp)

# call fib
jal fib

# pop current address from the stack
lw $ra, ($sp)
addu $sp, $sp, 4

# print result
move $a0, $v0
li $v0, 1
syscall

# terminate
li $v0, 10
syscall

fib:
    # peek current argument from the stack into $a0
    lw $a0, ($sp)

    ### base case
    ble $a0, 1, fibBase

    ### fib(n - 1)

    # subtract 1 from a0 into t0
    subu $t0, $a0, 1

    # push current address to stack
    subu $sp, $sp, 4
    sw $ra, ($sp)

    # push t0 as argument onto the stack
    subu $sp, $sp, 4
    sw $t0, ($sp)

    # call fib(n - 1)
    jal fib

    # pop current address from the stack
    lw $ra, ($sp)
    addu $sp, $sp, 4

    # peek current argument from the stack into $a0
    lw $a0, ($sp)

    # push value of fib(n - 1) onto stack
    subu $sp, $sp, 4
    sw $v0, ($sp)

    ### fib(n - 2)

    # subtract 2 from a0 into t1
    subu $t1, $a0, 2

    # push current address to stack
    subu $sp, $sp, 4
    sw $ra, ($sp)

    # push input onto the stack
    subu $sp, $sp, 4
    sw $t1, ($sp)

    # call fib(n - 2)
    jal fib

    # pop current address from the stack
    lw $ra, ($sp)
    addu $sp, $sp, 4

    # store fib(n - 2) into t1
    move $t1, $v0


    ### return statement

    # pop value of fib(n - 1) from stack into t0
    lw $t0, ($sp)
    addu $sp, $sp, 4

    # add fib(n - 1) and fib(n - 2) into $v0
    addu $v0, $t0, $t1

    # pop argument from stack
    addu $sp, $sp, 4

    # return sum
    jr $ra

# base case label
fibBase:
    # pop argument into $v0
    lw $v0, ($sp)
    addu $sp, $sp, 4

    # return 0
    jr $ra

.data
    prompt: .asciiz "Number: "