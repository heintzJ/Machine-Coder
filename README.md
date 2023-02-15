# Machine-Coder

Takes an instruction is assembly and turns it into machine code.
Currently works for arithmetic and memory instructions.

Correct input for memory should follow:
`<Instruction> <register or integer> <register or integer>`

ex: ld 5 r1 

Correct input for arithmetic should follow:
`<Instruction> <register> <register or integer> <register>`

ex: addcc r1 5 r3
