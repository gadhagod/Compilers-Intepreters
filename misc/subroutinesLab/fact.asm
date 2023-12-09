# Recursively computes the factorial value given a number to compute.
# Essentially, does the following:
# 
# fact(n):
#   if (n == 1) return 1        # fact1 label
#   else return n * fact(n - 1)
# 
# print fact(input)
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

# call fact of input to get factorial
jal fact

# pop current argument from the stack into a0
lw $a0, ($sp)
addu $sp, $sp, 4

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

fact:
    # if a0 is 1, jump to postFact
    beq $a0, 1, fact1

    # add current address to stack
    subu $sp, $sp, 4
    sw $ra, ($sp)

    # push param onto the stack
    subu $sp, $sp, 4
    sw $a0, ($sp)

    # subtract 1 from a0
    subu $a0, $a0, 1
    
    # call fact
    jal fact

    # pop param from the stack into a0
    lw $a0, ($sp)
    addu $sp, $sp, 4

    # pop current address from the stack
    lw $ra, ($sp)
    addu $sp, $sp, 4    

    # multiply t0 by fact of (argument - 1) and store in $v0
    mul $v0, $v0, $a0

    # return product stored in $v0 to caller
    jr $ra

# base case
fact1:
    # put 0 into v0
    li $v0, 1

    # return 0 to caller
    jr $ra

.data
    prompt: .asciiz "Number: "