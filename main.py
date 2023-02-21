import re

number = input('Type in a floating point number!\n')
x = re.search(r"([0-9]*\.)?(?(1)([0-9]*(\.)?[0-9]*(e|E)?\-?([0-9])*)|[0-9]+([eE])(\-?)[0-9]+)(f|F|l|L)?", number)
if x == None:
  print("Sorry not a float!\n")
else:
  print("Congrats its a float!\n")