import re
#input will be in the form of (0|0x|0X)[0-9]*(include [a-fA-F] on 0x|0X)(u|l|I64|UI64)
number = input('Type in a integer constant!\n') 
x = re.search(r"(0x|0X)?(0)?(?(1)([0-9a-fA-F]+(I64|UI64)?)|([0-9]+)(u|l)?)", number)
if x == None:
  print("Sorry not an integer constant!\n")
else:
  print("Congrats its an integer constant!\n")