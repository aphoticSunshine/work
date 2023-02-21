fun main(args: Array<String>) {
  print("Do you have an email or nah? Type it here and find out: ")
  val inputString = readln()
  val pattern = Regex("[^.]([a-zA-Z0-9#!%$‘&+*–/=?^_’{|}~](.)?)+[^.]@[^-]+([a-zA-Z0-9]|(-)?)+([^-]).([^-])([a-zA-Z0-9]|(-)?)+([^-]$)")
    if(pattern.matches(inputString)) {
      println("Congrats! You have an email.")
    }
    else{
      println("Not an email dude!")
    }
}
