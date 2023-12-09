# Computes the maximum of three numbers.
# Essentially, does the following:
# 
# max3(input1, input2, input3):
#   return max2(max2(input1, input2), input3)
# 
# max2(x, y):
#   if x > y return x                       # max2ReturnA0 label
#   else return y
# 
# input1  # get user input
# input2  # get user input
# input3  # get user input
# 
# print max3(input1, input2, input3)
#
# @author Aarav Borthakur
# @version 12/9/23

.text 0x00400000

# print prompt1
li $v0, 4 
la $a0, prompt1
syscall

# accept input
li $v0, 5
syscall

# move input into t0
move $t0, $v0

# print prompt2
li $v0, 4
la $a0, prompt2
syscall

# accept input
li $v0, 5
syscall

# move input t1
move $t1, $v0

# print prompt3
li $v0, 4
la $a0, prompt3
syscall

# accept input
li $v0, 5
syscall

# move input t3
move $t3, $v0

# load arguments for max3
move $a0, $t0
move $a1, $t1
move $a2, $t2

# add current address to stack
subu $sp, $sp, 4
sw $ra, ($sp)

# call max2 subroutine
jal max3

# pop current address from the stack
lw $ra, ($sp)
addu $sp, $sp, 4

# print max
move $a0, $v0
li $v0, 1
syscall

# clean up
li $v0, 10
syscall

max2:
    bgt $a0 $a1, max2ReturnA0  # if a0 > a1, jump to A0
    move $v0, $a1              # set return value to a1
    jr $ra                     # jump back

max2ReturnA0:
    move $v0, $a0          # set return value to a0
    jr $ra                 # jump back

max3: 
    # add current address to stack
    subu $sp, $sp, 4
    sw $ra, ($sp)

    # call inner max2
    jal max2

    # pop current address from the stack
    lw $ra, ($sp)
    addu $sp, $sp, 4


    # store the return value of the nested call as the first argument
    move $a0, $v0

    # store the third param to max3 to max2
    move $a1, $a2

    # add current address to stack
    subu $sp, $sp, 4
    sw $ra, ($sp)

    # call max2 subroutine
    jal max2

    # pop current address from the stack
    lw $ra, ($sp)
    addu $sp, $sp, 4

    # return
    jr $ra

.data
    prompt1: .asciiz "Number 1: "
    prompt2: .asciiz "Number 2: "
    prompt3: .asciiz "Number 3: "