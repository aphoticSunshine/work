import re

string = input("Type in a Floating Point Literal:\n")
x = re.search(r"[+-]?([0-9]*\.[0-9]+|[0-9]+\.)([eE][+-]?[0-9]+)?[lLfF]?|[+-]?[0-9]+[eE][+-]?[0-9]+[lLfF]?", string)
if x == None:
  print("Sorry not a Floating Point :(")
else:
  print("Thats a sick Floating Point :)")
