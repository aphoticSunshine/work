import re

string = input("Please type in a string with an even number of a's and an odd number of b's,\nfollowed by any number of c's/d's OR a string containing even occurences of cbad:\n")
x = re.match(r"(b(aa|bb|aabb|abab|abba|baba|baab|bbaa)*(abaaba)*[cdCD]*)|((aa|bb|aabb|abab|abba|baba|baab|bbaa)*(abaaba)*b(aa|bb|aabb|abab|abba|baba|baab|bbaa)*(abaaba)*[cdCD]*)|(cbadcbad)*", string)
if x == None:
  print("Sorry thats a bad string!")
if x.span()[1] != len(string):
  print("Sorry thats a bad string!")
else:
  print("Good job, the string is a match!")
