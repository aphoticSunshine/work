import re

num = input("Type in an Integer Literal:\n")
x = re.match(r"([1-9][0-9]*|0[xX][0-9a-fA-F]+|[0-7]+)(i64|I64|ui64|Ui64|uI64|UI64|u|U|l|L)?", num)
if x == None:
  print("Sorry not an Integer Literal :(")
if x.group() == "0" and len(num) > 1:
  print("Sorry not an Integer Literal :(")
else:
  print("That's a sick Integer Literal :)")