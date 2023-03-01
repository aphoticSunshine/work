import re

string = input("Enter a name for a Java method, class, or variable!\n")
x = re.match(r"[$_a-zA-Z][0-9a-zA-Z$_]*", string)
if x == None:
  print("Sorry thats not a name :/")
elif x.span()[1] != len(string):
  print("Sorry thats not a name :/")
else:
  print("Good job, that could totally be a name!")
