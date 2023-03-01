import re

string = input("Type in an email address:\n")
x = re.search(r"[^\.\@]([a-zA-Z0-9\#\!\%\$\‘\&\+\*\–\/\=\?\^\_\’\{\|\}\~](\.)?)*[^\.\@]@(([^\-\.]+|[^\-\.][^\.]*[^\-\.])\.([^\-\.]+|[^\-\.][^\.]*[^\-\.])\.*)+", string)
if x == None:
  print("Sorry not an email :(")
else:
  print("Thats a sick email :)")
