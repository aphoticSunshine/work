import re

string = input("Enter a C++ multiline comment!\n")
x = re.match(r"\/\*.*\*\/", string)
if x == None:
  print("Sorry thats not a comment :/")
elif x.span()[1] != len(string):
  print("Sorry thats not a comment :/")
else:
  print("Good job, that's a sick comment!")
