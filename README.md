# Machine-Coder

Takes an instruction in ARC assembly and turns it into machine code.
Currently works for arithmetic and memory instructions.

Correct input for memory should follow:
`<instruction> <register or integer> <register or integer>`

ex: ld 5 r1 

Correct input for arithmetic should follow:
`<instruction> <register> <register or integer> <register>`

ex: addcc r1 5 r3

Correct input for branching should follow:
`<type of branch> <number of lines to jump>`

Current working instructions:
- ld
- st
- addcc
- subcc
- andcc
- orcc
- orncc
- srl
- jmpl
- branching
